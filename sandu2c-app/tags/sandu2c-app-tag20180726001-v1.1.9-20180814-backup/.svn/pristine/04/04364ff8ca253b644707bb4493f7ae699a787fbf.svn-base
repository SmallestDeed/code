/*
import com.sandu.DesignPlanProvider;
import com.sandu.common.model.PageModel;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.common.objectconvert.user.UserObject;
import com.sandu.design.model.ProductsCost;
import com.sandu.design.model.search.DesignTempletSearch;
import com.sandu.designplan.dao.DesignPlanRecommendedProductMapper;
import com.sandu.designplan.model.DesignPlan;
import com.sandu.designplan.model.DesignPlanRecommended;
import com.sandu.designplan.model.PlanRecommendedListModel;
import com.sandu.designplan.service.DesignPlanRecommendedProductService;
import com.sandu.designplan.service.DesignPlanRecommendedService;
import com.sandu.designplan.service.DesignPlanService;
import com.sandu.designplan.vo.RecommendedPlanProductRelatedVo;
import com.sandu.designtemplate.dao.DesignTempletMapper;
import com.sandu.product.model.ProductCostDetail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DesignPlanProvider.class)
public class DesignPlanTest {

    @Autowired
    private DesignPlanService designPlanService;
    @Autowired
    private DesignTempletMapper designTempletMapper;
    @Autowired
    private DesignPlanRecommendedService designPlanRecommendedService;
    @Autowired
    private DesignPlanRecommendedProductService designPlanRecommendedProductService;
    
    @Autowired
    private DesignPlanRecommendedProductMapper designPlanRecommendedProductMapper;

    //测试查询设计方案
    @Test
    public void getDesignPlanTest() {
        DesignTempletSearch designTempletSearch = new DesignTempletSearch();

        //DesignPlan designPlan = designPlanService.get(6214);
        DesignPlan designPlan = designPlanService.get(6214);

        //int selectCount = designTempletMapper.selectCount(designTempletSearch);
        String a = "";

    }

    //查询产品相关推荐方案
    @Test
    public void queryRecommendedPlanOfProductRelatedByProductIdTest() {
        List<RecommendedPlanProductRelatedVo> recommendedPlanProductRelatedList = designPlanRecommendedProductService.queryRecommendedPlanOfProductRelatedByProductId(208880, Arrays.asList(2574));
        String a = "";
    }

    //查询推荐方案List
    @Test
    public void queryRecommendList(){
        PlanRecommendedListModel planRecommendedListModel = new PlanRecommendedListModel();
        planRecommendedListModel.setLimit(10);
        planRecommendedListModel.setStart(0);

        List<Integer> brandList = Arrays.asList(2390, 2563, 2564, 2566, 2567, 2574, 2597);
        planRecommendedListModel.setBrandList(brandList);

        ResponseEnvelope planRecommendedList = designPlanRecommendedService.getPlanRecommendedList(planRecommendedListModel);

        String a = "";
    }
    
    //
    @Test
    public void getProductCost(){
    	ProductsCost cost = new ProductsCost();
    	cost.setPlanId(86486);
    	List<ProductCostDetail> costDetail = designPlanRecommendedProductMapper.costDetail(cost);

        String a = "";
        System.out.println(a);
    }
    
    @Test
    public void getProductCost2(){
    	String str = ".110000.110100.110115.";
    	String[] strs = str.split(".");
        String a = "";
        System.out.println(a);
    }

    @Test
    public void getRenderPicFromDesignPlanRecommendTest(){
        DesignPlanRecommended designPlanRecommended = new DesignPlanRecommended();
            designPlanRecommended.setId(87184);
        DesignPlanRecommended renderPicFromDesignPlanRecommend = designPlanRecommendedService.getRenderPicFromDesignPlanRecommend(designPlanRecommended);
        String a = "";
    }
}


*/
