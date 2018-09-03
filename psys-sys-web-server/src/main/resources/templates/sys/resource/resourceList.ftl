<!-- tree控件及控件拓展js -->
<script src="${ctx}/resource/js/ref/fancytree/jquery.fancytree.js"></script>
<script src="${ctx}/resource/js/ref/ref-custom/jquery.fancytree.expand.js"></script>

<link href="${ctx}/resource/js/ref/fancytree/skin-win8/ui.fancytree.css" rel="stylesheet" charset="utf-8" type="text/css" />
<link href="${ctx}/resource/js/ref/ref-custom/fancytree-custom.css" rel="stylesheet" charset="utf-8" type="text/css" />

<!-- 资源管理功能 -->
<script src="${ctx}/resource/js/sys/sys.resource.js"></script>

<!-- Start #content -->
<div id="content">
    <!-- Start .content-wrapper -->
    <div class="content-wrapper">
        <div class="row">
            <!-- Start .row -->
            <!-- Start .page-header -->
            <div class="col-lg-12 heading">
                <h1 class="page-header"><i class="im-table2"></i> 资源管理</h1>
                <!-- Start .bredcrumb -->
                <ul id="crumb" class="breadcrumb"></ul>
                <!-- End .breadcrumb -->
                <!-- Start .option-buttons -->
                <div class="option-buttons">
                    <div class="btn-toolbar" role="toolbar">
                        <div class="btn-group">
                            <a id="clear-localstorage" class="btn tip" title="Reset panels position">
                                <i class="ec-refresh color-red s24"></i>
                            </a>
                        </div>
                    </div>
                </div>
                <!-- End .option-buttons -->
            </div>
            <!-- End .page-header -->
        </div>
        <!-- End .row -->
        <div class="outlet">
            <!-- Start .outlet -->
            <!-- Page start here -->
            <div id="email-sidebar">
                <!-- Start #email-sidebar -->
                <div class="p15">
                    <a title="新增同级" onClick="addResourceSiblingNode('resourceTree')" href="javascript:void(0)" class="im-plus" onFocus="this.blur()"></a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <a title="新增下级" onClick="addResourceChildNode('resourceTree')" href="javascript:void(0)" class="fa-plus" onFocus="this.blur()"></a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <a title="删除资源" onClick="delResourceNode('resourceTree')" href="javascript:void(0)" class="im-minus" onFocus="this.blur()"></a>
                </div>
                <ul id="email-nav" class="nav nav-pills nav-stacked">
                    <div class="tree" id="exTree">
                        <div class="exTree" id="resourceTree"></div>
                    </div>
                </ul>
            </div>
            <!--End #email-sidebar -->
            <div id="email-content">
                <!-- Start #email-content -->
                <div class="email-wrapper">
                    <!-- End .email-toolbar -->
                    <div class="email-write">
                        <form id="editResourceForm" method="post" action="#" class="form-horizontal" style="display:none" role="form" onsubmit="return false;">
                            <!-- 资源名称:  -->
                            <div class="form-group">
                                <label class="col-sm-2 control-label Validform_label"><span>*</span>资源名</label>
                                <div class="col-sm-6">
                                    <input id="resourceId" type="hidden" />
                                    <input id="resourceCode" type="hidden" />
                                    <input id="parentId" type="hidden" />
                                    <input id="originalName" type="hidden" />
                                    <input class="form-control" id="name" type="text" datatype="s" maxlength="20" ajaxurl="${secContext}/sys/resource/query/existNameByParentId?access_token=${tokenValue}" needSend="parentId,originalName"
                                           nullmsg="请填写资源名!" sucms="资源名输入正确!" errormsg="请输入3到20个字符!"  placeholder="请输入资源名" />
                                </div>
                                <div class="col-sm-4"><div class="Validform_checktip"></div></div>
                            </div>

                            <!-- 链接URL: -->
                            <div class="form-group">
                                <label class="col-sm-2 control-label" >链接URL</label>
                                <div class="col-sm-6">
                                    <input class="form-control" id="url" type="text" placeholder="请输入链接URL.示例:/secUser/toList">
                                </div>
                                <div class="col-sm-4"><div class="Validform_checktip"></div></div>
                            </div>

                            <!-- 链接URL: -->
                            <div class="form-group">
                                <label class="col-sm-2 control-label" >新窗口打开</label>
                                <div class="col-sm-6">
                                    <select id="newWindow" class="form-control" >
                                        <option value="N">否</option>
                                        <option value="Y">是</option>
                                    </select>
                                </div>
                                <div class="col-sm-4"><div class="Validform_checktip"></div></div>
                            </div>
                            <!-- 排序: -->
                            <div class="form-group">
                                <label class="col-sm-2 control-label" >排序</label>
                                <div class="col-sm-6">
                                    <input class="form-control" id="sort" type="number" datatype="n" ignore="ignore" nullmsg="请填写菜单排序!" sucms="菜单排序输入正确!" errormsg="请输入数字!"  placeholder="请输入菜单排序">
                                </div>
                                <div class="col-sm-4"><div class="Validform_checktip"></div></div>
                            </div>
                            <!-- 备注: -->
                            <div class="form-group">
                                <label class="col-sm-2 control-label" for="remark" >备注</label>
                                <div class="col-sm-8">
                                    <textarea id="remark" class="form-control" rows="3"></textarea>
                                </div>
                                <div class="col-sm-2"><div class="Validform_checktip"></div></div>
                            </div>

                            <!-- 操作: -->
                            <div class="form-group">
                                <div class="centerBtnGroup">
                                    <button id="submitEditResourceForm" type="button" class="btn btn-blue"><i class="fa fa-check-square-o"></i>保存</button>
                                    <!-- 隐藏域Id不重置 -->
                                    <button id="clearShowEditResource" onclick="clearShowEditResourceEvent()" type="button" class="btn btn-blue"><i class="fa fa-refresh"></i>重置</button>
                                    <button id="resetEditResourceForm" style="display:none" type="button" class="btn btn-blue"><i class="fa fa-refresh"></i>重置表单</button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <!-- End .email-write -->
                </div>
                <!-- End .email-wrapper -->
            </div>
            <!--End #email-content -->
            <!-- Page End here -->
        </div>
        <!-- End .outlet -->
    </div>
    <!-- End .content-wrapper -->
    <div class="clearfix"></div>
</div>
<!-- End #content -->
<!-- Javascripts -->
<!--[if lt IE 9]>
<script type="text/javascript" src="${ctx}/assets/js/libs/excanvas.min.js"></script>
<script type="text/javascript" src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<script type="text/javascript" src="${ctx}/assets/js/libs/respond.min.js"></script>
<![endif]-->
<!-- Bootstrap plugins -->
<script src="${ctx}/assets/js/bootstrap/bootstrap.js"></script>
<!-- Core plugins ( not remove ever) -->
<!-- Handle responsive view functions -->
<script src="${ctx}/assets/js/jRespond.min.js"></script>
<!-- Custom scroll for sidebars,tables and etc. -->
<script src="${ctx}/assets/plugins/core/slimscroll/jquery.slimscroll.js"></script>
<!-- Resize text area in most pages -->
<script src="${ctx}/assets/plugins/forms/autosize/jquery.autosize.js"></script>
<!-- Bootbox confirm dialog for reset postion on panels -->
<script src="${ctx}/assets/plugins/ui/bootbox/bootbox.js"></script>
<!-- Other plugins ( load only nessesary plugins for every page) -->
<script src="${ctx}/assets/plugins/charts/sparklines/jquery.sparkline.js"></script>
<script src="${ctx}/assets/plugins/charts/pie-chart/jquery.easy-pie-chart.js"></script>
<script src="${ctx}/assets/plugins/forms/icheck/jquery.icheck.js"></script>
<script src="${ctx}/assets/plugins/forms/tags/jquery.tagsinput.min.js"></script>
<script src="${ctx}/assets/plugins/forms/tinymce/tinymce.min.js"></script>
<script src="${ctx}/assets/plugins/misc/highlight/highlight.pack.js"></script>
<script src="${ctx}/assets/plugins/misc/countTo/jquery.countTo.js"></script>
<script src="${ctx}/assets/js/jquery.sprFlat.js"></script>
<script src="${ctx}/assets/js/app.js"></script>
<script src="${ctx}/assets/js/pages/email.js"></script>

<script>
    //表单添加验证事件
    $("#editResourceForm").Validform({
        btnSubmit:"#submitEditResourceForm",
        btnReset:"#resetEditResourceForm",
        ignoreHidden:true, //默认为false，当为true时对:hidden的表单元素将不做验证;
        beforeSubmit:function(curform){
            ajaxSubAddOrModResource('resourceTree');
            return false;
        },
        tiptype:function(msg,o,cssctl){
            if(!o.obj.is("form")){//验证表单元素时o.obj为该表单元素，全部验证通过提交表单时o.obj为该表单对象;
                var objtip=o.obj.parents().siblings().children(".Validform_checktip");
                cssctl(objtip,o.type);
                objtip.text(msg);
            }
        }
    });

    var menuMaxNum = "${menuMaxNum}"; //读取配置文件中的信息
    var menuMaxLevel = "${menuMaxLevel}"; //读取配置文件中的最高层级数
    $(document).ready(function(){
        loadResourceList('resourceTree');
    });
</script>