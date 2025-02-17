package com.seek.retotecnico.config;

import com.seek.retotecnico.model.gateway.CustomerGateway;
import com.seek.retotecnico.usecase.usermanager.CustomerManagerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {

        @Bean
        public CustomerManagerUseCase userManagerUseCase(
                CustomerGateway customerGateway) {
                return new CustomerManagerUseCase(customerGateway);
        }
}
