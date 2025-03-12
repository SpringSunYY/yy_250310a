package com.lz.manage.model.vo.taskProgressInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.TaskProgressInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
/**
 * 任务进度Vo对象 tb_task_progress_info
 *
 * @author YY
 * @date 2025-03-11
 */
@Data
public class TaskProgressInfoVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 客户 */
    private String clientName;
    private Long clientId;

    /** 需求 */
    private String demandName;
    private Long demandId;

    /** 任务 */
    private String taskName;
    private Long taskId;

    /** 进度描述 */
    private String progress;

    /** 任务状态 */
    private String taskStatus;

    /** 装修人员 */
    private String userName;
    private Long userId;

    /** 部门 */
    private String deptName;
    private Long deptId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /** 备注 */
    private String remark;


     /**
     * 对象转封装类
     *
     * @param taskProgressInfo TaskProgressInfo实体对象
     * @return TaskProgressInfoVo
     */
    public static TaskProgressInfoVo objToVo(TaskProgressInfo taskProgressInfo) {
        if (taskProgressInfo == null) {
            return null;
        }
        TaskProgressInfoVo taskProgressInfoVo = new TaskProgressInfoVo();
        BeanUtils.copyProperties(taskProgressInfo, taskProgressInfoVo);
        return taskProgressInfoVo;
    }
}
