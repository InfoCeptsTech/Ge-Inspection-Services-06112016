package com.ge.inspection.config;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource({ "classpath:application.properties" })
@EnableTransactionManagement
@EnableAutoConfiguration
@EnableJpaRepositories(
        entityManagerFactoryRef = "mutaEntityManagerFactory",
        transactionManagerRef = "mutaTransactionManager",
        basePackages = {"com.ge.inspection.ir.repository.muta"})
public class RepositoryConfig extends DataSourceAutoConfiguration{
    @Autowired
    JpaVendorAdapter jpaVendorAdapter;
    
    @Value("${muta.spring.database}")
    private String database;

    @Value("${muta.spring.datasource.url}")
    private String databaseUrl;

    @Value("${muta.spring.datasource.username}")
    private String username;

    @Value("${muta.spring.datasource.password}")
    private String password;

    @Value("${muta.spring.datasource.driverClassName}")
    private String driverClassName;

    @Value("${muta.spring.datasource.hibernate.dialect}")
    private String dialect;

    public DriverManagerDataSource dataSource() {
    	
    	String decodedPassword = new String(DatatypeConverter.parseBase64Binary(password));
        DriverManagerDataSource dataSource = new DriverManagerDataSource(databaseUrl, username, decodedPassword);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }

    @Bean(name = "mutaEntityManager")
    public EntityManager entityManager() {
        return entityManagerFactory().createEntityManager();
    }
    @Primary
    @Bean(name = "mutaEntityManagerFactory")
    public EntityManagerFactory entityManagerFactory() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", dialect);
        properties.setProperty("hibernate.database", database);
       // properties.setProperty("hibernate.hbm2ddl.auto", "create");
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setJpaVendorAdapter(jpaVendorAdapter);
        emf.setPackagesToScan("com.ge.inspection.ir.domain.muta");   
        emf.setPersistenceUnitName("mutaPersistenceUnit");
        emf.setJpaProperties(properties);
        emf.afterPropertiesSet();
        return emf.getObject();
    }

    @Bean(name = "mutaTransactionManager")
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory());
    }
}