<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.nork.common.model.ResponseEnvelope" %>
<%@ page import="com.nork.common.model.LoginUser" %>
<%@ page import="com.nork.common.util.Utils" %>
<jsp:useBean id="now" class="java.util.Date" /> 
<%
    String version = "1";
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + path  ;
	String style = "app";
	String jspPath =  path +"/"+"pages/" + style;
	String cPath =  path +"/" + style;
	String resPath =  request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + path +"/"+"pages/" + style +"/" ;
	ResponseEnvelope res = (ResponseEnvelope)request.getAttribute("result");
	LoginUser  user = request.getSession()==null?null:(LoginUser)request.getSession().getAttribute("loginUser");
	String loginName = (user==null?"":user.getLoginName());
	Integer userId = (user==null?-1:user.getId());
	String name = (user==null?"":user.getName());
	String picPath = (user==null?path+"/pages/online/images/noPicture/NoPicture(180X135).jpg":user.getPicPath());
	String _version = Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6);
	String resUrl = Utils.getValue("app.resources.url","http://localhost:89");
	String wsSiteUrl =Utils.getValue("ws.server.site.url","ws://localhost:8080/app").trim();
%>

<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/app/jquery-ui.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/app/js/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/app/js/jquery/jquery.ui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/pages/app/js/jquery-1.7.2.min.js"></script>


<!-- <script type='text/javascript' src='https://ssl-webplayer.unity3d.com/download_webplayer-3.x/3.0/uo/jquery.min.js'></script> -->
<script type="text/javascript">
<!--
/* var unityObjectUrl = "http://webplayer.unity3d.com/download_webplayer-3.x/3.0/uo/UnityObject2.js";
if (document.location.protocol == 'https:')
	unityObjectUrl = unityObjectUrl.replace("http://", "https://ssl-");
document.write('<script type="text\/javascript" src="' + unityObjectUrl + '"><\/script>'); */
-->
</script>
<script type="text/javascript">
	$(document).ready(function(){
		$(".nav li a").each(function(){
			var num= $(this);
			if(num[0].href==String(window.location)){
				$(this).addClass("current");
			}
		});
	});
/* var storageServers = "";
var config = {
	backgroundcolor: "A0A0A0",
	width: 1024, 
	height: 768,
	params: { enableDebugging:"0" 
		//logoimage: storageServers + "image/others/login_logo.png?" + webVersion,
      // progressbarimage: storageServers + "image/others/progress_bar_in.png?" + webVersion,
       //progressframeimage: storageServers + "image/others/progress_bar.png?"+webVersion,
       //baseDownloadUrl: "http://wp-china.unity3d.com/download_webplayer-3.x/",
       //autoupdateURL: "http://wp-china.unity3d.com/autodownload_webplugin-3.x",
       //autoupdateURLSignature: "02a5f78b3066d7d31fb063186a2eec36fdf1205d49c6b0808eb37ef85ed9902e2e1904d87f599238a802ba0abbfe4f18aa82dd2eb5171e99ba839a5cea9e6ea9c1be9eae505937b56fe4a5fd254cffe08958d961f42d970136b5eab9e6c2cd08b81bc8a11e5ade57dc63dcfef2248d89689e4d4feed3cdfe7374c848fd57ebd4"  		 
	}
	
};
config.params["disableContextMenu"] = true;
var u = new UnityObject2(config);

jQuery(function() {

	var $missingScreen = jQuery("#unityPlayer").find(".missing");
	var $brokenScreen = jQuery("#unityPlayer").find(".broken");
	$missingScreen.hide();
	$brokenScreen.hide();
	
	u.observeProgress(function (progress) {
		switch(progress.pluginStatus) {
			case "broken":
				$brokenScreen.find("a").click(function (e) {
					e.stopPropagation();
					e.preventDefault();
					u.installPlugin();
					return false;
				});
				$brokenScreen.show();
			break;
			case "missing":
				$missingScreen.find("a").click(function (e) {
					e.stopPropagation();
					e.preventDefault();
					u.installPlugin();
					return false;
				});
				$missingScreen.show();
			break;
			case "installed":
				$missingScreen.remove();
			break;
			case "first":
			break;
		}
	});
	//var baseWebplayer = "http://localhost:8080/onlineDecorate/pages/online/test/exp.unity3d";
    var baseWebplayer = "main.unity3d";
	u.initPlugin(jQuery("#unityPlayer")[0], baseWebplayer); 
});*/
</script>
<script type="text/javascript">
    var path = '<%=path%>';
    var _path_ = '<%=path%>';
	var cPath = '<%=cPath%>';
	var resPath = '<%=resPath%>';
	var basePath = '<%=basePath%>';
	var jspPath = '<%=jspPath%>';
	var userId = '<%=userId%>';
	var loginName = '<%=loginName%>';
	var picPath = '<%=picPath%>';
	var _version = '<%=_version%>';
	var resUrl = '<%=resUrl%>';
	var wsSiteUrl  = '<%=wsSiteUrl%>';
/* 	if( loginName == null || loginName == ''){
		window.location.href = path + "/login.jsp";
	} */
	var errorPlacement = 
		function(error, element){
			var placement = element.parent();
				placement.css("color", "#FF0000").css("font-size","12px").css("font-family","宋体");
				error.appendTo(placement);
	}
</script>