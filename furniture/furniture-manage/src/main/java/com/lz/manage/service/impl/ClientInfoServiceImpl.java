package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import javax.annotation.Resource;

import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.ClientInfoMapper;
import com.lz.manage.model.domain.ClientInfo;
import com.lz.manage.service.IClientInfoService;
import com.lz.manage.model.dto.clientInfo.ClientInfoQuery;
import com.lz.manage.model.vo.clientInfo.ClientInfoVo;

/**
 * 客户信息Service业务层处理
 * 
 * @author YY
 * @date 2025-03-11
 */
@Service
public class ClientInfoServiceImpl extends ServiceImpl<ClientInfoMapper, ClientInfo> implements IClientInfoService
{
    @Resource
    private ClientInfoMapper clientInfoMapper;

    @Resource
    private ISysUserService userService;

    //region mybatis代码
    /**
     * 查询客户信息
     * 
     * @param id 客户信息主键
     * @return 客户信息
     */
    @Override
    public ClientInfo selectClientInfoById(Long id)
    {
        return clientInfoMapper.selectClientInfoById(id);
    }

    /**
     * 查询客户信息列表
     * 
     * @param clientInfo 客户信息
     * @return 客户信息
     */
    @Override
    public List<ClientInfo> selectClientInfoList(ClientInfo clientInfo)
    {
        List<ClientInfo> clientInfos = clientInfoMapper.selectClientInfoList(clientInfo);
        for (ClientInfo info : clientInfos) {
            SysUser user = userService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(user)) {
                info.setUserName(user.getUserName());
            }
        }
        return clientInfos;
    }

    /**
     * 新增客户信息
     * 
     * @param clientInfo 客户信息
     * @return 结果
     */
    @Override
    public int insertClientInfo(ClientInfo clientInfo)
    {
        clientInfo.setUserId(SecurityUtils.getUserId());
        clientInfo.setCreateTime(DateUtils.getNowDate());
        return clientInfoMapper.insertClientInfo(clientInfo);
    }

    /**
     * 修改客户信息
     * 
     * @param clientInfo 客户信息
     * @return 结果
     */
    @Override
    public int updateClientInfo(ClientInfo clientInfo)
    {
        clientInfo.setUpdateBy(SecurityUtils.getUsername());
        clientInfo.setUpdateTime(DateUtils.getNowDate());
        return clientInfoMapper.updateClientInfo(clientInfo);
    }

    /**
     * 批量删除客户信息
     * 
     * @param ids 需要删除的客户信息主键
     * @return 结果
     */
    @Override
    public int deleteClientInfoByIds(Long[] ids)
    {
        return clientInfoMapper.deleteClientInfoByIds(ids);
    }

    /**
     * 删除客户信息信息
     * 
     * @param id 客户信息主键
     * @return 结果
     */
    @Override
    public int deleteClientInfoById(Long id)
    {
        return clientInfoMapper.deleteClientInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<ClientInfo> getQueryWrapper(ClientInfoQuery clientInfoQuery){
        QueryWrapper<ClientInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = clientInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = clientInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        String clientName = clientInfoQuery.getClientName();
        queryWrapper.like(StringUtils.isNotEmpty(clientName) ,"client_name",clientName);

        String phone = clientInfoQuery.getPhone();
        queryWrapper.like(StringUtils.isNotEmpty(phone) ,"phone",phone);

        String sex = clientInfoQuery.getSex();
        queryWrapper.eq(StringUtils.isNotEmpty(sex) ,"sex",sex);

        String clientAddress = clientInfoQuery.getClientAddress();
        queryWrapper.eq(StringUtils.isNotEmpty(clientAddress) ,"client_address",clientAddress);

        Long userId = clientInfoQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Date createTime = clientInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        String updateBy = clientInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy) ,"update_by",updateBy);

        Date updateTime = clientInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime"))&&StringUtils.isNotNull(params.get("endUpdateTime")),"update_time",params.get("beginUpdateTime"),params.get("endUpdateTime"));

        return queryWrapper;
    }

    @Override
    public List<ClientInfoVo> convertVoList(List<ClientInfo> clientInfoList) {
        if (StringUtils.isEmpty(clientInfoList)) {
            return Collections.emptyList();
        }
        return clientInfoList.stream().map(ClientInfoVo::objToVo).collect(Collectors.toList());
    }

}
