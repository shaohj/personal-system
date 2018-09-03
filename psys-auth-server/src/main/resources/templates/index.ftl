<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>个人开发学习平台</title>
</head>
<body  >
    欢迎，<#if Session.login_user?exists>${Session.login_user.userName}</#if>,首页
    <br /><a href="${ctx}/loginout">退出登录</a>
</body>
</html>
