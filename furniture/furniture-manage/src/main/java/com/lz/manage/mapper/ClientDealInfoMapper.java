package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.ClientDealInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 客户交易Mapper接口
 * 
 * @author YY
 * @date 2025-03-11
 */
public interface ClientDealInfoMapper extends BaseMapper<ClientDealInfo>
{
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
     * 删除客户交易
     * 
     * @param id 客户交易主键
     * @return 结果
     */
    public int deleteClientDealInfoById(Long id);

    /**
     * 批量删除客户交易
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteClientDealInfoByIds(Long[] ids);
}
