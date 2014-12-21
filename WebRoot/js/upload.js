
jQuery.getScript("/js/plupload/plupload.full.min.js");
jQuery.getScript("/js/plupload/jquery.plupload.queue/jquery.plupload.queue.min.js");
jQuery.getScript("/js/plupload/i18n/zh_CN.js");


$(function() {
	$("#uploader").bind('show',function(){
		$('#uploader').pluploadQueue({
			url : '/file/upload',
			multipart_params:{dataType:'install'},
			max_file_size : '10mb',
			filters : [
				{title : "Excel files", extensions : "xls,xlsx"}
			],
			rename: false,
			multiple_queues:true,
			prevent_duplicates:true,
			flash_swf_url : '/js/plupload/Moxie.swf',
			silverlight_xap_url : '/js/plupload/Moxie.xap',
			init:{
				Error: function(up, err) {
					//当服务器端返回错误信息时error方法显示错误提示，
					//服务器端返回错误信息会被plupload转换为-200 http错误,
					//所以只能做==-200比较。更好的提示，需要修改插件源代码。
					if(err.code==-200){
						alert("文件格式错误，请检查后重新上传!");
					}
					if(err.code==-602){
						alert("文件已存在!");
					}
				},
			}
		});
	});
	
	
	var uploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : 'pickfiles', // you can pass in id...
		container: document.getElementById('container'), // ... or DOM Element itself
		url : '/file/upload',
		flash_swf_url : '../js/Moxie.swf',
		silverlight_xap_url : '../js/Moxie.xap',
		
		filters : {
			max_file_size : '10mb',
			mime_types: [
				{title : "Image files", extensions : "jpg,gif,png"},
				{title : "Zip files", extensions : "zip"}
			]
		},

		init: {
			PostInit: function() {
				document.getElementById('filelist').innerHTML = '';
				/**
				document.getElementById('uploadfiles').onclick = function() {
					uploader.start();
					return false;
				};*/
			},

			FilesAdded: function(up, files) {
				plupload.each(files, function(file) {
					document.getElementById('filelist').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ') <b></b></div>';
					uploader.start();
				});
			},

			UploadProgress: function(up, file) {
				document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
			},

            FileUploaded: function(up, file, info) {
				/** 
				 * response: "6WrPohFPXtgfRUq3n8kGHm.png"
				   responseHeaders: "Date: Sun, 16 Nov ..."
				   status: 200
				 */
				handleFileInfo(info);
	        },
			
			UploadComplete: function(up, files) {
			},			
			
			Error: function(up, err) {
				document.getElementById('console').innerHTML += "\nError #" + err.code + ": " + err.message;
			}
		}
	});

	uploader.init();	
}); 



