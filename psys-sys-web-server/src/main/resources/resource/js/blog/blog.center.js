var _saveOrUpdateBlogUrl = contextPath + "/blog/center/saveOrUpdate",
	_voteArticleUrl = contextPath + "/blog/center/voteArticle";

(function($){
	
	/** 初始化创建博客对话框插件 */
	$.fn.initAddBlogDialog = function(option){
		var $self = $(this);
		
		var $mngDialog = $("#blog_center_dialog_div"); //管理对话框的IDV
		var $thisDialog = $mngDialog.children("#add_blog_dialog"); //本次操作的对话框对象
		var $thisForm = $thisDialog.find("#add_blog_form"); //本次操作的form对象
		
		$thisDialog.dialog({
			bgiframe: true, resizable: false, width:650, modal: true, closeOnEscape:false, 
			open:function(event,ui){$(".ui-dialog-titlebar-close").hide();},
			autoOpen: false,
			overlay: {backgroundColor: '#000', opacity: 0.5, background: "black"},
			buttons:[{
				text:"保存", icons:{primary:"ui-icon-folder-collapsed"}, click:function(){
					$thisForm.find("#submitAddBlogFrom").click(); 
				}
			},{
				text:"取消", icons:{primary:"ui-icon-closethick"}, click:function(){
					$(this).dialog("close");
				}
			}],
		});
		
		$self.click(function(){
			resetAddBlogDialog();//阻止浏览器自动填充表单
			$thisDialog.dialog("open");
		})
		
	};
	
})(jQuery);

/** 异步增加或修改博客事件 */
function ajaxAddAndModLink(dialogId, formId){
	var $thisDialog = $("#" + dialogId); //本次操作的对话框对象
	var $thisForm = $thisDialog.find("#" + formId); //本次操作的form对象
	
	var obj = {
		title : $thisForm.find("[name='btitle']").val(),
		content : $thisForm.find("[name='bcontent']").val(),
		link : $thisForm.find("[name='blink']").val()
	};
	
	var requestUrl = appendUrlRandom(_saveOrUpdateBlogUrl);
	ajaxPost(requestUrl, obj, function(respData){
		$thisDialog.dialog("close");
		searchForPage();
		optMsgDialog("add", "success");
	}, function (){
		optMsgDialog("add", "fail");
	});
}

function voteArticle(artId){
	var obj = {
		artId : artId
	};

	var requestUrl = appendUrlRandom(_voteArticleUrl);
	ajaxPost(requestUrl, obj, function(respData){
		searchForPage();

		if(true === respData){
			optMsgDialog("vote", "success");
		} else {
			failDialog("文章已投票,不能再次投票");
		}
	}, function (respData){
        if(respData && respData.errorMsg){
            failDialog(respData.errorMsg);
        } else {
            optMsgDialog("vote", "fail");
        }
	});
}

/** 重置增加对话框内容 */
function resetAddBlogDialog(){
	var $thisForm = $("#add_blog_form");
	$thisForm.find("#resetAddBlogFrom").click(); 
	
	$thisForm.find("[name='btitle']").val("");
	$thisForm.find("[name='bcontent']").val("");
	$thisForm.find("[name='blink']").val("");
};