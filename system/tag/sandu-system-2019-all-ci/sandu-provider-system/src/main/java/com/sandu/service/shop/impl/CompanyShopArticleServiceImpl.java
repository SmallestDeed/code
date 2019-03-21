package com.sandu.service.shop.impl;

import com.sandu.api.pic.model.ResPic;
import com.sandu.api.pic.service.ResPicService;
import com.sandu.api.shop.input.CompanyShopArticleAdd;
import com.sandu.api.shop.input.CompanyShopArticleQuery;
import com.sandu.api.shop.input.CompanyShopArticleReleaseUpdate;
import com.sandu.api.shop.input.CompanyShopArticleUpdate;
import com.sandu.api.shop.model.CompanyShopArticle;
import com.sandu.api.shop.output.CompanyShopArticleDetailVO;
import com.sandu.api.shop.output.CompanyShopArticleListVO;
import com.sandu.api.shop.service.CompanyShopArticleService;
import com.sandu.commons.LoginUser;
import com.sandu.commons.Utils;
import com.sandu.commons.util.StringUtils;
import com.sandu.service.shop.dao.CompanyShopArticleDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 店铺博文--Service层接口实现类
 * @author WangHaiLin
 * @date 2018/8/9  10:57
 */
@Service("companyShopArticleService")
public class CompanyShopArticleServiceImpl implements CompanyShopArticleService {

    private Logger logger = LoggerFactory.getLogger(CompanyShopArticleServiceImpl.class);

    @Autowired
    private CompanyShopArticleDao articleDao;

    @Autowired
    private ResPicService resPicService;

    /**
     * WangHaiLin
     * 新增博文
     * @param articleAdd 新增对象
     * @param loginUser 当前用户对象
     * @return id
     */
    @Override
    public Long add(CompanyShopArticleAdd articleAdd, LoginUser loginUser) {
        //数据转换
        CompanyShopArticle companyShopArticle=new CompanyShopArticle();
        companyShopArticle.setContent(articleAdd.getContent());
        companyShopArticle.setTitle(articleAdd.getTitle());
        companyShopArticle.setShopId(articleAdd.getShopId());
        if (org.apache.commons.lang3.StringUtils.isNotBlank(articleAdd.getPicIds())){
            companyShopArticle.setPicIds(articleAdd.getPicIds());
        }else{
            companyShopArticle.setPicIds(0+"");
        }
        //处理系统字段
        this.saveSysInfo(companyShopArticle,loginUser);
        //向数据库插入数据
        int result = articleDao.add(companyShopArticle);
        if (result>0){
            if (articleAdd.getShopType() == null || articleAdd.getShopType() == 2) {
                Integer mainShopId = articleDao.getMainCompanyShopId(articleAdd.getShopId().intValue());
                if (mainShopId != null && mainShopId != 0) {
                    companyShopArticle.setShopId(mainShopId.longValue());
                    companyShopArticle.setSid(companyShopArticle.getId());
                    articleDao.add(companyShopArticle);
                }
            }
            return companyShopArticle.getId();
        }else {
            return 0L;
        }

    }

    /**
     * WangHaiLin
     * 更具Id删除博文
     * @param id 主键Id
     * @param loginUser 当前登录用户对象
     * @return
     */
    @Override
    public int delete(Long id, LoginUser loginUser, Integer shopType) {
        //获取要删除的店铺博文
        CompanyShopArticle article = articleDao.get(id);
        //修改删除状态
        article.setIsDeleted(1);
        //处理系统字段
        this.saveSysInfo(article,loginUser);
        //修改博文删除状态并返回
        int res = articleDao.update(article);
        if (res > 0) {
            if (shopType == null || shopType == 2) {
                Integer mainShopId = articleDao.getMainCompanyShopId(article.getShopId().intValue());
                if (mainShopId != null && mainShopId != 0) {
                    Long mainId = articleDao.getMainShopArticleId(id, mainShopId);
                    article.setId(mainId);
                    article.setShopId(mainShopId.longValue());
                    articleDao.update(article);
                }
            }
        }
        return res;
    }

    /**
     * WangHaiLin
     * 修改博文
     * @param articleUpdate 修改对象
     * @param loginUser 当前登录用户对象
     * @return
     */
    @Override
    public int update(CompanyShopArticleUpdate articleUpdate, LoginUser loginUser) {
        //获取旧值
        CompanyShopArticle article=articleDao.get(articleUpdate.getArticleId());
        //为要修改的值赋新值
        article.setShopId(articleUpdate.getShopId());
        article.setTitle(articleUpdate.getTitle());
        article.setContent(articleUpdate.getContent());
        if (org.apache.commons.lang3.StringUtils.isNotBlank(articleUpdate.getPicIds())){
            article.setPicIds(articleUpdate.getPicIds());
        }else{
            article.setPicIds(0+"");
        }
        //存储系统字段
        this.saveSysInfo(article,loginUser);
        //直接返回操作结果
        int res = articleDao.update(article);
        if (res > 0) {
            if (articleUpdate.getShopType() == null || articleUpdate.getShopType() == 2) {
                Integer mainShopId = articleDao.getMainCompanyShopId(articleUpdate.getShopId().intValue());
                if (mainShopId != null && mainShopId != 0) {
                    Long mainId = articleDao.getMainShopArticleId(articleUpdate.getArticleId(), mainShopId);
                    article.setId(mainId);
                    article.setShopId(mainShopId.longValue());
                    articleDao.update(article);
                }
            }
        }
        return res;
    }

    /**
     * WangHaiLin
     * 查询店铺博文列表
     * @param query 查询条件
     * @return list
     */
    @Override
    public List<CompanyShopArticleListVO> findList(CompanyShopArticleQuery query) {
        List<CompanyShopArticleListVO> listVO=new ArrayList<>();
        //查询列表
        List<CompanyShopArticle> articleList = articleDao.getList(query);
        //数据转换  构造输出实体并处理图片地址
        for (CompanyShopArticle article:articleList){
            CompanyShopArticleListVO articleListVO=new CompanyShopArticleListVO();
            articleListVO.setArticleId(article.getId());
            articleListVO.setArticleTitle(article.getTitle());
            articleListVO.setCreateDate(article.getGmtCreate());
            articleListVO.setReleaseStatus(article.getReleaseStatus());
            // 取富文本图片第一个图
            if (StringUtils.isNotEmpty(article.getPicIds())) {
                List<Integer> idList = Utils.getIntegerListFromStringList(article.getPicIds());
                if (idList != null && 0 < idList.size()) {
                    if (0!=Long.valueOf(idList.get(0))){
                        ResPic resPic = resPicService.selectByPrimaryKey(Long.valueOf(idList.get(0)));
                        logger.info("查询店铺博文列表  查询展示图片信息结果 ResPic:{}",resPic);
                        if (null!=resPic){
                            articleListVO.setPicPath(resPic.getPicPath());
                        }
                    }
                }
            }
            listVO.add(articleListVO);
        }
        return listVO;
    }

    /**
     * 查询满足条件的店铺博文数量
     * @param query 查询条件
     * @return
     */
    @Override
    public Integer getCount(CompanyShopArticleQuery query) {
        return articleDao.getCount(query);
    }

    /**
     * WangHaiLin
     * 查询店铺博文详情
     * @param articleId 店铺博文Id
     * @return CompanyShopArticleDetailVO
     */
    @Override
    public CompanyShopArticleDetailVO select(Long articleId) {
        //查询数据
        CompanyShopArticle article = articleDao.get(articleId);
        //构建输出结果
        if (null!=article){
            CompanyShopArticleDetailVO detailVO=new CompanyShopArticleDetailVO();
            detailVO.setArticleId(article.getId());
            detailVO.setShopId(article.getShopId());
            detailVO.setTitle(article.getTitle());
            detailVO.setContent(article.getContent());
            detailVO.setPicIds(article.getPicIds());
            //处理图片地址
            if (StringUtils.isNotEmpty(article.getPicIds())) {
                List<Integer> idList = Utils.getIntegerListFromStringList(article.getPicIds());
                if (idList != null && 0 < idList.size()) {
                    List<ResPic> list=new ArrayList<>();
                    for (Integer picId:idList){
                        if (0!=picId){
                            ResPic resPic = resPicService.selectByPrimaryKey(Long.valueOf(picId));
                            logger.info("查询店铺博文列表  查询展示图片信息结果 ResPic:{}",resPic);
                            if (null!=resPic){
                                list.add(resPic);
                            }
                        }
                    }
                    detailVO.setResPics(list);
                }
            }
            return detailVO;
        }
        return null;
    }

    /**
     * 发布博文
     * @param update 博文发布状态修改入参
     * @return boolean
     */
    @Override
    public boolean release(CompanyShopArticleReleaseUpdate update, LoginUser loginUser){
        //获取要发布的店铺博文
        CompanyShopArticle article = articleDao.get(update.getArticleId());
        //修改发布状态
        article.setReleaseStatus(update.getReleaseStatus());
        //处理系统字段
        this.saveSysInfo(article,loginUser);
        //如果是发布，则添加发布时间
        if (1==article.getReleaseStatus()){
            article.setReleaseTime(new Date());
        }
        //修改博文发布状态
        int res = articleDao.update(article);
        if (res > 0) {
            if (update.getShopType() == null || update.getShopType() == 2) {
                Integer mainShopId = articleDao.getMainCompanyShopId(article.getShopId().intValue());
                if (mainShopId != null && mainShopId != 0) {
                    Long mainId = articleDao.getMainShopArticleId(update.getArticleId(), mainShopId);
                    article.setId(mainId);
                    article.setShopId(mainShopId.longValue());
                    articleDao.update(article);
                }
            }
        }
        if (res>0){
            return true;
        }
        return false;
    }

    /**
     * 通过店铺Id进行批量删除
     * @param shopId 店铺Id
     * @param loginUser 当前用户对象
     * @return
     */
    @Override
    public int deleteByShopId(Long shopId, LoginUser loginUser) {
        return articleDao.deleteByShopId(shopId,loginUser.getLoginName());
    }

    @Override
    public Integer deleteArticleByShopId(List<Integer> shopIds,String loginUserName) {
        return articleDao.deleteArticleByShopId(shopIds,loginUserName);
    }

    @Override
    public Integer getArticleByShopId(List<Integer> shopIds) {
        return articleDao.getArticleByShopId(shopIds);
    }

    @Override
    public Integer getMainCompanyShopId(Integer shopId){
        if (shopId == null) {
            return null;
        }
        return articleDao.getMainCompanyShopId(shopId);
    }

    @Override
    public Integer updateMainShopArticleStatus(Integer mainShopId, Integer shopId, Integer status) {
        return articleDao.updateMainShopArticleStatus(mainShopId, shopId, status);
    }

    /**
     * 存储系统字段
     * @param article 店铺博文实体
     * @param loginUser 当前登录用户
     */
    private void saveSysInfo(CompanyShopArticle article,LoginUser loginUser){
        if (article != null) {
            //新增
            if (article.getId() == null) {
                article.setGmtCreate(new Date());
                article.setCreator(loginUser.getLoginName());
                article.setReleaseStatus(0);
                article.setIsDeleted(0);
                if (article.getSysCode() == null || "".equals(article.getSysCode())) {
                    article.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) + "_" + Utils.generateRandomDigitString(6));
                }
            }
            article.setGmtModified(new Date());
            article.setModifier(loginUser.getLoginName());
        }
    }
}
