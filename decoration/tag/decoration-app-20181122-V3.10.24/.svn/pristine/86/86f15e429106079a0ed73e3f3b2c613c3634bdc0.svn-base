package com.nork.design.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.nork.common.model.FileModel;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.FileUploadUtils;
import com.nork.common.util.FtpUploadUtils;
import com.nork.common.util.Tools;
import com.nork.common.util.Utils;
import com.nork.design.controller.web.DesginPlanSpellingflowerRecordController;
import com.nork.design.dao.DesginPlanSpellingflowerRecordMapper;
import com.nork.design.model.DesginPlanSpellingflowerRecord;
import com.nork.design.model.DesignPlan;
import com.nork.design.model.search.DesginPlanSpellingflowerRecordSearch;
import com.nork.design.service.DesginPlanSpellingflowerRecordService;
import com.nork.design.service.DesignPlanService;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.SplitTextureDTO;
import com.nork.product.service.BaseProductService;
import com.nork.product.service.BaseProductServiceV2;
import com.nork.system.model.ResDesign;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResTexture;
import com.nork.system.service.ResDesignService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResTextureService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**   
 * @Title: DesginPlanSpellingflowerRecordServiceImpl.java 
 * @Package com.nork.design.service.impl
 * @Description:拼花-设计方案瓷砖拼花记录ServiceImpl
 * @createAuthor roc
 * @CreateDate 2017-11-23 14:41:02
 * @version V1.0   
 */
@Service("desginPlanSpellingflowerRecordService")
public class DesginPlanSpellingflowerRecordServiceImpl implements DesginPlanSpellingflowerRecordService {
	private static Logger logger = Logger.getLogger(DesginPlanSpellingflowerRecordServiceImpl.class);

	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private BaseProductServiceV2 baseProductServiceV2;
	@Autowired
	private DesignPlanService designPlanService;
	@Autowired
	private ResDesignService ResDesignService;
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private ResTextureService resTextureService;
	@Autowired
	private ResModelService resModelService;
	@Autowired
	private DesginPlanSpellingflowerRecordMapper desginPlanSpellingflowerRecordMapper;

	/**
	 * 新增数据
	 * @param desginPlanSpellingflowerRecord
	 * @return 返回信息
	 */
	@Override
	public ResponseEnvelope add(DesginPlanSpellingflowerRecord desginPlanSpellingflowerRecord,LoginUser loginUser,MultipartFile multipartFile) {
		logger.info("新增数据 service 开始:参数="+ JSON.toJSONString(desginPlanSpellingflowerRecord)+"用户="+JSON.toJSONString(loginUser));
		String msgId = desginPlanSpellingflowerRecord.getMsgId();

		/** 获取方案信息,并判断、设值 */
		//1.获取方案信息
		DesignPlan designPlan = designPlanService.get(desginPlanSpellingflowerRecord.getPlanId());
		if(designPlan == null) {
			return new ResponseEnvelope<DesignPlan>(false,"草图方案不存在",msgId);
		}
		if(loginUser.getId().intValue() != designPlan.getUserId().intValue()) {
			return new ResponseEnvelope<DesignPlan>(false,"无法操作他人方案",msgId);
		}
		//2.将方案sysCode设为拼花的sysCode
		desginPlanSpellingflowerRecord.setSysCode(designPlan.getSysCode());

		/** 判断该方案已使用拼花数量 */
		//1.设值查询参数
		DesginPlanSpellingflowerRecordSearch desginRecordSearch = new DesginPlanSpellingflowerRecordSearch();
		desginRecordSearch.setPlanId(desginPlanSpellingflowerRecord.getPlanId());
		desginRecordSearch.setOrder("re.gmt_create");
		desginRecordSearch.setOrderNum("ASC");
		desginRecordSearch.setIsDeleted(0);
		desginRecordSearch.setStart(-1);
		desginRecordSearch.setLimit(-1);
		//2.调用
		List<DesginPlanSpellingflowerRecord> list = desginPlanSpellingflowerRecordMapper.selectPaginatedList(desginRecordSearch);
		//3.判断
		if(null!=list&&list.size()==48){
			//4.删除旧的那条数据
			Integer idDel = desginPlanSpellingflowerRecordMapper.deleteByPrimaryKey(list.get(0).getId());
			//5.判断是否删除成功
			if(null==idDel){
				return new ResponseEnvelope<DesignPlan>(false,"删除旧拼花数据失败",msgId);
			}
		}

		/** 保存拼花信息到服务器 */
		//1.获取存储路径
		String fileKey = "design.designPlan.spellingFlowerFile.record.upload.path";
		String defaultPath = "/AA/d_userdesign/[yyyy]/[MM]/[dd]/[HH]/design/designPlan/spellingFlowerFile/record/";
		String configKey = Utils.getPropertyName("config/res",fileKey,defaultPath).trim();
		configKey = Utils.replaceDate(configKey);
		String fileName = System.currentTimeMillis()+"_" + Utils.generateRandomDigitString(6)+".txt";
		String filePath = Utils.getAbsolutePath(configKey + fileName, null);

		//测试添加
//		filePath = FileUploadUtils.UPLOAD_ROOT+filePath;

		if( "linux".equals(FileUploadUtils.SYSTEM_FORMAT) ){
			filePath = filePath.replace("\\", "/");
		}
		//2.上传到服务器
		boolean uploadFtpFlag = FileUploadUtils.writeTxtFile(filePath,desginPlanSpellingflowerRecord.getSpellingFlower());
		//3.判断是否成功上传到服务器：成功保存资源路径
		if(uploadFtpFlag){
			//4.获取上传文件详细信息
			Map <String, String> map = FileUploadUtils.getMap(new File(filePath), fileKey, false);
			//5.保存资源路径到resDesign表
			Integer id = this.saveSpellingFlowerFile(map,desginPlanSpellingflowerRecord.getSysCode(),desginPlanSpellingflowerRecord.getCreator(),desginPlanSpellingflowerRecord.getPlanId());
			if(null!=id){
				//6.将保存资源路径表的id设值到已使用表中
				desginPlanSpellingflowerRecord.setSpellingFlowerFileId(id);
			}else{
				return new ResponseEnvelope<DesignPlan>(false,"保存资源路径到resDesign出错",msgId);
			}
		}else{
			return new ResponseEnvelope<DesignPlan>(false,"上传资源到服务器出错",msgId);
		}

		/** 保存图片到服务器 */
		if(null!=multipartFile){
			String picFileKey = "design.designPlan.spellingFlowerFile.record.pic.upload.path";
			String picDefaultPath = "/AA/d_userdesign/[yyyy]/[MM]/[dd]/[HH]/design/designPlan/spellingFlowerFile/record/pic/";
			// 上传文件的原名(即上传前的文件名字)
			String imageName = null;
			String suffix = null;

			String originalFilename = multipartFile.getOriginalFilename();
			// 获得当前时间
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			// 转换为字符串
			String formatDate = format.format(new Date());
			// 随机生成文件编号
			int random = new Random().nextInt(10000);
			imageName = new StringBuffer().append(formatDate).append(random).toString();
			suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

			// 获取配置的业务文件目录
			String picFilePath = Utils.getPropertyName("config/res", picFileKey, picDefaultPath);
			picFilePath = Utils.replaceDate(picFilePath);
			// 数据库存储目录
			String dbFilePath = picFilePath + imageName + suffix;
			// 文件服务器的存储路径
			String realPath = Tools.getRootPath(picFilePath.replace("/", "\\"),"") + picFilePath;
			if ("linux".equals(FileUploadUtils.SYSTEM_FORMAT)) {
				realPath = Tools.getRootPath(picFilePath,"") + picFilePath;
			}
			try {
				// 这里不必处理IO流关闭的问题,因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
				FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), new File(realPath, imageName + suffix));
			} catch (IOException e) {
				e.printStackTrace();
				return new ResponseEnvelope(false, "保存拼花图片信息失败！", msgId);
			}
			String fpath = realPath + imageName + suffix;
			File f = new File(fpath);
			Map map = FileUploadUtils.getMap(f, Tools.getRootPath(fpath, ""), false);

			//5.保存资源路径到resPic表
			Integer picId = this.saveSpellingFlowerFilePic(map,desginPlanSpellingflowerRecord.getSysCode(),desginPlanSpellingflowerRecord.getCreator(),desginPlanSpellingflowerRecord.getPlanId());
			if(null!=picId){
				//6.将保存资源路径表的id设值到已使用表中
				desginPlanSpellingflowerRecord.setSpellingFlowerPicId(picId);
			}else{
				return new ResponseEnvelope<DesignPlan>(false,"保存图片资源信息到resPic出错",msgId);
			}

			/* ftp上传 */
			try {
				boolean flag = false;
				// 仅支持ftp服务器上传
				if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 2) {
					logger.info("------ 拼花图片上传ftp服务器删除本地文件");
					flag = FtpUploadUtils.uploadFile(imageName + suffix, realPath + imageName + suffix, picFilePath);
					if (flag) {
						// 删除本地
						FileUploadUtils.deleteFile(dbFilePath);
					} else {
						logger.info("---------仅支持ftp服务器文件上传异常！");
					}
				}
				// 3 本地和ftp同时上传(默认是本地上传)
				if (Utils.getIntValue(FileUploadUtils.FTP_UPLOAD_METHOD) == 3) {
					logger.info("------拼花图片上传ftp服务器不删除本地文件");
					flag = FtpUploadUtils.uploadFile(imageName + suffix, realPath + imageName + suffix, picFilePath);
					if (!flag) {
						logger.info("---------本地和ftp服务器同时文件上传异常！");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("----ftp上传异常：" + e.getMessage());
			}

		}

		/** 保存拼花信息到已使用表 */
		//1.调用mapper保存信息
		Integer id = desginPlanSpellingflowerRecordMapper.insertSelective(desginPlanSpellingflowerRecord);
		//2.判断是否保存成功
		if(null==id){
			return new ResponseEnvelope<DesignPlan>(false,"保存拼花信息到已使用表失败",msgId);
		}else {
			return new ResponseEnvelope<DesignPlan>(true, "保存已使用拼花成功", msgId);
		}

	}

	/**
	 * 将拼花文件信息保存到数据库中
	 * @param map 上传服务器文件的详细信息
	 * @param sysCode 该资源所关联的表sysCode
	 * @param creator 创建人
	 * @param businessId 该资源所关联的表id
	 * @return
	 */
	public Integer saveSpellingFlowerFile(Map map,String sysCode,String creator,Integer businessId){
		Integer id = null;
		if( map != null && map.size() > 0 ) {
			ResDesign resDesign = new ResDesign();

			//设置数据
			String dbFilePath = map.get(FileUploadUtils.DB_FILE_PATH).toString();
			String fileName = map.get(FileModel.FILE_NAME).toString();
			resDesign.setSysCode(sysCode);//该资源所关联的表sysCode
			resDesign.setFileCode(sysCode);
			resDesign.setGmtCreate(new Date());
			resDesign.setGmtModified(new Date());
			resDesign.setCreator(creator);
			resDesign.setModifier(creator);
			resDesign.setIsDeleted(0);
			resDesign.setFileName(fileName);//文件名
			resDesign.setFileSize(map.get(FileModel.FILE_SIZE).toString());
			resDesign.setFileSuffix(map.get(FileModel.FILE_SUFFIX).toString());
			resDesign.setBusinessId(businessId);//该资源所关联的表id
			resDesign.setFileOriginalName(fileName);//原文件名
			resDesign.setFilePath(dbFilePath);
			resDesign.setFileKey(map.get(FileModel.FILE_KEY).toString());
			resDesign.setFileLevel("0");
			resDesign.setFileType("设计方案已使用拼花文件");

			//保存
			id = ResDesignService.add(resDesign);
		}

		return id;
	}

	/**
	 * 将拼花图片信息保存到数据库中
	 * @param map 上传服务器文件的详细信息
	 * @param sysCode 该资源所关联的表sysCode
	 * @param creator 创建人
	 * @param businessId 该资源所关联的表id
	 * @return
	 */
	public Integer saveSpellingFlowerFilePic(Map map,String sysCode,String creator,Integer businessId){
		Integer id = null;
		if( map != null && map.size() > 0 ) {
			ResPic resPic = new ResPic();

			//设置数据
			String dbFilePath = map.get(FileUploadUtils.DB_FILE_PATH).toString();
			String fileName = map.get(FileModel.FILE_NAME).toString();
			resPic.setSysCode(sysCode);//该资源所关联的表sysCode
			resPic.setPicCode(sysCode);
			resPic.setGmtCreate(new Date());
			resPic.setGmtModified(new Date());
			resPic.setCreator(creator);
			resPic.setModifier(creator);
			resPic.setIsDeleted(0);
			resPic.setPicName(fileName);//文件名
			resPic.setPicSize(Integer.parseInt(map.get(FileModel.FILE_SIZE).toString()));
			resPic.setPicSuffix(map.get(FileModel.FILE_SUFFIX).toString());
			resPic.setBusinessId(businessId);//该资源所关联的表id
			resPic.setPicName(fileName);//原文件名
			resPic.setPicFileName(fileName);
			resPic.setPicPath(dbFilePath);
			resPic.setFileKey(map.get(FileModel.FILE_KEY).toString());
			resPic.setPicLevel("0");
			resPic.setPicHigh(map.get(FileModel.PIC_WEIGHT).toString());
			resPic.setPicWeight(map.get(FileModel.PIC_HEIGHT).toString());
			resPic.setPicFormat(map.get(FileModel.FORMAT).toString());
			resPic.setPicType("设计方案已使用拼花图片");
			//保存
			id = resPicService.add(resPic);
		}

		return id;
	}

	/**
	 * 分页获取数据
	 * @param  recordSearch 查询参数
	 * @return
	 */
	@Override
	public ResponseEnvelope<DesginPlanSpellingflowerRecord> getPaginatedList(DesginPlanSpellingflowerRecordSearch recordSearch) {
		logger.info("分页获取数据getPaginatedList参数 service 开始：参数="+ JSON.toJSONString(recordSearch));
		String msgId = recordSearch.getMsgId();
		long totalCount = 0;
		List<DesginPlanSpellingflowerRecord> recordList = new ArrayList<DesginPlanSpellingflowerRecord>();

		/** 设值查询参数 */
		recordSearch.setIsDeleted(0);
		recordSearch.setOrder("re.gmt_create");
		recordSearch.setOrderNum("DESC");

		/** 获取当前方案未删除的数据  */
		try {
			//1.获取总数量
			totalCount = desginPlanSpellingflowerRecordMapper.selectCount(recordSearch);
			if(totalCount>0){
				//2.获取集合
				recordList = desginPlanSpellingflowerRecordMapper.selectPaginatedList(recordSearch);
			}
		}catch (Exception e){
			logger.error("获取已使用拼花数据失败"+e.getMessage(),e);
			return new ResponseEnvelope(false,"获取已使用拼花数据失败！",msgId);
		}


		/** 判断集合与获取相对应的资源信息，并解析 */
		//1.判断是否存在数据
		if(null!=recordList&&recordList.size()>0){
			try {
				//2.循环数据
				for(int i = 0;i<recordList.size();i++){
					//3.获取当前对象
					DesginPlanSpellingflowerRecord record = recordList.get(i);
					//3.获取产品ids
					String productIds = record.getSpellingFlowerProduct();
					//4.获取当期拼花所对应的产品map集合
					Map<String,Object> spellingFlowerProductMap = this.spellingFlowerData(productIds);
					//5.设值到当前对象
					record.setSpellingFlowerProductMap(spellingFlowerProductMap);
				}
			}catch (Exception e){
				logger.error("获取已使用拼花产品数据失败"+e.getMessage(),e);
				return new ResponseEnvelope(false,"获取已使用拼花产品数据失败！",msgId);
			}
		}

		return new ResponseEnvelope<DesginPlanSpellingflowerRecord>(msgId,true,"获取已使用拼花产品数据成功！",totalCount,recordList);
	}


	/**
	 * 获取拼花产品信息
	 * @param spellingFlowerProduct 产品ids
	 * @return
	 */
	public Map spellingFlowerData(String spellingFlowerProduct) throws Exception{
		Map<String,Object> spellingFlowerProductMap = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(spellingFlowerProduct)){
			//获取产品id集合
			String [] arr = spellingFlowerProduct.split(",");
			List<BaseProduct>  productList = null;
			if(arr != null && arr.length > 0 && !"[]".equals(arr)) {
				List<Integer>ids = new ArrayList<Integer>();
				for (String id : arr) {
					ids.add(Integer.parseInt(id));
				}
				productList = baseProductServiceV2.getBatchData(ids);
			}

			if(productList != null && productList.size() > 0) {
				for (BaseProduct product : productList) {
					List<SplitTextureDTO> splitTextureDTOList = new ArrayList<>();
					Integer isSplit = 0;
					if(StringUtils.isNotEmpty(product.getSplitTexturesInfo())){
						Map<String, Object> map = baseProductService.dealWithSplitTextureInfo(product.getId(), product.getSplitTexturesInfo(), "choose");
						isSplit = (Integer) map.get("isSplit");
						splitTextureDTOList = (List<SplitTextureDTO>) map.get("splitTexturesChoose");
					}else {
						if(StringUtils.isNotBlank(product.getMaterialPicIds())){
							this.getSingleTexture(product, splitTextureDTOList);
						}
					}
					if(null!=splitTextureDTOList&&splitTextureDTOList.size()>0){
						spellingFlowerProductMap.put(product.getId()+"", splitTextureDTOList);
					}
				}
			}
		}
		return spellingFlowerProductMap;
	}

	/**
	 * 获取产品材质信息
	 * @param product
	 * @param splitTextureDTOList
	 */
	public void getSingleTexture(BaseProduct product, List<SplitTextureDTO> splitTextureDTOList) {
		if( splitTextureDTOList == null ){
			return;
		}
		ResModel textureBallModel = null;
		String materialPath = "";
		ResPic normalPic = null;
		String normalParam = "";
		String normalPath = "";
		SplitTextureDTO splitTextureDTO = null;
		ResTexture resTexture = resTextureService.get(Integer.valueOf(product.getMaterialPicIds()));
		if(null!=resTexture){
			if(resTexture.getTextureBallFileId() != null){
				textureBallModel = resModelService.get(resTexture.getTextureBallFileId());
				if(textureBallModel != null){
					materialPath = textureBallModel.getModelPath();
					materialPath = Utils.dealWithPath(materialPath, "linux");
				}
			}
			if(resTexture.getNormalPicId()!=null){
				normalParam = resTexture.getNormalParam();
				normalPic =  resPicService.get(resTexture.getNormalPicId());
				if(normalPic!=null){
					normalPath = normalPic.getPicPath();
					normalPath = Utils.dealWithPath(normalPath, "linux");
				}
			}

			String resTexturePic = null; //材质图片
			if(resTexture.getPicId() !=null && resTexture.getPicId().intValue() > 0) {
				ResPic resPic = resPicService.get(resTexture.getPicId());
				if(resPic != null) {
					resTexturePic = resPic.getPicPath();
				}
			}

			// 单材质产品
			splitTextureDTO = new SplitTextureDTO(product.getProductCode(), "", null);
			SplitTextureDTO.ResTextureDTO resTextureDTO = splitTextureDTO.new ResTextureDTO(
					Integer.valueOf(product.getMaterialPicIds()),resTexturePic,resTexture.getTextureAttrValue(),
					resTexture.getFileHeight(),resTexture.getFileWidth(),resTexture.getLaymodes(),materialPath,normalParam,normalPath);
			resTextureDTO.setKey("1");
			resTextureDTO.setProductId(product.getId());
			List<SplitTextureDTO.ResTextureDTO> resTextureDTOList = new ArrayList<SplitTextureDTO.ResTextureDTO>();
			resTextureDTOList.add(resTextureDTO);
			splitTextureDTO.setList(resTextureDTOList);
			
			//加入
			splitTextureDTOList.add(splitTextureDTO);
		}
	}


	/**
	 *    更新数据
	 *
	 * @param desginPlanSpellingflowerRecord
	 * @return  int
	 */
	@Override
	public int update(DesginPlanSpellingflowerRecord desginPlanSpellingflowerRecord) {
		return desginPlanSpellingflowerRecordMapper.updateByPrimaryKeySelective(desginPlanSpellingflowerRecord);
	}

	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int
	 */
	@Override
	public int delete(Integer id) {
		return desginPlanSpellingflowerRecordMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  DesginPlanSpellingflowerRecord
	 */
	@Override
	public DesginPlanSpellingflowerRecord get(Integer id) {
		return desginPlanSpellingflowerRecordMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 *
	 * @param  desginPlanSpellingflowerRecord
	 * @return   List<DesginPlanSpellingflowerRecord>
	 */
	@Override
	public List<DesginPlanSpellingflowerRecord> getList(DesginPlanSpellingflowerRecord desginPlanSpellingflowerRecord) {
		return desginPlanSpellingflowerRecordMapper.selectList(desginPlanSpellingflowerRecord);
	}

	/**
	 *    获取数据数量
	 *
	 * @param  desginPlanSpellingflowerRecordSearch
	 * @return   int
	 */
	@Override
	public int getCount(DesginPlanSpellingflowerRecordSearch desginPlanSpellingflowerRecordSearch){
		return  desginPlanSpellingflowerRecordMapper.selectCount(desginPlanSpellingflowerRecordSearch);
	}
}
