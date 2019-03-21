package com.sandu.service.platform.impl;

import com.alibaba.druid.sql.visitor.functions.Now;
import com.sandu.api.platform.model.PlatformProductRel;
import com.sandu.api.platform.service.Platform2cProductRelService;
import com.sandu.api.product.model.Product;
import com.sandu.api.product.model.bo.StyleParseBO;
import com.sandu.service.platform.dao.Platform2cProductRelDao;
import com.sandu.service.product.dao.ProductDao;
import com.sandu.util.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Sandu
 * @version V1.0
 * @Title: Platform2bProductRelServiceImpl.java
 * @Package com.nork.product.service.impl
 * @Description:产品-2b类型平台-产品关联表ServiceImpl
 * @createAuthor pandajun
 * @CreateDate 2017-12-28 20:58:57
 */
@Service
@Slf4j
public class Platform2cProductRelServiceImpl implements Platform2cProductRelService {

    @Autowired
    private Platform2cProductRelDao platform2cProductRelDao;
    @Autowired
    private ProductDao productDao;

    /**
     * 新增数据
     *
     * @param platformProductRel
     * @return int
     */
    @Override
    public int add(PlatformProductRel platformProductRel) {
        platform2cProductRelDao.insertSelective(platformProductRel);
        return platformProductRel.getId();
    }

    /**
     * 更新数据
     *
     * @param platformProductRel
     * @return int
     */
    @Override
    public int update(PlatformProductRel platformProductRel) {
        return platform2cProductRelDao
                .updateByPrimaryKeySelective(platformProductRel);
    }

    /**
     * 删除数据
     *
     * @param id
     * @return int
     */
    @Override
    public int delete(Integer id) {
        return platform2cProductRelDao.deleteByPrimaryKey(id);
    }

    /**
     * 获取数据详情
     *
     * @param id
     * @return PlatformProductRel
     */
    @Override
    public PlatformProductRel get(Integer id) {
        return platform2cProductRelDao.selectByPrimaryKey(id);
    }

    /**
     * 所有数据
     *
     * @param platformProductRel
     * @return List<PlatformProductRel>
     */
    @Override
    public List<PlatformProductRel> getList(PlatformProductRel platformProductRel) {
        return platform2cProductRelDao.selectList(platformProductRel);
    }

    @Override
    public int issueProducts(Integer platformId, List<PlatformProductRel> platformProductRels) {
        if (platformProductRels.isEmpty()) {
            return -1;
        }
        return platform2cProductRelDao.insertList(platformId, platformProductRels);
    }

    @Override
    public Map<String, List<Integer>> checkProductIssue(Integer platformId, List<Integer> productIds) {
        //已发布
        List<Integer> issue = null;
        if (!productIds.isEmpty()) {
            //获取已经发布的产品ID
            issue = platform2cProductRelDao.getIssueProductIdsFromIds(platformId, productIds);
        } else {
            issue = new ArrayList<>();
        }
        productIds.removeAll(issue);
        //未发布或者取消过发布
        List<Integer> notIssue = productIds;
        //过滤出取消过发布的ids
        List<Integer> cancleIssue = null;
        if (!notIssue.isEmpty()) {
            cancleIssue = platform2cProductRelDao.getCancleIssueIdsFromIds(platformId, notIssue);
        } else {
            cancleIssue = new ArrayList<>();
        }
        notIssue.removeAll(cancleIssue);
        Map<String, List<Integer>> ret = new HashMap<>(3);
        ret.put("issue", issue);
        ret.put("notIssue", notIssue);
        ret.put("cancleIssue", cancleIssue);
        return ret;
    }

    @Override
    public int issueProductsToPlatformIds(List<Integer> platformIds, List<Integer> productIds) {

        try {
            //插入
            for (Integer platformId : platformIds) {
                List<Integer> list = new ArrayList<>();
                list.addAll(productIds);
                Map<String, List<Integer>> stringListMap = checkProductIssue(platformId, list);
                List<PlatformProductRel> platformProductRels = new ArrayList<>();
                //得到未发布的产品ID, 构造出 PlatformProductRel 对象集合,设置自动存储字段
                if (stringListMap.get("notIssue").size() > 0) {
                    List<Product> products = productDao.getListByIds(stringListMap.get("notIssue"));
                    for (Product product : products) {
                        PlatformProductRel tmp = new PlatformProductRel();
                        tmp.setProductId(product.getId().intValue());
                        tmp.setGmtCreate(new Date());
                        tmp.setGmtModified(new Date());
                        tmp.setIsDeleted(0);
                        tmp.setAllotState(1);
                        //设置分配产品属性
                        if (StringUtils.isNoneBlank(product.getProductStyleIdInfo())) {
                            StyleParseBO styleParseBO = JsonParser.fromJson(product.getProductStyleIdInfo(), StyleParseBO.class);
                            tmp.setStyleId(styleParseBO.getIsLeaf_0());
                        }
                        if (product.getAdvicePrice() != null) {
                            tmp.setAdvicePrice(product.getAdvicePrice());
                        }
                        if (product.getSalePrice() != null) {
                            tmp.setSalePrice(product.getSalePrice());
                        }
                        if (product.getPicIds() != null) {
                            tmp.setPicIds(product.getPicIds());
                        }
                        if (product.getPicId() != null) {
                            tmp.setPicId(product.getPicId());
                        }
                        if (product.getProductDesc() != null) {
                            tmp.setDescription(product.getProductDesc());
                        }
                        platformProductRels.add(tmp);
                    }
                }
                //数据插入(插入到某一个平台)
                if (!platformProductRels.isEmpty()) {
                    issueProducts(platformId, platformProductRels);
                }
                //--------------------------------------------------//
                //更新
                List<PlatformProductRel> platformProductRelsUpdate = new ArrayList<>();
                //得到被取消发布的产品ID, 构造出 PlatformProductRel 对象集合,设置自动存储字段
                for (Integer productId : stringListMap.get("cancleIssue")) {
                    PlatformProductRel tmp = new PlatformProductRel();
                    tmp.setPlatformId(platformId);
                    tmp.setProductId(productId);
                    tmp.setAllotState(1);
                    tmp.setGmtCreate(new Date());
                    tmp.setGmtModified(new Date());
                    tmp.setId(-1);
                    platformProductRelsUpdate.add(tmp);
                }
                //更新发布状态
                if (!platformProductRelsUpdate.isEmpty()) {
                    updatePlatformProductRels(platformProductRelsUpdate);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    @Override
    public void cancelIssueProducts(List<Integer> productIds) {
        if (!productIds.isEmpty()) {
            deleteByProductIds(productIds);
        }
    }

    @Override
    public void updatePlatformProductRels(List<PlatformProductRel> platformProductRelList) {
        List<PlatformProductRel> insertList = new LinkedList<>();
        for (PlatformProductRel platformProductRel : platformProductRelList) {
            int mark = platform2cProductRelDao.updateByPlatformIdAndProductId(platformProductRel);
            if (mark <= 0) {
                platformProductRel.setGmtCreate(new Date());
                platformProductRel.setRemark(null);
                insertList.add(platformProductRel);
            }
        }
        if (!insertList.isEmpty()) {
            insertList.forEach(item -> {
                item.setAllotState(1);
                if (Objects.isNull(item.getPutawayState())) {
                    item.setPutawayState(0);
                }
            });
            platform2cProductRelDao.insertRelListIfNotExist(insertList);
        }
//         platform2cProductRelDao.updatePlatformProductRels(platformProductRelList);
    }

    @Override
    public void updatePlatformProductRel(PlatformProductRel platformProductRel) {
        platform2cProductRelDao.updateByPlatformIdAndProductId(platformProductRel);
    }

    @Override
    public PlatformProductRel getByProductIdAndPlatform(Integer platformId, Integer productId) {
        return platform2cProductRelDao.getByProductIdAndPlatform(platformId, productId);
    }

    @Override
    public List<String> getAllIssuePlatformByProductId(Integer id) {
        return platform2cProductRelDao.getAllIssuePlatfromByProductId(id);
    }

    @Override
    public List<String> getAllPutPlatformByProductId(Integer id) {
        return platform2cProductRelDao.getAllPutPlatfromByProductId(id);
    }

    @Override
    public int checkProductHasIssueToPlatform(PlatformProductRel platformProductRel) {
        return platform2cProductRelDao.checkProductHasIssueToPlatform(platformProductRel);
    }

    @Override
    public Integer getPlatformIdByProductId(long productId) {
        return platform2cProductRelDao.getPlatformIdByProductId(productId);
    }

    @Override
    public int deleteByProductIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return -1;
        }

        return platform2cProductRelDao.deleteByProductIds(ids);
    }

    @Override
    public int insertRelList(List<PlatformProductRel> collect) {
        if (collect.isEmpty()) {
            return -1;
        }
        return platform2cProductRelDao.insertRelList(collect);
    }

    @Override
    public List<Integer> getNotExistIds(List<Integer> listRel2c) {
        listRel2c = listRel2c.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
        if (listRel2c == null || listRel2c.isEmpty()) {
            return Collections.emptyList();
        }
        List<Integer> exist = platform2cProductRelDao.getExistProductIds(listRel2c);
        return listRel2c.stream().distinct().filter(it -> !exist.contains(it)).collect(Collectors.toList());
    }

    @Override
    public Map<Integer, String> getBePutawayPlatformByProductIds(List<Integer> productIds) {
        if (productIds.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<Integer, String> ret = new HashMap<>();
        List<Map<String, String>> list = platform2cProductRelDao.getBePutawayPlatformByProductIds(productIds);
        list.forEach(item -> System.out.println("----------->" + item));
        log.info("list map------->[]", list);
        for (Map<String, String> item : list) {
            Integer productId = Integer.parseInt(String.valueOf(item.get("productId")));
            String platformIds = item.get("platformIds");
            ret.put(productId, platformIds);
        }
        return ret;
    }

	@Override
	public int deleteByIdsAndPlatForm(List<Integer> ids, List<String> platformIds) {
		if(ids.isEmpty()) {
			return 0;
		}
		if(platformIds.isEmpty()) {
			return 0;
		}
		return platform2cProductRelDao.deleteByIdsAndPlatForm(ids,platformIds);
	}

	@Override
	public int insertRelListIfNotExist(List<PlatformProductRel> insertList) {
		return platform2cProductRelDao.insertRelListIfNotExist(insertList);
	}
}