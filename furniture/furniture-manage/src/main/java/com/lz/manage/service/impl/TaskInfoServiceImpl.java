package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lz.common.annotation.DataScope;
import com.lz.common.core.domain.entity.SysDept;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;

import javax.annotation.Resource;

import com.lz.manage.mapper.TaskProgressInfoMapper;
import com.lz.manage.model.domain.ClientDemandInfo;
import com.lz.manage.model.domain.ClientInfo;
import com.lz.manage.model.domain.TaskProgressInfo;
import com.lz.manage.model.enums.DemandStatusEnum;
import com.lz.manage.model.enums.TaskStatusEnum;
import com.lz.manage.service.IClientDemandInfoService;
import com.lz.manage.service.IClientInfoService;
import com.lz.system.service.ISysDeptService;
import com.lz.system.service.ISysUserService;
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
public class TaskInfoServiceImpl extends ServiceImpl<TaskInfoMapper, TaskInfo> implements ITaskInfoService {
    @Resource
    private TaskInfoMapper taskInfoMapper;

    @Resource
    private IClientInfoService clientInfoService;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysDeptService deptService;

    @Resource
    private IClientDemandInfoService demandInfoService;

    @Resource
    private TaskProgressInfoMapper progressInfoMapper;

    //region mybatis代码

    /**
     * 查询任务信息
     *
     * @param id 任务信息主键
     * @return 任务信息
     */
    @Override
    public TaskInfo selectTaskInfoById(Long id) {
        return taskInfoMapper.selectTaskInfoById(id);
    }

    /**
     * 查询任务信息列表
     *
     * @param taskInfo 任务信息
     * @return 任务信息
     */
    @Override
    @DataScope(userAlias = "tb_task_info",deptAlias = "tb_task_info")
    public List<TaskInfo> selectTaskInfoList(TaskInfo taskInfo) {
        List<TaskInfo> taskInfos = taskInfoMapper.selectTaskInfoList(taskInfo);
        for (TaskInfo info : taskInfos) {
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
        }
        return taskInfos;
    }

    /**
     * 新增任务信息
     *
     * @param taskInfo 任务信息
     * @return 结果
     */
    @Override
    public int insertTaskInfo(TaskInfo taskInfo) {
        checkTaskInfo(taskInfo);
        taskInfo.setTaskStatus(TaskStatusEnum.TASK_STATUS_0.getValue());
        taskInfo.setCreateTime(DateUtils.getNowDate());
        return taskInfoMapper.insertTaskInfo(taskInfo);
    }

    private void checkTaskInfo(TaskInfo taskInfo) {
        ClientDemandInfo clientDemandInfo = demandInfoService.selectClientDemandInfoById(taskInfo.getDemandId());
        if (StringUtils.isNull(clientDemandInfo)) {
            throw new ServiceException("该需求不存在！！！");
        }
        if (clientDemandInfo.getDemandStatus().equals(DemandStatusEnum.DEMAND_STATUS_2.getValue())) {
            throw new ServiceException("该需求已完成，不能发布或者修改任务");
        }
        SysUser user = userService.selectUserById(clientDemandInfo.getUserId());
        if (StringUtils.isNull(user)) {
            throw new ServiceException("该装修人员不存在！！！");
        }
        taskInfo.setDeptId(clientDemandInfo.getDeptId());
        taskInfo.setClientId(clientDemandInfo.getClientId());
    }

    /**
     * 修改任务信息
     *
     * @param taskInfo 任务信息
     * @return 结果
     */
    @Override
    public int updateTaskInfo(TaskInfo taskInfo) {
        checkTaskInfo(taskInfo);
        taskInfo.setUpdateBy(SecurityUtils.getUsername());
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
    public int deleteTaskInfoByIds(Long[] ids) {
        //查询是否旗下有进度
        List<TaskProgressInfo> taskProgressInfos = progressInfoMapper.selectList(new LambdaQueryWrapper<TaskProgressInfo>().in(TaskProgressInfo::getTaskId,  ids));
        if (StringUtils.isNotEmpty(taskProgressInfos)) {
            throw new ServiceException("该任务下有进度，不能删除");
        }
        return taskInfoMapper.deleteTaskInfoByIds(ids);
    }

    /**
     * 删除任务信息信息
     *
     * @param id 任务信息主键
     * @return 结果
     */
    @Override
    public int deleteTaskInfoById(Long id) {
        return taskInfoMapper.deleteTaskInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<TaskInfo> getQueryWrapper(TaskInfoQuery taskInfoQuery) {
        QueryWrapper<TaskInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = taskInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = taskInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long clientId = taskInfoQuery.getClientId();
        queryWrapper.eq(StringUtils.isNotNull(clientId), "client_id", clientId);

        Long demandId = taskInfoQuery.getDemandId();
        queryWrapper.eq(StringUtils.isNotNull(demandId), "demand_id", demandId);

        String taskName = taskInfoQuery.getTaskName();
        queryWrapper.like(StringUtils.isNotEmpty(taskName), "task_name", taskName);

        String taskStatus = taskInfoQuery.getTaskStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(taskStatus), "task_status", taskStatus);

        Long userId = taskInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Long deptId = taskInfoQuery.getDeptId();
        queryWrapper.eq(StringUtils.isNotNull(deptId), "dept_id", deptId);

        Date createTime = taskInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        String updateBy = taskInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy), "update_by", updateBy);

        Date updateTime = taskInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime")) && StringUtils.isNotNull(params.get("endUpdateTime")), "update_time", params.get("beginUpdateTime"), params.get("endUpdateTime"));

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
