package com.globant.corp.kit.configuration;

/**
 *
 * @author ramiro.acoglanis
 */

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.context.annotation.PropertySource;
/**
 *
 * @author ramiro.acoglanis
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.globant.corp.kit.repo.kace", entityManagerFactoryRef = "kaceEntityManagerFactory", transactionManagerRef = "kaceTransactionManager")
@EnableTransactionManagement
@PropertySource("classpath:kace-datasource.properties")
public class KaceRepoConfig
{
  @Bean
  @ConfigurationProperties(prefix = "kace.datasource")
  public DataSource kaceDataSource()
  {
    return DataSourceBuilder.create().build();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean kaceEntityManagerFactory(final EntityManagerFactoryBuilder builder)
  {
    return builder
        .dataSource(kaceDataSource())
        .packages("com.globant.corp.kit.entity.kace")
        .persistenceUnit("kacePersistenceUnit")
        .build();
  }

  @Bean
  public JpaTransactionManager kaceTransactionManager(@Qualifier("kaceEntityManagerFactory") final EntityManagerFactory factory)
  {
    return new JpaTransactionManager(factory);
  }
}
