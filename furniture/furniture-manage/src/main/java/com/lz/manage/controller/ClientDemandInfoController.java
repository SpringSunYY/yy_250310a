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
import com.lz.manage.model.domain.ClientDemandInfo;
import com.lz.manage.model.vo.clientDemandInfo.ClientDemandInfoVo;
import com.lz.manage.model.dto.clientDemandInfo.ClientDemandInfoQuery;
import com.lz.manage.model.dto.clientDemandInfo.ClientDemandInfoInsert;
import com.lz.manage.model.dto.clientDemandInfo.ClientDemandInfoEdit;
import com.lz.manage.service.IClientDemandInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 客户需求Controller
 *
 * @author YY
 * @date 2025-03-11
 */
@RestController
@RequestMapping("/manage/clientDemandInfo")
public class ClientDemandInfoController extends BaseController
{
    @Resource
    private IClientDemandInfoService clientDemandInfoService;

    /**
     * 查询客户需求列表
     */
    @PreAuthorize("@ss.hasAnyPermi('manage:clientDemandInfo:list,manage:clientDemandInfo:query')")
    @GetMapping("/list")
    public TableDataInfo list(ClientDemandInfoQuery clientDemandInfoQuery)
    {
        ClientDemandInfo clientDemandInfo = ClientDemandInfoQuery.queryToObj(clientDemandInfoQuery);
        startPage();
        List<ClientDemandInfo> list = clientDemandInfoService.selectClientDemandInfoList(clientDemandInfo);
        List<ClientDemandInfoVo> listVo= list.stream().map(ClientDemandInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出客户需求列表
     */
    @PreAuthorize("@ss.hasPermi('manage:clientDemandInfo:export')")
    @Log(title = "客户需求", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ClientDemandInfoQuery clientDemandInfoQuery)
    {
        ClientDemandInfo clientDemandInfo = ClientDemandInfoQuery.queryToObj(clientDemandInfoQuery);
        List<ClientDemandInfo> list = clientDemandInfoService.selectClientDemandInfoList(clientDemandInfo);
        ExcelUtil<ClientDemandInfo> util = new ExcelUtil<ClientDemandInfo>(ClientDemandInfo.class);
        util.exportExcel(response, list, "客户需求数据");
    }

    /**
     * 获取客户需求详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:clientDemandInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        ClientDemandInfo clientDemandInfo = clientDemandInfoService.selectClientDemandInfoById(id);
        return success(ClientDemandInfoVo.objToVo(clientDemandInfo));
    }

    /**
     * 新增客户需求
     */
    @PreAuthorize("@ss.hasPermi('manage:clientDemandInfo:add')")
    @Log(title = "客户需求", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ClientDemandInfoInsert clientDemandInfoInsert)
    {
        ClientDemandInfo clientDemandInfo = ClientDemandInfoInsert.insertToObj(clientDemandInfoInsert);
        return toAjax(clientDemandInfoService.insertClientDemandInfo(clientDemandInfo));
    }

    /**
     * 修改客户需求
     */
    @PreAuthorize("@ss.hasPermi('manage:clientDemandInfo:edit')")
    @Log(title = "客户需求", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ClientDemandInfoEdit clientDemandInfoEdit)
    {
        ClientDemandInfo clientDemandInfo = ClientDemandInfoEdit.editToObj(clientDemandInfoEdit);
        return toAjax(clientDemandInfoService.updateClientDemandInfo(clientDemandInfo));
    }

    /**
     * 删除客户需求
     */
    @PreAuthorize("@ss.hasPermi('manage:clientDemandInfo:remove')")
    @Log(title = "客户需求", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(clientDemandInfoService.deleteClientDemandInfoByIds(ids));
    }
}
