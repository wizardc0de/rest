package cn.jason.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @MapperScan,此注解为mapper接口文件存放目录，及注明可以使用的sqlsession工厂
 */
@Configuration
@MapperScan(basePackages = "cn.jason.mapper", sqlSessionFactoryRef = "masterSqlSessionfactory")
@ConditionalOnProperty(prefix = "rest.multiDs", name = "open", havingValue = "false")
public class MasterMysqlConfig {

    @Bean(name = "masterDs")
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource dataSource() {
        System.out.println("加载masterDs");
        return new DruidDataSource();
    }

    @Bean(name = "masterSqlSessionfactory")
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mybatis/*/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

}
