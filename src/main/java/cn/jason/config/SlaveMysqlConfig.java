package cn.jason.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(SlaveMysqlConfig.class);
    @Bean(name = "slaveDs")
    @ConfigurationProperties(value = "spring.datasource2")
    public DataSource dataSource() {
        DruidDataSource slaveDs = new DruidDataSource();
        logger.info("加载了slaveDs");
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
