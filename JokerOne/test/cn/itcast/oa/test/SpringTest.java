package cn.itcast.oa.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.*;

public class SpringTest {
	private ApplicationContext ac = new ClassPathXmlApplicationContext(
			"applicationContext.xml");

	// 测试事务
	@Test
	public void testTransaction() throws Exception {
		TestService testService = (TestService) ac.getBean("testService");
		testService.saveTwouser();
		System.out.println(testService);
	}
}
