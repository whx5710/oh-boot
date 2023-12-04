package com.iris.generator.service;

import java.util.zip.ZipOutputStream;

/**
 * 代码生成
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface GeneratorService {

    void downloadCode(Long tableId, ZipOutputStream zip);

    void generatorCode(Long tableId);
}
