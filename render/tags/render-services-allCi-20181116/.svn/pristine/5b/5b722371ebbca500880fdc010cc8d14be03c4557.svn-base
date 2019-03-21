jQuery(document).ready(function () {
	var autoFlag = $('#autoFlag').attr("value");
	if(autoFlag){
	   searchClick();
	}
});

function searchClick(){
	  $("#mid_right").load($('#searchForm').attr("action"),$('#searchForm').serialize());
}

function openPage(type,id) {
	var url='',obj='',style='';
	if(type=='add'){
	   url = jspPath + '/system/resVersion/resVersion_add.jsp';
	   obj = window;
	}
	
	style='dialogWidth=614px;dialogHeight=407px;dialogLeft=400px;dialogTop=150px;resizable=no;status=no;scroll=false;help=no;center=yes;';
	
	if(type=='edit'){
		url = cPath + '/system/resVersion/jspget.htm?pageType=edit&id='+id;
		obj = window;
	}

	if(type=='view'){
		url = cPath + '/system/resVersion/jspget.htm?pageType=view&id='+id;
		obj = window;
	}
	
	window.showModalDialog(url,obj,style);
}

	  
function del(id){
	var confirm_ = confirm('确定删除该条数据？');
      if(confirm_){
      	$.ajax({
	        type: "POST",
	        datatype: "json",
	        url: cPath + "/system/resVersion/del.htm",
	        data:"ids="+id,
			timeout: 2000,
			async: true,
			error: function(request) {
	            alert("数据删除失败!");
	        },
			success: function(res) {
	        	    var data=eval("("+res+")");
	        	    if(data.success){
	        	       alert("数据删除成功!");
	        	       var t1 = $('#searchForm').attr("action");
	        		   var t2 = $('#searchForm').serialize();
	        		   var t =  $('#mid_right');
	        		   t.load(t1,t);
			}
	        }
	    });
     }
  }
