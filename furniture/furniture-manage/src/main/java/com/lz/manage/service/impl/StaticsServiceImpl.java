package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.common.annotation.DataScope;
import com.lz.manage.mapper.StaticsMapper;
import com.lz.manage.model.domain.ClientDealInfo;
import com.lz.manage.model.domain.ClientDemandInfo;
import com.lz.manage.model.domain.ClientInfo;
import com.lz.manage.model.domain.TaskInfo;
import com.lz.manage.model.dto.statics.StaticsBaseDto;
import com.lz.manage.model.enums.TaskStatusEnum;
import com.lz.manage.model.vo.statics.StaticCountPriceVo;
import com.lz.manage.model.vo.statics.StaticCountVo;
import com.lz.manage.model.vo.statics.StaticsBaseVo;
import com.lz.manage.model.vo.statics.StaticsPieVo;
import com.lz.manage.service.IClientDemandInfoService;
import com.lz.manage.service.IClientInfoService;
import com.lz.manage.service.IStaticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: furniture
 * Package: com.lz.manage.service.impl
 * Author: YY
 * CreateTime: 2025-03-13  09:16
 * Description: StaticsServiceImpl
 * Version: 1.0
 */
@Service
public class StaticsServiceImpl implements IStaticsService {

    @Resource
    private IClientInfoService clientInfoService;

    @Resource
    private IClientDemandInfoService clientDemandInfoService;

    @Resource
    private StaticsMapper staticsMapper;

    @Override
    public StaticCountVo getClientCount(ClientInfo clientInfo) {
        long count = clientInfoService.count(new QueryWrapper<ClientInfo>(clientInfo));
        StaticCountVo staticCountVo = new StaticCountVo();
        if (count > 0) {
            staticCountVo.setCount(count);
        } else {
            staticCountVo.setCount(0L);
        }
        return staticCountVo;
    }

    @DataScope(userAlias = "tb_client_demand_info", deptAlias = "tb_client_demand_info")
    @Override
    public StaticCountVo getDemandCount(ClientDemandInfo clientDemandInfo) {
        return staticsMapper.getDemandCount(clientDemandInfo);
    }

    @DataScope(userAlias = "tb_task_info", deptAlias = "tb_task_info")
    @Override
    public StaticCountVo getTaskCount(TaskInfo taskInfo) {
        return staticsMapper.getTaskCount(taskInfo);
    }

    @Override
    public StaticCountPriceVo getDealCount(ClientDealInfo clientDealInfo) {
        return staticsMapper.getDealCount(clientDealInfo);
    }

    @DataScope(userAlias = "tb_task_info", deptAlias = "tb_task_info")
    @Override
    public List<StaticsBaseVo<Long>> getTaskByDay(TaskInfo taskInfo) {
        List<StaticsBaseVo<Long>> list = staticsMapper.getTaskByDay(taskInfo);
        for (StaticsBaseVo<Long> vo : list) {
            TaskStatusEnum.getEnumByValue(vo.getName()).ifPresent(taskStatusEnum -> vo.setName(taskStatusEnum.getText()));
        }
        return list;
    }

    @Override
    public StaticsPieVo<BigDecimal> getDealPriceByDay(StaticsBaseDto staticsBaseDto) {
        List<StaticsBaseVo<BigDecimal>> list = staticsMapper.getDealPriceByDay(staticsBaseDto);
        StaticsPieVo<BigDecimal> pieVo = new StaticsPieVo<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<BigDecimal> values = new ArrayList<>();

        for (StaticsBaseVo<BigDecimal> vo : list) {
            names.add(vo.getName());
            values.add(vo.getValue());
        }
        pieVo.setValues(values);
        pieVo.setNames(names);
        return pieVo;
    }
}
