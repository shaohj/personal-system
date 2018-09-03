<form id="add_user_form" class="form-horizontal" role="form" onsubmit="return false;">
	<!-- 用户名  -->
	<div class="form-group">
		<label class="col-sm-2 control-label Validform_label" for="userName01" ><span>*</span>用户名</label>
		<div class="col-sm-6">
			<!-- ,可带数字、'_'、'.'的字串! -->
			<input class="form-control" id="userName01" name="userName" type="text" ajaxurl="${secContext}/sys/user/query/existName?access_token=${tokenValue}" datatype="s" maxlength="20" nullmsg="请填写用户名!" sucms="用户名输入正确!" errormsg="请输入不超过20个字符" placeholder="请输入用户名" />
		</div>
		<div class="col-sm-4"><div class="Validform_checktip"></div></div>
	</div>

	<!-- 密码 -->
	<div class="form-group">
		<label class="col-sm-2 control-label Validform_label" for="password01"><span>*</span>密码</label>
		<div class="col-sm-6">
			<input class="form-control" id="password01" name="password" type="password" datatype="*6-20" nullmsg="请填写密码!" sucms="密码输入正确!" errormsg="请输入6到20个字符!" placeholder="请输入密码!" value="" />
		</div>
		<div class="col-sm-4"><div class="Validform_checktip"></div></div>
	</div>
	
	<div class="form-group">
		<label class="col-sm-2 control-label Validform_label" ></label>
		<div class="col-sm-6" style="margin-top: -15px;">
			<a href="javascript:initPwd()" ><i class="fa fa-key"></i>初始化密码(123456)</a>
		</div>
	</div>
	
	<!-- 姓名 -->
	<div class="form-group">
		<label class="col-sm-2 control-label Validform_label" for="realName01"><span>*</span>姓名</label>
		<div class="col-sm-6">
			<input class="form-control" id="realName01" name="realName" datatype="s2-50" sucms="姓名输入正确!" nullmsg="请填写真实姓名!" errormsg="姓名格式不正确"  placeholder="请输入真实姓名" />
		</div>
		<div class="col-sm-4"><div class="Validform_checktip"></div></div>
	</div>
	
	<!-- 性别 -->
	<div class="form-group">
		<label class="col-sm-2 control-label Validform_label" >性别</label>
		<div class="col-sm-6">
			<!-- id格式为sex_(value),取后台值后拼字符可找到显示表单ID -->
			<label class="radio-inline"><input type="radio" name="sex" id="sex_01" value="1" checked />男&nbsp;&nbsp;</label>
			<label class="radio-inline"><input type="radio" name="sex" id="sex_02" value="2" />女&nbsp;&nbsp;</label>
		</div>
		<div class="col-sm-4"><div class="Validform_checktip"></div></div>
	</div>

	<!-- 生日 -->
	<div class="form-group">
		<label class="col-sm-2 control-label Validform_label" for="birthday01">生日</label>
		<div class="col-sm-6">
			<input type="text" id="birthday01" name="birthday" class="Wdate form-control time" onClick="WdatePicker()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
					placeholder="请选择日期" readOnly />
		</div>
		<div class="col-sm-4"><div class="Validform_checktip"></div></div>
	</div>
	
	<!-- 手机号码 -->
	<div class="form-group">
		<label class="col-sm-2 control-label Validform_label" for="mobilePhone01"><span>*</span>手机号码</label>
		<div class="col-sm-6">
			<input class="form-control" id="mobilePhone01" name="mobilePhone" datatype="m" nullmsg="请填写手机号码!" sucms="手机号码输入正确!" errormsg="手机号码格式不正确" placeholder="请输入手机号码" />
		</div>
		<div class="col-sm-4"><div class="Validform_checktip"></div></div>
	</div>
	
	<!-- 邮箱 -->
	<div class="form-group">
		<label class="col-sm-2 control-label Validform_label" for="email01"><span>*</span>邮箱</label>
		<div class="col-sm-6">
			<input class="form-control" id="email01" name="email" datatype="e" nullmsg="请填写邮箱!" sucms="邮箱输入正确!" errormsg="邮箱格式不正确" placeholder="请输入邮箱" />
		</div>
		<div class="col-sm-4"><div class="Validform_checktip"></div></div>
	</div>
	
	<!-- 成员组织-->
	<div class="form-group">
		<label class="col-sm-2 control-label Validform_label" ><span>*</span>成员组织</label>
		<div class="col-sm-6">
			<div class="tree" id="exTree" >
				<select id="addGroup" class="form-control" style="white-space:nowrap;text-overflow:ellipsis;overflow:hidden;" >
					<option selected style="display:none;"></option>
				</select>
				<div class="exTree" id="addGroupTree" style="display:none;margin-top:-184px;width:100%;height:150px;overflow-y: auto;overflow-x:hidden;border: 1px solid #66afe9;"></div>
			</div>
		</div>
		<div class="col-sm-4"><div class="Validform_checktip"></div></div>
	</div>
	
	<a id="submitAddUserFrom"></a>
	<a id="resetAddUserFrom"></a>
</form>

<script>
	//表单添加验证事件
	$("#add_user_form").Validform({
		btnSubmit:"#submitAddUserFrom",
		btnReset:"#resetAddUserFrom",
		ignoreHidden:true, //默认为false，当为true时对:hidden的表单元素将不做验证;
		beforeSubmit:function(curform){
			ajaxSubAddOrModUser("add_user_dialog", "add_user_form", "addGroupTree");
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