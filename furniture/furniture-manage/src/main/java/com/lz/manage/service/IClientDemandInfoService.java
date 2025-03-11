package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.ClientDemandInfo;
import com.lz.manage.model.vo.clientDemandInfo.ClientDemandInfoVo;
import com.lz.manage.model.dto.clientDemandInfo.ClientDemandInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 客户需求Service接口
 * 
 * @author YY
 * @date 2025-03-11
 */
public interface IClientDemandInfoService extends IService<ClientDemandInfo>
{
    //region mybatis代码
    /**
     * 查询客户需求
     * 
     * @param id 客户需求主键
     * @return 客户需求
     */
    public ClientDemandInfo selectClientDemandInfoById(Long id);

    /**
     * 查询客户需求列表
     * 
     * @param clientDemandInfo 客户需求
     * @return 客户需求集合
     */
    public List<ClientDemandInfo> selectClientDemandInfoList(ClientDemandInfo clientDemandInfo);

    /**
     * 新增客户需求
     * 
     * @param clientDemandInfo 客户需求
     * @return 结果
     */
    public int insertClientDemandInfo(ClientDemandInfo clientDemandInfo);

    /**
     * 修改客户需求
     * 
     * @param clientDemandInfo 客户需求
     * @return 结果
     */
    public int updateClientDemandInfo(ClientDemandInfo clientDemandInfo);

    /**
     * 批量删除客户需求
     * 
     * @param ids 需要删除的客户需求主键集合
     * @return 结果
     */
    public int deleteClientDemandInfoByIds(Long[] ids);

    /**
     * 删除客户需求信息
     * 
     * @param id 客户需求主键
     * @return 结果
     */
    public int deleteClientDemandInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param clientDemandInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<ClientDemandInfo> getQueryWrapper(ClientDemandInfoQuery clientDemandInfoQuery);

    /**
     * 转换vo
     *
     * @param clientDemandInfoList ClientDemandInfo集合
     * @return ClientDemandInfoVO集合
     */
    List<ClientDemandInfoVo> convertVoList(List<ClientDemandInfo> clientDemandInfoList);
}
