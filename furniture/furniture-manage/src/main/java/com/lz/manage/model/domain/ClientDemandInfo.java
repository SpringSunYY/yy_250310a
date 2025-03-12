package com.lz.manage.model.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.lz.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * 客户需求对象 tb_client_demand_info
 *
 * @author YY
 * @date 2025-03-11
 */
@TableName("tb_client_demand_info")
@Data
public class ClientDemandInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 客户 */
    @Excel(name = "客户")
    @TableField(exist = false)
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
    @TableField(exist = false)
    private String userName;
    private Long userId;

    /** 部门 */
    @Excel(name = "部门")
    @TableField(exist = false)
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

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
