<!-- Start #header -->
<div id="header">
    <div class="container-fluid">
        <div class="navbar">
            <div class="navbar-header">
                <a class="navbar-brand" href="${ctx}/index">
                    <i class="im-windows8 text-logo-element animated bounceIn"></i><span class="text-logo">stu</span><span class="text-slogan">-felix</span>
                </a>
            </div>
            <nav class="top-nav" role="navigation">
                <ul class="nav navbar-nav pull-left">
                    <li id="toggle-sidebar-li"><a href="#" id="toggle-sidebar"><i class="en-arrow-left2"></i></a></li>
                    <li><a href="#" class="full-screen"><i class="fa-fullscreen"></i></a></li>
                </ul>
                <ul class="nav navbar-nav pull-right">
                    <li class="dropdown">
                        <a href="#" data-toggle="dropdown">
                            <img class="user-avatar" src="${ctx}/assets/img/avatars/48.jpg" alt="admin"><#if user?exists>${user.userName}</#if></a>
                        <ul class="dropdown-menu right" role="menu">
                            <!--   http://localhost:18090/auth-server/loginout logout -->
                            <li><a href="${ctx}/revokeToken"><i class="im-exit"></i> 登出</a></li>
                        </ul>
                    </li>
                    <li id="toggle-right-sidebar-li">
                        <a href="#" id="toggle-right-sidebar"><i class="ec-users"></i> <span class="notification">1</span></a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
    <!-- Start .header-inner -->
</div>
<!-- End #header -->