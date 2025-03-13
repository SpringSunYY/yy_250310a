package com.lz.manage.service;

import com.lz.manage.model.domain.ClientDealInfo;
import com.lz.manage.model.domain.ClientDemandInfo;
import com.lz.manage.model.domain.ClientInfo;
import com.lz.manage.model.domain.TaskInfo;
import com.lz.manage.model.vo.statics.StaticCountPriceVo;
import com.lz.manage.model.vo.statics.StaticCountVo;

/**
 * Project: furniture
 * Package: com.lz.manage.service
 * Author: YY
 * CreateTime: 2025-03-13  09:15
 * Description: IStaticsService
 * Version: 1.0
 */
public interface IStaticsService {
    /**
     * description: 统计客户总数
     * author: YY
     * method: getClientCount
     * date: 2025/3/13 09:21
     * param:
     * return: com.lz.manage.model.vo.statics.StaticCountVo
     **/
    StaticCountVo getClientCount(ClientInfo clientInfo);

    /**
     * description: 统计需求总数
     * author: YY
     * method: getDemandCount
     * date: 2025/3/13 09:28
     * param:
     * param: clientDemandInfo
     * return: com.lz.manage.model.vo.statics.StaticCountVo
     **/
    StaticCountVo getDemandCount(ClientDemandInfo clientDemandInfo);

    /**
     * description: 统计任务总数
     * author: YY
     * method: getTaskCount
     * date: 2025/3/13 09:39
     * param:
     * param: taskInfo
     * return: com.lz.manage.model.vo.statics.StaticCountVo
     **/
    StaticCountVo getTaskCount(TaskInfo taskInfo);

    /**
     * description: 统计需求总数
     * author: YY
     * method: getDealCount
     * date: 2025/3/13 09:41
     * param:
     * param: clientDealInfo
     * return: com.lz.manage.model.vo.statics.StaticCountPriceVo
     **/
    StaticCountPriceVo getDealCount(ClientDealInfo clientDealInfo);
}
