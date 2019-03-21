package com.nork.common.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.nork.common.async.TaskExecutor;
public class ApplicationListenner implements ServletContextListener {

	public Logger logger = Logger.getLogger(ApplicationListenner.class);
	private ServletContext context = null; 

	//@Autowired
	//private ProCategoryService proCategoryService;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		//获取url
		context = event.getServletContext();
		context.setAttribute("is_allow", "true");
		context = event.getServletContext();  
		
		try{
		//WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(context);
		//SysDictionaryService sysDictionaryService = (SysDictionaryService)wac.getBean("sysDictionaryService");
		//SysUserService sysUserService = wac.getBean(SysUserService.class);
		//ProCategoryService proCategoryService = (ProCategoryService)wac.getBean("proCategoryService");
		//ProCategory proCategory  = proCategoryService.getAllCategory();
		//context.setAttribute("_allCategory", proCategory);
		//CacheManager.getInstance().getCacher().setObject("_allCategory", proCategory, -1);
		//JedisUtils.setObject("_allCategory", proCategory, -1);
	//sysDictionaryService = (SysDictionaryService)wac.getBean("sysDictionaryService");
	//	sysUserService = wac.getBean(SysUserService.class);
	/*	String[] location = {"classpath:spring/spring.xml","classpath:spring/spring-mybatis.xml"};
		ApplicationContext ct = new ClassPathXmlApplicationContext(location,true); 
		sysUserService = ct.getBean(SysUserService.class);
		
		
 		sysUserService =wac.getBean(SysUserService.class);
 		sysDictionaryService = wac.getBean(SysDictionaryService.class);*/
 		
 		//SysUserService sysUserService =(SysUserService)this.getObjectFromApplication(event.getServletContext(),"sysUserService");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		context =null;
		TaskExecutor.getInstance().release();
	}
	
}
