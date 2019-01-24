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

    private Logger logger = LoggerFactory.getLogger(MasterMysqlConfig.class);

    /**
     * primary 表明首先使用此bean
     *
     * @return
     */
    @Bean(name = "masterDs")
    @Primary
    @ConfigurationProperties(value = "spring.datasource")
    public DataSource dataSource() {
        DruidDataSource masterDs = new DruidDataSource();
        logger.info("加载masterDs");
        return masterDs;
    }

    @Bean(name = "masterSqlSessionfactory")
    public SqlSessionFactory sqlSessionFactoryBean(@Qualifier(value = "masterDs") DataSource dataSource) throws Exception {
        //调用默认的构造方法
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

        sqlSessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mybatis/master/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "masterTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier(value = "masterDs") DataSource dataSource) {
        PlatformTransactionManager platformTransactionManager = new DataSourceTransactionManager(dataSource);
        return platformTransactionManager;
    }

}
