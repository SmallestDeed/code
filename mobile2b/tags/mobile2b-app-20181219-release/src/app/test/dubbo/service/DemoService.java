package app.test.dubbo.service;

import java.util.List;
import app.test.dubbo.model.User;

public interface DemoService {

	String sayHello(String name);

	public List<User> getUsers();
	
}