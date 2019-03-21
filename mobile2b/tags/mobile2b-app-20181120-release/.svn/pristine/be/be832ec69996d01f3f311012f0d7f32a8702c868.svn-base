package com.nork.system.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.properties.ResProperties;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.Tools;
import com.nork.common.util.Utils;
import com.nork.pay.alipay.util.UtilDate;
import com.nork.system.dao.SysUserLoginLogMapper;
import com.nork.system.model.SysUserLoginLog;
import com.nork.system.model.search.SysUserLoginLogSearch;
import com.nork.system.service.SysUserLoginLogService;

/**   
 * @Title: SysUserLoginLogServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统模块-用户信息采集ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2017-06-28 16:14:59
 * @version V1.0   
 */
@Service("sysUserLoginLogService")
public class SysUserLoginLogServiceImpl implements SysUserLoginLogService {
	private static Logger logger = Logger.getLogger(SysUserLoginLogServiceImpl.class);
	private SysUserLoginLogMapper sysUserLoginLogMapper;

	@Autowired
	public void setSysUserLoginLogMapper(
			SysUserLoginLogMapper sysUserLoginLogMapper) {
		this.sysUserLoginLogMapper = sysUserLoginLogMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param sysUserLoginLog
	 * @return  int 
	 */
	@Override
	public int add(SysUserLoginLog sysUserLoginLog) {
		sysUserLoginLogMapper.insertSelective(sysUserLoginLog);
		return sysUserLoginLog.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param sysUserLoginLog
	 * @return  int 
	 */
	@Override
	public int update(SysUserLoginLog sysUserLoginLog) {
		return sysUserLoginLogMapper
				.updateByPrimaryKeySelective(sysUserLoginLog);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return sysUserLoginLogMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  SysUserLoginLog 
	 */
	@Override
	public SysUserLoginLog get(Integer id) {
		return sysUserLoginLogMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  sysUserLoginLog
	 * @return   List<SysUserLoginLog>
	 */
	@Override
	public List<SysUserLoginLog> getList(SysUserLoginLog sysUserLoginLog) {
	    return sysUserLoginLogMapper.selectList(sysUserLoginLog);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  sysUserLoginLog
	 * @return   int
	 */
	@Override
	public int getCount(SysUserLoginLogSearch sysUserLoginLogSearch){
		return  sysUserLoginLogMapper.selectCount(sysUserLoginLogSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  sysUserLoginLog
	 * @return   List<SysUserLoginLog>
	 */
	@Override
	public List<SysUserLoginLog> getPaginatedList(
			SysUserLoginLogSearch sysUserLoginLogSearch) {
		return sysUserLoginLogMapper.selectPaginatedList(sysUserLoginLogSearch);
	}

	/**
	 * 其他
	 * 
	 */
	
	/**
	 * 批量插入
	 * @param  List<SysUserLoginLog
	 * @return int
	 */
	@Override
	public int insertBatch(List<SysUserLoginLog> list) {
		return sysUserLoginLogMapper.insertLogBatch(list);
	}
	
	
	
	
	/**
	 *   txt存数据
	 * @param  sysUserLoginLog
	 * @return void
	 */
	@Override
	public void txtSaveUserLog(SysUserLoginLog sysUserLoginLog) {
		try {
			if(sysUserLoginLog.getUserMobile() != null){
				String fileName = sysUserLoginLog.getUserMobile()+"_"+UtilDate.getDate();
				String path = Utils.getAbsolutePath(Utils.getPath2("system.sysUser.loginLog.upload.path",""),null);
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
				sb.append(sysUserLoginLog.getUserId()+"#");		 			   						    //登录的用户
				sb.append(UtilDate.ConverToString(sysUserLoginLog.getLoginTime())+"#");  		       //登录,登出时间
				sb.append(sysUserLoginLog.getLoginType()+"#");             							  //登录类型 1:登录 2:登出
				sb.append(sysUserLoginLog.getClientIp()+"#");               						 //客户端IP
				sb.append(sysUserLoginLog.getServerIp()+"#");	         						    //服务器IP
				sb.append(sysUserLoginLog.getLoginDevice()+"#");        						   //操作系统
				sb.append(sysUserLoginLog.getSystemModel()+"#"+"\r\n"); 						  //操作系统型号
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
	public void userLoginDataEntry() {
		//第一步:复制文件
		//String path = Utils.getAbsolutePath(Utils.getPath2("system.sysUser.loginLog.upload.path",""),null);
		String copyPath = Utils.getAbsolutePath(Utils.getPath2("system.sysUser.loginLog.upload.path",""),null);
		logger.error("=========================从该目录复制txt文件======================="+copyPath); 
		//String path = Utils.getAbsolutePath(Utils.getPath2("system.sysUser.copyLoginLog.upload.path",""),null);	//复制到该目录下
		String path = Utils.getAbsolutePath(Utils.getValueByFileKey(ResProperties.RES,"system.sysUser.copyLoginLog.upload.path",null),null);	//复制到该目录下
		path = Utils.replaceDate(path);
		logger.error("=========================复制到该目录下======================"+path);
		File file = new File(path);
		if(!file.exists()){											  	//如果该目录不存在!则创建
			file.mkdirs();
		}
		String txtName = "";
		List<String> list = Utils.walk2(Paths.get(copyPath),-1);			//获得要复制的txt文件
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
		SysUserLoginLog sysUserLoginLog = null;
		try(
		    DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(copyPath), "*.txt")){
		    for(Path entry : stream){														//文件夹下的txt
		    	String convert = entry.toString();
            	String last = convert.substring(convert.lastIndexOf("_")+1);
            	String lastBack = last.substring(0, 8);
            	if(Integer.parseInt(lastBack) <= Integer.parseInt(Utils.dayDate(-1))){			//筛选出日期为今天以后的
            		List<SysUserLoginLog> userList = new ArrayList<SysUserLoginLog>();
            		
			       for(String line : Files.readAllLines(entry, Charset.forName("UTF-8"))){		//txt中的数据条数
			          String[] contentStr = line.split("#");		         
			          if(!"null".equals(contentStr[0]) && !"null".equals(contentStr[2]) && !"null".equals(contentStr[3]) 
			        		  && !"null".equals(contentStr[4]) && !"null".equals(contentStr[5]) && !"null".equals(contentStr[6])) {
				          sysUserLoginLog = new SysUserLoginLog();
				          sysUserLoginLog.setUserId(Integer.parseInt(contentStr[0]));
				          sysUserLoginLog.setLoginTime(UtilDate.ConverToDate(contentStr[1]));
				          sysUserLoginLog.setLoginType(Integer.parseInt(contentStr[2]));
				          sysUserLoginLog.setClientIp(contentStr[3]);
				          sysUserLoginLog.setServerIp(contentStr[4]);
				          sysUserLoginLog.setLoginDevice(contentStr[5]);
				          sysUserLoginLog.setSystemModel(contentStr[6]);
						  sysUserLoginLog.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
						  sysUserLoginLog.setCreator("sys");
						  sysUserLoginLog.setGmtCreate(new Date());
						  sysUserLoginLog.setModifier("sys");
				          sysUserLoginLog.setGmtModified(new Date());
				          sysUserLoginLog.setIsDeleted(0);
				          userList.add(sysUserLoginLog);
				          totaOne++;
			          }
			       }
			       if(userList.size()>0){
				    	sysUserLoginLogMapper.insertLogBatch(userList);	    //批量插入
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
		  }catch(Exception e){
		      e.printStackTrace();
		  }
	}

	/**
	 *  删除备份文件夹中七天前的txt
	 * @return void
	 */
	@Override
	public void deleteText() {
		/*String path = Utils.getAbsolutePath(Utils.getPath2("system.sysUser.copyLoginLog.upload.path",""),null);	//删除该目录下的txt文件
		List<String> list = Utils.walk2(Paths.get(path),-7);					   //获得要删除的txt文件
		for (String copyList : list) {
			   File deleteFile = new File(copyList);	
		       deleteFile.delete();							     //开始删除
		}
		logger.info("==========================删除txt文件:"+list.size()+"个==============================");*/
		String path = Tools.getRootPath(Utils.getPath("app.upload.root",""), "")+"/AA/e_userlogs";
		logger.debug("------删除7天之前的日志信息,path:" + path);
		Calendar cal = Calendar.getInstance();
	    int day = cal.get(Calendar.DATE)-7;								//删除七天以前的文件夹
	    int month = cal.get(Calendar.MONTH) + 1;
	    int year = cal.get(Calendar.YEAR);
	    List<Integer> numList = new ArrayList<Integer>();
		numList.add(year);
		numList.add(month);
		numList.add(day);
		Utils.deleteFile3(new File(path), numList);						//执行删除
	}

}
