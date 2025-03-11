package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.lz.common.utils.StringUtils;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.ClientDealInfoMapper;
import com.lz.manage.model.domain.ClientDealInfo;
import com.lz.manage.service.IClientDealInfoService;
import com.lz.manage.model.dto.clientDealInfo.ClientDealInfoQuery;
import com.lz.manage.model.vo.clientDealInfo.ClientDealInfoVo;

/**
 * 客户交易Service业务层处理
 * 
 * @author YY
 * @date 2025-03-11
 */
@Service
public class ClientDealInfoServiceImpl extends ServiceImpl<ClientDealInfoMapper, ClientDealInfo> implements IClientDealInfoService
{
    @Resource
    private ClientDealInfoMapper clientDealInfoMapper;

    //region mybatis代码
    /**
     * 查询客户交易
     * 
     * @param id 客户交易主键
     * @return 客户交易
     */
    @Override
    public ClientDealInfo selectClientDealInfoById(Long id)
    {
        return clientDealInfoMapper.selectClientDealInfoById(id);
    }

    /**
     * 查询客户交易列表
     * 
     * @param clientDealInfo 客户交易
     * @return 客户交易
     */
    @Override
    public List<ClientDealInfo> selectClientDealInfoList(ClientDealInfo clientDealInfo)
    {
        return clientDealInfoMapper.selectClientDealInfoList(clientDealInfo);
    }

    /**
     * 新增客户交易
     * 
     * @param clientDealInfo 客户交易
     * @return 结果
     */
    @Override
    public int insertClientDealInfo(ClientDealInfo clientDealInfo)
    {
        clientDealInfo.setCreateTime(DateUtils.getNowDate());
        return clientDealInfoMapper.insertClientDealInfo(clientDealInfo);
    }

    /**
     * 修改客户交易
     * 
     * @param clientDealInfo 客户交易
     * @return 结果
     */
    @Override
    public int updateClientDealInfo(ClientDealInfo clientDealInfo)
    {
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
    public int deleteClientDealInfoByIds(Long[] ids)
    {
        return clientDealInfoMapper.deleteClientDealInfoByIds(ids);
    }

    /**
     * 删除客户交易信息
     * 
     * @param id 客户交易主键
     * @return 结果
     */
    @Override
    public int deleteClientDealInfoById(Long id)
    {
        return clientDealInfoMapper.deleteClientDealInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<ClientDealInfo> getQueryWrapper(ClientDealInfoQuery clientDealInfoQuery){
        QueryWrapper<ClientDealInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = clientDealInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = clientDealInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long clientId = clientDealInfoQuery.getClientId();
        queryWrapper.eq( StringUtils.isNotNull(clientId),"client_id",clientId);

        Long demandId = clientDealInfoQuery.getDemandId();
        queryWrapper.eq( StringUtils.isNotNull(demandId),"demand_id",demandId);

        String payStatus = clientDealInfoQuery.getPayStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(payStatus) ,"pay_status",payStatus);

        Long userId = clientDealInfoQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Long deptId = clientDealInfoQuery.getDeptId();
        queryWrapper.eq( StringUtils.isNotNull(deptId),"dept_id",deptId);

        Date createTime = clientDealInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        String updateBy = clientDealInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy) ,"update_by",updateBy);

        Date updateTime = clientDealInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime"))&&StringUtils.isNotNull(params.get("endUpdateTime")),"update_time",params.get("beginUpdateTime"),params.get("endUpdateTime"));

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
