package com.finn.urban.convert;

import com.finn.common.utils.DateUtils;
import com.finn.urban.entity.SportRecordEntity;
import com.finn.urban.vo.SportRecordVO;

import java.util.ArrayList;
import java.util.List;

public class SportRecordExtConvert implements SportRecordConvert{

    private final SportRecordConvert sportRecordConvert;

    public SportRecordExtConvert(SportRecordConvert sportRecordConvert){
        this.sportRecordConvert = sportRecordConvert;
    }

    @Override
    public SportRecordEntity convert(SportRecordVO vo) {
        return sportRecordConvert.convert(vo);
    }

    @Override
    public SportRecordVO convert(SportRecordEntity entity) {
        SportRecordVO vo = new SportRecordVO();
        vo.setId(entity.getId());
        vo.setName(entity.getName());
        vo.setUserId(entity.getUserId());
        vo.setRecordDate(entity.getRecordDate());
        if(entity.getDuration() != null){
            vo.setDuration(DateUtils.formatDuration(entity.getDuration()));
        }
        vo.setRemark(entity.getRemark());
        vo.setStartTime(DateUtils.format(entity.getStartTime(), "yyyy年MM月dd日 HH:mm"));
        if(entity.getEndTime() != null){
            vo.setEndTime(DateUtils.format(entity.getEndTime(), "yyyy年MM月dd日 HH:mm"));
        }
        return vo;
    }

    @Override
    public List<SportRecordVO> convertList(List<SportRecordEntity> list) {
        if ( list == null ) {
            return null;
        }
        List<SportRecordVO> list1 = new ArrayList<>(list.size());
        for(SportRecordEntity sportRecord: list){
            list1.add(convert(sportRecord));
        }
        return list1;
    }
}
