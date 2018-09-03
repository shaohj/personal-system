<table class="table display dataTable" id="datatable" aria-describedby="datatable_info">
	<thead>
	<tr role="row">
		<th style="width:15%;" role="columnheader" aria-controls="datatable" rowspan="1" colspan="1" aria-label="标题">标题</th>
		<th style="width:20%;" role="columnheader" aria-controls="datatable" rowspan="1" colspan="1" aria-label="链接">链接</th>
		<th style="width:10%;" role="columnheader" aria-controls="datatable" rowspan="1" colspan="1" aria-label="投票数">投票数</th>
		<th style="width:15%;" role="columnheader" aria-controls="datatable" rowspan="1" colspan="1" aria-label="创建人">创建人</th>
		<th style="width:20%;" role="columnheader" aria-controls="datatable" rowspan="1" colspan="1" aria-label="创建时间">创建时间</th>
		<th style="width:10%;" role="columnheader" aria-controls="datatable" rowspan="1" colspan="1" aria-label="操作">操作</th>
	</tr>
	</thead>
	<tbody role="alert" aria-live="polite" aria-relevant="all">
	<#if pageResult.content?exists>
		<#list pageResult.content as blog>
		<tr for="ck_blogId_${blog_index}" class="gradeA <#if blog_index%2 == 0>odd<#else >even</#if> " >
			<td title="${blog.title?default('')}" class="ellipsis">${blog.title?default("")}</td>
			<td >${blog.link?default("")}</td>
			<td class="c">${blog.votes?default("")}</td>
			<td class="c">${blog.poster?default("")}</td>
			<td class="c">${blog.ctimeShow?default("")}</td>
			<td class="c"><a class="button-a" onclick="voteArticle(${blog.aid?default('')});">投票</a></td>
		</tr>
		</#list>
	</#if>
	</tbody>
</table>
<#if pageResult.content?size == 0>
	<div class="common-text-size14"><span>未查询到符合条件的记录</span></div>
</#if>
<#include "../../include/page_tool.ftl" encoding="UTF-8"/>

