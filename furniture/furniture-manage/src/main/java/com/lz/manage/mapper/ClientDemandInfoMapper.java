package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.ClientDemandInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 客户需求Mapper接口
 * 
 * @author YY
 * @date 2025-03-11
 */
public interface ClientDemandInfoMapper extends BaseMapper<ClientDemandInfo>
{
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
     * 删除客户需求
     * 
     * @param id 客户需求主键
     * @return 结果
     */
    public int deleteClientDemandInfoById(Long id);

    /**
     * 批量删除客户需求
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteClientDemandInfoByIds(Long[] ids);
}
