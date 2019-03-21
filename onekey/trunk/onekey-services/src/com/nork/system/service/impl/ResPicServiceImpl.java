package com.nork.system.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.nork.onekeydesign.service.DesignRenderRoamService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.model.FileModel;
import com.nork.common.model.LoginUser;
import com.nork.common.properties.ResProperties;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.FtpUploadUtils;
import com.nork.common.util.ResizeImage;
import com.nork.common.util.Tools;
import com.nork.common.util.Utils;
import com.nork.onekeydesign.model.DesignPlan;
import com.nork.onekeydesign.service.DesignPlanService;
import com.nork.sandu.model.dto.TDesignSketch;
import com.nork.sync.model.ResEntity;
import com.nork.system.dao.ResPicMapper;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.search.ResPicSearch;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResRenderPicService;

/**   
 * @Title: ResPicServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统-图片资源库ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 16:06:59
 * @version V1.0   
 */
@Service("resPicService")
@Transactional
public class ResPicServiceImpl implements ResPicService {

	private ResPicMapper resPicMapper;

	private static Logger logger = Logger
			.getLogger(ResPicServiceImpl.class);
	
	@Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private ResRenderPicService resRenderPicService;
	@Autowired
	private DesignRenderRoamService designRenderRoamService;
	@Autowired
	public void setResPicMapper(
			ResPicMapper resPicMapper) {
		this.resPicMapper = resPicMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param resPic
	 * @return  int 
	 */
	@Override
	public int add(ResPic resPic) {
		resPicMapper.insertSelective(resPic);
		return resPic.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param resPic
	 * @return  int 
	 */
	@Override
	public int update(ResPic resPic) {
		//获取多个 resPic filekeys ,busniessIds
		
		return resPicMapper
				.updateByPrimaryKeySelective(resPic);
	}
	
	/**
	 *    更新数据
	 *
	 * @param resPic
	 * @return  int 
	 */
	@Override
	public int update(ResPic resPic,Integer businessId,String picKey,String sysCode) {
		//获取多个 resPic filekeys ,busniessIds
		String fileKey = resPic.getFileKey();
		Integer bId = resPic.getBusinessId();
		//当前资源 业务Id不为空，且fileKeys为空，就必须给fileKeys赋初始值
		if(bId != null && bId > 0  && !bId.equals(businessId) && StringUtils.isBlank(resPic.getFileKeys())){
			resPic.setFileKeys(fileKey);
		}
		String fileKeys = "" ;
		//如果fileKeys不为空，累加fileKeys
		if(StringUtils.isNotBlank(resPic.getFileKeys())){
			fileKeys = resPic.getFileKeys()+","+picKey;
		}else{
			fileKeys = picKey+"";
		}
		resPic.setFileKeys(fileKeys);
		
		String businessIds = "";
		//业务Ids为空或不为空，则赋值或累加
		if(bId != null && bId > 0 && !bId.equals(businessId) && StringUtils.isBlank(resPic.getBusinessIds())){
			businessIds =  bId + "," + businessId ;
			resPic.setBusinessId(0);
		}else if(StringUtils.isNotBlank(resPic.getBusinessIds())){
			businessIds =   resPic.getBusinessIds()+","+businessId ;
			resPic.setBusinessId(0);
		}else{
			businessIds = businessId+"";
			resPic.setBusinessId(businessId);
		}
		resPic.setBusinessIds(businessIds);
		resPic.setSysCode(sysCode);
		resPic.setPicCode(sysCode);
		return resPicMapper.updateByPrimaryKeySelective(resPic);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return resPicMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  ResPic 
	 */
	@Override
	public ResPic get(Integer id) {
		return resPicMapper.selectByPrimaryKey(id);
	}

    public TDesignSketch findById(Integer id){
    	return resPicMapper.findById(id);
    }
    
    public int findCount(ResPicSearch resPicSearch){
    	return resPicMapper.findCount(resPicSearch);
    }
    
    public List<TDesignSketch> findList(ResPicSearch resPicSearch){
    	return resPicMapper.findList(resPicSearch);
    }
	
	/**
	 * 所有数据
	 * 
	 * @param  resPic
	 * @return   List<ResPic>
	 */
	@Override
	public List<ResPic> getList(ResPic resPic) {
	    return resPicMapper.selectList(resPic);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  resPic
	 * @return   int
	 */
	@Override
	public int getCount(ResPicSearch resPicSearch){
		return  resPicMapper.selectCount(resPicSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  resPic
	 * @return   List<ResPic>
	 */
	@Override
	public List<ResPic> getPaginatedList(
			ResPicSearch resPicSearch) {
		return resPicMapper.selectPaginatedList(resPicSearch);
	}

	/**
	 * 将图片信息记录到数据库中
	 */
	@Override
	public Integer saveFiles(String planId,List<Map> list,String level,String renderingType){
		Integer smallPicId=0;
		if(StringUtils.isNotBlank(planId) ){

			DesignPlan designPlan = designPlanService.get(Integer.valueOf(planId));
//			ResPic smallRenderPic = null;
			ResRenderPic smallRenderPic = null;
			ResRenderPic renderPic = null;
//			ResPic renderPic = null;
			for( Map smallRenderPicMap : list ){
				//保存渲染图原图
				Map renderPicMap = (Map)smallRenderPicMap.get("original");
				renderPic = assembleResPic(renderPicMap);
				renderPic.setIsDeleted(0);
				renderPic.setCreator(designPlan.getCreator());
				renderPic.setModifier(designPlan.getCreator());
				renderPic.setBusinessId(Integer.valueOf(planId));
				renderPic.setPicLevel(Integer.getInteger(level));
				renderPic.setRenderingType(Integer.getInteger(renderingType));
				renderPic.setPicType("高清原图");
				renderPic.setFileKey(ResProperties.DESIGNPLAN_RENDER_PIC_FILEKEY);
				int original = resRenderPicService.add(renderPic);
				
				smallRenderPic = assembleResPic(smallRenderPicMap);
				smallRenderPic.setPid(original);
				smallRenderPic.setCreator(designPlan.getCreator());
				smallRenderPic.setIsDeleted(0);
				smallRenderPic.setModifier(designPlan.getCreator());
				smallRenderPic.setBusinessId(Integer.valueOf(planId));
				smallRenderPic.setPicLevel(Integer.getInteger(level));
				smallRenderPic.setFileKey(ResProperties.DESIGNPLAN_RENDER_PIC_SMALL_FILEKEY);
				smallRenderPic.setPicType("高清缩略图");
				//保存缩略图
				int small = resRenderPicService.add(smallRenderPic);
				

			}
		}
		 
		return smallPicId;
	}

	/**
	 * 组装resPic
	 * @param map
	 * @return
	 */
	public ResRenderPic assembleResPic(Map map){
//		ResPic resPic = new ResPic();
		ResRenderPic resPic = new ResRenderPic();
		String dbFilePath = Utils.dealWithPath(map.get(FileUploadUtils.DB_FILE_PATH).toString(), "linux");
		/*String dbFilePath = map.get(FileUploadUtils.DB_FILE_PATH).toString();*/
		resPic.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
		resPic.setPicCode(resPic.getSysCode());
		resPic.setGmtCreate(new Date());
		resPic.setGmtModified(new Date());
		resPic.setIsDeleted(0);
		resPic.setPicName(map.get(FileModel.FILE_ORIGINAL_NAME)==null?"":map.get(FileModel.FILE_ORIGINAL_NAME).toString());
		resPic.setPicSize(map.get(FileModel.FILE_SIZE)==null?-1:Integer.valueOf(map.get(FileModel.FILE_SIZE).toString()));
		resPic.setPicSuffix(map.get(FileModel.FILE_SUFFIX).toString());
		resPic.setPicFileName(dbFilePath.substring(dbFilePath.lastIndexOf("/") + 1, dbFilePath.lastIndexOf(".")));
		resPic.setPicPath(dbFilePath);
		resPic.setFileKey(map.get(FileModel.FILE_KEY).toString());
		resPic.setPicType("无");
		resPic.setViewPoint(Integer.parseInt(map.get("viewPoint").toString()));
		resPic.setScene(Integer.parseInt(map.get("scene").toString()));
		return resPic;
	}

	/**
	 * 回填图片资源
	 * @param businessId 业务表ID
	 * @param rePicId 图片资源表ID
	 */
	public void backFillResPic(Integer businessId,Integer rePicId,String picKey,String sysCode){
		ResPic resPic = this.get(rePicId);
		if(resPic != null){
			this.update(resPic,businessId,picKey,sysCode);
		}
	}

	/**
	 * 删除当前资源信息
	 * @param businessId 业务Id
	 * @param picId  图片资源Id 
	 */
	public void clearBackfillResPic(Integer businessId,Integer picId){
		StringBuffer bidSb = new StringBuffer();
		StringBuffer keySb = new StringBuffer();
		ResPic resPic = this.get(picId);
		if(resPic != null){
			String bids = resPic.getBusinessIds();
			String keys = resPic.getFileKeys();
			if( bids != null ){
				String bidArr[] = bids.split(",");
				String keyArr[] = keys.split(",");
				for( int i=0;i<bidArr.length;i++ ){
					if(!bidArr[i].equals(businessId+"")){
						bidSb.append(bidArr[i]).append(",");
						keySb.append(keyArr[i]).append(",");
					}
				}
				String bid = bidSb.toString();
				String key = keySb.toString();
				if(bid.length()>0){
					bid = bid.substring(0, bid.length()-1);
					key = key.substring(0, key.length()-1);
				}
				resPic.setBusinessIds(bid);
				resPic.setFileKeys(key);
				//判断bid，给businessId赋值
				if(StringUtils.isNotBlank(bid)){
					if(bid.contains(","))
						resPic.setBusinessId(0);
					else
						resPic.setBusinessId(Utils.getIntValue(bid));
				}else{
					resPic.setBusinessId(0);
				}
				this.update(resPic);
			}
		}
	}
	
	private final static ResourceBundle app = ResourceBundle.getBundle("app");
	
	/**生成缩略图*/
	public Map<String,ResPic> createThumbnail(ResPic resPic,HttpServletRequest request) throws IOException{
		/*判断其是否要重新生成缩略图(查看smallPicInfo属性)*/
		if(StringUtils.isNotBlank(resPic.getSmallPicInfo())){
			/*判断为已生成过缩略图*/
			return new HashMap<String,ResPic>();
		}
		String fileKey=resPic.getFileKey();
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
		}else if(StringUtils.equals("onekeydesign.designTemplet.pic", fileKey)){
			//onekeydesign.designTemplet.pic
			/*识别为样板房-空间布局图*/
			fileKeyInfo=4;
		}else if(StringUtils.equals("onekeydesign.designTemplet.piclist", fileKey)){
			//onekeydesign.designTemplet.piclist
			/*识别为样板房-效果图*/
			fileKeyInfo=5;
		}
		/*
		 * FIXME productDecoration无用，已删
		 * else if(StringUtils.equals("product.productDecoration.piclist", fileKey)){
			//product.productDecoration.piclist
			识别为饰品图片列表
			fileKeyInfo=6;
		}*/else if(StringUtils.equals("home.designRecommendation.piclist", fileKey)){
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
		}else if(StringUtils.equals("onekeydesign.designTemplet.effectPlan", fileKey)){
			/*识别为样板房平面效果图*/
			fileKeyInfo=11;
		}else if(StringUtils.equals("home.spaceCommon.viewPlan", fileKey)){
			/*识别为空间俯视平面图*/
			fileKeyInfo=10;
		}else{
			/*不能识别*/
			return new HashMap<String,ResPic>();
		}
		String UPLOAD_ROOT = Tools.getRootPath(resPic.getPicPath(), "D:\\app");
		String resPicUrl=UPLOAD_ROOT+resPic.getPicPath();
		String resSmallPicFolder="";
		
		if(fileKeyInfo==1){
			//resSmallPicFolder=UPLOAD_ROOT+app.getString("product.baseProduct.pic.upload.path");//缩略图生成路径(不包括缩略图名)
			resSmallPicFolder = UPLOAD_ROOT + Utils.getPropertyName("config/res", "product.baseProduct.pic.upload.path", "/product/baseProduct/pic/");
		}else if(fileKeyInfo==2){
			//resSmallPicFolder=UPLOAD_ROOT+app.getString("home.spaceCommon.pic.upload.path");//缩略图生成路径(不包括缩略图名)
			resSmallPicFolder=UPLOAD_ROOT + Utils.getPropertyName("config/res", "home.spaceCommon.pic.upload.path", "/home/spaceCommon/pic/");
		}else if(fileKeyInfo==3){
			//resSmallPicFolder=UPLOAD_ROOT+app.getString("home.spaceCommon.view3dPic.upload.path");//缩略图生成路径(不包括缩略图名)
			resSmallPicFolder=UPLOAD_ROOT + Utils.getPropertyName("config/res", "home.spaceCommon.view3dPic.upload.path", "/home/spaceCommon/view3dPic/");
		}else if(fileKeyInfo==4){
			//resSmallPicFolder=UPLOAD_ROOT+app.getString("onekeydesign.designTemplet.pic.upload.path");//缩略图生成路径(不包括缩略图名)
			resSmallPicFolder=UPLOAD_ROOT + Utils.getPropertyName("config/res", "design.designTemplet.pic.upload.path", "/onekeydesign/designTemplet/[code]/pic/");
		}else if(fileKeyInfo==5){
			//resSmallPicFolder=UPLOAD_ROOT+app.getString("onekeydesign.designTemplet.piclist.upload.path");//缩略图生成路径(不包括缩略图名)
			resSmallPicFolder=UPLOAD_ROOT+Utils.getPropertyName("config/res", "design.designTemplet.piclist.upload.path", "/onekeydesign/designTemplet/[code]/piclist/");
		}
		/*
		 * FIXME productDecoration无用，已删
		 * else if(fileKeyInfo==6){
			//resSmallPicFolder=UPLOAD_ROOT+app.getString("product.productDecoration.pic.upload.path");//缩略图生成路径(不包括缩略图名)
			resSmallPicFolder=UPLOAD_ROOT+Utils.getPropertyName("config/res", "product.productDecoration.pic.upload.path", "/product/productDecoration/pic/");//缩略图生成路径(不包括缩略图名)
		}*/
		else if(fileKeyInfo==7){
			//resSmallPicFolder=UPLOAD_ROOT+app.getString("home.designRecommendation.pic.upload.path");//缩略图生成路径(不包括缩略图名)
			resSmallPicFolder=UPLOAD_ROOT+Utils.getPropertyName("config/res", "home.designRecommendation.pic.upload.path", "/home/designRecommendation/pic/");
		}else if(fileKeyInfo==8){
			//resSmallPicFolder=UPLOAD_ROOT+app.getString("system.sysUser.pic.upload.path");//缩略图生成路径(不包括缩略图名)
			resSmallPicFolder =  UPLOAD_ROOT + Utils.getPropertyName("config/res", "system.sysUser.pic.upload.path", "/system/sysUser/pic/");
		}else if(fileKeyInfo==9){
			//resSmallPicFolder=UPLOAD_ROOT+app.getString("system.sysUser.designerWorks.piclist.path");//缩略图生成路径(不包括缩略图名)
			resSmallPicFolder = UPLOAD_ROOT + Utils.getPropertyName("config/res", "system.sysUser.designerWorks.piclist.path", "/system/sysUser/designerWorks/piclist/");
		}else if(fileKeyInfo==11){
			//resSmallPicFolder=UPLOAD_ROOT+app.getString("onekeydesign.designTemplet.effectPlan.upload.path");//缩略图生成路径(不包括缩略图名)
			resSmallPicFolder=UPLOAD_ROOT+Utils.getPropertyName("config/res", "design.designTemplet.effectPlan.upload.path", "/c_basedesign/onekeydesign/designTemplet/effectPlan/");//缩略图生成路径(不包括缩略图名)
		}else if(fileKeyInfo==10){
			//resSmallPicFolder=UPLOAD_ROOT+app.getString("home.spaceCommon.viewPlan.upload.path");//缩略图生成路径(不包括缩略图名)
			resSmallPicFolder = UPLOAD_ROOT+Utils.getPropertyName("config/res", "home.spaceCommon.viewPlan.upload.path", "/home/spaceCommon/[code]/viewPlan/");
		}
		/*由于特殊的路径要加code(样板房,所以加入以下逻辑处理)*/
		//resSmallPicFolder=resSmallPicFolder.replace("[code]", resPic.getPicCode());
		resSmallPicFolder = Utils.replaceDate(resSmallPicFolder);
		/*生成缩略图*/
		String resPicPath=resPic.getPicPath();
		if(StringUtils.isBlank(resPicPath)){
			logger.info("------id为"+resPic.getId()+"的图片没有存路径(picPath)");
		}
		String name=resPicPath.substring(resPicPath.lastIndexOf("/")+1);

		String urlWeb="";
		String urlIpad="";
		
		if(fileKeyInfo==1){
			urlWeb=resSmallPicFolder+"web_"+name;
			urlIpad=resSmallPicFolder+"ipad_"+name;
		}else if(fileKeyInfo==2){
			urlWeb=resSmallPicFolder+"small/web_"+name;
			urlIpad=resSmallPicFolder+"small/ipad_"+name;
		}else if(fileKeyInfo==3){
			urlWeb=resSmallPicFolder+"small/web_"+name;
			urlIpad=resSmallPicFolder+"small/ipad_"+name;
		}else if(fileKeyInfo==4){
			urlWeb=resSmallPicFolder+"small/web_"+name;
			urlIpad=resSmallPicFolder+"small/ipad_"+name;
		}else if(fileKeyInfo==5){
			urlWeb=resSmallPicFolder+"small/web_"+name;
			urlIpad=resSmallPicFolder+"small/ipad_"+name;
		}else if(fileKeyInfo==6){
			urlWeb=resSmallPicFolder+"web_"+name;
			urlIpad=resSmallPicFolder+"ipad_"+name;
		}else if(fileKeyInfo==7){
			urlWeb=resSmallPicFolder+"web_"+name;
			urlIpad=resSmallPicFolder+"ipad_"+name;
		}else if(fileKeyInfo==8){
			urlWeb=resSmallPicFolder+"small/web_"+name;
			urlIpad=resSmallPicFolder+"small/ipad_"+name;
		}else if(fileKeyInfo==9){
			urlWeb=resSmallPicFolder+"small/web_"+name;
			urlIpad=resSmallPicFolder+"small/ipad_"+name;
		}else if(fileKeyInfo==11){
			urlWeb=resSmallPicFolder+"small/web_"+name;
			urlIpad=resSmallPicFolder+"small/ipad_"+name;
		}else if(fileKeyInfo==10){
			urlWeb=resSmallPicFolder+"small/web_"+name;
			urlIpad=resSmallPicFolder+"small/ipad_"+name;
		}
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
		ResPic resPicIpad=new ResPic();
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
		resPicIpad.setPicSuffix(resPic.getPicSuffix());
		
		if(fileKeyInfo==1){
			//resPicIpad.setPicPath(app.getString("product.baseProduct.pic.upload.path")+"ipad_"+name);
			resPicIpad.setPicPath(Utils.getPropertyName("config/res", "product.baseProduct.pic.upload.path", "/product/baseProduct/pic/")+"ipad_"+name);
			resPicIpad.setFileKey("product.baseProduct.pic");
			resPicIpad.setPicType("产品ipad端缩略图");
		}else if(fileKeyInfo==2){
			//resPicIpad.setPicPath(app.getString("home.spaceCommon.pic.upload.path")+"small/ipad_"+name);
			resPicIpad.setPicPath(Utils.getPropertyName("config/res", "home.spaceCommon.pic.upload.path", "/home/spaceCommon/pic/")+"small/ipad_"+name);
			resPicIpad.setFileKey("home.spaceCommon.pic.small");
			resPicIpad.setPicType("空间户型图ipad端缩略图");
		}else if(fileKeyInfo==3){
			//resPicIpad.setPicPath(app.getString("home.spaceCommon.view3dPic.upload.path")+"small/ipad_"+name);
			resPicIpad.setPicPath(Utils.getPropertyName("config/res", "home.spaceCommon.view3dPic.upload.path", "/home/spaceCommon/view3dPic/")+"small/ipad_"+name);
			resPicIpad.setFileKey("home.spaceCommon.view3dPic.small");
			resPicIpad.setPicType("3D空间俯视图ipad端缩略图");
		}else if(fileKeyInfo==4){
			//resPicIpad.setPicPath(app.getString("onekeydesign.designTemplet.pic.upload.path")+"small/ipad_"+name);
			resPicIpad.setPicPath(Utils.getPropertyName("config/res", "design.designTemplet.pic.upload.path", "/onekeydesign/designTemplet/[code]/pic/")+"small/ipad_"+name);
			resPicIpad.setFileKey("onekeydesign.designTemplet.pic.small");
			resPicIpad.setPicType("样板房-空间布局图ipad端缩略图");
		}else if(fileKeyInfo==5){
			//resPicIpad.setPicPath(app.getString("onekeydesign.designTemplet.piclist.upload.path")+"small/ipad_"+name);
			resPicIpad.setPicPath(Utils.getPropertyName("config/res", "design.designTemplet.piclist.upload.path", "/onekeydesign/designTemplet/[code]/piclist/")+"small/ipad_"+name);
			resPicIpad.setFileKey("onekeydesign.designTemplet.piclist.small");
			resPicIpad.setPicType("样板房-效果图ipad端缩略图");
		}/*
		FIXME productDecoration无用，已删
		else if(fileKeyInfo==6){
			//resPicIpad.setPicPath(app.getString("product.productDecoration.pic.upload.path")+"ipad_"+name);
			resPicIpad.setPicPath(Utils.getPropertyName("config/res", "product.productDecoration.pic.upload.path", "/product/productDecoration/pic/")+"ipad_"+name);
			resPicIpad.setFileKey("product.productDecoration.pic");
			resPicIpad.setPicType("饰品ipad端缩略图");
		}*/else if(fileKeyInfo==7){
			//resPicIpad.setPicPath(app.getString("home.designRecommendation.pic.upload.path")+"ipad_"+name);
			resPicIpad.setPicPath(Utils.getPropertyName("config/res", "home.designRecommendation.pic.upload.path", "/home/designRecommendation/pic/")+"ipad_"+name);
			resPicIpad.setFileKey("home.designRecommendation.pic");
			resPicIpad.setPicType("效果图推荐ipad端缩略图");
		}else if(fileKeyInfo==8){
			//resPicIpad.setPicPath(app.getString("system.sysUser.pic.upload.path")+"small/ipad_"+name);
			resPicIpad.setPicPath(Utils.getPropertyName("config/res", "system.sysUser.pic.upload.path", "/system/sysUser/pic/")+"small/ipad_"+name);
			resPicIpad.setFileKey("system.sysUser.pic.small");
			resPicIpad.setPicType("用户头像ipad端缩略图");
		}else if(fileKeyInfo==9){
			//resPicIpad.setPicPath(app.getString("system.sysUser.designerWorks.piclist.path")+"small/ipad_"+name);
			resPicIpad.setPicPath(Utils.getPropertyName("config/res", "system.sysUser.designerWorks.piclist.path", "/system/sysUser/designerWorks/piclist/") + "small/ipad_"+name);
			resPicIpad.setFileKey("system.sysUser.designerWorks.piclist.small");
			resPicIpad.setPicType("设计师作品ipad端缩略图");
		}else if(fileKeyInfo==11){
			//resPicIpad.setPicPath(app.getString("onekeydesign.designTemplet.effectPlan.upload.path")+"small/ipad_"+name);
			resPicIpad.setPicPath(Utils.getPropertyName("config/res", "design.designTemplet.effectPlan.upload.path", "/c_basedesign/onekeydesign/designTemplet/effectPlan/")+"small/ipad_"+name);
			resPicIpad.setFileKey("onekeydesign.designTemplet.effectPlan.small");
			resPicIpad.setPicType("样板房平面效果图ipad端缩略图");
		}else if(fileKeyInfo==10){
			resPicIpad.setPicPath(app.getString("home.spaceCommon.viewPlan.upload.path")+"small/ipad_"+name);
			resPicIpad.setFileKey("home.spaceCommon.viewPlan.small");
			resPicIpad.setPicType("空间俯视平面图ipad端缩略图");
		}
		/*由于特殊的路径要加code(样板房,所以加入以下逻辑处理)*/
		//resPicIpad.setPicPath(resPicIpad.getPicPath().replace("[code]", resPic.getPicCode()));
		resPicIpad.setPicPath(Utils.replaceDate(resPicIpad.getPicPath()));
		
		//resPicIpad.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS)+ "_"+ Utils.generateRandomDigitString(6));
		/*修改缩略图code和原图一致*/
		resPicIpad.setSysCode(resPic.getSysCode());
		resPicIpad.setGmtCreate(new Date());
		resPicIpad.setCreator(loginUser.getLoginName());
		resPicIpad.setIsDeleted(0);
		resPicIpad.setGmtModified(new Date());
		resPicIpad.setModifier(loginUser.getLoginName());
		resPicIpad.setPicCode(resPicIpad.getSysCode());
		resPicIpad.setPicFormat(resPic.getPicSuffix().substring(1,resPic.getPicSuffix().length()));
		resPicIpad.setBusinessId(resPic.getBusinessId());
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
		ResPic resPicWeb=new ResPic();
		resPicWeb.setPicName("web_"+name.substring(0,name.lastIndexOf(".")));
		resPicWeb.setPicFileName("web_"+name.substring(0,name.lastIndexOf(".")));
		resPicWeb.setPicSize((int)(fileWeb.length()));
		resPicWeb.setPicWeight(""+imageWeb.getWidth());
		resPicWeb.setPicHigh(""+imageWeb.getHeight());
		resPicWeb.setPicSuffix(resPic.getPicSuffix());
		if(fileKeyInfo==1){
			//resPicWeb.setPicPath(app.getString("product.baseProduct.pic.upload.path")+"web_"+name);
			resPicWeb.setPicPath(Utils.getPropertyName("config/res", "product.baseProduct.pic.upload.path", "/product/baseProduct/pic/")+"web_"+name);
			resPicWeb.setFileKey("product.baseProduct.pic");
			resPicWeb.setPicType("产品web端缩略图");
		}else if(fileKeyInfo==2){
			//resPicWeb.setPicPath(app.getString("home.spaceCommon.pic.upload.path")+"small/web_"+name);
			resPicWeb.setPicPath(Utils.getPropertyName("config/res", "home.spaceCommon.pic.upload.path", "/home/spaceCommon/pic/")+"small/web_"+name);
			resPicWeb.setFileKey("home.spaceCommon.pic.small");
			resPicWeb.setPicType("空间户型图web端缩略图");
		}else if(fileKeyInfo==3){
			//resPicWeb.setPicPath(app.getString("home.spaceCommon.view3dPic.upload.path")+"small/web_"+name);
			resPicWeb.setPicPath(Utils.getPropertyName("config/res", "home.spaceCommon.view3dPic.upload.path", "/home/spaceCommon/view3dPic/")+"small/web_"+name);
			resPicWeb.setFileKey("home.spaceCommon.view3dPic.small");
			resPicWeb.setPicType("3D空间俯视图web端缩略图");
		}else if(fileKeyInfo==4){
			//resPicWeb.setPicPath(app.getString("onekeydesign.designTemplet.pic.upload.path")+"small/web_"+name);
			resPicWeb.setPicPath(Utils.getPropertyName("config/res", "design.designTemplet.pic.upload.path", "/onekeydesign/designTemplet/[code]/pic/")+"small/web_"+name);
			resPicWeb.setFileKey("onekeydesign.designTemplet.pic.small");
			resPicWeb.setPicType("样板房-空间布局图web端缩略图");
		}else if(fileKeyInfo==5){
			//resPicWeb.setPicPath(app.getString("onekeydesign.designTemplet.piclist.upload.path")+"small/web_"+name);
			resPicWeb.setPicPath(Utils.getPropertyName("config/res", "design.designTemplet.piclist.upload.path", "/onekeydesign/designTemplet/[code]/piclist/")+"small/web_"+name);
			resPicWeb.setFileKey("onekeydesign.designTemplet.piclist.small");
			resPicWeb.setPicType("样板房-效果图web端缩略图");
		}
		/*
		 * FIXME productDecoration无用，已删
		 * else if(fileKeyInfo==6){
			//resPicWeb.setPicPath(app.getString("product.productDecoration.pic.upload.path")+"web_"+name);
			resPicWeb.setPicPath(Utils.getPropertyName("config/res", "product.productDecoration.pic.upload.path", "/product/productDecoration/pic/")+"web_"+name);
			resPicWeb.setFileKey("product.productDecoration.pic");
			resPicWeb.setPicType("饰品web端缩略图");
		}*/
		else if(fileKeyInfo==7){
			//resPicWeb.setPicPath(app.getString("home.designRecommendation.pic.upload.path")+"web_"+name);
			resPicWeb.setPicPath(Utils.getPropertyName("config/res", "home.designRecommendation.pic.upload.path", "/home/designRecommendation/pic/")+"web_"+name);
			resPicWeb.setFileKey("home.designRecommendation.pic");
			resPicWeb.setPicType("效果图推荐web端缩略图");
		}else if(fileKeyInfo==8){
			//resPicWeb.setPicPath(app.getString("system.sysUser.pic.upload.path")+"small/web_"+name);
			resPicWeb.setPicPath(Utils.getPropertyName("config/res", "system.sysUser.pic.upload.path", "/system/sysUser/pic/")+"small/web_"+name);
			resPicWeb.setFileKey("system.sysUser.pic.small");
			resPicWeb.setPicType("用户头像web端缩略图");
		}else if(fileKeyInfo==9){
			//resPicWeb.setPicPath(app.getString("system.sysUser.designerWorks.piclist.path")+"small/web_"+name);
			resPicWeb.setPicPath(Utils.getPropertyName("config/res", "system.sysUser.designerWorks.piclist.path", "/system/sysUser/designerWorks/piclist/") + "small/web_"+name);
			resPicWeb.setFileKey("system.sysUser.designerWorks.piclist.small");
			resPicWeb.setPicType("设计师作品web端缩略图");
		}else if(fileKeyInfo==11){
			resPicWeb.setPicPath(app.getString("design.designTemplet.effectPlan.upload.path")+"small/web_"+name);
			resPicWeb.setFileKey("onekeydesign.designTemplet.effectPlan.small");
			resPicWeb.setPicType("样板房平面效果图web端缩略图");
		}else if(fileKeyInfo==10){
			resPicWeb.setPicPath(app.getString("home.spaceCommon.viewPlan.upload.path")+"small/web_"+name);
			resPicWeb.setFileKey("home.spaceCommon.viewPlan.small");
			resPicWeb.setPicType("空间平面俯视图web端缩略图");
		}
		/*由于特殊的路径要加code(样板房,所以加入以下逻辑处理)*/
		resPicWeb.setPicPath(resPicWeb.getPicPath().replace("[code]", resPic.getPicCode()));
		
		//resPicWeb.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS)+ "_"+ Utils.generateRandomDigitString(6));
		/*修改为缩略图和原图sysCode一致*/
		resPicWeb.setSysCode(resPic.getSysCode());
		resPicWeb.setGmtCreate(new Date());
		resPicWeb.setCreator(loginUser.getLoginName());
		resPicWeb.setIsDeleted(0);
		resPicWeb.setGmtModified(new Date());
		resPicWeb.setModifier(loginUser.getLoginName());
		resPicWeb.setPicCode(resPic.getSysCode());
		resPicWeb.setPicFormat(resPic.getPicSuffix().substring(1,resPic.getPicSuffix().length()));
		resPicWeb.setBusinessId(resPic.getBusinessId());
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
		resPic.setSmallPicInfo(smallPicInfo);
		update(resPic);
		/*返回生成的两张缩略图信息*/
		Map<String,ResPic> map=new HashMap<String,ResPic>();
		map.put("web", resPicWeb);
		map.put("ipad", resPicIpad);
		return map;
	}
	
	/**回填该resPic图片对应的缩略图的businessId(缩略图businessId和原图businessId相同),同时填写businessIds*/
	public void updateSmallPicBusinessId(ResPic resPic){
		List<Integer> ids=new ArrayList<Integer>();
		if(StringUtils.isNotBlank(resPic.getSmallPicInfo())){
			String[] strs=resPic.getSmallPicInfo().split(";");
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
				ResPic resPic2=get(id);
				if(resPic2!=null){
					resPic2.setBusinessId(resPic.getBusinessId());
					update(resPic2);
				}
			}
		}	
	}

	@Override
	public int picPathCount(String picPath) {
		return resPicMapper.picPathCount(picPath);
	}

	/**
	 * 更新图片code
	 * @param picId 图片id
	 * @param code 变更后的code
	 */
	public void updateCode(Integer picId, String code) {
		ResPic resPic=get(picId);
		if(resPic!=null){
			resPic.setPicCode(code);
			resPic.setSysCode(code);
			update(resPic);
		}
	}

	@Override
	public boolean updatePath(Integer picId,String code) {
		ResPic resPic=get(picId);
		if(resPic==null){
			return false;
		}
		String fileKey=resPic.getFileKey();
		/*应对缩略图例如design.designTemplet.piclist.small的情况(app中未保存)*/
		String correctFilePath="";
		if(fileKey.endsWith("small")){
			correctFilePath=app.getString(fileKey.substring(0,fileKey.lastIndexOf("."))+".upload.path")+"small/";
		}else{
			correctFilePath=app.getString(fileKey+".upload.path");
		}
		
		correctFilePath=correctFilePath.replace("[code]", code);
		logger.info("------correctFilePath:"+correctFilePath);
		if(resPic.getPicPath().indexOf(correctFilePath)==0){
			/*不用更新*/
			return false;
		}else{
			/*需要更新*/
			/*复制本地文件*/
			String fileName=resPic.getPicPath().substring(resPic.getPicPath().lastIndexOf("/")+1,resPic.getPicPath().length());
			String localUrl1=Tools.getRootPath(resPic.getPicPath(),"")+resPic.getPicPath();//原文件路径
			String localUrl2=Tools.getRootPath(correctFilePath,"")+correctFilePath+fileName;//更新后文件路径
			try {
				FileUploadUtils.copyFile2(localUrl1, localUrl2);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			/*复制ftp文件*/
			int type=Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD);
			if(type==2||type==3){
				FtpUploadUtils.copyFileFromFtp(resPic.getPicPath(), correctFilePath);
			}
			/*缩略图更新路径*/
			if(StringUtils.isNotBlank(resPic.getSmallPicInfo())){
				Map<String,String> map=readFileDesc(resPic.getSmallPicInfo());
				String webPicId=map.get("web");
				String ipadPicId=map.get("ipad");
				updatePath(Integer.parseInt(webPicId), code);
				updatePath(Integer.parseInt(ipadPicId), code);
			}
			resPic.setPicPath(correctFilePath+fileName);
			update(resPic);
			/*缩略图更新路径END*/
			return true;
		}
	}
	
	/**读取固定格式字符串*/
	private Map<String,String> readFileDesc(String fileDesc){
		Map<String, String> map = new HashMap<String, String>();
		String[] strs = fileDesc.split(";");
		for (String str : strs) {
			if (str.split(":").length == 2) {
				map.put(str.split(":")[0].trim(), str.split(":")[1].trim());
			}
		}
		return map;
	}

	@Override
	public Integer copyPic(Integer pidId, HttpServletRequest request) {
		ResPic resPic = get(pidId);
		if(resPic==null){
			return 0;
		}
		resPic.setPicCode(null);
		resPic.setSysCode(null);
		sysSave(resPic, request);
		resPic.setId(null);
		resPic.setFileKeys(null);
		resPic.setBusinessIds(null);
		resPic.setBusinessId(null);
		add(resPic);
		return resPic.getId();
	}
	
	/**
	 * 自动存储系统字段
	 */
	private void sysSave(ResPic model,HttpServletRequest request){
		if(model != null){
				 LoginUser loginUser = new LoginUser();
				 if(com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request)==null){
					loginUser.setLoginName("nologin");
				 }else{
				    loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
				 }
				 
				if(model.getId() == null){
					model.setGmtCreate(new Date());
					model.setCreator(loginUser.getLoginName());
					model.setIsDeleted(0);
				    if(model.getSysCode()==null || "".equals(model.getSysCode())){
					   model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
				   }
				}
				
				model.setGmtModified(new Date());
				model.setModifier(loginUser.getLoginName());
		}
	}

	/**
	 * 获取文件数据为ResEntity对象
	 * @param id
	 * @return
	 */
	@Override
	public ResEntity selectResEntity(Integer id){
		return resPicMapper.selectResEntity(id);
	}

	/**
	 * 保存ResEntity对象
	 * @param resEntity
	 * @return
	 */
	@Override
	public int insertEntity(ResEntity resEntity) {
		resPicMapper.insertEntity(resEntity);
		return resEntity.getId();
	}

	@Override
	public int selectCountGuide(ResPicSearch resPicSearch) {
		return resPicMapper.selectCountGuide(resPicSearch);
	}

	@Override
	public List<ResPic> selectPaginatedListGuide(ResPicSearch resPicSearch) {
		return resPicMapper.selectPaginatedListGuide(resPicSearch);
	}

	@Override
	public Integer savePlanRenderPicOfPhot(Integer planId, List<Map> list, String level, Integer renderingType,Integer sourcePlanId, Integer templateId) {

//		Integer smallPicId=0;
		int original = 0;
		int small = 0;
		Date date = new Date();
		
		if(planId != null){
			DesignPlan designPlan = designPlanService.get(Integer.valueOf(planId));
			ResRenderPic smallRenderPic = null;
			ResRenderPic renderPic = null;
			for( Map smallRenderPicMap : list ){
				//保存渲染图原图
				Map renderPicMap = (Map)smallRenderPicMap.get("original");
				renderPic = assembleResPic(renderPicMap);
				renderPic.setIsDeleted(0);
				renderPic.setCreator(designPlan.getCreator());
				renderPic.setModifier(designPlan.getCreator());
				renderPic.setBusinessId(planId);
//				renderPic.setPicLevel(level);
				renderPic.setRenderingType(renderingType);
				renderPic.setPicType("照片级原图");
				renderPic.setFileKey(ResProperties.DESIGNPLAN_RENDER_PIC_FILEKEY);
				renderPic.setTaskCreateTime(date);
				renderPic.setSourcePlanId(sourcePlanId);
				renderPic.setTemplateId(templateId);
				logger.info("addrenderPic="+planId);
				original = resRenderPicService.add(renderPic);
				
				logger.info("endrenderPic="+original);
				smallRenderPic = assembleResPic(smallRenderPicMap);
				smallRenderPic.setPid(original);
				smallRenderPic.setCreator(designPlan.getCreator());
				smallRenderPic.setIsDeleted(0);
				smallRenderPic.setModifier(designPlan.getCreator());
				smallRenderPic.setBusinessId(planId);
//				smallRenderPic.setPicLevel(level);
				smallRenderPic.setFileKey(ResProperties.DESIGNPLAN_RENDER_PIC_SMALL_FILEKEY);
				smallRenderPic.setPicType("照片级缩略图");
				smallRenderPic.setRenderingType(renderingType);
				smallRenderPic.setTaskCreateTime(date);
				smallRenderPic.setSourcePlanId(sourcePlanId);
				smallRenderPic.setTemplateId(templateId);
				//保存缩略图
				logger.info("add_small="+original);
				small = resRenderPicService.add(smallRenderPic);
				logger.info("end_small="+small);
			}
		}else{
			logger.error("planId is null!");
		}
		if(small > 0 && original > 0){
			return original;
		}else{
			return original;
		}
	}

	@Override
	public List<ResPic> getBatchGet(List<Integer> list) {
		return resPicMapper.getBatchGet(list);
	}
	
	public List<ResPic> getPicList(List<String> list){
		return resPicMapper.getPicList(list);
	}

}
