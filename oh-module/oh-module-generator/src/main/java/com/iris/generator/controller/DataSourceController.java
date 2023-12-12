package com.iris.generator.controller;

import com.iris.generator.common.utils.Result;
import com.iris.generator.config.GenDataSource;
import com.iris.generator.entity.DataSourceEntity;
import com.iris.generator.entity.TableEntity;
import com.iris.generator.service.DataSourceService;
import com.iris.generator.utils.DbUtils;
import com.iris.generator.utils.GenUtils;
import jakarta.annotation.Resource;
import com.iris.generator.common.page.PageResult;
import com.iris.generator.common.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 数据源管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@RestController
@RequestMapping("oh-generator/gen/datasource")
public class DataSourceController {

    private static final Logger log = LoggerFactory.getLogger(DataSourceController.class);

    @Resource
    private DataSourceService datasourceService;

    @GetMapping("page")
    public Result<PageResult<DataSourceEntity>> page(Query query) {
        PageResult<DataSourceEntity> page = datasourceService.page(query);

        return Result.ok(page);
    }

    @GetMapping("list")
    public Result<List<DataSourceEntity>> list() {
        List<DataSourceEntity> list = datasourceService.getList();

        return Result.ok(list);
    }

    @GetMapping("{id}")
    public Result<DataSourceEntity> get(@PathVariable("id") Long id) {
        DataSourceEntity data = datasourceService.getById(id);

        return Result.ok(data);
    }

    @GetMapping("test/{id}")
    public Result<String> test(@PathVariable("id") Long id) {
        try {
            DataSourceEntity entity = datasourceService.getById(id);

            DbUtils.getConnection(new GenDataSource(entity));
            return Result.ok("连接成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error("连接失败，请检查配置信息");
        }
    }

    @PostMapping
    public Result<String> save(@RequestBody DataSourceEntity entity) {
        datasourceService.save(entity);

        return Result.ok();
    }

    @PutMapping
    public Result<String> update(@RequestBody DataSourceEntity entity) {
        datasourceService.updateById(entity);

        return Result.ok();
    }

    @DeleteMapping
    public Result<String> delete(@RequestBody Long[] ids) {
        datasourceService.removeBatchByIds(Arrays.asList(ids));

        return Result.ok();
    }

    /**
     * 根据数据源ID，获取全部数据表
     *
     * @param id 数据源ID
     */
    @GetMapping("table/list/{id}")
    public Result<List<TableEntity>> tableList(@PathVariable("id") Long id) {
        try {
            // 获取数据源
            GenDataSource datasource = datasourceService.get(id);
            // 根据数据源，获取全部数据表
            List<TableEntity> tableList = GenUtils.getTableList(datasource);

            return Result.ok(tableList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error("数据源配置错误，请检查数据源配置！");
        }
    }
}