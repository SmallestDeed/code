package com.nork.client.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nork.client.model.ResTextureResponse;
import com.nork.client.model.TextureResponse;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.design.controller.web.WebDesignPlanProductController;
import com.nork.system.dao.ResFileMapper;
import com.nork.system.dao.ResTextureMapper;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResTexture;
import com.nork.system.model.search.ResTextureSearch;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResTextureService;

/**   
 * @Title: ResTextureController.java 
 * @Package com.nork.client.controller
 * @Description:客户端材质模块-材质库Controller
 * @createAuthor xiaoxc 
 * @CreateDate 2016-09-13 21:56:42
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/client/resTexture")
public class ResTextureController {
	private static Logger logger = Logger.getLogger(WebDesignPlanProductController.class);
	private final String RESOURCES_ROOT_URL = Utils.getValue("app.resources.url", "http://192.168.23.4:8080/timeSpace");
	
	@Autowired
	private ResTextureService resTextureService;
	@Autowired
	private ResFileService resFileService;
	@Autowired
	private ResModelService resModelService;
	@Autowired
	private ResTextureMapper resTextureMapper;
	@Autowired
	private ResFileMapper resFileMapper;
	/**
	 * 材质配置列表
	 */
	@RequestMapping(value = "/getMaterialList")
	@ResponseBody
	public Object getMaterialList( @ModelAttribute("resTextureSearch") ResTextureSearch resTextureSearch,HttpServletRequest request, HttpServletResponse response) {
		
		TextureResponse textureResponse = null;
		
		//查询材质配置文件列表(type)标识
		resTextureSearch.setIsDeleted(0);
		List<TextureResponse> list = new ArrayList<TextureResponse> ();
		int total = 0;
		try {
			total = resTextureService.getCount(resTextureSearch);
            
			if (total > 0) {
				List<ResTexture> rtList = resTextureService.getPaginatedList(resTextureSearch);
				for(ResTexture rt : rtList){
					
					textureResponse = new TextureResponse();
					
					if( rt.getPicId() != null && rt.getPicId() > 0 ){
						textureResponse.setFileName(rt.getFileName());
					}
					list.add(textureResponse);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ResTexture>(false, "数据异常!",resTextureSearch.getMsgId());
		}
		return new ResponseEnvelope<TextureResponse>(total, list, resTextureSearch.getMsgId());
	}
	
	
	/**
	 * 材质贴图列表
	 */
	@RequestMapping(value = "/getTextureList")
	@ResponseBody
	public Object getTextureList(String fileName, String type, HttpServletRequest request, HttpServletResponse response) {
		
		if( StringUtils.isEmpty(fileName) ){
			return new ResponseEnvelope<ResTexture>(false, "参数fileName为空!","");
		}
		if( StringUtils.isEmpty(type) ){
			return new ResponseEnvelope<ResTexture>(false, "参数type为空!","");
		}
		ResTextureSearch resTextureSearch = new ResTextureSearch();
		ResTextureResponse resTextureResponse = new ResTextureResponse();
		//查询材质贴图(type)标识,根据传的图片名称搜索
		resTextureSearch.setFileName(fileName);
		resTextureSearch.setAtt1(type);
		resTextureSearch.setIsDeleted(0);
		try {
			List<ResTexture> rtList = resTextureService.getPaginatedList(resTextureSearch);
			if( Lists.isNotEmpty(rtList) ){
				resTextureResponse.setPicPath(RESOURCES_ROOT_URL+rtList.get(0).getFilePath());
			}else{
				resTextureResponse.setPicPath("");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<ResTexture>(false, "数据异常!",resTextureSearch.getMsgId());
		}
		return new ResponseEnvelope<>(resTextureResponse, resTextureSearch.getMsgId() ,true);
	}
	
	/**
	 * TODO:将保存res_file表中的材质球文件移动到res_model表
	 * @return
	 */
	@RequestMapping(value = "/fileMoveToModel")
	@ResponseBody
	public Object fileMoveToModel() {
		
		ResFile resFile = new ResFile();
		resFile.setFileKey("system.resTexture.textureBallFile");
		resFile.setIsDeleted(0);
		List<ResFile> resFileList = resFileService.getList(resFile);
		List<Integer> resFileIds = new ArrayList<>();
		if(Lists.isNotEmpty(resFileList)) {
			for(ResFile file : resFileList) {
				ResModel resModel = new ResModel();
				resModel.setSysCode(file.getSysCode());
				resModel.setCreator(file.getCreator());
				resModel.setGmtCreate(file.getGmtCreate());
				resModel.setModifier(file.getModifier());
			    resModel.setGmtModified(file.getGmtModified());
			    resModel.setIsDeleted(file.getIsDeleted());
			    resModel.setModelCode(file.getFileCode());
			    resModel.setModelName(file.getFileName());
			    resModel.setModelFileName(file.getFileOriginalName());
			    resModel.setModelType(file.getFileType());
			    resModel.setModelSize(file.getFileSize());
			    resModel.setModelSuffix(file.getFileSuffix());
			    resModel.setModelLevel(file.getFileLevel());
			    resModel.setModelPath(file.getFilePath());
			    resModel.setModelDesc(file.getFileDesc());
			    resModel.setModelOrdering(file.getFileOrdering());
			    resModel.setFileKey(file.getFileKey());
			    resModel.setFileKeys(file.getFileKeys());
			    resModel.setBusinessIds(file.getBusinessIds());
			    resModel.setAtt4(file.getAtt4());
			    resModel.setAtt5(file.getAtt5());
		  	    resModel.setAtt6(file.getAtt6());
			    resModel.setDateAtt1(file.getDateAtt1());
		 	    resModel.setDateAtt2(file.getDateAtt2());
			    resModel.setBusinessId(file.getBusinessId());
			    resModel.setNumAtt2(file.getNumAtt2());
			    resModel.setNumAtt3(file.getNumAtt3());
			    resModel.setNumAtt4(file.getNumAtt4());
			    resModel.setRemark(file.getRemark());
			    Integer id = resModelService.add(resModel);//把resfile表里的所有 材质球数据 保存到resModel表里
//			    ResTexture resTexture = resTextureService.get(resModel.getBusinessId());
			    //查询到原来的材质球关联的  材质数据
			    ResTexture resTexture = resTextureMapper.selectByPrimaryKey2(resModel.getBusinessId());
			    if(resTexture != null ) {//如果原材质存在就把关联的字段修改为resModel表的新数据的id
			    	resTexture.setTextureBallFileId(id);
				    resTextureService.update(resTexture);
			    }
			    
			    resFileIds.add(file.getId());
			}
			//将原来的res_file表里的材质球文件逻辑删除
			int s = resFileMapper.batchDelete(resFileIds);
			if(s > 0) {
				return "ture";
			}
			
		}else {
			return "resFile表里没有材质球文件的数据";
		}
		
		return "false";
	}
}
