//package com.nork.common.listener;
//
//
////import java.util.List;
////
////import javax.servlet.ServletContext;
//import javax.servlet.ServletContext;
//import javax.servlet.http.HttpSession;
//import javax.servlet.http.HttpSessionEvent;
//import javax.servlet.http.HttpSessionListener;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.web.context.support.WebApplicationContextUtils;
//
//import com.nork.common.filter.context.MySessionContext;
//import com.nork.system.service.SysDictionaryService;
//import com.nork.system.service.SysUserService;
//
//public class MySessionListener implements HttpSessionListener {
//	private static Logger logger = Logger.getLogger(MySessionListener.class);
//	private MySessionContext myc = MySessionContext.getInstance();
//	@Autowired
//	private SysUserService sysUserService;
//	@Autowired
//	private SysDictionaryService sysDictionaryService;
//	
//	@Override
//	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
//		//logger.info("sessionCreated...");
//		//HttpSession session = httpSessionEvent.getSession();
//
//
//		//sysUserService = (SysUserService)this.getObjectFromApplication(httpSessionEvent.getSession().getServletContext(),"sysUserService");
//		//sysDictionaryService= (SysDictionaryService)this.getObjectFromApplication(httpSessionEvent.getSession().getServletContext(),"sysDictionaryService");
//		//session.setAttribute("sysUserList", sysUserService.getList(null));
//		//session.setAttribute("sysDictionaryList", sysDictionaryService.getList(null));
//		
//		//myc.AddSession(session);
//	}
//
//	@Override
//	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
//		//logger.info("sessionDestroyed...");
//		//HttpSession session = httpSessionEvent.getSession();
//		//myc.DelSession(session);
//	}
//	
//	/**
//	 * 通过WebApplicationContextUtils 得到Spring容器的实例。根据bean的名称返回bean的实例。
//	 * 
//	 * @param servletContext
//	 *            ：ServletContext上下文。
//	 * @param beanName
//	 *            :要取得的Spring容器中Bean的名称。
//	 * @return 返回Bean的实例。
//	 */
//	private Object getObjectFromApplication(ServletContext servletContext,
//			String beanName) {
//		// 通过WebApplicationContextUtils 得到Spring容器的实例。
//		ApplicationContext application = WebApplicationContextUtils
//				.getWebApplicationContext(servletContext);
//		// 返回Bean的实例。
//		return application.getBean(beanName);
//	}
//}
