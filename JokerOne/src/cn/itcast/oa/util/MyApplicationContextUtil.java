package cn.itcast.oa.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class MyApplicationContextUtil implements ApplicationContextAware{

	private  ApplicationContext context;
	private Log log = LogFactory.getLog(this.getClass());
	
	@Override
	public void setApplicationContext(ApplicationContext contex)
			throws BeansException {
		this.context = contex;
		
	}
	
	public void handle() {
//		JPAUtil.setApplicationContext(context);
		ApplicationUtil.setApplicationContext(context); 
		log.debug("applicationContext进行设置完成。。。");
		
	}

	
}