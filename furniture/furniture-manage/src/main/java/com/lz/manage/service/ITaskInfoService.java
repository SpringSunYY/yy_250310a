package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.TaskInfo;
import com.lz.manage.model.vo.taskInfo.TaskInfoVo;
import com.lz.manage.model.dto.taskInfo.TaskInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 任务信息Service接口
 * 
 * @author YY
 * @date 2025-03-11
 */
public interface ITaskInfoService extends IService<TaskInfo>
{
    //region mybatis代码
    /**
     * 查询任务信息
     * 
     * @param id 任务信息主键
     * @return 任务信息
     */
    public TaskInfo selectTaskInfoById(Long id);

    /**
     * 查询任务信息列表
     * 
     * @param taskInfo 任务信息
     * @return 任务信息集合
     */
    public List<TaskInfo> selectTaskInfoList(TaskInfo taskInfo);

    /**
     * 新增任务信息
     * 
     * @param taskInfo 任务信息
     * @return 结果
     */
    public int insertTaskInfo(TaskInfo taskInfo);

    /**
     * 修改任务信息
     * 
     * @param taskInfo 任务信息
     * @return 结果
     */
    public int updateTaskInfo(TaskInfo taskInfo);

    /**
     * 批量删除任务信息
     * 
     * @param ids 需要删除的任务信息主键集合
     * @return 结果
     */
    public int deleteTaskInfoByIds(Long[] ids);

    /**
     * 删除任务信息信息
     * 
     * @param id 任务信息主键
     * @return 结果
     */
    public int deleteTaskInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param taskInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<TaskInfo> getQueryWrapper(TaskInfoQuery taskInfoQuery);

    /**
     * 转换vo
     *
     * @param taskInfoList TaskInfo集合
     * @return TaskInfoVO集合
     */
    List<TaskInfoVo> convertVoList(List<TaskInfo> taskInfoList);
}
