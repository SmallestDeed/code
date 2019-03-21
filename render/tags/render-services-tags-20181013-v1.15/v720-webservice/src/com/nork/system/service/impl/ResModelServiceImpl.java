package com.nork.system.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.model.LoginUser;
import com.nork.common.util.DeCompressUtil;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.FtpUploadUtils;
import com.nork.common.util.Tools;
import com.nork.common.util.Utils;
import com.nork.product.model.BaseProduct;
import com.nork.product.service.BaseProductService;
import com.nork.sync.model.ResEntity;
import com.nork.system.dao.ResModelMapper;
import com.nork.system.model.ResModel;
import com.nork.system.model.search.ResModelSearch;
import com.nork.system.service.ResModelService;

/**   
 * @Title: ResModelServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统-模型资源库ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2015-05-19 16:05:22
 * @version V1.0   
 */
@Service("resModelService")
@Transactional
public class ResModelServiceImpl implements ResModelService {


	private static Logger logger = Logger.getLogger(ResModelServiceImpl.class);
	
	private final static ResourceBundle app = ResourceBundle.getBundle("app");

	@Autowired
	private ResModelMapper resModelMapper;
	@Autowired
	private BaseProductService baseProductService;
	
	/**
	 * 新增数据
	 *
	 * @param resModel
	 * @return  int 
	 */
	@Override
	public int add(ResModel resModel) {
		resModelMapper.insertSelective(resModel);
		return resModel.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param resModel
	 * @return  int 
	 */
	@Override
	public int update(ResModel resModel) {
		return resModelMapper
				.updateByPrimaryKeySelective(resModel);
	}
	/**
	 *    更新数据
	 *
	 * @param resModel
	 * @return  int 
	 */
	@Override
	public int update(ResModel resModel,Integer businessId,String modelKey) {
		//获取多个 resPic filekeys ,busniessIds
		String fileKey = resModel.getFileKey();
		Integer bId = resModel.getBusinessId();
		//当前资源 业务Id不为空，且fileKeys为空，就必须给fileKeys赋值 
		if(bId != null && bId > 0 && !bId.equals(businessId) && StringUtils.isBlank(resModel.getFileKeys())){
			resModel.setFileKeys(fileKey);
		}
		String fileKeys = "" ;
		//如果fileKeys不为空，累加fileKeys
		if(StringUtils.isNotBlank(resModel.getFileKeys())){
			fileKeys = resModel.getFileKeys()+","+modelKey;
		}else{
			fileKeys = modelKey+"";
		}
		resModel.setFileKeys(fileKeys);
		
		String businessIds = "";
		//业务Ids为空或不为空，则赋值或累加
		if(bId != null && bId > 0 && !bId.equals(businessId) && StringUtils.isBlank(resModel.getBusinessIds())){
			businessIds = bId + "," + businessId;
			resModel.setBusinessId(0);
		}else if(StringUtils.isNotBlank(resModel.getBusinessIds())){
			businessIds = resModel.getBusinessIds()+","+businessId;
			resModel.setBusinessId(0);
		}else{
			businessIds = businessId+"";
			resModel.setBusinessId(businessId);
		}
		resModel.setBusinessIds(businessIds);
		return resModelMapper.updateByPrimaryKeySelective(resModel);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return resModelMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  ResModel 
	 */
	@Override
	public ResModel get(Integer id) {
		return resModelMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  resModel
	 * @return   List<ResModel>
	 */
	@Override
	public List<ResModel> getList(ResModel resModel) {
	    return resModelMapper.selectList(resModel);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  resModel
	 * @return   int
	 */
	@Override
	public int getCount(ResModelSearch resModelSearch){
		return  resModelMapper.selectCount(resModelSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  resModel
	 * @return   List<ResModel>
	 */
	@Override
	public List<ResModel> getPaginatedList(
			ResModelSearch resModelSearch) {
		return resModelMapper.selectPaginatedList(resModelSearch);
	}

	/**
	 * 其他
	 * 
	 */


	/**
	 * 验证媒介类型
	 * @param mediaType
	 * @param fileId
	 * @return
	 */
	public boolean checkMediaType(String mediaType,Integer fileId){
		boolean flag = false;
		if(StringUtils.isBlank(mediaType) || fileId == null ){
			return flag;
		}
		ResModel resModel = resModelMapper.selectByPrimaryKey(fileId);
		if( ("."+mediaType+".").indexOf("."+resModel.getFileKey()+".") > -1 ){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 回填模型资源的businessId
	 * @param businessId 业务表ID
	 * @param resModelId 模型资源表ID
	 */
	public void backFillResModel(Integer businessId,Integer resModelId,String modelKey){
		ResModel resModel = this.get(resModelId);
		if( resModel != null ) {
			this.update(resModel,businessId,modelKey);
		}
	}
	
	/**
	 * 删除当前模型资源信息
	 * @param businessId 业务表ID
	 * @param resModelId 模型资源表ID
	 */
	public void clearBackfillResModel(Integer businessId,Integer resModelId){
		StringBuffer bidSb = new StringBuffer();
		StringBuffer keySb = new StringBuffer();
		ResModel resModel = this.get(resModelId);
		if( resModel != null ) {
			String bids = resModel.getBusinessIds();
			String keys = resModel.getFileKeys();
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
				resModel.setBusinessIds(bid);
				resModel.setFileKeys(key);
				//判断bid，给businessId赋值
				if(StringUtils.isNotBlank(bid)){
					if(bid.contains(","))
						resModel.setBusinessId(0);
					else
						resModel.setBusinessId(Utils.getIntValue(bid));
				}else{
					resModel.setBusinessId(0);
				}
				this.update(resModel);
			}
		}
	}
	
	/**
	 * 删除当前模型资源信息
	 * @param businessId 业务表ID
	 * @param resModelId 模型资源表ID
	 */
	public void clearBackfillResModel(Integer businessId,String fileKey,Integer resModelId){
		StringBuffer bidSb = new StringBuffer();
		StringBuffer keySb = new StringBuffer();
		ResModel resModel = this.get(resModelId);
		if( resModel != null ) {
			String bids = resModel.getBusinessIds();
			String keys = resModel.getFileKeys();
			if( bids != null ){
				String bidArr[] = bids.split(",");
				String keyArr[] = keys.split(",");
				for( int i=0;i<bidArr.length;i++ ){
					if(!bidArr[i].equals(businessId+"") && !keyArr[i].equals(fileKey)){
						bidSb.append(bidArr[i]).append(",");
						keySb.append(keyArr[i]).append(",");
						resModel.setFileKey(keyArr[i]);
					}else if(bidArr[i].equals(businessId+"") && !keyArr[i].equals(fileKey)){//同模型资源id不同媒介
						bidSb.append(bidArr[i]).append(",");
						keySb.append(keyArr[i]).append(",");
						resModel.setFileKey(keyArr[i]);
					}
				}
				String bid = bidSb.toString();
				String key = keySb.toString();
				if(bid.length()>0){
					bid = bid.substring(0, bid.length()-1);
					key = key.substring(0, key.length()-1);
				}
				resModel.setBusinessIds(bid);
				resModel.setFileKeys(key);
				//判断bid，给businessId赋值
				if(StringUtils.isNotBlank(bid)){
					if(bid.contains(","))
						resModel.setBusinessId(0);
					else
						resModel.setBusinessId(Utils.getIntValue(bid));
				}else{
					resModel.setBusinessId(0);
				}
				this.update(resModel);
			}
		}
	}

	/**
	 * 同类型添加模型数据(更新code和businessId)
	 * @param modelId 待复制的model的id
	 * @param objectId (产品)id
	 * @param objectCode (产品)code
	 * @return 新生成的model的id(生成失败返回0)
	 */
	public Integer sameTypeAdd(Integer modelId,Integer objectId, String objectCode,HttpServletRequest request) {
		ResModel resModel=get(modelId);
		if(resModel==null){
			return 0;
		}
		resModel.setModelCode(objectCode);
		resModel.setSysCode(null);
		sysSave(resModel, request);
		resModel.setId(null);
		/*由于复制ipad模型会共用一条数据,所以要对ios模型的数据的file_key做处理*/
		resModel.setFileKeys(null);
		resModel.setBusinessIds(null);
		resModel.setBusinessId(objectId);
		add(resModel);
		return resModel.getId();
	}

	/**
	 * 更新model数据的code
	 * @param id modelId
	 * @param code 变更后的code
	 */
	public void updateCode(Integer id,String code){
		ResModel resModel=get(id);
		if(resModel!=null){
			resModel.setModelCode(code);
			resModel.setSysCode(code);
			update(resModel);
		}
	}
	
	/**
	 * 自动存储系统字段
	 */
	private void sysSave(ResModel model,HttpServletRequest request){
		if (model != null) {
			/* 获得操作者信息 */
			LoginUser loginUser = new LoginUser();
			if (com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null
					|| com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request) == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = com.nork.common.constant.util.SystemCommonUtil.getCurrentLoginUserInfo(request);
			}

			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
			}
			model.setSysCode(model.getModelCode());
			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}
	
	@Override
	public int modelPathCount(String modelPath) {
		return resModelMapper.modelPathCount(modelPath);
	}

	@Override
	public Integer sameTypeAdd(Integer modelId, Integer objectId,
			String objectCode, HttpServletRequest request, String fileKey) {
		ResModel resModel=get(modelId);
		if(resModel==null){
			return 0;
		}
		resModel.setModelCode(objectCode);
		resModel.setSysCode(null);
		sysSave(resModel, request);
		resModel.setId(null);
		/*由于复制ipad模型会共用一条数据,所以要对ios模型的数据的file_key做处理*/
		resModel.setFileKeys("");
		resModel.setFileKey(fileKey);
		resModel.setBusinessIds(null);
		resModel.setBusinessId(objectId);
		add(resModel);
		return resModel.getId();
	}

	@Override
	public boolean updatePath(Integer modelId, String code) {
		ResModel resModel=get(modelId);
		if(resModel==null){
			return false;
		}
		String fileKey=resModel.getFileKey();
		String correctFilePath=app.getString(fileKey+".upload.path");
		correctFilePath=correctFilePath.replace("[code]", code);
		logger.info("------correctFilePath:"+correctFilePath);
		if(resModel.getModelPath().indexOf(correctFilePath)==0){
			/*不用更新*/
			return false;
		}else{
			/*需要更新*/
			/*复制本地文件*/
			String fileName=resModel.getModelPath().substring(resModel.getModelPath().lastIndexOf("/")+1,resModel.getModelPath().length());
			String localUrl1=Tools.getRootPath(resModel.getModelPath(), "")+resModel.getModelPath();//原文件路径
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
				FtpUploadUtils.copyFileFromFtp(resModel.getModelPath(), correctFilePath);
			}
			resModel.setModelPath(correctFilePath+fileName);
			update(resModel);
			return true;
		}
	}

	/**
	 * 属性新增复制模型数据
	 * @param modelId 待复制的model的id
	 * @return 新生成的model的id(生成失败返回0)
	 */
	@Override
	public Integer copyModel(Integer modelId, HttpServletRequest request) {
		ResModel resModel=get(modelId);
		if(resModel==null){
			return 0;
		}
		resModel.setModelCode(null);
		resModel.setSysCode(null);
		sysSave(resModel, request);
		resModel.setId(null);
		resModel.setFileKeys(null);
		resModel.setBusinessIds(null);
		resModel.setBusinessId(null);
		add(resModel);
		return resModel.getId();
	}
	
	@Override
	public ResEntity selectResEntity(Integer id){
		return resModelMapper.selectResEntity(id);
	}
	
	/**
	 * 保存ResEntity对象
	 * @param resEntity
	 * @return
	 */
	@Override
	public int insertEntity(ResEntity resEntity) {
		resModelMapper.insertEntity(resEntity);
		return resEntity.getId();
	}

	/**
	 * 通过产品id得到该pc模型的code
	 * @author huangsongbo
	 * @param productId
	 * @return
	 */
	public String getModelCodeByProductId(Integer productId) {
		BaseProduct baseProduct = baseProductService.get(productId);
		if(baseProduct == null)
			return "";
		Integer modelId = baseProduct.getWindowsU3dModelId();
		if(modelId == null || modelId <= 0)
			return "";
		ResModel resModel = this.get(modelId);
		if(resModel == null)
			return "";
		return resModel.getModelCode();
	}

	public boolean decompressModel(Integer modelId, String productCode) {
		if(modelId == null){
			logger.error("------decompressModel:modelId为空");
			return false;
		}
		ResModel resModel = this.get(modelId);
		if(resModel == null){
			logger.error("------decompressModel:resModel为空,modelId:" + modelId);
			return false;
		}
		if(StringUtils.isBlank(productCode)){
			logger.error("------decompressModel:productCode为空");
			return false;
		}
		// 得到3dmax模型文件路径
		String uploadRoot =Tools.getRootPath(resModel.getModelPath(),  "D:\\nork\\resources-test");
		String resModelPath=uploadRoot+resModel.getModelPath();
		/*得到3dmax模型文件路径->end*/
		String decompressPath = Utils.getPropertyName("render", "render.renderResourcesPath", "/home/nork/MaxRender/resources");//F:\chengdu\MaxRender\resources
		decompressPath+="/baseProduct/"+productCode;
		decompressPath = Utils.replaceDate(decompressPath);
		/*不同系统,对应路径的不同处理方法*/
		String systemName = Utils.getValue("app.system.format", "linux");
		resModelPath=Utils.dealWithPath(resModelPath, systemName);
		decompressPath=Utils.dealWithPath(decompressPath, systemName);
		/*不同系统,对应路径的不同处理方法->end*/
		/*解压3dmax文件*/
		try {
			File file=new File(decompressPath);
			if(file.isDirectory()){
				Utils.deleteFile(file);
			}
			DeCompressUtil.deCompress(resModelPath, decompressPath, "");
			// 更新resModel的
			resModel.setIsDecompress(new Integer(1));
			update(resModel);
		} catch (Exception e) {
			logger.error(e);
			logger.error("------decompressModel:解压失败:resModelPath:" + resModelPath + "decompressPath:" + decompressPath);
			return false;
		}
		/*解压3dmax文件->end*/
		return true;
	}
	
}
