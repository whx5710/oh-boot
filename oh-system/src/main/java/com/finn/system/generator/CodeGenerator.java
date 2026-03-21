package com.finn.system.generator;

import com.finn.framework.datasource.config.DynamicDataSource;
import com.finn.system.generator.dto.TableInfo;
import com.finn.system.generator.dto.ColumnInfo;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 代码生成器核心类
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Component
public class CodeGenerator {

    private final DynamicDataSource dynamicDataSource;

    public CodeGenerator(DynamicDataSource dynamicDataSource) {
        this.dynamicDataSource = dynamicDataSource;
    }

    public static class GeneratorConfig {
        public static final String BASE_PACKAGE = "com.finn";
        public static final String MODULE_NAME = "oh-system";
        public static final String TABLE_PREFIX = "t_";
        public static final String SYSTEM_PACKAGE = BASE_PACKAGE + ".system";
        public static final String AUTHOR = "王小费 whx5710@qq.com";

        public static final String FRAMEWORK_PACKAGE = BASE_PACKAGE + ".framework";
        public static final String CORE_PACKAGE = BASE_PACKAGE + ".core";
        
        public static final String ENTITY_PACKAGE = SYSTEM_PACKAGE + ".entity";
        public static final String MAPPER_PACKAGE = SYSTEM_PACKAGE + ".mapper";
        public static final String QUERY_PACKAGE = SYSTEM_PACKAGE + ".query";
        public static final String VO_PACKAGE = SYSTEM_PACKAGE + ".vo";
        public static final String CONVERT_PACKAGE = SYSTEM_PACKAGE + ".convert";
        public static final String SERVICE_PACKAGE = SYSTEM_PACKAGE + ".service";
        public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";
        public static final String CONTROLLER_PACKAGE = SYSTEM_PACKAGE + ".controller";

        
        public static final String TABLE_NAME_ANNOTATION = FRAMEWORK_PACKAGE + ".datasource.annotations.TableName";
        public static final String TENANT_ENTITY = FRAMEWORK_PACKAGE + ".entity.TenantEntity";
        public static final String PAGES_ANNOTATION = FRAMEWORK_PACKAGE + ".datasource.annotations.Pages";
        public static final String QUERY_BASE = FRAMEWORK_PACKAGE + ".query.Query";
        public static final String PAGE_RESULT = CORE_PACKAGE + ".utils.PageResult";
        public static final String RESULT = CORE_PACKAGE + ".utils.Result";
        public static final String SERVICE_ANNOTATION = "org.springframework.stereotype.Service";
        
        public static final String JAVA_SOURCE_PATH = "/src/main/java/";
        public static final String RESOURCES_PATH = "/src/main/resources/";
        public static final String MAPPER_PATH = RESOURCES_PATH + "mapper/";
        
        public static String getPackagePath(String packageName) {
            return packageName.replace('.', '/');
        }
        
        public static String getEntityPath() {
            return JAVA_SOURCE_PATH + getPackagePath(ENTITY_PACKAGE);
        }
        
        public static String getMapperPath() {
            return JAVA_SOURCE_PATH + getPackagePath(MAPPER_PACKAGE);
        }
        
        public static String getQueryPath() {
            return JAVA_SOURCE_PATH + getPackagePath(QUERY_PACKAGE);
        }
        
        public static String getVoPath() {
            return JAVA_SOURCE_PATH + getPackagePath(VO_PACKAGE);
        }
        
        public static String getConvertPath() {
            return JAVA_SOURCE_PATH + getPackagePath(CONVERT_PACKAGE);
        }
        
        public static String getServicePath() {
            return JAVA_SOURCE_PATH + getPackagePath(SERVICE_PACKAGE);
        }
        
        public static String getServiceImplPath() {
            return JAVA_SOURCE_PATH + getPackagePath(SERVICE_IMPL_PACKAGE);
        }
        
        public static String getControllerPath() {
            return JAVA_SOURCE_PATH + getPackagePath(CONTROLLER_PACKAGE);
        }
        
        public static String getMapperXmlPath() {
            return MAPPER_PATH;
        }
        
        public static String getGenerateDate() {
            return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }

    /**
     * 获取表信息
     * @param tableName 表名
     * @return 表信息
     */
    public TableInfo getTableInfo(String tableName) throws SQLException {
        try (Connection connection = dynamicDataSource.getPrimaryDb().getConnection()) {
            TableInfo tableInfo = new TableInfo();
            tableInfo.setTableName(tableName);
            tableInfo.setEntityName(convertToEntityName(tableName));
            tableInfo.setControllerName(tableInfo.getEntityName() + "Controller");
            tableInfo.setServiceName(tableInfo.getEntityName() + "Service");
            tableInfo.setMapperName(tableInfo.getEntityName() + "Mapper");
            tableInfo.setQueryName(tableInfo.getEntityName() + "Query");
            tableInfo.setVoName(tableInfo.getEntityName() + "VO");
            tableInfo.setConvertName(tableInfo.getEntityName() + "Convert");
            tableInfo.setExtConvertName(tableInfo.getEntityName() + "ExtConvert");
            
            // 获取表注释
            String tableComment = getTableComment(connection, tableName);
            tableInfo.setTableComment(tableComment);
            
            // 获取列信息
            List<ColumnInfo> columnInfos = getColumnInfos(connection, tableName);
            tableInfo.setColumns(columnInfos);
            
            return tableInfo;
        }
    }

    /**
     * 获取表注释
     */
    private String getTableComment(Connection connection, String tableName) throws SQLException {
        String sql = "SHOW CREATE TABLE " + tableName;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                String createTable = resultSet.getString(2);
                int commentIndex = createTable.indexOf("COMMENT='");
                if (commentIndex != -1) {
                    int endIndex = createTable.indexOf("'", commentIndex + 9);
                    if (endIndex != -1) {
                        return createTable.substring(commentIndex + 9, endIndex);
                    }
                }
            }
        }
        return tableName;
    }

    /**
     * 获取列信息
     */
    private List<ColumnInfo> getColumnInfos(Connection connection, String tableName) throws SQLException {
        List<ColumnInfo> columnInfos = new ArrayList<>();
        String sql = "SHOW FULL COLUMNS FROM " + tableName;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                ColumnInfo columnInfo = new ColumnInfo();
                columnInfo.setColumnName(resultSet.getString("Field"));
                columnInfo.setColumnType(resultSet.getString("Type"));
                columnInfo.setColumnComment(resultSet.getString("Comment"));
                columnInfo.setJavaFieldName(convertToJavaFieldName(columnInfo.getColumnName()));
                columnInfo.setJavaType(convertToJavaType(columnInfo.getColumnType()));
                columnInfos.add(columnInfo);
            }
        }
        return columnInfos;
    }

    /**
     * 转换表名为实体名
     */
    private String convertToEntityName(String tableName) {
        String actualTableName = tableName;
        if (tableName.startsWith(GeneratorConfig.TABLE_PREFIX)) {
            actualTableName = tableName.substring(GeneratorConfig.TABLE_PREFIX.length());
        }
        StringBuilder sb = new StringBuilder();
        String[] parts = actualTableName.split("_");
        for (String part : parts) {
            sb.append(part.substring(0, 1).toUpperCase()).append(part.substring(1));
        }
        return sb.toString();
    }

    private String getTableNameWithoutPrefix(String tableName) {
        if (tableName.startsWith(GeneratorConfig.TABLE_PREFIX)) {
            return tableName.substring(GeneratorConfig.TABLE_PREFIX.length());
        }
        return tableName;
    }

    /**
     * 转换列名为Java字段名
     */
    private String convertToJavaFieldName(String columnName) {
        StringBuilder sb = new StringBuilder();
        String[] parts = columnName.split("_");
        for (int i = 0; i < parts.length; i++) {
            if (i == 0) {
                sb.append(parts[i].toLowerCase());
            } else {
                sb.append(parts[i].substring(0, 1).toUpperCase()).append(parts[i].substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    /**
     * 转换数据库类型为Java类型
     */
    private String convertToJavaType(String columnType) {
        if (columnType.contains("int")) {
            return "Integer";
        } else if (columnType.contains("bigint")) {
            return "Long";
        } else if (columnType.contains("varchar") || columnType.contains("text")) {
            return "String";
        } else if (columnType.contains("datetime") || columnType.contains("timestamp")) {
            return "LocalDateTime";
        } else if (columnType.contains("date")) {
            return "LocalDate";
        } else if (columnType.contains("time")) {
            return "LocalTime";
        } else if (columnType.contains("decimal")) {
            return "BigDecimal";
        } else if (columnType.contains("boolean")) {
            return "Boolean";
        } else {
            return "String";
        }
    }

    /**
     * 生成实体类代码
     */
    public String generateEntity(TableInfo tableInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("package " + GeneratorConfig.ENTITY_PACKAGE + ";\n\n");
        sb.append("import " + GeneratorConfig.TABLE_NAME_ANNOTATION + ";\n");
        sb.append("import " + GeneratorConfig.TENANT_ENTITY + ";\n");
        if (tableInfo.getColumns().stream().anyMatch(col -> col.getJavaType().equals("LocalDateTime") || col.getJavaType().equals("LocalDate") || col.getJavaType().equals("LocalTime"))) {
            sb.append("import java.time.LocalDateTime;\n");
        }
        if (tableInfo.getColumns().stream().anyMatch(col -> col.getJavaType().equals("BigDecimal"))) {
            sb.append("import java.math.BigDecimal;\n");
        }
        sb.append("\n");
        sb.append("/**\n");
        sb.append(" * ").append(tableInfo.getTableComment()).append("\n");
        sb.append(" *\n");
        sb.append(" * @author " + GeneratorConfig.AUTHOR + "\n");
        sb.append(" * @since ").append(GeneratorConfig.getGenerateDate()).append("\n");
        sb.append(" *\n");
        sb.append(" */\n");
        sb.append("@TableName(\"").append(tableInfo.getTableName()).append("\")\n");
        sb.append("public class ").append(tableInfo.getEntityName()).append(" extends TenantEntity {\n");
        
        // 生成字段
        for (ColumnInfo column : tableInfo.getColumns()) {
            if (!column.getColumnName().equals("id") && !column.getColumnName().equals("tenant_id") 
                && !column.getColumnName().equals("create_time") && !column.getColumnName().equals("update_time") 
                && !column.getColumnName().equals("db_status") && !column.getColumnName().equals("creator")
                && !column.getColumnName().equals("updater")) {
                sb.append("    /**\n");
                sb.append("     * ").append(column.getColumnComment() != null ? column.getColumnComment() : column.getColumnName()).append("\n");
                sb.append("     */\n");
                sb.append("    private ").append(column.getJavaType()).append(" ").append(column.getJavaFieldName()).append(";\n\n");
            }
        }
        
        // 生成getter和setter
        for (ColumnInfo column : tableInfo.getColumns()) {
            if (!column.getColumnName().equals("id") && !column.getColumnName().equals("tenant_id") 
                && !column.getColumnName().equals("create_time") && !column.getColumnName().equals("update_time") 
                && !column.getColumnName().equals("db_status") && !column.getColumnName().equals("creator")
                && !column.getColumnName().equals("updater")) {
                sb.append("    public ").append(column.getJavaType()).append(" get").append(column.getJavaFieldName().substring(0, 1).toUpperCase()).append(column.getJavaFieldName().substring(1)).append("() {\n");
                sb.append("        return ").append(column.getJavaFieldName()).append(";\n");
                sb.append("    }\n\n");
                sb.append("    public void set").append(column.getJavaFieldName().substring(0, 1).toUpperCase()).append(column.getJavaFieldName().substring(1)).append("(").append(column.getJavaType()).append(" ").append(column.getJavaFieldName()).append(") {\n");
                sb.append("        this.").append(column.getJavaFieldName()).append(" = ").append(column.getJavaFieldName()).append(";\n");
                sb.append("    }\n\n");
            }
        }
        
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * 生成Mapper接口代码
     */
    public String generateMapper(TableInfo tableInfo) {
        return "package " + GeneratorConfig.MAPPER_PACKAGE + ";\n\n" +
                "import " + GeneratorConfig.PAGES_ANNOTATION + ";\n" +
                "import " + GeneratorConfig.ENTITY_PACKAGE + "." + tableInfo.getEntityName() + ";\n" +
                "import " + GeneratorConfig.QUERY_PACKAGE + "." + tableInfo.getQueryName() + ";\n" +
                "import com.finn.framework.datasource.mapper.BaseMapper;\n" +
                "import org.apache.ibatis.annotations.Mapper;\n\n" +
                "import java.util.List;\n\n" +
                "/**\n" +
                " * " + tableInfo.getTableComment() + "\n" +
                " *\n" +
                " * @author " + GeneratorConfig.AUTHOR + "\n" +
                " * @since " + GeneratorConfig.getGenerateDate() + "\n" +
                " * \n" +
                " */\n" +
                "@Mapper\n" +
                "public interface " + tableInfo.getMapperName() + " extends BaseMapper<" + tableInfo.getEntityName() + "> {\n" +
                "    @Pages\n" +
                "    List<" + tableInfo.getEntityName() + "> getList(" + tableInfo.getQueryName() + " query);\n" +
                "}\n";
    }

    /**
     * 生成Query对象代码
     */
    public String generateQuery(TableInfo tableInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("package " + GeneratorConfig.QUERY_PACKAGE + ";\n\n");
        sb.append("import " + GeneratorConfig.QUERY_BASE + ";\n\n");
        sb.append("/**\n");
        sb.append(" * ").append(tableInfo.getTableComment()).append("查询\n");
        sb.append(" *\n");
        sb.append(" * @author " + GeneratorConfig.AUTHOR + "\n");
        sb.append(" * @since ").append(GeneratorConfig.getGenerateDate()).append("\n");
        sb.append(" *\n");
        sb.append(" */\n");
        sb.append("public class ").append(tableInfo.getQueryName()).append(" extends Query {\n");
        
        // 生成查询字段
        for (ColumnInfo column : tableInfo.getColumns()) {
            if (!column.getColumnName().equals("id") && !column.getColumnName().equals("tenant_id") && !column.getColumnName().equals("create_time") && !column.getColumnName().equals("update_time") && !column.getColumnName().equals("db_status")) {
                sb.append("    /**\n");
                sb.append("     * ").append(column.getColumnComment() != null ? column.getColumnComment() : column.getColumnName()).append("\n");
                sb.append("     */\n");
                sb.append("    private ").append(column.getJavaType()).append(" ").append(column.getJavaFieldName()).append(";\n\n");
            }
        }
        
        // 生成getter和setter
        for (ColumnInfo column : tableInfo.getColumns()) {
            if (!column.getColumnName().equals("id") && !column.getColumnName().equals("tenant_id") && !column.getColumnName().equals("create_time") && !column.getColumnName().equals("update_time") && !column.getColumnName().equals("db_status")) {
                sb.append("    public ").append(column.getJavaType()).append(" get").append(column.getJavaFieldName().substring(0, 1).toUpperCase()).append(column.getJavaFieldName().substring(1)).append("() {\n");
                sb.append("        return ").append(column.getJavaFieldName()).append(";\n");
                sb.append("    }\n\n");
                sb.append("    public void set").append(column.getJavaFieldName().substring(0, 1).toUpperCase()).append(column.getJavaFieldName().substring(1)).append("(").append(column.getJavaType()).append(" ").append(column.getJavaFieldName()).append(") {\n");
                sb.append("        this.").append(column.getJavaFieldName()).append(" = ").append(column.getJavaFieldName()).append(";\n");
                sb.append("    }\n\n");
            }
        }
        
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * 生成VO对象代码
     */
    public String generateVO(TableInfo tableInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("package " + GeneratorConfig.VO_PACKAGE + ";\n\n");
        sb.append("import java.io.Serial;\n");
        sb.append("import java.io.Serializable;\n");
        if (tableInfo.getColumns().stream().anyMatch(col -> col.getJavaType().equals("LocalDateTime") || col.getJavaType().equals("LocalDate") || col.getJavaType().equals("LocalTime"))) {
            sb.append("import java.time.LocalDateTime;\n");
        }
        if (tableInfo.getColumns().stream().anyMatch(col -> col.getJavaType().equals("BigDecimal"))) {
            sb.append("import java.math.BigDecimal;\n");
        }
        sb.append("\n");
        sb.append("/**\n");
        sb.append(" * ").append(tableInfo.getTableComment()).append("\n");
        sb.append(" *\n");
        sb.append(" * @author " + GeneratorConfig.AUTHOR + "\n");
        sb.append(" * @since ").append(GeneratorConfig.getGenerateDate()).append("\n");
        sb.append(" *\n");
        sb.append(" */\n");
        sb.append("public class ").append(tableInfo.getVoName()).append(" implements Serializable {\n");
        sb.append("    @Serial\n");
        sb.append("    private static final long serialVersionUID = 1L;\n\n");
        
        // 生成字段
        sb.append("    /**\n");
        sb.append("     * id\n");
        sb.append("     */\n");
        sb.append("    private Long id;\n\n");
        
        for (ColumnInfo column : tableInfo.getColumns()) {
            if (!column.getColumnName().equals("id") && !column.getColumnName().equals("db_status")) {
                sb.append("    /**\n");
                sb.append("     * ").append(column.getColumnComment() != null ? column.getColumnComment() : column.getColumnName()).append("\n");
                sb.append("     */\n");
                sb.append("    private ").append(column.getJavaType()).append(" ").append(column.getJavaFieldName()).append(";\n\n");
            }
        }
        
        sb.append("    /**\n");
        sb.append("     * 租户名称\n");
        sb.append("     */\n");
        sb.append("    private String tenantName;\n");
        
        // 生成getter和setter
        sb.append("\n    public Long getId() {\n");
        sb.append("        return id;\n");
        sb.append("    }\n\n");
        sb.append("    public void setId(Long id) {\n");
        sb.append("        this.id = id;\n");
        sb.append("    }\n\n");
        
        for (ColumnInfo column : tableInfo.getColumns()) {
            if (!column.getColumnName().equals("id") && !column.getColumnName().equals("db_status")) {
                sb.append("    public ").append(column.getJavaType()).append(" get").append(column.getJavaFieldName().substring(0, 1).toUpperCase()).append(column.getJavaFieldName().substring(1)).append("() {\n");
                sb.append("        return ").append(column.getJavaFieldName()).append(";\n");
                sb.append("    }\n\n");
                sb.append("    public void set").append(column.getJavaFieldName().substring(0, 1).toUpperCase()).append(column.getJavaFieldName().substring(1)).append("(").append(column.getJavaType()).append(" ").append(column.getJavaFieldName()).append(") {\n");
                sb.append("        this.").append(column.getJavaFieldName()).append(" = ").append(column.getJavaFieldName()).append(";\n");
                sb.append("    }\n\n");
            }
        }
        
        sb.append("    public String getTenantName() {\n");
        sb.append("        return tenantName;\n");
        sb.append("    }\n\n");
        sb.append("    public void setTenantName(String tenantName) {\n");
        sb.append("        this.tenantName = tenantName;\n");
        sb.append("    }\n");
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * 生成Convert接口代码
     */
    public String generateConvert(TableInfo tableInfo) {
        return "package " + GeneratorConfig.CONVERT_PACKAGE + ";\n\n" +
                "import " + GeneratorConfig.ENTITY_PACKAGE + "." + tableInfo.getEntityName() + ";\n" +
                "import " + GeneratorConfig.VO_PACKAGE + "." + tableInfo.getVoName() + ";\n" +
                // sb.append("import org.mapstruct.DecoratedWith;\n");
                "import org.mapstruct.Mapper;\n" +
                "import org.mapstruct.factory.Mappers;\n\n" +
                "import java.util.List;\n\n" +
                "/**\n" +
                " * " + tableInfo.getTableComment() + "\n" +
                " * @since 1.0.0 " + GeneratorConfig.getGenerateDate() + "\n" +
                " * @author " + GeneratorConfig.AUTHOR + "\n" +
                " *\n" +
                " */\n" +
                "@Mapper\n" +
                // sb.append("@DecoratedWith(" + tableInfo.getExtConvertName() + ".class) // 指定实现类\n");
                "public interface " + tableInfo.getConvertName() + " {\n\n" +
                "    " + tableInfo.getConvertName() + " INSTANCE = Mappers.getMapper(" + tableInfo.getConvertName() + ".class);\n\n" +
                "    " + tableInfo.getEntityName() + " convert(" + tableInfo.getVoName() + " vo);\n\n" +
                "    " + tableInfo.getVoName() + " convert(" + tableInfo.getEntityName() + " entity);\n\n" +
                "    List<" + tableInfo.getVoName() + "> convertList(List<" + tableInfo.getEntityName() + "> list);\n\n" +
                "}\n";
    }

    /**
     * 生成ExtConvert装饰器类代码
     */
    // public String generateExtConvert(TableInfo tableInfo) {
    //     StringBuilder sb = new StringBuilder();
    //     sb.append("package " + GeneratorConfig.CONVERT_PACKAGE + ";\n\n");
    //     sb.append("import " + GeneratorConfig.ENTITY_PACKAGE + "." + tableInfo.getEntityName() + ";\n");
    //     sb.append("import " + GeneratorConfig.VO_PACKAGE + "." + tableInfo.getVoName() + ";\n\n");
    //     sb.append("import java.util.List;\n\n");
    //     sb.append("/**\n");
    //     sb.append(" * " + tableInfo.getTableComment() + "扩展转换\n");
    //     sb.append(" *\n");
    //     sb.append(" * @author " + GeneratorConfig.AUTHOR + "\n");
    //     sb.append(" * @since " + GeneratorConfig.getGenerateDate() + "\n");
    //     sb.append(" *\n");
    //     sb.append(" */\n");
    //     sb.append("public abstract class " + tableInfo.getExtConvertName() + " implements " + tableInfo.getConvertName() + " {\n\n");
    //     sb.append("    @Override\n");
    //     sb.append("    public " + tableInfo.getVoName() + " convert(" + tableInfo.getEntityName() + " entity) {\n");
    //     sb.append("        " + tableInfo.getVoName() + " vo = " + tableInfo.getConvertName() + ".INSTANCE.convert(entity);\n");
    //     sb.append("        // 扩展转换逻辑\n");
    //     sb.append("        return vo;\n");
    //     sb.append("    }\n\n");
    //     sb.append("    @Override\n");
    //     sb.append("    public List<" + tableInfo.getVoName() + "> convertList(List<" + tableInfo.getEntityName() + "> list) {\n");
    //     sb.append("        List<" + tableInfo.getVoName() + "> voList = " + tableInfo.getConvertName() + ".INSTANCE.convertList(list);\n");
    //     sb.append("        // 扩展转换逻辑\n");
    //     sb.append("        return voList;\n");
    //     sb.append("    }\n\n");
    //     sb.append("}\n");
    //     return sb.toString();
    // }

    /**
     * 生成Service接口代码
     */
    public String generateService(TableInfo tableInfo) {
        return "package " + GeneratorConfig.SERVICE_PACKAGE + ";\n\n" +
                "import " + GeneratorConfig.PAGE_RESULT + ";\n" +
                "import " + GeneratorConfig.QUERY_PACKAGE + "." + tableInfo.getQueryName() + ";\n" +
                "import " + GeneratorConfig.VO_PACKAGE + "." + tableInfo.getVoName() + ";\n\n" +
                "import java.util.List;\n\n" +
                "/**\n" +
                " * " + tableInfo.getTableComment() + "\n" +
                " *\n" +
                " * @author " + GeneratorConfig.AUTHOR + "\n" +
                " * @since " + GeneratorConfig.getGenerateDate() + "\n" +
                " *\n" +
                " */\n" +
                "public interface " + tableInfo.getServiceName() + " {\n\n" +
                "    PageResult<" + tableInfo.getVoName() + "> page(" + tableInfo.getQueryName() + " query);\n\n" +
                "    Long save(" + tableInfo.getVoName() + " vo);\n\n" +
                "    void update(" + tableInfo.getVoName() + " vo);\n\n" +
                "    void delete(List<Long> idList);\n" +
                "}\n";
    }

    /**
     * 生成Service实现类代码
     */
    public String generateServiceImpl(TableInfo tableInfo) {
        return "package " + GeneratorConfig.SERVICE_IMPL_PACKAGE + ";\n\n" +
                "import " + GeneratorConfig.PAGE_RESULT + ";\n" +
                "import " + GeneratorConfig.CONVERT_PACKAGE + "." + tableInfo.getConvertName() + ";\n" +
                "import " + GeneratorConfig.ENTITY_PACKAGE + "." + tableInfo.getEntityName() + ";\n" +
                "import " + GeneratorConfig.MAPPER_PACKAGE + "." + tableInfo.getMapperName() + ";\n" +
                "import " + GeneratorConfig.QUERY_PACKAGE + "." + tableInfo.getQueryName() + ";\n" +
                "import " + GeneratorConfig.SERVICE_PACKAGE + "." + tableInfo.getServiceName() + ";\n" +
                "import " + GeneratorConfig.VO_PACKAGE + "." + tableInfo.getVoName() + ";\n" +
                "import " + GeneratorConfig.SERVICE_ANNOTATION + ";\n\n" +
                "import java.util.List;\n\n" +
                "/**\n" +
                " * " + tableInfo.getTableComment() + "\n" +
                " *\n" +
                " * @author " + GeneratorConfig.AUTHOR + "\n" +
                " * @since " + GeneratorConfig.getGenerateDate() + "\n" +
                " *\n" +
                " */\n" +
                "@Service\n" +
                "public class " + tableInfo.getServiceName() + "Impl implements " + tableInfo.getServiceName() + " {\n\n" +
                "    private final " + tableInfo.getMapperName() + " " + tableInfo.getMapperName().substring(0, 1).toLowerCase() + tableInfo.getMapperName().substring(1) + ";\n" +
                "    public " + tableInfo.getServiceName() + "Impl(" + tableInfo.getMapperName() + " " + tableInfo.getMapperName().substring(0, 1).toLowerCase() + tableInfo.getMapperName().substring(1) + ") {\n" +
                "        this." + tableInfo.getMapperName().substring(0, 1).toLowerCase() + tableInfo.getMapperName().substring(1) + " = " + tableInfo.getMapperName().substring(0, 1).toLowerCase() + tableInfo.getMapperName().substring(1) + ";\n" +
                "    }\n\n" +
                "    @Override\n" +
                "    public PageResult<" + tableInfo.getVoName() + "> page(" + tableInfo.getQueryName() + " query) {\n" +
                "        List<" + tableInfo.getEntityName() + "> list = " + tableInfo.getMapperName().substring(0, 1).toLowerCase() + tableInfo.getMapperName().substring(1) + ".getList(query);\n" +
                "        return new PageResult<>(" + tableInfo.getConvertName() + ".INSTANCE.convertList(list), query.getTotal());\n" +
                "    }\n\n" +
                "    @Override\n" +
                "    public Long save(" + tableInfo.getVoName() + " vo) {\n" +
                "        " + tableInfo.getEntityName() + " entity = " + tableInfo.getConvertName() + ".INSTANCE.convert(vo);\n\n" +
                "        " + tableInfo.getMapperName().substring(0, 1).toLowerCase() + tableInfo.getMapperName().substring(1) + ".insert(entity);\n" +
                "        return entity.getId();\n" +
                "    }\n\n" +
                "    @Override\n" +
                "    public void update(" + tableInfo.getVoName() + " vo) {\n" +
                "        " + tableInfo.getEntityName() + " entity = " + tableInfo.getConvertName() + ".INSTANCE.convert(vo);\n" +
                "        " + tableInfo.getMapperName().substring(0, 1).toLowerCase() + tableInfo.getMapperName().substring(1) + ".updateById(entity);\n" +
                "    }\n\n" +
                "    @Override\n" +
                "    public void delete(List<Long> idList) {\n" +
                "        idList.forEach(id -> {\n" +
                "            " + tableInfo.getEntityName() + " param = new " + tableInfo.getEntityName() + "();\n" +
                "            param.setId(id);\n" +
                "            param.setDbStatus(0);\n" +
                "            " + tableInfo.getMapperName().substring(0, 1).toLowerCase() + tableInfo.getMapperName().substring(1) + ".updateById(param);\n" +
                "        });\n" +
                "    }\n\n" +
                "}\n";
    }

    /**
     * 生成Controller类代码
     */
    public String generateController(TableInfo tableInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("package " + GeneratorConfig.CONTROLLER_PACKAGE + ";\n\n");
        sb.append("import com.finn.framework.operatelog.annotations.Log;\n");
        sb.append("import com.finn.framework.operatelog.enums.OperateTypeEnum;\n");
        sb.append("import " + GeneratorConfig.PAGE_RESULT + ";\n");
        sb.append("import " + GeneratorConfig.RESULT + ";\n");
        sb.append("import " + GeneratorConfig.QUERY_PACKAGE + ".").append(tableInfo.getQueryName()).append(";\n");
        sb.append("import " + GeneratorConfig.SERVICE_PACKAGE + ".").append(tableInfo.getServiceName()).append(";\n");
        sb.append("import " + GeneratorConfig.VO_PACKAGE + ".").append(tableInfo.getVoName()).append(";\n");
        sb.append("import jakarta.validation.Valid;\n");
        sb.append("import org.springframework.security.access.prepost.PreAuthorize;\n");
        sb.append("import org.springframework.web.bind.annotation.*;\n\n");
        sb.append("import java.util.List;\n\n");
        sb.append("/**\n");
        sb.append(" * ").append(tableInfo.getTableComment()).append("\n");
        sb.append(" *\n");
        sb.append(" * @author " + GeneratorConfig.AUTHOR + "\n");
        sb.append(" * @since ").append(GeneratorConfig.getGenerateDate()).append("\n");
        sb.append(" * \n");
        sb.append(" */\n");
        sb.append("@RestController\n");
        String requestMappingPath = tableInfo.getTableName();
        if (requestMappingPath.startsWith(GeneratorConfig.TABLE_PREFIX)) {
            requestMappingPath = requestMappingPath.substring(GeneratorConfig.TABLE_PREFIX.length());
        }
        sb.append("@RequestMapping(\"sys/").append(requestMappingPath).append("\")\n");
        sb.append("public class ").append(tableInfo.getControllerName()).append(" {\n");
        sb.append("    private final ").append(tableInfo.getServiceName()).append(" ").append(tableInfo.getServiceName().substring(0, 1).toLowerCase()).append(tableInfo.getServiceName().substring(1)).append(";\n\n");
        sb.append("    public ").append(tableInfo.getControllerName()).append("(").append(tableInfo.getServiceName()).append(" ").append(tableInfo.getServiceName().substring(0, 1).toLowerCase()).append(tableInfo.getServiceName().substring(1)).append(") {\n");
        sb.append("        this.").append(tableInfo.getServiceName().substring(0, 1).toLowerCase()).append(tableInfo.getServiceName().substring(1)).append(" = ").append(tableInfo.getServiceName().substring(0, 1).toLowerCase()).append(tableInfo.getServiceName().substring(1)).append(";\n");
        sb.append("    }\n\n");
        sb.append("    /**\n");
        sb.append("     * 分页查询\n");
        sb.append("     * @param query 查询条件\n");
        sb.append("     * @return 列表\n");
        sb.append("     */\n");
        sb.append("    @GetMapping(\"page\")\n");
        sb.append("    @PreAuthorize(\"hasAuthority('sys:").append(getTableNameWithoutPrefix(tableInfo.getTableName())).append(":page')\")\n");
        sb.append("    public Result<PageResult<").append(tableInfo.getVoName()).append(">> page(@Valid ").append(tableInfo.getQueryName()).append(" query) {\n");
        sb.append("        PageResult<").append(tableInfo.getVoName()).append("> page = ").append(tableInfo.getServiceName().substring(0, 1).toLowerCase()).append(tableInfo.getServiceName().substring(1)).append(".page(query);\n\n");
        sb.append("        return Result.ok(page);\n");
        sb.append("    }\n\n");
        sb.append("    /**\n");
        sb.append("     * 保存\n");
        sb.append("     * @param vo ").append(tableInfo.getTableComment()).append("信息\n");
        sb.append("     * @return 提示信息\n");
        sb.append("     */\n");
        sb.append("    @PostMapping\n");
        sb.append("    @Log(module = \"").append(tableInfo.getTableComment()).append("\", name = \"保存\", type = OperateTypeEnum.INSERT)\n");
        sb.append("    @PreAuthorize(\"hasAuthority('sys:").append(getTableNameWithoutPrefix(tableInfo.getTableName())).append(":save')\")\n");
        sb.append("    public Result<String> save(@RequestBody ").append(tableInfo.getVoName()).append(" vo) {\n");
        sb.append("        Long id = ").append(tableInfo.getServiceName().substring(0, 1).toLowerCase()).append(tableInfo.getServiceName().substring(1)).append(".save(vo);\n");
        sb.append("        return Result.ok(String.valueOf(id));\n");
        sb.append("    }\n\n");
        sb.append("    /**\n");
        sb.append("     * 修改\n");
        sb.append("     * @param vo ").append(tableInfo.getTableComment()).append("信息\n");
        sb.append("     * @return 提示信息\n");
        sb.append("     */\n");
        sb.append("    @PutMapping\n");
        sb.append("    @Log(module = \"").append(tableInfo.getTableComment()).append("\", name = \"修改\", type = OperateTypeEnum.UPDATE)\n");
        sb.append("    @PreAuthorize(\"hasAuthority('sys:").append(getTableNameWithoutPrefix(tableInfo.getTableName())).append(":update')\")\n");
        sb.append("    public Result<String> update(@RequestBody ").append(tableInfo.getVoName()).append(" vo) {\n");
        sb.append("        ").append(tableInfo.getServiceName().substring(0, 1).toLowerCase()).append(tableInfo.getServiceName().substring(1)).append(".update(vo);\n");
        sb.append("        return Result.ok();\n");
        sb.append("    }\n\n");
        sb.append("    /**\n");
        sb.append("     * 删除\n");
        sb.append("     * @param idList ").append(tableInfo.getTableComment()).append("ID集合\n");
        sb.append("     * @return 提示信息\n");
        sb.append("     */\n");
        sb.append("    @PostMapping(\"/del\")\n");
        sb.append("    @Log(module = \"").append(tableInfo.getTableComment()).append("\", name = \"删除\", type = OperateTypeEnum.DELETE)\n");
        sb.append("    @PreAuthorize(\"hasAuthority('sys:").append(getTableNameWithoutPrefix(tableInfo.getTableName())).append(":delete')\")\n");
        sb.append("    public Result<String> delete(@RequestBody List<Long> idList) {\n");
        sb.append("        ").append(tableInfo.getServiceName().substring(0, 1).toLowerCase()).append(tableInfo.getServiceName().substring(1)).append(".delete(idList);\n\n");
        sb.append("        return Result.ok();\n");
        sb.append("    }\n");
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * 生成MyBatis XML映射文件代码
     */
    public String generateMapperXml(TableInfo tableInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
        sb.append("<mapper namespace=\"" + GeneratorConfig.MAPPER_PACKAGE + ".").append(tableInfo.getMapperName()).append("\">\n");
        sb.append("    <resultMap id=\"BaseResultMap\" type=\"" + GeneratorConfig.ENTITY_PACKAGE + ".").append(tableInfo.getEntityName()).append("\">");
        sb.append("        <id property=\"id\" column=\"id\" />\n");
        for (ColumnInfo column : tableInfo.getColumns()) {
            if (!column.getColumnName().equals("id")) {
                sb.append("        <result property=\"").append(column.getJavaFieldName()).append("\" column=\"").append(column.getColumnName()).append("\" />\n");
            }
        }
        sb.append("    </resultMap>\n");
        sb.append("    <sql id=\"Base_Column_List\">\n");
        sb.append("        id\n");
        for (ColumnInfo column : tableInfo.getColumns()) {
            if (!column.getColumnName().equals("id")) {
                sb.append("        , ").append(column.getColumnName()).append("\n");
            }
        }
        sb.append("    </sql>\n");
        sb.append("    <select id=\"getList\" resultMap=\"BaseResultMap\">\n");
        sb.append("        select\n");
        sb.append("        <include refid=\"Base_Column_List\" />\n");
        sb.append("        from ").append(tableInfo.getTableName()).append("\n");
        sb.append("        where db_status = 1\n");
        for (ColumnInfo column : tableInfo.getColumns()) {
            if (!column.getColumnName().equals("id") && !column.getColumnName().equals("tenant_id") && !column.getColumnName().equals("create_time") && !column.getColumnName().equals("update_time") && !column.getColumnName().equals("db_status")) {
                sb.append("        <if test=\"").append(column.getJavaFieldName()).append(" != null");
                if (column.getJavaType().equals("String")) {
                    sb.append(" and ").append(column.getJavaFieldName()).append(" != ''");
                }
                sb.append("\">\n");
                sb.append("            and ").append(column.getColumnName()).append(" = #{").append(column.getJavaFieldName()).append("}\n");
                sb.append("        </if>\n");
            }
        }
        sb.append("        order by create_time desc\n");
        sb.append("    </select>\n");
        sb.append("</mapper>");
        return sb.toString();
    }
}