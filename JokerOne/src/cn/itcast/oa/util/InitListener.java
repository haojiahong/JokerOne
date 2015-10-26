package cn.itcast.oa.util;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.itcast.oa.domain.Privilege;
import cn.itcast.oa.service.PrivilegeService;

import com.google.gson.Gson;

public class InitListener implements ServletContextListener {
	/**
	 * 因为这个类是要放到web.xml中的 不能采用Spring注入的形式生成实例，即使交给spring管理，在tomcat中也用不到
	 * 看web.xml的注释
	 * 现在这个类没什么用了，原先有个menuBean的bean类让我给删了。
	 */
	// @Resource PrivilegeService privilegeService;

	public void contextDestroyed(ServletContextEvent arg0) {

	}

	public void contextInitialized(ServletContextEvent sce) {
		// 获取已经创建了的容器
		ApplicationContext ac = WebApplicationContextUtils
				.getWebApplicationContext(sce.getServletContext());
		PrivilegeService privilegeService = (PrivilegeService) ac
				.getBean("privilegeService");
		// 准备数据topPrivilegeList
		List<Privilege> topPrivilegeList = privilegeService.findTopList();
//		List<MenuBean> menuBeanLs = new ArrayList<MenuBean>();
		for(Privilege p : topPrivilegeList){
//			MenuBean m = new MenuBean();
//			m.setPrivilegeName(p.getPrivilegeName());
//			m.setPrivilegeUrl(p.getUrl());
//			menuBeanLs.add(m);
		}
		Gson gson = new Gson();
//		String menuListJson = gson.toJson(menuBeanLs,
//				new TypeToken<List<MenuBean>>() {
//				}.getType());
//		System.out.println(menuListJson);
		sce.getServletContext().setAttribute("topPrivilegeList",
				topPrivilegeList);
//		sce.getServletContext().setAttribute("menuListJson",
//				menuListJson);
		System.out.println("===============已准备好顶级权限列表数据==============");
		// 准备数据 数据库中存放的权限 allPrivilegeUrls
		List<String> allPrivilegeUrls = privilegeService.getAllPrivilegeUrls();
		sce.getServletContext().setAttribute("allPrivilegeUrls",
				allPrivilegeUrls);
		System.out
				.println("===============已准备好allPrivilegeUrls数据==============");
	}

}
