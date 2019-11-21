package com.xincan.transaction.order.config;

import io.seata.core.context.RootContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 绑定seata通过feign传来的全局事务xid
 */
@Component
public class SeataFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String xid = req.getHeader(RootContext.KEY_XID.toLowerCase());
        boolean isBind = false;
        if (StringUtils.isNotBlank(xid)) {
            // 绑定seata事务id
            RootContext.bind(xid);
            isBind = true;
        }
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            if (isBind) {
                RootContext.unbind();
            }
        }
    }

    @Override
    public void destroy() {
    }
}