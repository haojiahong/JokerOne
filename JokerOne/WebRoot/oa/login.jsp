<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<html>
<head>
	<title>登陆页面</title>
    <%@ include file="/jsp/public/commons.jspf" %>
<style type="text/css">
*{ padding:0; margin:0}
html,body,form{ font-family:"微软雅黑"; font-size:12px;height:100%;overflow:hidden}
a img{ border:none}
a{ text-decoration:none}

/*整个容器桌面  */
.content{
	position:absolute;
	top:0px;left:0px;
	height:100%;
	width:100%;
	background:url(${pageContext.request.contextPath}/script/images/login/fullbg.jpg) no-repeat 0px -10px;
/* 	background-color:#eee; */
	background-size: cover;
}

/*最上边的一点灰色区域  */
.content_box{
	height: 6px;
	position: absolute;
	width: 100%;
	background-color: gray;
}

/*文字  */
.text{
	width: 41%;
	position: absolute;
	top: 35%;
	left: 30%;
	height: 35%;
/* 	background: url(images/login/text.png) no-repeat; */
	background-size: contain;
	display:none;
}

/*左上角商标  */
.logo{
	width: 100%;
	top: 10px;
/* 	left: 50px; */
	height: 10%;	
	position: absolute;
	z-index: 0;
/* 	background: url(../script/images/login/logo.jpg); */
	background-size: contain;
}

.dl{
 	background:url(${pageContext.request.contextPath}/script/images/login/dlbg.png) repeat; 
	width:335px;
	height:320px;
	position: absolute;
	top: 28%;
	right: 40%;
}

.dl .dl_box{
	margin:10px;
	height:300px;
	background-color:white;
}
.dl .dl_box .top{
	position: relative;
	height: 40px;
	background-color: coral;
	font-size: 20px;
	color: white;
}

.dl .dl_box .top span{
	display: block;
	height: 28px;
	position: absolute;
	top: 6px;
	left: 10px;
}

.dl .dl_box .center{
	padding:29px;
}
.s2errormessage{
	background-color:red;
	border-radius: 3px 3px; 
	position:absolute;
	top:0px;
	width:100%;
}

.errorMessage {
	padding:0px;
	text-align:center;
	font-family:'宋体';
	background-color: #999;
	align: center;
 	border-radius: 3px 3px; 
	color: white;
	font-size: 16px;
	height: 25px;
}

.errorMessage li {
	list-style-type: none;
}

.errorMessage li span {
	position: relative;
	top: 5px;
}
.userinput:focus { 
 border: 0; 
}
.footer{
	position: absolute;
	bottom: 0px;
	background-color: white;
	width: 100%;
	text-align:center;
}
</style>
</head>

<body  >

<s:form action="loginAction!login.do" name="loginForm">
<div class="content">
		<div class="content_box">
		</div>
		<div class="text"></div>
		<div class="s2errormessage">
				<s:actionerror />
				<s:fielderror />
		</div>
		<div class="dl">
			<div class="dl_box">
				<div class="top">
					<span>用户登陆</span>
				</div>
				<div class="center">
					<input id="username" name="loginName"  style="width:220px; height:35px; padding:0px 5px 0 32px; background:url(${pageContext.request.contextPath}/script/images/login/personname.png) no-repeat; font-size:17px; border:none; margin-bottom:10px"></input>
					<input id="password" name="password"  type="password" style="width:220px; height:35px; padding:0px 5px 0 32px; background:url(${pageContext.request.contextPath}/script/images/login/personpass.png) no-repeat; font-size:17px; border:none; margin:12px 0;"></input>
					
					<a href="javascript:void(0);" class="tj" style="background:url(${pageContext.request.contextPath}/script/images/login/loginbtn.png) no-repeat;display:block;width:88px;height:38px;font-size: 18px;color: white;line-height: 37px;text-align: center;margin-left: 82px;margin-top: 15px;" onclick="loginIn()">登陆</a>
				</div>
			</div>
		</div>
		
	</div>
	<div class="footer">
			<p>haojiahong</p>
	</div>

</s:form>

<script type="text/javascript">

function loginIn(){
	$("form[name='loginForm']").submit();
}
	
</script>

</body>

</html>

