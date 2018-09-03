<!DOCTYPE html>
<html lang="en">
    <head>
        <title>个人开发学习平台</title>
        <meta charset="utf-8">
        <!-- Mobile specific metas -->
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

        <!-- Force IE9 to render in normal mode -->
        <!--[if IE]><meta http-equiv="x-ua-compatible" content="IE=9" /><![endif]-->
        <meta name="description" content="felix shao description" />
        <meta name="keywords" content="felix shao keywords"
        />
        <meta name="application-name" content="个人开发学习平台" />
        <!-- Import google fonts - Heading first/ text second -->
        <link rel='stylesheet' type='text/css' />
        <!--[if lt IE 9]><![endif]-->

        <!-- Css files -->
        <!-- Icons -->
        <link href="${ctx}/assets/css/icons.css" rel="stylesheet" />
        <!-- jQueryUI -->
        <link href="${ctx}/assets/css/sprflat-theme/jquery.ui.all.css" rel="stylesheet" />
        <!-- Bootstrap stylesheets (included template modifications) -->
        <link href="${ctx}/assets/css/bootstrap.css" rel="stylesheet" />
        <!-- Plugins stylesheets (all plugin custom css) -->
        <link href="${ctx}/assets/css/plugins.css" rel="stylesheet" />
        <!-- Main stylesheets (template main css file) -->
        <link href="${ctx}/assets/css/main.css" rel="stylesheet" />
        <!-- Custom stylesheets ( Put your own changes here ) -->
        <link href="${ctx}/assets/css/custom.css" rel="stylesheet" />
        <!-- Fav and touch icons -->
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="${ctx}/assets/img/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="${ctx}/assets/img/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="${ctx}/assets/img/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="${ctx}/assets/img/ico/apple-touch-icon-57-precomposed.png">
        <link rel="icon" href="${ctx}/assets/img/ico/favicon.ico" type="image/png">
        <!-- Windows8 touch icon ( http://www.buildmypinnedsite.com/ )-->
        <meta name="msapplication-TileColor" content="#3399cc" />

        <!-- Important javascript libs(put in all pages) -->
        <script src="${ctx}/assets/js/libs/jquery-2.1.1.min.js"></script>
        <script src="${ctx}/assets/js/libs/jquery-ui-1.10.4.min.js"></script>

        <script src="${ctx}/resource/js/util/customAjaxUtil.js"></script>
        <script src="${ctx}/resource/js/util/util.js"></script>
        <script src="${ctx}/resource/js/util/uiutil.js"></script>
        <script src="${ctx}/resource/js/ref/toastr/toastr.js"></script>
        <script src="${ctx}/resource/js/ref/ref-custom/tip-custom.js"></script>

        <script src="${ctx}/resource/js/ref/Validform_v5.3.2/Validform_v5.3.2.js"></script>
        <link href="${ctx}/resource/js/ref/Validform_v5.3.2/style/validForm.css" rel="stylesheet" charset="utf-8" type="text/css" />

        <script src="${ctx}/resource/js/ref/confirm-dialog/confirm.dialog.js"></script>
        <link href="${ctx}/resource/js/ref/confirm-dialog/confirm.dialog.css" rel="stylesheet" charset="utf-8" type="text/css" />

        <!-- 导入css -->
        <link href="${ctx}/resource/css/form/form.css" rel="stylesheet" charset="utf-8" type="text/css" />
        <link href="${ctx}/resource/css/ref/font-awesome-4.7.0/css/font-awesome.css" rel="stylesheet" charset="utf-8" type="text/css" />
        <link href="${ctx}/resource/js/ref/toastr/toastr.css" rel="stylesheet" charset="utf-8" type="text/css" />

        <script>
            var contextPath = "${ctx}";
            var secContext = "${secContext}";
            var userToken = "${tokenValue?default('-1')}";
            var loginUserId = "${user.userId?default(-1)}";
            //console.log("userToken=" + userToken);
            //console.log("contextPath=" + contextPath);
            //禁止全局的回车按钮  可能会出现 重复响应事件的bug
            document.onkeypress = function(e){
                e = e || event;
                if(e.keyCode == 13){
                    return false;
                }
            }
        </script>
    </head>
    <body>
