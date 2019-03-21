package app.test.dubbo;



import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import app.test.dubbo.model.User;
import app.test.dubbo.service.DemoService;


public class Consumer {

	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] {  "classpath:spring/spring-dubbo-common.xml"
						       ,"classpath:spring/spring-dubbo-consumer.xml" });
		context.start();
		
		DemoService demoService = (DemoService) context.getBean("demoService2");
		String hello = demoService.sayHello("tom");
		//System.out.println(hello);

		List<User> list = demoService.getUsers();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				//System.out.println(list.get(i));
			}
		}
		
		System.in.read();
	}

}