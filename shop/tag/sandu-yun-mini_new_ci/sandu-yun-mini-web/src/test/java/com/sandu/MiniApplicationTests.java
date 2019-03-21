package com.sandu;

import com.sandu.company.model.query.ProjectCaseQuery;
import com.sandu.company.model.vo.ProjectCaseVo;
import com.sandu.company.service.CompanyShopService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SanduYunMiniWebApplication.class)
public class MiniApplicationTests {

	@Autowired
	private CompanyShopService companyShopService;
	@Test
	public void contextLoads() {
		ProjectCaseQuery projectCaseQuery=new ProjectCaseQuery();
		//projectCaseQuery.setCaseId(11);
		//projectCaseQuery.setPageType("detail");
		projectCaseQuery.setShopId(148);
		List<ProjectCaseVo> shopProjectCaseList = companyShopService.getShopProjectCaseList(projectCaseQuery);
		System.out.println(shopProjectCaseList.toString());
		//companyShopService.updateBrowseCount(11L,1);
	}

}
