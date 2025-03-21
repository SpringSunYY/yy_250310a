package com.lz.manage.controller;

import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.manage.model.domain.ClientDealInfo;
import com.lz.manage.model.domain.ClientDemandInfo;
import com.lz.manage.model.domain.ClientInfo;
import com.lz.manage.model.domain.TaskInfo;
import com.lz.manage.model.dto.clientDealInfo.ClientDealInfoQuery;
import com.lz.manage.model.dto.clientDemandInfo.ClientDemandInfoQuery;
import com.lz.manage.model.dto.clientInfo.ClientInfoQuery;
import com.lz.manage.model.dto.statics.StaticsBaseDto;
import com.lz.manage.model.dto.taskInfo.TaskInfoQuery;
import com.lz.manage.service.IStaticsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Project: furniture
 * Package: com.lz.manage.controller
 * Author: YY
 * CreateTime: 2025-03-13  09:15
 * Description: StaticsController
 * Version: 1.0
 */
@RestController
@RequestMapping("/manage/statics")
public class StaticsController extends BaseController {
    @Resource
    private IStaticsService staticsService;

    @PreAuthorize("@ss.hasPermi('manage:statics:statics')")
    @GetMapping("/getClientCount")
    public AjaxResult getClientCount(ClientInfoQuery clientInfoQuery) {
        ClientInfo clientInfo = ClientInfoQuery.queryToObj(clientInfoQuery);
        return success(staticsService.getClientCount(clientInfo));
    }

    @PreAuthorize("@ss.hasPermi('manage:statics:statics')")
    @GetMapping("/getDemandCount")
    public AjaxResult getDemandCount(ClientDemandInfoQuery clientDemandInfoQuery) {
        ClientDemandInfo clientDemandInfo = ClientDemandInfoQuery.queryToObj(clientDemandInfoQuery);
        return success(staticsService.getDemandCount(clientDemandInfo));
    }

    @PreAuthorize("@ss.hasPermi('manage:statics:statics')")
    @GetMapping("/getTaskCount")
    public AjaxResult getDemandCount(TaskInfoQuery taskInfoQuery) {
        TaskInfo taskInfo = TaskInfoQuery.queryToObj(taskInfoQuery);
        return success(staticsService.getTaskCount(taskInfo));
    }

    @PreAuthorize("@ss.hasPermi('manage:statics:statics')")
    @GetMapping("/getDealCount")
    public AjaxResult getDemandCount(ClientDealInfoQuery clientDealInfoQuery) {
        ClientDealInfo clientDealInfo = ClientDealInfoQuery.queryToObj(clientDealInfoQuery);
        return success(staticsService.getDealCount(clientDealInfo));
    }

    @PreAuthorize("@ss.hasPermi('manage:statics:statics')")
    @GetMapping("/getTaskToday")
    public AjaxResult getTaskToday(TaskInfoQuery taskInfoQuery) {
        TaskInfo taskInfo = TaskInfoQuery.queryToObj(taskInfoQuery);
        taskInfo.setCreateTime(new Date());
        return success(staticsService.getTaskByDay(taskInfo));
    }

    @PreAuthorize("@ss.hasPermi('manage:statics:statics')")
    @GetMapping("/getDealPriceByDay")
    public AjaxResult getDealPriceByDay(StaticsBaseDto staticsBaseDto) {
        staticsBaseDto.setEndTime(new Date());
        //开始时间七天前
        staticsBaseDto.setStartTime(new Date(new Date().getTime() - 7 * 24 * 60 * 60 * 1000));
        return success(staticsService.getDealPriceByDay(staticsBaseDto));
    }

    @PreAuthorize("@ss.hasPermi('manage:statics:statics')")
    @GetMapping("/getDealAndDemandByDay")
    public AjaxResult getDealAndDemandByDay(StaticsBaseDto staticsBaseDto) {
        staticsBaseDto.setEndTime(new Date());
        //开始时间七天前
        staticsBaseDto.setStartTime(new Date(new Date().getTime() - 7 * 24 * 60 * 60 * 1000));
        return success(staticsService.getDealAndDemandByDay(staticsBaseDto));
    }
}
