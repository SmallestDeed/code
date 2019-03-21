package com.sandu.rendermachine.controller.product;

import com.sandu.rendermachine.common.cache.ProCategoryCacher;
import com.sandu.rendermachine.common.util.Utils;
import com.sandu.rendermachine.model.product.ProCategory;
import com.sandu.rendermachine.model.product.ProCategorySearch;
import com.sandu.rendermachine.model.product.SearchProCategorySmall;
import com.sandu.rendermachine.model.response.ResponseEnvelope;
import com.sandu.rendermachine.service.product.ProCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author Gao Jun
 * @Description
 * @Date:Created Administrator in 下午 3:08 2018/4/23 0023
 * @Modified By:
 */
@Slf4j
@Controller
@RequestMapping("/{style}/web/product/proCategory")
public class ProductCategoryController {

    @Autowired
    private ProCategoryService proCategoryService;

    /**
     * 分类查询接口
     * @return
     */
    @RequestMapping(value = "/searchProCategory")
    @ResponseBody
    public Object searchProCategory(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "type",required = false) String type,
                                    @RequestParam(value = "cid",required = false) String cid,
                                    @RequestParam(value = "msgId",required = false) String msgId){
        long startTime = System.currentTimeMillis();
        String msg = "";
        if(StringUtils.isBlank(msgId)){
            msg = "参数msgId不能为空";
            return new ResponseEnvelope<SearchProCategorySmall>(false,msg,msgId);
        }
        if(StringUtils.isBlank(type)){
            msg = "参数type不能为空";
            return new ResponseEnvelope<SearchProCategorySmall>(false,msg,msgId);
        }
        List<SearchProCategorySmall> categoryList = new ArrayList<SearchProCategorySmall>();
        if(StringUtils.isNotBlank(type)){
            ProCategory category = null;
            SearchProCategorySmall categorySmall = null;
            int total = 0;
            //查询所有类目
            if("A".equals(type)){
                if(Utils.enableRedisCache()){
                    category =(ProCategory) ProCategoryCacher.getAllList();
                }else{
                    category = proCategoryService.getAllCategory();
                }
            }else{
                if(StringUtils.isNotBlank(cid)){
                    //查询pid等于cid的所有产品类目
                    if( "P".equals(type) ){
                        if(Utils.enableRedisCache()){
                            category = ProCategoryCacher.get(Integer.valueOf(cid));
                        }else{
                            category = proCategoryService.get(Integer.valueOf(cid));
                        }

                        categoryList = recursionCategory(category);

                        MyCompartor compartor = new MyCompartor();
                        Collections.sort(categoryList,compartor);

                        category.setChildrenNodes(categoryList);
                        //查询ID等cid的类目
                    }else if( "O".equals(type) ){
                        if(Utils.enableRedisCache()){
                            category = ProCategoryCacher.get(Integer.valueOf(cid));
                        }else{
                            category = proCategoryService.get(Integer.valueOf(cid));
                        }
                    }
                }else{
                    msg = "参数type为‘"+type+"’时，必须传入cid参数";
                    return new ResponseEnvelope<SearchProCategorySmall>(false,msg,msgId);
                }
            }
            if(category != null){
                categorySmall = new SearchProCategorySmall();
                categorySmall.setGg(category.getNameFirstLetter());
                categorySmall.setAa(category.getId());
                categorySmall.setBb(category.getCode());
                categorySmall.setCc(category.getPid());
                categorySmall.setDd(category.getName());
                categorySmall.setFf(category.getChildrenNodes() == null ? new ArrayList<SearchProCategorySmall>() : category.getChildrenNodes());
                List<SearchProCategorySmall> resultList = new ArrayList<SearchProCategorySmall>();
                resultList.add(categorySmall);
                long endTime = System.currentTimeMillis();
                long executeTime = endTime - startTime;

                log.info("searchProCategory executeTime : " + executeTime + "ms");

                MyCompartor compartor = new MyCompartor();
                Collections.sort(resultList,compartor);

                return new ResponseEnvelope<SearchProCategorySmall>(total, resultList,msgId);
            }
        }else{
            msg = "参数type不能为空";
            return new ResponseEnvelope<SearchProCategorySmall>(false,msg,msgId);
        }
        return new ResponseEnvelope<SearchProCategorySmall>(false,msg,msgId);
    }

    /**
     * 递归查询分类
     * @param category
     * @return
     */
    private List<SearchProCategorySmall> recursionCategory(ProCategory category){
        List<SearchProCategorySmall> childrenNodes = category.getChildrenNodes();
        List<ProCategory> list = null;

        if(Utils.enableRedisCache()){
            ProCategory pc = new ProCategory();
            pc.setPid(category.getId());
            pc.setLongCode(category.getLongCode());
            list = ProCategoryCacher.getAllList(pc);
        }else{
            ProCategorySearch search = new ProCategorySearch();
            search.setPid(category.getId());
            search.setLongCode(category.getLongCode());
            list = proCategoryService.getList(search);
        }

        if( list != null && list.size() > 0 ){
            if( childrenNodes == null ){
                childrenNodes = new ArrayList<SearchProCategorySmall>();
            }
            SearchProCategorySmall newCategory = null;
            SearchProCategorySmall newCategory_ = null;
            for( ProCategory childrenNode : list ){
                if("其他".equals(childrenNode.getName())){
                    newCategory_ = new SearchProCategorySmall();
                    newCategory_.setAa(childrenNode.getId());
                    newCategory_.setCc(childrenNode.getPid());
                    newCategory_.setBb(childrenNode.getCode());
                    newCategory_.setDd(childrenNode.getName());
                    newCategory_.setFf(recursionCategory(childrenNode));
                }else{
                    newCategory = new SearchProCategorySmall();
                    newCategory.setAa(childrenNode.getId());
                    newCategory.setCc(childrenNode.getPid());
                    newCategory.setBb(childrenNode.getCode());
                    newCategory.setDd(childrenNode.getName());
                    newCategory.setFf(recursionCategory(childrenNode));
                    childrenNodes.add(newCategory);
                }
            }
            if(newCategory_!=null){
                childrenNodes.add(newCategory_);
            }
            category.setChildrenNodes(childrenNodes);
        }
        return childrenNodes;
    }

    //1、排序升序 2、name拼音首字排序
    class MyCompartor implements Comparator
    {
        @Override
        public int compare(Object o1, Object o2)
        {
            ProCategory sdto1= (ProCategory )o1;
            ProCategory sdto2= (ProCategory )o2;
            int flag = (sdto1.getOrdering() == null ? new Integer(0) : new Integer(sdto1.getOrdering()))
                    .compareTo(sdto2.getOrdering() == null ? new Integer(0) : new Integer(sdto2.getOrdering()));
            if (flag != 0) {
                return (sdto1.getOrdering() == null ? new Integer(0) : new Integer(sdto1.getOrdering()))
                        .compareTo(sdto2.getOrdering() == null ? new Integer(0)
                                : new Integer(sdto2.getOrdering()));
            } else {
                Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);
                return com.compare(sdto1.getName(), sdto2.getName());
            }
        }
    }
}
