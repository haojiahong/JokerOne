<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="cn.itcast.oa.domain.User"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

/*进首页之后重新定向到我们自己定义的走action进首页  */
/*重定向和转发的区别  这里是重定向 转发是一个功能的多个页面之间转发  重定向是不同功能  */
	response.sendRedirect(request.getContextPath()+"/oa/loginAction.do");
%>
<%--	User user = (User) session.getAttribute("user");--%>
<%--	if (user != null) {--%>
<%--		request.getRequestDispatcher("/oa/loginsuccess.jsp").forward(request, response);--%>
<%--	} else {--%>
<%--		request.getRequestDispatcher("/oa/login.jsp").forward(request, response);--%>
<%--	}--%>

