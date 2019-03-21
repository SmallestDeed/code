package com.nork.cityunion.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.nork.cityunion.controller.UnionDesignPlanStoreReleaseController;
import com.nork.cityunion.model.DesignPlanStoreReleaseModel;
import com.nork.cityunion.model.UnionDesignPlanStoreReleaseDetails;
import com.nork.cityunion.model.vo.UnionDesignPlanStoreReleaseVO;
import com.nork.cityunion.service.UnionDesignPlanStoreReleaseDetailsService;
import com.nork.cityunion.service.UnionDesignPlanStoreService;
import com.nork.common.model.LoginUser;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Constants;
import com.nork.common.util.StringUtils;
import com.nork.common.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nork.cityunion.dao.UnionDesignPlanStoreReleaseMapper;
import com.nork.cityunion.model.UnionDesignPlanStoreRelease;
import com.nork.cityunion.model.search.UnionDesignPlanStoreReleaseSearch;
import com.nork.cityunion.service.UnionDesignPlanStoreReleaseService;
import sun.rmi.runtime.Log;

/**   
 * @Title: UnionDesignPlanStoreReleaseServiceImpl.java 
 * @Package com.nork.cityunion.service.impl
 * @Description:同城联盟-联盟素材发布表ServiceImpl
 * @createAuthor pandajun 
 * @CreateDate 2018-01-16 18:25:43
 * @version V1.0   
 */
@Service("unionDesignPlanStoreReleaseService")
public class UnionDesignPlanStoreReleaseServiceImpl implements UnionDesignPlanStoreReleaseService {

	private static Logger logger = LoggerFactory.getLogger(UnionDesignPlanStoreReleaseServiceImpl.class);
	private UnionDesignPlanStoreReleaseMapper unionDesignPlanStoreReleaseMapper;

	@Autowired
	private UnionDesignPlanStoreReleaseDetailsService unionDesignPlanStoreReleaseDetailsService;

	@Autowired
	private UnionDesignPlanStoreService unionDesignPlanStoreService;

	@Autowired
	public void setUnionDesignPlanStoreReleaseMapper(
			UnionDesignPlanStoreReleaseMapper unionDesignPlanStoreReleaseMapper) {
		this.unionDesignPlanStoreReleaseMapper = unionDesignPlanStoreReleaseMapper;
	}

	/**
	 * 新增数据
	 *
	 * @param unionDesignPlanStoreRelease
	 * @return  int 
	 */
	@Override
	public int add(UnionDesignPlanStoreRelease unionDesignPlanStoreRelease) {
		unionDesignPlanStoreReleaseMapper.insertSelective(unionDesignPlanStoreRelease);
		return unionDesignPlanStoreRelease.getId();
	}

	/**
	 *    更新数据
	 *
	 * @param unionDesignPlanStoreRelease
	 * @return  int 
	 */
	@Override
	public int update(UnionDesignPlanStoreRelease unionDesignPlanStoreRelease) {
		return unionDesignPlanStoreReleaseMapper
				.updateByPrimaryKeySelective(unionDesignPlanStoreRelease);
	}
	
	/**
	 *    删除数据
	 *
	 * @param id
	 * @return  int 
	 */
	@Override
	public int delete(Integer id) {
		return unionDesignPlanStoreReleaseMapper.deleteByPrimaryKey(id);
	}

	/**
	 *    获取数据详情
	 *
	 * @param id
	 * @return  UnionDesignPlanStoreRelease 
	 */
	@Override
	public UnionDesignPlanStoreRelease get(Integer id) {
		return unionDesignPlanStoreReleaseMapper.selectByPrimaryKey(id);
	}

	/**
	 * 所有数据
	 * 
	 * @param  unionDesignPlanStoreRelease
	 * @return   List<UnionDesignPlanStoreRelease>
	 */
	@Override
	public List<UnionDesignPlanStoreRelease> getList(UnionDesignPlanStoreRelease unionDesignPlanStoreRelease) {
	    return unionDesignPlanStoreReleaseMapper.selectList(unionDesignPlanStoreRelease);
	}
	
	/**
	 *    获取数据数量
	 *
	 * @param  unionDesignPlanStoreReleaseSearch
	 * @return   int
	 */
	@Override
	public int getCount(UnionDesignPlanStoreReleaseSearch unionDesignPlanStoreReleaseSearch){
		return  unionDesignPlanStoreReleaseMapper.selectCount(unionDesignPlanStoreReleaseSearch);	
    }
	

	/**
	 *    分页获取数据
	 *
	 * @param  unionDesignPlanStoreReleaseSearch
	 * @return   List<UnionDesignPlanStoreRelease>
	 */
	@Override
	public List<UnionDesignPlanStoreRelease> getPaginatedList(
			UnionDesignPlanStoreReleaseSearch unionDesignPlanStoreReleaseSearch) {
		return unionDesignPlanStoreReleaseMapper.selectPaginatedList(unionDesignPlanStoreReleaseSearch);
	}

	@Override
	public int deleteByGroupId(Integer id) {
		// TODO Auto-generated method stub
		return unionDesignPlanStoreReleaseMapper.deleteByGroupId(id);
	}

	/**
	 * 根据分类Id删除发布素材
	 * @param storeRelease
	 * @return
	 */
	@Override
	public int updateByStoreRelease(UnionDesignPlanStoreRelease storeRelease){
		return unionDesignPlanStoreReleaseMapper.updateByStoreRelease(storeRelease);
	}

	/**
	 * 保存、编辑发布素材数据
	 * @param storeRelease
	 * @param loginUser
	 * @return
	 */
	public String save(UnionDesignPlanStoreRelease storeRelease, LoginUser loginUser) {

		if (storeRelease == null) {
			return null;
		}
		//TODO 编辑没有编辑素材一说。。只有新增保存关联关系
		StringBuffer releaseIds = new StringBuffer("");
		try {
			if (storeRelease.getId() == null) {
				//TODO 区分打包发布和单独发布逻辑（1 打包发布，0 单独发布）
				if (Constants.UNION_STORE_RELEASE_STATUS.equals(storeRelease.getReleaseType())) {
					List<Integer> storeIdList = new ArrayList<>();
					for (DesignPlanStoreReleaseModel releaseModel : storeRelease.getStoreReleaseModelList()) {
						storeIdList.add(releaseModel.getStoreId());
					}
					//TODO 打包发布需获取优先进入房间的素材ID
					Integer storeId = 0 ;
//					Integer storeId = unionDesignPlanStoreService.getStoreIdByStoreIds(storeIdList);
					List<Integer> storeIds = unionDesignPlanStoreService.findStoreIdByStoreIdsList(storeIdList);
					if (storeIds != null) {
						storeId = storeIds.get(0);
					}
					storeRelease.setDesignPlanStoreId(storeId == null ? 0 : storeId);
					int id = unionDesignPlanStoreReleaseMapper.insertSelective(storeRelease);
					logger.info("add:id=" + storeRelease.getId());
					if (id > 0) {
						releaseIds.append(storeRelease.getId()).append(",");
						for (DesignPlanStoreReleaseModel releaseModel : storeRelease.getStoreReleaseModelList()) {
							UnionDesignPlanStoreReleaseDetails storeReleaseDetails = new UnionDesignPlanStoreReleaseDetails();
							//获取排序值
							for (int i=0;i<storeIds.size();i++) {
								if (releaseModel.getStoreId().equals(storeIds.get(i))) {
									storeReleaseDetails.setStoreSequence(i+1);
									break;
								}
							}
							storeReleaseDetails.setUnionDesignPlanStoreId(releaseModel.getStoreId());
							storeReleaseDetails.setUnionDesignPlanStoreReleaseId(storeRelease.getId());
							unionDesignPlanStoreReleaseDetailsService.sysSave(storeReleaseDetails,loginUser);
							unionDesignPlanStoreReleaseDetailsService.add(storeReleaseDetails);
						}
					}
				} else {
					for (DesignPlanStoreReleaseModel releaseModel : storeRelease.getStoreReleaseModelList()) {
						storeRelease.setDesignPlanStoreId(releaseModel.getStoreId());
						storeRelease.setReleaseName(releaseModel.getStoreReleaseName());
						unionDesignPlanStoreReleaseMapper.insertSelective(storeRelease);
						releaseIds.append(storeRelease.getId()).append(",");
						logger.info("add:id=" + storeRelease.getId());
						UnionDesignPlanStoreReleaseDetails storeReleaseDetails = new UnionDesignPlanStoreReleaseDetails();
						storeReleaseDetails.setUnionDesignPlanStoreId(releaseModel.getStoreId());
						storeReleaseDetails.setUnionDesignPlanStoreReleaseId(storeRelease.getId());
						unionDesignPlanStoreReleaseDetailsService.sysSave(storeReleaseDetails,loginUser);
						unionDesignPlanStoreReleaseDetailsService.add(storeReleaseDetails);
					}
				}
			} else {
				int id = unionDesignPlanStoreReleaseMapper.updateByPrimaryKeySelective(storeRelease);
				logger.info("update:id=" + id);
				releaseIds.append(storeRelease).append(",");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		if (releaseIds != null && releaseIds.length() > 0) {
			return releaseIds.substring(0,releaseIds.length()-1).toString();
		}else {
			return null;
		}
	}


	/**
	 * 发布列表查询
	 * @param catoryId
	 * @param releaseName
	 * @param userId
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public int findStoreReleaseCount(Integer catoryId, String releaseName, Integer userId, Integer start, Integer limit) {
		return unionDesignPlanStoreReleaseMapper.findStoreReleaseCount(catoryId,releaseName,userId,start,limit);
	}

	/**
	 * 发布列表查询
	 * @param catoryId
	 * @param releaseName
	 * @param userId
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public List<UnionDesignPlanStoreReleaseVO> findStoreReleaseList(Integer catoryId, String releaseName, Integer userId, Integer start, Integer limit) {
	    List<UnionDesignPlanStoreReleaseVO> list = unionDesignPlanStoreReleaseMapper.findStoreReleaseList(catoryId,releaseName,userId,start,limit);
	    for (UnionDesignPlanStoreReleaseVO releaseVO : list) {
	        //处理活动内容格式
            if(StringUtils.isNotEmpty(releaseVO.getSpecialOfferContent())) {
                //转换活动内容
                StringBuffer contentBuffer = new StringBuffer();
                try {
                    Map contentMap = (Map) JSON.parseObject(releaseVO.getSpecialOfferContent());
                    for (Object obj : contentMap.keySet()) {
                        //拼接活动内容加换行
                        contentBuffer.append((String)contentMap.get(obj)+"\n");
                    }
                } catch (Exception e) {
                    logger.debug("findStoreReleaseList----->转换活动内容格式时出现异常,需转换的活动类型:{}。",releaseVO.getSpecialOfferContent(),e);
                    //发生异常，可能是活动内容不是json格式字符串，则正常读取
                    continue;
                }
                String contentStr = contentBuffer.toString();
                if(StringUtils.isNotBlank(contentStr)){
                    if(contentStr.lastIndexOf("\n") > -1){
                        //剪切掉结尾的换行符
                        contentStr = contentStr.substring(0,contentStr.lastIndexOf("\n"));
                    }
                    releaseVO.setSpecialOfferContent(contentStr);
                }
            }
            
        }
	    return list;
	}

	/**
	 * 通过设计效果图方案ID查询联盟设计方案发布的集合
	 * @param designSceneId
	 * @return
	 */
	@Override
	public List<UnionDesignPlanStoreRelease> findStoreReleaseListByDesignSceneId(Integer designSceneId) {
		return unionDesignPlanStoreReleaseMapper.findStoreReleaseListByDesignSceneId(designSceneId);
	}


}
