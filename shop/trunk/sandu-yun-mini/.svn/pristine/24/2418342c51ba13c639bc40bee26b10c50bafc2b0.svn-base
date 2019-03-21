package com.sandu.company.service.impl;

import com.sandu.company.dao.CompanyShopArticleDao;
import com.sandu.company.model.CompanyShopArticle;
import com.sandu.company.model.query.CompanyShopDetailQuery;
import com.sandu.company.model.query.ShopArticleQuery;
import com.sandu.company.model.vo.CompanyShopArticleDetailVO;
import com.sandu.company.model.vo.CompanyShopArticleListVO;
import com.sandu.company.model.vo.CompanyShopDetailVo;
import com.sandu.company.service.CompanyShopArticleService;
import com.sandu.company.service.CompanyShopService;
import com.sandu.resource.model.vo.ResPicVo;
import com.sandu.resource.service.ResPicService;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 店铺博文--前端展示--Service层接口实现
 * @author WangHaiLin
 * @date 2018/8/10  15:58
 */
@Service("companyShopArticleService")
public class CompanyShopArticleServiceImpl implements CompanyShopArticleService {
    private static final Logger logger = LoggerFactory.getLogger(CompanyShopArticleServiceImpl.class.getName());
    @Autowired
    private CompanyShopArticleDao companyShopArticleDao;
    @Autowired
    private ResPicService resPicService;
    @Autowired
    private CompanyShopService companyShopService;

    /**
     * 获取博文列表
     * @param query 查询入参
     * @return List
     */
    @Override
    public List<CompanyShopArticleListVO> getList(ShopArticleQuery query) {
        //根据店铺Id获取博文列表
        List<CompanyShopArticleListVO> list = companyShopArticleDao.getList(query);
        if (null!=list&&list.size()>0){
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //循环列表，取图片Id获取图片路径
            for (CompanyShopArticleListVO article:list) {
                //处理发布时间显示
                String releaseTimeStr = format.format(article.getReleaseTime());
                article.setReleaseTimeStr(releaseTimeStr);
                // 取富文本图片第一个图
                if (StringUtils.isNotEmpty(article.getPicIds())) {
                    List<Integer> idList = this.getIntegerListFromStringList(article.getPicIds());
                    if (idList != null && 0 < idList.size()) {
                        if (0!=Long.valueOf(idList.get(0))){
                            ResPicVo resPicVo = resPicService.getPicById(Long.valueOf(idList.get(0)));
                            logger.info("查询店铺博文列表  查询展示图片信息结果 ResPicVo:{}",resPicVo.toString());
                            logger.info("查询店铺博文列表  查询展示图片信息结果 ResPicVo:{}",resPicVo.getPicPath());
                            if (null!=resPicVo.getPicPath()){
                                article.setPicPath(resPicVo.getPicPath());
                            }
                        }
                    }
                }
            }
        }
        return list;
    }


    /**
     * 查询博文数量
     * @param query 店铺Id
     * @return 数量
     */
    @Override
    public Integer getCount(ShopArticleQuery query) {
        return companyShopArticleDao.getCount(query);
    }

    /**
     * 查看博文详情
     * @param articleId 博文Id
     * @return CompanyShopArticleDetailVO
     */
    @Override
    public CompanyShopArticleDetailVO get(Long articleId) {
        CompanyShopArticleDetailVO detailVO = companyShopArticleDao.get(articleId);
        if (detailVO.getSid() != null) {
            CompanyShopArticleDetailVO personDetail = companyShopArticleDao.getDetail(detailVO.getSid().longValue());
            if (personDetail != null) {
                detailVO.setShopId(personDetail.getShopId());
            }
        }
        CompanyShopDetailQuery query = new CompanyShopDetailQuery();
        query.setShopId(detailVO.getShopId().longValue());
        CompanyShopDetailVo shop = companyShopService.get(query);
        detailVO.setShopDetail(shop);
        //发布时间处理
        if (null!=detailVO&&null!=detailVO.getReleaseTime()){
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String releaseTimeStr = format.format(detailVO.getReleaseTime());
            detailVO.setReleaseTimeStr(releaseTimeStr);
        }
        return detailVO;
    }


    /**
     * 修改浏览次数
     * @param article 博文实体
     * @return boolean
     */
    @Override
    public boolean updateBrowseCount(CompanyShopArticle article) {
        int result = companyShopArticleDao.updateBrowseCount(article);
        if (result>0){
            return true;
        }
       return false;
    }




    public static List<Integer> getIntegerListFromStringList(String str) {
        List<Integer> list = new ArrayList();
        if (!StringUtils.isBlank(str) && !"null".equals(str)) {
            String[] strs = str.split(",");
            String[] var3 = strs;
            int var4 = strs.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String idStr = var3[var5];
                if (!StringUtils.isBlank(idStr)) {
                    list.add(Integer.parseInt(idStr));
                }
            }

            return list;
        } else {
            return list;
        }
    }
}
