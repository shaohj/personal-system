/**
 * 获取不为null的字符串值
 * @param tVal
 * @returns
 */
function getNotNullStr(tVal){
	if(tVal == undefined || tVal == null ){
		return "";
	}else{
		return tVal;
	}
}

/**
 * 给Url追加一个随机数,防止url相同,使得第二次提交请求不被处理
 * @param tUrl
 */
function appendUrlRandom(tUrl){
	var idx = tUrl.indexOf("?");
	if(idx>0){
		if(tUrl.length >(idx+1)){
			//?号不为最后一个字符串,即?号后跟参数
			tUrl += "&temp="+Math.random();
		}else{
			tUrl += "temp="+Math.random();
		}
	}else{
		tUrl += "?temp="+Math.random();
	}
	return tUrl;
}