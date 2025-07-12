package com.finn.sys.base.controller;

import com.finn.framework.operatelog.annotations.Log;
import com.finn.framework.operatelog.enums.OperateTypeEnum;
import com.finn.core.utils.Result;
import com.finn.sys.base.service.AttachmentService;
import com.finn.sys.base.vo.AttachmentVO;
import com.finn.sys.base.vo.FileUploadVO;
import com.finn.sys.storage.service.StorageService;
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
    private final StorageService storageService;

    private final AttachmentService attachmentService;

    public FileUploadController(StorageService storageService, AttachmentService attachmentService) {
        this.storageService = storageService;
        this.attachmentService = attachmentService;
    }

    /**
     * 上传
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("upload")
    @Log(module = "文件上传", name = "上传", type = OperateTypeEnum.INSERT)
    public Result<FileUploadVO> upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return Result.error("请选择需要上传的文件");
        }

        // 上传路径
        String path = storageService.getPath(file.getOriginalFilename());
        // 上传文件
        String url = storageService.upload(file.getBytes(), path);

        FileUploadVO vo = new FileUploadVO();
        vo.setUrl(url);
        vo.setSize(file.getSize());
        vo.setName(file.getOriginalFilename());
        vo.setPlatform(storageService.properties.getConfig().getType().name());
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
