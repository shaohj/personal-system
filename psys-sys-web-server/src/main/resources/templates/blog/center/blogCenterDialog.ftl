<div id="add_blog_dialog" title="新增博客" style="display:none;">
	<form id="add_blog_form" class="form-horizontal" role="form" onsubmit="return false;">
		<!-- 标题:  -->
		<div class="form-group">
			<label class="col-sm-2 control-label Validform_label" for="btitle01"><span>*</span>标题:</label>
			<div class="col-sm-6">
				<input class="form-control" id="btitle01" name="btitle" type="text" datatype="s" maxlength="50" nullmsg="请填写标题!" sucms="标题输入正确!" errormsg="请输入不超过50个字符!"  placeholder="请输入标题" >
			</div>
			<div class="col-sm-4">
				<div class="Validform_checktip"></div>
			</div>
		</div>
		
		<!-- 内容: -->	
		<div class="form-group">
			<label class="col-sm-2 control-label" for="bcontent01" >内容:</label>
			<div class="col-sm-6">
				<textarea id="bcontent01" name="bcontent" class="form-control" rows="3"></textarea>
			</div>
			<div class="col-sm-4">
				<div class="Validform_checktip"></div>
			</div>
		</div>
		
		<div class="form-group">
			<label class="col-sm-2 control-label" for="blink01" >链接:</label>
			<div class="col-sm-6">
				<input id="blink01" name="blink" class="form-control" type="text" placeholder="请输入链接" />
			</div>
			<div class="col-sm-4">
				<div class="Validform_checktip"></div>
			</div>
		</div>
		
		<a id="submitAddBlogFrom"></a>
		<a id="resetAddBlogFrom"></a>
	</form>
</div>

<script>
	//表单添加验证事件
	$("#add_blog_form").Validform({
			btnSubmit:"#submitAddBlogFrom",
			btnReset:"#resetAddBlogFrom",
			ignoreHidden:true, //默认为false，当为true时对:hidden的表单元素将不做验证;
			beforeSubmit:function(curform){
				ajaxAddAndModLink("add_blog_dialog", "add_blog_form");
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