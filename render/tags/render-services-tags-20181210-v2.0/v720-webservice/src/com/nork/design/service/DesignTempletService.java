package com.nork.design.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestParam;

import com.nork.common.model.LoginUser;
import com.nork.design.model.DesignSpaceResult;
import com.nork.design.model.DesignTemplet;
import com.nork.design.model.DesignTempletResult;
import com.nork.design.model.search.DesignTempletSearch;

/**   
 * @Title: DesignTempletService.java 
 * @Package com.nork.design.service
 * @Description:设计模块-设计方案样板房表Service
 * @createAuthor pandajun 
 * @CreateDate 2015-07-05 14:47:35
 * @version V1.0   
 */
public interface DesignTempletService {
	/**
	 * 新增数据
	 *
	 * @param designTemplet
	 * @return  int 
	 */
	public int add(DesignTemplet designTemplet);

	/**
	 *    更新数据
	 *
	 * @param designTemplet
	 * @return  int 
	 */
	public int update(DesignTemplet designTemplet);

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
	 * @return  DesignTemplet 
	 */
	public DesignTemplet get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  designTemplet
	 * @return   List<DesignTemplet>
	 */
	public List<DesignTemplet> getList(DesignTemplet designTemplet);

	/**
	 *    获取数据数量
	 *
	 * @param  designTemplet
	 * @return   int
	 */
	public int getCount(DesignTempletSearch designTempletSearch,int userId);
	public int findAllCode(DesignTempletSearch designTempletSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  designTemplet
	 * @return   List<DesignTemplet>
	 */
	public List<DesignTemplet> getPaginatedList(
				DesignTempletSearch designTemplettSearch,int userId);

	public List<DesignTemplet> getListBySpaceIdsAndArea(List<String> spaceIds);

	
	/**
	 * 空间（样板房）数据
	 * 
	 * @param  designTemplet
	 * @return   List<DesignTemplet>
	 */
	public List<DesignSpaceResult> getSpaceList(DesignTemplet designTemplet);
	
	/**
	 * 空间（样板房）数据
	 * 
	 * @param  designTemplet
	 * @return   List<DesignTemplet>
	 */
	public DesignSpaceResult getSpaceRender(Integer templetId);
	
	/**
	 * 详情
	 * 
	 * @param  designTemplet
	 * @return   List<DesignTemplet>
	 */
	public DesignTempletResult getDesignList(Integer designId);
	
	
	public void backfill(DesignTemplet designTemplet);
	
	public void clearBackfill(DesignTemplet designTemplet);

	public List<String> findAllName();
	
	/**
	 * 所有空房数据
	 * 
	 * @param  designTemplet
	 * @return   List<DesignTemplet>
	 */
	public List<DesignTemplet> getVacantRoomList(DesignTempletSearch designTempletSearch);
	
	/**
	 * 通过编码删除
	 * @param designCode
	 * @return
	 */
	int deleteByCode(String designCode);

	DesignTemplet sysSave(DesignTemplet designTemplet,LoginUser loginUser);

	/**
	 * 根据空间ids得到关联样板房ids
	 * @author huangsongbo
	 * @param spaceIds
	 * @param putawayStates 
	 * @return
	 */
	public List<Integer> getTempletIdsBySpaceIds(List<Integer> spaceIds, List<Integer> putawayStates);
	/**
	 * 根据产品id查找推荐该产品的已上架样板房的code
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	public List<String> getCodeByProductIdFromProductRecommendation(Integer id);
	/**
	 * 根据空间id查找关联的已上架的样板房
	 * @author huangsongbo
	 * @param spaceCommonId
	 * @return
	 */
	public List<String> getTempletCodeBySpaceId(Integer spaceCommonId);
	
	/**
	 * 根据产品id查找关联的所有样板房
	 * @author 
	 * @param productId
	 * @return
	 */
	public List<DesignTemplet> getDesignTempletByProduct(Map map);
	
	/**
	 * 根据产品id查找关联的所有样板房  总条数
	 * @author 
	 * @param productId
	 * @return
	 */
	public int getDesignTempletByProductCount(Map map);

	
	/**
	 *    获取样板房 数据详情  和  空间模型
	 *
	 * @param id
	 * @return  DesignTemplet 
	 */
	public DesignTemplet getV2(Integer integer);
	
	/**
	 * 通过状态查找样板房idList
	 * @author huangsongbo
	 * @param status
	 * @return
	 */
	public List<Integer> findIdListByStatus(int status);
	
	/**
	 * 批量修改样板房状态
	 * @author huangsongbo
	 * @param oldStatus 旧的状态
	 * @param newStatus 新的状态
	 */
	public void updateStatus(int oldStatus, int newStatus);

	/**
	 * 通过编码获取样板房对象
	 * @author xiaoxc
	 * @param templetCode 样板房编码
	 * return DesignTemplet
	 */
	public DesignTemplet getDesignTempletByCode(String templetCode);
	
	/**
	 * 样板房查询接口。根据空间查询出样板房列表
	 * @param spaceCommonIdText
	 * @param msgId
	 * @param limit
	 * @param start
	 * @param loginUser
	 * @return
	 */
	public Object newSpaceDesign(String spaceCommonIdText,String msgId,String limit, String start,LoginUser loginUser);
	/**
	 * 通过样板房编码获取样板房
	 * @param designCode 样板房编码
	 * @return
	 */
	public DesignTemplet selectByTemplateCode(String designCode);

    /**
     * 根据主键查询样板房
     * @param templateId
     * @return
     */
    public DesignTemplet selectByPrimaryKey(Integer templateId);

}
