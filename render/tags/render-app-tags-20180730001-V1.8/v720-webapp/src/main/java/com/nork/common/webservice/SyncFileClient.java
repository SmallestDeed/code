package com.nork.common.webservice;


import java.io.File;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

//import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings("unused")
public class SyncFileClient {
	
	public final static String url = "http://localhost:8080/trunk/webservice/syncFile?wsdl";
	private static ApplicationContext applicationContext;
	/**
	 * use original method to test upload file.
	 * @param the real file path.
	 */
	private static void invokingUploadFile(String filePath,String svrRootPath,String svrDBPath){
		SyncFileEntity fileEntity = constructFileEntity(filePath);
		
		//obtain web service
		//JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		//factory.setAddress(url);
		//factory.setServiceClass(SyncFileService.class);
		
		//upload file
		//SyncFileService uploadFileService = (SyncFileService)factory.create();
		//uploadFileService.uploadFile(fileEntity,svrRootPath,svrDBPath);
		
		//////System.out.println("invokingUploadFile Upload file succeed!");
	}
	
	/**
	 * use the spring application context to test upload file.
	 * @param the real file path.
	 */
	private static void invokingUploadFileBySpring(String filePath,String svrRootPath,String svrDBPath){
		SyncFileEntity fileEntity = constructFileEntity(filePath);
		
		applicationContext = new ClassPathXmlApplicationContext(new String[] {"spring/spring.xml"});
		//UploadFileService uploadFileService = applicationContext.getBean("uploadFileService", UploadFileService.class);
		SyncFileService uploadFileClient = (SyncFileService) applicationContext.getBean("syncFileClient");
		try {
			uploadFileClient.uploadFile(fileEntity,svrRootPath,svrDBPath);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		//////System.out.println("invokingUploadFileBySpring Upload file succeed!");
	}
	
	/**
	 * Construct FileEntity.
	 * @param the real file path.
	 * @return FileEntity.
	 */
	private static SyncFileEntity constructFileEntity(String filePath) {
		// construct FileEntity
		SyncFileEntity fileEntity = new SyncFileEntity();
		File file = new File(filePath);
		fileEntity.setFileName(file.getName().substring(0,(file.getName().lastIndexOf("."))));
		fileEntity.setFileType(filePath.substring(filePath.lastIndexOf(".")+1));
		DataSource source = new FileDataSource(file);
		DataHandler handler = new DataHandler(source);
		fileEntity.setFile(handler);
		return fileEntity;
	}
	
	public static void main(String[] args) {
		//String filePath = "c:/111/CentOS-6.7-x86_64-netinstall.iso";
		String  filePath = "c:/111/YNote.exe";
		
		String svrRootPath="D:\\websercie";
		String svrDBPath ="/server/".replace("/", "\\\\");
		//invokingUploadFile(filePath,svrRootPath,svrDBPath);
		invokingUploadFileBySpring(filePath,svrRootPath,svrDBPath);
	}
}
