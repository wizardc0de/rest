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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "cn.jason.mapper.slave", sqlSessionFactoryRef = "slaveSqlsessionFactory")
public class SlaveMysqlConfig {
    @Value("${spring.datasource2.url}")
    private String url;

    @Value("${spring.datasource2.username}")
    private String user;

    @Value("${spring.datasource2.password}")
    private String password;

    @Value("${spring.datasource2.driver-class-name}")
    private String driverClass;

    @Bean(name = "slaveDs")
    public DataSource dataSource() {
        DruidDataSource slaveDs = new DruidDataSource();
        slaveDs.setUrl(url);
        slaveDs.setUsername(user);
        slaveDs.setPassword(password);
        slaveDs.setDriverClassName(driverClass);
        System.out.println("加载slaveDs");
        return slaveDs;
    }


    @Bean(name = "slaveSqlsessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(@Qualifier(value = "slaveDs") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mybatis/slave/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "slaveTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier(value = "slaveDs") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
