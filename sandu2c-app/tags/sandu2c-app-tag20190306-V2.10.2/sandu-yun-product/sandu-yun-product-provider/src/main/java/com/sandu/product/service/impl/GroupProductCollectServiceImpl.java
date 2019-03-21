package com.sandu.product.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.common.util.Utils;
import com.sandu.product.dao.GroupProductCollectMapper;
import com.sandu.product.model.GroupCollectDetails;
import com.sandu.product.model.GroupProduct;
import com.sandu.product.model.GroupProductCollect;
import com.sandu.product.model.GroupProductCollectSearch;
import com.sandu.product.service.BaseProductService;
import com.sandu.product.service.GroupCollectDetailsService;
import com.sandu.product.service.GroupProductCollectService;
import com.sandu.system.model.ResPic;
import com.sandu.system.service.ResPicService;
import com.sandu.user.model.LoginUser;
import com.sandu.user.service.SysUserService;


/**   
 * @Title: GroupProductCollectServiceImpl.java 
 * @Package com.nork.product.service.impl
 * @Description:产品模块-组合收藏表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2016-06-22 19:46:06
 * @version V1.0   
 */
@Service("groupProductCollectService")
public class GroupProductCollectServiceImpl implements GroupProductCollectService {
	
	Logger logger=LoggerFactory.getLogger(GroupProductCollectServiceImpl.class);
	
	@Autowired
	private GroupProductCollectMapper groupProductCollectMapper;
	@Autowired
	private ResPicService resPicService;
	@Autowired
	private GroupCollectDetailsService groupCollectDetailsService;
	@Autowired
	private BaseProductService baseProductService;

	
	/**
	 * 新增数据
	 *
	 * @param groupProductCollect
	 * @return  int 
	 */
	@Override
	public int add(GroupProductCollect groupProductCollect) {
		groupProductCollectMapper.insertSelective(groupProductCollect);
		return groupProductCollect.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param groupProductCollect
	 * @return  int 
	 */
	@Override
	public int update(GroupProductCollect groupProductCollect) {
		return groupProductCollectMapper
				.updateByPrimaryKeySelective(groupProductCollect);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return groupProductCollectMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  GroupProductCollect 
	 */
	@Override
	public GroupProductCollect get(Integer id) {
		return groupProductCollectMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  groupProductCollect
	 * @return   List<GroupProductCollect>
	 */
	@Override
	public List<GroupProductCollect> getList(GroupProductCollect groupProductCollect) {
	    return groupProductCollectMapper.selectList(groupProductCollect);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  groupProductCollect
	 * @return   int
	 */
	@Override
	public int getCount(GroupProductCollectSearch groupProductCollectSearch){
		return  groupProductCollectMapper.selectCount(groupProductCollectSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  groupProductCollect
	 * @return   List<GroupProductCollect>
	 */
	@Override
	public List<GroupProductCollect> getPaginatedList(
			GroupProductCollectSearch groupProductCollectSearch) {
		return groupProductCollectMapper.selectPaginatedList(groupProductCollectSearch);
	}

	/**
	 * updateDetails方法验证参数
	 * @author huangsongbo
	 * @param groupId
	 * @param productId
	 * @param type
	 * @return
	 */
	public Map<String, Object> updateDetailsVerifyParams(Integer groupId, Integer productId, Integer type,
			String msgId) {
		boolean success=true;
		String msg="";
		if(StringUtils.isBlank(msgId)){
			success=false;
			msg="参数msgId不能为空";
		}else if(groupId==null){
			success=false;
			msg="参数groupId不能为空";
		}else if(productId==null){
			success=false;
			msg="参数productId不能为空";
		}else if(type==null){
			success=false;
			msg="参数type不能为空";
		}
		Map<String,Object> returnMap=new HashMap<String,Object>();
		returnMap.put("success", success);
		returnMap.put("msg", msg);
		return returnMap;
	}

	/**
	 * 取得收藏的封面图并保存
	 * @author huangsongbo
	 * @param groupProductCollect
	 */
	public void savePicUrl(GroupProductCollect groupProductCollect) {
		if(groupProductCollect.getPicId()==null){
			/*取关联产品缩略图(优先主产品,没有主产品取第一个产品)*/
			groupProductCollect.setProductPath(getPicUrlFromProducts(groupProductCollect.getId()));
		}else{
			ResPic resPic=resPicService.get(groupProductCollect.getPicId());
			if(resPic==null){
				/*取关联产品缩略图(优先主产品,没有主产品取第一个产品)*/
				groupProductCollect.setProductPath(getPicUrlFromProducts(groupProductCollect.getId()));
			}else{
				groupProductCollect.setProductPath(resPic.getPicPath());
			}
		}
	}

	/**
	 * 取关联产品缩略图(优先主产品,没有主产品取第一个产品)
	 * @author huangsongbo
	 * @param id
	 * @return
	 */
	private String getPicUrlFromProducts(Integer id) {
		List<GroupCollectDetails> list=groupCollectDetailsService.findAllByGroupId(id);
		String url="";
		if(list==null||list.size()==0){
			logger.info("------id为{}的组合收藏中没有关联产品,这种情况不允许出现",id);
			return null;
		}
		/*识别list里面有没有主产品->有,取得缩略图url;无:取第一个产品的url*/
		Integer mainProductId=0;
		for(GroupCollectDetails groupCollectDetails:list){
			if(new Integer(1).equals(groupCollectDetails.getIsMain()==null?0:groupCollectDetails.getIsMain())){
				mainProductId=groupCollectDetails.getProductId();
				break;
			}
		}
		if(!new Integer(0).equals(mainProductId)){
			/*存在主产品*/
			url=baseProductService.getPicUrlFromProductId(mainProductId);
		}else{
			/*不存在主产品*/
			url=baseProductService.getPicUrlFromProductId(list.get(0).getProductId());
		}
		/*识别list里面有没有主产品->有,取得缩略图url;无:取第一个产品的url->end*/
		return url;
	}

	/**
	 * 收藏组合
	 * @author huangsongbo
	 * @param groupProduct
	 * @return id
	 */
	public int saveCollectByGroup(GroupProduct groupProduct, LoginUser loginUser) {
		GroupProductCollect groupProductCollect=new GroupProductCollect();
		groupProductCollect.setUserId(loginUser.getId());
		groupProductCollect.setName(groupProduct.getGroupName());
		groupProductCollect.setTypeValue(groupProduct.getType());
		groupProductCollect.setCoordinates(null/*等待更新字段*/);
		groupProductCollect.setGroupId(groupProduct.getId());
		sysSave(groupProductCollect, loginUser);
		int id=add(groupProductCollect);
		return id;
	}

	private void sysSave(GroupProductCollect model, LoginUser loginUser){
		if(model != null){
			if(model.getId() == null){
				model.setGmtCreate(new Date());
				model.setCreator(loginUser.getLoginName());
				model.setIsDeleted(0);
			    if(model.getSysCode()==null || "".equals(model.getSysCode())){
				   model.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
			   }
			}
			model.setGmtModified(new Date());
			model.setModifier(loginUser.getLoginName());
		}
	}

	/**
	 * 删除组合收藏,通过参数groupId和userId
	 * @author huangsongbo
	 * @param groupId
	 * @param id
	 * @return
	 */
	public int deleteByGroupIdAndUserId(Integer groupId, Integer userId) {
		/*查找指定组合*/
		int num=0;
		GroupProductCollectSearch groupProductCollectSearch=new GroupProductCollectSearch();
		groupProductCollectSearch.setStart(0);
		groupProductCollectSearch.setLimit(1);
		groupProductCollectSearch.setGroupId(groupId);
		groupProductCollectSearch.setUserId(userId);
		List<GroupProductCollect> groupProductCollects=getPaginatedList(groupProductCollectSearch);
		if(groupProductCollects!=null&&groupProductCollects.size()>0){
			GroupProductCollect groupProductCollect=groupProductCollects.get(0);
			/*删除收藏详情表中的关联数据*/
			groupCollectDetailsService.deleteByGroupCollectId(groupProductCollect.getId());
			/*删除收藏详情表中的关联数据->end*/
			num=delete(groupProductCollect.getId());
		}
		return num;
	}

	@Override
	public int getGroupProductCollectCount(
			GroupProductCollectSearch groupProductCollectSearch) {
		return groupProductCollectMapper.groupProductCollectCount(groupProductCollectSearch);
	}

	@Override
	public List<GroupProductCollect> getGroupProductCollectList(
			GroupProductCollectSearch groupProductCollecttSearch) {
		// TODO Auto-generated method stub
		return groupProductCollectMapper.groupProductCollectList(groupProductCollecttSearch);
	}
	
}
