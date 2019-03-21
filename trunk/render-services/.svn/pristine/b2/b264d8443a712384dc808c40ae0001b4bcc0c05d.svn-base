package com.nork.cityunion.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.cityunion.dao.UnionSpecialOfferMapper;
import com.nork.cityunion.model.UnionSpecialOffer;
import com.nork.cityunion.model.search.UnionSpecialOfferSearch;
import com.nork.cityunion.model.vo.UnionSpecialOfferVo;
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
	private static Logger logger = LoggerFactory.getLogger(UnionSpecialOfferService.class);
	private UnionSpecialOfferMapper unionSpecialOfferMapper;

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
	 *
	 * @param  unionSpecialOfferSearch
	 * @return   int
	 */
	@Override
	public int getCount(UnionSpecialOfferSearch unionSpecialOfferSearch){
		return  unionSpecialOfferMapper.selectCount(unionSpecialOfferSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  unionSpecialOfferSearch
	 * @return   List<UnionSpecialOffer>
	 */
	@Override
	public List<UnionSpecialOffer> getPaginatedList(
			UnionSpecialOfferSearch unionSpecialOfferSearch) {
		return unionSpecialOfferMapper.selectPaginatedList(unionSpecialOfferSearch);
	}

    @Override
    public UnionSpecialOfferVo getUnionSpecialOfferVoByReleaseId(Integer releaseId) {
      UnionSpecialOfferVo unionSpecialOfferVo = new UnionSpecialOfferVo();
      if(releaseId == null) {
        return unionSpecialOfferVo;
      }
      UnionSpecialOffer unionSpecialOffer = unionSpecialOfferMapper.getUnionSpecialOfferByReleaseId(releaseId);
      if(unionSpecialOffer == null) {
        return unionSpecialOfferVo;
      }
      unionSpecialOfferVo.setSpecialOfferName(unionSpecialOffer.getSpecialOfferName());
      unionSpecialOfferVo.setIsDisplayed(unionSpecialOffer.getIsDisplayed());
      //处理优惠活动内容显示格式
      String content = this.transformSpecialOfferContent(unionSpecialOffer.getSpecialOfferContent());
	  unionSpecialOfferVo.setSpecialOfferContent(content);
	  return unionSpecialOfferVo;
    }

	/**
	 * 其他
	 * 
	 */
	/**
	 * 处理数据以兼容旧同城联盟
	 * 新的活动内容为json格式存储,旧同城联盟前端读取的是String类型数据
	 * 所以要将json格式存储的活动内容转为String类型的数据
	 * @return
	 */
	private String transformSpecialOfferContent(String content) {
		if(StringUtils.isBlank(content)){
			return content;
		}
		//转换活动内容
		StringBuffer contentBuffer = new StringBuffer();
		try {
			Map contentMap = (Map) JSON.parseObject(content);
			for (Object obj : contentMap.keySet()) {
				//拼接活动内容加换行
				contentBuffer.append((String)contentMap.get(obj)+"\n");
			}
		} catch (Exception e) {
			//发生异常，可能是活动内容不是json格式字符串，则正常读取
			logger.error("transformSpecialOfferContent 转换活动内容时出现问题，可能不是json格式字符串,content:{}",content,e);
			return content;
		}
		String contentStr = contentBuffer.toString();
		if(StringUtils.isNotBlank(contentStr)){
			if(contentStr.lastIndexOf("\n") > -1){
				//剪切掉结尾的换行符
				contentStr = contentStr.substring(0,contentStr.lastIndexOf("\n"));
			}
		}
		return contentStr;
	}

	@Override
	public UnionSpecialOfferVo getUnionSpecialOfferVoById(Integer id) {
		UnionSpecialOfferVo unionSpecialOfferVo = new UnionSpecialOfferVo();
		if(id == null) {
			return null;
		}
		UnionSpecialOffer unionSpecialOffer = unionSpecialOfferMapper.selectByPrimaryKey(id);
		if(unionSpecialOffer == null) {
			return unionSpecialOfferVo;
		}
		unionSpecialOfferVo.setSpecialOfferName(unionSpecialOffer.getSpecialOfferName());
		unionSpecialOfferVo.setIsDisplayed(unionSpecialOffer.getIsDisplayed());
		//处理优惠活动内容显示格式
		String content = this.transformSpecialOfferContent(unionSpecialOffer.getSpecialOfferContent());
		unionSpecialOfferVo.setSpecialOfferContent(content);
		//转换时间显示格式
		if(unionSpecialOffer.getEffectiveBegin() != null){
			Date beginDate = unionSpecialOffer.getEffectiveBegin();
			String beginDateStr = Utils.getDateStr(beginDate,Utils.DATE);
			unionSpecialOfferVo.setEffectiveBegin(beginDateStr);
		}
		if(unionSpecialOffer.getEffectiveEnd() != null){
			Date endDate = unionSpecialOffer.getEffectiveEnd();
			String endDateStr = Utils.getDateStr(endDate,Utils.DATE);
			unionSpecialOfferVo.setEffectiveEnd(endDateStr);
		}

		return unionSpecialOfferVo;
	}
}
