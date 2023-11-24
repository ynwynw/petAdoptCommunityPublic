package com.ruoyi.adopt.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.adopt.domain.AnswerPrize;
import com.ruoyi.adopt.service.IAnswerPrizeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 答题Controller
 * 
 * @author ruoyi
 * @date 2022-02-22
 */
@RestController
@RequestMapping("/adopt/prize")
public class AnswerPrizeController extends BaseController
{
    @Autowired
    private IAnswerPrizeService answerPrizeService;

    /**
     * 查询答题列表
     */
    @PreAuthorize("@ss.hasPermi('adopt:prize:list')")
    @GetMapping("/list")
    public TableDataInfo list(AnswerPrize answerPrize)
    {
        startPage();
        List<AnswerPrize> list = answerPrizeService.selectAnswerPrizeList(answerPrize);
        return getDataTable(list);
    }

    /**
     * 导出答题列表
     */
    @PreAuthorize("@ss.hasPermi('adopt:prize:export')")
    @Log(title = "答题", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AnswerPrize answerPrize)
    {
        List<AnswerPrize> list = answerPrizeService.selectAnswerPrizeList(answerPrize);
        ExcelUtil<AnswerPrize> util = new ExcelUtil<AnswerPrize>(AnswerPrize.class);
        util.exportExcel(response, list, "答题数据");
    }

    /**
     * 获取答题详细信息
     */
    @PreAuthorize("@ss.hasPermi('adopt:prize:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(answerPrizeService.selectAnswerPrizeById(id));
    }

    /**
     * 新增答题
     */
    @PreAuthorize("@ss.hasPermi('adopt:prize:add')")
    @Log(title = "答题", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AnswerPrize answerPrize)
    {
        return toAjax(answerPrizeService.insertAnswerPrize(answerPrize));
    }

    /**
     * 修改答题
     */
    @PreAuthorize("@ss.hasPermi('adopt:prize:edit')")
    @Log(title = "答题", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AnswerPrize answerPrize)
    {
        return toAjax(answerPrizeService.updateAnswerPrize(answerPrize));
    }

    /**
     * 删除答题
     */
    @PreAuthorize("@ss.hasPermi('adopt:prize:remove')")
    @Log(title = "答题", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(answerPrizeService.deleteAnswerPrizeByIds(ids));
    }
}
