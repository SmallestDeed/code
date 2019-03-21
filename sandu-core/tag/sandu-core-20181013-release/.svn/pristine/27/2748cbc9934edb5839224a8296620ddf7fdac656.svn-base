import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sandu.DesignProvider;
import com.sandu.api.base.common.exception.BizExceptionEE;
import com.sandu.api.fullhouse.service.FullHouseDesignPlanService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DesignProvider.class)
public class Test {

	@Autowired
	private FullHouseDesignPlanService fullHouseDesignPlanService;
	
	@org.junit.Test
	public void test0001() throws BizExceptionEE {
		System.out.println(fullHouseDesignPlanService.getMatchInfo(367546, 3));
	}
	
}
