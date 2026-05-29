package com.finn.system.controller;

import com.finn.framework.aop.annotations.Log;
import com.finn.framework.common.enums.OperateTypeEnum;
import com.finn.framework.entity.Result;
import com.finn.system.config.StorageProperties;
import com.finn.system.service.AttachmentService;
import com.finn.system.vo.AttachmentVO;
import com.finn.system.vo.FileUploadVO;
import com.finn.system.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@RestController
@RequestMapping("sys/file")
public class FileUploadController {

    private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);

    /**
     * 默认最大文件大小：100MB
     */
    private static final long MAX_FILE_SIZE = 100 * 1024 * 1024;

    private final StorageService storageService;

    private final AttachmentService attachmentService;

    private final StorageProperties storageProperties;

    public FileUploadController(StorageService storageService, AttachmentService attachmentService,
                                StorageProperties storageProperties) {
        this.storageService = storageService;
        this.attachmentService = attachmentService;
        this.storageProperties = storageProperties;
    }

    /**
     * 上传（使用流式上传，支持大文件）
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/upload")
    @Log(module = "文件上传", name = "上传", type = OperateTypeEnum.INSERT)
    public Result<FileUploadVO> upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return Result.error("请选择需要上传的文件");
        }

        // 检查文件大小
        if (file.getSize() > MAX_FILE_SIZE) {
            return Result.error("文件大小超过限制，最大支持 " + (MAX_FILE_SIZE / 1024 / 1024) + "MB");
        }

        // 上传路径
        String path = storageService.getPath(file.getOriginalFilename());

        // 使用流式上传，避免大文件读取到内存
        String url;
        try (var inputStream = file.getInputStream()) {
            url = storageService.upload(inputStream, path);
        } catch (Exception e) {
            log.error("文件上传失败", e);
            return Result.error("文件上传失败：" + e.getMessage());
        }

        FileUploadVO vo = new FileUploadVO();
        vo.setUrl(url);
        vo.setSize(file.getSize());
        vo.setName(file.getOriginalFilename());
        vo.setPlatform(storageProperties.getConfig().getType().name());
        // 保存文件信息
        AttachmentVO attachmentVO = new AttachmentVO();
        attachmentVO.setName(vo.getName());
        attachmentVO.setUrl(vo.getUrl());
        attachmentVO.setSize(vo.getSize());
        attachmentVO.setPlatform(vo.getPlatform());
        Long id = attachmentService.save(attachmentVO);
        vo.setId(id);
        return Result.ok(vo);
    }
}
