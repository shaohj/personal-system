<table class="table display dataTable" id="datatable" aria-describedby="datatable_info">
    <thead>
    <tr role="row">
        <th style="width:3%;" role="columnheader" aria-controls="datatable" rowspan="1" colspan="1" aria-label="全选"><input type="checkbox" /></th>
        <th style="width:15%;" role="columnheader" aria-controls="datatable" rowspan="1" colspan="1" aria-label="用户名">用户名</th>
        <th style="width:12%;" role="columnheader" aria-controls="datatable" rowspan="1" colspan="1" aria-label="姓名">姓名</th>
        <th style="width:18%;" role="columnheader" aria-controls="datatable" rowspan="1" colspan="1" aria-label="邮箱">邮箱</th>
        <th style="width:12%;" role="columnheader" aria-controls="datatable" rowspan="1" colspan="1" aria-label="手机">手机</th>
        <th style="width:13%;" role="columnheader" aria-controls="datatable" rowspan="1" colspan="1" aria-label="组织">组织</th>
        <th style="width:22%;" role="columnheader" aria-controls="datatable" rowspan="1" colspan="1" aria-label="用户角色">用户角色</th>
        <th style="width:5%;" role="columnheader" aria-controls="datatable" rowspan="1" colspan="1" aria-label="状态">状态</th>
    </tr>
    </thead>
    <tbody role="alert" aria-live="polite" aria-relevant="all">
    <#if pageResult.content?exists>
        <#list pageResult.content as user>
            <tr for="ck_userid_${user_index}" class="gradeA <#if user_index%2 == 0>odd<#else >even</#if> " >
                <td class="  "><input type="checkbox" id="ck_userid_${user_index}" name="ck_userid"
                                      value="${user.userId?default('')}|${user.userName?default('')}|${user.isAdmin?default('N')}|${user.status?default('')}" /></td>
                <td class=" " title='${user.userName?default("")}' >${user.userName?default("")}</td>
                <td class=" " title='${user.realName?default("")}'>${user.realName?default("")}</td>
                <td class=" " title='${user.email?default("")}'>${user.email?default("")}</td>
                <td class=" ">${user.mobile?default("")}</td>

                <td class=" " title='${user.groupNames?default("")}'>${user.groupNames?default("")}</td>
                <td class=" " title='${user.roleNames?default("")}'>${user.roleNames?default("")}</td>
                <td class=" " title='${user.showStatus?default("")}'>${user.showStatus?default("")}</td>
            </tr>
		</#list>
	</#if>
    </tbody>
</table>
<#if pageResult.content?size == 0>
	<div class="common-text-size14"><span>未查询到符合条件的记录</span></div>
</#if>
<#include "../../include/page_tool.ftl" encoding="UTF-8"/>