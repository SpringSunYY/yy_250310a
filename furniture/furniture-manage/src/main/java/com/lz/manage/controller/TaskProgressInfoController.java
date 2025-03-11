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
import com.lz.manage.model.domain.TaskProgressInfo;
import com.lz.manage.model.vo.taskProgressInfo.TaskProgressInfoVo;
import com.lz.manage.model.dto.taskProgressInfo.TaskProgressInfoQuery;
import com.lz.manage.model.dto.taskProgressInfo.TaskProgressInfoInsert;
import com.lz.manage.model.dto.taskProgressInfo.TaskProgressInfoEdit;
import com.lz.manage.service.ITaskProgressInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 任务进度Controller
 *
 * @author YY
 * @date 2025-03-11
 */
@RestController
@RequestMapping("/manage/taskProgressInfo")
public class TaskProgressInfoController extends BaseController
{
    @Resource
    private ITaskProgressInfoService taskProgressInfoService;

    /**
     * 查询任务进度列表
     */
    @PreAuthorize("@ss.hasPermi('manage:taskProgressInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(TaskProgressInfoQuery taskProgressInfoQuery)
    {
        TaskProgressInfo taskProgressInfo = TaskProgressInfoQuery.queryToObj(taskProgressInfoQuery);
        startPage();
        List<TaskProgressInfo> list = taskProgressInfoService.selectTaskProgressInfoList(taskProgressInfo);
        List<TaskProgressInfoVo> listVo= list.stream().map(TaskProgressInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出任务进度列表
     */
    @PreAuthorize("@ss.hasPermi('manage:taskProgressInfo:export')")
    @Log(title = "任务进度", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TaskProgressInfoQuery taskProgressInfoQuery)
    {
        TaskProgressInfo taskProgressInfo = TaskProgressInfoQuery.queryToObj(taskProgressInfoQuery);
        List<TaskProgressInfo> list = taskProgressInfoService.selectTaskProgressInfoList(taskProgressInfo);
        ExcelUtil<TaskProgressInfo> util = new ExcelUtil<TaskProgressInfo>(TaskProgressInfo.class);
        util.exportExcel(response, list, "任务进度数据");
    }

    /**
     * 获取任务进度详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:taskProgressInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        TaskProgressInfo taskProgressInfo = taskProgressInfoService.selectTaskProgressInfoById(id);
        return success(TaskProgressInfoVo.objToVo(taskProgressInfo));
    }

    /**
     * 新增任务进度
     */
    @PreAuthorize("@ss.hasPermi('manage:taskProgressInfo:add')")
    @Log(title = "任务进度", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TaskProgressInfoInsert taskProgressInfoInsert)
    {
        TaskProgressInfo taskProgressInfo = TaskProgressInfoInsert.insertToObj(taskProgressInfoInsert);
        return toAjax(taskProgressInfoService.insertTaskProgressInfo(taskProgressInfo));
    }

    /**
     * 修改任务进度
     */
    @PreAuthorize("@ss.hasPermi('manage:taskProgressInfo:edit')")
    @Log(title = "任务进度", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TaskProgressInfoEdit taskProgressInfoEdit)
    {
        TaskProgressInfo taskProgressInfo = TaskProgressInfoEdit.editToObj(taskProgressInfoEdit);
        return toAjax(taskProgressInfoService.updateTaskProgressInfo(taskProgressInfo));
    }

    /**
     * 删除任务进度
     */
    @PreAuthorize("@ss.hasPermi('manage:taskProgressInfo:remove')")
    @Log(title = "任务进度", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(taskProgressInfoService.deleteTaskProgressInfoByIds(ids));
    }
}
