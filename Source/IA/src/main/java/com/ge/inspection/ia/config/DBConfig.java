package com.ge.inspection.ia.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DBConfig{
 
	  @Value("${spring.database}")
	    private String database;

	    @Value("${spring.datasource.url}")
	    private String databaseUrl;

	    @Value("${spring.datasource.username1}")
	    private String username;

	    @Value("${spring.datasource.password}")
	    private String password;

	    @Value("${spring.datasource.driverClassName}")
	    private String driverClassName;

	    @Value("${spring.datasource.hibernate.dialect}")
	    private String dialect;
	    
	    
   @Bean
   public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
      LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
      em.setDataSource(dataSource());
      em.setPackagesToScan(new String[] { "com.ge.inspection.ia.domain" });
 
      JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
      em.setJpaVendorAdapter(vendorAdapter);
      em.setJpaProperties(additionalProperties());
 
      return em;
   }
 
   @Bean
   public DataSource dataSource(){
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      String decodedPassword = new String(DatatypeConverter.parseBase64Binary(password));
      dataSource.setDriverClassName(driverClassName);
      dataSource.setUrl(databaseUrl);
      dataSource.setUsername( username );
      dataSource.setPassword( decodedPassword );
      return dataSource;
   }
 
   @Bean
   public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
      JpaTransactionManager transactionManager = new JpaTransactionManager();
      transactionManager.setEntityManagerFactory(emf);
 
      return transactionManager;
   }
 
   @Bean
   public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
      return new PersistenceExceptionTranslationPostProcessor();
   }
 
   Properties additionalProperties() {
      Properties properties = new Properties();
      properties.setProperty("hibernate.dialect", dialect);
      return properties;
   }
}