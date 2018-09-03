<!-- 博客中心功能 -->
<script src="${ctx}/resource/js/util/tableCheckbox.js"></script>
<script src="${ctx}/resource/js/blog/blog.center.js"></script>

<!-- Start #content -->
<div id="content">
	<!-- Start .content-wrapper -->
	<div class="content-wrapper">
		<div class="row">
			<!-- Start .row -->
			<!-- Start .page-header -->
			<div class="col-lg-12 heading">
				<h1 class="page-header"><i class="im-table2"></i> 博客中心</h1>
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
							<h4 class="panel-title">博客中心</h4>
						</div>
						<div class="panel-body">
							<div class="slimScrollDiv" style="position: relative; overflow: hidden; width: 100%; height: auto;">
								<div class="table-responsive" style="overflow: hidden; width: 100%; height: auto;">
                                   <table width="100%">
                                       <tr>
                                           <!--  排序 -->
                                           <td width="4%"><span >排序</span></td>
                                           <td width="1%"></td>
                                           <td width="25%">
                                               <select class="form-control" name="sortField" id="sortField" >
                                                   <option value="">请选择</option>
                                                   <#if blogSortFieldList?exists>
                                                       <#list blogSortFieldList as blogSortField>
                                                           <option value="${blogSortField.field}">${blogSortField.fname}</option>
                                                       </#list>
                                                   </#if>
                                               </select>
                                           </td>
                                           <td width="1%"></td>

                                           <!--   -->
                                           <td width="4%"><span class="label"></span></td>
                                           <td width="1%"></td>
                                           <td width="25%"></td>
                                           <td width="1%"></td>

                                           <!--  -->
                                           <td width="4%"><span class="label"></span></td>
                                           <td width="1%"></td>
                                           <td width="25%"></td>
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
                                           <li><a href="javascript:void(0)" id="add-blog-button" ><i class="fa fa-plus"></i>新增</a></li>
                                       </ul>
                                   </div>

									<div id="datatable_wrapper" class="dataTables_wrapper form-inline" role="grid">
										<div id="result_set"></div>
										<div class="slimScrollBar ui-draggable" style="background: rgb(153, 153, 153); height: 5px; position: absolute; bottom: 3px; opacity: 0.4; display: none; border-radius: 5px; z-index: 99; width: 1227px; left: 0px;"></div>
										<div class="slimScrollRail" style="width: 100%; height: 5px; position: absolute; bottom: 3px; display: none; border-radius: 5px; background: rgb(51, 51, 51); opacity: 0.5; z-index: 90;"></div>
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

<div id="blog_center_dialog_div" style="display:none;">
	<#include "/blog/center/blogCenterDialog.ftl" encoding="UTF-8"/>
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
    //定义一个查询对象保存查询数据,无查询当前页以及数量属性
    var queryObj = new Object(); //构造查询对象;
    queryObj.sortField="";

    //重置查询条件文件框,非重置表单
    function resetCondition(){
        var options = $("#sortField").find("option");
        options.attr("selected", false);
        options.first().attr("selected", true);
    }

    //重新设置查询对象,并重新查询数据
    function searchByResetCondition(){
        var sortField = getNotNullStr($('#sortField').val());

        queryObj.sortField = sortField;
        searchForPage(0); //查询第一页数据
    }

    //重置查询条件,且查询第一页数据
    function initRefreshList(){
        resetCondition();
        searchForPage(0);
    }

    //查询第几页
    //pageNum 参数可选
    function searchForPage(pageNumParam){
        var pageNum = $('#pageNum').val();

        if(undefined != pageNumParam){
            pageNum = pageNumParam;
        }

        if(pageNum == undefined && pageNum == undefined){
            pageNum = 0;
        }else if(pageNum == undefined){
            pageNum = pageNum;
        }

        var pageSize = getNotNullStr($('#pageSize').val());
        pageSize = pageSize==""?10:pageSize;

        var url = appendUrlRandom("${ctx}/blog/center/to/listData?page="+pageNum+"&size="+pageSize+"&sort="+queryObj.sortField); //添加随机数,无缓存请求
        ajaxLoad($("#result_set"), url, function(){
            $("#datatable").tableCheck("trBackgroundColor");
        });
    }

    $(document).ready(function(){
        searchForPage(0); //默认刷新第1页

        $("#add-blog-button").initAddBlogDialog('');
    });
</script>
