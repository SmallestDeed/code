package com.sandu.service.designplan.impl.biz;

import com.sandu.api.designplan.service.biz.DesignPlanRecommendedBizService;
import com.sandu.api.fullhouse.input.DesignPlanQuery;
import com.sandu.api.fullhouse.output.DesignPlanVO;
import com.sandu.service.designplan.dao.DesignPlanRecommendedDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by zhangchengda
 * 2018/8/16 13:57
 * 推荐方案业务服务
 */
@Service("designPlanRecommendedBizService")
public class DesignPlanRecommendedBizServiceImpl implements DesignPlanRecommendedBizService {
    @Autowired
    private DesignPlanRecommendedDao designPlanRecommendedDao;

    private static final int DEFAULT_CURRENT_PAGE = 1;

    private static final int DEFAULT_PAGE_SIZE = 20;

    private static final int DEFAULT_START = 0;

    private static final int SPACE_TYPE_KITCHEN = 5;

    /**
     * created by zhangchengda
     * 2018/8/16 14:00
     * 查询当前登录用户的已审核通过的方案组合的主方案（空间类型为厨房时则查询一键方案），按方案创建时间降序排列
     * @param query 查询参数
     * @return 返回方案的集合
     */
    @Override
    public List<DesignPlanVO> selectGroupPrimaryDesignPlan(DesignPlanQuery query) {
        // 处理分页
        if (query.getCurPage() == null || query.getCurPage() <= 0){
            query.setCurPage(DEFAULT_CURRENT_PAGE);
        }
        if (query.getPageSize() == null || query.getPageSize() <= 0){
            query.setPageSize(DEFAULT_PAGE_SIZE);
        }
        if (query.getPageSize() != null && query.getCurPage() != null){
            query.setStart((query.getCurPage() - 1) * query.getPageSize());
        }else {
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
        return designPlanVOList;
    }

    /**
     * created by zhangchengda
     * 2018/8/16 16:02
     * 查询当前登录用户的已审核通过的方案组合的主方案（空间类型为厨房时则查询一键方案）总数
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
}
