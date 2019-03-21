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

import com.nork.client.model.BaseProductResponse;
import com.nork.client.model.DesignTempletResponse;
import com.nork.client.model.ProductResponse;
import com.nork.common.constant.SystemCommonConstant;
import com.nork.common.constant.util.SystemCommonUtil;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.Lists;
import com.nork.design.model.DesignTemplet;
import com.nork.design.model.search.DesignTempletSearch;
import com.nork.design.service.DesignTempletService;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.search.BaseProductSearch;
import com.nork.product.service.BaseProductService;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResPic;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResPicService;


/**   
 * @Title: BaseProductController.java 
 * @Package com.nork.client.controller
 * @Description:产品模块-产品库Controller
 * @createAuthor xiaoxc 
 * @CreateDate 2015-09-14 15:56:37
 * @version V1.0   
 */
@Controller
@RequestMapping("/{style}/client/baseProduct")
public class BaseProductController {
	private static Logger logger = Logger.getLogger(BaseProductController.class);
	
	@Autowired
	private BaseProductService baseProductService;
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private ResFileService resFileService;
	@Autowired
	private DesignTempletService designTempletService;
	
	/**
	 * 产品列表
	 */
	@RequestMapping(value = "/getProductList")
	@ResponseBody
	public Object getProductList( @ModelAttribute("baseProductSearch") BaseProductSearch baseProductSearch) {
		
		List<ProductResponse> list = new ArrayList<ProductResponse>();
		
		ProductResponse productResponse = null;
		
		int total = 0;
		try {
			baseProductSearch.setIsDeleted(0);
			total = baseProductService.selectProCount(baseProductSearch);
			logger.warn("产品总数量："+total);
			if( total > 0 ){
				List<BaseProduct> proList = baseProductService.selectProPaginatedList(baseProductSearch);
				for(BaseProduct baseProduct : proList){
					productResponse = new ProductResponse();
					//产品配置文件路径
					ResFile res = resFileService.get(baseProduct.getConfigId());
					logger.info("baseProduct.getConfigId()--------->"+baseProduct.getConfigId());
					logger.info("res.getFileName()--------->"+res.getFileName());
					productResponse.setFileName(res.getFileName());
					list.add(productResponse);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<CategoryProductResult>(false, "数据异常!",baseProductSearch.getMsgId());
		}
		return new ResponseEnvelope<ProductResponse>(total, list,baseProductSearch.getMsgId());
	}
	
	/**
	 * 验证产品编码
	 */
	@RequestMapping(value = "/verifyProductCode")
	@ResponseBody
	public Object verifyProductCode(String productCode,String msgId,HttpServletRequest request, HttpServletResponse response) {
		if(StringUtils.isEmpty(productCode)){
			return new ResponseEnvelope<BaseProduct>(false,"产品编码为空！",msgId);
		}
		BaseProduct baseProduct = new BaseProduct();
		baseProduct.setProductCode(productCode);
		baseProduct.setIsDeleted(0);
		try {
			List<BaseProduct> list = baseProductService.getList(baseProduct);
			if(Lists.isNotEmpty(list)){
				BaseProduct product = list.get(0);
				if(product.getFbxFileId() != null && product.getFbxFileId() > 0){
					return new ResponseEnvelope<BaseProduct>(true,"exist",msgId);
				}else{
					return new ResponseEnvelope<BaseProduct>(true,"noExist",msgId);
				}
			}else{
				return new ResponseEnvelope<BaseProduct>(false,"false",msgId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<CategoryProductResult>(false, "数据异常!",msgId);
		}
	}
	
	/**
	 * 获取单个产品Fbx文件和fbx贴图
	 */
	@RequestMapping(value = "/getProductFbxList")
	@ResponseBody
	public Object getProductFbx(@ModelAttribute("baseProductSearch") BaseProductSearch baseProductSearch) {
		List<BaseProductResponse> list = new ArrayList<BaseProductResponse> ();
		BaseProductResponse baseProductResponse = null;
		int total = 0;
		try {
			baseProductSearch.setIsDeleted(0);
			total = baseProductService.selectBaseCount(baseProductSearch);
			logger.warn("产品总数量："+total);
			if( total > 0 ){
				List<BaseProduct> proList = baseProductService.getList(baseProductSearch);
				for(BaseProduct product : proList){
					baseProductResponse = new BaseProductResponse();
					baseProductResponse.setProductCode(product.getProductCode());
					if(product.getFbxFileId() != null && product.getFbxFileId() > 0){
						ResFile resFile = resFileService.get(product.getFbxFileId());
						baseProductResponse.setFbxPath(resFile==null?"":resFile.getFilePath());
						baseProductResponse.setProductName(product.getProductName());
						baseProductResponse.setSmallPicPath(product.getPicPath());
					}
					List<String> textureList = new ArrayList<>();
					String textures = product.getFbxTexture();
					for(String picid : textures.split(",")){
						ResPic resPic = resPicService.get(Utils.getIntValue(picid));
						textureList.add(resPic==null?"":resPic.getPicPath());
					}
					baseProductResponse.setFbxTextureList(textureList);
					list.add(baseProductResponse);
				}
			}else{
				return new ResponseEnvelope<BaseProductResponse>(false,"找不到该编码产品",baseProductSearch.getMsgId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<BaseProductResponse>(false, "数据异常!",baseProductSearch.getMsgId());
		}
		return new ResponseEnvelope<BaseProductResponse>(total,list,baseProductSearch.getMsgId());
	}
	
	/**
	 * 获取单个产品Fbx文件和fbx贴图
	 */
	@RequestMapping(value = "/getDesignTempletFbx")
	@ResponseBody
	public Object getDesignTempletFbx(@ModelAttribute("designTempletSearch") DesignTempletSearch designTempletSearch,HttpServletRequest request, HttpServletResponse response) {
		
		List<DesignTempletResponse> list = new ArrayList<DesignTempletResponse>();
		
		LoginUser loginUser = SystemCommonUtil.getLoginUserFromSession(request);
        if (loginUser==null) {
            ResponseEnvelope envelope = new ResponseEnvelope();
            envelope.setSuccess(false);
            envelope.setMessage(SystemCommonConstant.PLEASE_LOGIN);
            envelope.setMsgId(designTempletSearch.getMsgId());
            return envelope;
        }
		DesignTempletResponse designTempletResponse = null;
		int total = 0;
		try {
			designTempletSearch.setIsDeleted(0);
			total = designTempletService.getCount(designTempletSearch,loginUser.getId());
			logger.warn("产品总数量："+total);
			if( total > 0 ){
				
				List<DesignTemplet> proList = designTempletService.getList(designTempletSearch);
				
				for(DesignTemplet templet : proList){
					designTempletResponse = new DesignTempletResponse();
					designTempletResponse.setProductCode(templet.getDesignCode());
					
					if(templet.getConfigFileId() != null && templet.getConfigFileId() > 0){
						ResFile resFile = resFileService.get(templet.getConfigFileId());
						designTempletResponse.setFbxPath(resFile==null?"":resFile.getFilePath());
						designTempletResponse.setProductName(templet.getDesignName());
						designTempletResponse.setSmallPicPath(templet.getPicPath());
					}
					List<String> texturesList = new ArrayList<String>();
					String textures = templet.getFbxTexture();
					for(String picId : textures.split(",")){
						ResPic resPic = resPicService.get(Utils.getIntValue(picId));
						texturesList.add(resPic==null?"":resPic.getPicPath());
					}
					designTempletResponse.setFbxTextureList(texturesList);
					list.add(designTempletResponse);
				}
				
			}else{
				return new ResponseEnvelope<DesignTempletResponse>(false,"找不到该编码产品",designTempletSearch.getMsgId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEnvelope<DesignTempletResponse>(false, "数据异常!",designTempletSearch.getMsgId());
		}
		return new ResponseEnvelope<DesignTempletResponse>(total,list,designTempletSearch.getMsgId());
	}
	
	
}
