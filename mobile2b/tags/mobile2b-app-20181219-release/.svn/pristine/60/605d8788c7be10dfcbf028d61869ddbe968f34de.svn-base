package app.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nork.decorateOrder.constant.DecorateSeckillConstants;
import com.nork.decorateOrder.service.DecorateOrderService;
import com.nork.decorateOrder.service.DecorateSeckillService;
import com.nork.product.model.BaseProduct;
import com.nork.product.service.BaseProductService;
import com.nork.render.service.RenderTaskService;
import com.nork.system.model.SysDictionary;
import com.nork.system.service.SysDictionaryService;
import com.nork.task.model.SysTask;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring.xml")
public class TestCreateByHuangsongbo {
	
	@Autowired
	private RenderTaskService renderTaskService;
	
	@Autowired
	private SysDictionaryService sysDictionaryService;
	
	@Autowired
	private BaseProductService baseProductService;
	
	@Autowired
	private DecorateSeckillService decorateSeckillService;
	
	@Autowired
	private DecorateOrderService decorateOrderService;
	
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
		//System.out.println(totalFee);
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
	
	@Test
	public void test004() {
		List<Long> idList = new ArrayList<Long>(Arrays.asList(1143L, 1142L));
		decorateSeckillService.update(null, DecorateSeckillConstants.DECORATESECKILLSEARCH_STATUS_NOT_BEING_SNAPPED_UP, idList);
	}
	
	@Test
	public void test005() {
		decorateOrderService.updateOverTimeOrder();
	}
	
}
