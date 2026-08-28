package com.finn.urban.service.impl;

import com.finn.common.utils.AssertUtils;
import com.finn.common.utils.JsonUtils;
import com.finn.framework.datasource.wrapper.CountWrapper;
import com.finn.framework.datasource.wrapper.QueryWrapper;
import com.finn.framework.exception.ServerException;
import com.finn.framework.security.user.SecurityUser;
import com.finn.urban.convert.SportCheckinConvert;
import com.finn.urban.entity.SportCheckin;
import com.finn.urban.mapper.SportCheckinMapper;
import com.finn.urban.service.SportCheckinService;
import com.finn.urban.vo.SportCheckinVO;
import com.finn.urban.vo.SportRecordVO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.finn.common.constant.Constant.DESC;

/**
 * 拍照打卡（微信小程序端）
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-19
 */
@Service
public class SportCheckinServiceImpl implements SportCheckinService {

    private final SportCheckinMapper sportCheckinMapper;

    public SportCheckinServiceImpl(SportCheckinMapper sportCheckinMapper){
        this.sportCheckinMapper = sportCheckinMapper;
    }


    @Override
    public Long saveCheckin(SportCheckinVO vo) {
        AssertUtils.isNull(vo.getGroupId(), "运动记录ID");
        // 本次打卡次数获取
        Long groupId = vo.getGroupId();
        CountWrapper<SportCheckin> countWrapper = CountWrapper.of(SportCheckin.class);
        countWrapper.eq(SportCheckin::getDbStatus, 1)
                .eq(SportCheckin::getGroupId, groupId);
        long l = sportCheckinMapper.count(countWrapper);
        if(l > 100){
            throw new ServerException("本次运动打卡次数已达100次");
        }
        SportCheckin entity = SportCheckinConvert.INSTANCE.convert(vo);
        // entity.setPhotos(toJson(vo.getPhotos()));
        entity.setPhotos(JsonUtils.toJsonString(vo.getPhotos()));
        LocalDateTime now = LocalDateTime.now();
        entity.setDbStatus(1);
        entity.setCreator(SecurityUser.getUserId());
        entity.setCreateTime(now);
        if(entity.getCreatedAt() == null){
            entity.setCreatedAt(System.currentTimeMillis());
        }
        sportCheckinMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public List<SportCheckin> listByGroupId(Long groupId) {
        QueryWrapper<SportCheckin> cqw = QueryWrapper.of(SportCheckin.class);
        cqw.eq(SportCheckin::getGroupId, groupId)
                .eq(SportCheckin::getDbStatus, 1)
                .orderBy(SportCheckin::getCreatedAt, DESC);
        return sportCheckinMapper.listByWrapper(cqw);
    }

    @Override
    public List<SportCheckinVO> listCheckinsByGroupId(Long groupId) {
        List<SportCheckin> checkins = listByGroupId(groupId);
        if (checkins == null || checkins.isEmpty()) {
            return new ArrayList<>();
        }
        List<SportCheckinVO> cvos = SportCheckinConvert.INSTANCE.convertList(checkins);
        for (int i = 0; i < checkins.size(); i++) {
            cvos.get(i).setPhotos(JsonUtils.parseArray(checkins.get(i).getPhotos(), String.class));
        }
        return cvos;
    }

    /**
     * 按 clientId 去重插入打卡点；clientId 为空则直接插入
     */
    @Override
    public void upsertCheckin(SportCheckinVO vo) {
        if (vo.getClientId() != null && !vo.getClientId().isEmpty()) {
            QueryWrapper<SportCheckin> qw = QueryWrapper.of(SportCheckin.class);
            qw.eq(SportCheckin::getGroupId, vo.getGroupId())
                    .eq(SportCheckin::getClientId, vo.getClientId())
                    .eq(SportCheckin::getDbStatus, 1);
            List<SportCheckin> exist = sportCheckinMapper.listByWrapper(qw);
            if (exist != null && !exist.isEmpty()) {
                return;
            }
        }
        saveCheckin(vo);
    }

    @Override
    public void fillCheckinCount(List<SportRecordVO> vos) {
        if (vos == null || vos.isEmpty()) {
            return;
        }
        List<Long> ids = new ArrayList<>();
        for (SportRecordVO v : vos) {
            if (v.getId() != null) {
                ids.add(v.getId());
            }
        }
        if (ids.isEmpty()) {
            return;
        }
        QueryWrapper<SportCheckin> qw = QueryWrapper.of(SportCheckin.class);
        qw.eq(SportCheckin::getDbStatus, 1).in(SportCheckin::getGroupId, ids)
                .orderBy(SportCheckin::getCreatedAt, DESC);
        List<SportCheckin> all = sportCheckinMapper.listByWrapper(qw);
        Map<Long, Integer> countMap = new HashMap<>();
        if (all != null) {
            for (SportCheckin c : all) {
                countMap.merge(c.getGroupId(), 1, Integer::sum);
            }
        }
        for (SportRecordVO v : vos) {
            v.setCheckinCount(countMap.getOrDefault(v.getId(), 0));
        }
    }
}
