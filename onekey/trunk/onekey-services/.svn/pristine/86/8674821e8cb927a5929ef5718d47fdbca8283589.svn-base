import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

public class Provider {

	private static final ReentrantLock LOCK = new ReentrantLock();
	private static final Condition STOP = LOCK.newCondition();

	public static void main(String[] args) throws Exception {

		System.out.println("############### 开始启动 ###############");
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "classpath:spring/spring.xml" });
		Log4jConfigurer.initLogging("classpath:config/log4j.properties", 6000);
		context.start();
		// System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟
		System.out.println("############### 启动完成 ###############");

		try {
			LOCK.lock();
			STOP.await();
		} catch (InterruptedException e) {
//			logger.warn("Dubbo service server stopped, interrupted by other thread!", e);
		} finally {
			LOCK.unlock();
		}
	}
}
