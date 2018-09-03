<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>个人开发学习平台</title>

    <link href="${ctx}/static/css/style.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/static/js/toastr/toastr.css" rel="stylesheet" charset="utf-8" type="text/css" />

    <script src="${ctx}/static/js/jquery-3.2.1.js"></script>
    <script src="${ctx}/static/js/cookie.js"></script>
    <script src="${ctx}/static/js/md5.js"></script>

    <script src="${ctx}/static/js/toastr/toastr.js"></script>
    <script src="${ctx}/static/js/tip-custom.js"></script>

    <script type="text/javascript" language="javascript" charset="utf-8">
        //登录Url设置为top的location.href层
        function setTopLoaction(){
            var href = window.top.location.href;

            //当前的请求地址是否以login结尾(登录时会将请求login设置为login结尾)
            if(href.lastIndexOf("login")<0){
                top.location.href = "${ctx}/login";
            }
        }

        $(document).ready(function () {
            setTopLoaction();

            var mf = function(){
                var msg = $('#msg').val();
                if(msg && msg != ''){
                    warningDialog(msg);
                }
            }
            setTimeout(mf,200);

            //表单提交前密码加密
            $('#loginForm').submit(function(){
                //如果用户名+密码不为空，则进行md5加密
                var userName = $('#username').val();
                var password = $('#password').val();

                if(userName == '' || password== ''){
                    notifyDialog('请输入账号和密码');
                    return false;
                }
                //验证码不能为空
                if($('#validateCode').val() == ''){
                    notifyDialog('请输入验证码');
                    return false;
                }
                password = hex_md5(password);
                console.log("password="+password);
                $('#password').val(password);
                setCookie('isTip','1');
                return true;
            });
        })

        //登录
        function login(){
            $('#loginForm').submit();
        }

        $(function(){
            $('#validateCode').bind('keypress',function(event){
                if(event.keyCode == "13")
                {
                    $('#loginForm').submit();
                }
            });
        });

        $(function(){
            $('#username').bind('keypress',function(event){
                if(event.keyCode == "13")
                {
                    $('#loginForm').submit();
                }
            });
        });

        $(function(){
            $('#password').bind('keypress',function(event){
                if(event.keyCode == "13")
                {
                    $('#loginForm').submit();
                }
            });
        });

        //刷新验证码
        function refresh(obj){
            var obj = $('#codeImg')[0];
            obj.src = '${ctx}/login/randomValidateCode?'+Math.random();
        }
    </script>

</head>
<body  >

<div class="videozz"></div>

<div class="box">
	<div class="box-a">
    <div class="m-2">
          <div class="m-2-1">
            <form id="loginForm" action="${ctx}/j_spring_secutity_check" method="post">
                <div class="m-2-2">
                   <input name="username" id="username" type="text" placeholder="请输入账号" />
                </div>
                <div class="m-2-2">
                   <input name="password" id="password" type="password" placeholder="请输入密码"/>
                </div>
                <div class="m-2-2-1">
                   <input name="validateCode" id="validateCode" type="text" placeholder="请输入验证码" />
                   <image id="codeImg" title="点击更换" onclick="javascript:refresh();"  src="${ctx}/login/randomValidateCode"/></span>
                </div>
                <div class="m-2-2">
                   <input type="hidden" id="msg" value="<#if loginErrorMsg?exists>${loginErrorMsg}</#if>" />
                   <button type="button" value="登录" onclick="login()" /> 登录
                </div>
            </form>
          </div>
    </div>
    <div class="m-5"> 
    <div id="m-5-id-1"> 
    <div id="m-5-2"> 
    <div id="m-5-id-2">  
    </div> 
    <div id="m-5-id-3"></div> 
    </div> 
    </div> 
    </div>   
    <div class="m-10"></div>
    <div class="m-xz7"></div>
    <div class="m-xz8 xzleft"></div>
    <div class="m-xz9"></div>
    <div class="m-xz9-1"></div>
    <div class="m-x17 xzleft"></div>
    <div class="m-x18"></div>
    <div class="m-x19 xzleft"></div>
    <div class="m-x20"></div>  
    <div class="m-8"></div>
    <div class="m-9"><div class="masked1" id="sx8">个人开发学习平台</div></div>
    <div class="m-11">
    	<div class="m-k-1"><div class="t1"></div></div>
        <div class="m-k-2"><div class="t2"></div></div>
        <div class="m-k-3"><div class="t3"></div></div>
        <div class="m-k-4"><div class="t4"></div></div>
        <div class="m-k-5"><div class="t5"></div></div>
        <div class="m-k-6"><div class="t6"></div></div>
        <div class="m-k-7"><div class="t7"></div></div>
    </div>   
    <div class="m-14"><div class="ss"></div></div>
    <div class="m-15-a">
    <div class="m-15-k">
    	<div class="m-15xz1">
            <div class="m-15-dd2"></div>
        </div>
    </div>
    </div>
    <div class="m-16"></div>
    <div class="m-17"></div>
    <div class="m-18 xzleft"></div>
    <div class="m-19"></div>
    <div class="m-20 xzleft"></div>
    <div class="m-21"></div>
    <div class="m-22"></div>
    <div class="m-23 xzleft"></div>
    <div class="m-24" id="localtime"></div>
    </div>
</div>
<script src="${ctx}/static/js/login-style.js"></script>
</body>
</html>
