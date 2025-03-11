package com.lz.manage.model.dto.clientInfo;

import java.util.Map;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.ClientInfo;
/**
 * 客户信息Query对象 tb_client_info
 *
 * @author YY
 * @date 2025-03-11
 */
@Data
public class ClientInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 客户名称 */
    private String clientName;

    /** 手机号码 */
    private String phone;

    /** 性别 */
    private String sex;

    /** 地址 */
    private String clientAddress;

    /** 创建人 */
    private Long userId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param clientInfoQuery 查询对象
     * @return ClientInfo
     */
    public static ClientInfo queryToObj(ClientInfoQuery clientInfoQuery) {
        if (clientInfoQuery == null) {
            return null;
        }
        ClientInfo clientInfo = new ClientInfo();
        BeanUtils.copyProperties(clientInfoQuery, clientInfo);
        return clientInfo;
    }
}
