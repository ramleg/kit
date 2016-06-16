package com.globant.corp.kit.configuration;

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
@EntityScan(basePackages = {"com.globant.corp.kit.model.beans"})
@EnableJpaRepositories(basePackages = {"com.globant.corp.kit.repository"})
@EnableTransactionManagement
public class RepositoryConfiguration {}
