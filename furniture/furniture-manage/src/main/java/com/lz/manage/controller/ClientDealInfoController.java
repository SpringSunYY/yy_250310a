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
import com.lz.manage.model.domain.ClientDealInfo;
import com.lz.manage.model.vo.clientDealInfo.ClientDealInfoVo;
import com.lz.manage.model.dto.clientDealInfo.ClientDealInfoQuery;
import com.lz.manage.model.dto.clientDealInfo.ClientDealInfoInsert;
import com.lz.manage.model.dto.clientDealInfo.ClientDealInfoEdit;
import com.lz.manage.service.IClientDealInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 客户交易Controller
 *
 * @author YY
 * @date 2025-03-11
 */
@RestController
@RequestMapping("/manage/clientDealInfo")
public class ClientDealInfoController extends BaseController
{
    @Resource
    private IClientDealInfoService clientDealInfoService;

    /**
     * 查询客户交易列表
     */
    @PreAuthorize("@ss.hasPermi('manage:clientDealInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(ClientDealInfoQuery clientDealInfoQuery)
    {
        ClientDealInfo clientDealInfo = ClientDealInfoQuery.queryToObj(clientDealInfoQuery);
        startPage();
        List<ClientDealInfo> list = clientDealInfoService.selectClientDealInfoList(clientDealInfo);
        List<ClientDealInfoVo> listVo= list.stream().map(ClientDealInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出客户交易列表
     */
    @PreAuthorize("@ss.hasPermi('manage:clientDealInfo:export')")
    @Log(title = "客户交易", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ClientDealInfoQuery clientDealInfoQuery)
    {
        ClientDealInfo clientDealInfo = ClientDealInfoQuery.queryToObj(clientDealInfoQuery);
        List<ClientDealInfo> list = clientDealInfoService.selectClientDealInfoList(clientDealInfo);
        ExcelUtil<ClientDealInfo> util = new ExcelUtil<ClientDealInfo>(ClientDealInfo.class);
        util.exportExcel(response, list, "客户交易数据");
    }

    /**
     * 获取客户交易详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:clientDealInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        ClientDealInfo clientDealInfo = clientDealInfoService.selectClientDealInfoById(id);
        return success(ClientDealInfoVo.objToVo(clientDealInfo));
    }

    /**
     * 新增客户交易
     */
    @PreAuthorize("@ss.hasPermi('manage:clientDealInfo:add')")
    @Log(title = "客户交易", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ClientDealInfoInsert clientDealInfoInsert)
    {
        ClientDealInfo clientDealInfo = ClientDealInfoInsert.insertToObj(clientDealInfoInsert);
        return toAjax(clientDealInfoService.insertClientDealInfo(clientDealInfo));
    }

    /**
     * 修改客户交易
     */
    @PreAuthorize("@ss.hasPermi('manage:clientDealInfo:edit')")
    @Log(title = "客户交易", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ClientDealInfoEdit clientDealInfoEdit)
    {
        ClientDealInfo clientDealInfo = ClientDealInfoEdit.editToObj(clientDealInfoEdit);
        return toAjax(clientDealInfoService.updateClientDealInfo(clientDealInfo));
    }

    /**
     * 删除客户交易
     */
    @PreAuthorize("@ss.hasPermi('manage:clientDealInfo:remove')")
    @Log(title = "客户交易", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(clientDealInfoService.deleteClientDealInfoByIds(ids));
    }
}
