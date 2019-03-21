package com.nork.cityunion.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSONArray;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSONObject;
import com.nork.cityunion.model.SpecialOfferModel;
import com.nork.cityunion.model.vo.UnionSpecialOfferVO;
import com.nork.common.objectconvert.cityUnion.UnionSpecialOfferObject;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import com.nork.common.util.collections.CustomerListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.cityunion.dao.UnionSpecialOfferMapper;
import com.nork.cityunion.model.UnionSpecialOffer;
import com.nork.cityunion.model.search.UnionSpecialOfferSearch;
import com.nork.cityunion.service.UnionSpecialOfferService;

/**   
 * @Title: UnionSpecialOfferServiceImpl.java 
 * @Package com.nork.cityunion.service.impl
 * @Description:同城联盟-联盟优惠活动表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:24:34
 * @version V1.0   
 */
@Service("unionSpecialOfferService")
public class UnionSpecialOfferServiceImpl implements UnionSpecialOfferService {

	private UnionSpecialOfferMapper unionSpecialOfferMapper;

	private static Logger logger = LoggerFactory.getLogger(UnionSpecialOffer.class);
	@Autowired
	public void setUnionSpecialOfferMapper(
			UnionSpecialOfferMapper unionSpecialOfferMapper) {
		this.unionSpecialOfferMapper = unionSpecialOfferMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param unionSpecialOffer
	 * @return  int 
	 */
	@Override
	public int add(UnionSpecialOffer unionSpecialOffer) {
		unionSpecialOfferMapper.insertSelective(unionSpecialOffer);
		return unionSpecialOffer.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param unionSpecialOffer
	 * @return  int 
	 */
	@Override
	public int update(UnionSpecialOffer unionSpecialOffer) {
		return unionSpecialOfferMapper
				.updateByPrimaryKeySelective(unionSpecialOffer);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return unionSpecialOfferMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  UnionSpecialOffer 
	 */
	@Override
	public UnionSpecialOffer get(Integer id) {
		return unionSpecialOfferMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  unionSpecialOffer
	 * @return   List<UnionSpecialOffer>
	 */
	@Override
	public List<UnionSpecialOffer> getList(UnionSpecialOffer unionSpecialOffer) {
	    return unionSpecialOfferMapper.selectList(unionSpecialOffer);
	}
	
	/**
	 *    获取数据数量
	 * @return   int
	 */
	@Override
	public int getCount(UnionSpecialOfferSearch unionSpecialOfferSearch){
		return  unionSpecialOfferMapper.selectCount(unionSpecialOfferSearch);	
    }
	

	/**
	 *    分页获取数据
	 * @return   List<UnionSpecialOffer>
	 */
	@Override
	public List<UnionSpecialOffer> getPaginatedList(
			UnionSpecialOfferSearch unionSpecialOfferSearch) {
		return unionSpecialOfferMapper.selectPaginatedList(unionSpecialOfferSearch);
	}

	/**
	 * 其他
	 * 
	 */

	/**
	 * 其他
	 *
	 */

	/**
	 * 查询个人用户优惠活动数量
	 * @param userId
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public int findSpecialOfferCount(Integer userId,Integer start,Integer limit) {
		return unionSpecialOfferMapper.findSpecialOfferCount(userId,start,limit);
	}

	/**
	 * 查询个人用户优惠活动集合
	 * @return
	 */
	@Override
	public List<UnionSpecialOffer> findSpecialOfferList(SpecialOfferModel model) {
		List<UnionSpecialOffer> list = null;
		list = unionSpecialOfferMapper.findSpecialOfferList(model.getUserId(),model.getStart(),model.getLimit());
		if(!model.isTransformContent()){
			//处理活动内容的格式
			for (UnionSpecialOffer unionSpecialOffer : list) {
				unionSpecialOffer = this.transformSpecialOfferWithContent(unionSpecialOffer);
			}
		}
		return list;
	}

	@Override
	public UnionSpecialOffer transformSpecialOfferWithContent(UnionSpecialOffer unionSpecialOffer) {
		if(unionSpecialOffer == null){
			return null;
		}
		if(StringUtils.isNotBlank(unionSpecialOffer.getSpecialOfferContent())){
			//转换活动内容
			StringBuffer contentBuffer = new StringBuffer();
			try {
				Map contentMap = (Map) JSON.parseObject(unionSpecialOffer.getSpecialOfferContent());
				for (Object obj : contentMap.keySet()) {
					//拼接活动内容加换行
					contentBuffer.append((String)contentMap.get(obj)+"\n");
				}
			} catch (Exception e) {
				logger.debug("transformSpecialOfferListWithContent----->转换活动内容格式时出现异常,需转换的活动类型:{}。",unionSpecialOffer.getSpecialOfferContent(),e);
				//发生异常，可能是活动内容不是json格式字符串，则正常读取
			}
			String contentStr = contentBuffer.toString();
			if(StringUtils.isNotBlank(contentStr)){
				if(contentStr.lastIndexOf("\n") > -1){
					//剪切掉结尾的换行符
					contentStr = contentStr.substring(0,contentStr.lastIndexOf("\n"));
				}
				unionSpecialOffer.setSpecialOfferContent(contentStr);
			}
		}
		return unionSpecialOffer;
	}

	@Override
	public UnionSpecialOfferVO getUnionSpecialOfferVOById(Integer id) {
		if(id == null || id <= 0){
			return null;
		}
		UnionSpecialOfferVO specialOfferVO = null;
		UnionSpecialOffer specialOffer = this.get(id);

		//处理活动内容数据
		specialOffer = this.transformSpecialOfferWithContent(specialOffer);
			//转换vo对象
		specialOfferVO = UnionSpecialOfferObject.paramToSpecialOfferVOBySpecialOffer(specialOffer);
		return specialOfferVO;
	}

	@Override
	public UnionSpecialOffer transformSpecialOfferContentToJson(UnionSpecialOffer unionSpecialOffer) {
		if(unionSpecialOffer == null || StringUtils.isBlank(unionSpecialOffer.getSpecialOfferContent())){
			return unionSpecialOffer;
		}
		//判断是否是json 格式数据
		if(isJsonContent(unionSpecialOffer.getSpecialOfferContent())){
			return unionSpecialOffer;
		}else{
			//转换数据格式 示例："优惠1\n优惠2" 转为 "{"1":"优惠活动","2":"优惠2"}"
			List<String> contentList = Utils.getListFromStr2(unionSpecialOffer.getSpecialOfferContent(),"\n");
			if(CustomerListUtils.isEmpty(contentList)){
				return unionSpecialOffer;
			}
			try {
				JSONObject jsonObject = new JSONObject();
				for (int i = 0 ; i<contentList.size() ; i++){
					jsonObject.put((i + 1)+"",contentList.get(i));
                }
				unionSpecialOffer.setSpecialOfferContent(jsonObject.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return unionSpecialOffer;
	}

	/**
	 * 判断字符串是否是json格式
	 * @param content
	 * @return
	 */
	private boolean isJsonContent(String content){
		boolean ret = false;
		try {
			Map contentMap = (Map) JSON.parseObject(content);
		} catch (Exception e) {
			return ret;
		}
		ret = true;
		return ret;
	}
}
