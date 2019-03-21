package com.nork.product.controller;

import java.text.Collator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.nork.common.model.ResponseEnvelope;
import com.nork.common.util.Utils;
import com.nork.product.cache.ProCategoryCacher;
import com.nork.product.model.ProCategory;
import com.nork.product.model.search.ProCategorySearch;
import com.nork.product.model.small.SearchProCategorySmall;
import com.nork.product.service.ProCategoryService;

/**
 * @version V1.0
 * @Title: ProductCategoryRelController.java
 * @Package com.nork.product.controller
 * @Description:产品模块-产品与产品类目关联Controller
 * @createAuthor pandajun
 * @CreateDate 2015-06-17 14:50:47
 */
@Controller
@RequestMapping("/{style}/web/product/proCategory")
public class WebProductCategoryController {
    private static Logger logger = Logger.getLogger(WebProductCategoryController.class);
    private final String STYLE = "online";
    private final String JSPSTYLE = "online";
    private final String MAIN = "/" + STYLE + "/product/proCategory";
    private final String BASEMAIN = "/" + STYLE + "/product/proCategory";
    private final String JSPMAIN = "/" + JSPSTYLE + "/product/proCategory";
    @Autowired
    private ProCategoryService proCategoryService;

    @InitBinder
    public void initBinder(WebDataBinder binder) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, true));
    }

	

	/**
	 * 分类查询接口供移动端调用
	 * @return
	 */
	@RequestMapping(value = "/searchProCategory")
	@ResponseBody
	public Object searchProCategory(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "type",required = false) String type,
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
				//ServletContext servletContext=request.getSession().getServletContext();
				//category =(ProCategory)servletContext.getAttribute("_allCategory");
//				if(Utils.enableRedisCache()){
//					category =(ProCategory)ProCategoryCacher.getAllList();
//				}else{
	            	category = proCategoryService.getAllCategory();
//	            }

				/*if(category == null){
					//category = proCategoryService.get(1);
		            ProCategory pc = new ProCategory();
		            pc.setState("0");
		            pc.setOrder(" ordering");
		            List<ProCategory> categoryAllList=null;
		            if(Utils.enableRedisCache()){
		            	categoryAllList = ProCategoryCacher.getAllList(pc);
		            }else{
		            	categoryAllList = proCategoryService.getList(pc);
		            }

		            if(categoryAllList!= null && categoryAllList.size()>=0){
		            	for(ProCategory ppc :categoryAllList){
		            		if(ppc.getId().intValue() == 1){
		            			category = ppc;
		            			break;
		            		}
		            	}
		            }
					//
					//categoryList = recursionCategory(category);
		            categoryList = recursionCategory2(category,categoryAllList);
					category.setChildrenNodes(categoryList);
				}*/
			}else{
				if(StringUtils.isNotBlank(cid)){
					//查询pid等于cid的所有产品类目
					if( "P".equals(type) ){
//						if(Utils.enableRedisCache()){
//							category = ProCategoryCacher.get(Integer.valueOf(cid));
//						}else{
							category = proCategoryService.get(Integer.valueOf(cid));
//						}

						categoryList = recursionCategory(category);

						MyCompartor compartor = new MyCompartor();
						Collections.sort(categoryList,compartor);

						category.setChildrenNodes(categoryList);
					//查询ID等cid的类目
					}else if( "O".equals(type) ){
//						if(Utils.enableRedisCache()){
//							category = ProCategoryCacher.get(Integer.valueOf(cid));
//						}else{
							category = proCategoryService.get(Integer.valueOf(cid));
//						}
						
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

			    logger.info("searchProCategory executeTime : " + executeTime + "ms");

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
