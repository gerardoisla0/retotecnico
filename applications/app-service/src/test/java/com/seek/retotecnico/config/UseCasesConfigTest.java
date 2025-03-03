package com.seek.retotecnico.config;

import com.seek.retotecnico.model.gateway.UserGateway;
import com.seek.retotecnico.usecase.usermanager.UserManagerUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UseCasesConfigTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private UseCasesConfig useCasesConfig;


    @Test
    void shouldCreateSecurityUseCase() {
        UserManagerUseCase userManagerUseCase =
                useCasesConfig.userManagerUseCase(userGateway);
        Assertions.assertNotNull(userManagerUseCase);
    }

}
