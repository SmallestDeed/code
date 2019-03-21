package com.nork.system.service.impl;

import java.awt.Container;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nork.common.model.LoginUser;
import com.nork.common.util.Constants;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.FtpUploadUtils;
import com.nork.common.util.ResizeImage;
import com.nork.common.util.ThumbnailConvert;
import com.nork.common.util.Tools;
import com.nork.common.util.Utils;
import com.nork.system.dao.ResHousePicMapper;
import com.nork.system.model.ResHousePic;
import com.nork.system.model.SysUser;
import com.nork.system.model.search.ResHousePicSearch;
import com.nork.system.service.ResHousePicService;

/**   
 * @Title: ResHousePicServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统模块-户型、空间图片资源表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2016-08-13 16:34:09
 * @version V1.0   
 */
@Service("resHousePicService")
public class ResHousePicServiceImpl implements ResHousePicService {
	private static Logger logger = Logger.getLogger(ResPicServiceImpl.class);
	private ResHousePicMapper resHousePicMapper;

	/*** 获取配置文件 tmg.properties */
	private final static ResourceBundle app = ResourceBundle.getBundle("app");
	
	
	@Autowired
	public void setResHousePicMapper(
			ResHousePicMapper resHousePicMapper) {
		this.resHousePicMapper = resHousePicMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param resHousePic
	 * @return  int 
	 */
	@Override
	public int add(ResHousePic resHousePic) {
		resHousePicMapper.insertSelective(resHousePic);
		return resHousePic.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param resHousePic
	 * @return  int 
	 */
	@Override
	public int update(ResHousePic resHousePic) {
		return resHousePicMapper
				.updateByPrimaryKeySelective(resHousePic);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return resHousePicMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  ResHousePic 
	 */
	@Override
	public ResHousePic get(Integer id) {
		return resHousePicMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  resHousePic
	 * @return   List<ResHousePic>
	 */
	@Override
	public List<ResHousePic> getList(ResHousePic resHousePic) {
	    return resHousePicMapper.selectList(resHousePic);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  resHousePic
	 * @return   int
	 */
	@Override
	public int getCount(ResHousePicSearch resHousePicSearch){
		return  resHousePicMapper.selectCount(resHousePicSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  resHousePic
	 * @return   List<ResHousePic>
	 */
	@Override
	public List<ResHousePic> getPaginatedList(
			ResHousePicSearch resHousePicSearch) {
		return resHousePicMapper.selectPaginatedList(resHousePicSearch);
	}

	@Override
	public void updateSmallPicBusinessId(ResHousePic resHousePic) {
		List<Integer> ids=new ArrayList<Integer>();
		if(StringUtils.isNotBlank(resHousePic.getSmallPicInfo())){
			String[] strs=resHousePic.getSmallPicInfo().split(";");
			if(strs.length>0){
				for(String str:strs){
					String[] strs2=str.split(":");
					if(strs2.length>1){
						ids.add(Integer.parseInt(strs2[1]));
					}
				}
			}
		}
		/*回填缩略图的businessId*/
		if(ids.size()>0){
			for(Integer id:ids){
				ResHousePic resHousePic2=get(id);
				if(resHousePic2!=null){
					resHousePic2.setBusinessId(resHousePic.getBusinessId());
					update(resHousePic2);
				}
			}
		}	
	}
	

	
	/**生成缩略图*/
	@Override
	public Map<String, ResHousePic> createThumbnail(ResHousePic resHousePic,HttpServletRequest request) throws IOException{
			/**生成缩略图*/

				/*判断其是否要重新生成缩略图(查看smallPicInfo属性)*/
				if(StringUtils.isNotBlank(resHousePic.getSmallPicInfo())){
					/*判断为已生成过缩略图*/
					return new HashMap<String,ResHousePic>();
				}
				String fileKey=resHousePic.getFileKey();
				Integer fileKeyInfo=0;
				if(StringUtils.equals("product.baseProduct.piclist", fileKey)){
					/*识别为产品*/
					fileKeyInfo=1;
				}else if(StringUtils.equals("home.spaceCommon.pic", fileKey)){
					/*识别为空间数据-*/
					fileKeyInfo=2;
				}else if(StringUtils.equals("home.spaceCommon.view3dPic", fileKey)){
					/*识别为空间数据-*/
					fileKeyInfo=3;
				}else if(StringUtils.equals("design.designTemplet.pic", fileKey)){
					//design.designTemplet.pic
					/*识别为样板房-空间布局图*/
					fileKeyInfo=4;
				}else if(StringUtils.equals("design.designTemplet.piclist", fileKey)){
					//design.designTemplet.piclist
					/*识别为样板房-效果图*/
					fileKeyInfo=5;
				}else if(StringUtils.equals("product.productDecoration.piclist", fileKey)){
					//product.productDecoration.piclist
					/*识别为饰品图片列表*/
					fileKeyInfo=6;
				}else if(StringUtils.equals("home.designRecommendation.piclist", fileKey)){
					//home.designRecommendation.piclist
					/*识别为效果图推荐图片列表*/
					fileKeyInfo=7;
				}else if(StringUtils.equals("system.sysUser.pic.upload.path", fileKey)){
					//system.sysUser.pic
					/*识别为设计师头像*/
					fileKeyInfo=8;
				}else if(StringUtils.equals("system.sysUser.designerWorks.piclist", fileKey)){
					//system.sysUser.pic
					/*识别为设计师作品图片*/
					fileKeyInfo=9;
				}else if(StringUtils.equals("design.designTemplet.effectPlan", fileKey)){
					/*识别为样板房平面效果图*/
					fileKeyInfo=11;
				}else if(StringUtils.equals("home.spaceCommon.viewPlan", fileKey)){
					/*识别为空间俯视平面图*/
					fileKeyInfo=10;
				}else{
					/*不能识别*/
					return new HashMap<String,ResHousePic>();
				}
				String resPicUrl=Tools.getRootPath(resHousePic.getPicPath(), "D:\\app")+resHousePic.getPicPath();
				/*生成缩略图*/
				String resPicPath=resHousePic.getPicPath();
				if(StringUtils.isBlank(resPicPath)){
					logger.info("------id为"+resHousePic.getId()+"的图片没有存路径(picPath)");
				}
				String name=resPicPath.substring(resPicPath.lastIndexOf("/")+1);

				String urlWeb="";
				String urlIpad="";
				String fileKeyWeb="";
				String fileKeyIpad="";
				if(fileKeyInfo==1){
					fileKeyWeb="product.baseProduct.pic.web.upload.path";
					fileKeyIpad="product.baseProduct.pic.ipad.upload.path";
				}else if(fileKeyInfo==2){
					fileKeyWeb="home.spaceCommon.pic.small.web.upload.path";
					fileKeyIpad="home.spaceCommon.pic.small.ipad.upload.path";
				}else if(fileKeyInfo==3){
					fileKeyWeb="home.spaceCommon.view3dPic.small.web.upload.path";
					fileKeyIpad="home.spaceCommon.view3dPic.small.ipad.upload.path";
				}else if(fileKeyInfo==4){
					fileKeyWeb="design.designTemplet.pic.small.web.upload.path";
					fileKeyIpad="design.designTemplet.pic.small.ipad.upload.path";
				}else if(fileKeyInfo==5){
					fileKeyWeb="design.designTemplet.piclist.small.web.upload.path";
					fileKeyIpad="design.designTemplet.piclist.small.ipad.upload.path";
				}
				/*
				 * FIXME productDecoration无用，已删
				 * else if(fileKeyInfo==6){
					fileKeyWeb="product.productDecoration.pic.web.upload.path";
					fileKeyIpad="product.productDecoration.pic.ipad.upload.path";
				}*/else if(fileKeyInfo==7){
					fileKeyWeb="home.designRecommendation.pic.web.upload.path";
					fileKeyIpad="home.designRecommendation.pic.ipad.upload.path";
				}else if(fileKeyInfo==8){
					fileKeyWeb="system.sysUser.pic.small.web.upload.path";
					fileKeyIpad="system.sysUser.pic.small.ipad.upload.path";
				}else if(fileKeyInfo==9){
					fileKeyWeb="system.sysUser.designerWorks.piclist.small.web.upload.path";
					fileKeyIpad="system.sysUser.designerWorks.piclist.small.ipad.upload.path";
				}else if(fileKeyInfo==11){
					fileKeyWeb="design.designTemplet.effectPlan.small.web.upload.path";
					fileKeyIpad="design.designTemplet.effectPlan.small.ipad.upload.path";
				}else if(fileKeyInfo==10){
					fileKeyWeb="home.spaceCommon.viewPlan.small.web.upload.path";
					fileKeyIpad="home.spaceCommon.viewPlan.small.ipad.upload.path";
				}
				/**if(StringUtils.isBlank(Utils.getValue(fileKeyWeb,""))){
					logger.warn("请配置key:"+fileKeyWeb);
					throw new RuntimeException("请配置key:"+fileKeyWeb);
				}
				if(StringUtils.isBlank(Utils.getValue(fileKeyIpad,""))){
					logger.warn("请配置key:"+fileKeyIpad);
					throw new RuntimeException("请配置key:"+fileKeyIpad);
				}*/
				String fileKeyWebValue=app.getString(fileKeyWeb);
				String fileKeyIpadValue=app.getString(fileKeyIpad);
				
				if(fileKeyWebValue==null||"".equals(fileKeyWebValue)){
					logger.warn("请配置key:"+fileKeyWeb);
					throw new RuntimeException("请配置key:"+fileKeyWeb);
				}
				if(fileKeyIpadValue==null||"".equals(fileKeyIpadValue)){
					logger.warn("请配置key:"+fileKeyWeb);
					throw new RuntimeException("请配置key:"+fileKeyWeb);
				}
				/*urlWeb=Constants.UPLOAD_ROOT+Utils.getValue(fileKeyWeb,"")+"web_"+name;
				urlIpad=Constants.UPLOAD_ROOT+Utils.getValue(fileKeyIpad,"")+"ipad_"+name;*/
				urlWeb=Utils.getAbsolutePath(Utils.getValue(fileKeyWeb,"")+"web_"+name, null);
				urlIpad=Utils.getAbsolutePath(Utils.getValue(fileKeyIpad,"")+"ipad_"+name, null);
				/*由于特殊的路径要加code(样板房,所以加入以下逻辑处理)*/
				urlWeb=urlWeb.replace("[code]", resHousePic.getPicCode());
				urlIpad=urlIpad.replace("[code]", resHousePic.getPicCode());
				int ipadwidth=0;
				int ipadheight=0;
				int webwidth=0;
				int webheight=0;
				/*调整缩略图参考像素*/
				if(fileKeyInfo==7){
					/*效果图推荐(设计潮流)*/
					ipadwidth=664;
					ipadheight=381;
					webwidth=320;
					webheight=193;
				}else if(fileKeyInfo==2){
					ipadwidth=576;
					ipadheight=348;
					webwidth=296;
					webheight=180;
				}else if(fileKeyInfo==3){
					ipadwidth=576;
					ipadheight=348;
					webwidth=296;
					webheight=180;
				}else if(fileKeyInfo==4){
					/*样板房布局图*/
					ipadwidth=576;
					ipadheight=348;
					webwidth=296;
					webheight=180;
				}else if(fileKeyInfo==5){
					/*样板房效果图(俯视图)*/
					ipadwidth=576;
					ipadheight=348;
					webwidth=296;
					webheight=180;
				}else if(fileKeyInfo==8){
					/*设计师头像*/
					ipadwidth=180;
					ipadheight=180;
					webwidth=92;
					webheight=92;
				}else if(fileKeyInfo==9){
					/*设计师作品*/
					ipadwidth=96;
					ipadheight=96;
					webwidth=54;
					webheight=54;
				}else if(fileKeyInfo==11){
					ipadwidth=576;
					ipadheight=348;
					webwidth=296;
					webheight=180;
				}else if(fileKeyInfo==10){
					ipadwidth=576;
					ipadheight=348;
					webwidth=296;
					webheight=180;
				}else{
					/*产品*/
					ipadwidth=265;
					ipadheight=245;
					webwidth=141;
					webheight=132;
				}
				ResizeImage.createThumbnail(resPicUrl,urlIpad , ipadwidth, ipadheight);//生成ipad缩略图
				ResizeImage.createThumbnail(resPicUrl,urlWeb , webwidth, webheight);//生成web缩略图
				/*记录user*/
				LoginUser loginUser = new LoginUser();
				if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
						|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
					loginUser.setLoginName("nologin");
				} else {
					loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
				}
				/*保存两张缩略图信息到respic表中*/
				ResHousePic resPicIpad=new ResHousePic();
				File fileIpad=new File(urlIpad);
				File fileWeb=new File(urlWeb);
				BufferedImage imageIpad=null;
				BufferedImage imageWeb=null;
				try {
					imageIpad=javax.imageio.ImageIO.read(fileIpad);
					imageWeb=javax.imageio.ImageIO.read(fileWeb);
				} catch (IOException e) {
					/*读取图片失败*/
					e.printStackTrace();
				}
				resPicIpad.setPicName("ipad_"+name.substring(0,name.lastIndexOf(".")));
				resPicIpad.setPicFileName("ipad_"+name.substring(0,name.lastIndexOf(".")));
				resPicIpad.setPicSize((int)(fileIpad.length()));
				resPicIpad.setPicWeight(""+imageIpad.getWidth());
				resPicIpad.setPicHigh(""+imageIpad.getHeight());
				resPicIpad.setPicSuffix(resHousePic.getPicSuffix());
				resPicIpad.setPicPath(Utils.getValue(fileKeyIpad,"")+"ipad_"+name);
				resPicIpad.setFileKey(fileKeyIpad.replace(".upload.path", ""));
				if(fileKeyInfo==1){
					/*resPicIpad.setPicPath(app.getString("product.baseProduct.pic.upload.path")+"ipad_"+name);
					resPicIpad.setFileKey("product.baseProduct.pic");*/
					resPicIpad.setPicType("产品ipad端缩略图");
				}else if(fileKeyInfo==2){
					/*resPicIpad.setPicPath(app.getString("home.spaceCommon.pic.upload.path")+"small/ipad_"+name);
					resPicIpad.setFileKey("home.spaceCommon.pic.small");*/
					resPicIpad.setPicType("空间户型图ipad端缩略图");
				}else if(fileKeyInfo==3){
					/*resPicIpad.setPicPath(app.getString("home.spaceCommon.view3dPic.upload.path")+"small/ipad_"+name);
					resPicIpad.setFileKey("home.spaceCommon.view3dPic.small");*/
					resPicIpad.setPicType("3D空间俯视图ipad端缩略图");
				}else if(fileKeyInfo==4){
					/*resPicIpad.setPicPath(app.getString("design.designTemplet.pic.upload.path")+"small/ipad_"+name);
					resPicIpad.setFileKey("design.designTemplet.pic.small");*/
					resPicIpad.setPicType("样板房-空间布局图ipad端缩略图");
				}else if(fileKeyInfo==5){
					/*resPicIpad.setPicPath(app.getString("design.designTemplet.piclist.upload.path")+"small/ipad_"+name);
					resPicIpad.setFileKey("design.designTemplet.piclist.small");*/
					resPicIpad.setPicType("样板房-效果图ipad端缩略图");
				}/*
				FIXME productDecoration无用，已删
				else if(fileKeyInfo==6){
					resPicIpad.setPicPath(app.getString("product.productDecoration.pic.upload.path")+"ipad_"+name);
					resPicIpad.setFileKey("product.productDecoration.pic");
					resPicIpad.setPicType("饰品ipad端缩略图");
				}*/else if(fileKeyInfo==7){
					/*resPicIpad.setPicPath(app.getString("home.designRecommendation.pic.upload.path")+"ipad_"+name);
					resPicIpad.setFileKey("home.designRecommendation.pic");*/
					resPicIpad.setPicType("效果图推荐ipad端缩略图");
				}else if(fileKeyInfo==8){
					/*resPicIpad.setPicPath(app.getString("system.sysUser.pic.upload.path")+"small/ipad_"+name);
					resPicIpad.setFileKey("system.sysUser.pic.small");*/
					resPicIpad.setPicType("用户头像ipad端缩略图");
				}else if(fileKeyInfo==9){
					/*resPicIpad.setPicPath(app.getString("system.sysUser.designerWorks.piclist.path")+"small/ipad_"+name);
					resPicIpad.setFileKey("system.sysUser.designerWorks.piclist.small");*/
					resPicIpad.setPicType("设计师作品ipad端缩略图");
				}else if(fileKeyInfo==11){
					/*resPicIpad.setPicPath(app.getString("design.designTemplet.effectPlan.upload.path")+"small/ipad_"+name);
					resPicIpad.setFileKey("design.designTemplet.effectPlan.small");*/
					resPicIpad.setPicType("样板房平面效果图ipad端缩略图");
				}else if(fileKeyInfo==10){
					/*resPicIpad.setPicPath(app.getString("home.spaceCommon.viewPlan.upload.path")+"small/ipad_"+name);
					resPicIpad.setFileKey("home.spaceCommon.viewPlan.small");*/
					resPicIpad.setPicType("空间俯视平面图ipad端缩略图");
				}
				/*由于特殊的路径要加code(样板房,所以加入以下逻辑处理)*/
				resPicIpad.setPicPath(resPicIpad.getPicPath().replace("[code]", resHousePic.getPicCode()));
				
				//resPicIpad.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS)+ "_"+ Utils.generateRandomDigitString(6));
				/*修改缩略图code和原图一致*/
				resPicIpad.setSysCode(resHousePic.getSysCode());
				resPicIpad.setGmtCreate(new Date());
				resPicIpad.setCreator(loginUser.getLoginName());
				resPicIpad.setIsDeleted(0);
				resPicIpad.setGmtModified(new Date());
				resPicIpad.setModifier(loginUser.getLoginName());
				resPicIpad.setPicCode(resPicIpad.getSysCode());
				resPicIpad.setPicFormat(resHousePic.getPicSuffix().substring(1,resHousePic.getPicSuffix().length()));
				resPicIpad.setBusinessId(resHousePic.getBusinessId());
				add(resPicIpad);//保存ipad端缩略图
				/*支持ftp上传ipad端缩略图*/
				String ipadImageName=resPicIpad.getPicName();
				String ipadSuffix=resPicIpad.getPicSuffix();
				String ipadDbFilePath=urlIpad;
				String ipadFilePath=resPicIpad.getPicPath().substring(0,resPicIpad.getPicPath().lastIndexOf("/")+1);
				try{
					boolean flag = false;
					//仅支持ftp服务器上传
					if(Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD)==2){
						logger.info("------用户头像上传ftp服务器删除本地文件");
						flag = FtpUploadUtils.uploadFile(ipadImageName + ipadSuffix,ipadDbFilePath,ipadFilePath);
						if(flag){
							//删除本地
							FileUploadUtils.deleteFile(ipadDbFilePath);
						}else{
							logger.info("---------仅支持ftp服务器文件上传异常！");
						}
					}
					//3 本地和ftp同时上传(默认是本地上传)
					if(Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD)==3){
						logger.info("------用户头像上传ftp服务器不删除本地文件");
						flag = FtpUploadUtils.uploadFile(ipadImageName + ipadSuffix,ipadDbFilePath,ipadFilePath);
						if(!flag){
							logger.info("---------本地和ftp服务器同时文件上传异常！");
						}
					}
				}catch(Exception e){
					e.printStackTrace();
					logger.error("----ftp上传异常："+e.getMessage());
				}
				
				/*保存web端缩略图*/
				ResHousePic resPicWeb=new ResHousePic();
				resPicWeb.setPicName("web_"+name.substring(0,name.lastIndexOf(".")));
				resPicWeb.setPicFileName("web_"+name.substring(0,name.lastIndexOf(".")));
				resPicWeb.setPicSize((int)(fileWeb.length()));
				resPicWeb.setPicWeight(""+imageWeb.getWidth());
				resPicWeb.setPicHigh(""+imageWeb.getHeight());
				resPicWeb.setPicSuffix(resHousePic.getPicSuffix());
				resPicWeb.setPicPath(Utils.getValue(fileKeyWeb,"")+"Web_"+name);
				resPicWeb.setFileKey(fileKeyWeb.replace(".upload.path", ""));
				if(fileKeyInfo==1){
					/*resPicWeb.setPicPath(app.getString("product.baseProduct.pic.upload.path")+"web_"+name);
					resPicWeb.setFileKey("product.baseProduct.pic");*/
					resPicWeb.setPicType("产品web端缩略图");
				}else if(fileKeyInfo==2){
					/*resPicWeb.setPicPath(app.getString("home.spaceCommon.pic.upload.path")+"small/web_"+name);
					resPicWeb.setFileKey("home.spaceCommon.pic.small");*/
					resPicWeb.setPicType("空间户型图web端缩略图");
				}else if(fileKeyInfo==3){
					/*resPicWeb.setPicPath(app.getString("home.spaceCommon.view3dPic.upload.path")+"small/web_"+name);
					resPicWeb.setFileKey("home.spaceCommon.view3dPic.small");*/
					resPicWeb.setPicType("3D空间俯视图web端缩略图");
				}else if(fileKeyInfo==4){
					/*resPicWeb.setPicPath(app.getString("design.designTemplet.pic.upload.path")+"small/web_"+name);
					resPicWeb.setFileKey("design.designTemplet.pic.small");*/
					resPicWeb.setPicType("样板房-空间布局图web端缩略图");
				}else if(fileKeyInfo==5){
					/*resPicWeb.setPicPath(app.getString("design.designTemplet.piclist.upload.path")+"small/web_"+name);
					resPicWeb.setFileKey("design.designTemplet.piclist.small");*/
					resPicWeb.setPicType("样板房-效果图web端缩略图");
				}
				/*
				 * FIXME productDecoration无用，已删
				 * else if(fileKeyInfo==6){
					resPicWeb.setPicPath(app.getString("product.productDecoration.pic.upload.path")+"web_"+name);
					resPicWeb.setFileKey("product.productDecoration.pic");
					resPicWeb.setPicType("饰品web端缩略图");
				}*/
				else if(fileKeyInfo==7){
					/*resPicWeb.setPicPath(app.getString("home.designRecommendation.pic.upload.path")+"web_"+name);
					resPicWeb.setFileKey("home.designRecommendation.pic");*/
					resPicWeb.setPicType("效果图推荐web端缩略图");
				}else if(fileKeyInfo==8){
					/*resPicWeb.setPicPath(app.getString("system.sysUser.pic.upload.path")+"small/web_"+name);
					resPicWeb.setFileKey("system.sysUser.pic.small");*/
					resPicWeb.setPicType("用户头像web端缩略图");
				}else if(fileKeyInfo==9){
					/*resPicWeb.setPicPath(app.getString("system.sysUser.designerWorks.piclist.path")+"small/web_"+name);
					resPicWeb.setFileKey("system.sysUser.designerWorks.piclist.small");*/
					resPicWeb.setPicType("设计师作品web端缩略图");
				}else if(fileKeyInfo==11){
					/*resPicWeb.setPicPath(app.getString("design.designTemplet.effectPlan.upload.path")+"small/web_"+name);
					resPicWeb.setFileKey("design.designTemplet.effectPlan.small");*/
					resPicWeb.setPicType("样板房平面效果图web端缩略图");
				}else if(fileKeyInfo==10){
					/*resPicWeb.setPicPath(app.getString("home.spaceCommon.viewPlan.upload.path")+"small/web_"+name);
					resPicWeb.setFileKey("home.spaceCommon.viewPlan.small");*/
					resPicWeb.setPicType("空间平面俯视图web端缩略图");
				}
				/*由于特殊的路径要加code(样板房,所以加入以下逻辑处理)*/
				resPicWeb.setPicPath(resPicWeb.getPicPath().replace("[code]", resHousePic.getPicCode()));
				
				//resPicWeb.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS)+ "_"+ Utils.generateRandomDigitString(6));
				/*修改为缩略图和原图sysCode一致*/
				resPicWeb.setSysCode(resHousePic.getSysCode());
				resPicWeb.setGmtCreate(new Date());
				resPicWeb.setCreator(loginUser.getLoginName());
				resPicWeb.setIsDeleted(0);
				resPicWeb.setGmtModified(new Date());
				resPicWeb.setModifier(loginUser.getLoginName());
				resPicWeb.setPicCode(resHousePic.getSysCode());
				resPicWeb.setPicFormat(resHousePic.getPicSuffix().substring(1,resHousePic.getPicSuffix().length()));
				resPicWeb.setBusinessId(resHousePic.getBusinessId());
				add(resPicWeb);//保存web端缩略图
				
				/*支持ftp上传web端缩略图*/
				String webImageName=resPicWeb.getPicName();
				String webSuffix=resPicWeb.getPicSuffix();
				String webDbFilePath=urlWeb;
				String webFilePath=resPicWeb.getPicPath().substring(0,resPicWeb.getPicPath().lastIndexOf("/")+1);
				try{
					boolean flag = false;
					//仅支持ftp服务器上传
					if(Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD)==2){
						logger.info("------用户头像上传ftp服务器删除本地文件");
						flag = FtpUploadUtils.uploadFile(webImageName + webSuffix,webDbFilePath,webFilePath);
						if(flag){
							//删除本地
							FileUploadUtils.deleteFile(webDbFilePath);
						}else{
							logger.info("---------仅支持ftp服务器文件上传异常！");
						}
					}
					//3 本地和ftp同时上传(默认是本地上传)
					if(Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD)==3){
						logger.info("------用户头像上传ftp服务器不删除本地文件");
						flag = FtpUploadUtils.uploadFile(webImageName + webSuffix,webDbFilePath,webFilePath);
						if(!flag){
							logger.info("---------本地和ftp服务器同时文件上传异常！");
						}
					}
				}catch(Exception e){
					e.printStackTrace();
					logger.error("----ftp上传异常："+e.getMessage());
				}
				
				/*补充resPic信息(绑定其缩略图id:放在smallPicInfo字段)*/
				String smallPicInfo="";
				if(resPicWeb.getId()!=null&&resPicWeb.getId()>0){
					smallPicInfo+="web:"+resPicWeb.getId()+";";
				}
				if(resPicIpad.getId()!=null&&resPicIpad.getId()>0){
					smallPicInfo+="ipad:"+resPicIpad.getId()+";";
				}
				resHousePic.setSmallPicInfo(smallPicInfo);
				update(resHousePic);
				/*返回生成的两张缩略图信息*/
				Map<String,ResHousePic> map=new HashMap<String,ResHousePic>();
				map.put("web", resPicWeb);
				map.put("ipad", resPicIpad);
				return map;
			}

	@Override
	public void backFillResPic(Integer spaceId, Integer picId, String picKey,
			String spaceCode) {
		ResHousePic resHousePic = this.get(picId);
		if(resHousePic != null){
			resHousePic.setBusinessId(spaceId);
			resHousePic.setSysCode(spaceCode);
			resHousePic.setPicCode(spaceCode);
			resHousePicMapper.updateByPrimaryKeySelective(resHousePic);
//			this.update(resPic,businessId,picKey,sysCode);
		}
		
	}

	public Integer saveMultipartFile(MultipartFile file, String fileKey, SysUser sysUser) {
		String UPLOAD_PATH = fileKey;
		ResHousePic resHousePic=new ResHousePic();
		if (file != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(FileUploadUtils.UPLOADPATHTKEY, UPLOAD_PATH);
			map.put(FileUploadUtils.FILE, file);
			Long fileSize = file.getSize();
			String originalFilename = file.getOriginalFilename();
			String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
			String filename = originalFilename.substring(0, originalFilename.lastIndexOf("."));
			boolean flag = FileUploadUtils.saveFile2(map);
			if (flag) {
				String realPath = (String) map.get(FileUploadUtils.SERVER_FILE_PATH);
				String dbFilePath = (String) map.get(FileUploadUtils.DB_FILE_PATH);
				String ftpFilePath = (String) map.get("filePath");
				String finalFlieName = (String) map.get("finalFlieName");
				File sFile = new File(realPath);
				try {
					/* 应对CMYK模式图片上传报错的情况 */
					BufferedImage bufferedImage = null;
					try {
						bufferedImage = ImageIO.read(sFile);
					} catch (Exception e) {
						try {
							ThumbnailConvert tc = new ThumbnailConvert();
							tc.setCMYK_COMMAND(sFile.getPath());
							bufferedImage = null;
							Image image = Toolkit.getDefaultToolkit().getImage(sFile.getPath());
							MediaTracker mediaTracker = new MediaTracker(new Container());
							mediaTracker.addImage(bufferedImage, 0);
							mediaTracker.waitForID(0);
							bufferedImage = ThumbnailConvert.toBufferedImage(image);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					/* 应对CMYK模式图片上传报错的情况END */
					int width = bufferedImage.getWidth(); // 图片宽度
					int height = bufferedImage.getHeight();// 图片高度
					String format = suffix.replace(".", "");
					resHousePic.setPicWeight(new Integer(width).toString());
					resHousePic.setPicHigh(new Integer(height).toString());
					resHousePic.setPicFormat(format);// 图片格式
				} catch (Exception e) {
					e.printStackTrace();
				}
				/*ftp处理*/
				if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 2) {
					FtpUploadUtils.uploadFile(finalFlieName, realPath, ftpFilePath);
				}
				if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 3) {
					FtpUploadUtils.uploadFile(finalFlieName, realPath, ftpFilePath);
				}
				/*ftp处理->end*/
				resHousePic.setPicPath(dbFilePath);
				resHousePic.setFileKey(resHousePic.getFileKey());
				resHousePic.setBusinessId(resHousePic.getBusinessId());
				if (StringUtils.isEmpty(resHousePic.getPicName())) {
					if (filename.lastIndexOf(".") != -1) {
						resHousePic.setPicName(filename.substring(0, filename.lastIndexOf(".")));// 业务名称
					} else {
						resHousePic.setPicName(filename);
					}
				}
				resHousePic.setPicFileName(filename);
				resHousePic.setPicSuffix(suffix);
				resHousePic.setPicSize(new Long(fileSize).intValue());
				sysSave(resHousePic,sysUser);
				resHousePic.setPicCode(resHousePic.getSysCode());
				resHousePic.setPicType("户型申请图");
				resHousePic.setPicLevel("0");
				resHousePic.setFileKey(fileKey.replace(".upload.path", ""));
				return add(resHousePic);
			}
		}
		return 0;
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(ResHousePic model,SysUser loginUser) {
		if (model != null) {
			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getNickName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(
							Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getNickName());
		}
	}
	
}
