package com.sandu.common.filter;

import com.sandu.common.context.SystemContext;
import com.sandu.common.context.SystemContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.sandu.constant.Punctuation.*;

/**
 * @author   bvvy
 * 拦截上传路径信息
 */

@Component
public class ContextFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(ContextFilter.class);

    @Resource
    private Environment environment;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        SystemContext context = SystemContextHolder.getSystemContext();
        String serverPath = getServerPath(request);
        context.setServerPath(serverPath);
        context.setStoragePath(environment.getProperty("file.storage.path"));
        SystemContextHolder.setSystemContext(context);
        filterChain.doFilter(request, servletResponse);
    }

    private String getServerPath(HttpServletRequest request) {
        String ctx = request.getContextPath();
        return request.getScheme() + COLON + DOUBLE_SLASH + request.getServerName() + COLON + request.getServerPort() + ctx + SLASH;
    }

    @Override
    public void destroy() {
        logger.debug("remove system context ");
        SystemContextHolder.remove();
    }
}
