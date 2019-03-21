package com.nork.system.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.client.model.UploadFileResponse;
import com.nork.common.model.FileModel;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.pay.alipay.util.UtilDate;
import com.nork.product.model.BaseProduct;
import com.nork.system.dao.SysUserSystemOperationLogMapper;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResTexture;
import com.nork.system.model.SysUserSystemOperationLog;
import com.nork.system.model.search.SysUserSystemOperationLogSearch;
import com.nork.system.service.SysUserSystemOperationLogService;

/**   
 * @Title: SysUserSystemOperationLogServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统模块-用户系统操作记录ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2017-07-05 19:43:31
 * @version V1.0   
 */
@Service("sysUserSystemOperationLogService")
public class SysUserSystemOperationLogServiceImpl implements SysUserSystemOperationLogService {
	private static Logger logger = Logger.getLogger(SysUserSystemOperationLogServiceImpl.class);
	private SysUserSystemOperationLogMapper sysUserSystemOperationLogMapper;

	@Autowired
	public void setSysUserSystemOperationLogMapper(
			SysUserSystemOperationLogMapper sysUserSystemOperationLogMapper) {
		this.sysUserSystemOperationLogMapper = sysUserSystemOperationLogMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param sysUserSystemOperationLog
	 * @return  int 
	 */
	@Override
	public int add(SysUserSystemOperationLog sysUserSystemOperationLog) {
		sysUserSystemOperationLogMapper.insertSelective(sysUserSystemOperationLog);
		return sysUserSystemOperationLog.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param sysUserSystemOperationLog
	 * @return  int 
	 */
	@Override
	public int update(SysUserSystemOperationLog sysUserSystemOperationLog) {
		return sysUserSystemOperationLogMapper
				.updateByPrimaryKeySelective(sysUserSystemOperationLog);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return sysUserSystemOperationLogMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  SysUserSystemOperationLog 
	 */
	@Override
	public SysUserSystemOperationLog get(Integer id) {
		return sysUserSystemOperationLogMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  sysUserSystemOperationLog
	 * @return   List<SysUserSystemOperationLog>
	 */
	@Override
	public List<SysUserSystemOperationLog> getList(SysUserSystemOperationLog sysUserSystemOperationLog) {
	    return sysUserSystemOperationLogMapper.selectList(sysUserSystemOperationLog);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  sysUserSystemOperationLog
	 * @return   int
	 */
	@Override
	public int getCount(SysUserSystemOperationLogSearch sysUserSystemOperationLogSearch){
		return  sysUserSystemOperationLogMapper.selectCount(sysUserSystemOperationLogSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  sysUserSystemOperationLog
	 * @return   List<SysUserSystemOperationLog>
	 */
	@Override
	public List<SysUserSystemOperationLog> getPaginatedList(
			SysUserSystemOperationLogSearch sysUserSystemOperationLogSearch) {
		return sysUserSystemOperationLogMapper.selectPaginatedList(sysUserSystemOperationLogSearch);
	}


	/**
	 * 其他
	 * 
	 */
	
	
	/**
	 *   txt存数据
	 * @param  sysUserLoginLog
	 * @return void
	 */
	@Override
	public void txtSaveOperationLog(SysUserSystemOperationLog sysUserSystemOperationLog) {
		try {
			if(sysUserSystemOperationLog.getUserMobile() != null){
				String fileName = sysUserSystemOperationLog.getUserMobile()+"_"+UtilDate.getDate();
				String path = Utils.getAbsolutePath(Utils.getPath2("system.sysUser.systemOperationLog.upload.path",""),null);
				path = Utils.replaceDate(path);
				File file = new File(path);
				if(!file.exists()){											  	//如果该目录不存在!则创建
					file.mkdirs();
				}
				
				RandomAccessFile rout = new RandomAccessFile(path+fileName+".txt","rw"); //"rw"表示此文件可读可写
				File fileExist = new File(path+fileName+".txt");
				if(fileExist.exists()){									 		//判断该用户今天的txt是否存在!不在则创建
					rout.seek(rout.length());  									//将记录指针移动到文件最后  
				}
				StringBuilder sb=new StringBuilder("");
				sb.append(sysUserSystemOperationLog.getOperationUserId()+"#");        			  //用户ID
				sb.append(sysUserSystemOperationLog.getOperationUrlAddress()+"#"); 				  //接口地址
				sb.append(sysUserSystemOperationLog.getOperationClientIp()+"#");				  //客户端IP
				sb.append(Utils.getLinuxLocalIp()+"/"+Utils.getLocalIP()+"#");				      //服务器IP
				sb.append(sysUserSystemOperationLog.getOperationLoginDevice()+"#");				  //登录设备
				sb.append(sysUserSystemOperationLog.getOperationModuleName()+"#");                //模块名称
				sb.append(sysUserSystemOperationLog.getOperationPortTime()+"#");				  //接口耗时
				sb.append(UtilDate.ConverToString(sysUserSystemOperationLog.getOperationLoginTime())+"#");				  //登录时间
				sb.append(sysUserSystemOperationLog.getOperationBusiness()+"#");				  //操作业务
				sb.append(sysUserSystemOperationLog.getOperationSystemModel()+"#");				  //操作系统型号
				sb.append(sysUserSystemOperationLog.getOperationParameter()+"#"); 			  	  //接口参数
				sb.append(sysUserSystemOperationLog.getMsgId()+"#");						  	  //MSGID
				sb.append(sysUserSystemOperationLog.getUuId()+"#"+"\r\n"); 			  			  //UUID
				String transForm=sb.toString();
				byte[] entering=(transForm).getBytes();
				//写入文件，还可以用其他方法如：write(String str)
				rout.write(entering, 0, entering.length);
				//关闭流
				rout.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 *   txt数据录入到数据库表
	 * @param  sysUserLoginLog
	 * @return void
	 */
	@Override
	public void userOperationLog() {
		String copyPath = Utils.getAbsolutePath(Utils.getPath2("system.sysUser.systemOperationLog.upload.path",""),null);  //从该目录复制txt文件
		logger.error("=========================从该目录复制txt文件======================="+copyPath); 
		String path = Utils.getAbsolutePath(Utils.getPath2("system.sysUser.copySystemOperationLog.upload.path",""),null);	//复制到该目录下
		path = Utils.replaceDate(path);
		logger.error("=========================复制到该目录下======================"+path);
		File file = new File(path);
		if(!file.exists()){											  	//如果该目录不存在!则创建
			file.mkdirs();
		}
		List<String> list = Utils.walk2(Paths.get(copyPath),-1);			//获得要复制的txt文件
		String txtName = "";
		for (String copyList : list) {
			if (Utils.isWindowsOS()) {  
				 txtName = copyList.substring(copyList.lastIndexOf("\\")+1);
			}else{
				 txtName = copyList.substring(copyList.lastIndexOf("/")+1);
			}
			FileUploadUtils.fileCopy(copyList,path+txtName);                  //开始复制
		}
		
		//第二步:解析txt数据到数据库
		logger.info("==========================开始解析txt========================");
		int totaOne = 0;
		int totaTwo = 0;
		SysUserSystemOperationLog sysUserSystemOperationLog = null;
		try(
		    DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(copyPath), "*.txt")){
		    for(Path entry : stream){														//文件夹下的txt
		    	String convert = entry.toString();
            	String last = convert.substring(convert.lastIndexOf("_")+1);
            	String lastBack = last.substring(0, 8);
            	if(Integer.parseInt(lastBack) <= Integer.parseInt(Utils.dayDate(-1))){			//筛选出日期为今天以后的
            		List<SysUserSystemOperationLog> userList = new ArrayList<SysUserSystemOperationLog>();
            		
			       for(String line : Files.readAllLines(entry, Charset.forName("UTF-8"))){		//txt中的数据条数
			          String[] contentStr = line.split("#");		         
			          if(!"null".equals(contentStr[0]) && !"null".equals(contentStr[1]) && !"null".equals(contentStr[2]) && !"null".equals(contentStr[3]) 
			        		 && !"null".equals(contentStr[4]) && !"null".equals(contentStr[5]) && !"null".equals(contentStr[6])) {
			        	  	 sysUserSystemOperationLog = new SysUserSystemOperationLog();
					         sysUserSystemOperationLog.setOperationUserId(Integer.parseInt(contentStr[0]));        			  			   //用户ID
							 sysUserSystemOperationLog.setOperationUrlAddress(contentStr[1]); 								  			  //接口地址
							 sysUserSystemOperationLog.setOperationClientIp(contentStr[2]);									  			 //客户端IP
							 sysUserSystemOperationLog.setOperationServerIp(contentStr[3]);									  			//服务器IP
							 sysUserSystemOperationLog.setOperationLoginDevice(contentStr[4]);								  		   //登录设备
							 sysUserSystemOperationLog.setOperationModuleName(contentStr[5]);           				      		  //模块名称
							 sysUserSystemOperationLog.setOperationPortTime(contentStr[6]);									         //接口耗时
							 sysUserSystemOperationLog.setOperationLoginTime(UtilDate.ConverToDate(contentStr[7]));			        //登录时间
							 sysUserSystemOperationLog.setOperationBusiness(contentStr[8]);				 					       //操作业务
							 sysUserSystemOperationLog.setOperationSystemModel(contentStr[9]);								      //操作系统型号
							 if(contentStr[10].length() < 1800) {
								 sysUserSystemOperationLog.setOperationParameter(contentStr[10]); 		  						     //接口参数
							 }else {
								 sysUserSystemOperationLog.setOperationParameter("接口参数字符串长度为:"+contentStr[10].length()+",不存储"); 
							 }
							 sysUserSystemOperationLog.setGmtModified(new Date());											    //创建时间
							 sysUserSystemOperationLog.setIsDeleted(0);														   //是否删除
							 sysUserSystemOperationLog.setMsgId(contentStr[11]);								          	  //msgid 
							 if(contentStr.length>12){
								 sysUserSystemOperationLog.setUuId(contentStr[12]);											 //uuid 
							 }
							 userList.add(sysUserSystemOperationLog);
				          totaOne++;
			          }
			       }
			       if(userList.size()>0){
				    	sysUserSystemOperationLogMapper.insertLogBatch(userList);	    //批量插入
				    }
			       logger.info("==========================批量录入数据:"+userList.size()+"条==============================");
			       //第三步:执行完一个txt中的数据就将该txt删除
			       File deleteFile = new File(entry.toString());	
			       deleteFile.delete();
			       totaTwo++;
            	}
		    }
		    logger.info("==========================解析txt完成,共解析"+totaOne+"条数据==============================");
		    logger.info("==========================共解析"+totaTwo+"个txt完成,共删除"+totaTwo+"个解析完成的txt文件==============================");
		    //logger.info("==========================批量录入数据:"+userList.size()+"条==============================");
		   analysisAppTextFile();		//解析APP端发过来的文件
		  }catch(Exception e){
		      e.printStackTrace();
		  }
	}
	
	/**
	 *   txt文件删除
	 * @return void
	 */
	@Override
	public void deleteTextFile() {
		/*String path = Utils.getPath("app.upload.root","") + Utils.getPath2("system.sysUser.copySystemOperationLog.upload.path","");	//删除该目录下的txt文件
		List<String> list = Utils.walk2(Paths.get(path),-7);					   //获得要删除的txt文件
		for (String copyList : list) {
			   File deleteFile = new File(copyList);	
		       deleteFile.delete();							     //开始删除
		}
		logger.info("==========================删除服务端txt文件:"+list.size()+"个==============================");
		txtDelete();		//删除APP端
*/		
	}

	
	/**
	 *   解析APP传过来的文件
	 * @return void
	 * @throws IOException 
	 */
	@Override
	public void analysisAppTextFile() throws IOException {
		String copyPath = Utils.getAbsolutePath(Utils.getPath2("system.sysUser.appSystemOperationLog.upload.path",""),null);  //从该目录复制txt文件
		String path = Utils.getAbsolutePath(Utils.getPath2("system.sysUser.copyAppSystemOperationLog.upload.path",""),null); 	//复制到该目录下
		path = Utils.replaceDate(path);
		File file = new File(path);
		if(!file.exists()){											  	//如果该目录不存在!则创建
			file.mkdirs();
		}
		List<String> list = Utils.walk2(Paths.get(copyPath),-1);			//获得要复制的txt文件
		String txtName = "";
		for (String copyList : list) {
			if (Utils.isWindowsOS()) {  
				 txtName = copyList.substring(copyList.lastIndexOf("\\")+1);
			}else{
				 txtName = copyList.substring(copyList.lastIndexOf("/")+1);
			}
			FileUploadUtils.fileCopy(copyList,path+txtName);                  //开始复制
		}
		try(
		    DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(copyPath), "*.txt")){
		    for(Path entry : stream){														//文件夹下的txt
		    	String convert = entry.toString();
            	String last = convert.substring(convert.lastIndexOf("_")+1);
            	String lastBack = last.substring(0, 8);
            	if(Integer.parseInt(lastBack) <= Integer.parseInt(Utils.dayDate(-1))){			 //筛选出日期为今天之前的
			       for(String line : Files.readAllLines(entry, Charset.forName("UTF-8"))){		//txt中的数据条数
			          String[] contentStr = line.split("#");		         
			          if(contentStr.length!=0){
			        	  SysUserSystemOperationLog sysList = sysUserSystemOperationLogMapper.selectUuId(contentStr[1]); 	  //取得UUID去数据查找是该UUID是否存在
			        	  if(sysList != null){
			        		  SysUserSystemOperationLog systemOperationLog = new SysUserSystemOperationLog();			 	 //如果存在修改表里的客户端耗时
			        		  systemOperationLog.setClientTimestamp(contentStr[3]);										 	//时间戳
			        		  systemOperationLog.setClientPortTime(contentStr[2]);										   //总耗时
			        		  systemOperationLog.setGmtModified(new Date());		 									  //修改时间
			        		  systemOperationLog.setId(sysList.getId());												 //条件Id
			        		  sysUserSystemOperationLogMapper.updateByPrimaryKeySelective(systemOperationLog);			//找到数据后Update总耗时
			        	  }
			          }
			       }
			     //第三步:执行完一个txt中的数据就将该txt删除
			       File deleteFile = new File(entry.toString());	
			       deleteFile.delete();
            	}
		    }
		}
	}

	/*
	 * 删除APP端传过来的(七天以前)
	 * */
	public void txtDelete(){
		//删除七天以前的APP端txt文件
		/*String path = Utils.getPath("app.upload.root","") + Utils.getPath2("system.sysUser.copyAppSystemOperationLog.upload.path","");	//删除该目录下的txt文件
		List<String> list = Utils.walk2(Paths.get(path),-7);					   //获得要删除的txt文件
		for (String copyList : list) {
			 File deleteFile = new File(copyList);	
			 deleteFile.delete();							     //开始删除
		}
		logger.info("==========================删除APP端传过来的txt文件:"+list.size()+"个==============================");*/
	}
}
