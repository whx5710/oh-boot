package com.finn.system.controller;

import com.finn.core.utils.Result;
import com.finn.system.generator.CodeGenerator;
import com.finn.system.generator.dto.TableInfo;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 代码生成器控制器
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@RestController
@RequestMapping("sys/code")
public class CodeGeneratorController {

    private final CodeGenerator codeGenerator;
    private final String basePath;

    public CodeGeneratorController(CodeGenerator codeGenerator) {
        this.codeGenerator = codeGenerator;
        this.basePath = System.getProperty("user.dir") + "/" + CodeGenerator.GeneratorConfig.MODULE_NAME;
    }

    /**
     * 生成代码
     * @param tableName 表名
     * @return 提示信息
     */
    @GetMapping("/generate/{tableName}")
    public Result<String> generate(@PathVariable String tableName) {
        try {
            TableInfo tableInfo = codeGenerator.getTableInfo(tableName);
            
            generateFile(basePath + CodeGenerator.GeneratorConfig.getEntityPath() + "/" + tableInfo.getEntityName() + ".java",
                    codeGenerator.generateEntity(tableInfo));
            
            generateFile(basePath + CodeGenerator.GeneratorConfig.getMapperPath() + "/" + tableInfo.getMapperName() + ".java",
                    codeGenerator.generateMapper(tableInfo));
            
            generateFile(basePath + CodeGenerator.GeneratorConfig.getQueryPath() + "/" + tableInfo.getQueryName() + ".java",
                    codeGenerator.generateQuery(tableInfo));
            
            generateFile(basePath + CodeGenerator.GeneratorConfig.getVoPath() + "/" + tableInfo.getVoName() + ".java",
                    codeGenerator.generateVO(tableInfo));
            
            generateFile(basePath + CodeGenerator.GeneratorConfig.getConvertPath() + "/" + tableInfo.getConvertName() + ".java",
                    codeGenerator.generateConvert(tableInfo));
            
            generateFile(basePath + CodeGenerator.GeneratorConfig.getServicePath() + "/" + tableInfo.getServiceName() + ".java",
                    codeGenerator.generateService(tableInfo));
            
            generateFile(basePath + CodeGenerator.GeneratorConfig.getServiceImplPath() + "/" + tableInfo.getServiceName() + "Impl.java",
                    codeGenerator.generateServiceImpl(tableInfo));
            
            generateFile(basePath + CodeGenerator.GeneratorConfig.getControllerPath() + "/" + tableInfo.getControllerName() + ".java",
                    codeGenerator.generateController(tableInfo));
            
            generateFile(basePath + CodeGenerator.GeneratorConfig.getMapperXmlPath() + tableInfo.getMapperName() + ".xml",
                    codeGenerator.generateMapperXml(tableInfo));
            
            return Result.ok("代码生成成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.error("数据库操作失败：" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("文件操作失败：" + e.getMessage());
        }
    }

    /**
     * 生成文件
     */
    private void generateFile(String filePath, String content) throws IOException {
        File file = new File(filePath);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
    }

}