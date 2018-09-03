<form id="grant_userRole_form" class="form-horizontal" role="form" onsubmit="return false;">
	<input id="grantUserId" type="hidden" />
	
	<#if roleList?exists>
		<table style="table-layout:fixed;width:100%">
		<#list roleList as role>
			<#if role_index % 4 == 0 >
				<tr>
			</#if>
				<td title="${role.name}" style="white-space:nowrap;text-overflow:ellipsis;overflow:hidden;">
					<label >
						<#if role.isChecked != "false">
								<input type="checkbox" id="roleItem_${role_index}" name="roleItem" onclick="changeInputChecked(this);" 
										value="${role.roleCode}" checked="checked" />${role.name}
						</#if>
						<#if role.isChecked == "false">
								<input type="checkbox" id="roleItem_${role_index}" name="roleItem" onclick="changeInputChecked(this);" 
										value="${role.roleCode}" />${role.name}
						</#if>
					</label>
				</td>
			<#if role_index % 4 == 3 >
				</tr>
			</#if>
		</#list>
		
		<#assign divEnd>${roleList?size%4}</#assign>	
		<#if divEnd!="0">
			</div>
		</#if>
		</table>
	</#if>

	<a id="submitGrantUserRoleFrom"></a>
	<a id="resetGrantUserRoleFrom"></a>
</form>
<script>
	//表单添加验证事件
	$("#grant_userRole_form").Validform({
		btnSubmit:"#submitGrantUserRoleFrom",
		btnReset:"#resetGrantUserRoleFrom",
		ignoreHidden:true, //默认为false，当为true时对:hidden的表单元素将不做验证;
		beforeSubmit:function(curform){
			ajaxSubGrantUserRole();
			return false;
		}, 
		tiptype:function(msg,o,cssctl){
			if(!o.obj.is("form")){//验证表单元素时o.obj为该表单元素，全部验证通过提交表单时o.obj为该表单对象;
				var objtip=o.obj.parents().siblings().children(".Validform_checktip");
				cssctl(objtip,o.type);
				objtip.text(msg);
			}
		}
	});
</script>
