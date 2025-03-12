package com.lz.manage.model.vo.clientDemandInfo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.ClientDemandInfo;
/**
 * 客户需求Vo对象 tb_client_demand_info
 *
 * @author YY
 * @date 2025-03-11
 */
@Data
public class ClientDemandInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    private Long id;

    /** 客户 */
    @Excel(name = "客户")
    private String clientName;
    private Long clientId;

    /** 需求名称 */
    @Excel(name = "需求名称")
    private String demandName;

    /** 房屋面积 */
    @Excel(name = "房屋面积")
    private BigDecimal floorSpace;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 房屋朝向 */
    @Excel(name = "房屋朝向")
    private String buildOrientation;

    /** 装修风格 */
    @Excel(name = "装修风格")
    private String furnishStyle;

    /** 总预算 */
    @Excel(name = "总预算")
    private BigDecimal totalBudget;

    /** 期望开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "期望开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expectStartTime;

    /** 期望结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "期望结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expectEndTime;

    /** 需求状态 */
    @Excel(name = "需求状态")
    private String demandStatus;

    /** 销售员 */
    @Excel(name = "销售员")
    private String userName;
    private Long userId;

    /** 部门 */
    @Excel(name = "部门")
    private String deptName;
    private Long deptId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    @Excel(name = "更新人")
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;


     /**
     * 对象转封装类
     *
     * @param clientDemandInfo ClientDemandInfo实体对象
     * @return ClientDemandInfoVo
     */
    public static ClientDemandInfoVo objToVo(ClientDemandInfo clientDemandInfo) {
        if (clientDemandInfo == null) {
            return null;
        }
        ClientDemandInfoVo clientDemandInfoVo = new ClientDemandInfoVo();
        BeanUtils.copyProperties(clientDemandInfo, clientDemandInfoVo);
        return clientDemandInfoVo;
    }
}
