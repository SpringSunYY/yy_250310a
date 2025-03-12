package com.lz.manage.model.vo.taskInfo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.TaskInfo;
/**
 * 任务信息Vo对象 tb_task_info
 *
 * @author YY
 * @date 2025-03-11
 */
@Data
public class TaskInfoVo implements Serializable
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

    /** 任务名称 */
    private String taskName;

    /** 装修面积 */
    private BigDecimal furnishSpace;

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
     * @param taskInfo TaskInfo实体对象
     * @return TaskInfoVo
     */
    public static TaskInfoVo objToVo(TaskInfo taskInfo) {
        if (taskInfo == null) {
            return null;
        }
        TaskInfoVo taskInfoVo = new TaskInfoVo();
        BeanUtils.copyProperties(taskInfo, taskInfoVo);
        return taskInfoVo;
    }
}
