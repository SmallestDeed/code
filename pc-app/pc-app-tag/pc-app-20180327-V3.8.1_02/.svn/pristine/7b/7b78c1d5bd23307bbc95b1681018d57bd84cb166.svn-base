package app.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.nork.common.util.Utils;
import com.nork.demo.model.Ftp;

public class FtpUpload {
	/**
	* Description: 向FTP服务器上传文件
	* @Version1.0 Jul 27, 2008 4:31:09 PM by 崔红保（cuihongbao@d-heaven.com）创建
	* @param url FTP服务器hostname
	* @param port FTP服务器端口
	* @param username FTP登录账号
	* @param password FTP登录密码
	* @param path FTP服务器保存目录
	* @param filename 上传到FTP服务器上的文件名
	* @param input 输入流
	* @return 成功返回true，否则返回false
	*/ 
	public static boolean uploadFile(Ftp ftp, String filename, InputStream input) { 
	    boolean success = false; 
	    FTPClient ftpc = new FTPClient(); 
	    try { 
	        int reply; 
	        ftpc.connect(ftp.getFtpHost());//连接FTP服务器 
//	        ftp.connect(url, port);//连接FTP服务器 
	        //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器 
	        ftpc.login(ftp.getFtpUser(), ftp.getFtpPassword());//登录 
	        reply = ftpc.getReplyCode(); 
	        if (!FTPReply.isPositiveCompletion(reply)) { 
	        	ftpc.disconnect(); 
	            return success; 
	        } 
	        ftpc.changeWorkingDirectory(ftp.getFtpPath()); 
	        ftpc.setBufferSize(1024);  //设置1M缓冲
	        ftpc.setControlEncoding("GBK"); //设置编码为GBK
	        ftpc.setFileType(FTPClient.BINARY_FILE_TYPE); //文件类型为二进制文件
	        ftpc.storeFile(filename, input);          
	         
	        input.close(); 
	        ftpc.logout(); 
	        success = true; 
	    } catch (IOException e) { 
	        e.printStackTrace(); 
	    } finally { 
	        if (ftpc.isConnected()) { 
	            try { 
	            	ftpc.disconnect(); 
	            } catch (IOException ioe) { 
	            } 
	        } 
	    } 
	    return success; 
	}
	
	
	public static void testUpLoadFromDisk(){
		try {
			String filePath = "F:/test1.txt";
			FileInputStream in=new FileInputStream(new File(filePath));
			Ftp ftp = new Ftp();
			ftp.setFtpHost("192.168.1.6");
			ftp.setFtpUser("admin");
			ftp.setFtpPassword("123456");
			ftp.setFtpPath("/test/");
			ftp.setFtpPort(21);
			String fileName = Utils.getCurrentDateTime(Utils.DATETIMESSS)+ "_"+ Utils.generateRandomDigitString(6);
			String suffix = filePath.substring(filePath.lastIndexOf("."));
			boolean flag = uploadFile(ftp, fileName+suffix, in);
			//////System.out.println(flag);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		testUpLoadFromDisk();
	}

}
