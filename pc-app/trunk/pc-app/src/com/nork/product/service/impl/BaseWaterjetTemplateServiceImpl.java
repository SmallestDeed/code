package com.nork.product.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.nork.common.model.FileModel;
import com.nork.common.util.CodeUtil;
import com.nork.product.model.vo.BrandNameVO;
import com.nork.system.model.vo.SysDictionaryVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nork.common.model.LoginUser;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.product.dao.BaseWaterjetTemplateMapper;
import com.nork.product.model.BaseWaterjetTemplate;
import com.nork.product.model.search.BaseWaterjetTemplateSearch;
import com.nork.product.service.BaseWaterjetTemplateService;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResPic;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResPicService;

/**   
 * @Title: BaseWaterjetTemplateServiceImpl.java 
 * @Package com.nork.product.service.impl
 * @Description:产品模块-水刀模版ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2018-06-04 14:30:27
 * @version V1.0   
 */
@Service("baseWaterjetTemplateService")
@Transactional
public class BaseWaterjetTemplateServiceImpl implements BaseWaterjetTemplateService {

	private static final Logger logger = LoggerFactory.getLogger(BaseWaterjetTemplateServiceImpl.class);
	
	private String logPrefix = "customLog:";
	
	@Autowired
	private BaseWaterjetTemplateMapper baseWaterjetTemplateMapper;

	@Autowired
	private ResFileService resFileService;
	
	@Autowired
	private ResPicService resPicService;

	/**
	 * 新增数据
	 *
	 * @param baseWaterjetTemplate
	 * @return  int 
	 */
	@Override
	public int add(BaseWaterjetTemplate baseWaterjetTemplate) {
		baseWaterjetTemplateMapper.insertSelective(baseWaterjetTemplate);
		if(baseWaterjetTemplate.getId() > 0){
			BaseWaterjetTemplate updateObj = new BaseWaterjetTemplate();
			updateObj.setId(baseWaterjetTemplate.getId());
			updateObj.setTemplateCode(CodeUtil.formatCode("W", baseWaterjetTemplate.getId() - 1 +""));
			this.update(updateObj);

			//回填businessId
			this.setBusinessId(baseWaterjetTemplate.getId().intValue());
		}
		return baseWaterjetTemplate.getId().intValue();
	}

	//回填businessId
	private void setBusinessId(Integer id){
		BaseWaterjetTemplate basewaterjetBu = this.get(id);
		if(null != basewaterjetBu.getTemplatePicId()){

			//回填图片
			ResPic resPic = resPicService.get(basewaterjetBu.getTemplatePicId());
			resPic.setBusinessId(basewaterjetBu.getId().intValue());
			resPicService.update(resPic);
			if(org.apache.commons.lang.StringUtils.isNotBlank(resPic.getSmallPicInfo())){
				//web:7611230;ipad:7611229;
				String[] webpads = resPic.getSmallPicInfo().split(";");
				if(null != webpads && webpads.length > 0){
					for (int i = 0 ; i < webpads.length ; i++){
						String[] ids = webpads[i].split(":");
						if(null != ids && ids.length > 1){
							ResPic resPicSmall = resPicService.get(new Integer(ids[1]));
							resPicSmall.setBusinessId(basewaterjetBu.getId().intValue());
							resPicService.update(resPicSmall);
						}
					}
				}
			}

			//回填CAD文件
			if(null != basewaterjetBu.getCadSourceFileId()){
				ResFile resFile = resFileService.get(basewaterjetBu.getCadSourceFileId());
				if(null != resFile){
					resFile.setBusinessId(basewaterjetBu.getId().intValue());
					resFileService.update(resFile);
				}
			}

		}
	}
	/**
	 *    更新数据
	 *
	 * @param baseWaterjetTemplate
	 * @return  int 
	 */
	@Override
	public int update(BaseWaterjetTemplate baseWaterjetTemplate) {
		return baseWaterjetTemplateMapper
				.updateByPrimaryKeySelective(baseWaterjetTemplate);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return baseWaterjetTemplateMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  BaseWaterjetTemplate 
	 */
	@Override
	public BaseWaterjetTemplate get(Integer id) {
		return baseWaterjetTemplateMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  baseWaterjetTemplate
	 * @return   List<BaseWaterjetTemplate>
	 */
	@Override
	public List<BaseWaterjetTemplate> getList(BaseWaterjetTemplate baseWaterjetTemplate) {
	    return baseWaterjetTemplateMapper.selectList(baseWaterjetTemplate);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  baseWaterjetTemplateSearch
	 * @return   int
	 */
	@Override
	public int getCount(BaseWaterjetTemplateSearch baseWaterjetTemplateSearch){
		return  baseWaterjetTemplateMapper.selectCount(baseWaterjetTemplateSearch);	
    }
	
	/**
	 *    分页获取数据
	 *
	 * @param  baseWaterjetTemplateSearch
	 * @return   List<BaseWaterjetTemplate>
	 */
	@Override
	public List<BaseWaterjetTemplate> getPaginatedList(BaseWaterjetTemplateSearch baseWaterjetTemplateSearch) {
		return baseWaterjetTemplateMapper.selectPaginatedList(baseWaterjetTemplateSearch);
	}

	@Override
	public boolean save(BaseWaterjetTemplate baseWaterjetTemplate, LoginUser loginUser) {
		// 参数验证 ->start
		if(baseWaterjetTemplate == null) {
			logger.error(logPrefix + "baseWaterjetTemplate == null");
			return false;
		}
		// 参数验证 ->end
		
		this.sysSave(baseWaterjetTemplate, loginUser);
		
		if(baseWaterjetTemplate.getId() == null || baseWaterjetTemplate.getId() <= 0) {
			// create
			// 新增方法参数验证 ->start
			boolean flag = this.checkParamsForAdd(baseWaterjetTemplate);
			if(!flag) {
				return false;
			}
			// 新增方法参数验证 ->end
			
			this.add(baseWaterjetTemplate);
		}else {
			// update
			int updateCount = this.update(baseWaterjetTemplate);
			if(updateCount < 0) {
				logger.error(logPrefix + "update failed,id = {} 的数据没有找到", baseWaterjetTemplate.getId());
				return false;
			}
		}
		
		// 回填文件businessId ->start
		this.updateFileBusinessId(baseWaterjetTemplate);
		// 回填文件businessId ->end
		
		return true;
	}

	/**
	 * 回填文件(模型文件/展示图片)businessId
	 * 
	 * @author huangsongbo
	 * @param baseWaterjetTemplate
	 */
	private void updateFileBusinessId(BaseWaterjetTemplate baseWaterjetTemplate) {
		// 参数验证 ->start
		if(baseWaterjetTemplate == null) {
			logger.error(logPrefix + "baseWaterjetTemplate = null");
			return;
		}
		if(baseWaterjetTemplate.getId() == null) {
			logger.error(logPrefix + "baseWaterjetTemplate.getId() = null");
			return;
		}
		// 参数验证 ->end
		
		// templateFileId ->start
		Integer templateFileId = baseWaterjetTemplate.getTemplateFileId();
		resFileService.backfill(templateFileId, baseWaterjetTemplate.getId().intValue());
		// templateFileId ->end
		
		// templatePicId ->start
		Integer templatePicId = baseWaterjetTemplate.getTemplatePicId();
		resPicService.backfill(templatePicId, baseWaterjetTemplate.getId().intValue());
		// templatePicId ->end
		
	}

	@Override
	public boolean checkParamsForAdd(BaseWaterjetTemplate baseWaterjetTemplate) {
		if(baseWaterjetTemplate == null) {
			logger.error(logPrefix + "baseWaterjetTemplate = nulll");
			return false;
		}
		if(baseWaterjetTemplate.getTemplateLength() == null) {
			logger.error(logPrefix + "baseWaterjetTemplate.getTemplateLength() = null");
			return false;
		}
		if(baseWaterjetTemplate.getTemplateWidth() == null) {
			logger.error(logPrefix + "baseWaterjetTemplate.getTemplateWidth() = null");
			return false;
		}
		if(StringUtils.isEmpty(baseWaterjetTemplate.getTemplateName())) {
			logger.error(logPrefix + "StringUtils.isEmpty(baseWaterjetTemplate.getTemplateName()) = true");
			return false;
		}
		if(baseWaterjetTemplate.getTemplateFileId() == null || baseWaterjetTemplate.getTemplateFileId() <= 0) {
			logger.error(logPrefix + "(baseWaterjetTemplate.getTemplateFileId() == null || baseWaterjetTemplate.getTemplateFileId() <= 0) = true");
			return false;
		}
		if(baseWaterjetTemplate.getTemplatePicId() == null || baseWaterjetTemplate.getTemplatePicId() <= 0) {
			logger.error(logPrefix + "(baseWaterjetTemplate.getTemplatePicId() == null || baseWaterjetTemplate.getTemplatePicId() <= 0) = true");
			return false;
		}
		return true;
	}

	/**
	 * 自动存储系统字段
	 */
	private void sysSave(BaseWaterjetTemplate model, LoginUser loginUser) {
		if (model != null) {
			/*LoginUser loginUser = new LoginUser();
			if (request.getSession() == null || request.getSession().getAttribute("loginUser") == null) {
				loginUser.setLoginName("nologin");
			} else {
				loginUser = (LoginUser) request.getSession().getAttribute("loginUser");
			}*/

			if (model.getId() == null) {
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
				if (model.getSysCode() == null || "".equals(model.getSysCode())) {
					model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
				}
			}

			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}

	@Override
	public List<BaseWaterjetTemplate> findAllBySearch(BaseWaterjetTemplateSearch search) {
		// 参数验证/处理 ->start
		if(search == null) {
			logger.error(logPrefix + "search = null");
			return null;
		}
		/*if(search.getIsDeleted() == null) {
			search.setIsDeleted(0);
		}*/
		// 参数验证/处理 ->end

//		if(search.getSearchType() == 1){
//			List<BrandNameVO> brandNameVOList = baseWaterjetTemplateMapper.selectBrandNameList(search.getUserId(),search.getUserType());
//			if(brandNameVOList != null && brandNameVOList.size() > 0){
//				List<Integer> idList = brandNameVOList.stream().map(BrandNameVO::getId).map(m ->Integer.parseInt(m)).collect(Collectors.toList());
//				search.setBrandIdList(idList);
//			}
//		}
		
		return baseWaterjetTemplateMapper.findAllBySearch(search);
	}

	@Override
	public int getCountBySearch(BaseWaterjetTemplateSearch search) {
		// 参数验证/处理 ->start
		if(search == null) {
			logger.error(logPrefix + "search = null");
			return 0;
		}
		// 参数验证/处理 ->end

//		if(search.getSearchType() == 1){
//			List<BrandNameVO> brandNameVOList = baseWaterjetTemplateMapper.selectBrandNameList(search.getUserId(),search.getUserType());
//			if(brandNameVOList != null && brandNameVOList.size() > 0){
//				List<Integer> idList = brandNameVOList.stream().map(BrandNameVO::getId).map(m ->Integer.parseInt(m)).collect(Collectors.toList());
//				if(null == idList) {
//					logger.error(logPrefix + "该用户未关联品牌");
//					return 0;
//				}
//				search.setBrandIdList(idList);
//			}
//		}

		
		return baseWaterjetTemplateMapper.getCountBySearch(search);
	}

	@Override
	public List<BaseWaterjetTemplate> setMoreInfoForfindAll(List<BaseWaterjetTemplate> list) {
		// 参数验证 ->start
		if(Lists.isEmpty(list)) {
			logger.error(logPrefix + "Lists.isEmpty(list) = true");
			return list;
		}
		// 参数验证 ->end
		
		// 获取ipad端缩略图 ->start
		List<Integer> smallPicIdList = new ArrayList<Integer>();
		list.forEach(item -> {
			if(StringUtils.isNotEmpty(item.getTemplateSmallPicInfo())) {
				// web:6092535;ipad:6092534;
				Map<String, String> smallPicInfoMap = Utils.getMapFromStr(item.getTemplateSmallPicInfo());
				try {
					item.setTemplateIpadSmallPicId(Integer.valueOf(smallPicInfoMap.get("ipad")));
				}catch (Exception e) {
					logger.error("String转换Integer失败; templateIpadSmallPicId = {};", smallPicInfoMap.get("ipad"));
				}
				if(item.getTemplateIpadSmallPicId() != null) {
					smallPicIdList.add(item.getTemplateIpadSmallPicId());
				}
			}
		});
		
		// key:picId;value:picPath
		Map<Integer, String> picPathMap = new HashMap<Integer, String>();
		if(Lists.isNotEmpty(smallPicIdList)) {
			List<ResPic> resPicList = resPicService.getPicPathByIdList(smallPicIdList);
			if(Lists.isNotEmpty(resPicList)) {
				resPicList.forEach(item -> {
					picPathMap.put(item.getId(), item.getPicPath());
				});
			}
		}
		
		// 获取ipad端缩略图 ->end
		
		/** 获取CAD源文件路径 begin **/
		// 获取CAD源文件Id集合
		List<Integer> cadFileIdList = new ArrayList<Integer>(list.size());
		list.forEach(item ->{
		    if(Utils.isNotEmpty(item.getCadSourceFileId())){
		      cadFileIdList.add(item.getCadSourceFileId());
		    }
		});
		//获取文件地址
		Map<Integer, String> cadFilePathMap = new HashMap<Integer,String>();
		if(Lists.isNotEmpty(cadFileIdList)) {
		    List<ResFile> resFilePathList = resFileService.getFilePathByIdList(cadFileIdList);
		    if(Lists.isNotEmpty(resFilePathList)) {
		      resFilePathList.forEach(file -> {
		          cadFilePathMap.put(file.getId(), file.getFilePath());
		      });
		    }
		}
		
		/** 获取CAD源文件路径 end **/
		
		//回填数据
		list.forEach(item -> {
            if(item.getTemplateIpadSmallPicId() != null) {
                item.setTemplatePicUrl(picPathMap.get(item.getTemplateIpadSmallPicId()));
            }
            if(Utils.isNotEmpty(item.getCadSourceFileId())) {
                item.setCadSourceFilePath(cadFilePathMap.get(item.getCadSourceFileId()));
            }
        });
		return list;
	}

	@Override
	public BaseWaterjetTemplate getMoreInfoById(Integer id) {
		// 参数验证 ->start
		if(id == null) {
			logger.error("id = null");
			return null;
		}
		// 参数验证 ->end

		return baseWaterjetTemplateMapper.getMoreInfoById(id);
	}

	/**
	 * 将图片上传信息保存到resPic
	 * @param map 上传服务器文件的详细信息
	 * @param businessId 该资源所关联的表id
	 * @param loginUser 登录名
	 * @return
	 */
	public Integer saveUploadImgPic(Map map,Integer businessId,LoginUser loginUser,String mes){
		Integer id = null;
		if( map != null && map.size() > 0 ) {
			ResPic resPic = new ResPic();

			//设置数据
			String dbFilePath = map.get("dbFilePath").toString();
			String fileName = map.get(FileModel.FILE_NAME).toString();
			resPic.setGmtCreate(new Date());
			resPic.setGmtModified(new Date());
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
			resPic.setPicType(mes);

			this.saveSystemInfo(resPic,loginUser);

			//保存
			resPicService.add(resPic);
			if(resPic.getId() > 0)
				id = resPic.getId().intValue();
		}

		return id;
	}

	@Override
	public List<BrandNameVO> selectBrandNameListByUser(int userId, Integer userType){
		return baseWaterjetTemplateMapper.selectBrandNameListByUser(userId,userType);
	}

	@Override
	public List<SysDictionaryVo> getSysDictionaryList(){
		return baseWaterjetTemplateMapper.selectSysDictionaryList();
	}

	/**
	 * 将文件上传信息保存到resfile
	 * @author chenqiang
	 * @param map 上传服务器文件的详细信息
	 * @param businessId 该资源所关联的表id
	 * @param loginUser 登录名
	 * @return 新增数据主键id
	 */
	public Integer saveUploadFile(Map map, Integer businessId, LoginUser loginUser, String mes){
		Integer id = null;
		if( map != null && map.size() > 0 ) {
			ResFile resFile = new ResFile();

			//设置数据
			String dbFilePath = map.get("dbFilePath").toString();
			String fileName = map.get(FileModel.FILE_NAME).toString();
			resFile.setIsDeleted(0);
			resFile.setFileName(fileName);//文件名
			resFile.setFileOriginalName(fileName);
			resFile.setFileType(mes);
			resFile.setFileSize(map.get(FileModel.FILE_SIZE).toString());
			resFile.setFileSuffix(map.get(FileModel.FILE_SUFFIX).toString());
			resFile.setFileLevel("0");
			resFile.setFilePath(dbFilePath);
			resFile.setFileOrdering(0);
			resFile.setFileKey(map.get(FileModel.FILE_KEY).toString());
			resFile.setBusinessId(businessId);//该资源所关联的表id

			this.saveSystemInfo(resFile,loginUser);

			//保存
			resFileService.add(resFile);
			if(resFile.getId() > 0)
				id = resFile.getId().intValue();
		}

		return id;
	}

	private void saveSystemInfo(ResPic pic, LoginUser loginUser) {
		if(pic != null){
			//新增
			if(pic.getId() == null){
				pic.setGmtCreate(new Date());
				pic.setCreator(loginUser.getLoginName());
				pic.setIsDeleted(0);
				if(pic.getSysCode()==null || "".equals(pic.getSysCode())){
					pic.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
					pic.setPicCode(pic.getSysCode());
				}
			}
			//修改
			pic.setGmtModified(new Date());
			pic.setModifier(loginUser.getLoginName());
		}
	}

	private void saveSystemInfo(ResFile file, LoginUser loginUser) {
		if(file != null){
			//新增
			if(file.getId() == null){
				file.setGmtCreate(new Date());
				file.setCreator(loginUser.getLoginName());
				file.setIsDeleted(0);
				if(file.getSysCode()==null || "".equals(file.getSysCode())){
					file.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
					file.setFileCode(file.getSysCode());
				}
			}
			//修改
			file.setGmtModified(new Date());
			file.setModifier(loginUser.getLoginName());
		}
	}

}
