package com.lz.manage.service;

import com.lz.manage.model.domain.ClientDealInfo;
import com.lz.manage.model.domain.ClientDemandInfo;
import com.lz.manage.model.domain.ClientInfo;
import com.lz.manage.model.domain.TaskInfo;
import com.lz.manage.model.dto.statics.StaticsBaseDto;
import com.lz.manage.model.vo.statics.*;

import java.math.BigDecimal;
import java.util.List;

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

    /**
     * description: 统计日的任务总数 每种类型
     * author: YY
     * method: getTaskToday
     * date: 2025/3/13 10:35
     * param:
     * param: taskInfo
     * return: com.lz.manage.model.vo.statics.StaticsBaseVo<java.lang.Long>
     **/
    List<StaticsBaseVo<Long>> getTaskByDay(TaskInfo taskInfo);

    /**
     * description: 获取需求每日价格
     * author: YY
     * method: getDealPriceByDay
     * date: 2025/3/13 10:55
     * param:
     * param: clientDealInfo
     * return: com.lz.manage.model.vo.statics.StaticsPieVo<java.math.BigDecimal>
     **/
    StaticsPieVo<BigDecimal> getDealPriceByDay(StaticsBaseDto staticsBaseDto);

    /**
     * description:  获取需求和成交价格折线图
     * author: YY
     * method: getDealAndDemandByDay
     * date: 2025/3/13 11:33
     * param:
     * param: staticsBaseDto
     * return: com.lz.manage.model.vo.statics.StaticsLineChartVo
     **/
    StaticsLineChartVo<Long> getDealAndDemandByDay(StaticsBaseDto staticsBaseDto);
}
