package com.globant.corp.kit.configuration;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
@EnableJpaRepositories(basePackages = "com.globant.corp.kit.repo.local", entityManagerFactoryRef = "localEntityManagerFactory", transactionManagerRef = "localTransactionManager")
@EnableTransactionManagement
@PropertySource("classpath:local-datasource.properties")
public class LocalRepoConfig
{
  @Bean
  @ConfigurationProperties(prefix = "local.datasource")
  @Primary
  public DataSource localDataSource()
  {
    return DataSourceBuilder.create().build();
  }

  @Bean
  @Primary
  public LocalContainerEntityManagerFactoryBean localEntityManagerFactory(final EntityManagerFactoryBuilder builder)
  {
    return builder
        .dataSource(localDataSource())
        .packages("com.globant.corp.kit.entity.local")
        .persistenceUnit("localPersistenceUnit")
        .build();
  }

  @Bean
  @Primary
  public JpaTransactionManager localTransactionManager(@Qualifier("localEntityManagerFactory") final EntityManagerFactory factory)
  {
    return new JpaTransactionManager(factory);
  }
}
