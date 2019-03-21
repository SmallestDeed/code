package com.sandu.service.designplan.impl.biz;

import com.sandu.api.designplan.common.constants.ResRenderPicConstant;
import com.sandu.api.designplan.model.ResRenderPic;
import com.sandu.api.designplan.service.ResRenderPicService;
import com.sandu.api.designplan.service.biz.DesignPlanRecommendedBizService;
import com.sandu.api.fullhouse.input.DesignPlanQuery;
import com.sandu.api.fullhouse.output.DesignPlanVO;
import com.sandu.service.designplan.dao.DesignPlanRecommendedDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * created by zhangchengda
 * 2018/8/16 13:57
 * 推荐方案业务服务
 */
@Service("designPlanRecommendedBizService")
@Slf4j
public class DesignPlanRecommendedBizServiceImpl implements DesignPlanRecommendedBizService {
    @Autowired
    private DesignPlanRecommendedDao designPlanRecommendedDao;
    @Autowired
    private ResRenderPicService resRenderPicService;

    private static final int DEFAULT_CURRENT_PAGE = 1;

    private static final int DEFAULT_PAGE_SIZE = 20;

    private static final int DEFAULT_START = 0;

    private static final int SPACE_TYPE_KITCHEN = 5;

    /**
     * created by zhangchengda
     * 2018/8/16 14:00
     * 查询当前登录用户的已审核通过的方案组合的主方案（空间类型为厨房时则查询一键方案），按方案创建时间降序排列
     *
     * @param query 查询参数
     * @return 返回方案的集合
     */
    @Override
    public List<DesignPlanVO> selectGroupPrimaryDesignPlan(DesignPlanQuery query) {
        // 处理分页
        if (query.getCurPage() == null || query.getCurPage() <= 0) {
            query.setCurPage(DEFAULT_CURRENT_PAGE);
        }
        if (query.getPageSize() == null || query.getPageSize() <= 0) {
            query.setPageSize(DEFAULT_PAGE_SIZE);
        }
        if (query.getPageSize() != null && query.getCurPage() != null) {
            query.setStart((query.getCurPage() - 1) * query.getPageSize());
        } else {
            query.setStart(DEFAULT_START);
        }
        // 查询方案，空间类型为厨房的话直接查询方案，其他类型则查询方案组合中的主方案
        List<DesignPlanVO> designPlanVOList = null;
        if (query.getSpaceType() != null) {
            if (SPACE_TYPE_KITCHEN == query.getSpaceType()) {
                designPlanVOList = designPlanRecommendedDao.selectDesignPlan(query);
            } else {
                designPlanVOList = designPlanRecommendedDao.selectGroupPrimaryDesignPlan(query);
            }
        }
        // 查找方案封面图
        for (DesignPlanVO designPlanVO : designPlanVOList) {
            List<ResRenderPic> picList = resRenderPicService.selectByRecommendedIdAndKeyAndRenderType(designPlanVO.getDesignPlanId(),
                    ResRenderPicConstant.FILE_KEY_DESIGN_RECOMMENFED_SMALL_PIC,
                    null);
            if (picList != null && picList.size() > 0) {
                designPlanVO.setPicPath(picList.get(0).getPicPath());
            }
        }
        return designPlanVOList;
    }

    /**
     * created by zhangchengda
     * 2018/8/16 16:02
     * 查询当前登录用户的已审核通过的方案组合的主方案（空间类型为厨房时则查询一键方案）总数
     *
     * @param query 查询参数
     * @return 返回方案的总数
     */
    @Override
    public Integer selectGroupPrimaryDesignPlanCount(DesignPlanQuery query) {
        // 处理分页
        query.setCurPage(null);
        query.setPageSize(null);
        query.setStart(null);
        // 查询方案总数，空间类型为厨房的话直接查询方案总数，其他类型则查询方案组合中的主方案总数
        Integer count = null;
        if (query.getSpaceType() != null) {
            if (SPACE_TYPE_KITCHEN == query.getSpaceType()) {
                count = designPlanRecommendedDao.selectDesignPlanCount(query);
            } else {
                count = designPlanRecommendedDao.selectGroupPrimaryDesignPlanCount(query);
            }
        }
        return count;
    }

    /**
     * created by zhangchengda
     * 2018/11/29 19:13
     * 获取所有的推荐方案ID
     *
     * @return
     */
    @Override
    public List<Integer> getAllRecommendedId() {
        List<Integer> list = designPlanRecommendedDao.getAllRecommendedId();
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public Integer increase(Integer nodeId, Byte detailType, Integer num) {
        return designPlanRecommendedDao.increase(nodeId, detailType, num);
    }

    @Override
    public List<Integer> getAllFullHouseId() {
        List<Integer> list = designPlanRecommendedDao.getAllFullHouseId();
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public List<Integer> getAllSuperiorRecommendedId() {
        List<Integer> list = designPlanRecommendedDao.getAllSuperiorRecommendedId();
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public List<Integer> getAllSuperiorFullHouseId() {
        List<Integer> list = designPlanRecommendedDao.getAllSuperiorFullHouseId();
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public List<Integer> getShopIdListByNameList(List<String> nameList) {
        if (nameList == null || nameList.size() <= 0) {
            return null;
        }
        return designPlanRecommendedDao.getShopIdListByNameList(nameList);
    }

    @Override
    public List<Integer> getShopIdList(List<Integer> idList) {
        if (idList == null) {
            idList = Arrays.asList(0);
        }
        if (idList.size() == 0) {
            idList.add(0);
        }
        return designPlanRecommendedDao.getShopIdList(idList);
    }
}
