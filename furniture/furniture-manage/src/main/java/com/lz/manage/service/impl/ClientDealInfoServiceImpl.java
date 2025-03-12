package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lz.common.core.domain.entity.SysDept;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;

import javax.annotation.Resource;

import com.lz.manage.model.domain.ClientDemandInfo;
import com.lz.manage.model.domain.ClientInfo;
import com.lz.manage.model.enums.DemandStatusEnum;
import com.lz.manage.model.enums.PayStatusEnum;
import com.lz.manage.service.IClientDemandInfoService;
import com.lz.manage.service.IClientInfoService;
import com.lz.system.service.ISysDeptService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.ClientDealInfoMapper;
import com.lz.manage.model.domain.ClientDealInfo;
import com.lz.manage.service.IClientDealInfoService;
import com.lz.manage.model.dto.clientDealInfo.ClientDealInfoQuery;
import com.lz.manage.model.vo.clientDealInfo.ClientDealInfoVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * 客户交易Service业务层处理
 *
 * @author YY
 * @date 2025-03-11
 */
@Service
public class ClientDealInfoServiceImpl extends ServiceImpl<ClientDealInfoMapper, ClientDealInfo> implements IClientDealInfoService {
    @Resource
    private ClientDealInfoMapper clientDealInfoMapper;

    @Resource
    private IClientInfoService clientInfoService;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysDeptService deptService;

    @Resource
    private IClientDemandInfoService demandInfoService;

    //region mybatis代码

    /**
     * 查询客户交易
     *
     * @param id 客户交易主键
     * @return 客户交易
     */
    @Override
    public ClientDealInfo selectClientDealInfoById(Long id) {
        return clientDealInfoMapper.selectClientDealInfoById(id);
    }

    /**
     * 查询客户交易列表
     *
     * @param clientDealInfo 客户交易
     * @return 客户交易
     */
    @Override
    public List<ClientDealInfo> selectClientDealInfoList(ClientDealInfo clientDealInfo) {
        List<ClientDealInfo> clientDealInfos = clientDealInfoMapper.selectClientDealInfoList(clientDealInfo);
        for (ClientDealInfo info : clientDealInfos) {
            SysUser user = userService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(user)) {
                info.setUserName(user.getUserName());
            }
            SysDept dept = deptService.selectDeptById(info.getDeptId());
            if (StringUtils.isNotNull(dept)) {
                info.setDeptName(dept.getDeptName());
            }
            ClientInfo clientInfo = clientInfoService.selectClientInfoById(info.getClientId());
            if (StringUtils.isNotNull(clientInfo)) {
                info.setClientName(clientInfo.getClientName());
            }
            ClientDemandInfo clientDemandInfo = demandInfoService.selectClientDemandInfoById(info.getDemandId());
            if (StringUtils.isNotNull(clientDemandInfo)) {
                info.setDemandName(clientDemandInfo.getDemandName());
            }
        }
        return clientDealInfos;
    }

    /**
     * 新增客户交易
     *
     * @param clientDealInfo 客户交易
     * @return 结果
     */
    @Transactional
    @Override
    public int insertClientDealInfo(ClientDealInfo clientDealInfo) {
        //一个需求只能一个交易
        ClientDealInfo old = this.getOne(new LambdaQueryWrapper<ClientDealInfo>().eq(ClientDealInfo::getDemandId, clientDealInfo.getDemandId()));
        if (StringUtils.isNotNull(old)) {
            throw new ServiceException("该需求已有交易！！！");
        }
        ClientDemandInfo clientDemandInfo = checkClientDeal(clientDealInfo);

        clientDemandInfo.setDemandStatus(DemandStatusEnum.DEMAND_STATUS_1.getValue());
        demandInfoService.updateById(clientDemandInfo);

        clientDealInfo.setCreateTime(DateUtils.getNowDate());
        return clientDealInfoMapper.insertClientDealInfo(clientDealInfo);
    }

    private ClientDemandInfo checkClientDeal(ClientDealInfo clientDealInfo) {
        ClientDemandInfo clientDemandInfo = demandInfoService.selectClientDemandInfoById(clientDealInfo.getDemandId());
        if (StringUtils.isNull(clientDemandInfo)) {
            throw new ServiceException("该需求不存在！！！");
        }
        if (clientDemandInfo.getDemandStatus().equals(DemandStatusEnum.DEMAND_STATUS_2.getValue())) {
            throw new ServiceException("该需求已完成，不能交易");
        }
        clientDealInfo.setUserId(clientDemandInfo.getUserId());
        clientDealInfo.setDeptId(clientDemandInfo.getDeptId());
        clientDealInfo.setClientId(clientDemandInfo.getClientId());
        return clientDemandInfo;
    }

    /**
     * 修改客户交易
     *
     * @param clientDealInfo 客户交易
     * @return 结果
     */
    @Override
    public int updateClientDealInfo(ClientDealInfo clientDealInfo) {
        ClientDealInfo old = clientDealInfoMapper.selectClientDealInfoById(clientDealInfo.getId());
        if (StringUtils.isNull(old)) {
            throw new ServiceException("该交易不存在！！！");
        }
        if (old.getPayStatus().equals(PayStatusEnum.PAY_STATUS_1.getValue())) {
            throw new ServiceException("该交易已付，不能修改");
        }
        if (!old.getDemandId().equals(clientDealInfo.getDemandId())) {
            throw new ServiceException("客户需求，不能修改");
        }

        checkClientDeal(clientDealInfo);
        clientDealInfo.setUpdateTime(DateUtils.getNowDate());
        return clientDealInfoMapper.updateClientDealInfo(clientDealInfo);
    }

    /**
     * 批量删除客户交易
     *
     * @param ids 需要删除的客户交易主键
     * @return 结果
     */
    @Override
    public int deleteClientDealInfoByIds(Long[] ids) {
        try {
            return clientDealInfoMapper.deleteClientDealInfoByIds(ids);
        } catch (Exception e) {
            throw new ServiceException("请先删除与交易有关的信息");
        }
    }

    /**
     * 删除客户交易信息
     *
     * @param id 客户交易主键
     * @return 结果
     */
    @Override
    public int deleteClientDealInfoById(Long id) {
        return clientDealInfoMapper.deleteClientDealInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<ClientDealInfo> getQueryWrapper(ClientDealInfoQuery clientDealInfoQuery) {
        QueryWrapper<ClientDealInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = clientDealInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = clientDealInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long clientId = clientDealInfoQuery.getClientId();
        queryWrapper.eq(StringUtils.isNotNull(clientId), "client_id", clientId);

        Long demandId = clientDealInfoQuery.getDemandId();
        queryWrapper.eq(StringUtils.isNotNull(demandId), "demand_id", demandId);

        String payStatus = clientDealInfoQuery.getPayStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(payStatus), "pay_status", payStatus);

        Long userId = clientDealInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Long deptId = clientDealInfoQuery.getDeptId();
        queryWrapper.eq(StringUtils.isNotNull(deptId), "dept_id", deptId);

        Date createTime = clientDealInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        String updateBy = clientDealInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy), "update_by", updateBy);

        Date updateTime = clientDealInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime")) && StringUtils.isNotNull(params.get("endUpdateTime")), "update_time", params.get("beginUpdateTime"), params.get("endUpdateTime"));

        return queryWrapper;
    }

    @Override
    public List<ClientDealInfoVo> convertVoList(List<ClientDealInfo> clientDealInfoList) {
        if (StringUtils.isEmpty(clientDealInfoList)) {
            return Collections.emptyList();
        }
        return clientDealInfoList.stream().map(ClientDealInfoVo::objToVo).collect(Collectors.toList());
    }

}
