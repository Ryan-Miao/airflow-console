package com.ryanmiao.airflow.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;


/**
 * @author miaoruifeng
 */
@Configuration
public class Db1DataSourceConfig {

    /**
     * SqlSessionFactory
     */
    @Bean(name = "sqlSessionFactory4DB1")
    public SqlSessionFactory getSqlSessionFactory4DB1(DataSource datasource)
            throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(datasource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/**/*.xml"));
        factoryBean.setConfigLocation(new ClassPathResource("config/mybatis-config.xml"));
        return factoryBean.getObject();
    }

    /**
     * MapperScannerConfigurer
     */
    @Bean(name = "mapperScannerConfigurer4DB1")
    public MapperScannerConfigurer mapperScannerConfigurer4DB1() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory4DB1");
        mapperScannerConfigurer.setBasePackage("com.ryanmiao.airflow.domain.*.mapper");
        Properties properties = new Properties();
        // 这里要特别注意，不要把MyMapper放到 basePackage 中，也就是不能同其他Mapper一样被扫描到。
        properties.setProperty("mappers", Mapper.class.getName());
        mapperScannerConfigurer.setProperties(properties);
        return mapperScannerConfigurer;
    }
}
