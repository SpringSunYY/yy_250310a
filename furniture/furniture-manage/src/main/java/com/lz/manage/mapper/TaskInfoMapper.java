package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.TaskInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 任务信息Mapper接口
 * 
 * @author YY
 * @date 2025-03-11
 */
public interface TaskInfoMapper extends BaseMapper<TaskInfo>
{
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
     * 删除任务信息
     * 
     * @param id 任务信息主键
     * @return 结果
     */
    public int deleteTaskInfoById(Long id);

    /**
     * 批量删除任务信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTaskInfoByIds(Long[] ids);
}
