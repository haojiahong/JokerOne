package cn.itcast.oa.util;

import org.springframework.context.ApplicationContext;

public class ApplicationUtil {
private static ApplicationContext applicationContext;
	
	public static Object getBean(String beanName) {
		return getCtx().getBean(beanName);
	}

	public static AppConfig getAppConfig()
    {
        return (AppConfig)getCtx().getBean("appConfig");
    }
	
	public static void setApplicationContext(ApplicationContext ctx) {
		applicationContext = ctx;
	}
	
	private static ApplicationContext getCtx() {
		return applicationContext;
	}
}
