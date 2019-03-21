package com.nork.design.service;

import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.DesginPlanSpellingflowerRecord;
import com.nork.design.model.search.DesginPlanSpellingflowerRecordSearch;
import com.nork.product.model.BaseProduct;
import com.nork.product.model.SplitTextureDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**   
 * @Title: DesginPlanSpellingflowerRecordService.java 
 * @Package com.nork.design.service
 * @Description:拼花-设计方案瓷砖拼花记录Service
 * @createAuthor roc
 * @CreateDate 2017-11-23 14:41:02
 * @version V1.0   
 */
public interface DesginPlanSpellingflowerRecordService {
	/**
	 * 新增数据
	 *
	 * @param desginPlanSpellingflowerRecord
	 * @return  int 
	 */
	public ResponseEnvelope add(DesginPlanSpellingflowerRecord desginPlanSpellingflowerRecord, LoginUser loginUser, MultipartFile multipartFile);

	/**
	 * 将拼花文件信息保存到数据库中
	 * @param map 上传服务器文件的详细信息
	 * @param sysCode 该资源所关联的表sysCode
	 * @param creator 创建人
	 * @param businessId 该资源所关联的表id
	 * @return
	 */
	public Integer saveSpellingFlowerFile(Map map, String sysCode, String creator, Integer businessId);

	/**
	 * 将拼花文件信息保存到数据库中
	 * @param map 上传服务器文件的详细信息
	 * @param sysCode 该资源所关联的表sysCode
	 * @param creator 创建人
	 * @param businessId 该资源所关联的表id
	 * @return
	 */
	public Integer saveSpellingFlowerFilePic(Map map,String sysCode,String creator,Integer businessId);


	/**
	 *    分页获取数据
	 *
	 * @param  desginPlanSpellingflowerRecordtSearch
	 * @return   List<DesginPlanSpellingflowerRecord>
	 */
	public ResponseEnvelope<DesginPlanSpellingflowerRecord> getPaginatedList(DesginPlanSpellingflowerRecordSearch desginPlanSpellingflowerRecordtSearch,LoginUser loginUser);

	/**
	 * 获取拼花产品信息
	 * @param spellingFlowerProduct 产品ids
	 * @return
	 */
	public Map spellingFlowerData(String spellingFlowerProduct,LoginUser loginUser) throws Exception;

	/**
	 * 获取产品材质信息
	 * @param product
	 * @param splitTextureDTOList
	 */
	public void getSingleTexture(BaseProduct product, List<SplitTextureDTO> splitTextureDTOList);
	/**
	 *    更新数据
	 *
	 * @param desginPlanSpellingflowerRecord
	 * @return  int 
	 */
	public int update(DesginPlanSpellingflowerRecord desginPlanSpellingflowerRecord);

	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	public int delete(Integer id);

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  DesginPlanSpellingflowerRecord 
	 */
	public DesginPlanSpellingflowerRecord get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  desginPlanSpellingflowerRecord
	 * @return   List<DesginPlanSpellingflowerRecord>
	 */
	public List<DesginPlanSpellingflowerRecord> getList(DesginPlanSpellingflowerRecord desginPlanSpellingflowerRecord);

	/**
	 *    获取数据数量
	 *
	 * @param  desginPlanSpellingflowerRecordSearch
	 * @return   int
	 */
	public int getCount(DesginPlanSpellingflowerRecordSearch desginPlanSpellingflowerRecordSearch);



	/**
	 * 其他
	 * 
	 */

}
