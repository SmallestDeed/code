package com.nork.system.cache;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.nork.common.cache.CacheManager;
import com.nork.common.cache.utils.KeyGenerator;
import com.nork.common.metadata.ModuleType;
import com.nork.common.util.SpringContextHolder;
import com.nork.common.util.collections.CustomerListUtils;
import com.nork.design.service.OptimizePlanService;
import com.nork.system.model.ResDesign;
import com.nork.system.model.ResFile;
import com.nork.system.model.ResHousePic;
import com.nork.system.model.ResModel;
import com.nork.system.model.ResPic;
import com.nork.system.model.ResRenderPic;
import com.nork.system.service.ResDesignService;
import com.nork.system.service.ResFileService;
import com.nork.system.service.ResHousePicService;
import com.nork.system.service.ResModelService;
import com.nork.system.service.ResPicService;
import com.nork.system.service.ResRenderPicService;

/***
 * 系统资源缓存层
 * @author qiu.jun
 * @date 2016-05-10
 */
public class ResourceCacher {
	private static ResFileService resFileService=SpringContextHolder.getBean(ResFileService.class);
    private static ResPicService resPicService=SpringContextHolder.getBean(ResPicService.class);
    private static ResHousePicService resHousePicService=SpringContextHolder.getBean(ResHousePicService.class);
    private static ResModelService resModelService=SpringContextHolder.getBean(ResModelService.class);
    private static ResDesignService resDesignService=SpringContextHolder.getBean(ResDesignService.class);

    private static ResRenderPicService resRenderPicService=SpringContextHolder.getBean(ResRenderPicService.class);
    private static OptimizePlanService optimizeDesingPlanService=SpringContextHolder.getBean(OptimizePlanService.class);

    /***
     * 获取单个文件资源的详情
     * @param id
     * @return
     */
	public static ResFile getFile(int id){
		ResFile pic=null;
		String key=KeyGenerator.getIdKey(ModuleType.ResFile, id);
		if(CacheManager.getInstance().getCacher()!=null){
			pic=(ResFile)CacheManager.getInstance().getCacher().getObject(key);
			if(pic==null){
				pic=resFileService.get(id);
				if(pic!=null){
				    CacheManager.getInstance().getCacher().setObject(key, pic, 0);
				}
			}
		}
		return pic;
	}
	
    /***
     * 获取单个图片资源的详情
     * @param id
     * @return
     */
	public static ResDesign getResDesign(int id){
		ResDesign resDesign = null;
 
		String key=KeyGenerator.getIdKey(ModuleType.ResPic, id);
		if(CacheManager.getInstance().getCacher()!=null){
			resDesign=(ResDesign)CacheManager.getInstance().getCacher().getObject(key);
			if(resDesign==null){
				resDesign=resDesignService.get(id);
				if(resDesign!=null){
				    CacheManager.getInstance().getCacher().setObject(key, resDesign, 0);
				}
			}
		}
		return resDesign;
	}
	 /***
     * 获取单个图片资源的详情
     * @param id
     * @return
     */
	public static ResDesign getResDesign2(int id){
		ResDesign resDesign = null;
 
		String key=KeyGenerator.getIdKey(ModuleType.ResPic, id);
		if(CacheManager.getInstance().getCacher()!=null){
			resDesign=(ResDesign)CacheManager.getInstance().getCacher().getObject(key);
			if(resDesign==null){
				resDesign=optimizeDesingPlanService.get(id);
				if(resDesign!=null){
				    CacheManager.getInstance().getCacher().setObject(key, resDesign, 0);
				}
			}
		}
		return resDesign;
	}
	
	/***
     * 获取单个图片资源的详情
     * @param id
     * @return
     */
	public static ResPic getPic(int id){
		ResPic pic=null;
		String key=KeyGenerator.getIdKey(ModuleType.ResPic, id);
		if(CacheManager.getInstance().getCacher()!=null){
			pic=(ResPic)CacheManager.getInstance().getCacher().getObject(key);
			if(pic==null){
				pic=resPicService.get(id);
				if(pic!=null){
				    CacheManager.getInstance().getCacher().setObject(key, pic, 0);
				}
			}
		}
		return pic;
	
	}
	
 
	
	/***
     * 获取单个渲染图片资源的详情
     * @param id
     * @return
     */
	public static ResRenderPic getResRenderPic(int id){
		ResRenderPic   resRenderPic =null;
		String key=KeyGenerator.getIdKey(ModuleType.ResPic, id);
		if(CacheManager.getInstance().getCacher()!=null){
			resRenderPic=(ResRenderPic)CacheManager.getInstance().getCacher().getObject(key);
			if(resRenderPic==null){
				resRenderPic=resRenderPicService.get(id);
				if(resRenderPic!=null){
				    CacheManager.getInstance().getCacher().setObject(key, resRenderPic, 0);
				}
			}
		}
		return resRenderPic;
	}
	
	

    /***
     * 获取单个图片资源的详情
     * @param id
     * @return
     */
	public static ResHousePic getHousePic(int id){
		ResHousePic pic=null;
		String key=KeyGenerator.getIdKey(ModuleType.ResHousePic, id);
		if(CacheManager.getInstance().getCacher()!=null){
			pic=(ResHousePic)CacheManager.getInstance().getCacher().getObject(key);
			if(pic==null){
				pic= resHousePicService.get(id);
				if(pic!=null){
				    CacheManager.getInstance().getCacher().setObject(key, pic, 0);
				}
			}
		}
		return pic;
	}
	
    /***
     * 获取单个模型资源的详情
     * @param id
     * @return
     */
	public static ResModel getModel(int id){
		ResModel pic=null;
		String key=KeyGenerator.getIdKey(ModuleType.ResModel, id);
		if(CacheManager.getInstance().getCacher()!=null){
			pic=(ResModel)CacheManager.getInstance().getCacher().getObject(key);
			if(pic==null){
				pic=resModelService.get(id);
				if(pic!=null){
				    CacheManager.getInstance().getCacher().setObject(key, pic, 0);
				}
			}
		}
		return pic;
	}
	
	/***
	 * 根据查询条件获取所有图片资源信息
	 * @param resPic
	 * @return
	 */
	public static List<ResPic> getAllPicList(ResPic resPic){
		List<ResPic> lstPic=Lists.newArrayList();
		Map<String, String> map=new HashMap<String, String>();
		map.put("fileKey", resPic.getFileKey());
		map.put("businessId", String.valueOf(resPic.getBusinessId()));
		if(StringUtils.isNotBlank(resPic.getOrders())){
			   map.put("orders", resPic.getOrders().replace(" ", "_"));
		}
		String key=KeyGenerator.getAllListKeyWithMap(ModuleType.ResFile, map);
		lstPic=CacheManager.getInstance().getCacher().getList(ResPic.class, key);
		if(CustomerListUtils.isEmpty(lstPic)){
			lstPic=resPicService.getList(resPic);
			if(CustomerListUtils.isNotEmpty(lstPic)){
				CacheManager.getInstance().getCacher().setObject(key, lstPic, 0);
			}
		}
		return lstPic;
	}
	
	/***
	 * 移除单个文件资源的缓存
	 * @param id
	 */
	public static void removeResFile(int id){
		String key=KeyGenerator.getIdKey(ModuleType.ResFile, id);
		CacheManager.getInstance().getCacher().removeObject(ModuleType.ResFile,key);
	}
	
	/***
	 * 移除单个模型资源的缓存
	 * @param id
	 */
	public static void removeResModel(int id){
		String key=KeyGenerator.getIdKey(ModuleType.ResModel, id);
		CacheManager.getInstance().getCacher().removeObject(ModuleType.ResModel,key);
	}
	
	/***
	 * 移除单个图片资源的缓存
	 * @param id
	 */
	public static void removeResPic(int id){
		String key=KeyGenerator.getIdKey(ModuleType.ResPic, id);
		CacheManager.getInstance().getCacher().removeObject(ModuleType.ResPic,key);
	}
}
