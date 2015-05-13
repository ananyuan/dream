
<jsp:include page="/views/serial/header_serial.jsp" flush="true" />

<link rel="stylesheet" href="/css/datepicker3.css" />
<link rel="stylesheet" type="text/css" href="/css/dataTables.bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="/css/table.css"/>

<script type="text/javascript" src="/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="/js/dataTables.bootstrap.js"></script>
<script charset="utf-8" src="/js/bootstrap-datepicker.js"></script>
<script charset="utf-8" src="/js/validator.js"></script>



<%@ page import="com.dream.controller.serial.model.InsField"%>
<%@ page import="com.dream.model.DictEntry"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%
	InsField itemObj;
	if (null != request.getAttribute("itemObj")) {
		itemObj = (InsField) request.getAttribute("itemObj");
	} else {
		itemObj = new InsField();
	}
	List<DictEntry> insInputTypes = new ArrayList();
	if (null != request.getAttribute("insInputTypes")) {
		insInputTypes = (List) request.getAttribute("insInputTypes");
	}	
%>

<style>
body {
	background-color:gray;
	padding : 20px;
}

.outter-div {
	background-color : white;
	padding:20px;
	min-height: 700px;
}
.header-table {
	padding-bottom : 10px;
}

.header-table .page-header {
	text-align : center;
}

.input-group.date {
   padding-left: 15px;
   padding-right: 15px;
}

</style>
<body>
<div class="outter-div">
<div class="header-table">
	<h2 class="page-header">
		字段设置
	</h2>
</div>
					<form class="form-horizontal" data-toggle="validator" id="formId_field" style="padding-top:20px">
						<div class="form-group col-sm-6">
							<label for="code" class="col-sm-4 control-label">编码</label> 
							<div class="col-sm-6">
						        <input type="text" class="form-control item-code" id="code" value="<%=itemObj.getCode()%>" required data-error="该项必填">
						        <div class="help-block with-errors"></div>
						    </div>
						</div>
						<div class="form-group col-sm-6">
							<label for="name" class="col-sm-4 control-label">字段名称</label> 
							<div class="col-sm-6">
						        <input type="text" class="form-control item-code" id="name" value="<%=itemObj.getName()%>" required data-error="该项必填">
						        <div class="help-block with-errors"></div>
						    </div>
						</div>


						<div class="form-group col-sm-6">
							<label for="itemtype" class="col-sm-4 control-label">类型</label> 
							<div class="col-sm-6">
						        <select class="form-control item-code" id="itemtype">
						        	<% for (DictEntry inputType: insInputTypes) {
						        		String option = "<option value='"+inputType.getCode()+"' ";
						        		if (inputType.getCode().equals(String.valueOf(itemObj.getItemtype()))) {
						        			option += " selected=\"selected\" ";
						        		}
						        		option +=  ">"+inputType.getName()+"</option>";
						        		
						        		out.print(option);
						        	} %>
						        </select>
						        <div class="help-block with-errors"></div>
						    </div>
						</div>
						<div class="form-group col-sm-6">
							<label for="reldict" class="col-sm-4 control-label">关联字典</label> 
							<div class="col-sm-6">
						        <input type="text" class="form-control item-code" id="reldict" value="<%=itemObj.getReldict()%>">
						        <div class="help-block with-errors"></div>
						    </div>
						</div>

						<div class="form-group col-sm-6">
							<label for="defaultval" class="col-sm-4 control-label">默认值</label> 
							<div class="col-sm-6">
						      <input type="text" class="form-control item-code" id="defaultval" value="<%=itemObj.getDefaultval()%>" >
						      <div class="help-block with-errors"></div>
						    </div>
						</div>
					
						<div class="form-group col-sm-6">
							<label for="sortnum" class="col-sm-4 control-label">排序</label> 
							<div class="col-sm-6">
						      <input type="number" class="form-control item-code" id="sortnum" value="<%=itemObj.getSortnum()%>" data-error="填写数字">
						      <div class="help-block with-errors"></div>
						    </div>
						</div>
					

						<div class="form-group col-sm-6">
							<label for="command" class="col-sm-4 control-label">值变化发起命令</label> 
							<div class="col-sm-6">
						        <input type="text" class="form-control item-code" id="command" value="<%=itemObj.getCommand()%>">
						        <div class="help-block with-errors"></div>
						    </div>
						</div>
						<div class="form-group col-sm-6">
							<label for="cssplus" class="col-sm-4 control-label">扩展样式</label> 
							<div class="col-sm-6">
						        <input type="text" class="form-control item-code" id="cssplus" value="<%=itemObj.getCssplus()%>">
						        <div class="help-block with-errors"></div>
						    </div>
						</div>

						<div class="form-group col-sm-12">
							<label for="extconfig" class="col-sm-2 control-label">扩展配置</label> 
							<div class="col-sm-9">
								<textarea id="extconfig" title="滑块:{'MIN':'20','MAX':'100','STEP':'10'}" name="content" class="form-control item-code" style="height: 80px;"><%=itemObj.getExtconfig()%></textarea>
						        <div class="help-block with-errors"></div>
						    </div>
						</div>
					
						<div class="form-group col-sm-12">
							<div style="text-align: center">
								<button type="button" class="btn btn-primary" id="submit" onclick="saveField()" value="保存" />
								<span class="glyphicon glyphicon-fire" aria-hidden="true"></span>
								保存
								</button>
								<button type="button" class="btn btn-primary" id="back_his" onclick="back()" value="返回" />
								<span class="glyphicon glyphicon-fire" aria-hidden="true"></span>
								返回
								</button>			
							</div>
						</div>
						<input type="hidden" class="item-code" id="id" value="<%=itemObj.getId()%>">
						<input type="hidden" class="item-code" id="insid" value="<%=itemObj.getInsid()%>">
					</form>




</div>
</body>

<script>
$(document).ready(function(){
	$('.input-group.date').datepicker({
	    format: "yyyy-mm-dd"
	});
	
});

function saveField() {
	jQuery("#formId_field").submit();
	
	var xx = jQuery(".has-error").length;
	if (xx > 0) {
		alert("检查输入项");
		
		return;
	}
	
	var param = {};
	jQuery("#formId_field").find(".item-code").each(function(){
		var itemObj = $(this);
		var fieldName = itemObj.attr("id");
		var fieldValue = itemObj.val();
		
		param[fieldName] = fieldValue;
	});

	
 	var rtnResult = sendAjax("/insField/save", param);
 	
 	//window.location.href = "/insDefMgr/list";
}


function back() {
	history.go(-1);
}







</script>
