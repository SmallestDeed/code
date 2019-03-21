package com.sandu.web.company.controller;

import com.sandu.commons.PageHelper;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.company.model.CompanyShopArticle;
import com.sandu.company.model.query.ShopArticleQuery;
import com.sandu.company.model.vo.CompanyShopArticleDetailVO;
import com.sandu.company.model.vo.CompanyShopArticleListVO;
import com.sandu.company.service.CompanyShopArticleService;
import com.sandu.matadata.ResultCode;
import com.sandu.matadata.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 店铺博文---前端展示--controller层
 * @author WangHaiLin
 * @date 2018/8/10  17:31
 */

@Api(description = "店铺博文", tags = "ShopArticle")
@RestController
@RequestMapping(value = "/v1/sandu/shop/article")
public class CompanyShopArticleController {

    private static final Logger logger = LoggerFactory.getLogger(CompanyShopArticleController.class.getName());

    @Autowired
    private CompanyShopArticleService articleService;


    @ApiOperation(value = "博文详情", response = ResultMessage.class)
    @ApiImplicitParam(name = "articleId", value = "博文Id", paramType = "query", dataType = "long", required = true)
    @GetMapping("/detail")
    public ResponseEnvelope articleDetail(@RequestParam("articleId") Long articleId) {

        try {
            //校验入参
            if (null == articleId || articleId.equals("")) {
                return new ResponseEnvelope(false, "参数错误");
            }
            //获取详情
            CompanyShopArticleDetailVO articleDetailVO = articleService.get(articleId);
            //获取详情成功，修改浏览次数
            if (null != articleDetailVO) {
                Integer browseCount = 0;
                if (null != articleDetailVO.getBrowseCount()) {
                    browseCount = articleDetailVO.getBrowseCount();
                }
                browseCount++;
                CompanyShopArticle article = new CompanyShopArticle();
                article.setId(articleDetailVO.getArticleId());
                article.setBrowseCount(browseCount);
                //修改浏览数量
                boolean res = articleService.updateBrowseCount(article);
                if (res) {
                    ResponseEnvelope response = new ResponseEnvelope();
                    response.setMessage("获取详情成功");
                    response.setObj(articleDetailVO);
                    response.setSuccess(true);
                    return response;
                } else {
                    return new ResponseEnvelope(false, "修改博文浏览数量失败");
                }
            } else {
                return new ResponseEnvelope(false, "获取博文详情失败");
            }
        } catch (Exception e) {
            logger.error("博文详情  方法： 系统异常", e);
            return new ResponseEnvelope(false, "博文详情  方法：系统异常");
        }
    }



    @ApiOperation(value = "获取博文列表", response = ResultMessage.class)
    @GetMapping("/list")
    public ResponseEnvelope articleList(ShopArticleQuery query) {
        try{
            //校验入参
            if (null==query){
                return  new ResponseEnvelope(false,"参数错误");
            }
            Integer count = articleService.getCount(query);
            //处理分页
            PageHelper page = PageHelper.getPage(count, query.getLimit(), query.getPage());
            query.setStart(page.getStart());
            query.setLimit(page.getPageSize());
            if (count>0){
                List<CompanyShopArticleListVO> list = articleService.getList(query);
                if (null!=list&&list.size()>0){
                    return new ResponseEnvelope<>(true, count, list);
                }
            }
            return new ResponseEnvelope<>(true, 0, null);
        }catch (Exception e){
            logger.error("博文列表  方法： 系统异常",e);
            return new ResponseEnvelope(false,"博文列表  方法：系统异常");
        }
    }

}
