package com.iris.generator.controller;

import com.iris.generator.common.query.Query;
import com.iris.generator.common.utils.Result;
import com.iris.generator.entity.TableEntity;
import com.iris.generator.entity.TableFieldEntity;
import com.iris.generator.service.TableFieldService;
import com.iris.generator.service.TableService;
import jakarta.annotation.Resource;
import com.iris.generator.common.page.PageResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据表管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@RestController
@RequestMapping("oh-generator/gen/table")
public class TableController {

    @Resource
    private TableService tableService;
    @Resource
    private TableFieldService tableFieldService;

    /**
     * 分页
     *
     * @param query 查询参数
     */
    @GetMapping("page")
    public Result<PageResult<TableEntity>> page(Query query) {
        PageResult<TableEntity> page = tableService.page(query);

        return Result.ok(page);
    }

    /**
     * 获取表信息
     *
     * @param id 表ID
     */
    @GetMapping("{id}")
    public Result<TableEntity> get(@PathVariable("id") Long id) {
        TableEntity table = tableService.getById(id);

        // 获取表的字段
        List<TableFieldEntity> fieldList = tableFieldService.getByTableId(table.getId());
        table.setFieldList(fieldList);

        return Result.ok(table);
    }

    /**
     * 修改
     *
     * @param table 表信息
     */
    @PutMapping
    public Result<String> update(@RequestBody TableEntity table) {
        tableService.updateById(table);

        return Result.ok();
    }

    /**
     * 删除
     *
     * @param ids 表id数组
     */
    @DeleteMapping
    public Result<String> delete(@RequestBody Long[] ids) {
        tableService.deleteBatchIds(ids);

        return Result.ok();
    }

    /**
     * 同步表结构
     *
     * @param id 表ID
     */
    @PostMapping("sync/{id}")
    public Result<String> sync(@PathVariable("id") Long id) {
        tableService.sync(id);

        return Result.ok();
    }

    /**
     * 导入数据源中的表
     *
     * @param datasourceId  数据源ID
     * @param tableNameList 表名列表
     */
    @PostMapping("import/{datasourceId}")
    public Result<String> tableImport(@PathVariable("datasourceId") Long datasourceId, @RequestBody List<String> tableNameList) {
        for (String tableName : tableNameList) {
            tableService.tableImport(datasourceId, tableName);
        }

        return Result.ok();
    }

    /**
     * 修改表字段数据
     *
     * @param tableId        表ID
     * @param tableFieldList 字段列表
     */
    @PutMapping("field/{tableId}")
    public Result<String> updateTableField(@PathVariable("tableId") Long tableId, @RequestBody List<TableFieldEntity> tableFieldList) {
        tableFieldService.updateTableField(tableId, tableFieldList);

        return Result.ok();
    }

}
