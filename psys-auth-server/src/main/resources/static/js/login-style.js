//图片无缝滚动
var speed=10; 
var tab=document.getElementById("m-5-id-1"); 
var tab1=document.getElementById("m-5-id-2"); 
var tab2=document.getElementById("m-5-id-3"); 
tab2.innerHTML=tab1.innerHTML; 
function Marquee(){ 
if(tab2.offsetWidth-tab.scrollLeft<=0) 
tab.scrollLeft-=tab1.offsetWidth 
else{ 
tab.scrollLeft++; 
} 
} 
var MyMar=setInterval(Marquee,speed); 
tab.onmouseover=function() {clearInterval(MyMar)}; 
tab.onmouseout=function() {MyMar=setInterval(Marquee,speed)}; 

// 处理返回信息函数
function processResponse() { 
if (XMLHttpReq.readyState == 4) { // 判断对象状态 
if (XMLHttpReq.status == 200) { // 信息已经成功返回，开始处理信息 
DisplayHot(); 
setTimeout("sendRequest()",2000); //设置自动刷新时间，这里是毫秒，即2秒//RemoveRow(); 
} else { //页面不正常 
//window.alert("您所请求的页面有异常。"); 
} 
} 
} 
function DisplayHot()
{
var theDate = XMLHttpReq.responseText ;//如果出现编码问题,可以在服务端escape一下,然后在这里使用unescape( responseText )

// gxsj1.innerHTML = theDate ;
}

//获取当前时间带跳动
function showLocale(objD){
	var str,colorhead,colorfoot;
	var yy = objD.getYear();
	if(yy<1900) yy = yy+1900;
	var MM = objD.getMonth()+1;
	if(MM<10) MM = '0' + MM;
	var dd = objD.getDate();
	if(dd<10) dd = '0' + dd;
	var hh = objD.getHours();
	if(hh<10) hh = '0' + hh;
	var mm = objD.getMinutes();
	if(mm<10) mm = '0' + mm;
	var ss = objD.getSeconds();
	if(ss<10) ss = '0' + ss;
	var ww = objD.getDay();
	// if  ( ww==0 )  colorhead="";
	// if  ( ww > 0 && ww < 6 )  colorhead="";
	if  ( ww==6 )  colorhead="";
	if  (ww==0)  colorhead="星期日";
	if  (ww==1)  colorhead="星期一";
	if  (ww==2)  colorhead="星期二";
	if  (ww==3)  colorhead="星期三";
	if  (ww==4)  colorhead="星期四";
	if  (ww==5)  colorhead="星期五";
	if  (ww==6)  colorhead="星期六";
	colorfoot="";
	str = colorhead + "  " + yy + "-" + MM + "-" + dd + " " + hh + ":" + mm + ":" + ss + "  " + colorfoot;
	return(str);
}

function tick(){
	var today;
	today = new Date();
	document.getElementById("localtime").innerHTML = showLocale(today);
	window.setTimeout("tick()", 1000);
}

tick();
