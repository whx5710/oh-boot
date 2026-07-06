package com.finn.system.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SQL 方言转换工具类
 *
 * <p>支持 MySQL 与 PostgreSQL 之间常见的 DDL/DML 语法互转。</p>
 * <p>主要用于 Navicat 等工具导出的建表脚本、注释、索引、主键等结构转换，
 * 转换后建议在目标数据库执行前人工复核。</p>
 *
 * @author finn
 */
public class SqlDialectConverter {

    /**
     * 方言类型
     */
    public enum DialectType {
        MYSQL,
        POSTGRESQL
    }

    /**
     * 转换 SQL 文件并输出到新文件
     *
     * @param inputPath  源 SQL 文件路径
     * @param outputPath 目标 SQL 文件路径
     * @param from       源方言
     * @param to         目标方言
     * @throws IOException IO 异常
     */
    public static void convertFile(String inputPath, String outputPath, DialectType from, DialectType to) throws IOException {
        Path in = Paths.get(inputPath);
        Path out = Paths.get(outputPath);
        String sql = Files.readString(in, StandardCharsets.UTF_8);
        String converted = convert(sql, from, to);
        Files.createDirectories(out.getParent());
        Files.writeString(out, converted, StandardCharsets.UTF_8);
    }

    /**
     * 转换 SQL 文本
     *
     * @param sql  原始 SQL
     * @param from 源方言
     * @param to   目标方言
     * @return 转换后的 SQL
     */
    public static String convert(String sql, DialectType from, DialectType to) {
        if (from == to) {
            return sql;
        }
        if (from == DialectType.POSTGRESQL && to == DialectType.MYSQL) {
            return pgToMySql(sql);
        }
        if (from == DialectType.MYSQL && to == DialectType.POSTGRESQL) {
            return mySqlToPg(sql);
        }
        throw new IllegalArgumentException("不支持的方言转换: " + from + " -> " + to);
    }

    // ==================== PostgreSQL -> MySQL ====================

    private static String pgToMySql(String sql) {
        List<String> statements = splitStatements(sql);

        // 第一遍：收集列注释，便于在 CREATE TABLE 中内联
        Map<String, String> columnComments = new LinkedHashMap<>();
        for (String stmt : statements) {
            String trimmed = stmt.trim();
            if (upperStartsWith(trimmed, "COMMENT ON COLUMN")) {
                String[] info = parsePgColumnComment(trimmed);
                if (info != null) {
                    columnComments.put(info[0] + "." + info[1], info[2]);
                }
            }
        }

        Set<String> droppedTables = new HashSet<>();
        // 收集每表的列类型，用于 INSERT 时值转换（如 bool -> tinyint(1)）
        Map<String, List<String>> tableColumnTypes = new LinkedHashMap<>();
        StringBuilder result = new StringBuilder();
        for (String stmt : statements) {
            String trimmedNoComments = removeComments(stmt).trim();
            if (trimmedNoComments.isEmpty()) {
                continue;
            }

            // DROP TABLE
            if (upperStartsWith(trimmedNoComments, "DROP TABLE")) {
                String convertedDrop = convertPgDropTable(trimmedNoComments, droppedTables);
                if (convertedDrop != null) {
                    result.append(convertedDrop).append("\n\n");
                }
                continue;
            }

            // 表注释
            if (upperStartsWith(trimmedNoComments, "COMMENT ON TABLE")) {
                String cmt = convertPgTableCommentToInline(trimmedNoComments);
                if (cmt != null) {
                    result.append(cmt).append("\n\n");
                }
                continue;
            }

            // 列注释已在 CREATE TABLE 中内联，单独语句跳过
            if (upperStartsWith(trimmedNoComments, "COMMENT ON COLUMN")) {
                continue;
            }

            // 主键约束
            if (upperStartsWith(trimmedNoComments, "ALTER TABLE") && trimmedNoComments.toUpperCase().contains("PRIMARY KEY")) {
                String pk = extractPgPrimaryKey(trimmedNoComments);
                if (pk != null) {
                    result.append(pk).append("\n\n");
                }
                continue;
            }

            // 唯一约束
            if (upperStartsWith(trimmedNoComments, "ALTER TABLE") && trimmedNoComments.toUpperCase().contains("UNIQUE")) {
                String uk = convertPgUniqueToMySqlIndex(trimmedNoComments);
                if (uk != null) {
                    result.append(uk).append("\n\n");
                }
                continue;
            }

            // CREATE INDEX
            if (upperStartsWith(trimmedNoComments, "CREATE INDEX")) {
                String idx = convertPgIndexToMySql(trimmedNoComments);
                if (idx != null) {
                    result.append(idx).append("\n\n");
                }
                continue;
            }

            // CREATE TABLE
            if (upperStartsWith(trimmedNoComments, "CREATE TABLE")) {
                result.append(convertPgCreateTable(trimmedNoComments, columnComments, droppedTables, tableColumnTypes)).append("\n\n");
                continue;
            }

            // INSERT INTO：替换 bool 值 't'/'f' 为 1/0，再替换引号
            if (upperStartsWith(trimmedNoComments, "INSERT INTO")) {
                result.append(convertPgInsertToMySql(trimmedNoComments, tableColumnTypes)).append("\n\n");
                continue;
            }

            // 其他语句：仅替换字符串外的双引号为反引号，schema 前缀去掉
            result.append(replacePgQuotesOutsideStrings(trimmedNoComments)).append("\n\n");
        }

        String output = result.toString();
        // 移除残留的 schema 前缀（反引号形式）
        output = output.replaceAll("`([a-zA-Z_][a-zA-Z0-9_]*)`\\.`([a-zA-Z_][a-zA-Z0-9_]*)`", "`$2`");
        output = addMySqlHeaderIfNeeded(output);
        return output.trim() + "\n";
    }

    private static String convertPgDropTable(String sql, Set<String> droppedTables) {
        // DROP TABLE IF EXISTS "schema"."table";
        Pattern p = Pattern.compile("DROP TABLE\\s+(IF EXISTS\\s+)?(?:\"([a-zA-Z_][a-zA-Z0-9_]*)\"\\.)?\"?([a-zA-Z_][a-zA-Z0-9_]*)\"?", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(sql);
        if (m.find()) {
            String ifExists = m.group(1) != null ? "IF EXISTS " : "";
            String table = m.group(3);
            droppedTables.add(table);
            return "DROP TABLE " + ifExists + "`" + table + "`;";
        }
        return replacePgQuotesOutsideStrings(sql);
    }

    private static String convertPgCreateTable(String sql, Map<String, String> columnComments, Set<String> droppedTables,
                                               Map<String, List<String>> tableColumnTypes) {
        Pattern tablePattern = Pattern.compile("CREATE TABLE\\s+(.+?)\\s*\\(", Pattern.CASE_INSENSITIVE);
        Matcher tableMatcher = tablePattern.matcher(sql);
        if (!tableMatcher.find()) {
            return replacePgQuotesOutsideStrings(sql);
        }

        String fullTableName = tableMatcher.group(1).trim();
        String tableName = stripPgQuotes(fullTableName);
        if (tableName.contains(".")) {
            tableName = tableName.substring(tableName.lastIndexOf('.') + 1);
        }

        int start = tableMatcher.end();
        int end = findMatchingParen(sql, start - 1);
        if (end <= start) {
            return replacePgQuotesOutsideStrings(sql);
        }

        String columnsBlock = sql.substring(start, end);
        String afterBlock = sql.substring(end + 1).trim();

        StringBuilder mysqlColumns = new StringBuilder();
        List<String> columnDefs = splitColumnDefs(columnsBlock);
        List<String> mysqlTypes = new ArrayList<>();

        for (String colDef : columnDefs) {
            String c = colDef.trim();
            if (c.isEmpty()) {
                continue;
            }
            if (upperStartsWith(c, "CONSTRAINT")) {
                String myCon = convertPgInlineConstraint(c);
                if (!mysqlColumns.isEmpty()) {
                    mysqlColumns.append(",\n  ");
                }
                mysqlColumns.append(myCon);
                continue;
            }

            String myCol = convertPgColumnDef(c, tableName, columnComments, mysqlTypes);
            if (!mysqlColumns.isEmpty()) {
                mysqlColumns.append(",\n  ");
            }
            mysqlColumns.append(myCol);
        }
        tableColumnTypes.put(tableName, mysqlTypes);

        StringBuilder create = new StringBuilder();
        if (!droppedTables.contains(tableName)) {
            create.append("DROP TABLE IF EXISTS `").append(tableName).append("`;\n");
            droppedTables.add(tableName);
        }
        create.append("CREATE TABLE `").append(tableName).append("` (\n  ");
        create.append(mysqlColumns);
        create.append("\n) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci");

        String tableComment = extractInlineTableComment(afterBlock);
        if (tableComment != null) {
            create.append(" COMMENT = '").append(escapeSql(tableComment)).append("'");
        }
        create.append(" ROW_FORMAT = DYNAMIC;");

        return create.toString();
    }

    private static String convertPgInsertToMySql(String sql, Map<String, List<String>> tableColumnTypes) {
        // 解析表名，支持 "schema"."table" 或 "table"
        Pattern tablePattern = Pattern.compile("INSERT INTO\\s+(?:\"([^\"]+)\"\\.)?\"?([^\"\\s(]+)\"?", Pattern.CASE_INSENSITIVE);
        Matcher tm = tablePattern.matcher(sql);
        if (!tm.find()) {
            return replacePgQuotesOutsideStrings(sql);
        }
        String table = tm.group(2);
        List<String> types = tableColumnTypes.get(table);
        if (types == null || types.isEmpty()) {
            return replacePgQuotesOutsideStrings(sql);
        }

        // 提取 VALUES 后的值部分
        Pattern valuesPattern = Pattern.compile("VALUES\\s+(.+)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher vm = valuesPattern.matcher(sql);
        if (!vm.find()) {
            return replacePgQuotesOutsideStrings(sql);
        }
        String valuesPart = vm.group(1);

        List<String> rowBlocks = extractParenthesizedGroups(valuesPart);
        if (rowBlocks.isEmpty()) {
            return replacePgQuotesOutsideStrings(sql);
        }

        StringBuilder result = new StringBuilder();
        result.append("INSERT INTO `").append(table).append("` VALUES ");
        for (int i = 0; i < rowBlocks.size(); i++) {
            if (i > 0) {
                result.append(", ");
            }
            String convertedRow = convertPgInsertValues(rowBlocks.get(i), types);
            result.append("(").append(convertedRow).append(")");
        }
        result.append(";");
        return result.toString();
    }

    private static List<String> extractParenthesizedGroups(String sql) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        int depth = 0;
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        for (int i = 0; i < sql.length(); i++) {
            char c = sql.charAt(i);
            if (c == '\'' && !inDoubleQuote) {
                inSingleQuote = !inSingleQuote;
            } else if (c == '"' && !inSingleQuote) {
                inDoubleQuote = !inDoubleQuote;
            }
            if (!inSingleQuote && !inDoubleQuote) {
                if (c == '(') {
                    if (depth == 0) {
                        current.setLength(0);
                    } else {
                        current.append(c);
                    }
                    depth++;
                    continue;
                } else if (c == ')') {
                    depth--;
                    if (depth == 0) {
                        result.add(current.toString());
                        current.setLength(0);
                    } else {
                        current.append(c);
                    }
                    continue;
                }
            }
            if (depth > 0) {
                current.append(c);
            }
        }
        return result;
    }

    private static String convertPgInsertValues(String rowBlock, List<String> types) {
        List<String> tokens = splitInsertValues(rowBlock);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < tokens.size(); i++) {
            if (i > 0) {
                result.append(", ");
            }
            String token = tokens.get(i).trim();
            if (i < types.size() && types.get(i).toLowerCase().startsWith("tinyint(1)")) {
                String upper = token.toUpperCase();
                if (upper.equals("'T'") || upper.equals("TRUE")) {
                    result.append("1");
                } else if (upper.equals("'F'") || upper.equals("FALSE")) {
                    result.append("0");
                } else {
                    result.append(token);
                }
            } else {
                result.append(token);
            }
        }
        return result.toString();
    }

    private static List<String> splitInsertValues(String rowBlock) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        int depth = 0;
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        for (int i = 0; i < rowBlock.length(); i++) {
            char c = rowBlock.charAt(i);
            if (c == '\'' && !inDoubleQuote) {
                inSingleQuote = !inSingleQuote;
            } else if (c == '"' && !inSingleQuote) {
                inDoubleQuote = !inDoubleQuote;
            }
            if (!inSingleQuote && !inDoubleQuote) {
                if (c == '(' || c == '[') {
                    depth++;
                } else if (c == ')' || c == ']') {
                    depth--;
                } else if (c == ',' && depth == 0) {
                    result.add(current.toString());
                    current.setLength(0);
                    continue;
                }
            }
            current.append(c);
        }
        if (current.length() > 0) {
            result.add(current.toString());
        }
        return result;
    }

    private static String convertPgColumnDef(String colDef, String tableName, Map<String, String> columnComments,
                                             List<String> mysqlTypes) {
        Pattern namePattern = Pattern.compile("^\"?([a-zA-Z_][a-zA-Z0-9_]*)\"?\\s+(.*)$");
        Matcher m = namePattern.matcher(colDef);
        if (!m.find()) {
            return colDef;
        }
        String colName = m.group(1);
        String rest = m.group(2).trim();

        StringBuilder sb = new StringBuilder();
        sb.append("`").append(colName).append("` ");

        // 移除 COLLATE
        rest = rest.replaceAll("(?i)COLLATE\\s+\"?pg_catalog\"?\\.\"?default\"?\\s*", " ");

        // 提取类型
        String type = extractType(rest);
        String modifiers = rest.substring(type.length()).trim();

        String mysqlType = convertPgTypeToMySql(type);
        mysqlTypes.add(mysqlType);
        sb.append(mysqlType);

        // NOT NULL / NULL
        boolean notNull = modifiers.toUpperCase().contains("NOT NULL");
        boolean explicitNull = !notNull && Pattern.compile("(?i)\\bNULL\\b(?!::)").matcher(modifiers).find();
        if (notNull) {
            sb.append(" NOT NULL");
            modifiers = modifiers.replaceAll("(?i)NOT\\s+NULL\\s*", " ");
        } else if (explicitNull) {
            sb.append(" NULL");
            modifiers = modifiers.replaceAll("(?i)\\bNULL\\b(?!::)\\s*", " ");
        }

        // DEFAULT
        String def = extractDefault(modifiers);
        if (def != null) {
            sb.append(" DEFAULT ").append(convertPgDefaultToMySql(def, type));
            modifiers = modifiers.replaceAll("(?i)DEFAULT\\s+" + Pattern.quote(def) + "\\s*", " ");
        }

        // 列注释内联
        String comment = columnComments.get(tableName + "." + colName);
        if (comment != null && !comment.isEmpty()) {
            sb.append(" COMMENT '").append(escapeSql(comment)).append("'");
        }

        return sb.toString();
    }

    private static String convertPgTypeToMySql(String pgType) {
        String upper = pgType.toUpperCase().trim();
        if (upper.equals("INT8") || upper.equals("BIGINT")) {
            return "bigint";
        }
        if (upper.equals("INT4") || upper.equals("INTEGER")) {
            return "int";
        }
        if (upper.equals("INT2") || upper.equals("SMALLINT")) {
            return "smallint";
        }
        if (upper.equals("BOOL") || upper.equals("BOOLEAN")) {
            return "tinyint(1)";
        }
        if (upper.startsWith("VARCHAR")) {
            return pgType.toLowerCase();
        }
        if (upper.equals("TEXT")) {
            return "longtext";
        }
        if (upper.startsWith("TIMESTAMP")) {
            Pattern timestampPattern = Pattern.compile("timestamp(?:\\((\\d+)\\))?", Pattern.CASE_INSENSITIVE);
            Matcher timestampMatcher = timestampPattern.matcher(pgType);
            if (timestampMatcher.find() && timestampMatcher.group(1) != null) {
                return "datetime(" + timestampMatcher.group(1) + ")";
            }
            return "datetime";
        }
        if (upper.startsWith("NUMERIC")) {
            return pgType.toLowerCase().replace("numeric", "decimal");
        }
        return pgType.toLowerCase();
    }

    private static String convertPgDefaultToMySql(String def, String type) {
        String trimmed = def.trim();
        String upper = trimmed.toUpperCase();
        String typeUpper = type.toUpperCase();

        if (upper.startsWith("NULL::")) {
            return "NULL";
        }
        if (upper.contains("::")) {
            String value = trimmed.substring(0, trimmed.indexOf("::"));
            if (typeUpper.startsWith("BOOL") || typeUpper.startsWith("INT") || typeUpper.equals("INT2") || typeUpper.equals("INT4") || typeUpper.equals("INT8")) {
                return value.replace("'", "");
            }
            return value;
        }
        if (upper.contains("NEXTVAL")) {
            return "NULL";
        }
        if (upper.equals("'T'") || upper.equals("TRUE")) {
            return "1";
        }
        if (upper.equals("'F'") || upper.equals("FALSE")) {
            return "0";
        }
        if (typeUpper.startsWith("INT") || typeUpper.equals("INT2") || typeUpper.equals("INT4") || typeUpper.equals("INT8")) {
            return trimmed.replace("'", "");
        }
        return trimmed;
    }

    private static String convertPgInlineConstraint(String conDef) {
        Pattern pkPattern = Pattern.compile("CONSTRAINT\\s+\"?([^\"]+)\"?\\s+PRIMARY KEY\\s*\\(([^)]+)\\)", Pattern.CASE_INSENSITIVE);
        Matcher pk = pkPattern.matcher(conDef);
        if (pk.find()) {
            String cols = stripPgQuotes(pk.group(2));
            return "PRIMARY KEY (" + quoteBacktick(cols) + ") USING BTREE";
        }

        Pattern ukPattern = Pattern.compile("CONSTRAINT\\s+\"?([^\"]+)\"?\\s+UNIQUE\\s*\\(([^)]+)\\)", Pattern.CASE_INSENSITIVE);
        Matcher uk = ukPattern.matcher(conDef);
        if (uk.find()) {
            String name = uk.group(1);
            String cols = stripPgQuotes(uk.group(2));
            return "CONSTRAINT `" + name + "` UNIQUE (" + quoteBacktick(cols) + ")";
        }
        return conDef;
    }

    private static String[] parsePgColumnComment(String stmt) {
        Pattern p = Pattern.compile("COMMENT ON COLUMN\\s+[^\"]*\"?([a-zA-Z_][a-zA-Z0-9_]*)\"?\\.\"?([a-zA-Z_][a-zA-Z0-9_]*)\"?\\s+IS\\s+'([^']*)'", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(stmt);
        if (m.find()) {
            String table = m.group(1);
            if (table.contains(".")) {
                table = table.substring(table.lastIndexOf('.') + 1);
            }
            return new String[]{table, m.group(2), m.group(3)};
        }
        return null;
    }

    private static String convertPgTableCommentToInline(String stmt) {
        Pattern p = Pattern.compile("COMMENT ON TABLE\\s+[^\"]*\"?([a-zA-Z_][a-zA-Z0-9_]*)\"?\\.\"?([a-zA-Z_][a-zA-Z0-9_]*)\"?\\s+IS\\s+'([^']*)'", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(stmt);
        if (m.find()) {
            String table = m.group(2);
            String comment = m.group(3);
            return "ALTER TABLE `" + table + "` COMMENT = '" + escapeSql(comment) + "';";
        }
        return null;
    }

    private static String extractPgPrimaryKey(String stmt) {
        Pattern p = Pattern.compile("ALTER TABLE\\s+[^\"]*\"?([a-zA-Z_][a-zA-Z0-9_]*)\"?\\.\"?([a-zA-Z_][a-zA-Z0-9_]*)\"?\\s+ADD CONSTRAINT\\s+\"?([^\"]+)\"?\\s+PRIMARY KEY\\s*\\(([^)]+)\\)", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(stmt);
        if (m.find()) {
            String table = m.group(2);
            String cols = stripPgQuotes(m.group(4));
            return "ALTER TABLE `" + table + "` ADD PRIMARY KEY (" + quoteBacktick(cols) + ");";
        }
        return null;
    }

    private static String convertPgUniqueToMySqlIndex(String stmt) {
        Pattern p = Pattern.compile("ALTER TABLE\\s+[^\"]*\"?([a-zA-Z_][a-zA-Z0-9_]*)\"?\\.\"?([a-zA-Z_][a-zA-Z0-9_]*)\"?\\s+ADD CONSTRAINT\\s+\"?([^\"]+)\"?\\s+UNIQUE\\s*\\(([^)]+)\\)", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(stmt);
        if (m.find()) {
            String table = m.group(2);
            String name = m.group(3);
            String cols = stripPgQuotes(m.group(4));
            return "CREATE UNIQUE INDEX `" + name + "` ON `" + table + "` (" + quoteBacktick(cols) + ");";
        }
        return null;
    }

    private static String convertPgIndexToMySql(String stmt) {
        Pattern p = Pattern.compile("CREATE INDEX\\s+\"?([^\"]+)\"?\\s+ON\\s+[^\"]*\"?([a-zA-Z_][a-zA-Z0-9_]*)\"?\\.\"?([a-zA-Z_][a-zA-Z0-9_]*)\"?\\s+USING\\s+\\w+\\s*\\(([^)]+)\\)", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(stmt);
        if (m.find()) {
            String name = m.group(1);
            String table = m.group(3);
            String cols = cleanPgIndexColumns(m.group(4));
            return "CREATE INDEX `" + name + "` ON `" + table + "` (" + quoteBacktick(cols) + ") USING BTREE;";
        }
        return null;
    }

    private static String cleanPgIndexColumns(String cols) {
        String result = cols.replaceAll("(?i)COLLATE\\s+(?:\"[^\"]*\"|\\w+)(?:\\.(?:\"[^\"]*\"|\\w+))?\\s*", " ");
        result = result.replaceAll("(?i)\"?pg_catalog\"?\\.\"?[^\"]+\"?\\s*", " ");
        result = result.replaceAll("(?i)ASC NULLS LAST", "");
        result = result.replaceAll("(?i)DESC NULLS FIRST", "");
        result = result.replaceAll("\"", "");
        return result.trim();
    }

    /**
     * 将 PostgreSQL 双引号标识符转换为 MySQL 反引号，但跳过单引号字符串内部的内容。
     */
    private static String replacePgQuotesOutsideStrings(String sql) {
        StringBuilder result = new StringBuilder();
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        for (int i = 0; i < sql.length(); i++) {
            char c = sql.charAt(i);
            if (c == '\'' && !inDoubleQuote) {
                inSingleQuote = !inSingleQuote;
                result.append(c);
            } else if (c == '"' && !inSingleQuote) {
                inDoubleQuote = !inDoubleQuote;
                result.append('`');
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    private static String extractInlineTableComment(String afterBlock) {
        Pattern p = Pattern.compile("COMMENT ON TABLE\\s+[^\"]*\"?[^\"]+\"?\\s+IS\\s+'([^']*)'", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(afterBlock);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }

    // ==================== MySQL -> PostgreSQL ====================

    private static String mySqlToPg(String sql) {
        List<String> statements = splitStatements(sql);
        Set<String> droppedTables = new HashSet<>();
        StringBuilder result = new StringBuilder();

        for (String stmt : statements) {
            String trimmedNoComments = removeComments(stmt).trim();
            if (trimmedNoComments.isEmpty()) {
                continue;
            }

            if (upperStartsWith(trimmedNoComments, "SET NAMES") || upperStartsWith(trimmedNoComments, "SET FOREIGN_KEY_CHECKS")) {
                continue;
            }

            if (upperStartsWith(trimmedNoComments, "DROP TABLE")) {
                String convertedDrop = convertMySqlDropTable(trimmedNoComments, droppedTables);
                if (convertedDrop != null) {
                    result.append(convertedDrop).append("\n\n");
                }
                continue;
            }

            if (upperStartsWith(trimmedNoComments, "CREATE TABLE")) {
                result.append(convertMySqlCreateTable(trimmedNoComments, droppedTables)).append("\n\n");
                continue;
            }

            if (upperStartsWith(trimmedNoComments, "ALTER TABLE") && trimmedNoComments.toUpperCase().contains("COMMENT")) {
                String cmt = convertMySqlCommentToPg(trimmedNoComments);
                if (cmt != null) {
                    result.append(cmt).append("\n\n");
                }
                continue;
            }

            if (upperStartsWith(trimmedNoComments, "CREATE INDEX") || upperStartsWith(trimmedNoComments, "CREATE UNIQUE INDEX")) {
                String idx = convertMySqlIndexToPg(trimmedNoComments);
                if (idx != null) {
                    result.append(idx).append("\n\n");
                }
                continue;
            }

            result.append(replaceMySqlQuotesOutsideStrings(trimmedNoComments)).append("\n\n");
        }

        return result.toString().trim() + "\n";
    }

    private static String convertMySqlDropTable(String sql, Set<String> droppedTables) {
        Pattern p = Pattern.compile("DROP TABLE\\s+(IF EXISTS\\s+)?`?([^`]+)`?", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(sql);
        if (m.find()) {
            String ifExists = m.group(1) != null ? "IF EXISTS " : "";
            String table = m.group(2);
            droppedTables.add(table);
            return "DROP TABLE " + ifExists + "\"" + table + "\";";
        }
        return replaceMySqlQuotesOutsideStrings(sql);
    }

    private static String convertMySqlCreateTable(String sql, Set<String> droppedTables) {
        Pattern tablePattern = Pattern.compile("CREATE TABLE\\s+`?([^`]+)`?\\s*\\(", Pattern.CASE_INSENSITIVE);
        Matcher tableMatcher = tablePattern.matcher(sql);
        if (!tableMatcher.find()) {
            return replaceMySqlQuotesOutsideStrings(sql);
        }

        String tableName = tableMatcher.group(1).trim();
        int start = tableMatcher.end();
        int end = findMatchingParen(sql, start - 1);
        if (end <= start) {
            return replaceMySqlQuotesOutsideStrings(sql);
        }

        String columnsBlock = sql.substring(start, end);
        String afterBlock = sql.substring(end + 1).trim();

        String tableComment = null;
        Pattern commentPattern = Pattern.compile("COMMENT\\s*=\\s*'([^']*)'", Pattern.CASE_INSENSITIVE);
        Matcher cm = commentPattern.matcher(afterBlock);
        if (cm.find()) {
            tableComment = cm.group(1);
        }

        StringBuilder pgColumns = new StringBuilder();
        List<String> columnDefs = splitColumnDefs(columnsBlock);
        List<String> inlineComments = new ArrayList<>();
        List<String> inlineConstraints = new ArrayList<>();

        for (String colDef : columnDefs) {
            String c = colDef.trim();
            if (c.isEmpty()) {
                continue;
            }

            if (upperStartsWith(c, "PRIMARY KEY") || upperStartsWith(c, "KEY ") || upperStartsWith(c, "INDEX ")
                    || upperStartsWith(c, "UNIQUE KEY") || upperStartsWith(c, "UNIQUE INDEX") || upperStartsWith(c, "CONSTRAINT")) {
                String con = convertMySqlConstraint(c, tableName);
                if (con != null) {
                    inlineConstraints.add(con);
                }
                continue;
            }

            String[] colAndComment = convertMySqlColumnDef(c);
            if (colAndComment[1] != null) {
                inlineComments.add("COMMENT ON COLUMN \"" + tableName + "\".\"" + colAndComment[2] + "\" IS '" + escapeSql(colAndComment[1]) + "';");
            }
            if (!pgColumns.isEmpty()) {
                pgColumns.append(",\n  ");
            }
            pgColumns.append(colAndComment[0]);
        }

        StringBuilder create = new StringBuilder();
        if (!droppedTables.contains(tableName)) {
            create.append("DROP TABLE IF EXISTS \"").append(tableName).append("\";\n");
            droppedTables.add(tableName);
        }
        create.append("CREATE TABLE \"").append(tableName).append("\" (\n  ");
        create.append(pgColumns);
        create.append("\n);");

        StringBuilder full = new StringBuilder(create.toString());
        if (tableComment != null && !tableComment.isEmpty()) {
            full.append("\n\nCOMMENT ON TABLE \"").append(tableName).append("\" IS '").append(escapeSql(tableComment)).append("';");
        }
        for (String cmt : inlineComments) {
            full.append("\n\n").append(cmt);
        }
        for (String con : inlineConstraints) {
            full.append("\n\n").append(con);
        }
        return full.toString();
    }

    private static String[] convertMySqlColumnDef(String colDef) {
        Pattern namePattern = Pattern.compile("^`?([a-zA-Z_][a-zA-Z0-9_]*)`?\\s+(.*)$");
        Matcher m = namePattern.matcher(colDef);
        if (!m.find()) {
            return new String[]{colDef, null, null};
        }
        String colName = m.group(1);
        String rest = m.group(2).trim();

        StringBuilder sb = new StringBuilder();
        sb.append("\"").append(colName).append("\" ");

        String type = extractType(rest);
        String modifiers = rest.substring(type.length()).trim();

        sb.append(convertMySqlTypeToPg(type));

        boolean notNull = modifiers.toUpperCase().contains("NOT NULL");
        if (notNull) {
            sb.append(" NOT NULL");
            modifiers = modifiers.replaceAll("(?i)NOT\\s+NULL\\s*", " ");
        }

        String def = extractDefault(modifiers);
        if (def != null) {
            sb.append(" DEFAULT ").append(convertMySqlDefaultToPg(def, type));
            modifiers = modifiers.replaceAll("(?i)DEFAULT\\s+" + Pattern.quote(def) + "\\s*", " ");
        }

        String comment = extractComment(modifiers);
        return new String[]{sb.toString(), comment, colName};
    }

    private static String convertMySqlTypeToPg(String mySqlType) {
        String upper = mySqlType.toUpperCase().trim();
        if (upper.startsWith("BIGINT")) {
            return "int8";
        }
        if (upper.startsWith("INT") || upper.startsWith("INTEGER")) {
            return "int4";
        }
        if (upper.startsWith("SMALLINT")) {
            return "int2";
        }
        if (upper.startsWith("TINYINT") || upper.startsWith("BOOL")) {
            return "int2";
        }
        if (upper.startsWith("DATETIME")) {
            return "timestamp(6)";
        }
        if (upper.equals("LONGTEXT") || upper.equals("MEDIUMTEXT")) {
            return "text";
        }
        if (upper.startsWith("VARCHAR")) {
            return mySqlType.toLowerCase();
        }
        if (upper.startsWith("DECIMAL")) {
            return mySqlType.toLowerCase().replace("decimal", "numeric");
        }
        return mySqlType.toLowerCase();
    }

    private static String convertMySqlDefaultToPg(String def, String type) {
        String trimmed = def.trim();
        String upper = trimmed.toUpperCase();
        String typeUpper = type.toUpperCase();

        if (upper.equals("NULL")) {
            return "NULL";
        }
        if (typeUpper.startsWith("VARCHAR") || typeUpper.startsWith("CHAR")) {
            return trimmed + "::character varying";
        }
        if (typeUpper.startsWith("TINYINT") || typeUpper.startsWith("SMALLINT") || typeUpper.startsWith("INT")) {
            return trimmed + "::smallint";
        }
        return trimmed;
    }

    private static String convertMySqlConstraint(String conDef, String tableName) {
        String upper = conDef.toUpperCase();
        if (upper.startsWith("PRIMARY KEY")) {
            Pattern p = Pattern.compile("PRIMARY KEY\\s*\\(([^)]+)\\)", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(conDef);
            if (m.find()) {
                String cols = stripBackticks(m.group(1));
                return "ALTER TABLE \"" + tableName + "\" ADD CONSTRAINT \"" + tableName + "_pkey\" PRIMARY KEY (\"" + cols.replace(", ", "\", \"") + "\");";
            }
        }
        if (upper.startsWith("UNIQUE KEY") || upper.startsWith("UNIQUE INDEX") || (upper.startsWith("CONSTRAINT") && upper.contains("UNIQUE"))) {
            Pattern p = Pattern.compile("(?:UNIQUE KEY|UNIQUE INDEX|CONSTRAINT\\s+`?([^`]+)`?)\\s+(?:UNIQUE\\s*)?\\(([^)]+)\\)", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(conDef);
            if (m.find()) {
                String name = m.group(1);
                String cols = stripBackticks(m.group(2));
                if (name == null || name.isEmpty()) {
                    name = tableName + "_uk";
                }
                return "ALTER TABLE \"" + tableName + "\" ADD CONSTRAINT \"" + name + "\" UNIQUE (\"" + cols.replace(", ", "\", \"") + "\");";
            }
        }
        if (upper.startsWith("KEY ") || upper.startsWith("INDEX ")) {
            Pattern p = Pattern.compile("(?:KEY|INDEX)\\s+`?([^`]+)`?\\s*\\(([^)]+)\\)", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(conDef);
            if (m.find()) {
                String name = m.group(1);
                String cols = stripBackticks(m.group(2));
                return "CREATE INDEX \"" + name + "\" ON \"" + tableName + "\" USING btree (\"" + cols.replace(", ", "\", \"") + "\");";
            }
        }
        if (upper.startsWith("CONSTRAINT") && upper.contains("FOREIGN KEY")) {
            return conDef.replace("`", "\"").replaceAll("(?i)\\s+USING\\s+\\w+", "");
        }
        return null;
    }

    private static String convertMySqlCommentToPg(String stmt) {
        Pattern colPattern = Pattern.compile("ALTER TABLE\\s+`?([^`]+)`?\\s+MODIFY COLUMN\\s+`?([^`]+)`?.*COMMENT\\s+'([^']*)'", Pattern.CASE_INSENSITIVE);
        Matcher cm = colPattern.matcher(stmt);
        if (cm.find()) {
            return "COMMENT ON COLUMN \"" + cm.group(1) + "\".\"" + cm.group(2) + "\" IS '" + escapeSql(cm.group(3)) + "';";
        }
        Pattern tablePattern = Pattern.compile("ALTER TABLE\\s+`?([^`]+)`?\\s+COMMENT\\s*=\\s*'([^']*)'", Pattern.CASE_INSENSITIVE);
        Matcher tm = tablePattern.matcher(stmt);
        if (tm.find()) {
            return "COMMENT ON TABLE \"" + tm.group(1) + "\" IS '" + escapeSql(tm.group(2)) + "';";
        }
        return null;
    }

    private static String convertMySqlIndexToPg(String stmt) {
        Pattern p = Pattern.compile("CREATE\\s+(UNIQUE\\s+)?INDEX\\s+`?([^`]+)`?\\s+ON\\s+`?([^`]+)`?\\s*\\(([^)]+)\\)(?:\\s+USING\\s+\\w+)?", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(stmt);
        if (m.find()) {
            boolean unique = m.group(1) != null;
            String name = m.group(2);
            String table = m.group(3);
            String cols = stripBackticks(m.group(4));
            String colsQuoted = "\"" + cols.replace(", ", "\", \"") + "\"";
            if (unique) {
                return "ALTER TABLE \"" + table + "\" ADD CONSTRAINT \"" + name + "\" UNIQUE (" + colsQuoted + ");";
            }
            return "CREATE INDEX \"" + name + "\" ON \"" + table + "\" USING btree (" + colsQuoted + ");";
        }
        return null;
    }

    private static String replaceMySqlQuotesOutsideStrings(String sql) {
        StringBuilder result = new StringBuilder();
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        for (int i = 0; i < sql.length(); i++) {
            char c = sql.charAt(i);
            if (c == '\'' && !inDoubleQuote) {
                inSingleQuote = !inSingleQuote;
                result.append(c);
            } else if (c == '`' && !inSingleQuote) {
                result.append('"');
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    // ==================== 通用工具方法 ====================

    private static List<String> splitStatements(String sql) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;

        for (int i = 0; i < sql.length(); i++) {
            char c = sql.charAt(i);
            current.append(c);

            if (c == '\'' && !inDoubleQuote) {
                inSingleQuote = !inSingleQuote;
            } else if (c == '"' && !inSingleQuote) {
                inDoubleQuote = !inDoubleQuote;
            }

            if (c == ';' && !inSingleQuote && !inDoubleQuote) {
                result.add(current.toString());
                current.setLength(0);
            }
        }
        if (!current.isEmpty()) {
            result.add(current.toString());
        }
        return result;
    }

    /**
     * 移除 SQL 中的多行注释与单行注释。
     */
    private static String removeComments(String sql) {
        String noBlockComments = sql.replaceAll("/\\*[\\s\\S]*?\\*/", "");
        StringBuilder result = new StringBuilder();
        for (String line : noBlockComments.split("\n")) {
            String trimmed = line.trim();
            if (!trimmed.startsWith("--")) {
                result.append(line).append("\n");
            }
        }
        return result.toString();
    }

    private static List<String> splitColumnDefs(String columnsBlock) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        int depth = 0;
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;

        for (int i = 0; i < columnsBlock.length(); i++) {
            char c = columnsBlock.charAt(i);

            if (c == '\'' && !inDoubleQuote) {
                inSingleQuote = !inSingleQuote;
            } else if (c == '"' && !inSingleQuote) {
                inDoubleQuote = !inDoubleQuote;
            }

            if (!inSingleQuote && !inDoubleQuote) {
                if (c == '(') {
                    depth++;
                } else if (c == ')') {
                    depth--;
                } else if (c == ',' && depth == 0) {
                    result.add(current.toString());
                    current.setLength(0);
                    continue;
                }
            }
            current.append(c);
        }
        if (!current.isEmpty()) {
            result.add(current.toString());
        }
        return result;
    }

    private static int findMatchingParen(String sql, int openPos) {
        int depth = 0;
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        for (int i = openPos; i < sql.length(); i++) {
            char c = sql.charAt(i);
            if (c == '\'' && !inDoubleQuote) {
                inSingleQuote = !inSingleQuote;
            } else if (c == '"' && !inSingleQuote) {
                inDoubleQuote = !inDoubleQuote;
            }
            if (!inSingleQuote && !inDoubleQuote) {
                if (c == '(') {
                    depth++;
                } else if (c == ')') {
                    depth--;
                    if (depth == 0) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    private static String extractType(String rest) {
        StringBuilder type = new StringBuilder();
        int i = 0;
        while (i < rest.length() && Character.isWhitespace(rest.charAt(i))) {
            i++;
        }
        while (i < rest.length()) {
            char c = rest.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                type.append(c);
                i++;
            } else if (c == '(') {
                type.append(c);
                i++;
                int depth = 1;
                while (i < rest.length() && depth > 0) {
                    char ch = rest.charAt(i);
                    type.append(ch);
                    if (ch == '(') {
                        depth++;
                    } else if (ch == ')') {
                        depth--;
                    }
                    i++;
                }
                break;
            } else {
                break;
            }
        }
        return type.toString().trim();
    }

    private static String extractDefault(String modifiers) {
        Pattern p = Pattern.compile("(?i)DEFAULT\\s+('(?:''|[^'])*'|NULL::\\S+|\\S+)(?:\\s+|$)");
        Matcher m = p.matcher(modifiers);
        if (m.find()) {
            return m.group(1).trim();
        }
        return null;
    }

    private static String extractComment(String modifiers) {
        Pattern p = Pattern.compile("(?i)COMMENT\\s+'([^']*)'");
        Matcher m = p.matcher(modifiers);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }

    private static String stripPgQuotes(String str) {
        return str.replace("\"", "").trim();
    }

    private static String stripBackticks(String str) {
        return str.replace("`", "").trim();
    }

    private static String quoteBacktick(String cols) {
        String[] parts = cols.split(",");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            String c = part.trim();
            if (!sb.isEmpty()) {
                sb.append(", ");
            }
            if (!c.isEmpty() && !c.startsWith("`")) {
                sb.append("`").append(c).append("`");
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static String escapeSql(String value) {
        return value.replace("'", "''");
    }

    private static boolean upperStartsWith(String str, String prefix) {
        return str.toUpperCase().startsWith(prefix.toUpperCase());
    }

    private static String addMySqlHeaderIfNeeded(String sql) {
        if (!sql.toUpperCase().contains("SET NAMES")) {
            return "SET NAMES utf8mb4;\nSET FOREIGN_KEY_CHECKS = 0;\n\n" + sql + "\nSET FOREIGN_KEY_CHECKS = 1;";
        }
        return sql;
    }

    /**
     * 命令行入口
     */
    public static void main(String[] args) throws IOException {
        if (args.length < 4) {
            System.out.println("用法: java SqlDialectConverter <输入文件> <输出文件> <源方言> <目标方言>");
            System.out.println("方言可选: mysql, postgresql");
            System.out.println("示例: java SqlDialectConverter oh_sys.sql oh_sys_mysql.sql postgresql mysql");
            return;
        }
        DialectType from = parseDialect(args[2]);
        DialectType to = parseDialect(args[3]);
        convertFile(args[0], args[1], from, to);
        System.out.println("转换完成: " + args[1]);
    }

    private static DialectType parseDialect(String arg) {
        return switch (arg.toLowerCase()) {
            case "mysql" -> DialectType.MYSQL;
            case "postgresql", "pg", "postgres" -> DialectType.POSTGRESQL;
            default -> throw new IllegalArgumentException("不支持的方言: " + arg);
        };
    }
}
