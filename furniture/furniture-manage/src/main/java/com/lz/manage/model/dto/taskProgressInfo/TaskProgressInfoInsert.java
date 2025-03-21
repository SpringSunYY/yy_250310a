package com.lz.manage.model.dto.taskProgressInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.TaskProgressInfo;
/**
 * 任务进度Vo对象 tb_task_progress_info
 *
 * @author YY
 * @date 2025-03-11
 */
@Data
public class TaskProgressInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 客户 */
    private Long clientId;

    /** 需求 */
    private Long demandId;

    /** 任务 */
    private Long taskId;

    /** 凭证 */
    private String progressImage;

    /** 进度描述 */
    private String progress;

    /** 任务状态 */
    private String taskStatus;

    /** 装修人员 */
    private Long userId;

    /** 部门 */
    private Long deptId;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param taskProgressInfoInsert 插入对象
     * @return TaskProgressInfoInsert
     */
    public static TaskProgressInfo insertToObj(TaskProgressInfoInsert taskProgressInfoInsert) {
        if (taskProgressInfoInsert == null) {
            return null;
        }
        TaskProgressInfo taskProgressInfo = new TaskProgressInfo();
        BeanUtils.copyProperties(taskProgressInfoInsert, taskProgressInfo);
        return taskProgressInfo;
    }
}
