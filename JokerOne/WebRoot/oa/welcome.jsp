<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<title>欢迎页面</title>
</head>
<body>
	<h1>项目介绍：</h1>
	<h4>项目模仿简单的OA管理系统进行构建，能够满足基本的管理需求。也可以在此基础上进行多方面的扩展，以实现业务逻辑更加丰富的系统。
		<br />
	</h4>
	<h2>项目框架与环境：</h2>
	项目由MyEclipse创建，Struts2.3.x+Spring3.2.x+Hibernate3.x+EasyUI1.4.1架构的示例程序；
	<br />框架所需环境：JAVA环境：JDK6+；数据库环境：oracle10g+/mysql5+；WEB容器环境：tomcat6+；
	
	<h2>项目实现的功能：</h2>
	<h3>登陆登出功能。</h3>
	<hr />
	
	<h3>用户、角色、菜单的增删改查与分页排序功能。</h3>
	<p>系统，前端使用EasyUI框架构建的表格，后台使用SSH的MVC思想，构建出了通用的增删改查模块。以及分页和排序的功能。</p>
	<hr />
	
	<h3>EasyUI框架中重要插件的使用。</h3>
	<ul>
		<li>Datagrid数据网格，Treegrid树形网格对数据进行展示。</li>
		<li>Tree树形成的异步加载菜单树。</li>
		<li>Dialog与iframe构建的弹窗，以及Messager构建的消息框。</li>
		<li>form提交表单，以及其中使用的各种组合框</li>
		<li>更换皮肤，及保存cookie</li>
	</ul>
	<hr />
	
	<h3>Excel的导入导出。</h3>
	<p>通过使用Apache POI构建通用的Excel导入导出工具类，实现了通过excel来批量添加用户的功能，和通过excel导出用户数据的功能。
		其中，上传Excel使用的是uploadify插件。</p>
	<hr />
	
	<h3>制作报表并实现打印的功能</h3>
	<p>通过使用jasperReport和ireport实现报表的制作和打印功能。</p>
	<hr />
	
	<h3>使用echarts实现图表的制作功能</h3>
	<p>通过使用echarts这款前台框架可以实现图表显示处理数据。主要是后台数据通过JSON将数据传递给echarts，echarts解析数据并制作相关图表。</p>
	<hr />
	
	<h3>权限控制管理</h3>
	<p>系统实现了不同登陆用户拥有不同的菜单权限功能。用户分配不同的角色，角色拥有不同的菜单权限，从而实现了不同登陆用户拥有不同的菜单权限功能。</p>
	<hr />
	
	<h3>附件管理功能（FTP服务器）</h3>
	<p>通过使用SWFUpload实现了文件、图片等的上传FTP功能，并通过业务表记录上传记录，方便用户进行附件的管理。</p>
	<hr />
	
	<h3>Quartz定时任务的实现</h3>
	<p>通过使用quartz与spring的结合，实现了定时任务功能，定时清空项目临时缓存文件。</p>
	<hr />
	
	<h3>用户密码md5加密</h3>
	<p>对用户设置的密码进行了md5加密实现。</p>
	<hr />
	<h2>项目借鉴：</h2>
	<p>项目完全有自己搭建与编写。通过借鉴其他人的项目是项目更加完善。其中有：传智播客的人事管理系统项目&easyUI论坛上的sshe项目</p>
	<p>也感谢这两位老师辛苦录制的视频教程。</p>
	<h2>说明：</h2>
	<p>由于项目还没有完善，所以请使用Google浏览器进行访问。开发的时候也是在Google上进行的。所以存在一定的浏览器兼容问题。正在完善。。。</p>
<body>