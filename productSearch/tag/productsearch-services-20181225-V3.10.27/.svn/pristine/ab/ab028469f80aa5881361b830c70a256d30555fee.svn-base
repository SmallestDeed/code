package app.test.dubbo.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import app.test.dubbo.model.User;
import app.test.dubbo.service.DemoRestService;

@Component("demoRestService")
public class DemoRestServiceImpl implements DemoRestService {

	public String sayHello(String name) {
		return "Hello " + name;
	}

	public List<User> getUsers() {
		List<User> list = new ArrayList<User>();
		User u1 = new User();
		u1.setName("jack");
		u1.setAge(20);
		u1.setSex("男");

		User u2 = new User();
		u2.setName("tom");
		u2.setAge(21);
		u2.setSex("女");

		User u3 = new User();
		u3.setName("rose");
		u3.setAge(19);
		u3.setSex("女");

		list.add(u1);
		list.add(u2);
		list.add(u3);
		return list;
	}
	
	public User getUser(User user) {
		return user;
	}
	
}
