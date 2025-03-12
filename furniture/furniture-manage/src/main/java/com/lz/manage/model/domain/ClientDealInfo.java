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
 * 客户交易对象 tb_client_deal_info
 *
 * @author YY
 * @date 2025-03-11
 */
@TableName("tb_client_deal_info")
@Data
public class ClientDealInfo implements Serializable
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

    /** 需求 */
    @Excel(name = "需求")
    @TableField(exist = false)
    private String demandName;
    private Long demandId;

    /** 总金额 */
    @Excel(name = "总金额")
    private BigDecimal totalPrice;

    /** 支付状态 */
    @Excel(name = "支付状态")
    private String payStatus;

    /** 合同附件 */
    @Excel(name = "合同附件")
    private String contractFile;

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
