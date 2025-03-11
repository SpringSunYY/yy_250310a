package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.ClientInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 客户信息Mapper接口
 * 
 * @author YY
 * @date 2025-03-11
 */
public interface ClientInfoMapper extends BaseMapper<ClientInfo>
{
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
     * 删除客户信息
     * 
     * @param id 客户信息主键
     * @return 结果
     */
    public int deleteClientInfoById(Long id);

    /**
     * 批量删除客户信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteClientInfoByIds(Long[] ids);
}
