package com.lz.manage.model.vo.clientDealInfo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.ClientDealInfo;
/**
 * 客户交易Vo对象 tb_client_deal_info
 *
 * @author YY
 * @date 2025-03-11
 */
@Data
public class ClientDealInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    private Long id;

    /** 客户 */
    @Excel(name = "客户")
    private Long clientId;

    /** 需求 */
    @Excel(name = "需求")
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
    private Long userId;

    /** 部门 */
    @Excel(name = "部门")
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
     * @param clientDealInfo ClientDealInfo实体对象
     * @return ClientDealInfoVo
     */
    public static ClientDealInfoVo objToVo(ClientDealInfo clientDealInfo) {
        if (clientDealInfo == null) {
            return null;
        }
        ClientDealInfoVo clientDealInfoVo = new ClientDealInfoVo();
        BeanUtils.copyProperties(clientDealInfo, clientDealInfoVo);
        return clientDealInfoVo;
    }
}
