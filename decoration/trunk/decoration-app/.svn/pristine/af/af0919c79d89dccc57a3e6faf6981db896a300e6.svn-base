package com.nork.system.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.nork.common.model.LoginUser;
import com.nork.design.model.DesignPlan;
import com.nork.system.model.ResDesign;
 

public interface ResDesignService {

	public int add(ResDesign resDesign);
	
	public int update(ResDesign resDesign);
	
	public int delete(Integer id);

	int physicallyDelete(Integer id);

	public ResDesign get(Integer id);
	
	public ResDesign getByOldId(Integer OldId);
	
	public List<ResDesign> getList(ResDesign resDesign);
	
	public int getCount(ResDesign resDesign);
	public int filePathCount(String filePath);
	
	/*public int getCount(ResFileSearch resFileSearch);*/
	
	/**
	 *    分页获取数据
	 *
	 * @param  resFile
	 * @return   List<ResFile>
	 */
	/*public List<ResFile> getPaginatedList(ResFileSearch resFiletSearch);*/
	//创建设计方案配置文件数据
	public boolean saveResDesign(DesignPlan designPlan, Map map);

	/**
	 * 根据resDesign数据,copy物理文件,并且保存数据(ResDesign)
	 * @author huangsongbo
	 * @param resDesign
	 * @param id
	 * @return
	 */
	public long createFileAndAddDate(ResDesign resDesign, long id);

	/**
	 * describe: 批量删除配置文件
	 * creat_user: yanghz
	 * creat_date: 2017-07-25
	 * creat_time: 下午 06:25
	 **/
	void batchDelTempDesignConfig(List<Integer> delConfigList);

	/**
	 * create ResDesign
	 * 
	 * @author huangsongbo
	 * @param file
	 * @param fileKey
	 * @param code
	 * @param username
	 * @param businessId
	 * @param opType 
	 * @return
	 */
	public Integer createResDesignByFile(File file, String fileKey, String code,
			String username, Integer businessId, Integer opType);

	
	/**
	 * 将拼花文件信息保存到数据库中
	 * @author zhaobl
	 * @param designPlan
	 * @param map
	 * @return
	 */
	public boolean saveSpellingFlowerFile(DesignPlan designPlan, Map<String, String> map,String isRenderServe,String opType);


	List<ResDesign> getSpellingFlowerFileTo();

	/**
	 * 保存配置文件 通用
	 * @auth xiaoxc_20180828
	 * @param uploadPath
	 * @param content
	 * @param loginUser
	 * @param businessId
	 * @param fileType
	 * @return
	 */
	Integer saveFile(String uploadPath, String content, LoginUser loginUser, Integer businessId, String fileType);

	/**
	 * 更新配置文件
	 * @param resDesign
	 * @param content
	 * @param loginUser
	 * @return
	 */
	Integer updateFile(ResDesign resDesign, String content, LoginUser loginUser);
}
