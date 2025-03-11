package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.ClientInfo;
import com.lz.manage.model.vo.clientInfo.ClientInfoVo;
import com.lz.manage.model.dto.clientInfo.ClientInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 客户信息Service接口
 * 
 * @author YY
 * @date 2025-03-11
 */
public interface IClientInfoService extends IService<ClientInfo>
{
    //region mybatis代码
    /**
     * 查询客户信息
     * 
     * @param id 客户信息主键
     * @return 客户信息
     */
    public ClientInfo selectClientInfoById(Long id);

    /**
     * 查询客户信息列表
     * 
     * @param clientInfo 客户信息
     * @return 客户信息集合
     */
    public List<ClientInfo> selectClientInfoList(ClientInfo clientInfo);

    /**
     * 新增客户信息
     * 
     * @param clientInfo 客户信息
     * @return 结果
     */
    public int insertClientInfo(ClientInfo clientInfo);

    /**
     * 修改客户信息
     * 
     * @param clientInfo 客户信息
     * @return 结果
     */
    public int updateClientInfo(ClientInfo clientInfo);

    /**
     * 批量删除客户信息
     * 
     * @param ids 需要删除的客户信息主键集合
     * @return 结果
     */
    public int deleteClientInfoByIds(Long[] ids);

    /**
     * 删除客户信息信息
     * 
     * @param id 客户信息主键
     * @return 结果
     */
    public int deleteClientInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param clientInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<ClientInfo> getQueryWrapper(ClientInfoQuery clientInfoQuery);

    /**
     * 转换vo
     *
     * @param clientInfoList ClientInfo集合
     * @return ClientInfoVO集合
     */
    List<ClientInfoVo> convertVoList(List<ClientInfo> clientInfoList);
}
