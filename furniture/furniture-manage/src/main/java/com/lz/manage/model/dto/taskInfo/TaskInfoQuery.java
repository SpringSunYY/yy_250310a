package com.lz.manage.model.dto.taskInfo;

import java.util.Map;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.TaskInfo;
/**
 * 任务信息Query对象 tb_task_info
 *
 * @author YY
 * @date 2025-03-11
 */
@Data
public class TaskInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 客户 */
    private Long clientId;

    /** 需求 */
    private Long demandId;

    /** 任务名称 */
    private String taskName;

    /** 任务状态 */
    private String taskStatus;

    /** 装修人员 */
    private Long userId;

    /** 部门 */
    private Long deptId;

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
     * @param taskInfoQuery 查询对象
     * @return TaskInfo
     */
    public static TaskInfo queryToObj(TaskInfoQuery taskInfoQuery) {
        if (taskInfoQuery == null) {
            return null;
        }
        TaskInfo taskInfo = new TaskInfo();
        BeanUtils.copyProperties(taskInfoQuery, taskInfo);
        return taskInfo;
    }
}
