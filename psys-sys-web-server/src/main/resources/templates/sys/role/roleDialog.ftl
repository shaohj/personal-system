<div id="add_role_dialog" title="新增角色" style="display:none;">
	<form id="add_role_form" class="form-horizontal" role="form" onsubmit="return false;">
		<!-- 角色名:  -->
		<div class="form-group">
			<label class="col-sm-2 control-label Validform_label" for="role_name"><span>*</span>角色名:</label>
			<div class="col-sm-6">
				<input class="form-control" id="role_name" name="role_name" type="text" ajaxurl="${secContext}/sys/role/query/existName?access_token=${tokenValue}" datatype="s" maxlength="20" nullmsg="请填写角色名!" sucms="角色名输入正确!" errormsg="请输入不超过20个字符!"  placeholder="请输入角色名" >
			</div>
			<div class="col-sm-4">
				<div class="Validform_checktip"></div>
			</div>
		</div>
		
		<!-- 描述: -->	
		<div class="form-group">
			<label class="col-sm-2 control-label" for="role_desc" >描述:</label>
			<div class="col-sm-6">
				<input class="form-control" id="role_desc" type="text" placeholder="请输入角色描述" />
			</div>
			<div class="col-sm-4">
				<div class="Validform_checktip"></div>
			</div>
		</div>
		
		<!-- 加载所有权限列表 -->
		<div class="form-group">
			<label class="col-sm-2 control-label" for="role_desc">资源授权:</label>
			<div class="col-sm-8" >
				<div class="tree" id="exTree" >
					<div class="exTree" id="addResTree" style="width:100%; height:225px;overflow-y: auto;overflow-x:hidden;border:1px solid #ddd;"></div>
				</div>
			</div>
			<div class="col-sm-2">
				<div class="Validform_checktip"></div>
			</div>
		</div>
		
		<a id="submitAddRoleFrom"></a>
		<a id="resetAddRoleFrom"></a>
	</form>
</div>

<div id="mod_role_dialog" title="修改角色" style="display:none;">
	<form id="mod_role_form" class="form-horizontal" role="form" onsubmit="return false;">
		<!-- 角色名:  -->
		<div class="form-group">
			<label class="col-sm-2 control-label Validform_label" for="role_name"><span>*</span>角色名:</label>
			<div class="col-sm-6">
				<input type="hidden" id="roleId" />
				<input type="hidden" id="roleCode" />
				<input type="hidden" id="originalName" />
				<input class="form-control" id="role_name" type="text" ajaxurl="${secContext}/sys/role/query/existName?access_token=${tokenValue}" needSend="originalName" datatype="s" maxlength="20" nullmsg="请填写角色名!" sucms="角色名输入正确!" errormsg="请输入3到20个字符!"  placeholder="请输入角色名" />
			</div>
			<div class="col-sm-4"><div class="Validform_checktip"></div></div>
		</div>
		
		<!-- 描述: -->	
		<div class="form-group">
			<label class="col-sm-2 control-label" for="role_desc" >描述:</label>
			<div class="col-sm-6">
				<input class="form-control" id="role_desc" type="text" placeholder="请输入角色描述" />
			</div>
			<div class="col-sm-4"><div class="Validform_checktip"></div></div>
		</div>
		
		<!-- 加载所有权限列表 -->
		<div class="form-group">
			<label class="col-sm-2 control-label" for="role_desc">资源授权:</label>
			<div class="col-sm-8" >
				<div class="tree" id="exTree" >
					<div class="exTree" id="modResTree" style="width:100%; height:225px;overflow-y: auto;overflow-x:hidden;border:1px solid #ddd;"></div>
				</div>
			</div>
			<div class="col-sm-2"> <div class="Validform_checktip"></div></div>
		</div>
		
		<a id="submitModRoleFrom"></a>
		<a id="resetModRoleFrom"></a>
	</form>
</div>

<script>
	//表单添加验证事件
	$("#add_role_form").Validform({
			btnSubmit:"#submitAddRoleFrom",
			btnReset:"#resetAddRoleFrom",
			ignoreHidden:true, //默认为false，当为true时对:hidden的表单元素将不做验证;
			beforeSubmit:function(curform){
				ajaxAddAndModRole("add", "addResTree");
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
		
	//表单添加验证事件
	$("#mod_role_form").Validform({
			btnSubmit:"#submitModRoleFrom",
			btnReset:"#resetModRoleFrom",
			ignoreHidden:true, //默认为false，当为true时对:hidden的表单元素将不做验证;
			beforeSubmit:function(curform){
				ajaxAddAndModRole("mod", "modResTree");
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