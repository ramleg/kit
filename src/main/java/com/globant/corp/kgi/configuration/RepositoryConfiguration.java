package com.globant.corp.kgi.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 *
 * @author ramiro.acoglanis
 */
@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.globant.corp.kgi.model.beans"})
@EnableJpaRepositories(basePackages = {"com.globant.corp.kgi.repository"})
@EnableTransactionManagement
public class RepositoryConfiguration {}
