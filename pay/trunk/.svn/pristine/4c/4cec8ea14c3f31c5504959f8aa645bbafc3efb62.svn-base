package com.sandu.common.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 支持ajax的跨域请求
 * @author Administrator
 *
 */
public class SimpleCORSFilter implements Filter {

	   

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,

            ServletException {
    	//System.out.println("123456789");

        HttpServletResponse response = (HttpServletResponse) res;

        response.setHeader("Access-Control-Allow-Origin", "*");

        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");

        response.setHeader("Access-Control-Max-Age", "3600");

        response.setHeader(

                "Access-Control-Allow-Headers",

                "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires,                                  Content-Type, X-E4M-With");

          chain.doFilter(req, res);

    }

   

    public void init(FilterConfig filterConfig) {}   

    public void destroy() {}

  }
