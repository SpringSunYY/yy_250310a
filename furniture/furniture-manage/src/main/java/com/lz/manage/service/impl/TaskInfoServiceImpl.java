package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.lz.common.utils.StringUtils;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.TaskInfoMapper;
import com.lz.manage.model.domain.TaskInfo;
import com.lz.manage.service.ITaskInfoService;
import com.lz.manage.model.dto.taskInfo.TaskInfoQuery;
import com.lz.manage.model.vo.taskInfo.TaskInfoVo;

/**
 * 任务信息Service业务层处理
 * 
 * @author YY
 * @date 2025-03-11
 */
@Service
public class TaskInfoServiceImpl extends ServiceImpl<TaskInfoMapper, TaskInfo> implements ITaskInfoService
{
    @Resource
    private TaskInfoMapper taskInfoMapper;

    //region mybatis代码
    /**
     * 查询任务信息
     * 
     * @param id 任务信息主键
     * @return 任务信息
     */
    @Override
    public TaskInfo selectTaskInfoById(Long id)
    {
        return taskInfoMapper.selectTaskInfoById(id);
    }

    /**
     * 查询任务信息列表
     * 
     * @param taskInfo 任务信息
     * @return 任务信息
     */
    @Override
    public List<TaskInfo> selectTaskInfoList(TaskInfo taskInfo)
    {
        return taskInfoMapper.selectTaskInfoList(taskInfo);
    }

    /**
     * 新增任务信息
     * 
     * @param taskInfo 任务信息
     * @return 结果
     */
    @Override
    public int insertTaskInfo(TaskInfo taskInfo)
    {
        taskInfo.setCreateTime(DateUtils.getNowDate());
        return taskInfoMapper.insertTaskInfo(taskInfo);
    }

    /**
     * 修改任务信息
     * 
     * @param taskInfo 任务信息
     * @return 结果
     */
    @Override
    public int updateTaskInfo(TaskInfo taskInfo)
    {
        taskInfo.setUpdateTime(DateUtils.getNowDate());
        return taskInfoMapper.updateTaskInfo(taskInfo);
    }

    /**
     * 批量删除任务信息
     * 
     * @param ids 需要删除的任务信息主键
     * @return 结果
     */
    @Override
    public int deleteTaskInfoByIds(Long[] ids)
    {
        return taskInfoMapper.deleteTaskInfoByIds(ids);
    }

    /**
     * 删除任务信息信息
     * 
     * @param id 任务信息主键
     * @return 结果
     */
    @Override
    public int deleteTaskInfoById(Long id)
    {
        return taskInfoMapper.deleteTaskInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<TaskInfo> getQueryWrapper(TaskInfoQuery taskInfoQuery){
        QueryWrapper<TaskInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = taskInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = taskInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long clientId = taskInfoQuery.getClientId();
        queryWrapper.eq( StringUtils.isNotNull(clientId),"client_id",clientId);

        Long demandId = taskInfoQuery.getDemandId();
        queryWrapper.eq( StringUtils.isNotNull(demandId),"demand_id",demandId);

        String taskName = taskInfoQuery.getTaskName();
        queryWrapper.like(StringUtils.isNotEmpty(taskName) ,"task_name",taskName);

        String taskStatus = taskInfoQuery.getTaskStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(taskStatus) ,"task_status",taskStatus);

        Long userId = taskInfoQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Long deptId = taskInfoQuery.getDeptId();
        queryWrapper.eq( StringUtils.isNotNull(deptId),"dept_id",deptId);

        Date createTime = taskInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        String updateBy = taskInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy) ,"update_by",updateBy);

        Date updateTime = taskInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime"))&&StringUtils.isNotNull(params.get("endUpdateTime")),"update_time",params.get("beginUpdateTime"),params.get("endUpdateTime"));

        return queryWrapper;
    }

    @Override
    public List<TaskInfoVo> convertVoList(List<TaskInfo> taskInfoList) {
        if (StringUtils.isEmpty(taskInfoList)) {
            return Collections.emptyList();
        }
        return taskInfoList.stream().map(TaskInfoVo::objToVo).collect(Collectors.toList());
    }

}
