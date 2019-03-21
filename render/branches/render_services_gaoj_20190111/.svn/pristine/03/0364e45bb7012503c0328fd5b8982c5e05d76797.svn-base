<%@ page language="java" import="org.apache.log4j.Logger" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>异常处理页面</title>
</head>
<body>
<H2>
<%--<% Exception ex = (Exception) request.getAttribute("Exception");%>--%>
<%--<H2>Exception:<%=ex.getMessage()%>--%>
<%  
    Exception exception = (Exception)request.getAttribute("exception");  
    final Logger logger = Logger.getRootLogger();  
    logger.error(exception.getMessage(),exception);  
%>  
</H2>
</body>
</html>