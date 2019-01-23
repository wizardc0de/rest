package cn.jason.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @MapperScan,此注解为mapper接口文件存放目录，及注明可以使用的sqlsession工厂
 */
@Configuration
@MapperScan(basePackages = "cn.jason.mapper.master", sqlSessionFactoryRef = "masterSqlSessionfactory")
public class MasterMysqlConfig {
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClass;

    @Bean(name = "masterDs")
    @Primary
    public DataSource dataSource() {
        DruidDataSource masterDs = new DruidDataSource();
        masterDs.setUrl(url);
        masterDs.setUsername(user);
        masterDs.setPassword(password);
        masterDs.setDriverClassName(driverClass);
        System.out.println("加载masterDs");
        return masterDs;
    }

    @Bean(name = "masterSqlSessionfactory")
    public SqlSessionFactory sqlSessionFactoryBean(@Qualifier(value = "masterDs") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mybatis/master/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "masterTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier(value = "masterDs") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
