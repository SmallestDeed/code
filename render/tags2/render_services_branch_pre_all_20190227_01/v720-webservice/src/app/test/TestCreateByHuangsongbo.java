package app.test;

import java.util.ArrayList;
import java.util.List;


import com.nork.common.model.ResponseEnvelope;
import com.nork.common.properties.ResProperties;
import com.nork.common.util.collections.Lists;
import com.nork.design.dao.DesignPlanAutoRenderMapper;
import com.nork.design.model.AutoRenderTask;
import com.nork.mobile.common.MobileCommonConstants;
import com.nork.platform.model.BasePlatform;
import com.nork.platform.service.BasePlatformService;
import com.nork.system.dao.ResRenderPicMapper;
import com.nork.system.model.ResRenderPic;
import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
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
	private BasePlatformService basePlatformService;

	@Autowired
	private BaseProductService baseProductService;

	@Autowired
	private DesignPlanAutoRenderMapper designPlanAutoRenderMapper;

	@Autowired
	private ResRenderPicMapper resRenderPicMapper;

	public void renderRefendTest(){
		SysTask sysTask = new SysTask();
		sysTask.setId(3512);
		renderTaskService.renderRefund(sysTask, null);
	}

	@Test
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

	@Test
	public void test003(){
		BaseProduct baseProduct = new BaseProduct();
		String productCode = "wu_E02_0232_001_cuki_0001";
		baseProduct.setProductCode(productCode);
		baseProduct.setIsDeleted(0);
		List<BaseProduct> baseProducts = baseProductService.getList(baseProduct);
		System.out.println(baseProducts.size());
	}


	@Test
	public void test004(){
		long s1 = System.currentTimeMillis();
		AutoRenderTask autoRenderTask = new AutoRenderTask();
		autoRenderTask.setPlatformCode("mobile2b");
		autoRenderTask.setPlatformBussinessType("2c");
		autoRenderTask.setOperationUserId(42341);
		autoRenderTask.setStart(0);
		autoRenderTask.setLimit(10);
		String platformCode = autoRenderTask.getPlatformCode();
		BasePlatform basePlatform = basePlatformService.getByCode(platformCode);
		//平台业务类型
		String platformType = basePlatform.getPlatformBussinessType();
		autoRenderTask.setPlatformBussinessType(platformType);
		// 平台Id
		Integer platformId = basePlatform.getId();
		autoRenderTask.setPlatformId(platformId);
		//所有渲染任务
		List<AutoRenderTask> allTaskList = new ArrayList<AutoRenderTask>();

		Integer count = designPlanAutoRenderMapper.selectUnionCount(autoRenderTask);

		if(null != count && 0<count){
			allTaskList = designPlanAutoRenderMapper.selectUnionList(autoRenderTask);
			if(Lists.isNotEmpty(allTaskList)){
				this.setTask(allTaskList);
			}
		}



		long s6 = System.currentTimeMillis();



		System.out.println("响应时间:"+(s6-s1));
	}

	private void setTask(List<AutoRenderTask> allTaskList) {
		for(AutoRenderTask autoRenderTask : allTaskList){
			//处理任务状态
			switch (autoRenderTask.getRenderTypesStr()){
				case "1":
					autoRenderTask.setIsSuccess(autoRenderTask.getRenderPic());
					break;
				case "2":
					autoRenderTask.setIsSuccess(autoRenderTask.getRender720());
					break;
				case "4":
					autoRenderTask.setIsSuccess(autoRenderTask.getRenderVideo());
					break;
				case "3":
					autoRenderTask.setIsSuccess(autoRenderTask.getRenderN720());
					break;
				default:
					break;
			}

		}
	}


	private AutoRenderTask setTaskSuccessState(AutoRenderTask task) {
		if ("1".equals(task.getRenderTypesStr())) {
			task.setIsSuccess(task.getRenderPic());
		} else if ("2".equals(task.getRenderTypesStr())) {
			task.setIsSuccess(task.getRender720());
		} else if ("4".equals(task.getRenderTypesStr())) {
			task.setIsSuccess(task.getRenderVideo());
		} else if ("3".equals(task.getRenderTypesStr())) {
			task.setIsSuccess(task.getRenderN720());
		}
		return task;
	}



	private List<AutoRenderTask> setTaskSmallPicPath(List<AutoRenderTask> allStartedTaskList, List<Integer> sceneIds, List<Integer> recommendIds) {
		ResRenderPic pic = new ResRenderPic();
		if (Lists.isNotEmpty(allStartedTaskList)) {
			pic.setSceneIds(sceneIds);
			pic.setIsDeleted(new Integer(0));
			List<String> fileKeyList = new ArrayList<>();
			fileKeyList.add(ResProperties.DESIGNPLAN_RENDER_PIC_SMALL_FILEKEY);
			fileKeyList.add(ResProperties.DESIGNPLAN_RENDER_VIDEO_COVER);
			pic.setFileKeyList(fileKeyList);
			List<ResRenderPic> picList = resRenderPicMapper.getReplaceRenderResult(pic);
			for (AutoRenderTask task : allStartedTaskList) {
				if (task.getIsSuccess() == 0 || task.getIsSuccess() == 1) {
					//未渲染或渲染中，显示渲染中的图片
					task.setSmallPicPath("/default/myReplaceRecord_rendering_thumb.gif");
				} else if (task.getIsSuccess() == 2 && task.getBusinessId() != null) {
					//渲染成功，显示效果图的缩略图
					ResRenderPic resRenderPic = (ResRenderPic) CollectionUtils.find(picList, new BeanPropertyValueEqualsPredicate("designSceneId", task.getBusinessId()));
					if (resRenderPic != null) {
						task.setSmallPicPath(resRenderPic.getPicPath());
					} else {
						sceneIds.clear();
						recommendIds.clear();
						fileKeyList.clear();
						if (MobileCommonConstants.RENDER_TASK_OP_SOURCE_OF_MYDESIGN.equals(task.getOperationSource())) {
							sceneIds.add(task.getPlanId());
							fileKeyList.add(ResProperties.DESIGNPLAN_RENDER_PIC_SMALL_FILEKEY);
						} else if (MobileCommonConstants.RENDER_TASK_OP_SOURCE_OF_RECOMMEND.equals(task.getOperationSource())) {
							recommendIds.add(task.getPlanId());
							fileKeyList.add(ResProperties.DESIGNPLANRECOMMENDED_PIC_SMALL_FILEKEY);
						}
						pic.setRecommendIds(recommendIds);
						pic.setSceneIds(sceneIds);
						pic.setFileKeyList(fileKeyList);
						List<ResRenderPic> sourcePlanPicList = resRenderPicMapper.getReplaceRenderResult(pic);
						if (sourcePlanPicList != null && sourcePlanPicList.size() > 0) {
							task.setSmallPicPath(sourcePlanPicList.get(0).getPicPath());
						} else {
							task.setSmallPicPath("/default/myReplaceRecord_fail_thumb.png");
						}
					}
				} else {
					//显示渲染失败的图片
					task.setSmallPicPath("/default/myReplaceRecord_fail_thumb.png");
				}
			}

		}
		return allStartedTaskList;
	}

}
