package com.sandu.service.category.impl.biz;

import com.sandu.api.category.input.ProCategoryListQuery;
import com.sandu.api.category.model.output.CategoryAndIndustryVO;
import com.sandu.api.category.output.CategoryListVO;
import com.sandu.api.category.output.ProCategoryListVO;
import com.sandu.api.category.service.ProCategoryService;
import com.sandu.api.category.service.biz.ProCategoryBizService;
import com.sandu.api.company.common.StringUtil;
import com.sandu.api.company.service.CompanyCategoryRelService;
import com.sandu.api.dictionary.output.DictionaryTypeListVO;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.commons.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author chenqiang
 * @Description 产品分类 逻辑 业务层
 * @Date 2018/6/1 0001 10:26
 * @Modified By
 */
@Service("proCategoryBizService")
public class ProCategoryBizServiceImpl implements ProCategoryBizService {
    private Logger logger = LoggerFactory.getLogger(ProCategoryBizServiceImpl.class);

    @Autowired
    private ProCategoryService proCategoryService;

    @Autowired
    private CompanyCategoryRelService companyCategoryRelService;

    @Autowired
    private DictionaryService dictionaryService;


    /**
     * 获取企业可见产品范围
     * @author chenqiang
     * @param productVisibilityRange 企业分类ids
     * @return 分类集合
     * @date 2018/5/31 0031 18:21
     */
    public List<ProCategoryListVO> getCompanyCategoryList(String productVisibilityRange){

        List<Integer> idListAll =  getIdListAll(productVisibilityRange);
        String categoryIdAll = getStringByList(idListAll);

        //1.初始参数
        ProCategoryListQuery query = new ProCategoryListQuery();
        query.setCategoryId(1);
        query.setLevel(3);

        //2.获取list数据
        List<ProCategoryListVO> categoryList = proCategoryService.getCategoryListByPidLevel(query);
        categoryList = this.getProCategoryList(categoryList,categoryIdAll);

        return null == categoryList ? new ArrayList<>() : categoryList;
    }

    /**
     * 获取经销商可见产品范围
     * @author chenqiang
     * @param productVisibilityRange 经销商分类ids
     * @return 分类集合
     * @date 2018/5/31 0031 18:21
     */
    public List<ProCategoryListVO> getFranchiserCategoryList(Integer companyId, String productVisibilityRange){

        //1.获取企业产品分类ids
        String categoryIds = companyCategoryRelService.getCompanyCategoryIds(companyId);

        //2.判断企业是否具有可见产品范围
        if(StringUtils.isEmpty(categoryIds)){
            return new ArrayList<>();
        }

        //3.获取经销商企业全部产品分类
        List<Integer> idListAll =  getIdListAll(productVisibilityRange);
        String categoryFranchiserIdAll = getStringByList(idListAll);


        //4.获取经销商所属企业 3层分类Ids
        List<Integer> idListRoom = new ArrayList<>();
        String[] categoryIdss = categoryIds.split(",");
        idListRoom.add(0);
        for (String categoryId : categoryIdss) {
            if(StringUtils.isNotEmpty(categoryId))
                idListRoom.add(Integer.parseInt(categoryId));
        }
        List<Integer> idListTwo = proCategoryService.getPCategoryListByIdList(idListRoom);
        List<Integer> idListOne = proCategoryService.getPCategoryListByIdList(idListTwo);


        //5.获取list数据
        List<ProCategoryListVO> categoryList = proCategoryService.getListByIdListPid(idListOne,1);
        for (ProCategoryListVO proCategoryListVO : categoryList) {
            if(categoryFranchiserIdAll.contains(proCategoryListVO.getCategoryId().toString()))
                proCategoryListVO.setIsChecked(1);

            List<ProCategoryListVO> categoryListTwo = proCategoryService.getListByIdListPid(idListTwo,proCategoryListVO.getCategoryId());
            proCategoryListVO.setList(categoryListTwo);
        }
        for (ProCategoryListVO proCategoryListVO : categoryList) {
            for (ProCategoryListVO categoryListVO : proCategoryListVO.getList()) {
                if(categoryFranchiserIdAll.contains(categoryListVO.getCategoryId().toString()))
                    categoryListVO.setIsChecked(1);

                List<ProCategoryListVO> categoryListThree = proCategoryService.getListByIdListPid(idListRoom,categoryListVO.getCategoryId());
                for (ProCategoryListVO listVO : categoryListThree) {
                    if(categoryFranchiserIdAll.contains(listVO.getCategoryId().toString()))
                        listVO.setIsChecked(1);
                }
                categoryListVO.setList(categoryListThree);
            }
        }

        return null == categoryList ? new ArrayList<>() : categoryList;
    }


    /**
     * 通过企业所属行业关联可见产品范围
     * @param newIndustryList 新所属行业Value
     * @param oldIndustryList 旧所属行业Value
     * @param oldCategoryList 旧可见产品范围
     * @return CategoryAndIndustryVO
     */
    @Override
    public CategoryAndIndustryVO getCategoryByCompanyIndustry(List<Integer> newIndustryList, List<Integer> oldIndustryList, List<Integer> oldCategoryList) {
        //1.定义一些需要用到的变量
        List<Integer> oldCategoryList2=new ArrayList<>();//企业原可见产品范围中不属于企业原所属行业带来的可见产品范围集合
        List<Integer> oldCategoryList3=new ArrayList<>();//企业原可见产品范围中属于企业原所属行业带来的可见产品范围集合
        List<Integer> newCategoryList=new ArrayList<>();//企业由所属行业变化而变化的可见产品范围集合
        List<Integer> finalCategoryList=new ArrayList<>();//企业最终可见产品范围集合
        List<Integer> oldCategoryList4=new ArrayList<>();//旧可见范围中有,新所属行业下可见范围中没有
        String industryNames=null;//企业所属行业名
        List<Integer> industryValue=new ArrayList<>();//企业所属行业值
        String categoryNames=null;//可见产品分类名
        List<Integer> categoryIds=new ArrayList<>();//可见产品分类Id集合
        CategoryAndIndustryVO categoryAndIndustryVO=new CategoryAndIndustryVO();//最终返回结果


        //2.判断新所属行业value集合且有值,获取新所属行业下的可见产品范围
        if (null!=newIndustryList&&newIndustryList.size()>0){
            List<DictionaryTypeListVO> industrys = dictionaryService.getListByTypeOrValues("industry", newIndustryList);
            logger.info("新所属业查询结果：{}",industrys);

            //循环查询新的行业下的可见产品
            StringBuilder sb=new StringBuilder();
            newCategoryList=new ArrayList<>();
            for (DictionaryTypeListVO dictionary:industrys) {
                String categoryCodes = dictionary.getAtt2();

                List<CategoryListVO> newCategoryListVO=null;
                sb.append(dictionary.getName());
                sb.append(",");
                if (StringUtils.isNotBlank(categoryCodes)){
                    industryValue.add(dictionary.getValue());
                    List<String> codeList = StringUtil.getStringListByString(categoryCodes);
                    newCategoryListVO= proCategoryService.getListByCodeList(codeList, 3 + "");
                    logger.info("新可见行业关联的可见产品分类查询结果：{}",newCategoryListVO);
                }

                if (null!=newCategoryListVO&&newCategoryListVO.size()>0){
                    for (CategoryListVO category:newCategoryListVO){
                        if (null!=category.getCategoryId()){newCategoryList.add(category.getCategoryId());
                        }

                    }
                }

            }
            logger.info("新所属行业关联的可见产品分类查询结果：{}",newCategoryList);
            String s = sb.toString();
            industryNames =s.length() == 0 ?s :s.substring(0, s.length()-1);//所属行业名称集(aa,bb,cc)
            logger.info("新可见行业关联的可见产品分类处理结束，新所属行业名称：{}",industryNames);
        }


        //3.判断原所属行业value集合且有值,得到原所属行业关联的可见产品范围Id集合
        if (oldIndustryList!=null&&oldIndustryList.size()>0){
            List<DictionaryTypeListVO> dictionaryVOList = dictionaryService.getListByTypeOrValues("industry", oldIndustryList);
            logger.info("原所属行业查询结果dictionaryVOList:{}"+dictionaryVOList);
            for (DictionaryTypeListVO dictionaryVO:dictionaryVOList){
                String oldCodes = dictionaryVO.getAtt2();
                if (StringUtils.isNotBlank(oldCodes)){
                    List<String> oldCodeList = StringUtil.getStringListByString(oldCodes);
                    List<CategoryListVO> oldCategoryListVO = proCategoryService.getListByCodeList(oldCodeList, 3 + "");
                    logger.info("原所属行业关联的可见产品范围查询结果oldCategoryListVO:{}"+oldCategoryListVO);
                    for (CategoryListVO category:oldCategoryListVO){
                        oldCategoryList3.add(category.getCategoryId());//原所属行业关联的可见产品范围Id集合(可能会不存在于oldCategoryList中)
                    }
                }
            }
        }

        //旧所属行业为空,新的所属行业也为空
        if (oldCategoryList3.size()<=0&&newCategoryList.size()<=0){
            logger.info("旧所属行业为空,新的所属行业也为空");
            finalCategoryList.addAll(oldCategoryList);
        }
        //旧所属行业为空，新所属行业不为空
        else if (oldCategoryList3.size()<=0&&newCategoryList.size()>0){
            logger.info("旧所属行业为空，新所属行业不为空");
            logger.info("cccccc{}",oldCategoryList);
            if (oldCategoryList.size()>0&&newCategoryList.size()>0){
                oldCategoryList4=getExistOneListNotExistOtherList(oldCategoryList,newCategoryList);
            }

            //新所属行业关联可见产品范围不为空
            if (newCategoryList.size()>0){
                finalCategoryList.addAll(newCategoryList);
            }
            //原可见产品范围中不存在于新所属行业关联可见产品
            if (oldCategoryList4.size()>0){
                finalCategoryList.addAll(oldCategoryList4);
            }
            //去重
            Set<Integer> set=new HashSet<>(finalCategoryList);
            finalCategoryList=new ArrayList<>(set);

        }
        //旧所属行业不为空，新所属行业为空
        else if(0<oldCategoryList3.size()&&0>=newCategoryList.size()){
            logger.info("旧所属行业不为空，新所属行业为空");
            //取出原可见产品中不属于原可见行业关联的可见产品
            if (oldCategoryList.size()>0&&oldCategoryList3.size()>0){
                oldCategoryList2=getExistOneListNotExistOtherList(oldCategoryList,oldCategoryList3);
            }
            logger.info("hhhhh{}",oldCategoryList2);
            //存在不属于原所属行业下的可见产品范围
            if (oldCategoryList2.size()>0){
                finalCategoryList.addAll(oldCategoryList2);
            }
            //去重
            Set<Integer> set=new HashSet<>(finalCategoryList);
            finalCategoryList=new ArrayList<>(set);
        }
        //旧所属行业不为空，新所属行业不为空
        else if (0<oldCategoryList3.size()&&0<newCategoryList.size()){
            logger.info("旧所属行业不为空，新所属行业不为空");
            //旧所属行业与新所属行业相等
            if (oldCategoryList3.equals(newCategoryList)){
                finalCategoryList.addAll(oldCategoryList);
            }else{
                //取出原可见产品中不属于原所属行业关联的可见产品
                logger.info("企业原可见产品id集合{}",oldCategoryList);
                logger.info("企业原所属行业关联可见产品id集合{}",oldCategoryList3);
                if (oldCategoryList.size()>0&&oldCategoryList3.size()>0){
                    oldCategoryList2=getExistOneListNotExistOtherList(oldCategoryList,oldCategoryList3);
                }
                //新可见产品=新所属行业关联产品+原可见产品中不属于原行业关联的产品
                //新所属行业关联可见产品范围不为空
                if (newCategoryList.size()>0){
                    logger.info("ttttt:{}",newCategoryList);
                    finalCategoryList.addAll(newCategoryList);
                    logger.info("YYYYY:{}",finalCategoryList);
                }
                //存在不属于原所属行业下的可见产品范围
                if (oldCategoryList2.size()>0){
                    logger.info("rrrrr:{}",oldCategoryList2);
                    finalCategoryList.addAll(oldCategoryList2);
                    logger.info("YYYYY:{}",finalCategoryList);
                }
                //去重
                Set<Integer> set=new HashSet<>(finalCategoryList);
                finalCategoryList=new ArrayList<>(set);

            }
        }

        logger.info("企业最终可见产品范围集合：{}",finalCategoryList);
        if (finalCategoryList.size()>0){
            categoryNames = proCategoryService.getCategoryNames(finalCategoryList);
        }
        logger.info("企业最终可见产品名称：{}",categoryNames);

        //6.给返回结果赋值
        categoryIds.addAll(finalCategoryList);
        categoryAndIndustryVO.setCategoryNames(categoryNames);
        categoryAndIndustryVO.setIndustryNames(industryNames);
        categoryAndIndustryVO.setIndustryValues(industryValue);
        categoryAndIndustryVO.setCategoryIds(categoryIds);

        logger.info("处理完最终返回结果：{}",categoryAndIndustryVO);
        return categoryAndIndustryVO;

    }


    /**
     * wanghailin
     * 取存在一个list里面而不存在另外一个list里面的元素
     * @param oneList 被取list
     * @param otherList 比较list
     * @return List 返回结果
     * 1050, 2003, 2004, 2005, 9498, 9496, 2007, 2003, 4457
     * 1050, 2003, 2004, 2005, 2006, 2007, 2008, 4457, 4516, 9497,
     */
    private static List<Integer> getExistOneListNotExistOtherList(List<Integer> oneList,List<Integer> otherList){
        List<Integer> returnList=new ArrayList<>();
        for (Integer element:oneList) {
            if (!otherList.contains(element)){
                returnList.add(element);
            }
        }
        return returnList;
    }




    private List<Integer> getIdListAll(String categoryIds){

        if(StringUtils.isEmpty(categoryIds))
            return null;
        List<Integer> idListRoom = new ArrayList<>();
        String[] categoryIdss = categoryIds.split(",");
        idListRoom.add(0);
        for (String categoryId : categoryIdss) {
            if(StringUtils.isNotEmpty(categoryId))
                idListRoom.add(Integer.parseInt(categoryId));
        }
        List<Integer> idListTwo = proCategoryService.getPCategoryListByIdList(idListRoom);
        List<Integer> idListOne = proCategoryService.getPCategoryListByIdList(idListTwo);
        idListRoom.addAll(idListTwo);
        idListRoom.addAll(idListOne);

        return idListRoom;
    }

    private String getStringByList(List<Integer> idListRoom){

        if( null == idListRoom)
            return "";

        StringBuffer stringBuffer = new StringBuffer();
        for (Integer integer : idListRoom) {
            stringBuffer.append(integer+",");
        }

        return stringBuffer.toString();
    }

    private List<ProCategoryListVO> getProCategoryList(List<ProCategoryListVO> categoryList,String categroyIds){
        if(null == categoryList || categoryList.size() <= 0){
            return null;
        }

        for (ProCategoryListVO categoryListVO : categoryList) {

            //判断处理对象
            if(categroyIds.contains(categoryListVO.getCategoryId().toString()))
                categoryListVO.setIsChecked(1);

            /** 进行下次循环 */
            //1.参数
            ProCategoryListQuery query = new ProCategoryListQuery();
            query.setCategoryId(categoryListVO.getCategoryId());
            query.setLevel(3);
            //2.获取集合
            List<ProCategoryListVO> categoryListvo = proCategoryService.getCategoryListByPidLevel(query);
            categoryListVO.setList(categoryListvo);
            this.getProCategoryList(categoryListvo,categroyIds);

        }

        return categoryList;
    }




}

