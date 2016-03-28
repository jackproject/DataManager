

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<base href="<%=basePath%>">

<title>DataManager</title>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link rel="stylesheet" type="text/css" href="resources/extjs/resources/css/ext-all.css">
<link rel="stylesheet" type="text/css" href="resources/css/style.css" />


<!-- 
<script type="text/javascript" src="resources/extjs/ext-all-debug.js"></script>
<script type="text/javascript" src="app.js"></script>
 -->

<script type="text/javascript" src="resources/extjs/ext.js"></script>
<script type="text/javascript" src="app-all.js"></script>
<!-- 
 -->


<style type="text/css">
</style>

<script type="text/javascript">


Ext.require([
	'dm.view.DMItemView',
	'Ext.container.Container'
]);

var pageContainer = null;

Ext.onReady(function () {

	pageContainer = Ext.create('Ext.container.Container', {
		id: 'htContainer',
		width: 800,
		height: 300,
		renderTo: 'grid'
	});


	pageContainer.add({
		xtype: 'dmItemView',
		width: 800,
		height: 300
	});

});

</script>

</head>
<body>

	<br>
    <a href="index.jsp"> 记录查询</a>
	<br>
	<br>

    <div id="grid"></div>
</body>
</html>
