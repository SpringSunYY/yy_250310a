package com.lz.manage.model.dto.clientDealInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.ClientDealInfo;
/**
 * 客户交易Vo对象 tb_client_deal_info
 *
 * @author YY
 * @date 2025-03-11
 */
@Data
public class ClientDealInfoEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 客户 */
    private Long clientId;

    /** 需求 */
    private Long demandId;

    /** 总金额 */
    private BigDecimal totalPrice;

    /** 支付状态 */
    private String payStatus;

    /** 合同附件 */
    private String contractFile;

    /** 销售员 */
    private Long userId;

    /** 部门 */
    private Long deptId;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param clientDealInfoEdit 编辑对象
     * @return ClientDealInfo
     */
    public static ClientDealInfo editToObj(ClientDealInfoEdit clientDealInfoEdit) {
        if (clientDealInfoEdit == null) {
            return null;
        }
        ClientDealInfo clientDealInfo = new ClientDealInfo();
        BeanUtils.copyProperties(clientDealInfoEdit, clientDealInfo);
        return clientDealInfo;
    }
}
