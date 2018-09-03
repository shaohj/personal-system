<table class="table display dataTable" id="datatable" aria-describedby="datatable_info">
    <thead>
    <tr role="row">
        <th style="width:3%;" role="columnheader" aria-controls="datatable" rowspan="1" colspan="1" aria-label="全选"><input type="checkbox" /></th>
        <th style="width:20%;" role="columnheader" aria-controls="datatable" rowspan="1" colspan="1" aria-label="群组名">群组名</th>
        <th style="width:20%;" role="columnheader" aria-controls="datatable" rowspan="1" colspan="1" aria-label="描述">描述</th>
        <th style="width:35%;" role="columnheader" aria-controls="datatable" rowspan="1" colspan="1" aria-label="相关博客">相关博客</th>
        <th style="width:25%;" role="columnheader" aria-controls="datatable" rowspan="1" colspan="1" aria-label="创建时间">创建时间</th>
    </tr>
    </thead>
    <tbody role="alert" aria-live="polite" aria-relevant="all">
    <#if pageResult.content?exists>
        <#list pageResult.content as blog>
            <tr for="ck_blogId_${blog_index}" class="gradeA <#if blog_index%2 == 0>odd<#else >even</#if> " >
        <td class="  "><input type="checkbox" /></td>
        <td class="  "></td>
        <td class=" "></td>
        <td class=" "></td>
        <td class=" "></td>
            </tr>
        </#list>
    </#if>
    </tbody>
</table>
<#if pageResult.content?size == 0>
	<div class="common-text-size14"><span>未查询到符合条件的记录</span></div>
</#if>
<#include "../../include/page_tool.ftl" encoding="UTF-8"/>