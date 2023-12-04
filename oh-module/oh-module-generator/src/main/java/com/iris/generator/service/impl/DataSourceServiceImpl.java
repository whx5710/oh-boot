package com.iris.generator.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import com.iris.generator.common.page.PageResult;
import com.iris.generator.common.query.Query;
import com.iris.generator.common.service.impl.BaseServiceImpl;
import com.iris.generator.config.DbType;
import com.iris.generator.config.GenDataSource;
import com.iris.generator.dao.DataSourceDao;
import com.iris.generator.entity.DataSourceEntity;
import com.iris.generator.service.DataSourceService;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;


/**
 * 数据源管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Service
public class DataSourceServiceImpl extends BaseServiceImpl<DataSourceDao, DataSourceEntity> implements DataSourceService {
    @Resource
    private DataSource dataSource;

    @Override
    public PageResult<DataSourceEntity> page(Query query) {
        IPage<DataSourceEntity> page = baseMapper.selectPage(
                getPage(query),
                getWrapper(query)
        );
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    @Override
    public List<DataSourceEntity> getList() {
        return baseMapper.selectList(Wrappers.emptyWrapper());
    }

    @Override
    public String getDatabaseProductName(Long dataSourceId) {
        if (dataSourceId.intValue() == 0) {
            return DbType.MySQL.name();
        } else {
            return getById(dataSourceId).getDbType();
        }
    }

    @Override
    public GenDataSource get(Long datasourceId) {
        // 初始化配置信息
        GenDataSource info = null;
        if (datasourceId.intValue() == 0) {
            try {
                info = new GenDataSource(dataSource.getConnection());
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
            }
        } else {
            info = new GenDataSource(this.getById(datasourceId));
        }

        return info;
    }

    @Override
    public boolean save(DataSourceEntity entity) {
        entity.setCreateTime(LocalDateTime.now());
        return super.save(entity);
    }
}