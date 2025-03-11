package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.ClientDealInfo;
import com.lz.manage.model.vo.clientDealInfo.ClientDealInfoVo;
import com.lz.manage.model.dto.clientDealInfo.ClientDealInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 客户交易Service接口
 * 
 * @author YY
 * @date 2025-03-11
 */
public interface IClientDealInfoService extends IService<ClientDealInfo>
{
    //region mybatis代码
    /**
     * 查询客户交易
     * 
     * @param id 客户交易主键
     * @return 客户交易
     */
    public ClientDealInfo selectClientDealInfoById(Long id);

    /**
     * 查询客户交易列表
     * 
     * @param clientDealInfo 客户交易
     * @return 客户交易集合
     */
    public List<ClientDealInfo> selectClientDealInfoList(ClientDealInfo clientDealInfo);

    /**
     * 新增客户交易
     * 
     * @param clientDealInfo 客户交易
     * @return 结果
     */
    public int insertClientDealInfo(ClientDealInfo clientDealInfo);

    /**
     * 修改客户交易
     * 
     * @param clientDealInfo 客户交易
     * @return 结果
     */
    public int updateClientDealInfo(ClientDealInfo clientDealInfo);

    /**
     * 批量删除客户交易
     * 
     * @param ids 需要删除的客户交易主键集合
     * @return 结果
     */
    public int deleteClientDealInfoByIds(Long[] ids);

    /**
     * 删除客户交易信息
     * 
     * @param id 客户交易主键
     * @return 结果
     */
    public int deleteClientDealInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param clientDealInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<ClientDealInfo> getQueryWrapper(ClientDealInfoQuery clientDealInfoQuery);

    /**
     * 转换vo
     *
     * @param clientDealInfoList ClientDealInfo集合
     * @return ClientDealInfoVO集合
     */
    List<ClientDealInfoVo> convertVoList(List<ClientDealInfo> clientDealInfoList);
}
