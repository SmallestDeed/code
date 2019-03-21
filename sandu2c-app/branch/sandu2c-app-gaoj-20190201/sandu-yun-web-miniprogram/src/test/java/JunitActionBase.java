import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: JunitActionBase
 * @Auther: gaoj
 * @Date: 2019/2/2 16:23
 * @Description:
 * @Version 1.0
 */
public class JunitActionBase {
    private static HandlerMapping handlerMapping;
    private static HandlerAdapter handlerAdapter;

    @BeforeClass
    public static void setUp() {
        if (handlerMapping == null) {
            String[] configs = {"classpath:spring/spring.xml"};
            XmlWebApplicationContext context = new XmlWebApplicationContext();
            context.setConfigLocations(configs);
            MockServletContext msc = new MockServletContext();
            context.setServletContext(msc);
            context.refresh();
            msc.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, context);
            handlerMapping = (HandlerMapping) context.getBean(DefaultAnnotationHandlerMapping.class);
            handlerAdapter = (HandlerAdapter) context.getBean(context.getBeanNamesForType(AnnotationMethodHandlerAdapter.class)[0]);
        }
    }

    public ModelAndView excuteAction(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING,true);
        ModelAndView modelAndView = null;
        try {
            HandlerExecutionChain chain = handlerMapping.getHandler(request);
            modelAndView = handlerAdapter.handle(request, response, chain.getHandler());
        } catch (Exception e) {

        }
        return modelAndView;
    }
}
