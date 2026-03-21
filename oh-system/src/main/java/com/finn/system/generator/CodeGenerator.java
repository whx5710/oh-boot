package com.finn.system.generator;

import com.finn.framework.datasource.config.DynamicDataSource;
import com.finn.system.generator.dto.TableInfo;
import com.finn.system.generator.dto.ColumnInfo;
import org.springframework.stereotype.Component;

import java.sql.*;
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
        public static final String SYSTEM_PACKAGE = BASE_PACKAGE + ".system";
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
        
        public static final String AUTHOR = "王小费 whx5710@qq.com";
        
        public static final String TABLE_NAME_ANNOTATION = FRAMEWORK_PACKAGE + ".datasource.annotations.TableName";
        public static final String TENANT_ENTITY = FRAMEWORK_PACKAGE + ".entity.TenantEntity";
        public static final String PAGES_ANNOTATION = FRAMEWORK_PACKAGE + ".datasource.annotations.Pages";
        public static final String QUERY_BASE = FRAMEWORK_PACKAGE + ".query.Query";
        public static final String PAGE_RESULT = CORE_PACKAGE + ".utils.PageResult";
        public static final String RESULT = CORE_PACKAGE + ".utils.Result";
        public static final String REST_CONTROLLER = "org.springframework.web.bind.annotation.RestController";
        public static final String REQUEST_MAPPING = "org.springframework.web.bind.annotation.RequestMapping";
        public static final String POST_MAPPING = "org.springframework.web.bind.annotation.PostMapping";
        public static final String PUT_MAPPING = "org.springframework.web.bind.annotation.PutMapping";
        public static final String DELETE_MAPPING = "org.springframework.web.bind.annotation.DeleteMapping";
        public static final String GET_MAPPING = "org.springframework.web.bind.annotation.GetMapping";
        public static final String REQUEST_BODY = "org.springframework.web.bind.annotation.RequestBody";
        public static final String PATH_VARIABLE = "org.springframework.web.bind.annotation.PathVariable";
        public static final String PARAM = "org.springframework.web.bind.annotation.RequestParam";
        public static final String AUTOWIRED = "org.springframework.beans.factory.annotation.Autowired";
        public static final String SERVICE_ANNOTATION = "org.springframework.stereotype.Service";
        public static final String VALIDATED = "org.springframework.validation.annotation.Validated";
        public static final String TRANSACTIONAL = "org.springframework.transaction.annotation.Transactional";
        
        public static final String MODULE_NAME = "oh-system";
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
        String sql = "DESCRIBE " + tableName;
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
        StringBuilder sb = new StringBuilder();
        String[] parts = tableName.split("_");
        for (String part : parts) {
            sb.append(part.substring(0, 1).toUpperCase()).append(part.substring(1));
        }
        return sb.toString();
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
        sb.append(" * " + tableInfo.getTableComment() + "\n");
        sb.append(" *\n");
        sb.append(" * @author " + GeneratorConfig.AUTHOR + "\n");
        sb.append(" *\n");
        sb.append(" */\n");
        sb.append("@TableName(\"" + tableInfo.getTableName() + "\")\n");
        sb.append("public class " + tableInfo.getEntityName() + " extends TenantEntity {\n");
        
        // 生成字段
        for (ColumnInfo column : tableInfo.getColumns()) {
            if (!column.getColumnName().equals("id") && !column.getColumnName().equals("tenant_id") && !column.getColumnName().equals("create_time") && !column.getColumnName().equals("update_time") && !column.getColumnName().equals("db_status")) {
                sb.append("    /**\n");
                sb.append("     * " + (column.getColumnComment() != null ? column.getColumnComment() : column.getColumnName()) + "\n");
                sb.append("     */\n");
                sb.append("    private " + column.getJavaType() + " " + column.getJavaFieldName() + ";\n\n");
            }
        }
        
        // 生成getter和setter
        for (ColumnInfo column : tableInfo.getColumns()) {
            if (!column.getColumnName().equals("id") && !column.getColumnName().equals("tenant_id") && !column.getColumnName().equals("create_time") && !column.getColumnName().equals("update_time") && !column.getColumnName().equals("db_status")) {
                sb.append("    public " + column.getJavaType() + " get" + column.getJavaFieldName().substring(0, 1).toUpperCase() + column.getJavaFieldName().substring(1) + "() {\n");
                sb.append("        return " + column.getJavaFieldName() + ";\n");
                sb.append("    }\n\n");
                sb.append("    public void set" + column.getJavaFieldName().substring(0, 1).toUpperCase() + column.getJavaFieldName().substring(1) + "(" + column.getJavaType() + " " + column.getJavaFieldName() + ") {\n");
                sb.append("        this." + column.getJavaFieldName() + " = " + column.getJavaFieldName() + ";\n");
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
        StringBuilder sb = new StringBuilder();
        sb.append("package " + GeneratorConfig.MAPPER_PACKAGE + ";\n\n");
        sb.append("import " + GeneratorConfig.PAGES_ANNOTATION + ";\n");
        sb.append("import " + GeneratorConfig.ENTITY_PACKAGE + "." + tableInfo.getEntityName() + ";\n");
        sb.append("import " + GeneratorConfig.QUERY_PACKAGE + "." + tableInfo.getQueryName() + ";\n");
        sb.append("import org.apache.ibatis.annotations.Mapper;\n\n");
        sb.append("import java.util.List;\n\n");
        sb.append("/**\n");
        sb.append(" * " + tableInfo.getTableComment() + "\n");
        sb.append(" *\n");
        sb.append(" * @author " + GeneratorConfig.AUTHOR + "\n");
        sb.append(" * \n");
        sb.append(" */\n");
        sb.append("@Mapper\n");
        sb.append("public interface " + tableInfo.getMapperName() + " {\n\n");
        sb.append("    @Pages\n");
        sb.append("    List<" + tableInfo.getEntityName() + "> getList(" + tableInfo.getQueryName() + " query);\n\n");
        sb.append("    boolean updateById(" + tableInfo.getEntityName() + " entity);\n\n");
        sb.append("    // @Options(useGeneratedKeys = true, keyProperty = \"id\", keyColumn = \"id\") // 回写ID\n");
        sb.append("    int insert(" + tableInfo.getEntityName() + " entity);\n");
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * 生成Query对象代码
     */
    public String generateQuery(TableInfo tableInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("package " + GeneratorConfig.QUERY_PACKAGE + ";\n\n");
        sb.append("import " + GeneratorConfig.QUERY_BASE + ";\n\n");
        sb.append("/**\n");
        sb.append(" * " + tableInfo.getTableComment() + "查询\n");
        sb.append(" *\n");
        sb.append(" * @author " + GeneratorConfig.AUTHOR + "\n");
        sb.append(" *\n");
        sb.append(" */\n");
        sb.append("public class " + tableInfo.getQueryName() + " extends Query {\n");
        
        // 生成查询字段
        for (ColumnInfo column : tableInfo.getColumns()) {
            if (!column.getColumnName().equals("id") && !column.getColumnName().equals("tenant_id") && !column.getColumnName().equals("create_time") && !column.getColumnName().equals("update_time") && !column.getColumnName().equals("db_status")) {
                sb.append("    /**\n");
                sb.append("     * " + (column.getColumnComment() != null ? column.getColumnComment() : column.getColumnName()) + "\n");
                sb.append("     */\n");
                sb.append("    private " + column.getJavaType() + " " + column.getJavaFieldName() + ";\n\n");
            }
        }
        
        // 生成getter和setter
        for (ColumnInfo column : tableInfo.getColumns()) {
            if (!column.getColumnName().equals("id") && !column.getColumnName().equals("tenant_id") && !column.getColumnName().equals("create_time") && !column.getColumnName().equals("update_time") && !column.getColumnName().equals("db_status")) {
                sb.append("    public " + column.getJavaType() + " get" + column.getJavaFieldName().substring(0, 1).toUpperCase() + column.getJavaFieldName().substring(1) + "() {\n");
                sb.append("        return " + column.getJavaFieldName() + ";\n");
                sb.append("    }\n\n");
                sb.append("    public void set" + column.getJavaFieldName().substring(0, 1).toUpperCase() + column.getJavaFieldName().substring(1) + "(" + column.getJavaType() + " " + column.getJavaFieldName() + ") {\n");
                sb.append("        this." + column.getJavaFieldName() + " = " + column.getJavaFieldName() + ";\n");
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
        sb.append(" * " + tableInfo.getTableComment() + "\n");
        sb.append(" *\n");
        sb.append(" * @author " + GeneratorConfig.AUTHOR + "\n");
        sb.append(" *\n");
        sb.append(" */\n");
        sb.append("public class " + tableInfo.getVoName() + " implements Serializable {\n");
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
                sb.append("     * " + (column.getColumnComment() != null ? column.getColumnComment() : column.getColumnName()) + "\n");
                sb.append("     */\n");
                sb.append("    private " + column.getJavaType() + " " + column.getJavaFieldName() + ";\n\n");
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
                sb.append("    public " + column.getJavaType() + " get" + column.getJavaFieldName().substring(0, 1).toUpperCase() + column.getJavaFieldName().substring(1) + "() {\n");
                sb.append("        return " + column.getJavaFieldName() + ";\n");
                sb.append("    }\n\n");
                sb.append("    public void set" + column.getJavaFieldName().substring(0, 1).toUpperCase() + column.getJavaFieldName().substring(1) + "(" + column.getJavaType() + " " + column.getJavaFieldName() + ") {\n");
                sb.append("        this." + column.getJavaFieldName() + " = " + column.getJavaFieldName() + ";\n");
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
        StringBuilder sb = new StringBuilder();
        sb.append("package " + GeneratorConfig.CONVERT_PACKAGE + ";\n\n");
        sb.append("import " + GeneratorConfig.ENTITY_PACKAGE + "." + tableInfo.getEntityName() + ";\n");
        sb.append("import " + GeneratorConfig.VO_PACKAGE + "." + tableInfo.getVoName() + ";\n");
        sb.append("import org.mapstruct.DecoratedWith;\n");
        sb.append("import org.mapstruct.Mapper;\n");
        sb.append("import org.mapstruct.factory.Mappers;\n\n");
        sb.append("import java.util.List;\n\n");
        sb.append("/**\n");
        sb.append(" * " + tableInfo.getTableComment() + "\n");
        sb.append(" * @since 1.0.0 2023-10-03\n");
        sb.append(" * @author " + GeneratorConfig.AUTHOR + "\n");
        sb.append(" *\n");
        sb.append(" */\n");
        sb.append("@Mapper\n");
        sb.append("@DecoratedWith(" + tableInfo.getExtConvertName() + ".class) // 指定实现类\n");
        sb.append("public interface " + tableInfo.getConvertName() + " {\n\n");
        sb.append("    " + tableInfo.getConvertName() + " INSTANCE = Mappers.getMapper(" + tableInfo.getConvertName() + ".class);\n\n");
        sb.append("    " + tableInfo.getEntityName() + " convert(" + tableInfo.getVoName() + " vo);\n\n");
        sb.append("    " + tableInfo.getVoName() + " convert(" + tableInfo.getEntityName() + " entity);\n\n");
        sb.append("    List<" + tableInfo.getVoName() + "> convertList(List<" + tableInfo.getEntityName() + "> list);\n\n");
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * 生成ExtConvert装饰器类代码
     */
    public String generateExtConvert(TableInfo tableInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("package " + GeneratorConfig.CONVERT_PACKAGE + ";\n\n");
        sb.append("import " + GeneratorConfig.ENTITY_PACKAGE + "." + tableInfo.getEntityName() + ";\n");
        sb.append("import " + GeneratorConfig.VO_PACKAGE + "." + tableInfo.getVoName() + ";\n\n");
        sb.append("import java.util.List;\n\n");
        sb.append("/**\n");
        sb.append(" * " + tableInfo.getTableComment() + "扩展转换\n");
        sb.append(" *\n");
        sb.append(" * @author " + GeneratorConfig.AUTHOR + "\n");
        sb.append(" *\n");
        sb.append(" */\n");
        sb.append("public abstract class " + tableInfo.getExtConvertName() + " implements " + tableInfo.getConvertName() + " {\n\n");
        sb.append("    @Override\n");
        sb.append("    public " + tableInfo.getVoName() + " convert(" + tableInfo.getEntityName() + " entity) {\n");
        sb.append("        " + tableInfo.getVoName() + " vo = " + tableInfo.getConvertName() + ".INSTANCE.convert(entity);\n");
        sb.append("        // 扩展转换逻辑\n");
        sb.append("        return vo;\n");
        sb.append("    }\n\n");
        sb.append("    @Override\n");
        sb.append("    public List<" + tableInfo.getVoName() + "> convertList(List<" + tableInfo.getEntityName() + "> list) {\n");
        sb.append("        List<" + tableInfo.getVoName() + "> voList = " + tableInfo.getConvertName() + ".INSTANCE.convertList(list);\n");
        sb.append("        // 扩展转换逻辑\n");
        sb.append("        return voList;\n");
        sb.append("    }\n\n");
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * 生成Service接口代码
     */
    public String generateService(TableInfo tableInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("package " + GeneratorConfig.SERVICE_PACKAGE + ";\n\n");
        sb.append("import " + GeneratorConfig.PAGE_RESULT + ";\n");
        sb.append("import " + GeneratorConfig.QUERY_PACKAGE + "." + tableInfo.getQueryName() + ";\n");
        sb.append("import " + GeneratorConfig.VO_PACKAGE + "." + tableInfo.getVoName() + ";\n\n");
        sb.append("import java.util.List;\n\n");
        sb.append("/**\n");
        sb.append(" * " + tableInfo.getTableComment() + "\n");
        sb.append(" *\n");
        sb.append(" * @author " + GeneratorConfig.AUTHOR + "\n");
        sb.append(" *\n");
        sb.append(" */\n");
        sb.append("public interface " + tableInfo.getServiceName() + " {\n\n");
        sb.append("    PageResult<" + tableInfo.getVoName() + "> page(" + tableInfo.getQueryName() + " query);\n\n");
        sb.append("    Long save(" + tableInfo.getVoName() + " vo);\n\n");
        sb.append("    void update(" + tableInfo.getVoName() + " vo);\n\n");
        sb.append("    void delete(List<Long> idList);\n");
        sb.append("}\n");
        return sb.toString();
    }

    /**
     * 生成Service实现类代码
     */
    public String generateServiceImpl(TableInfo tableInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("package " + GeneratorConfig.SERVICE_IMPL_PACKAGE + ";\n\n");
        sb.append("import " + GeneratorConfig.PAGE_RESULT + ";\n");
        sb.append("import " + GeneratorConfig.CONVERT_PACKAGE + "." + tableInfo.getConvertName() + ";\n");
        sb.append("import " + GeneratorConfig.ENTITY_PACKAGE + "." + tableInfo.getEntityName() + ";\n");
        sb.append("import " + GeneratorConfig.MAPPER_PACKAGE + "." + tableInfo.getMapperName() + ";\n");
        sb.append("import " + GeneratorConfig.QUERY_PACKAGE + "." + tableInfo.getQueryName() + ";\n");
        sb.append("import " + GeneratorConfig.SERVICE_PACKAGE + "." + tableInfo.getServiceName() + ";\n");
        sb.append("import " + GeneratorConfig.VO_PACKAGE + "." + tableInfo.getVoName() + ";\n");
        sb.append("import " + GeneratorConfig.SERVICE_ANNOTATION + ";\n\n");
        sb.append("import java.util.List;\n\n");
        sb.append("/**\n");
        sb.append(" * " + tableInfo.getTableComment() + "\n");
        sb.append(" *\n");
        sb.append(" * @author " + GeneratorConfig.AUTHOR + "\n");
        sb.append(" *\n");
        sb.append(" */\n");
        sb.append("@Service\n");
        sb.append("public class " + tableInfo.getServiceName() + "Impl implements " + tableInfo.getServiceName() + " {\n\n");
        sb.append("    private final " + tableInfo.getMapperName() + " " + tableInfo.getMapperName().substring(0, 1).toLowerCase() + tableInfo.getMapperName().substring(1) + ";\n");
        sb.append("    public " + tableInfo.getServiceName() + "Impl(" + tableInfo.getMapperName() + " " + tableInfo.getMapperName().substring(0, 1).toLowerCase() + tableInfo.getMapperName().substring(1) + ") {\n");
        sb.append("        this." + tableInfo.getMapperName().substring(0, 1).toLowerCase() + tableInfo.getMapperName().substring(1) + " = " + tableInfo.getMapperName().substring(0, 1).toLowerCase() + tableInfo.getMapperName().substring(1) + ";\n");
        sb.append("    }\n\n");
        sb.append("    @Override\n");
        sb.append("    public PageResult<" + tableInfo.getVoName() + "> page(" + tableInfo.getQueryName() + " query) {\n");
        sb.append("        List<" + tableInfo.getEntityName() + "> list = " + tableInfo.getMapperName().substring(0, 1).toLowerCase() + tableInfo.getMapperName().substring(1) + ".getList(query);\n");
        sb.append("        return new PageResult<>(" + tableInfo.getConvertName() + ".INSTANCE.convertList(list), query.getTotal());\n");
        sb.append("    }\n\n");
        sb.append("    @Override\n");
        sb.append("    public Long save(" + tableInfo.getVoName() + " vo) {\n");
        sb.append("        " + tableInfo.getEntityName() + " entity = " + tableInfo.getConvertName() + ".INSTANCE.convert(vo);\n\n");
        sb.append("        " + tableInfo.getMapperName().substring(0, 1).toLowerCase() + tableInfo.getMapperName().substring(1) + ".insert(entity);\n");
        sb.append("        return entity.getId();\n");
        sb.append("    }\n\n");
        sb.append("    @Override\n");
        sb.append("    public void update(" + tableInfo.getVoName() + " vo) {\n");
        sb.append("        " + tableInfo.getEntityName() + " entity = " + tableInfo.getConvertName() + ".INSTANCE.convert(vo);\n");
        sb.append("        " + tableInfo.getMapperName().substring(0, 1).toLowerCase() + tableInfo.getMapperName().substring(1) + ".updateById(entity);\n");
        sb.append("    }\n\n");
        sb.append("    @Override\n");
        sb.append("    public void delete(List<Long> idList) {\n");
        sb.append("        idList.forEach(id -> {\n");
        sb.append("            " + tableInfo.getEntityName() + " param = new " + tableInfo.getEntityName() + "();\n");
        sb.append("            param.setId(id);\n");
        sb.append("            param.setDbStatus(0);\n");
        sb.append("            " + tableInfo.getMapperName().substring(0, 1).toLowerCase() + tableInfo.getMapperName().substring(1) + ".updateById(param);\n");
        sb.append("        });\n");
        sb.append("    }\n\n");
        sb.append("}\n");
        return sb.toString();
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
        sb.append("import " + GeneratorConfig.QUERY_PACKAGE + "." + tableInfo.getQueryName() + ";\n");
        sb.append("import " + GeneratorConfig.SERVICE_PACKAGE + "." + tableInfo.getServiceName() + ";\n");
        sb.append("import " + GeneratorConfig.VO_PACKAGE + "." + tableInfo.getVoName() + ";\n");
        sb.append("import jakarta.validation.Valid;\n");
        sb.append("import org.springframework.security.access.prepost.PreAuthorize;\n");
        sb.append("import org.springframework.web.bind.annotation.*;\n\n");
        sb.append("import java.util.List;\n\n");
        sb.append("/**\n");
        sb.append(" * " + tableInfo.getTableComment() + "\n");
        sb.append(" *\n");
        sb.append(" * @author " + GeneratorConfig.AUTHOR + "\n");
        sb.append(" * \n");
        sb.append(" */\n");
        sb.append("@RestController\n");
        sb.append("@RequestMapping(\"sys/" + tableInfo.getTableName().replace("sys_", "") + ")\n");
        sb.append("public class " + tableInfo.getControllerName() + " {\n");
        sb.append("    private final " + tableInfo.getServiceName() + " " + tableInfo.getServiceName().substring(0, 1).toLowerCase() + tableInfo.getServiceName().substring(1) + ";\n\n");
        sb.append("    public " + tableInfo.getControllerName() + "(" + tableInfo.getServiceName() + " " + tableInfo.getServiceName().substring(0, 1).toLowerCase() + tableInfo.getServiceName().substring(1) + ") {\n");
        sb.append("        this." + tableInfo.getServiceName().substring(0, 1).toLowerCase() + tableInfo.getServiceName().substring(1) + " = " + tableInfo.getServiceName().substring(0, 1).toLowerCase() + tableInfo.getServiceName().substring(1) + ";\n");
        sb.append("    }\n\n");
        sb.append("    /**\n");
        sb.append("     * 分页查询\n");
        sb.append("     * @param query 查询条件\n");
        sb.append("     * @return 列表\n");
        sb.append("     */\n");
        sb.append("    @GetMapping(\"page\")\n");
        sb.append("    @PreAuthorize(\"hasAuthority('sys:" + tableInfo.getTableName().replace("sys_", "") + ":page')\")\n");
        sb.append("    public Result<PageResult<" + tableInfo.getVoName() + ">> page(@Valid " + tableInfo.getQueryName() + " query) {\n");
        sb.append("        PageResult<" + tableInfo.getVoName() + "> page = " + tableInfo.getServiceName().substring(0, 1).toLowerCase() + tableInfo.getServiceName().substring(1) + ".page(query);\n\n");
        sb.append("        return Result.ok(page);\n");
        sb.append("    }\n\n");
        sb.append("    /**\n");
        sb.append("     * 保存\n");
        sb.append("     * @param vo " + tableInfo.getTableComment() + "信息\n");
        sb.append("     * @return 提示信息\n");
        sb.append("     */\n");
        sb.append("    @PostMapping\n");
        sb.append("    @Log(module = \"" + tableInfo.getTableComment() + "\", name = \"保存\", type = OperateTypeEnum.INSERT)\n");
        sb.append("    @PreAuthorize(\"hasAuthority('sys:" + tableInfo.getTableName().replace("sys_", "") + ":save')\")\n");
        sb.append("    public Result<String> save(@RequestBody " + tableInfo.getVoName() + " vo) {\n");
        sb.append("        Long id = " + tableInfo.getServiceName().substring(0, 1).toLowerCase() + tableInfo.getServiceName().substring(1) + ".save(vo);\n");
        sb.append("        return Result.ok(String.valueOf(id));\n");
        sb.append("    }\n\n");
        sb.append("    /**\n");
        sb.append("     * 修改\n");
        sb.append("     * @param vo " + tableInfo.getTableComment() + "信息\n");
        sb.append("     * @return 提示信息\n");
        sb.append("     */\n");
        sb.append("    @PutMapping\n");
        sb.append("    @Log(module = \"" + tableInfo.getTableComment() + "\", name = \"修改\", type = OperateTypeEnum.UPDATE)\n");
        sb.append("    @PreAuthorize(\"hasAuthority('sys:" + tableInfo.getTableName().replace("sys_", "") + ":update')\")\n");
        sb.append("    public Result<String> update(@RequestBody " + tableInfo.getVoName() + " vo) {\n");
        sb.append("        " + tableInfo.getServiceName().substring(0, 1).toLowerCase() + tableInfo.getServiceName().substring(1) + ".update(vo);\n");
        sb.append("        return Result.ok();\n");
        sb.append("    }\n\n");
        sb.append("    /**\n");
        sb.append("     * 删除\n");
        sb.append("     * @param idList " + tableInfo.getTableComment() + "ID集合\n");
        sb.append("     * @return 提示信息\n");
        sb.append("     */\n");
        sb.append("    @PostMapping(\"/del\")\n");
        sb.append("    @Log(module = \"" + tableInfo.getTableComment() + "\", name = \"删除\", type = OperateTypeEnum.DELETE)\n");
        sb.append("    @PreAuthorize(\"hasAuthority('sys:" + tableInfo.getTableName().replace("sys_", "") + ":delete')\")\n");
        sb.append("    public Result<String> delete(@RequestBody List<Long> idList) {\n");
        sb.append("        " + tableInfo.getServiceName().substring(0, 1).toLowerCase() + tableInfo.getServiceName().substring(1) + ".delete(idList);\n\n");
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
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
        sb.append("<mapper namespace=\"" + GeneratorConfig.MAPPER_PACKAGE + "." + tableInfo.getMapperName() + "\">");
        sb.append("    <resultMap id=\"BaseResultMap\" type=\"" + GeneratorConfig.ENTITY_PACKAGE + "." + tableInfo.getEntityName() + "\">");
        sb.append("        <id property=\"id\" column=\"id\" />");
        for (ColumnInfo column : tableInfo.getColumns()) {
            if (!column.getColumnName().equals("id")) {
                sb.append("        <result property=\"").append(column.getJavaFieldName()).append("\" column=\"").append(column.getColumnName()).append("\" />");
            }
        }
        sb.append("    </resultMap>");
        sb.append("    <sql id=\"Base_Column_List\">");
        sb.append("        id");
        for (ColumnInfo column : tableInfo.getColumns()) {
            if (!column.getColumnName().equals("id")) {
                sb.append(", " + column.getColumnName());
            }
        }
        sb.append("    </sql>");
        sb.append("    <select id=\"getList\" resultMap=\"BaseResultMap\">");
        sb.append("        select");
        sb.append("        <include refid=\"Base_Column_List\" />");
        sb.append("        from " + tableInfo.getTableName());
        sb.append("        where db_status = 1");
        for (ColumnInfo column : tableInfo.getColumns()) {
            if (!column.getColumnName().equals("id") && !column.getColumnName().equals("tenant_id") && !column.getColumnName().equals("create_time") && !column.getColumnName().equals("update_time") && !column.getColumnName().equals("db_status")) {
                sb.append("        <if test=\"" + column.getJavaFieldName() + " != null");
                if (column.getJavaType().equals("String")) {
                    sb.append(" and " + column.getJavaFieldName() + " != ''");
                }
                sb.append("\">");
                sb.append("            and " + column.getColumnName() + " = #{");
                sb.append(column.getJavaFieldName());
                sb.append("}");
                sb.append("        </if>");
            }
        }
        sb.append("        order by create_time desc");
        sb.append("    </select>");
        sb.append("    <insert id=\"insert\" parameterType=\"" + GeneratorConfig.ENTITY_PACKAGE + "." + tableInfo.getEntityName() + "\">");
        sb.append("        insert into " + tableInfo.getTableName());
        sb.append("        <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">");
        for (ColumnInfo column : tableInfo.getColumns()) {
            if (!column.getColumnName().equals("id") && !column.getColumnName().equals("create_time") && !column.getColumnName().equals("update_time") && !column.getColumnName().equals("db_status")) {
                sb.append("            <if test=\"" + column.getJavaFieldName() + " != null\">" + column.getColumnName() + ",</if>");
            }
        }
        sb.append("            create_time,");
        sb.append("            update_time,");
        sb.append("            db_status");
        sb.append("        </trim>");
        sb.append("        <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">");
        for (ColumnInfo column : tableInfo.getColumns()) {
            if (!column.getColumnName().equals("id") && !column.getColumnName().equals("create_time") && !column.getColumnName().equals("update_time") && !column.getColumnName().equals("db_status")) {
                sb.append("            <if test=\"" + column.getJavaFieldName() + " != null\">#{");
                sb.append(column.getJavaFieldName());
                sb.append("},</if>");
            }
        }
        sb.append("            now(),");
        sb.append("            now(),");
        sb.append("            1");
        sb.append("        </trim>");
        sb.append("    </insert>");
        sb.append("    <update id=\"updateById\" parameterType=\"" + GeneratorConfig.ENTITY_PACKAGE + "." + tableInfo.getEntityName() + "\">");
        sb.append("        update " + tableInfo.getTableName());
        sb.append("        <set>");
        for (ColumnInfo column : tableInfo.getColumns()) {
            if (!column.getColumnName().equals("id") && !column.getColumnName().equals("create_time") && !column.getColumnName().equals("update_time")) {
                sb.append("            <if test=\"" + column.getJavaFieldName() + " != null\">" + column.getColumnName() + " = #{");
                sb.append(column.getJavaFieldName());
                sb.append("},</if>");
            }
        }
        sb.append("            update_time = now()");
        sb.append("        </set>");
        sb.append("        where id = #{id}");
        sb.append("    </update>");
        sb.append("</mapper>");
        return sb.toString();
    }
}