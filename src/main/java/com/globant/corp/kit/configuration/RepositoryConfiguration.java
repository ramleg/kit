package com.globant.corp.kit.configuration;

import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 *
 * @author ramiro.acoglanis
 */
@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.globant.corp.kit.model.beans"})
@EnableJpaRepositories(basePackages = {"com.globant.corp.kit.repository"})
//@PropertySource("classpath:/com/demo/app.properties")
@EnableTransactionManagement
public class RepositoryConfiguration {
    
          @Value("${spring.datasource.driver}")
	  private String DB_DRIVER;
	  @Value("${spring.datasource.password}")
	  private String DB_PASSWORD;
	  @Value("${spring.datasource.url}")
	  private String DB_URL;
	  @Value("${spring.datasource.username}")
	  private String DB_USERNAME;
	  @Value("${spring.jpa.database-platform}")
	  private String HIBERNATE_DIALECT;
	  @Value("${spring.jpa.show-sql}")
	  private String HIBERNATE_SHOW_SQL;
	  @Value("${spring.jpa.hibernate.ddl-auto}")
	  private String HIBERNATE_HBM2DDL_AUTO;
//	  @Value("${entitymanager.packagesToScan}")
	  private String ENTITYMANAGER_PACKAGES_TO_SCAN = "com.globant.corp.kit.model.beans";

	  @Bean
	  public DataSource dataSource() {
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName(DB_DRIVER);
	    dataSource.setUrl(DB_URL);
	    dataSource.setUsername(DB_USERNAME);
	    dataSource.setPassword(DB_PASSWORD);
	    return dataSource;
	  }

	  @Bean
	  public LocalSessionFactoryBean sessionFactory() {
	    LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
	    sessionFactoryBean.setDataSource(dataSource());
	    sessionFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
	    Properties hibernateProperties = new Properties();
	    hibernateProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
	    hibernateProperties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
	    hibernateProperties.put("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
	    hibernateProperties.put("hibernate.cache.use_second_level_cache", "false");
	    sessionFactoryBean.setHibernateProperties(hibernateProperties);

	    return sessionFactoryBean;
	  }

	  @Bean
	  public HibernateTransactionManager transactionManager() {
	    HibernateTransactionManager transactionManager =
	        new HibernateTransactionManager();
	    transactionManager.setSessionFactory(sessionFactory().getObject());
	    return transactionManager;
	  }
}
