package com.finn.urban.service;

import com.finn.urban.entity.SportCheckin;
import com.finn.urban.vo.SportCheckinVO;
import com.finn.urban.vo.SportRecordVO;

import java.util.List;

/**
 * 拍照打卡（微信小程序端）
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-19
 */
public interface SportCheckinService {

    /**
     * 保存运动打卡点（运动中途实时新增），返回后端生成的 id
     */
    Long saveCheckin(SportCheckinVO vo);

    /**
     * 根据groupId获取列表
     * @param groupId
     * @return
     */
    List<SportCheckin> listByGroupId(Long groupId);

    /**
     * 根据groupId获取打卡VO列表（photos 已解析为文件 key 列表）
     * @param groupId
     * @return
     */
    List<SportCheckinVO> listCheckinsByGroupId(Long groupId);

    /**
     * 按 clientId 去重插入打卡点；clientId 为空则直接插入
     */
    void upsertCheckin(SportCheckinVO vo);

    /**
     * 填充打卡点数量（列表页用）
     */
    void fillCheckinCount(List<SportRecordVO> vos);
}
