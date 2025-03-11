package com.lz.manage.model.vo.taskProgressInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.TaskProgressInfo;
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
    @Excel(name = "编号")
    private Long id;

    /** 客户 */
    @Excel(name = "客户")
    private Long clientId;

    /** 需求 */
    @Excel(name = "需求")
    private Long demandId;

    /** 任务 */
    @Excel(name = "任务")
    private Long taskId;

    /** 进度描述 */
    @Excel(name = "进度描述")
    private String progress;

    /** 任务状态 */
    @Excel(name = "任务状态")
    private String taskStatus;

    /** 装修人员 */
    @Excel(name = "装修人员")
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
