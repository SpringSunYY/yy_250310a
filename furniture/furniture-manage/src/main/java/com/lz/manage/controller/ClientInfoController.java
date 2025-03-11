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
import com.lz.manage.model.domain.ClientInfo;
import com.lz.manage.model.vo.clientInfo.ClientInfoVo;
import com.lz.manage.model.dto.clientInfo.ClientInfoQuery;
import com.lz.manage.model.dto.clientInfo.ClientInfoInsert;
import com.lz.manage.model.dto.clientInfo.ClientInfoEdit;
import com.lz.manage.service.IClientInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 客户信息Controller
 *
 * @author YY
 * @date 2025-03-11
 */
@RestController
@RequestMapping("/manage/clientInfo")
public class ClientInfoController extends BaseController
{
    @Resource
    private IClientInfoService clientInfoService;

    /**
     * 查询客户信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:clientInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(ClientInfoQuery clientInfoQuery)
    {
        ClientInfo clientInfo = ClientInfoQuery.queryToObj(clientInfoQuery);
        startPage();
        List<ClientInfo> list = clientInfoService.selectClientInfoList(clientInfo);
        List<ClientInfoVo> listVo= list.stream().map(ClientInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出客户信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:clientInfo:export')")
    @Log(title = "客户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ClientInfoQuery clientInfoQuery)
    {
        ClientInfo clientInfo = ClientInfoQuery.queryToObj(clientInfoQuery);
        List<ClientInfo> list = clientInfoService.selectClientInfoList(clientInfo);
        ExcelUtil<ClientInfo> util = new ExcelUtil<ClientInfo>(ClientInfo.class);
        util.exportExcel(response, list, "客户信息数据");
    }

    /**
     * 获取客户信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:clientInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        ClientInfo clientInfo = clientInfoService.selectClientInfoById(id);
        return success(ClientInfoVo.objToVo(clientInfo));
    }

    /**
     * 新增客户信息
     */
    @PreAuthorize("@ss.hasPermi('manage:clientInfo:add')")
    @Log(title = "客户信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ClientInfoInsert clientInfoInsert)
    {
        ClientInfo clientInfo = ClientInfoInsert.insertToObj(clientInfoInsert);
        return toAjax(clientInfoService.insertClientInfo(clientInfo));
    }

    /**
     * 修改客户信息
     */
    @PreAuthorize("@ss.hasPermi('manage:clientInfo:edit')")
    @Log(title = "客户信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ClientInfoEdit clientInfoEdit)
    {
        ClientInfo clientInfo = ClientInfoEdit.editToObj(clientInfoEdit);
        return toAjax(clientInfoService.updateClientInfo(clientInfo));
    }

    /**
     * 删除客户信息
     */
    @PreAuthorize("@ss.hasPermi('manage:clientInfo:remove')")
    @Log(title = "客户信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(clientInfoService.deleteClientInfoByIds(ids));
    }
}
