/**
 * 自定义select+DIV插件
 */
(function($) {
	
	//判断:当前元素是否是被筛选元素的子元素
	function isChildOf($obj, b){
		return ($obj.parents(b).length > 0); 
	}
	
	// 当前元素是否是被筛选元素的子元素或者本身 
	function isChildAndSelfOf($obj, b){
		return ($obj.closest(b).length > 0); 
	}
	
	$.fn.updateSelectDiv = function(options) {
		options = options || {};
		var $this = $(this);
		var objId = $this.attr('id');
		var selectDivId = options.selectDivId;
		var $selectDiv = $("#"+selectDivId);
		$("body").click(function(event){
			var $target = $(event.target); 
			if($target.attr('id') == objId){
				$selectDiv.css("display",$selectDiv.css("display") == "none" ? "" : "none");
			}else if($target.attr('id') != objId){
				var isChild = isChildOf($(event.target), "#"+selectDivId);
				if(!isChild){  
					$("#"+selectDivId).hide();    
				}  
			}
		}); 
	};
})(jQuery);