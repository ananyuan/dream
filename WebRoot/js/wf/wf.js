var graph = new joint.dia.Graph;
var nodes = [];
var lines = [];

var paper = new joint.dia.Paper({
    el: $('#paper'),
    width: 800,
    height: 600,
    gridSize: 1,
    model: graph,
    perpendicularLinks: false
});

/**
 * 空白处点击，则隐藏右边所有
 */
paper.on('blank:pointerdown', 
    function(cellView, evt, x, y) { 
	    jQuery("#view_wf").show();
		jQuery("#view_node").hide();
		jQuery("#view_link").hide();	    
    }
);


paper.on('cell:pointerup', 
    function(cellView, evt, x, y) { 
		if (cellView.sourceMagnet && !cellView.targetMagnet) { //鼠标up的时候， 在两点之间划上一条线，  存在sourceMagnet
			var srcObjId = cellView.sourceMagnet.parentElement.parentElement.parentElement.getAttribute("model-id");
			
			var targetObjId = evt.toElement.parentElement.parentElement.parentElement.getAttribute("model-id");
			
			//删除直线
			var allLinks = graph.getLinks();
			
			var existLink = false;
			
			jQuery.each(allLinks, function(index, item){
				if (item.attributes.type != "fsa.Arrow") {
					item.remove();
				}
				
				if (item.attributes.source.id == srcObjId && item.attributes.target.id == targetObjId) {
					existLink = true;
				}
			});
			
			if (!existLink) {
				//目标点在节点上				
				var srcModel = graph.getCell(srcObjId);
				
				var targetModel = graph.getCell(targetObjId);
				
				//创建曲线
				var newLinkId = randomStr();
				link(srcModel, targetModel, "", undefined, newLinkId);
				
			    var extObj = {};
			    setLineInfo(newLinkId, extObj);    
			}
		}
    }
);

/**
 * 双击， 删除
 */
paper.on('cell:pointerdblclick', 
    function(cellView, evt, x, y) {
	    var cellObj = cellView.model;
	    
	    var modelType = cellObj.get("type");
	    
		if (modelType == "org.Member") {
			//removeNode(cellView.model);
			showNodeView(cellObj);
			
		} else if (modelType == "fsa.Arrow") {
			showLinkView(cellObj);
		}
    }
);

/**
 * 单击， 修改属性
 */
paper.on('cell:pointerclick', 
    function(cellView, evt, x, y) { 
		var modelType = cellView.model.attributes.type;
		if (modelType == "org.Member") {  //节点
	        //显示节点，属性
			
			var objId = cellView.model.id;
		} else if (modelType == "fsa.Arrow") { //线
			//显示线属性
			
			
		}
    }
);


var member = function(id, x, y, rank, name, image, background, border) {

    var cell = new joint.shapes.org.Member({
		id: id,
        position: { x: x, y: y },
        attrs: {
            '.card': { fill: background, stroke: border},
              image: { 'xlink:href': '/css/images/wf/'+ image , magnet: true},
            '.rank': { text: rank }, '.name': { text: name }
        }
    });
    graph.addCell(cell);
    return cell;
};


function link(source, target, label, vertices, linkId) {
    
    var cell = new joint.shapes.fsa.Arrow({
        source: { id: source.id },
        target: { id: target.id },
        id: linkId,
        labels: [{ position: .5, attrs: { text: { text: label || '', 'font-weight': 'bold' } } }],
        vertices: vertices || []
    });
    graph.addCell(cell);
    return cell;
}

function addNode(){
	var newNodeId = randomStr();
	
	member(newNodeId,0,0,'审核', '审核节点', 'member1.png', '#F1C40F', 'gray');	
	
    var extObj = {};
    setNodeInfo(newNodeId, extObj);	
}

/**
 * 
 * @param nodeId 节点ID
 */
function setNodeInfo(nodeId, extInfo) {
	var nodeObj = graph.getCell(nodeId);
	var baseObj = {};
	baseObj.ndesc = nodeObj.attributes.attrs[".rank"].text;
	baseObj.nname = nodeObj.attributes.attrs[".name"].text;
	baseObj.id = nodeId;
    
	if (!extInfo) {
		extInfo = {};
	}
	
	var color = nodeObj.get("attrs")[".card"].fill;
	if (color == "#AAAFFF") {
		extInfo.startNode = "1";
	}
	
	//先判断是否已经有了
	jQuery.each(nodes, function(index, item){
		if (item.id == nodeId) { //已经存在了该ID的节点数据，则先删除
			nodes.splice(index, 1);
		}
	});
	
	nodes.push(jQuery.extend(baseObj, extInfo));  //节点的所有信息
}

/**
 * 
 * @param linkId 连线ID
 */
function setLineInfo(linkId, extInfo) {
	var linkObj = graph.getCell(linkId);
	var baseObj = {};
	
	baseObj.label = linkObj.attributes.labels[0].attrs.text.text;	
	baseObj.source = linkObj.get("source").id;
	baseObj.target = linkObj.get("target").id;
	baseObj.id = linkId;
	
	if (!extInfo) {
		extInfo = {};
	}
	
	//先判断是否已经有了
	jQuery.each(lines, function(index, item){
		if (item.id == linkId) { //已经存在了该ID的连线数据，则先删除
			lines.splice(index, 1);
		}
	});
	
	lines.push(jQuery.extend(baseObj, extInfo));  //节点的所有信息
}


/**
 * 删除节点
 * @param cellObj 节点对象
 */
function removeNode(cellObj) {
	if (!confirm("确定删除该节点?")) {
		return;
	}
	
	if (!cellObj) {
		var nodeId = jQuery("#node_id").val();
		cellObj = graph.getCell(nodeId);
	}
	
    //删除关联的线
    var opt = {deep : true};
    var relLinks = graph.getConnectedLinks(cellObj, opt);
    
	jQuery.each(relLinks, function(index, item){
		item.remove();
	});
    
    //删除节点
	cellObj.remove();		
}


/**
 * 保存节点数据
 */
function saveNode() {
	var nodeId = jQuery("#node_id").val();
	var nodeName = jQuery("#node_name").val();
	var nodeDesc = jQuery("#node_desc").val();
	
	
	var nodeObj = graph.getCell(nodeId);
	
	nodeObj.attributes.attrs[".rank"].text = nodeDesc;
    nodeObj.attributes.attrs[".name"].text = nodeName;
	
    nodeObj.findView(paper).render();
    
    var extObj = {};
    
    setNodeInfo(nodeId, extObj);
}

/**
 * 保存线数据
 */
function saveLink() {
	var linkId = jQuery("#link_id").val();
	var linkLabel = jQuery("#link_label").val();
	
	var linkObj = graph.getCell(linkId);
	
	linkObj.attributes.labels[0].attrs.text.text = linkLabel;
	
    linkObj.findView(paper).render();	
    
    var extObj = {};
    setLineInfo(linkId, extObj);    
}


/**
 * 
 * @param cellObj 节点对象
 */
function showNodeView(cellObj) {
	var cellObjId = cellObj.id;
	var nodeName = cellObj.get("attrs")[".name"].text;
	var nodeDesc = cellObj.get("attrs")[".rank"].text;
	
	jQuery("#view_wf").hide();
	jQuery("#view_node").show();
	jQuery("#view_link").hide();
	
	jQuery("#node_id").val(cellObjId);
	jQuery("#node_name").val(nodeName);
	jQuery("#node_desc").val(nodeDesc);
}

/**
 * 
 * @param linkObj 连线对象
 */
function showLinkView(linkObj) {
	var cellObjId = linkObj.id;
	var linkLabel = linkObj.get("labels")[0].attrs.text.text;
	
	
	jQuery("#view_wf").hide();
	jQuery("#view_node").hide();
	jQuery("#view_link").show();
	
	jQuery("#link_id").val(cellObjId);	
	jQuery("#link_label").val(linkLabel);
	
}

function saveWf() {
	var param = {};
	param.wfdef = JSON.stringify(graph.toJSON());
	param.wfcode = jQuery("#wf_code").val();
	param.wfname = jQuery("#wf_name").val();
	
	param.nodedef = nodes;
	param.linedef = lines;
	
	sendAjax("/wfdef/save", param);
	
	alert("流程保存成功");
}



/**
var bart = member('a',300,70,'CEO', 'Bart Simpson', 'member1.png', '#F1C40F', 'gray');
var homer = member('b',90,200,'VP Marketing', 'Homer Simpson', 'member2.png', '#2ECC71', '#008e09');
var marge = member('c',300,200,'VP Sales', 'Marge Simpson', 'member3.png', '#2ECC71', '#008e09');
var lisa = member('d',500,200,'VP Production' , 'Lisa Simpson', 'member4.png', '#2ECC71', '#008e09');
var maggie = member('e',400,350,'Manager', 'Maggie Simpson', 'member5.png', '#3498DB', '#333');
var lenny = member('f',190,350,'Manager', 'Lenny Leonard', 'member6.png', '#3498DB', '#333');
var carl = member('g',190,500,'Manager', 'Carl Carlson', 'member7.png', '#3498DB', '#333');

link(bart, marge, "", [{x: 385, y: 180}]);
link(bart, homer, "", [{x: 385, y: 180}, {x: 175, y: 180}]);
link(bart, lisa, "", [{x: 385, y: 180}, {x: 585, y: 180}]);
link(homer, lenny, "", [{x:175 , y: 380}]);
link(homer, carl, "", [{x:175 , y: 530}]);
link(marge, maggie, "", [{x:385 , y: 380}]);

//var jsonGraphString = {"cells":[{"type":"org.Member","size":{"width":180,"height":70},"position":{"x":162,"y":16},"angle":0,"id":"toaba2o7xg4g3nmi","z":1,"attrs":{".card":{"fill":"#AAAFFF","stroke":"gray"},"image":{"xlink:href":"/css/images/wf/member1.png","magnet":true},".rank":{"text":"起草"},".name":{"text":"起草审批单"}}},{"type":"org.Member","size":{"width":180,"height":70},"position":{"x":425,"y":128},"angle":0,"id":"375xpwh3fas8aor","z":2,"attrs":{".card":{"fill":"#F1C40F","stroke":"gray"},"image":{"xlink:href":"/css/images/wf/member1.png","magnet":true},".rank":{"text":"审核1"},".name":{"text":"审核节点"}}},{"type":"org.Member","size":{"width":180,"height":70},"position":{"x":264,"y":381},"angle":0,"id":"869thnatc4ayk3xr","z":3,"attrs":{".card":{"fill":"#F1C40F","stroke":"gray"},"image":{"xlink:href":"/css/images/wf/member1.png","magnet":true},".rank":{"text":"审核2"},".name":{"text":"审核节点"}}},{"type":"fsa.Arrow","smooth":true,"source":{"id":"toaba2o7xg4g3nmi"},"target":{"id":"375xpwh3fas8aor"},"id":"a1r651tps24kj4i","labels":[{"position":0.5,"attrs":{"text":{"text":"","font-weight":"bold"}}}],"vertices":[],"z":4,"attrs":{}},{"type":"fsa.Arrow","smooth":true,"source":{"id":"375xpwh3fas8aor"},"target":{"id":"869thnatc4ayk3xr"},"id":"4hrtn8vll61lwhfr","labels":[{"position":0.5,"attrs":{"text":{"text":"","font-weight":"bold"}}}],"vertices":[],"z":5,"attrs":{}}]};

*/






/**
 * 初始化
 * @param wfDefObj
 */
function initWf(wfDefObj) {
	if (wfDefObj && wfDefObj.cells) {
		graph.fromJSON(wfDefObj);	
	} else { //新添加
		var firstNodeId = randomStr();
		var secondNodeId = randomStr();
		var thirdNodeId = randomStr();
		var firstLinkId = randomStr();
		var secondLinkId = randomStr();
		 
		var jsonGraphString = {"cells":[{"type":"org.Member","size":{"width":180,"height":70},"position":{"x":162,"y":16},"angle":0,"id":firstNodeId,"z":1,"attrs":{".card":{"fill":"#AAAFFF","stroke":"gray"},"image":{"xlink:href":"/css/images/wf/member1.png","magnet":true},".rank":{"text":"起草"},".name":{"text":"起草审批单"}}},{"type":"org.Member","size":{"width":180,"height":70},"position":{"x":425,"y":128},"angle":0,"id":secondNodeId,"z":2,"attrs":{".card":{"fill":"#F1C40F","stroke":"gray"},"image":{"xlink:href":"/css/images/wf/member1.png","magnet":true},".rank":{"text":"审核1"},".name":{"text":"审核节点"}}},{"type":"org.Member","size":{"width":180,"height":70},"position":{"x":264,"y":381},"angle":0,"id":thirdNodeId,"z":3,"attrs":{".card":{"fill":"#F1C40F","stroke":"gray"},"image":{"xlink:href":"/css/images/wf/member1.png","magnet":true},".rank":{"text":"审核2"},".name":{"text":"审核节点"}}},{"type":"fsa.Arrow","smooth":true,"source":{"id":firstNodeId},"target":{"id":secondNodeId},"id":firstLinkId,"labels":[{"position":0.5,"attrs":{"text":{"text":"","font-weight":"bold"}}}],"vertices":[],"z":4,"attrs":{}},{"type":"fsa.Arrow","smooth":true,"source":{"id":secondNodeId},"target":{"id":thirdNodeId},"id":secondLinkId,"labels":[{"position":0.5,"attrs":{"text":{"text":"","font-weight":"bold"}}}],"vertices":[],"z":5,"attrs":{}}]};		
		
		
		graph.fromJSON(jsonGraphString);
		
		//初始化节点
		jQuery.each(graph.getElements(), function(index, item){
			setNodeInfo(item.get("id"));
		});
		
		//初始化连线
		jQuery.each(graph.getLinks(), function(index, item){
			setLineInfo(item.get("id"));
		});
	}
}

