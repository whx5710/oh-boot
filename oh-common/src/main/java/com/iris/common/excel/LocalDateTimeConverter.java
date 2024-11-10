package com.iris.common.excel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.iris.core.utils.DateUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalDateTime 日期格式化
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public class LocalDateTimeConverter implements Converter<LocalDateTime> {
    @Override
    public Class<LocalDateTime> supportJavaTypeKey() {
        return LocalDateTime.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDateTime convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        return LocalDateTime.parse(cellData.getStringValue(), DateTimeFormatter.ofPattern(DateUtils.DATE_TIME_PATTERN));
    }

    @Override
    public WriteCellData<LocalDateTime> convertToExcelData(LocalDateTime value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        return new WriteCellData<>(DateUtils.format(value));
    }
}
