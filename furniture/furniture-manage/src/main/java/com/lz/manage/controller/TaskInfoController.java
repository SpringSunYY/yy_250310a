package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lz.common.annotation.Log;
import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.common.enums.BusinessType;
import com.lz.manage.model.domain.TaskInfo;
import com.lz.manage.model.vo.taskInfo.TaskInfoVo;
import com.lz.manage.model.dto.taskInfo.TaskInfoQuery;
import com.lz.manage.model.dto.taskInfo.TaskInfoInsert;
import com.lz.manage.model.dto.taskInfo.TaskInfoEdit;
import com.lz.manage.service.ITaskInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 任务信息Controller
 *
 * @author YY
 * @date 2025-03-11
 */
@RestController
@RequestMapping("/manage/taskInfo")
public class TaskInfoController extends BaseController
{
    @Resource
    private ITaskInfoService taskInfoService;

    /**
     * 查询任务信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:taskInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(TaskInfoQuery taskInfoQuery)
    {
        TaskInfo taskInfo = TaskInfoQuery.queryToObj(taskInfoQuery);
        startPage();
        List<TaskInfo> list = taskInfoService.selectTaskInfoList(taskInfo);
        List<TaskInfoVo> listVo= list.stream().map(TaskInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出任务信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:taskInfo:export')")
    @Log(title = "任务信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TaskInfoQuery taskInfoQuery)
    {
        TaskInfo taskInfo = TaskInfoQuery.queryToObj(taskInfoQuery);
        List<TaskInfo> list = taskInfoService.selectTaskInfoList(taskInfo);
        ExcelUtil<TaskInfo> util = new ExcelUtil<TaskInfo>(TaskInfo.class);
        util.exportExcel(response, list, "任务信息数据");
    }

    /**
     * 获取任务信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:taskInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        TaskInfo taskInfo = taskInfoService.selectTaskInfoById(id);
        return success(TaskInfoVo.objToVo(taskInfo));
    }

    /**
     * 新增任务信息
     */
    @PreAuthorize("@ss.hasPermi('manage:taskInfo:add')")
    @Log(title = "任务信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TaskInfoInsert taskInfoInsert)
    {
        TaskInfo taskInfo = TaskInfoInsert.insertToObj(taskInfoInsert);
        return toAjax(taskInfoService.insertTaskInfo(taskInfo));
    }

    /**
     * 修改任务信息
     */
    @PreAuthorize("@ss.hasPermi('manage:taskInfo:edit')")
    @Log(title = "任务信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TaskInfoEdit taskInfoEdit)
    {
        TaskInfo taskInfo = TaskInfoEdit.editToObj(taskInfoEdit);
        return toAjax(taskInfoService.updateTaskInfo(taskInfo));
    }

    /**
     * 删除任务信息
     */
    @PreAuthorize("@ss.hasPermi('manage:taskInfo:remove')")
    @Log(title = "任务信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(taskInfoService.deleteTaskInfoByIds(ids));
    }
}
