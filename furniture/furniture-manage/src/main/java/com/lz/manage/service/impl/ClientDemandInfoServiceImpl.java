package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.lz.common.core.domain.entity.SysDept;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;

import javax.annotation.Resource;

import com.lz.manage.model.domain.ClientInfo;
import com.lz.manage.model.enums.DemandStatusEnum;
import com.lz.manage.service.IClientInfoService;
import com.lz.system.service.ISysDeptService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.ClientDemandInfoMapper;
import com.lz.manage.model.domain.ClientDemandInfo;
import com.lz.manage.service.IClientDemandInfoService;
import com.lz.manage.model.dto.clientDemandInfo.ClientDemandInfoQuery;
import com.lz.manage.model.vo.clientDemandInfo.ClientDemandInfoVo;

/**
 * 客户需求Service业务层处理
 *
 * @author YY
 * @date 2025-03-11
 */
@Service
public class ClientDemandInfoServiceImpl extends ServiceImpl<ClientDemandInfoMapper, ClientDemandInfo> implements IClientDemandInfoService {
    @Resource
    private ClientDemandInfoMapper clientDemandInfoMapper;

    @Resource
    private IClientInfoService clientInfoService;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysDeptService deptService;

    //region mybatis代码

    /**
     * 查询客户需求
     *
     * @param id 客户需求主键
     * @return 客户需求
     */
    @Override
    public ClientDemandInfo selectClientDemandInfoById(Long id) {
        return clientDemandInfoMapper.selectClientDemandInfoById(id);
    }

    /**
     * 查询客户需求列表
     *
     * @param clientDemandInfo 客户需求
     * @return 客户需求
     */
    @Override
    public List<ClientDemandInfo> selectClientDemandInfoList(ClientDemandInfo clientDemandInfo) {
        List<ClientDemandInfo> clientDemandInfos = clientDemandInfoMapper.selectClientDemandInfoList(clientDemandInfo);
        for (ClientDemandInfo info : clientDemandInfos) {
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
        }
        return clientDemandInfos;
    }

    /**
     * 新增客户需求
     *
     * @param clientDemandInfo 客户需求
     * @return 结果
     */
    @Override
    public int insertClientDemandInfo(ClientDemandInfo clientDemandInfo) {
        clientDemandInfo.setDemandStatus(DemandStatusEnum.DEMAND_STATUS_0.getValue());
        checkClientDemandInfo(clientDemandInfo);
        clientDemandInfo.setCreateTime(DateUtils.getNowDate());
        return clientDemandInfoMapper.insertClientDemandInfo(clientDemandInfo);
    }

    private void checkClientDemandInfo(ClientDemandInfo clientDemandInfo) {
        SysUser user = userService.selectUserById(clientDemandInfo.getUserId());
        if (StringUtils.isNull(user)) {
            throw new ServiceException("用户不存在");
        }
        clientDemandInfo.setDeptId(user.getDeptId());
        ClientInfo clientInfo = clientInfoService.selectClientInfoById(clientDemandInfo.getClientId());
        if (StringUtils.isNull(clientInfo)) {
            throw new ServiceException("客户不存在");
        }
    }

    /**
     * 修改客户需求
     *
     * @param clientDemandInfo 客户需求
     * @return 结果
     */
    @Override
    public int updateClientDemandInfo(ClientDemandInfo clientDemandInfo) {
//        ClientDemandInfo old = clientDemandInfoMapper.selectClientDemandInfoById(clientDemandInfo.getId());
//        if (old.getDemandStatus().equals(DemandStatusEnum.DEMAND_STATUS_2.getValue())) {
//            throw new ServiceException("该需求已完成，不能修改");
//        }
        checkClientDemandInfo(clientDemandInfo);
        clientDemandInfo.setUpdateTime(DateUtils.getNowDate());
        return clientDemandInfoMapper.updateClientDemandInfo(clientDemandInfo);
    }

    /**
     * 批量删除客户需求
     *
     * @param ids 需要删除的客户需求主键
     * @return 结果
     */
    @Override
    public int deleteClientDemandInfoByIds(Long[] ids) {
        return clientDemandInfoMapper.deleteClientDemandInfoByIds(ids);
    }

    /**
     * 删除客户需求信息
     *
     * @param id 客户需求主键
     * @return 结果
     */
    @Override
    public int deleteClientDemandInfoById(Long id) {
        return clientDemandInfoMapper.deleteClientDemandInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<ClientDemandInfo> getQueryWrapper(ClientDemandInfoQuery clientDemandInfoQuery) {
        QueryWrapper<ClientDemandInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = clientDemandInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = clientDemandInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long clientId = clientDemandInfoQuery.getClientId();
        queryWrapper.eq(StringUtils.isNotNull(clientId), "client_id", clientId);

        String demandName = clientDemandInfoQuery.getDemandName();
        queryWrapper.like(StringUtils.isNotEmpty(demandName), "demand_name", demandName);

        Date expectStartTime = clientDemandInfoQuery.getExpectStartTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginExpectStartTime")) && StringUtils.isNotNull(params.get("endExpectStartTime")), "expect_start_time", params.get("beginExpectStartTime"), params.get("endExpectStartTime"));

        Date expectEndTime = clientDemandInfoQuery.getExpectEndTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginExpectEndTime")) && StringUtils.isNotNull(params.get("endExpectEndTime")), "expect_end_time", params.get("beginExpectEndTime"), params.get("endExpectEndTime"));

        String demandStatus = clientDemandInfoQuery.getDemandStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(demandStatus), "demand_status", demandStatus);

        Long userId = clientDemandInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Long deptId = clientDemandInfoQuery.getDeptId();
        queryWrapper.eq(StringUtils.isNotNull(deptId), "dept_id", deptId);

        Date createTime = clientDemandInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        String updateBy = clientDemandInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy), "update_by", updateBy);

        Date updateTime = clientDemandInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime")) && StringUtils.isNotNull(params.get("endUpdateTime")), "update_time", params.get("beginUpdateTime"), params.get("endUpdateTime"));

        return queryWrapper;
    }

    @Override
    public List<ClientDemandInfoVo> convertVoList(List<ClientDemandInfo> clientDemandInfoList) {
        if (StringUtils.isEmpty(clientDemandInfoList)) {
            return Collections.emptyList();
        }
        return clientDemandInfoList.stream().map(ClientDemandInfoVo::objToVo).collect(Collectors.toList());
    }

}
