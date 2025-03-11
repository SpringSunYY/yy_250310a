package com.lz.manage.model.dto.clientInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.ClientInfo;
/**
 * 客户信息Vo对象 tb_client_info
 *
 * @author YY
 * @date 2025-03-11
 */
@Data
public class ClientInfoInsert implements Serializable
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

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param clientInfoInsert 插入对象
     * @return ClientInfoInsert
     */
    public static ClientInfo insertToObj(ClientInfoInsert clientInfoInsert) {
        if (clientInfoInsert == null) {
            return null;
        }
        ClientInfo clientInfo = new ClientInfo();
        BeanUtils.copyProperties(clientInfoInsert, clientInfo);
        return clientInfo;
    }
}
