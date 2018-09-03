<!-- Start #sidebar -->
<div id="sidebar">
    <!-- Start .sidebar-inner -->
    <div class="sidebar-inner">
        <!-- Start #sideNav -->
        <ul id="sideNav" class="nav nav-pills nav-stacked">
            <!-- 游客菜单 -->
            <#if touristResource?exists>
                <#list touristResource.children as rootMenu>
                    <li><a href="${ctx}/tourist">游客专区 <i class="im-screen"></i></a>
                    <li><a href="${rootMenu.url?default('#')}" >${rootMenu.name}<i class="im-table2"></i></a>
                        <#if rootMenu.children?exists>
                            <ul class="nav sub">
                            <#list rootMenu.children as childMenu>
                                <li><a href="${childMenu.url?default('#')}" TARGET="_blank"><i class="fa-list"></i>${childMenu.name}</a></li>
                            </#list>
                            </ul>
                        </#if>
                    </li>
                </#list>
            </#if>
            <!-- 首页 -->
            <li><a href="${ctx}/index">用户中心 <i class="im-screen"></i></a></li>
            <!-- 用户菜单 -->
            <#if userResource?exists>
                <#list userResource.children as rootMenu>
                    <li><a href="${ctx}/${rootMenu.url?default('#')}">${rootMenu.name}<i class="im-table2"></i></a>
                        <#if rootMenu.children?exists>
                            <ul class="nav sub">
                            <#list rootMenu.children as childMenu>
                                <li><a href="${ctx}/${childMenu.url?default('#')}"><i class="fa-list"></i>${childMenu.name}</a></li>
                            </#list>
                            </ul>
                        </#if>
                    </li>
                </#list>
            </#if>
        </ul>
        <!-- End #sideNav -->
    </div>
    <!-- End .sidebar-inner -->
</div>
<!-- End #sidebar -->