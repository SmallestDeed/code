package com.nork.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nork.common.model.LoginUser;
import com.nork.product.model.BaseBrand;
import com.nork.product.service.BaseBrandService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.product.model.SplitTextureDTO;
import com.nork.system.dao.ResTextureMapper;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResTexture;
import com.nork.system.model.search.ResTextureSearch;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResTextureService;

/**   
 * @Title: ResTextureServiceImpl.java 
 * @Package com.nork.system.service.impl
 * @Description:系统模块-材质库ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2016-06-30 14:10:42
 * @version V1.0   
 */ 
@Service("resTextureService")
public class ResTextureServiceImpl implements ResTextureService {
	
	/*private static Logger logger = Logger.getLogger(ResTextureServiceImpl.class);*/
	private static Logger logger = LoggerFactory.getLogger(ResTextureServiceImpl.class);
	
	private static final String logPrefix = "customLog:";
	
	@Autowired
	private ResTextureMapper resTextureMapper;

	@Autowired
	BaseBrandService baseBrandService;
	
	@Autowired
	private ResPicService resPicService;

	@Autowired
	private ResModelService resModelService;
	
	/**
	 * 新增数据
	 *
	 * @param resTexture
	 * @return  int 
	 */
	@Override
	public int add(ResTexture resTexture) {
		resTextureMapper.insertSelective(resTexture);
		return resTexture.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param resTexture
	 * @return  int 
	 */
	@Override
	public int update(ResTexture resTexture) {
		return resTextureMapper
				.updateByPrimaryKeySelective(resTexture);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return resTextureMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  ResTexture 
	 */
	@Override
	public ResTexture get(Integer id) {
		return resTextureMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  resTexture
	 * @return   List<ResTexture>
	 */
	@Override
	public List<ResTexture> getList(ResTexture resTexture) {
	    return resTextureMapper.selectList(resTexture);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  resTexture
	 * @return   int
	 */
	@Override
	public int getCount(ResTextureSearch resTextureSearch){
		return  resTextureMapper.selectCount(resTextureSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  resTexture
	 * @return   List<ResTexture>
	 */
	@Override
	public List<ResTexture> getPaginatedList(
			ResTextureSearch resTextureSearch) {
		return resTextureMapper.selectPaginatedList(resTextureSearch);
	}

	/**
	 * 根据图片信息,补充材质数据信息
	 * @author huangsongbo
	 * @param resPic
	 */
	@Override
	public void saveParamsByResPic(ResTexture resTexture, ResPic resPic) {
		
		//System.out.println("~~~~~~"+resPic.getPicHigh()+"~~~~~~"+resPic.getPicWeight());
		
		resTexture.setFileName(resPic.getPicFileName());
		resTexture.setFileSize(resPic.getPicSize());
		
		if(StringUtils.isNotEmpty(resPic.getPicWeight())&& (StringUtils.isNotBlank(resPic.getPicWeight()))){
			resTexture.setFileWidth(Integer.valueOf(resPic.getPicWeight()));
		}
		if(StringUtils.isNoneEmpty(resPic.getPicHigh()) && (StringUtils.isNoneBlank(resPic.getPicHigh()))){
			resTexture.setFileHeight(Integer.valueOf(resPic.getPicHigh()));
		}
		resTexture.setFileSuffix(resPic.getPicSuffix());
		resTexture.setFilePath(resPic.getPicPath());
		resTexture.setName(resPic.getPicFileName());
		resTexture.setFileCode(resPic.getPicCode());
		resTextureMapper.insertSelective(resTexture);
	}

	@Override
	public SplitTextureDTO.ResTextureDTO fromResTexture(ResTexture resTexture) {//TODO:增加了材质球文件路径
		Integer picId=resTexture.getPicId();
		Integer normalPicId = resTexture.getNormalPicId();
		String path="";
		String normalPicPath="";
		BaseBrand baseBrand = null;
		Integer textureBallFileId = resTexture.getTextureBallFileId();
		String materialPath = "";
		String brandName="";
		if(textureBallFileId != null && textureBallFileId.intValue()>0){
//			ResFile resFile=resFileService.get(textureBallFileId);
			ResModel resModel = resModelService.get(textureBallFileId);
			if(resModel != null){
				materialPath = resModel.getModelPath();
			}
		}

		if(picId!=null&&picId.intValue()>0){
			ResPic resPic=resPicService.get(picId);
			if(resPic!=null){
				path=resPic.getPicPath();
			}
		}
		if(normalPicId!=null&&normalPicId.intValue()>0){
			ResPic resPic=resPicService.get(normalPicId);
			if(resPic!=null){
				normalPicPath= /*Utils.getValue("app.resources.url", "") + */resPic.getPicPath();
				normalPicPath= Utils.dealWithPath(normalPicPath, "linux");
			}
		}
		/*添加品牌id与品牌名称*/
		if(null!=resTexture&&null!=resTexture.getBrandId()){
			baseBrand = baseBrandService.get(resTexture.getBrandId());
			if(null!=baseBrand){
				brandName = baseBrand.getBrandName();
			}
		}
		return new SplitTextureDTO().new ResTextureDTO(resTexture.getId(), path, resTexture.getTextureAttrValue(), resTexture.getFileHeight(),
				resTexture.getFileWidth(), resTexture.getLaymodes(),materialPath,resTexture.getNormalParam(),normalPicPath,
				resTexture.getBrandId(),brandName,resTexture.getTextureCode());
	}

	@Override
	public SplitTextureDTO.ResTextureDTO fromResTexture(LoginUser loginUser, ResTexture resTexture,String modelType) {//TODO,增加媒介类型判断
		String path="";
		String normalPicPath="";
		String materialPath = "";
		String brandName="";
		BaseBrand baseBrand = null;
		Integer textureBallFileId = null;

		// update by huangsongbo 2018.6.9 ->start
		/*String mediaType = loginUser.getMediaType();//媒介*/
		String mediaType = null;
		if(loginUser != null) {
			mediaType = loginUser.getMediaType();
		}
		// update by huangsongbo 2018.6.9 ->end
		
		Integer picId=resTexture.getPicId();
		Integer normalPicId = resTexture.getNormalPicId();

		logger.debug("fromResTexture.mediaType="+mediaType+",modelType="+modelType);

		//根据媒介类型取值
		if(StringUtils.isEmpty(modelType)||"null".equals(modelType)||StringUtils.isBlank(modelType)){
			if ("5".equals(mediaType)) {
				textureBallFileId = resTexture.getIosTextureBallFileId();
			} else if ("6".equals(mediaType)) {
				textureBallFileId = resTexture.getAndroidTextureBallFileId();
			}else{
				textureBallFileId = resTexture.getTextureBallFileId();
			}
		}else{
			if(modelType.equals("IPhonePlayer")){
				textureBallFileId = resTexture.getIosTextureBallFileId();
			}if(modelType.equals("Android")){
				textureBallFileId = resTexture.getAndroidTextureBallFileId();
			}else{
				textureBallFileId = resTexture.getTextureBallFileId();
			}
		}

		if(textureBallFileId != null && textureBallFileId.intValue()>0){
//			ResFile resFile=resFileService.get(textureBallFileId);
			ResModel resModel = resModelService.get(textureBallFileId);
			if(resModel != null){
				materialPath = resModel.getModelPath();
			}
		}
		
		if(picId!=null&&picId.intValue()>0){
			ResPic resPic=resPicService.get(picId);
			if(resPic!=null){
				String smallPicInfo = resPic.getSmallPicInfo();
				Map<String,String> map = this.getSmallPicInfoMap(smallPicInfo);

				//根据媒介类型取值
				if(StringUtils.isEmpty(modelType)||"null".equals(modelType)||StringUtils.isBlank(modelType)){

					if ("5".equals(mediaType)) {
						Integer iosId = null==map.get("ios")?null:Integer.valueOf(map.get("ios"));
						ResPic iosResPic = iosId == null ? null : resPicService.get(iosId);
						path = iosResPic != null ? iosResPic.getPicPath() : "";
					} else if ("6".equals(mediaType)) {
						Integer androidId = null==map.get("android")?null:Integer.valueOf(map.get("android"));
						ResPic androidResPic = androidId == null ? null : resPicService.get(androidId);
						path = androidResPic != null ? androidResPic.getPicPath() : "";
					}else{
						path=resPic.getPicPath();
					}

				}else{

					if(modelType.equals("IPhonePlayer")){
						Integer iosId = null==map.get("ios")?null:Integer.valueOf(map.get("ios"));
						ResPic iosResPic = iosId == null ? null : resPicService.get(iosId);
						path = iosResPic != null ? iosResPic.getPicPath() : null;
					}if(modelType.equals("Android")){
						Integer androidId = null==map.get("android")?null:Integer.valueOf(map.get("android"));
						ResPic androidResPic = androidId == null ? null : resPicService.get(androidId);
						path = androidResPic != null ? androidResPic.getPicPath() : "";
					}else{
						path=resPic.getPicPath();
					}

				}

			}
		}

		if(normalPicId!=null&&normalPicId.intValue()>0){
			ResPic resPic=resPicService.get(normalPicId);

			if(resPic!=null){
				String smallPicInfo = resPic.getSmallPicInfo();
				Map<String,String> map = this.getSmallPicInfoMap(smallPicInfo);

				//根据媒介类型取值
				if(StringUtils.isEmpty(modelType)||"null".equals(modelType)||StringUtils.isBlank(modelType)){

					if ("5".equals(mediaType)) {
						Integer iosId = null != map.get("ios") ? Integer.valueOf(map.get("ios")) : null;
						ResPic iosResPic = iosId != null ? resPicService.get(iosId) : null;
						normalPicPath = iosResPic != null ? iosResPic.getPicPath() : "";
					} else if ("6".equals(mediaType)) {
						Integer androidId = null != map.get("android") ? Integer.valueOf(map.get("android")) : null;
						ResPic androidResPic = androidId != null ? resPicService.get(androidId) : null;
						normalPicPath = androidResPic != null ? androidResPic.getPicPath() : "";
					}else{
						normalPicPath=resPic.getPicPath();
					}

				}else{

					if(modelType.equals("IPhonePlayer")){
						Integer iosId = null != map.get("ios") ? Integer.valueOf(map.get("ios")) : null;
						ResPic iosResPic = iosId != null ? resPicService.get(iosId) : null;
						normalPicPath = iosResPic != null ? iosResPic.getPicPath() : "";
					}if(modelType.equals("Android")){
						Integer androidId = null != map.get("android") ? Integer.valueOf(map.get("android")) : null;
						ResPic androidResPic = androidId != null ? resPicService.get(androidId) : null;
						normalPicPath = androidResPic != null ? androidResPic.getPicPath() : "";
					}else{
						normalPicPath=resPic.getPicPath();
					}

				}

				normalPicPath= Utils.dealWithPath(normalPicPath, "linux");
			}
		}

		/*添加品牌id与品牌名称*/
		if(null!=resTexture&&null!=resTexture.getBrandId()){
			baseBrand = baseBrandService.get(resTexture.getBrandId());
			if(null!=baseBrand){
				brandName = baseBrand.getBrandName();
			}
		}

		return new SplitTextureDTO().new ResTextureDTO(resTexture.getId(), path, resTexture.getTextureAttrValue(), resTexture.getFileHeight(), 
				resTexture.getFileWidth(), resTexture.getLaymodes(),materialPath,resTexture.getNormalParam(),normalPicPath,
				resTexture.getBrandId(),brandName,resTexture.getTextureCode());
	}

	private Map getSmallPicInfoMap(String smallPicInfo){
		Map<String,String> map = new HashMap<String,String>();
		String[] strs = StringUtils.isEmpty(smallPicInfo) ? new String[0] : smallPicInfo.split(";");
		for(String s:strs){
			String[] ms = s.split(":");
			map.put(ms[0], ms[1]);
		}
		return map;
	}
	/**
	 * 根据图片信息,补充材质数据信息
	 * @author huangsongbo
	 * @param resFile
	 */
	@Override
	public void saveParamsByResFile(ResTexture resTexture, ResFile resFile) {
		resTexture.setFileName(resFile.getFileName());
		resTexture.setFileSize(Integer.valueOf(resFile.getFileSize()));
		resTexture.setFileSuffix(resFile.getFileSuffix());
		resTexture.setFilePath(resFile.getFilePath());
		resTexture.setName(resFile.getFileName());
		resTexture.setFileCode(resFile.getFileCode());
		resTextureMapper.insertSelective(resTexture);
	}
	
	
	/**
	 * 通过id 集合 批量获取数据
	 * @param textureIdStrList
	 * @return
	 */
	@Override
	public List<ResTexture> getBatchGet(ResTexture resTexture) {
		return resTextureMapper.getBatchGet(resTexture);
	}

	@Override
	public List<SplitTextureDTO> getSplitTexturesChooseForSpellingflower(String parquetTextureIds, Integer productId) {
		// 参数验证 ->start
		if(StringUtils.isEmpty(parquetTextureIds)) {
			logger.error(logPrefix + "StringUtils.isEmpty(parquetTextureIds) = true");
			return null;
		}
		// 参数验证 ->end
		
		List<SplitTextureDTO> splitTextureDTOList = new ArrayList<SplitTextureDTO>();
		List<Integer> textureIdList = null;
		try {
			textureIdList = Utils.getIntegerListFromStringList(parquetTextureIds);
		}catch (Exception e) {
			logger.error(logPrefix + "材质信息格式错误; parquetTextureIds = {}", parquetTextureIds);
			logger.error(e.toString());
			return null;
		}
		
		if(Lists.isNotEmpty(textureIdList)) {
			textureIdList.forEach(item -> splitTextureDTOList.add(this.getSplitTextureDTOById(item, productId)));
		}
		
		return splitTextureDTOList;
	}

	@Override
	public SplitTextureDTO getSplitTextureDTOById(Integer id, Integer productId) {
		// 参数验证 ->start
		if(id == null || id < 1) {
			logger.error(logPrefix + "id = null || id < 1");
			return null;
		}
		// 参数验证 ->end
		
		SplitTextureDTO splitTextureDTO = null;
		ResTexture resTexture = this.get(id);
		if (resTexture != null) {
			splitTextureDTO = new SplitTextureDTO("1", "", null);
			List<SplitTextureDTO.ResTextureDTO> resTextureDTOList = new ArrayList<SplitTextureDTO.ResTextureDTO>();
			SplitTextureDTO.ResTextureDTO resTextureDTO = this.fromResTexture(resTexture);
			resTextureDTO.setKey(splitTextureDTO.getKey());
			resTextureDTO.setProductId(productId);
			resTextureDTOList.add(resTextureDTO);
			splitTextureDTO.setList(resTextureDTOList);
		}else {
			logger.error("resTexture is not found; resTextureId = {}" + id);
		}
		return splitTextureDTO;
	}
	
}
