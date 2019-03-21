package com.sandu.service.shop.impl.biz;

import com.sandu.api.shop.model.CompanyShop;
import com.sandu.api.shop.service.*;
import com.sandu.api.shop.service.biz.ShopBizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangHaiLin
 * @date 2018/8/31  9:47
 */
@Service("shopBizService")
public class ShopBizServiceImpl implements ShopBizService {

    private Logger logger = LoggerFactory.getLogger(ShopBizServiceImpl.class);

    @Autowired
    private CompanyShopService companyShopService;
    @Autowired
    private CompanyShopArticleService companyShopArticleService;
    @Autowired
    private CompanyShopDesignPlanService designPlanService;
    @Autowired
    private ProjectCaseService projectCaseService;


    @Override
    @Transactional
    public int delUsersShop(Long userId,String loginUserName) {
        //1.通过用户Id查找属于用户的店铺集合
        List<CompanyShop> shopList = companyShopService.getShopListByUserId(userId);
        //2.用户店铺集合不为空，取出店铺id,放入list中
        List<Integer> shopIds=new ArrayList<>();
        if (null!=shopList&&shopList.size()>0){
            for (CompanyShop shop:shopList) {
                shopIds.add(shop.getId());
            }
            //查看要删除的店铺是否存在工程案例
            Integer count = projectCaseService.getCaseByShopId(shopIds);
            logger.info("待删除店铺工程案例总数量：{}",count);
            //存在，通过店铺Id删店铺发布的工程案例
            if (count>0){
                Integer result = projectCaseService.deleteCaseByShopId(shopIds, loginUserName);
                logger.info("工程案例删除结果：{}",result);
            }

            //查看要删除的店铺是否存在博文
            Integer count2 = companyShopArticleService.getArticleByShopId(shopIds);
            logger.info("待删除店铺博文总数量：{}",count2);
            //存在，通过店铺Id删店铺发布的博文
            if (count2>0){
                Integer result =companyShopArticleService.deleteArticleByShopId(shopIds,loginUserName);
                logger.info("博文删除结果：{}",result);
            }

            //查看要删除的店铺是发布方案
            Integer count3 = designPlanService.getDesignPlanByShopId(shopIds);
            logger.info("待删除店铺方案总数量：{}",count3);
            //存在，通过店铺Id删店铺发布的方案
            if (count3>0){
                Integer result =designPlanService.deleteDesignPlanByShopId(shopIds);
                logger.info("方案删除结果：{}",result);
            }

            //通过用户Id删用户创建的店铺
            return companyShopService.deleteShopByUserId(userId,loginUserName);
        }
        return -1;
    }
}
