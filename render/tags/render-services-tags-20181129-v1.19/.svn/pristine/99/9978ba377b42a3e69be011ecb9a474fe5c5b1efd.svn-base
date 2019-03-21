jQuery(document).ready(function () {
		var validator = $("#addForm").validate({
			rules:{
				/* strAtt:{required:true,minlength:1,maxlength:64} */
	 	    }
	       ,messages : {
			    /* strAtt:{required : "&nbsp;请输入字符串",minlength:"&nbsp;字符串不能少于1个字符！",maxlength:"&nbsp;字符串不能超过64个字符！"} */
	       }
	       ,errorPlacement : errorPlacement
	       ,submitHandler:function(form){
	           ajaxSubmit();
	       }
		});
		
		$("#addreset").click(function(){
			 validator.resetForm();
		});
})

function ajaxSubmit(){
    $.ajax({
        type: "POST",
        datatype: "json",
        url: cPath + "/system/resVersion/save.htm",
		timeout: 2000,
		async: false,
		data:$('#addForm').serialize(),// 
		error: function(request) {
            alert("数据保存失败!");
        },
		success: function(res) {
        	    var data=eval("("+res+")");
        	    if(data.success){
        	     	alert("数据保存成功!");
        	        var t1 = $('#searchForm',window.opener.document).attr("action");
        			var t2 = $('#searchForm',window.opener.document).serialize();
        			var t = $('#mid_right',window.opener.document);
        			$.ajax({
        		        type: "POST",
        		        datatype: "json",
        		        url: t1,
        		        data:t2,
        				timeout: 2000,
        				async: true,
        				error: function(request) {
        		          
        		        },
        				success: function(data) {
        					 t.html(data);
        		        	 window.close();
        		        }
        		    }); 
        	    }else{
        	    	alert(data.message);
        	    }
        }
    });
}
