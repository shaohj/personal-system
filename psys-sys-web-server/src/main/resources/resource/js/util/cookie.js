//JS操作cookies方法!
function setCookie(name, value, option){ 
    var str=name+'='+escape(value); 
    if(option){ 
        if(option.expireHours){ 
            var d=new Date(); 
            d.setTime(d.getTime()+option.expireHours*3600*1000); 
            str+='; expires='+d.toGMTString(); 
        } 
        if(option.path) str+='; path='+option.path; 
        if(option.domain) str+='; domain='+option.domain; 
        if(option.secure) str+='; true'; 
    } 
    document.cookie=str; 
} 

function getCookie(name){ 
    var arr = document.cookie.split('; '); 
    if(arr.length==0) return ''; 
    for(var i=0; i <arr.length; i++){ 
        tmp = arr[i].split('='); 
        if(tmp[0]==name) return unescape(tmp[1]); 
    } 
    return ''; 
} 

function delCookie(name){ 
    this.setCookie(name,'',{expireHours:-1}); 
} 

/**How to use it 
Cookie.setCookie('own','this is cookie test!'); 
alert(Cookie.getCookie('own')); 
Cookie.delCookie('own'); 
alert(Cookie.getCookie('own')); 
**/
