package cn.jason.config;

import cn.jason.filter.TimeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig  {


    @Bean
    public FilterRegistrationBean timeFilter(){
        FilterRegistrationBean bean =new FilterRegistrationBean();
        TimeFilter timeFilter =new TimeFilter();
        bean.setFilter(timeFilter);
        List<String> urls=new ArrayList<>();
        urls.add("/*");
        bean.setUrlPatterns(urls);
        return bean;
    }
}
