<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.nork.common.util.Utils" %>
<%	
  String versionType = Utils.getValue("sys.version.type","1").trim();
%>	


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="cn">
<head>
	<meta charset="UTF-8">
	<title>室内装修效果图DIY-三度空间</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/pages/app/css/conn.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/pages/app/css/style.css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/pages/app/js/jquery-1.10.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/pages/app/js/slide.js"></script>
	<%
	  String path = request.getContextPath();
	  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + path  ;
	  String style = "app";
	  String jspPath =  path +"/"+"pages/" + style;
	  String cPath =  path +"/" + style;
	  String resPath =  request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + path +"/"+"pages/" + style +"/" ;
	  String resUrl = Utils.getValue("app.resources.url","http://localhost:8008");
	%>
	<script >

	    var cPath = '<%=cPath%>';
		var resUrl = '<%=resUrl%>';
		$(document).ready(function() {
			$(".slideInner").slide({
				slideContainer: $('.slideInner a'),
				effect: 'easeOutCirc',
				autoRunTime: 5000,
				slideSpeed: 1000,
				nav: true,
				autoRun: true,
				prevBtn: $('a.prev'),
				nextBtn: $('a.next')
			});
			$.ajax({
				type: "POST",
				datatype: "json",
				url: cPath + "/web/system/sysVersion/versionCheck2.htm",
				data:"msgId=versionCheck&systemType=2",
				timeout: 5000,
				async: false,
				error: function(request) {
					alert("获取数据失败!");
				},
				success: function(res) {
					var data = eval("(" + res + ")");
					if(data.obj.sysVersionType == "1"){
						$('#displayPc').show();
						$('#displayInternalPc').hide();
						$('#displayTestPc').hide();
					}else{
						$('#displayInternalPc').show();
						$('#displayTestPc').show();
						$('#displayPc').hide();
					}
					if( data.success ){
						if(data==null || data.obj==null){
							$('#versionNo').text('0.6');
						}else{
							$('#versionNo').text(data.obj.version);
						}
					}else{
						$('#versionNo').text('0.6');
					}
				}
			});
		})

		function downApp(sysType,versionType){
			 
			$.ajax({
				type: "POST",
				datatype: "json",
				url: cPath + "/web/system/sysVersion/versionCheck2.htm",
				data:"msgId=versionCheck&systemType="+sysType,
				timeout: 5000,
				async: false,
				error: function(request) {
					alert("获取数据失败!");
				},
				success: function(res) {
					var data = eval("(" + res + ")");
					if(data==null){
						$('#pc').attr('href','#');
						$('#internalPc').attr('href','#');
						$('#testPc').attr('href','#');
					}else{
						if(versionType == 1){
							/* 下载地址合法判断*/
// 						var url=resUrl+data.obj.filePath;
// 						if(!strReExp(url)){
							window.location.href=resUrl+data.obj.filePath;
// 						}
						}else if(versionType == 2){
// 							var url=resUrl+data.obj.internalFilePath;
// 							if(!strReExp(url)){
							window.location.href=resUrl+data.obj.internalFilePath; 
// 							}
						}else if(versionType == 3){
// 							var url=resUrl+data.obj.testFilePath;
// 							if(!strReExp(url)){
							window.location.href=resUrl+data.obj.testFilePath;
// 							}
						}else if(versionType == 4){
// 							var url=resUrl+data.obj.filePath32;
// 							if(!strReExp(url)){
							window.location.href=resUrl+data.obj.filePath32;
// 							}
						}
					}
				}
			});
		}
// 		 function strReExp(filePath){
// 			if(filePath==null||filePath==""){
// 					alert('地址错误！');
// 					return true;
// 			}
// 			var index=filePath.indexOf("/");
// 			var path=filePath.substring(index,index+32);
// 			if(path!="/pic/a_common/system/sysVersion/"){
// 				alert('地址错误！');
// 				return true;
// 			}
// 			if(path!="/a_common/system/sysVersion/"){
// 				alert('地址错误！');
// 				return true;
// 			}
// 			return false;
// 		}
		
	</script>
</head>
<body>

	<div id="head">
		<div id="site-nav">
			<div class="site-nav-bd w1180">
			    <div class="fr site-user">
			    	<ul class="clearfix J_user-actions">
						<li class="s-item s-arrive">
							<a class="s-item-link" href="#">客户端下载</a>
						</li>						
						<li class="s-item s-arrive">
							<a class="s-item-link" href="javascript:;">登录</a>
						</li>
						<li class="s-item s-arrive">
							<a class="s-item-link" href="javascript:;">注册</a>
						</li>						
					</ul>
			    </div>
			</div>
		</div>
		<div class="nav-main">
			<div class="nva-wrap clearfix w1180">
				<div class="h-logo">
					<h1>
						<a class="pngfix" href="javascript:;" title="三度空间"></a>
					</h1>
				</div>
				<div class="nav-menu fl">
					<ul class="fl">
						<li class="h-item h-cur"><a href="javascript:;" class="action">首页</a></li>
						<li class="h-item"><a href="" class="action">样板间</a></li>
						<li class="h-item"><a href="" class="action">品牌馆</a></li>
						<li class="h-item"><a href="" class="action">装修吧</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
<%-- ${versionType} --%>
<%-- ${pageScope.versionType}  --%>
	<div id="content" class="whiteBg">
		<div class="download ">
			<div class="left">
				<img src="${pageContext.request.contextPath}/pages/app/image/dlogo.png" alt="">
				<div class="version">版本：V<span id="versionNo"></span>版</div>
				<div class="tips">
					<img src="${pageContext.request.contextPath}/pages/app/image/tips.png" alt="" >
					<h5 href="">下载前请确保360等杀毒软件已关闭或将本软件已加入到安全信任列表中！</h5>
				</div>
	 
				<c:set var="versionType" value="<%=versionType%>"/> 
				 
<%-- 				<c:if test="${versionType == 1}"> --%>
					<div id="displayPc" class="downPc" style="display:none;" ><a  id="pc"  href="javascript:void(0);" onclick="downApp(2,1);">下载PC版${versionType}</a></div>
<!-- 					<div class="downPc"><a  id="pc"  href="javascript:void(0);" onclick="downApp(2,4);">下载PC版(32位)</a></div> -->
<!-- 				</c:if> -->
				<c:if test="${versionType == 2}">
					<div id="displayInternalPc" class="downPc"  style="display:none;" ><a  id="internalPc"  href="javascript:void(0);" onclick="downApp(2,2);">内部-下载PC版</a></div>
					<div id="displayTestPc" class="downPc"  style="display:none;" ><a  id="testPc"  href="javascript:void(0);" onclick="downApp(2,3);">调试-下载PC版</a></div>
				</c:if>
			</div>
			<div class="right">
				<img src="${pageContext.request.contextPath}/pages/app/image/banner/slide3p2.jpg" alt="">
			</div>
		</div>
			
	</div>		

	<div id="footer" >				
		<p>
			<a href="http://www.sanduspace.cn">?版权所有 . 三度空间  sanduspace.cn</a> | 
			<img src="${pageContext.request.contextPath}/pages/app/image/ghs.png" />
<!-- 			<a href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=33010602001840" target="_blank">京公网安备33010602001840号</a> -->
			<a href="http://www.miitbeian.gov.cn/" target="_blank">粤ICP备16107772号-1</a>
		</p>
	</div>

</body>
</html>