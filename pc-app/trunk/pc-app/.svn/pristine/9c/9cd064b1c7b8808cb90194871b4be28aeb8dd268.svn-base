package com.nork.system.service;

import java.util.List;

import com.nork.common.exception.BizException;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.design.model.ResRenderPicQO;
import com.nork.design.model.ThumbData;
import com.nork.system.model.ResRenderPic;
import com.nork.system.model.po.ResRenderPicPo;
import com.nork.system.model.query.ResRenderPicQuery;
import com.nork.system.model.search.ResRenderPicSearch;
import com.nork.system.model.small.ResScreenPicData;
import com.nork.system.model.vo.RenderPicOpenCVResult;
import org.apache.ibatis.annotations.Param;

/**   
 * @Title: ResRenderPicService.java 
 * @Package com.nork.system.service
 * @Description:渲染图片资源库-渲染图片资源库Service
 * @createAuthor pandajun 
 * @CreateDate 2017-03-22 14:39:08
 * @version V1.0   
 */
public interface ResRenderPicService {
	public int countRoamByFileKey(ResRenderPicSearch resRenderPicSearch);
	/**
	 * 新增数据
	 *
	 * @param resRenderPic
	 * @return  int 
	 */
	public int add(ResRenderPic resRenderPic);
	
	
	/**
	 * 新增数据
	 *
	 * @param resRenderPic
	 * @return  int 
	 */
	public int addNew(ResRenderPic resRenderPic);
	
	/**
	 * 新增效果图 渲染信息
	 * @author chenqiang
	 * @param resRenderPic 新增对象
	 * @return 主键id
	 * @date 2018/8/10 0010 15:14
	 */
	int addDesignRender(ResRenderPic resRenderPic);

	/**
	 *    更新数据
	 *
	 * @param resRenderPic
	 * @return  int 
	 */
	public int update(ResRenderPic resRenderPic);

	/**
	 * 根据id集合与sysTaskId 批量更改
	 * @author chenqiang
	 * @param sysTaskId
	 * @param idList
	 * @return
	 * @date 2018/8/10 0010 17:09
	 */
	int updateSysTaskId(Integer sysTaskId,List<Integer> idList);

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
	 * @return  ResRenderPic 
	 */
	public ResRenderPic get(Integer id);

	/**
	 * 所有数据
	 * 
	 * @param  resRenderPic
	 * @return   List<ResRenderPic>
	 */
	public List<ResRenderPic> getList(ResRenderPic resRenderPic);

	/**
	 *    获取数据数量
	 *
	 * @param  resRenderPic
	 * @return   int
	 */
	public int getCount(ResRenderPicSearch resRenderPicSearch);

	/**
	 *    分页获取数据
	 *
	 * @param  resRenderPic
	 * @return   List<ResRenderPic>
	 */
	public List<ResRenderPic> getPaginatedList(
				ResRenderPicSearch resRenderPictSearch);

	/**
	 * 其他
	 * 
	 */
	
	/**
     * 根据推荐方案Id和渲染类型获得最新渲染原图
     */
	public ResRenderPic getResRenderPicByPlanRecommended(Integer planRecommendedId,Integer renderingType);
	
	
	/**
	 *查询所有的缩略图和封面图 
	 */
	public List<ResRenderPic> selectListByFileKeys(ResRenderPicQO resRenderPicQO);

	/**
	 *
	 * getLatestSmallPic通过设计方案id获得最新的一条渲染缩略图id

	 * @param renderPic
	 * @return int    返回类型
	 * @Exception 异常对象
	 * @since  CodingExample　Ver(编码范例查看) 1.1
	 */
    public int getLatestSmallPic(ResRenderPic renderPic);

	
	public List<ResRenderPic> getRenderResourceListByopInfo(Integer sourcePlanId, Integer templateId, Integer renderType);

	/**
	 * 通过pid获取缩略图
	 * @param pid
	 * @return
	 */
	ResRenderPic selectOneByPid(Integer pid);

	/**
	 * 删除副本关联的渲染图
	 * @param sceneId 设计方案副本id
	 */
	public void deletePicBySceneId(Integer sceneId);

	/**
	 * describe: 更新原图所有原图根据缩略图id
	 * creat_user: yanghz
	 * creat_date: 2017-07-22
	 * creat_time: 上午 01:25
	 *
	 * @param resId
	 * @param resId*/
	void updateOrigPicBySmallPicId(ResRenderPic render);

	/**
	 * describe: 根据所缩略图id获取截图id
	 * creat_user: yanghz
	 * creat_date: 2017-07-22
	 * creat_time: 上午 01:55
	 **/
	public int getTaskIdBySmallId(int smallId);

	/**
	 * describe: 根据截图id更新场景id
	 * creat_user: yanghz
	 * creat_date: 2017-07-22
	 * creat_time: 上午 01:57
	 **/
	public void updateSceneIdByTaskId(int sceneId, int taskId);

	/**
	 * describe: 获取图片浏览地址信息
	 * creat_user: yanghz
	 * creat_date: 2017-07-27
	 * creat_time: 下午 02:00
	 **/
	String getQRCodeInfo(ResRenderPic resPic, LoginUser loginUser);

	String getQRpicPath(ResRenderPic resPic, LoginUser loginUser);

	/**
	 * 通过推荐id 渲染图
	 * @param planRecommendedId
	 */
	public void deletedByPlanRecommendedId(int planRecommendedId);
	
	/**
	 * describe: 根据场景副本id查询该方案下所有缩略图
	 * creat_user: yanghz
	 * creat_date: 2017-08-01
	 * creat_time: 上午 11:33
	 * @param sceneId 场景id
	 **/
	public List<ThumbData> getRenderPicIdsBySceneId(long sceneId);
	
	
	public int insertSelective(ResRenderPic resRenderPic);

	/**
	 * 获取720渲染数据
	 * @param query
	 * @return
	 */
	List<ResRenderPicPo> findHistoryData(ResRenderPicQuery query) throws BizException;


	/**
	 * 720渲染图片切片
	 * @return
	 */
	ResponseEnvelope repairRecommendedPlanV720HistoryData(String recommendedPlanId);

	/**
	 * 根据主键id 参选 渲染信息集合
	 * @author chenqiang
	 * @param idList 主键集合
	 * @return 返回渲染集合
	 * @date 2018/8/10 0010 10:35
	 */
	List<ResRenderPic> getListByIdList(List<Integer> idList);

	/**
	 * 获取720方案缩略图
	 * @param planId
	 * @return
	 */
	ResRenderPic get720SmallPicInfoByPlanId(@Param("planId") Integer planId);
}
