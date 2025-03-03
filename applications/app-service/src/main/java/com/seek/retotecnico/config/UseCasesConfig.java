package com.seek.retotecnico.config;

import com.seek.retotecnico.model.gateway.TaskGateway;
import com.seek.retotecnico.model.gateway.UserGateway;
import com.seek.retotecnico.usecase.taskmanager.TaskManagerUseCase;
import com.seek.retotecnico.usecase.usermanager.UserManagerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {

        @Bean
        public UserManagerUseCase userManagerUseCase(
                UserGateway userGateway) {
                return new UserManagerUseCase(userGateway);
        }

        @Bean
        public TaskManagerUseCase taskManagerUseCase(
                TaskGateway taskGateway) {
                return new TaskManagerUseCase(taskGateway);
        }
}
