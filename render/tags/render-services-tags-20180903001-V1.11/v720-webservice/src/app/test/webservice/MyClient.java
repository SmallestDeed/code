package app.test.webservice;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
//import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Async;

public class MyClient {
	public final static String url = "http://localhost:8080/onlineDecorate/webservice/demo?wsdl";

	@Async
	public static void test1() {
		// 直接构造法
		//JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
		//factoryBean.getInInterceptors().add(new LoggingInInterceptor());
		//factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
		//factoryBean.setServiceClass(CXFDemo.class);
		//factoryBean.setAddress(url);

		//CXFDemo demo = (CXFDemo) factoryBean.create();
		//String str = demo.sayHi("panda1");//
		//////System.out.println(str);
	}

	@Async
	public static void test2() {
		// 配置读取法
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"spring/spring-cxf-client.xml");
		// get到接口类型并调用
		CXFDemo demo = (CXFDemo) ctx.getBean("demoClient");
		String str = demo.sayHi("panda2");
		//////System.out.println(str);
	}

	// 两种调用方法
	public static void main(String[] args) {
//		while (true) {
//			//////System.out.println("1 start");
//			test1();
//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			//////System.out.println("2 start");
//			test2();
//		}
		test1();
		test2();
	}
}
