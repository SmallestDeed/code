import com.sandu.order.service.MallBaseOrderService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName: TestBaseOrder
 * @Auther: gaoj
 * @Date: 2019/2/2 15:44
 * @Description:
 * @Version 1.0
 */
// @RunWith(SpringJUnit4ClassRunner.class)
// @WebAppConfiguration
// @ContextConfiguration(locations = {"classpath:/spring/spring.xml", "classpath:/application.properties", "classpath:/app.properties"})
public class TestBaseOrder extends JunitActionBase{

    // @Autowired
    // WebApplicationContext context;
    // MockMvc mockMvc;

    @Autowired
    MallBaseOrderService mallBaseOrderService;

    // @Before
    // public void before() {
    //     mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    // }

    // @Test
    // public void testUserDeletedOrder() {
    //     this.mockMvc.perform(post("/v1/miniprogram/order/userDeletedOrder").param("orderId",1));
    // }

    @Test
    public void testUserDeletedOrder() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setServletPath("/v1/miniprogram/order/userDeletedOrder");
        request.addParameter("orderId","1");
        request.setMethod("post");
        request.setAttribute("msg", "测试成功");
        final ModelAndView mav = this.excuteAction(request, response);
        System.out.println(mav.getViewName());
    }
}
