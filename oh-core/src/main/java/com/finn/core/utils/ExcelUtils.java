package com.finn.core.utils;

import cn.hutool.core.util.URLUtil;
import cn.idev.excel.EasyExcel;
import cn.idev.excel.converters.longconverter.LongStringConverter;
import cn.idev.excel.support.ExcelTypeEnum;
import cn.idev.excel.util.StringUtils;
import com.finn.core.excel.ExcelDataListener;
import com.finn.core.excel.ExcelFinishCallBack;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 导入导出工具类
 * @author 王小费 whx5710@qq.com
 */
public class ExcelUtils {

    private static final Logger log = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * 读取excel文件
     *
     * @param <T>      数据类型
     * @param file     excel文件
     * @param head     列名
     * @param callBack 回调 导入时传入定义好的回调接口，excel数据解析完毕之后监听器将数据传入回调函数
     *                 这样调用工具类时可以通过回调函数获取导入的数据，如果数据量过大可根据实际情况进行分配入库
     */
    public static <T> void readAnalysis(MultipartFile file, Class<T> head, ExcelFinishCallBack<T> callBack) {
        try {
            EasyExcel.read(file.getInputStream(), head, new ExcelDataListener<>(callBack)).sheet().doRead();
        } catch (IOException e) {
            log.error("readAnalysis error", e);
        }
    }

    /**
     * 读取excel文件
     *
     * @param <T>      数据类型
     * @param file     excel文件
     * @param head     列名
     * @param callBack 回调 导入时传入定义好的回调接口，excel数据解析完毕之后监听器将数据传入回调函数
     *                 这样调用工具类时可以通过回调函数获取导入的数据，如果数据量过大可根据实际情况进行分配入库
     */
    public static <T> void readAnalysis(File file, Class<T> head, ExcelFinishCallBack<T> callBack) {
        try {
            EasyExcel.read(new FileInputStream(file), head, null).sheet().doRead();
        } catch (IOException e) {
            log.error("readAnalysis error", e);
        }
    }

    /**
     * 读取excel文件 同步
     *
     * @param <T>   数据类型
     * @param file  文件
     * @param clazz 模板类
     * @return java.util.List
     */
    public static <T> List<T> readSync(File file, Class<T> clazz) {
        return readSync(file, clazz, 1, 0, ExcelTypeEnum.XLSX);
    }

    /**
     * 读取excel文件 同步
     *
     * @param <T>       数据类型
     * @param file      文件
     * @param clazz     模板类
     * @param rowNum    数据开始行 1
     * @param sheetNo   第几张表
     * @param excelType 数据表格式类型
     * @return java.util.List list
     */
    public static <T> List<T> readSync(File file, Class<T> clazz, Integer rowNum, Integer sheetNo, ExcelTypeEnum excelType) {
        return EasyExcel.read(file).headRowNumber(rowNum).excelType(excelType).head(clazz).sheet(sheetNo).doReadSync();
    }


    /**
     * 导出数据到文件
     *
     * @param <T>  数据类型
     * @param head 类名
     * @param file 导入到文件
     * @param data 数据
     */
    public static <T> void excelExport(Class<T> head, File file, List<T> data) {
        excelExport(head, file, "sheet1", data);
    }

    /**
     * 导出数据到文件
     *
     * @param <T>       写入格式
     * @param head      类名
     * @param file      写入到文件
     * @param sheetName sheet名称
     * @param data      数据列表
     */
    public static <T> void excelExport(Class<T> head, File file, String sheetName, List<T> data) {
        try {
            EasyExcel.write(file, head).sheet(sheetName).registerConverter(new LongStringConverter()).doWrite(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 导出数据到web
     * 文件下载（失败了会返回一个有部分数据的Excel）
     *
     * @param head      类名
     * @param excelName excel名字
     * @param sheetName sheet名称
     * @param data      数据
     */
    public static <T> void excelExport(Class<T> head, String excelName, String sheetName, List<T> data) {
        try {
            HttpServletResponse response = getExportResponse(excelName);
            EasyExcel.write(response.getOutputStream(), head).sheet(StringUtils.isBlank(sheetName) ? "sheet1" : sheetName)
                    .registerConverter(new LongStringConverter()).doWrite(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 导出数据到web
     * 文件下载（失败了会返回一个有部分数据的Excel）
     *
     * @param head      类名
     * @param excelName excel名字
     * @param sheetName sheet名称
     * @param data      数据
     */
    public static <T> void excelExport(List<List<String>> head, String excelName, String sheetName, List<T> data) {
        try {
            HttpServletResponse response = getExportResponse(excelName);
            EasyExcel.write(response.getOutputStream()).head(head).sheet(StringUtils.isBlank(sheetName) ? "sheet1" : sheetName)
                    .registerConverter(new LongStringConverter()).doWrite(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static HttpServletResponse getExportResponse(String excelName) {
        HttpServletResponse response = HttpContextUtils.getHttpServletResponse();
        AssertUtils.isNull(response, "响应");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setCharacterEncoding("UTF-8");

        excelName += DateUtils.format(LocalDateTime.now(), "yyyy-MM-dd HHmmss");
        String fileName = URLUtil.encode(excelName, StandardCharsets.UTF_8);
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        return response;
    }

}
