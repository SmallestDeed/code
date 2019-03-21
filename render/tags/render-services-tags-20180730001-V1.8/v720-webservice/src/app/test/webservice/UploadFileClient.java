package app.test.webservice;
import java.io.File;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

//import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings("unused")
public class UploadFileClient {
	
	public final static String url = "http://localhost:8080/trunk/webservice/uploadFile?wsdl";
	private static ApplicationContext applicationContext;
	/**
	 * use original method to test upload file.
	 * @param the real file path.
	 */
	private static void invokingUploadFile(String filePath){
		FileEntity fileEntity = constructFileEntity(filePath);
		
		//obtain web service
		//JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		//factory.setAddress(url);
		//factory.setServiceClass(UploadFileService.class);
		
		//upload file
		//UploadFileService uploadFileService = (UploadFileService)factory.create();
		//uploadFileService.uploadFile(fileEntity);
	}
	
	/**
	 * use the spring application context to test upload file.
	 * @param the real file path.
	 */
	private static void invokingUploadFileBySpring(String filePath){
		FileEntity fileEntity = constructFileEntity(filePath);
		
		applicationContext = new ClassPathXmlApplicationContext("spring/spring-cxf-client.xml");
		//UploadFileService uploadFileService = applicationContext.getBean("uploadFileService", UploadFileService.class);
		UploadFileService uploadFileClient = (UploadFileService) applicationContext.getBean("uploadFileClient");
		try {
			uploadFileClient.uploadFile(fileEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		//////System.out.println("Upload file succeed!");
	}
	
	/**
	 * Construct FileEntity.
	 * @param the real file path.
	 * @return FileEntity.
	 */
	private static FileEntity constructFileEntity(String filePath) {
		// construct FileEntity
		FileEntity fileEntity = new FileEntity();
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
		//invokingUploadFile(filePath);
		invokingUploadFileBySpring(filePath);
	}
}
