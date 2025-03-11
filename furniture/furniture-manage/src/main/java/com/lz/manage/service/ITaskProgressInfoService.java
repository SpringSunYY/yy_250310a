package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.TaskProgressInfo;
import com.lz.manage.model.vo.taskProgressInfo.TaskProgressInfoVo;
import com.lz.manage.model.dto.taskProgressInfo.TaskProgressInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 任务进度Service接口
 * 
 * @author YY
 * @date 2025-03-11
 */
public interface ITaskProgressInfoService extends IService<TaskProgressInfo>
{
    //region mybatis代码
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
     * 批量删除任务进度
     * 
     * @param ids 需要删除的任务进度主键集合
     * @return 结果
     */
    public int deleteTaskProgressInfoByIds(Long[] ids);

    /**
     * 删除任务进度信息
     * 
     * @param id 任务进度主键
     * @return 结果
     */
    public int deleteTaskProgressInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param taskProgressInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<TaskProgressInfo> getQueryWrapper(TaskProgressInfoQuery taskProgressInfoQuery);

    /**
     * 转换vo
     *
     * @param taskProgressInfoList TaskProgressInfo集合
     * @return TaskProgressInfoVO集合
     */
    List<TaskProgressInfoVo> convertVoList(List<TaskProgressInfo> taskProgressInfoList);
}
