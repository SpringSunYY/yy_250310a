package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.TaskProgressInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 任务进度Mapper接口
 * 
 * @author YY
 * @date 2025-03-11
 */
public interface TaskProgressInfoMapper extends BaseMapper<TaskProgressInfo>
{
    /**
     * 查询任务进度
     * 
     * @param id 任务进度主键
     * @return 任务进度
     */
    public TaskProgressInfo selectTaskProgressInfoById(Long id);

    /**
     * 查询任务进度列表
     * 
     * @param taskProgressInfo 任务进度
     * @return 任务进度集合
     */
    public List<TaskProgressInfo> selectTaskProgressInfoList(TaskProgressInfo taskProgressInfo);

    /**
     * 新增任务进度
     * 
     * @param taskProgressInfo 任务进度
     * @return 结果
     */
    public int insertTaskProgressInfo(TaskProgressInfo taskProgressInfo);

    /**
     * 修改任务进度
     * 
     * @param taskProgressInfo 任务进度
     * @return 结果
     */
    public int updateTaskProgressInfo(TaskProgressInfo taskProgressInfo);

    /**
     * 删除任务进度
     * 
     * @param id 任务进度主键
     * @return 结果
     */
    public int deleteTaskProgressInfoById(Long id);

    /**
     * 批量删除任务进度
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTaskProgressInfoByIds(Long[] ids);
}
