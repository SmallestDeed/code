package com.sandu.service.company.impl.biz;

import com.github.pagehelper.PageInfo;
import com.sandu.api.area.output.BaseAreaListVO;
import com.sandu.api.area.service.BaseAreaService;
import com.sandu.api.brand.model.Brand;
import com.sandu.api.brand.service.BrandService;
import com.sandu.api.category.model.Category;
import com.sandu.api.category.service.CategoryService;
import com.sandu.api.company.input.CompanyShopQuery;
import com.sandu.api.company.input.CompanyUpdate;
import com.sandu.api.company.model.Company;
import com.sandu.api.company.model.CompanyCategoryRel;
import com.sandu.api.company.model.bo.CompanyBO;
import com.sandu.api.company.model.bo.CompanyBrandBo;
import com.sandu.api.company.model.bo.CompanyShopListBO;
import com.sandu.api.company.service.CompanyCategoryRelService;
import com.sandu.api.company.service.CompanyService;
import com.sandu.api.company.service.CompanyShopService;
import com.sandu.api.company.service.biz.CompanyBizService;
import com.sandu.api.decorateorder.model.DecorateOrder;
import com.sandu.api.decorateorder.model.DecorateOrderScore;
import com.sandu.api.decorateorder.service.DecorateOrderService;
import com.sandu.api.dictionary.model.Dictionary;
import com.sandu.api.dictionary.service.DictionaryService;
import com.sandu.api.user.model.User;
import com.sandu.api.user.service.UserService;
import com.sandu.commons.constant.SysDictionaryConstant;
import com.sandu.constant.Punctuation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Sandu
 */
@Service("companyBizService")
@Slf4j
public class CompanyBizServiceImpl implements CompanyBizService {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyCategoryRelService companyCategoryRelService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;

	@Autowired
	private CompanyShopService companyShopService;

	@Autowired
	private DecorateOrderService decorateOrderService;

	@Autowired
	private UserService userService;

	@Autowired
	private BaseAreaService baseAreaService;

	@Autowired
	private DictionaryService dictionaryService;

    @Override
    public boolean saveCompanyBiz(CompanyBO companyBO) {
        if (companyBO != null) {
            //保存company
            Company company = new Company();
            int companyId = companyService.saveCompany(company);
            //保存company_Category_rel
            List<CompanyCategoryRel> lists = new ArrayList<>();
            for (Integer id : Arrays.stream(companyBO.getCategoryIds().split(Punctuation.COMMA))
                    .map(Integer::parseInt).collect(Collectors.toList())) {
                CompanyCategoryRel ccrel = new CompanyCategoryRel();
                ccrel.setCompanyId(companyId);
                ccrel.setCategoryId(id);
                lists.add(ccrel);
                //保存系统字段
                /*  ...  */
            }
            companyCategoryRelService.saveCompanyCategoryList(lists);
        }
        return false;
    }

    @Override
    public Map<Integer, CompanyBrandBo> getCompanyByBrandIds(List<Integer> brandIds) {
        if (brandIds.isEmpty()) {
            return null;
        }
        List<CompanyBrandBo> list = companyService.getCompanyByBrandIds(brandIds);
        Map<Integer, CompanyBrandBo> map = new HashMap<>(list.size());
        for (CompanyBrandBo tmp : list) {
            map.put(tmp.getBrandId(), tmp);
        }
        return map;
    }

    @Override
    public CompanyBO getCompanyInfoById(long id) {
        CompanyBO bo = companyService.getCompanyInfoById(id);
        if (bo == null) {
            return null;
        }
        List<Category> nodes;
        List<Integer> categoryIds = null;
        if (StringUtils.isNoneBlank(bo.getCategoryIds())) {
            categoryIds = Arrays.stream(bo.getCategoryIds().split(Punctuation.COMMA)).
                    map(Integer::parseInt).collect(Collectors.toList());


        }
        if (categoryIds != null && !categoryIds.isEmpty()) {
            nodes = categoryService.getTreeLevelNodeByCategoryIds(categoryIds);
            List<String> list = new ArrayList<>();
            for (Category tmp : nodes) {
                list.add(tmp.getName());
            }
            bo.setCategoryNames(list);
        }
        return bo;
    }

    @Override
    public boolean updateCompanyInfo(CompanyUpdate companyUpdate) {
        int ret = 0;
        if (companyUpdate.getId() != null) {
            Company company = new Company();
            company.setId(companyUpdate.getId());
            company.setCompanyLogo(companyUpdate.getLogo());
            company.setCompanyDomainName(companyUpdate.getDomain());
            company.setCompanyCustomerQq(companyUpdate.getQq());
            ret = companyService.updateCompany(company);
        }
        Brand brand = new Brand();
        if (companyUpdate.getBrandId() != null) {
            brand.setId(companyUpdate.getBrandId());
            brand.setBrandLogo(companyUpdate.getLogo().toString());
            ret = brandService.updateBrand(brand);
        }
        return ret > 0;
    }

	@Override
	public PageInfo<CompanyShopListBO> listCompanyShop(CompanyShopQuery query) {

    	if(query.getCostRage() != null && query.getHouseAcreage() != null){
			Dictionary decorateBudget = dictionaryService.getByTypeAndValue("decorateBudget", query.getCostRage());
    		query.setDesignFeeStarting(Double.valueOf(decorateBudget.getAtt1()) / query.getHouseAcreage());
    		query.setDesignFeeEnding(Double.valueOf(decorateBudget.getAtt2()) / query.getHouseAcreage());
		}
		query.setIsReceiveInteriorDispatch(1);
		PageInfo<CompanyShopListBO> page = companyShopService.listCompanyShop(query);
		List<CompanyShopListBO> list = page.getList();
		//查询相关数据
		Set<Integer> companyIds = list.stream().filter(Objects::nonNull).map(CompanyShopListBO::getCompanyId).collect(Collectors.toSet());
		List<User> users = userService.listUserByCompanyIds(companyIds);
		List<Dictionary> styleTypes = dictionaryService.listByType(SysDictionaryConstant.DIRECTION_GOOD_STYLE);
		//获取相关企业
		Map<Long, String> companyMap = companyService.idAndNameMap(new ArrayList<>(companyIds));

		List<CompanyShopListBO> bos = list.stream()
				.filter(it -> it.getCompanyId() != null)
				.peek(bo -> setShopShowInfo(users, styleTypes, companyMap, bo, query.getHouseAcreage(), Collections.emptyList()))
				.sorted(Comparator.comparing(CompanyShopListBO::getOverage).reversed())
				.collect(Collectors.toList());

		page.setList(bos);
		return page;
	}

	@Override
	public List<Company> listCompanyForCustomerOrder(Integer id) {
		List<DecorateOrder> orders = decorateOrderService.listOrderByCustomerId(id);
		return companyService.idAndNameMap(orders.stream().map(DecorateOrder::getCompanyId).collect(Collectors.toList()))
				.entrySet().stream().map(it -> {
					Company company = new Company();
					company.setId(it.getKey());
					company.setCompanyName(it.getValue());
					return company;
				}).collect(Collectors.toList());
	}

	private void setShopShowInfo(List<User> users, List<Dictionary> styleTypes,
								 Map<Long, String> companyMap, CompanyShopListBO shop, Integer houseAcreage, List<DecorateOrderScore> scores) {
		//获取此公司所有订单
//		List<DecorateOrder> all = decorateOrderService.listOrderByCompanyId(shop.getCompanyPid());

//		shop.setCompanyId(shop.getCompanyId());
		shop.setCompanyName(companyMap.get(shop.getCompanyId().longValue()));

		//城市
		if (StringUtils.isNotBlank(shop.getCityCode())) {
			shop.setCityCode(shop.getCityCode());
			BaseAreaListVO baseArea = baseAreaService.queryAreaByCode(shop.getCityCode());
			if (baseArea != null) {
				shop.setCityName(baseArea.getAreaName());
			}
		}

		//擅长风格
		if (StringUtils.isNotBlank(shop.getCategoryIds())) {
			shop.setGoodStyle(Arrays.stream(shop.getCategoryIds().split(Punctuation.COMMA)).map(Integer::new).collect(Collectors.toList()));
			styleTypes
					.stream().filter(dic -> shop.getGoodStyle().contains(dic.getValue()))
					.forEach(dic -> {
						if (shop.getGoodStyleName() == null || shop.getGoodStyleName().isEmpty()) {
							shop.setGoodStyleName(new LinkedList<>());
						}
						shop.getGoodStyleName().add(dic.getName());
					});
		}

		//费用
		if (shop.getDesignFeeStarting() != null && shop.getDesignFeeEnding() != null) {
			shop.setCostRange(shop.getDesignFeeStarting() * houseAcreage + "-" + shop.getDesignFeeEnding() * houseAcreage);
		}

		//成交量
//		bo.setDealNumber(all.size());

		//好评率
//		AtomicInteger count = new AtomicInteger();
//		scores.stream().filter(it -> shop.getCompanyId().equals(it.getCompanyId()))
//				.reduce((o1, o2) -> {
//					count.getAndIncrement();
//					o1.setScore(o1.getScore() + o2.getScore());
//					return o1;
//				})
//				.ifPresent(it -> bo.setPraiseRate(it.getScore() * 1.0 / count.get()));


		//余额
		users.stream().filter(user -> user.getCompanyId().equals(shop.getCompanyId()))
				.reduce((o1, o2) -> {
					double value = Double.valueOf(o1.getBalanceAmount()) + Double.valueOf(o2.getBalanceAmount());
					o1.setBalanceAmount(value + "");
					return o1;
				})
				.ifPresent(user -> shop.setOverage(Double.valueOf(user.getBalanceAmount()).intValue()));
		if (shop.getOverage() == null) {
			shop.setOverage(0);
		}
	}


}
