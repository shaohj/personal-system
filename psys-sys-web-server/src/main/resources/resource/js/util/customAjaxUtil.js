/**
 * 通用的get ajax请求
 * @param reqUrl
 * @param successCallBack
 * @param failCallBack
 * @returns
 */
function ajaxGet(reqUrl, successCallBack, failCallBack){
	if(!failCallBack){
		failCallBack = function (){
			optMsgDialog("get","fail");
		}
	}
	ajaxCommon("application/json; charset=UTF-8", "GET", reqUrl, "json", undefined, false, successCallBack, failCallBack);
}

/**
 * 通用的post ajax请求
 * @param reqUrl
 * @param data
 * @param successCallBack
 * @param failCallBack
 * @returns
 */
function ajaxPost(reqUrl, data, successCallBack, failCallBack){
	if(!failCallBack){
		failCallBack = function (){
			optMsgDialog("post","fail");
		}
	}
	ajaxCommon("application/json; charset=UTF-8", "POST", reqUrl, "json", data, false, successCallBack, failCallBack);
}

/**
 * 通用的delete ajax请求
 * @param reqUrl
 * @param dataParam
 * @param successCallBack
 * @param failCallBack
 * @returns
 */
function ajaxDelete(reqUrl, dataParam, successCallBack, failCallBack){
	if(!failCallBack){
		failCallBack = function (){
			optMsgDialog("del","fail");
		}
	}
	ajaxCommon("application/json; charset=UTF-8", "DELETE", reqUrl, "json", dataParam, false, successCallBack, failCallBack);
}

/**
 * 通用ajax请求封装
 * @param contentType
 * @param type
 * @param reqUrl
 * @param dataType
 * @param cache
 * @param successCallBack
 * @param failCallBack
 * @returns
 */
function ajaxCommon(contentType, type, reqUrl, dataType, data, cache, successCallBack, failCallBack){
	var contentTypeParam = "application/json; charset=UTF-8", 
		typeParam = "GET",
		dataTypeParam = "json",
		cacheParam = false,
		dataParam = undefined;
	
	if(contentType){
		contentTypeParam = contentType;
	}
	if(type){
		typeParam = type;
	}
	if(reqUrl){
		dataTypeParam = dataType;
	}
	if(cache){
		cacheParam = cache;
	}
	if(data){
		dataParam = JSON.stringify(data);
	}
	
	$.ajax({
		contentType : contentTypeParam,
		type: typeParam,
        // headers: {
        //     userToken: userToken
        // },
		url: reqUrl,
		async: false,  
		dataType : dataTypeParam,
		cache : cacheParam,
		data : dataParam,
        // beforeSend: function(request) {
        //     request.setRequestHeader("Authorization", "Bearer " + userToken);
        // },
		success : function(data) {
			if(data.errorNO == '0'){
				if(successCallBack){
					successCallBack(data.result);
				}
			} else{
				if(failCallBack){
					failCallBack(data);
				} 
			}
		},
		error:function(response,status,xhr){
			ajaxErrorDialog(status);
		}
	}); //ajax end
}

/**
 * 通用的load ajax请求
 * @param $obj
 * @param reqUrl
 * @param successCallBack
 * @returns
 */
function ajaxLoad($obj, reqUrl, successCallBack){
	//设置ajax请求格式
	$.ajaxSetup({global:false,contentType:'application/json;charset=utf-8'});
	$obj.load(
		reqUrl,
		function(response, status, xhr){
			if(status == "success"){
				if(successCallBack){
					successCallBack();
				}
			}else{
				ajaxErrorDialog(status);
			}
		}
	)
}