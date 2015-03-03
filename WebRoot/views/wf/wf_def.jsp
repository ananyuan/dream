<jsp:include page="../header_banner_no.jsp" flush="true" />

<link rel="stylesheet" type="text/css" href="/css/wf/joint.css" />

<style>
    #paper {
        height: 1200px;
        border: 1px solid gray;
        background-color : white;
    }
</style>

<div class="outter-div">

<div class="row">
	<div class="center">
		<h2>流程定义</h2>
	</div>

	<div class="left-div col-md-9">
		<div id="paper"></div>
	</div>
	
	<div class="right-div col-md-3">
		<button type="button" class="btn btn-primary" id="add_node" onclick="addNode()"/>
		<span class="glyphicon glyphicon-fire" aria-hidden="true"></span>
		添加节点
		</button>	
	
		<div style="display:none" class="view-node" id="view_node">
			<div>id <input type="text" id="node_id" value="" readonly></div>
		
			<div>name <input type="text" id="node_name" value=""></div>
			
			<div>desc <input type="text" id="node_desc" value=""></div>
			
			<div>
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
			<div>id <input type="text" id="link_id" value="" readonly></div>
			
			<div>label <input type="text" id="link_label" value=""></div>
			<div>
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
	</div>
</div>



</div>



<script src="/js/wf/joint.js"></script>
<script src="/js/wf/joint.shapes.org.js"></script>
<script src="/js/wf/joint.shapes.fsa.js"></script>
<script src="/js/wf/wf.js"></script>

<script>
jQuery(document).ready(function(){
	resetFrameHei();

	jQuery("#v-2").width(jQuery("#paper").width()); //重新设置画图框的宽度
});

</script>



