package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.lz.common.annotation.DataScope;
import com.lz.common.core.domain.entity.SysDept;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;

import javax.annotation.Resource;

import com.lz.manage.model.domain.ClientDemandInfo;
import com.lz.manage.model.domain.ClientInfo;
import com.lz.manage.model.domain.TaskInfo;
import com.lz.manage.model.enums.TaskStatusEnum;
import com.lz.manage.service.IClientDemandInfoService;
import com.lz.manage.service.IClientInfoService;
import com.lz.manage.service.ITaskInfoService;
import com.lz.system.service.ISysDeptService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.TaskProgressInfoMapper;
import com.lz.manage.model.domain.TaskProgressInfo;
import com.lz.manage.service.ITaskProgressInfoService;
import com.lz.manage.model.dto.taskProgressInfo.TaskProgressInfoQuery;
import com.lz.manage.model.vo.taskProgressInfo.TaskProgressInfoVo;

/**
 * 任务进度Service业务层处理
 *
 * @author YY
 * @date 2025-03-11
 */
@Service
public class TaskProgressInfoServiceImpl extends ServiceImpl<TaskProgressInfoMapper, TaskProgressInfo> implements ITaskProgressInfoService {
    @Resource
    private TaskProgressInfoMapper taskProgressInfoMapper;

    @Resource
    private IClientInfoService clientInfoService;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysDeptService deptService;

    @Resource
    private IClientDemandInfoService demandInfoService;

    @Resource
    private ITaskInfoService taskInfoService;


    //region mybatis代码

    /**
     * 查询任务进度
     *
     * @param id 任务进度主键
     * @return 任务进度
     */
    @Override
    public TaskProgressInfo selectTaskProgressInfoById(Long id) {
        return taskProgressInfoMapper.selectTaskProgressInfoById(id);
    }

    /**
     * 查询任务进度列表
     *
     * @param taskProgressInfo 任务进度
     * @return 任务进度
     */
    @Override
    @DataScope(userAlias = "tb_task_progress_info",deptAlias = "tb_task_progress_info")
    public List<TaskProgressInfo> selectTaskProgressInfoList(TaskProgressInfo taskProgressInfo) {
        List<TaskProgressInfo> taskProgressInfos = taskProgressInfoMapper.selectTaskProgressInfoList(taskProgressInfo);
        for (TaskProgressInfo info : taskProgressInfos) {
            SysUser user = userService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(user)) {
                info.setUserName(user.getUserName());
            }
            SysDept dept = deptService.selectDeptById(info.getDeptId());
            if (StringUtils.isNotNull(dept)) {
                info.setDeptName(dept.getDeptName());
            }
            ClientInfo clientInfo = clientInfoService.selectClientInfoById(info.getClientId());
            if (StringUtils.isNotNull(clientInfo)) {
                info.setClientName(clientInfo.getClientName());
            }
            ClientDemandInfo clientDemandInfo = demandInfoService.selectClientDemandInfoById(info.getDemandId());
            if (StringUtils.isNotNull(clientDemandInfo)) {
                info.setDemandName(clientDemandInfo.getDemandName());
            }
            TaskInfo taskInfo = taskInfoService.selectTaskInfoById(info.getTaskId());
            if (StringUtils.isNotNull(taskInfo)) {
                info.setTaskName(taskInfo.getTaskName());
            }
        }
        return taskProgressInfos;
    }

    /**
     * 新增任务进度
     *
     * @param taskProgressInfo 任务进度
     * @return 结果
     */
    @Override
    public int insertTaskProgressInfo(TaskProgressInfo taskProgressInfo) {
        checkTaskProgress(taskProgressInfo);
        taskProgressInfo.setCreateTime(DateUtils.getNowDate());
        return taskProgressInfoMapper.insertTaskProgressInfo(taskProgressInfo);
    }

    private void checkTaskProgress(TaskProgressInfo taskProgressInfo) {
        TaskInfo taskInfo = taskInfoService.selectTaskInfoById(taskProgressInfo.getTaskId());
        if (StringUtils.isNull(taskInfo)) {
            throw new ServiceException("该任务不存在！！！");
        }
        if (taskInfo.getTaskStatus().equals(TaskStatusEnum.TASK_STATUS_2.getValue())) {
            throw new ServiceException("该任务已完成，不能发布或者修改任务");
        }
        taskProgressInfo.setDeptId(taskInfo.getDeptId());
        taskProgressInfo.setClientId(taskInfo.getClientId());
        taskProgressInfo.setUserId(taskInfo.getUserId());
        taskProgressInfo.setDemandId(taskInfo.getDemandId());
    }

    /**
     * 修改任务进度
     *
     * @param taskProgressInfo 任务进度
     * @return 结果
     */
    @Override
    public int updateTaskProgressInfo(TaskProgressInfo taskProgressInfo) {
        checkTaskProgress(taskProgressInfo);
        taskProgressInfo.setUpdateBy(SecurityUtils.getUsername());
        taskProgressInfo.setUpdateTime(DateUtils.getNowDate());
        return taskProgressInfoMapper.updateTaskProgressInfo(taskProgressInfo);
    }

    /**
     * 批量删除任务进度
     *
     * @param ids 需要删除的任务进度主键
     * @return 结果
     */
    @Override
    public int deleteTaskProgressInfoByIds(Long[] ids) {
        return taskProgressInfoMapper.deleteTaskProgressInfoByIds(ids);
    }

    /**
     * 删除任务进度信息
     *
     * @param id 任务进度主键
     * @return 结果
     */
    @Override
    public int deleteTaskProgressInfoById(Long id) {
        return taskProgressInfoMapper.deleteTaskProgressInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<TaskProgressInfo> getQueryWrapper(TaskProgressInfoQuery taskProgressInfoQuery) {
        QueryWrapper<TaskProgressInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = taskProgressInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = taskProgressInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long clientId = taskProgressInfoQuery.getClientId();
        queryWrapper.eq(StringUtils.isNotNull(clientId), "client_id", clientId);

        Long demandId = taskProgressInfoQuery.getDemandId();
        queryWrapper.eq(StringUtils.isNotNull(demandId), "demand_id", demandId);

        Long taskId = taskProgressInfoQuery.getTaskId();
        queryWrapper.eq(StringUtils.isNotNull(taskId), "task_id", taskId);

        String taskStatus = taskProgressInfoQuery.getTaskStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(taskStatus), "task_status", taskStatus);

        Long userId = taskProgressInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Long deptId = taskProgressInfoQuery.getDeptId();
        queryWrapper.eq(StringUtils.isNotNull(deptId), "dept_id", deptId);

        Date createTime = taskProgressInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        String updateBy = taskProgressInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy), "update_by", updateBy);

        Date updateTime = taskProgressInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime")) && StringUtils.isNotNull(params.get("endUpdateTime")), "update_time", params.get("beginUpdateTime"), params.get("endUpdateTime"));

        return queryWrapper;
    }

    @Override
    public List<TaskProgressInfoVo> convertVoList(List<TaskProgressInfo> taskProgressInfoList) {
        if (StringUtils.isEmpty(taskProgressInfoList)) {
            return Collections.emptyList();
        }
        return taskProgressInfoList.stream().map(TaskProgressInfoVo::objToVo).collect(Collectors.toList());
    }

}
