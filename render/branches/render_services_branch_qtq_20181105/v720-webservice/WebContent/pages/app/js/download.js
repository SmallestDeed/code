jQuery(document).ready(function () {
	downPc();
	downIpad()
})

function downPc(){
	$.ajax({
		type: "POST",
		datatype: "json",
		url: cPath + "/system/sysVersion/versionCheck.htm",
		data:"msgId=versionCheck&systemType=2",
		timeout: 5000,
		async: false,
		error: function(request) {
			alert("获取数据失败!");
		},
		success: function(res) {
			var data = eval("(" + res + ")");
			if(data==null){
				$('#pc').attr('href','#'); 
			}else{
				$('#pc').attr('href',data.obj.appPath);
			}
		}
	});
}
function downIpad(){
	$.ajax({
		type: "POST",
		datatype: "json",
		url: cPath + "/system/sysVersion/versionCheck.htm",
		data:"msgId=versionCheck&systemType=3",
		timeout: 5000,
		async: false,
		error: function(request) {
			alert("获取数据失败!");
		},
		success: function(res) {
			var data = eval("(" + res + ")");
			if(data==null){
				$('#ipad').attr('href','#'); 
			}else{
				$('#ipad').attr('href',data.obj.appPath);
			}
		}
	});
}