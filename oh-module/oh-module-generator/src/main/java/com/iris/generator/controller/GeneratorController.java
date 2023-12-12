package com.iris.generator.controller;

import cn.hutool.core.io.IoUtil;
import com.iris.generator.common.utils.Result;
import com.iris.generator.service.GeneratorService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Controller
@RequestMapping("oh-generator/gen/generator")
public class GeneratorController {

    @Resource
    private GeneratorService generatorService;

    /**
     * 生成代码（zip压缩包）
     */
    @GetMapping("download")
    public void download(String tableIds, HttpServletResponse response) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        // 生成代码
        for (String tableId : tableIds.split(",")) {
            generatorService.downloadCode(Long.parseLong(tableId), zip);
        }

        IoUtil.close(zip);

        // zip压缩包数据
        byte[] data = outputStream.toByteArray();

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"oh.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IoUtil.write(response.getOutputStream(), false, data);
    }

    /**
     * 生成代码（自定义目录）
     */
    @ResponseBody
    @PostMapping("code")
    public Result<String> code(@RequestBody Long[] tableIds) throws Exception {
        // 生成代码
        for (Long tableId : tableIds) {
            generatorService.generatorCode(tableId);
        }

        return Result.ok();
    }

}