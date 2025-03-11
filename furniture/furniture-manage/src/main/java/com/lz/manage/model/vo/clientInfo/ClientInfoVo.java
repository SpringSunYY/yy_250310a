package com.lz.manage.model.vo.clientInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.ClientInfo;
/**
 * 客户信息Vo对象 tb_client_info
 *
 * @author YY
 * @date 2025-03-11
 */
@Data
public class ClientInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    private Long id;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String clientName;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String phone;

    /** 性别 */
    @Excel(name = "性别")
    private String sex;

    /** 地址 */
    @Excel(name = "地址")
    private String clientAddress;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long userId;

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
     * @param clientInfo ClientInfo实体对象
     * @return ClientInfoVo
     */
    public static ClientInfoVo objToVo(ClientInfo clientInfo) {
        if (clientInfo == null) {
            return null;
        }
        ClientInfoVo clientInfoVo = new ClientInfoVo();
        BeanUtils.copyProperties(clientInfo, clientInfoVo);
        return clientInfoVo;
    }
}
