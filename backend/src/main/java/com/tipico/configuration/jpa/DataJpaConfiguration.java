package com.tipico.configuration.jpa;

import com.tipico.repository.BaseRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories(basePackages = {
        "com.tipico"}, repositoryBaseClass = BaseRepositoryImpl.class)
public class DataJpaConfiguration {
}
