/**
 * 设置DOM元素显示还是隐藏
 * @param elementId 元素ID
 * @param flag true,显示;其他,隐藏
 */
function toggleDomElementDisplay(elementId, flag){
	if(flag && flag == true){
		$("#" + elementId).css("display","block");
	}else{
		$("#" + elementId).css("display","none");
	}
}

//单选复选框切换checked
function changeInputChecked(obj){
	var checkedVal = $(obj).is(':checked');
	$(obj).attr("checked",checkedVal);
}