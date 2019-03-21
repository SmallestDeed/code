package com.nork.system.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nork.common.model.LoginUser;
import com.nork.common.util.Utils;
import com.nork.product.dao.BaseProductMapper;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.CategoryProductResult;
import com.nork.product.model.SplitTextureDTO;
import com.nork.product.model.SplitTextureInfoDTO;
import com.nork.product.service.BaseProductService;
import com.nork.system.dao.ModelAreaRelMapper;
import com.nork.system.dao.ModelAreaTextureRelMapper;
import com.nork.system.model.*;
import com.nork.system.model.po.ResTexturePo;
import com.nork.system.service.ModelAreaRelService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResTextureService;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author chenqiang
 * @Description
 * @Date 2018/8/7 0007 11:12
 * @Modified By
 */
@Service("modelAreaRelService")
public class ModelAreaRelServiceImpl implements ModelAreaRelService {
	private static org.slf4j.Logger logger  = LoggerFactory.getLogger(ModelAreaRelServiceImpl.class);

	@Autowired
	private ModelAreaRelMapper modelAreaRelMapper;

	@Autowired
	private ModelAreaTextureRelMapper modelAreaTextureRelMapper;

	@Autowired
	private BaseProductService baseProductService;

	@Autowired
	private BaseProductMapper baseProductMapper;

	@Autowired
	private ResTextureService resTextureService;

	@Autowired
	private ResModelService resModelService;

	@Autowired
	private ResPicService resPicService;

	/**
	 * 获取产品 多维材质 详情
	 * @author chenqiang
	 * @param baseProductId			产品id
	 * @param modelId				产品模型id
	 * @param type					获取材质默认(choose)、全部（info）
	 * @param modelType             是否使用模型获取材质信息：1:使用
	 * @return
	 * @date 2018/8/17 0017 14:37
	 */
	public Map<String, Object> dealWithSplitTextureInfo(Integer baseProductId , Integer modelId, String type,String modelType){

		/**--------变量声明--------*/
		//返回对象Map
		Map<String,Object> resultMap = null;
		//材质信息返回对象
		List<SplitTextureDTO> splitTexturesChooseList = new ArrayList<>();
		//模型区域对象
		List<ModelAreaRel> modelAreaRelList = null;
		//模型区域对象对应材质对象
		List<ModelAreaTextureRel> modelAreaTextureRelList = null;
		//模型区域对象对应材质对象处理Map
		Map<String,String> modelAreaMap = null;
		//材质单、多标识
		Integer isSplit = 0;
		//产品对应对象
		//CategoryProductResult categoryProductResult = null;


		/**----------获取对应产品信息----------*/
		List<Integer> idList = new ArrayList<>();
		idList.add(baseProductId);

		//根据idList查询产品详情
//		List<CategoryProductResult> datalist = baseProductMapper.getDetailByIds(idList, null, null, null);
		//判断获取对象
//		if(null == datalist || datalist.size() == 0)
//			throw  new RuntimeException("产品不存在id="+baseProductId);
//		categoryProductResult = datalist.get(0);

		BaseProduct baseProduct = baseProductService.get(baseProductId);
		if (null == baseProduct) {
			resultMap = new HashMap<>();
			resultMap.put("isSplit",isSplit);
			resultMap.put("splitTexturesChooseList",splitTexturesChooseList);
			return resultMap;
		}

		/**-----------------------材质信息获取-----------------------*/
		//判断该产品是否为多维材质
		if (StringUtils.isNotBlank(baseProduct.getSplitTexturesInfo())) {

			//判断modelId参数是佛存在、不存在根据产品id查询到windows模型id
			if(null == modelId && "1".equals(modelType))
				modelId = baseProduct.getWindowsU3dModelId();

			//判断模型id不为空，先走新逻辑
			if(null != modelId && "1".equals(modelType)){

				//查询模型材质区域
				modelAreaRelList = modelAreaRelMapper.selectListByModel(modelId);
				if(null != modelAreaRelList && modelAreaRelList.size() > 0 ){

					//获取区域对应材质
					List<Integer> areaIdList  = modelAreaRelList.stream().map(modelAreaRel -> modelAreaRel.getId()).collect(Collectors.toList());
					modelAreaTextureRelList =  modelAreaTextureRelMapper.selectListByModelAreaIdList(areaIdList,null);

					modelAreaMap = new HashMap<>();
					for (ModelAreaTextureRel modelAreaTextureRel : modelAreaTextureRelList) {

						//判断当前材质是否为默认材质
						if(null != modelAreaTextureRel.getIsDefault() && 1 == modelAreaTextureRel.getIsDefault().intValue()){

							//默认
							modelAreaMap.put(modelAreaTextureRel.getAreaId()+"_d",modelAreaTextureRel.getTextureId()+"");

						}else{

							//非默认
							String textureIds = modelAreaMap.get(modelAreaTextureRel.getAreaId());
							if(StringUtils.isNotBlank(textureIds))
								modelAreaMap.put(modelAreaTextureRel.getAreaId(),textureIds + "," + modelAreaTextureRel.getTextureId());
							else
								modelAreaMap.put(modelAreaTextureRel.getAreaId(),modelAreaTextureRel.getTextureId() + "");
						}
					}

				}
			}

			/**---------判断模型===对应数据是否存在：存在走新逻辑---------*/
			if(null != modelAreaRelList && modelAreaTextureRelList != null
					&& modelAreaRelList.size() > 0 && modelAreaTextureRelList.size() > 0){

				//材质标识
				isSplit = new Integer(1);

				//调取根据模型获取材质信息方法
				splitTexturesChooseList( modelAreaRelList,modelAreaMap,type,baseProductId,splitTexturesChooseList);

			}else{

				//材质标识
				isSplit = new Integer(1);

				//多维材质 老逻辑
//				baseProductService.dealWithSplitTextureInfo(baseProductNew.getId(), baseProductNew.getSplitTexturesInfo(),"info");
				dealWithSplitTextureInfo(baseProductId, baseProduct.getSplitTexturesInfo(), type,splitTexturesChooseList);

			}

		}else{

			/**-------单材质-------*/

			//材质标识
			isSplit = new Integer(0);

			//获取多材质信息详情
			// 2018-11-13 edit by zhangwj TODO：V20181113 现在多了一石多面的产品（硬装贴图产品不仅限于单材质了，需要改一下处理材质的代码）
//			SplitTextureDTO splitTextureDTO = resTextureToSplitTextureDTO(categoryProductResult);
			List<SplitTextureDTO> splitTextureDTOList = productMaterialHandler(baseProduct);

			//放入材质返回对象中
//			splitTexturesChooseList.add(splitTextureDTO);
			if (splitTextureDTOList != null) {
				splitTexturesChooseList.addAll(splitTextureDTOList);
			}
			// TODO: V20181113 end
		}

		/**--------赋值Map返回数据--------*/
		resultMap = new HashMap<>();
		resultMap.put("isSplit",isSplit);
		resultMap.put("splitTexturesChooseList",splitTexturesChooseList);
		return resultMap;
	}

	/**
	 * 处理多材质
	 * @param baseProduct
	 * @return
	 */
	public List<SplitTextureDTO> productMaterialHandler(BaseProduct baseProduct){
		List<SplitTextureDTO> list = new ArrayList<>();
		SplitTextureDTO splitTextureDTO = null;
		//判断该产品是否存在默认材质
		if (StringUtils.isNotBlank(baseProduct.getMaterialPicIds())) {
			List<String> productMaterialPicIdList = Arrays.asList(baseProduct.getMaterialPicIds().split(","))
					.stream().filter(materialPicId -> ( StringUtils.isNotBlank(materialPicId) && !"-1".equals(materialPicId) && !"0".equals(materialPicId) )).collect(Collectors.toList());

			if( productMaterialPicIdList == null || productMaterialPicIdList.size() == 0  ){
				return null;
			}
			for (String productMaterialPicId : productMaterialPicIdList) {
				//获取默认材质对象
				ResTexturePo resTexture = resTextureService.getTextureInfoById(Integer.valueOf(productMaterialPicId));
				if (null == resTexture) {
					continue;
				}
				/**-------单材质产品-------*/
				//材质区域对象
				splitTextureDTO = new SplitTextureDTO("1", "", null);

				//材质区域对象对应材质集合
				List<SplitTextureDTO.ResTextureDTO> resTextureDTOList = new ArrayList<>();

				//材质集合对象
				SplitTextureDTO.ResTextureDTO resTextureDTO = splitTextureDTO.new ResTextureDTO(
						Integer.valueOf(productMaterialPicId),
						resTexture.getTexturePicPath(),
						resTexture.getTextureAttrValue(),
						resTexture.getFileHeight(),
						resTexture.getFileWidth(),
						resTexture.getLaymodes(),
						resTexture.getMaterialPath(),
						resTexture.getNormalParam(),
						resTexture.getNormalPath());
				resTextureDTO.setKey("1");
				resTextureDTO.setProductId(baseProduct.getId());

				//赋值到材质集合
				resTextureDTOList.add(resTextureDTO);

				//赋值到材质区域对象
				splitTextureDTO.setList(resTextureDTOList);
				list.add(splitTextureDTO);
			}
		}
		return list;
	}

	/**
	 * 根据模型获取材质信息详情
	 * @author chenqiang
	 * @param modelAreaRelList			模型区域对象
	 * @param modelAreaMap				模型区域对象对应材质详情Map
	 * @param type						获取类型
	 * @param baseProductId				产品id
	 * @param splitTexturesChooseList	返回list
	 * @return
	 * @date 2018/8/17 0017 16:31
	 */
	private void splitTexturesChooseList(List<ModelAreaRel> modelAreaRelList,Map<String,String> modelAreaMap,String type,Integer baseProductId,List<SplitTextureDTO> splitTexturesChooseList){

		for (ModelAreaRel modelAreaRel : modelAreaRelList) {

			//材质区域对应材质信息集合
			List<SplitTextureDTO.ResTextureDTO> resTextureDTOList = new ArrayList<>();

			//材质区域对象
			SplitTextureDTO splitTextureDTO = modelAreaRel.getSplitTextureDTO();

			//取 区域 默认材质
			if("choose".equals(type)){

				//获取默认材质id
				String textureIds = modelAreaMap.get(modelAreaRel.getId()+"_d");
				Integer defaultId = StringUtils.isNotBlank(textureIds) ? Integer.parseInt(textureIds) : 0;

				//判断是否存在
				if (defaultId != null && defaultId > 0) {

					//获取材质信息
					ResTexture resTexture = resTextureService.get(defaultId);

					//转换材质信息
					SplitTextureDTO.ResTextureDTO resTextureDTO = resTextureToResTextureDTO(resTexture,splitTextureDTO.getKey(),baseProductId);

					//存入材质信息集合
					resTextureDTOList.add(resTextureDTO);
				}


				//放入材质区域对象
				splitTextureDTO.setList(resTextureDTOList);

			}


			//取 区域全部材质
			if("info".equals(type)){

				/**--------获取多维材质所有材质id、默认材质第一位--------*/

				//材质id
				String textureIdD = modelAreaMap.get(modelAreaRel.getId()+"_d");			//默认材质id
				String textureIds =  modelAreaMap.get(modelAreaRel.getId()+"");				//其余id

				//获取默认材质对象
				ResTexture resTextureD = null;
				if(StringUtils.isNotBlank(textureIdD))
					resTextureD = resTextureService.get(Integer.parseInt(textureIdD));

				//获取其余材质id集合
				List<String> textureIdStrList = null;
				if(StringUtils.isNotBlank(textureIds))
					textureIdStrList = Arrays.asList(textureIds.split(","));

				//获取其余材质对象
				List<ResTexture> textureList = null;
				if(null != textureIdStrList && textureIdStrList.size() > 0){

					//获取材质集合
					ResTexture resTextureSearch = new ResTexture();
					resTextureSearch.setResTextureIds(textureIdStrList);
					textureList = resTextureService.getBatchGet(resTextureSearch);

				}

				//判断是否存在其他材质对象
				if(null == textureList)
					textureList = new ArrayList<>();


				//默认材质第一
				textureList.add(0,resTextureD);


				/**-----------循环转换-----------*/
				if(null != textureList && textureList.size() > 0){
					for (ResTexture resTexture : textureList) {

						if(resTexture == null)
							continue;

						//转换材质信息
						SplitTextureDTO.ResTextureDTO resTextureDTO = resTextureToResTextureDTO(resTexture,splitTextureDTO.getKey(),baseProductId);

						//存入材质信息集合
						resTextureDTOList.add(resTextureDTO);

					}
				}

				//放入材质区域对象
				splitTextureDTO.setList(resTextureDTOList);
			}


			/**------放入产品材质区域返回集合中------*/
			splitTexturesChooseList.add(splitTextureDTO);
		}

	}

	/**
	 * 获取单材质返回区域对象
	 * @author chenqiang
	 * @return 单材质返回区域对象
	 * @date 2018/8/17 0017 16:34
	 */
	private SplitTextureDTO resTextureToSplitTextureDTO(BaseProduct baseProduct){

		SplitTextureDTO splitTextureDTO = null;

		//判断该产品是否存在默认材质
		if (StringUtils.isNotBlank(baseProduct.getMaterialPicIds())) {

			/**-----材质球文件路径-------*/
			String materialPath = "";
			/**-----法线参数-----*/
			String normalParam = "";
			/**-----材质法线贴图路径-----*/
			String normalPath = "";

			String texturePath = "";
			Integer textureAttrValue = null;
			Integer textureHeight = null;
			Integer textureWidth = null;
			String laymodes = "";


			//获取默认材质对象
			ResTexture resTexture = resTextureService.get(Integer.valueOf(baseProduct.getMaterialPicIds()));

			//判断是否存在材质球
			if (resTexture != null && resTexture.getTextureBallFileId() != null) {

				//获取材质球
				ResModel textureBallModel = resModelService.get(resTexture.getTextureBallFileId());

				if (textureBallModel != null)
					materialPath = textureBallModel.getModelPath();			//路径

			}

			//材质法线贴图
			if (resTexture != null && resTexture.getNormalPicId() != null) {

				//获取材质法线参数
				normalParam = resTexture.getNormalParam();

				//获取材质法线贴图对象
				ResPic normalPic = resPicService.get(resTexture.getNormalPicId());
				if (normalPic != null)
					normalPath = normalPic.getPicPath();		//路径

			}

			if (null != resTexture) {
				ResPic resPic = resPicService.get(resTexture.getPicId() != null ? resTexture.getPicId() : 0);
				texturePath = resPic != null ? resPic.getPicPath() : "";
				textureAttrValue = resTexture.getTextureAttrValue();
				textureHeight = resTexture.getFileHeight();
				textureWidth = resTexture.getFileWidth();
				laymodes = resTexture.getLaymodes();
			}


			/**-------单材质产品-------*/

			//材质区域对象
			splitTextureDTO = new SplitTextureDTO("1", "", null);

			//材质区域对象对应材质集合
			List<SplitTextureDTO.ResTextureDTO> resTextureDTOList = new ArrayList<>();

			//材质集合对象
			SplitTextureDTO.ResTextureDTO resTextureDTO = splitTextureDTO.new ResTextureDTO(
					Integer.valueOf(baseProduct.getMaterialPicIds()),
					texturePath,
					textureAttrValue,
					textureHeight != null ? (int) Double.parseDouble(textureHeight + "") : null,
					textureWidth != null ? (int) Double.parseDouble(textureWidth + "") : null,
					laymodes, materialPath, normalParam, normalPath);
			resTextureDTO.setKey("1");
			resTextureDTO.setProductId(baseProduct.getId());

			//赋值到材质集合
			resTextureDTOList.add(resTextureDTO);

			//赋值到材质区域对象
			splitTextureDTO.setList(resTextureDTOList);

		}

		return splitTextureDTO;
	}

	/**
	 * 根据产品多维材质JSON获取材质信息
	 * @author chenqiang
	 * @param baseProductId				产品id
	 * @param splitTexturesInfo			产品多维材质JSON
	 * @param type						获取类型：choose默认、info全部
	 * @param splitTexturesChooseList	返回区域对象集合
	 * @return
	 * @date 2018/8/18 0018 11:33
	 */
	private void dealWithSplitTextureInfo(Integer baseProductId, String splitTexturesInfo, String type,List<SplitTextureDTO> splitTexturesChooseList) {

		try {

			//根据JSON获取集合对象
			List<SplitTextureInfoDTO> SplitTextureList = new Gson().fromJson(splitTexturesInfo,new TypeToken<List<SplitTextureInfoDTO>>(){}.getType());

			//判断是否存在多维材质集合
			if (SplitTextureList != null && SplitTextureList.size() >= 1) {

				//循环处理多维材质信息
				for (SplitTextureInfoDTO splitTextureInfoDTO : SplitTextureList) {

					//获取区域对象
					SplitTextureDTO splitTextureDTO = splitTextureInfoDTO.getSplitTextureDTO();

					//创建区域对象对应材质对象集合
					List<SplitTextureDTO.ResTextureDTO> resTextureDTOList = new ArrayList<SplitTextureDTO.ResTextureDTO>();

					/**-------根据获取类型获取产品材质-------*/

					//默认材质
					if (StringUtils.equals("choose", type)) {

						//获取默认材质id
						Integer defaultId = splitTextureInfoDTO.getDefaultId();

						//判断是否存在默认材质id
						if (defaultId != null && defaultId > 0) {

							//获取默认材质信息
							ResTexture resTexture = resTextureService.get(defaultId);

							//判断是否存在材质对象
							if(null != resTexture){

								//转换材质信息，获取材质对象
								SplitTextureDTO.ResTextureDTO resTextureDTO = resTextureToResTextureDTO(resTexture,splitTextureInfoDTO.getKey(),baseProductId);

								//放入材质集合对象
								resTextureDTOList.add(resTextureDTO);
							}

						}

						//设值材质区域对象
						splitTextureDTO.setList(resTextureDTOList);
					}


					//全部材质
					if (StringUtils.equals("info", type)) {

						//获取多维材质ids
						String textureIdsStr = splitTextureInfoDTO.getTextureIds();
						//获取默认材质id
						Integer defaultId = splitTextureInfoDTO.getDefaultId();

						//获取集合ids
						List<String> textureIdStrList = Utils.getListFromStr(textureIdsStr);	//全部材质ids集合
						List<String> idList = new ArrayList<>();								//去除默认材质id的ids集合

						//判断ids查询集合是否存在
						if (textureIdStrList != null && textureIdStrList.size() > 0) {
							//存在去除默认材质id
							for (String s : textureIdStrList) {
								if(s.equals(defaultId+""))
									continue;
								idList.add(s);
							}
						}


						//获取默认材质对象与其他材质对象
						ResTexture resTextureD = resTextureService.get(defaultId);
						ResTexture resTextureSearch = new ResTexture();
						List<ResTexture> textureList = null;
						if (null != idList && idList.size() > 0){
							resTextureSearch.setResTextureIds(idList);
							textureList = resTextureService.getBatchGet(resTextureSearch);
						}

						//判断对象是否存在、并将默认材质，放置第一位
						if(null != resTextureD && null != textureList && textureList.size() > 0){

							//默认材质第一
							textureList.add(0,resTextureD);


							//循环处理材质，得到返回材质对象
							for (ResTexture resTexture : textureList) {
								if(resTexture == null)
									continue;

								SplitTextureDTO.ResTextureDTO resTextureDTO = resTextureToResTextureDTO(resTexture,splitTextureInfoDTO.getKey(),baseProductId);

								resTextureDTOList.add(resTextureDTO);
							}

						}

						//设值材质区域对象
						splitTextureDTO.setList(resTextureDTOList);
					}

					/**--------放入返回材质区域对象集合--------*/
					splitTexturesChooseList.add(splitTextureDTO);
				}

			}

		}catch(Exception e){
			logger.error("------baseProduct的SplitTextureInfo信息格式错误,SplitTextureInfo:" + splitTexturesInfo+"{}",e);
		}

	}


	/**
	 * 根据材质信息转换为所需返回材质对象
	 * @author chenqiang
	 * @param resTexture			材质对象
	 * @param key					材质对所需key
	 * @param baseProductId			产品id
	 * @return
	 * @date 2018/8/17 0017 16:32
	 */
	private SplitTextureDTO.ResTextureDTO resTextureToResTextureDTO(ResTexture resTexture,String key,Integer baseProductId){

		SplitTextureDTO.ResTextureDTO resTextureDTO = null;

		if (resTexture != null && resTexture.getId() != null && resTexture.getId() > 0) {
			resTextureDTO = resTextureService.fromResTexture(resTexture);
			resTextureDTO.setKey(key);
			resTextureDTO.setProductId(baseProductId);
			if (resTextureDTO.getTextureWidth() == null || resTextureDTO.getTextureWidth() == 0) {
				resTextureDTO.setTextureWidth(80); 				//徐扬确认。如果材质长度为空/0则给默认80
			}
			if (resTextureDTO.getTextureHeight() == null || resTextureDTO.getTextureHeight() == 0) {
				resTextureDTO.setTextureHeight(80); 			//徐扬确认。如果材质长度为空/0则给默认80
			}
		}

		return resTextureDTO;
	}
}
