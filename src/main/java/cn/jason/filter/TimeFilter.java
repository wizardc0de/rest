package cn.jason.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

@Component
public class TimeFilter implements Filter {

    private Logger log= LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("开始初始化TimeFilter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Long startTime =new Date().getTime();
        log.info("startTime:"+startTime);
        filterChain.doFilter(servletRequest,servletResponse);
        Long endTime=new Date().getTime();
        log.info("endTime:"+endTime);
        log.info("cost:"+(endTime-startTime)+"ms");
    }

    @Override
    public void destroy() {
        log.info("销毁TimeFilter");
    }
}
