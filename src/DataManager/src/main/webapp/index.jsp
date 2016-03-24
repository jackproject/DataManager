
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

<style type="text/css">
</style>
	
<script type="text/javascript">


Ext.require([
	'dm.view.DMRecordView',
	'Ext.container.Container'
]);

var pageContainer = null;


Ext.onReady(function () {

	pageContainer = Ext.create('Ext.container.Container', {
		id: 'htContainer',
		width: 1000,
		height: 300,
		renderTo: 'grid'
	});


	Ext.Ajax.request({
		url: 'item/item',
		method: 'GET',
		success: function(response){
			var text = response.responseText;
			
			var obj = eval('(' + text + ')');
			
			var data = obj.data;

			var itemInfo = [];
			for (var i = 0; i < data.length; i++) {
				var obj = {};

				obj['name'] = data[i]['name'];
				// 列头前加一个字母，转换成字符串
				// obj['item_id'] = 'c'+data[i]['item_id'];
				obj['item_id'] = ''+data[i]['item_id'];

				obj['type'] = data[i]['type'];

				itemInfo.push(obj);
			}

			pageContainer.add({
				xtype: 'dmRecordView',
				itemInfo: itemInfo,
				width: 1000,
				height: 500
			});
		}
	});
});
	
	
</script>
	
	
  </head>
  
  <body>


	<br>
    <a href="ItemManager.jsp"> 字段管理 </a>
	<br>
	<br>


    <div id="grid"></div>


  </body>
</html>
