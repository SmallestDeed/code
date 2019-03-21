package app.test;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nork.product.model.BaseProduct;
import com.nork.product.service.BaseProductService;
import com.nork.render.service.RenderTaskService;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.SysDictionaryService;
import com.nork.task.model.SysTask;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring.xml",
		"classpath:spring/spring-mybatis.xml",
		"classpath:spring/spring-servlet.xml" })
public class TestCreateByHuangsongbo {
	
	@Autowired
	private RenderTaskService renderTaskService;
	
	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	@Autowired
	private BaseProductService baseProductService;
	
	@org.junit.Test
	public void renderRefendTest(){
		SysTask sysTask = new SysTask();
		sysTask.setId(3512);
		renderTaskService.renderRefund(sysTask, null);
	}
	
	@org.junit.Test
	public void test002(){
		String type = "renderType";
		Integer value = 1;
		int totalFee = 1;
		SysDictionary sysDictionary = sysDictionaryService.findOneByTypeAndValue(type, value);
		if(sysDictionary == null){
			throw new RuntimeException("------支付类数据字典未找到:type=" + type + ";mode=" + value);
		}
		String totalFeeStr = sysDictionary.getAtt1();
		if(org.apache.commons.lang3.StringUtils.isNotBlank(totalFeeStr)){
			totalFee = Integer.parseInt(totalFeeStr);
		}
		System.out.println(totalFee);
	}

	@org.junit.Test
	public void test003(){
		BaseProduct baseProduct = new BaseProduct();
		String productCode = "wu_E02_0232_001_cuki_0001";
		baseProduct.setProductCode(productCode);
		baseProduct.setIsDeleted(0);
		List<BaseProduct> baseProducts = baseProductService.getList(baseProduct);
		//System.out.println(baseProducts.size());
	}
	
}
