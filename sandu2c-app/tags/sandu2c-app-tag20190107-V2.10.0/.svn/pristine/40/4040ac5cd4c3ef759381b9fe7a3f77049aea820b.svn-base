/*import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.sandu.ProductProvider;
import com.sandu.common.model.ResponseEnvelope;
import com.sandu.product.dao.BaseProductMapper;
import com.sandu.product.model.BaseCompany;
import com.sandu.product.model.CategoryProductResult;
import com.sandu.product.model.ProCategory;
import com.sandu.product.model.ProCategoryPo;
import com.sandu.product.model.ProducDetail;
import com.sandu.product.model.ProductCategoryRel;
import com.sandu.product.model.ProductListVo;
import com.sandu.product.service.BaseProductService;
import com.sandu.product.service.ProductCategoryRelService;
import com.sandu.system.exception.DomainConfigurationException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=ProductProvider.class)
public class ProductServiceTest{

    private static final Gson GSON = new Gson();
    @Autowired
    private BaseProductService baseProductService;
    @Autowired
    private ProductCategoryRelService productCategoryRelService;

    //测试查询我的方案
    @Test
    public void getAllProduc() {
    	long startTime = System.currentTimeMillis();
    	List<List<ProCategoryPo>> list = baseProductService.getAllProductCategory(28);
    	System.err.println(System.currentTimeMillis() - startTime);
    	String json = GSON.toJson(list);
    	System.out.println(json);
        String a = "";
        System.out.println(a);

    }
    
    @Test
    public void getAllProduc2() {
    	List<Integer> ids = new ArrayList<>();
    	ids.add(325);
    	ids.add(326);
    	ids.add(327);
    	Integer countByIds = baseProductService.getAllProductCountByIds(ids);
    	List<ProductListVo> ids2 = baseProductService.getAllProductByIds(ids, null, null);

    	String json = GSON.toJson(ids2);
    	System.out.println(json);
        String a = "";
        System.out.println(a);

    }


    @Test
    public void getAllProduct() {
    	ProductCategoryRel productCategoryRel = new ProductCategoryRel();
    	productCategoryRel.setCategoryCode("diz0203");

  		if( productCategoryRel.getCategoryCode().contains(",") ){
  			String[] arr = productCategoryRel.getCategoryCode().split(",");
  			productCategoryRel.setCategoryIdList(Arrays.asList(arr));
  		}else{
  			productCategoryRel.setCategoryCode("."+productCategoryRel.getCategoryCode()+".");
  		}


    	int countByCode = productCategoryRelService.findAllProductCountByCode(productCategoryRel);
    	List<CategoryProductResult> productResultByCode = productCategoryRelService.findAllProductResultByCode(productCategoryRel, null);
    	System.out.println(countByCode+"");
    	System.out.println(GSON.toJson(productResultByCode));
    	
        String a = "";
        System.out.println(a);
    }
    
    
    
    
    @Test
    public void getProductDetail() {
    	ProducDetail productDetail = baseProductService.getProducDetail(54318,null);
    	System.out.println(GSON.toJson(productDetail));
    	String str = ".110000.110100.110115.";
    	String substring = str.substring(1, str.length()-1);
    	String[] split = substring.split("\\.");
        String a = "";
        System.out.println(a);
        
    }
}


*/