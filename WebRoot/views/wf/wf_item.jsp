<jsp:include page="../header_banner_no.jsp" flush="true" />

<link rel="stylesheet" type="text/css" href="/css/wf/joint.css" />

<style>
    #paper {
        height: 1200px;
        border: 1px solid gray;
        background-color : white;
    }
</style>


<%@ page import="com.dream.model.wf.WfBean" %>

<% 
WfBean wfBean;
if (null != request.getAttribute("itemObj")) {
	wfBean = (WfBean)request.getAttribute("itemObj");	
} else {
	wfBean = new WfBean();
}

%>


<div class="outter-div">

<div class="row">
	<div class="center">
		<h2>流程定义</h2>
	</div>
	
	<div class="col-md-8">
		<button type="button" class="btn btn-primary" id="back_wf" onclick="back()"/>
		<span class="glyphicon glyphicon-fire" aria-hidden="true"></span>
		返回
		</button>
	</div>

	<div class="left-div col-md-9">
		<div id="paper"></div>
	</div>
	
	<div class="right-div col-md-3">
		<button type="button" class="btn btn-primary" id="add_node" onclick="addNode()"/>
		<span class="glyphicon glyphicon-fire" aria-hidden="true"></span>
		添加节点
		</button>

		<button type="button" class="btn btn-primary" id="save_wf" onclick="saveWf()"/>
		<span class="glyphicon glyphicon-fire" aria-hidden="true"></span>
		保存流程
		</button>

	
		<div style="display:none" class="view-node" id="view_node">
		
			<form class="form-horizontal" id="form_node">
				<div class="center"><h4>节点属性</h4></div>
			
				<div class="form-group col-sm-12 sm-padding-8">
					<label for="node_id" class="col-sm-3 control-label sm-padding-8">ID</label> 
					<div class="col-sm-9 sm-padding-8">
				        <input type="text" class="form-control item-code" id="node_id" value="" readonly>
				    </div>
				</div>
				<div class="form-group col-sm-12 sm-padding-8">
					<label for="node_name" class="col-sm-3 control-label sm-padding-8">名称</label> 
					<div class="col-sm-9 sm-padding-8">
				        <input type="text" class="form-control item-code" id="node_name" value="">
				    </div>
				</div>
				<div class="form-group col-sm-12 sm-padding-8">
					<label for="node_name" class="col-sm-3 control-label sm-padding-8">说明</label> 
					<div class="col-sm-9 sm-padding-8">
				        <input type="text" class="form-control item-code" id="node_desc" value="">
				    </div>
				</div>
				
				
				
				<div class="col-sm-12 center"><h4>送交属性</h4></div>			
				
				<div class="form-group col-sm-12 sm-padding-8">
					<label for="node_org_dept" class="col-sm-3 control-label sm-padding-8">部门</label> 
					<div class="col-sm-9 sm-padding-8">
				        <input type="text" class="form-control item-code" id="node_org_dept" value="">
				    </div>
				</div>				
				
			</form>
			
			<div class="center">
					<button type="button" class="btn btn-primary" id="save_node" onclick="saveNode()"/>
					<span class="glyphicon glyphicon-fire" aria-hidden="true"></span>
					保存
					</button>	
					
					<button type="button" class="btn btn-primary" id="remove_node" onclick="removeNode()"/>
					<span class="glyphicon glyphicon-fire" aria-hidden="true"></span>
					删除
					</button>
			</div>
			
		</div>
		
		
		<div style="display:none" class="view-link" id="view_link">
			<div class="center"><h4>连线属性</h4></div>
		
			<form class="form-horizontal" id="form_link">
				<div class="form-group col-sm-12 sm-padding-8">
					<label for="link_id" class="col-sm-3 control-label sm-padding-8">ID</label> 
					<div class="col-sm-9 sm-padding-8">
				        <input type="text" class="form-control item-code" id="link_id" value="" readonly>
				    </div>
				</div>
				<div class="form-group col-sm-12 sm-padding-8">
					<label for="link_label" class="col-sm-3 control-label sm-padding-8">显示</label> 
					<div class="col-sm-9 sm-padding-8">
				        <input type="text" class="form-control item-code" id="link_label" value="">
				    </div>
				</div>									
			</form>
			
			<div class="center">
					<button type="button" class="btn btn-primary" id="save_link" onclick="saveLink()"/>
					<span class="glyphicon glyphicon-fire" aria-hidden="true"></span>
					保存
					</button>	
					
					<button type="button" class="btn btn-primary" id="remove_link" onclick="removeLink()"/>
					<span class="glyphicon glyphicon-fire" aria-hidden="true"></span>
					删除
					</button>
			</div>
		</div>
		
		<div class="view-wf" id="view_wf">
			<div class="center"><h4>流程定义</h4></div>
		
			<form class="form-horizontal" id="form_link">
				<div class="form-group col-sm-12 sm-padding-8">
					<label for="wf_code" class="col-sm-3 control-label sm-padding-8">编码</label> 
					<div class="col-sm-9 sm-padding-8">
				        <input type="text" class="form-control item-code" id="wf_code" value="<%=wfBean.getCode() %>">
				    </div>
				</div>		
				<div class="form-group col-sm-12 sm-padding-8">
					<label for="wf_name" class="col-sm-3 control-label sm-padding-8">名称</label> 
					<div class="col-sm-9 sm-padding-8">
				        <input type="text" class="form-control item-code" id="wf_name" value="<%=wfBean.getName() %>">
				    </div>
				</div>											
			</form>
		</div>		
	</div>
</div>



</div>



<script src="/js/wf/joint.js"></script>
<script src="/js/wf/joint.shapes.org.js"></script>
<script src="/js/wf/joint.shapes.fsa.js"></script>
<script src="/js/wf/wf.js"></script>

<script>
var wfDef = <%=wfBean.getJsondef()%>;

jQuery(document).ready(function(){
	resetFrameHei();

	jQuery("#v-2").width(jQuery("#paper").width()); //重新设置画图框的宽度
	
	initWf(wfDef);
});

function back(){
	window.location.href = "/wfdef/list";
}

</script>



