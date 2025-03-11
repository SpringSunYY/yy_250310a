package com.lz.manage.model.dto.taskInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.TaskInfo;
/**
 * 任务信息Vo对象 tb_task_info
 *
 * @author YY
 * @date 2025-03-11
 */
@Data
public class TaskInfoInsert implements Serializable
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

    /** 装修面积 */
    private BigDecimal furnishSpace;

    /** 任务状态 */
    private String taskStatus;

    /** 装修人员 */
    private Long userId;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param taskInfoInsert 插入对象
     * @return TaskInfoInsert
     */
    public static TaskInfo insertToObj(TaskInfoInsert taskInfoInsert) {
        if (taskInfoInsert == null) {
            return null;
        }
        TaskInfo taskInfo = new TaskInfo();
        BeanUtils.copyProperties(taskInfoInsert, taskInfo);
        return taskInfo;
    }
}
