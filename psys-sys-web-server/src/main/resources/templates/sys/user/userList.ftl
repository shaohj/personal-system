<!-- 日期控件及md5控件 -->
<script src="${ctx}/resource/js/ref/md5.js"></script>
<script src="${ctx}/resource/js/ref/My97DatePicker/WdatePicker.js"></script>
<link href="${ctx}/resource/js/ref/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" charset="utf-8" type="text/css" />
<!-- tree控件及控件拓展js -->
<script src="${ctx}/resource/js/ref/fancytree/jquery.fancytree.js"></script>
<script src="${ctx}/resource/js/ref/ref-custom/jquery.fancytree.expand.js"></script>

<link href="${ctx}/resource/js/ref/fancytree/skin-win8/ui.fancytree.css" rel="stylesheet" charset="utf-8" type="text/css" />
<link href="${ctx}/resource/js/ref/ref-custom/fancytree-custom.css" rel="stylesheet" charset="utf-8" type="text/css" />

<!-- 资源管理功能 -->
<script src="${ctx}/resource/js/util/tableCheckbox.js"></script>
<script src="${ctx}/resource/js/sys/sys.user.js"></script>
<script src="${ctx}/resource/js/sys/jquery.fn.ex.js"></script>

<!-- Start #content -->
<div id="content">
    <!-- Start .content-wrapper -->
    <div class="content-wrapper">
        <div class="row">
            <!-- Start .row -->
            <!-- Start .page-header -->
            <div class="col-lg-12 heading">
                <h1 class="page-header"><i class="im-table2"></i> 用户管理</h1>
                <!-- Start .bredcrumb -->
                <ul id="crumb" class="breadcrumb">
                </ul>
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
            <!-- Page start here ( usual with .row ) -->
            <div class="row">
                <div class="col-lg-12">
                    <!-- col-lg-12 start here -->
                    <div class="panel panelRefresh" id="spr_0">
                        <!-- Start .panel -->
                        <div class="panel-heading white-bg">
                            <h4 class="panel-title">用户管理</h4>
                        </div>
                        <div class="panel-body">
                            <div class="slimScrollDiv" style="position: relative; overflow: hidden; width: 100%; height: auto;">
                                <div class="table-responsive" style="overflow: hidden; width: 100%; height: auto;">
                                    <table width="100%">
                                        <tr>
                                            <td width="4%">组织</td>
                                            <td width="1%"></td>
                                            <td width="25%">
                                                <div id="queryList">
                                                    <select id="groupCode" class="form-control" style="white-space:nowrap;text-overflow:ellipsis;overflow:hidden;" >
                                                        <option selected style="display:none;"></option>
                                                    </select>
                                                    <div id="exTree" class="selectfancytree">
                                                        <div class="exTree" id="queryGroupTree" style="display:none;width:165%; height:aout;overflow-y: auto;overflow-x:hidden;border: 1px solid #66afe9;position:absolute;z-index: 1;"></div>
                                                    </div>
                                                </div>
                                            </td>
                                            <td width="1%"></td>
                                            <!--  状态 -->
                                            <td width="4%">状态</td>
                                            <td width="1%"></td>
                                            <td width="25%">
                                                <select class="form-control" name="status" id="status" >
                                                    <option value="">请选择</option>
                                                        <#if userStateList?exists>
                                                            <#list userStateList as userState>
                                                                <option value="${userState.codeType}">${userState.name}</option>
                                                            </#list>
                                                        </#if>
                                                </select>
                                            </td>
                                            <td width="1%"></td>

                                            <!-- 用户名 -->
                                            <td width="4%">姓名</td>
                                            <td width="1%"></td>
                                            <td width="25%">
                                                <input type="text" name="key" id="key" onKeypress="if(event.keyCode==13)searchByResetCondition();" class="form-control" value="" placeholder="请输入用户名或姓名" />
                                            </td>
                                            <td width="1%"></td>

                                            <!-- 查询、重置 -->
                                            <td width="5%"><button type="button" onclick="searchByResetCondition()" class="btn btn-blue right"><i class="fa fa-search"></i>查询</button></td>
                                            <td width="1%"></td>
                                            <td width="5%"><button type="button" onclick="resetCondition()" class="btn btn-blue right"><i class="fa fa-retweet"></i>重置</button></td>
                                            <td width="36%"></td>
                                        </tr>
                                    </table>

                                    <div class="spacer10"></div>
                                    <!-- 操作按钮 -->
                                    <div class="do-icons" style="float:right">
                                        <ul>
                                            <li><a href="javascript:void(0)" id="add-user-button" ><i class="fa fa-plus"></i>新增</a></li>
                                            <li><a href="javascript:void(0)" id="mod-user-button" ><i class="fa fa-edit"></i>修改</a></li>
                                            <!-- 删除已开发好,暂不启用
                                            <li><a href="javascript:void(0)" onclick="javascript:comfirmOptionUserList('del')" ><i class="fa fa-remove"></i>删除</a></li>
                                            -->
                                            <li><a href="javascript:void(0)" onclick="javascript:comfirmOptionUserList('enableUser')" ><i class="fa fa-eye"></i>启用</a></li>
                                            <li><a href="javascript:void(0)" onclick="javascript:comfirmOptionUserList('disableUser')" ><i class="fa fa-eye-slash"></i>禁用</a></li>
                                            <li><a href="javascript:void(0)" id="grant-userRole-button" ><i class="fa fa-reorder"></i>角色分配</a></li>
                                        </ul>
                                    </div>
                                    <div id="datatable_wrapper" class="dataTables_wrapper form-inline" role="grid">
                                        <div id="result_set"></div>
                                        <div class="slimScrollBar ui-draggable" style="background: rgb(153, 153, 153); height: 5px; position: absolute; bottom: 3px; opacity: 0.4; display: none; border-radius: 5px; z-index: 99; width: 1227px; left: 0px;"></div>
                                        <div class="slimScrollRail" style="width: 100%; height: 5px; position: absolute; bottom: 3px; display: none; border-radius: 5px; background: rgb(51, 51, 51); opacity: 0.5; z-index: 90;"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div> <!-- End .panel -->
                </div>
            </div> <!-- col-lg-12 end here -->
        </div><!-- Page End here -->
    </div> <!-- End .outlet -->
    <div class="clearfix"></div>
</div> <!-- End .content-wrapper -->
</div><!-- End #content -->

<div id="user_mng_dialog_div" style="display:none;">
    <div id="add_user_dialog" title="新增用户" style="display:none;" >
		<#include "/sys/user/addUserDialog.ftl" encoding="UTF-8" />
    </div>
    <div id="mod_user_dialog" title="修改用户" style="display:none;">
		<#include "/sys/user/modUserDialog.ftl" encoding="UTF-8" />
    </div>
    <div id="grant_userRole_dialog" title="角色分配" style="display:none;"></div>
</div>

<!-- Javascripts -->
<!-- Important javascript libs(put in all pages) -->
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
<script src="${ctx}/assets/plugins/forms/icheck/jquery.icheck.js"></script>
<script src="${ctx}/assets/plugins/forms/tags/jquery.tagsinput.min.js"></script>
<script src="${ctx}/assets/plugins/forms/tinymce/tinymce.min.js"></script>
<script src="${ctx}/assets/plugins/tables/datatables/jquery.dataTables.min.js"></script>
<script src="${ctx}/assets/plugins/tables/datatables/jquery.dataTablesBS3.js"></script>
<script src="${ctx}/assets/plugins/tables/datatables/tabletools/ZeroClipboard.js"></script>
<script src="${ctx}/assets/plugins/tables/datatables/tabletools/TableTools.js"></script>
<script src="${ctx}/assets/plugins/misc/highlight/highlight.pack.js"></script>
<script src="${ctx}/assets/plugins/misc/countTo/jquery.countTo.js"></script>
<script src="${ctx}/assets/js/jquery.sprFlat.js"></script>
<script src="${ctx}/assets/js/app.js"></script>

<script>
    var queryObj = new Object(); // 定义一个查询对象
    queryObj.groupCode = "";
    queryObj.status = "";
    queryObj.key = "";

    //重置查询条件文件框,非重置表单
    function resetCondition(){
        var $group = $("#groupCode");
        var $groupOption = $group.find("option:selected");
        $groupOption.val("");
        $groupOption.text("");

        $('#status').val("");
        $('#key').val("");
    }

    //重新设置查询对象,并重新查询数据
    function searchByResetCondition(){
        var groupCode = getNotNullStr($('#groupCode').val()),
                status = getNotNullStr($('#status').val()),
                key = getNotNullStr($('#key').val());

        queryObj.groupCode = groupCode;
        queryObj.status = status;
        queryObj.key = key;
        searchForPage(0); //查询第一页数据
    }

    //重置查询条件,且查询第一页数据
    function initRefreshList(){
        resetCondition();
        searchForPage(0);
    }

    //查询第几页
    //pageNum 参数可选
    function searchForPage(pageNum){
        var pageNum = $('#pageNum').val()
        if(pageNum == undefined && pageNum == undefined){
            pageNum = 0;
        }else if(pageNum == undefined){
            pageNum = pageNum;
        }

        var pageSize = getNotNullStr($('#pageSize').val());
        pageSize = pageSize==""?10:pageSize;

        var url = "${ctx}/sys/user/to/listData?page=" + pageNum + "&size=" + pageSize + "&userName=" + queryObj.key +
                "&status=" + queryObj.status + "&groupCode=" + queryObj.groupCode;
        ajaxLoad($("#result_set"), url, function(){
            $("#datatable").tableCheck("trBackgroundColor");
        });
    }

    $(document).ready(function(){
        groupQuery("queryGroupTree", "queryList", "groupCode");

        $("#groupCode").updateSelectDiv({
            selectDivId: 'queryGroupTree'
        });

        searchForPage(0); //默认显示第1页数据

        $("#add-user-button").initAddUserDialog();
        $("#addGroup").updateSelectDiv({
            selectDivId:'addGroupTree'
        });

        $("#mod-user-button").initModUserDialog();
        $("#modGroup").updateSelectDiv({
            selectDivId:'modGroupTree'
        });

        $("#grant-userRole-button").initGrantUserRoleDialog();
    });

    function groupQuery(treeId, dialogId, selectId){
        var $thisDialog = $("#"+dialogId);
        var $select = $thisDialog.find("#"+selectId);
        var $selectOption = $select.find("option:selected");
        var $tree = $("#"+treeId);

        var url = secContext + "/sys/group/query/listTreeAll?access_token=${tokenValue}";

        ajaxGet(url, function(treeData){
            $tree.fancytree({
                selectMode: 2,
                source: treeData,
                loadChildren: function(event, data) {
                    //var $fancytree = $tree.fancytree("getTree");
                    //var key = "1"; //默认激活节点key
                    //$fancytree.activateKey(key);
                },
                loadError: function(event, data) {
                    optMsgDialog("loadTree", "fail");
                },
                activate: function(event, data){  //节点激活事件
                    var node = data.node,
                            nodeData = node.data;
                    if(nodeData.id && nodeData.id != ""){
                        $selectOption.val(nodeData.id);
                        var temp = nodeData.name;
                        $selectOption.text(temp);
                        $select.attr("title",temp);
                        $thisDialog.find("#"+treeId).css("display","none");
                    }
                },
            });
        });
    }
</script>