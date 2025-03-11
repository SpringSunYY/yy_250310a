package com.lz.manage.model.dto.clientDemandInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.ClientDemandInfo;
/**
 * 客户需求Vo对象 tb_client_demand_info
 *
 * @author YY
 * @date 2025-03-11
 */
@Data
public class ClientDemandInfoEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 客户 */
    private Long clientId;

    /** 需求名称 */
    private String demandName;

    /** 房屋面积 */
    private BigDecimal floorSpace;

    /** 地址 */
    private String address;

    /** 房屋朝向 */
    private String buildOrientation;

    /** 装修风格 */
    private String furnishStyle;

    /** 总预算 */
    private BigDecimal totalBudget;

    /** 期望开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expectStartTime;

    /** 期望结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expectEndTime;

    /** 需求状态 */
    private String demandStatus;

    /** 销售员 */
    private Long userId;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param clientDemandInfoEdit 编辑对象
     * @return ClientDemandInfo
     */
    public static ClientDemandInfo editToObj(ClientDemandInfoEdit clientDemandInfoEdit) {
        if (clientDemandInfoEdit == null) {
            return null;
        }
        ClientDemandInfo clientDemandInfo = new ClientDemandInfo();
        BeanUtils.copyProperties(clientDemandInfoEdit, clientDemandInfo);
        return clientDemandInfo;
    }
}
