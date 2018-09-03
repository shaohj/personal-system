<#assign pageNum = pageResult.number/>
<#assign pageSize = pageResult.size/>
<#assign totalPages = pageResult.totalPages/>
<#assign showPageNum = 5/>
<#assign totalElements = pageResult.totalElements/>
<#if totalPages != 0>
<input type="hidden" name="pageNum" id="pageNum" value="${pageNum}" />
<input type="hidden" name="pageSize" id="pageSize" value="${pageSize}" />

<div class="row-">
	<div class="col-lg-6 col-md-6 col-sm-12">
		<div class="dataTables_info" id="datatable_info">第${pageNum+1}页，共${totalPages}页，共${totalElements}条记录  到第<select id="selPageNum" style="width:50px" onchange="queryPage(this)">
		<#list 1..totalPages as i>
			<#if i == pageNum + 1>
				<option value="${i-1}" selected="true">${i}</option>
			<#else>
				<option value="${i-1}" >${i}</option>
			</#if>
		</#list>
        </select>页</div>
	</div>
	<div class="col-lg-6 col-md-6 col-sm-12">
		<div class="dataTables_paginate paging_bs_full" id="datatable_paginate">

			<ul class="pagination">
                <!--  首页和上一页 -->
				<#if pageNum == 0>
					<li class="disabled"><a href="javascript:void(0)" class="paginate_button first" title="First">First</a></li>
					<li class="disabled"><a href="javascript:void(0)" class="paginate_button previous" title="Previous">Previous</a></li>
					<#else>
					<li ><a href="javascript:void(0)" onclick="searchForPage(0)" class="paginate_button first" title="First">First</a></li>
					<li ><a href="javascript:void(0)" onclick="searchForPage(${pageNum-1})" class="paginate_button previous" title="Previous">Previous</a></li>
				</#if>

                <!--  中间页 -->
				<#if totalPages gt showPageNum>
					<!-- 数据大于n页时,对数据进行处理 -->
					<#if (totalPages-pageNum) gte showPageNum>
						<!-- 当前页到尾页数据大于等于showPageNum时,显示从当前页往后的showPageNum条数据 -->
						<#list 1..totalPages as i>
							<#if i == pageNum + 1>
								<li><a href="javascript:void(0)" onclick="searchForPage(${i-1})">${i}</a></li>
							<#elseif i_index==showPageNum>
								<#break>
							<#else>
								<li><a href="javascript:void(0)" onclick="searchForPage(${i-1})">${i}</a></li>
							</#if>
						</#list>
					<#else>
						<!-- 当前页到尾页数据小于showPageNum时,显示从尾页为往前的showPageNum条数据 -->
						<#list (totalPages-4)..totalPages as i>
							<li><a href="javascript:void(0)" onclick="searchForPage(${i-1})">${i}</a></li>
						</#list>
					</#if>
				<#else>
				<!-- 数据小于等于n页时,不进行处理 -->
					<#list 1..totalPages as i>
						<li><a href="javascript:void(0)" onclick="searchForPage(${i-1})">${i}</a></li>
					</#list>
				</#if>

                <!--  下一页和尾页 -->
				<#if pageNum == totalPages - 1>
					<li class="disabled"><a href="javascript:void(0)" class="paginate_button next" title="Next">Next</a></li>
					<li class="disabled"><a href="javascript:void(0)" class="paginate_button last" title="Last">Last</a></li>
				<#else>
					<li ><a href="javascript:void(0)" onclick="searchForPage(${pageNum+1})" class="paginate_button next" title="Next">Next</a></li>
					<li ><a href="javascript:void(0)" onclick="searchForPage(${totalPages-1})" class="paginate_button last" title="Last">Last</a></li>
				</#if>

			</ul>
		</div>
	</div>
</div>

<script>
	//输入指定页码进行查询方法	
	function queryPage(obj){
		var selNum = $(obj).val();
		console.log("selNum=" + selNum);
		searchForPage(selNum);
	}
</script>
</#if>
