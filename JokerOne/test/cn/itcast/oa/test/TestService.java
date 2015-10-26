package cn.itcast.oa.test;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.domain.User;

@Service("testService")
public class TestService {
	
	@Resource//注入bean的资源，按名称，再按类型
	private SessionFactory sessionFactory;
	
	public void saveTwouser() {
		User user = new User();
		user.setUserDescription("123");
		user.setUserName("haojiahong");
		System.out.println("sessionfactoty gogogogogog===============================");
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
		System.out.println("user saved=====================");
	}
}
