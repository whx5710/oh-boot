package com.finn.sys.service;

import com.finn.core.utils.DateUtils;
import com.finn.sys.config.StorageProperties;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.Date;

/**
 * 存储服务
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public abstract class StorageService {

    public StorageProperties properties;

    /**
     * 根据文件名，生成带时间戳的新文件名
     *
     * @param fileName 文件名
     * @return 返回带时间戳的文件名
     */
    public String getNewFileName(String fileName) {
        // 把当天HH:mm:ss，转换成秒
        Date date = new Date();
        String today = DateUtils.format(date, DateUtils.DATE_PATTERN) + " 00:00:00";
        Date d = DateUtils.parse(today, DateUtils.DATE_TIME_PATTERN);
        long time = (date.getTime() - d.getTime()) / 1000;

        int i = fileName.lastIndexOf(".");
        if(i > 0){
            // 主文件名，不包含扩展名
            String prefix = fileName.substring(0, i);
            // 文件扩展名
            String suffix = fileName.substring(i);
            // 新文件名
            return prefix + "_" + time + "." + suffix;
        }else{
            return time + "_" + fileName;
        }
    }

    /**
     * 生成路径，不包含文件名
     *
     * @return 返回生成的路径
     */
    public String getPath() {
        // 文件路径
        String path = DateUtils.format(new Date(), "yyyyMMdd");
        // 如果有前缀，则也带上
        if (StringUtils.hasText(properties.getConfig().getPrefix())) {
            path = properties.getConfig().getPrefix() + "/" + path;
        }
        return path;
    }

    /**
     * 根据文件名，生成路径
     *
     * @param fileName 文件名
     * @return 生成文件路径
     */
    public String getPath(String fileName) {
        return getPath() + "/" + getNewFileName(fileName);
    }

    /**
     * 文件上传
     *
     * @param data 文件字节数组
     * @param path 文件路径，包含文件名
     * @return 返回http地址
     */
    public abstract String upload(byte[] data, String path);

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @param path        文件路径，包含文件名
     * @return 返回http地址
     */
    public abstract String upload(InputStream inputStream, String path);

}
