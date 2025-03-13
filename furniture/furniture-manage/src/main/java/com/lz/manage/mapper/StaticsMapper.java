package com.lz.manage.mapper;

import com.lz.manage.model.domain.ClientDealInfo;
import com.lz.manage.model.domain.ClientDemandInfo;
import com.lz.manage.model.domain.TaskInfo;
import com.lz.manage.model.vo.statics.StaticCountPriceVo;
import com.lz.manage.model.vo.statics.StaticCountVo;

/**
 * Project: furniture
 * Package: com.lz.manage.mapper
 * Author: YY
 * CreateTime: 2025-03-13  09:17
 * Description: StaticsMapper
 * Version: 1.0
 */
public interface StaticsMapper {
    StaticCountVo getDemandCount(ClientDemandInfo clientDemandInfo);

    StaticCountVo getTaskCount(TaskInfo taskInfo);

    StaticCountPriceVo getDealCount(ClientDealInfo clientDealInfo);
}
