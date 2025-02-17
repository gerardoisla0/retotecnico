package com.seek.retotecnico.config;

import com.seek.retotecnico.model.gateway.CustomerGateway;
import com.seek.retotecnico.usecase.usermanager.CustomerManagerUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UseCasesConfigTest {

    @Mock
    private CustomerGateway customerGateway;

    @InjectMocks
    private UseCasesConfig useCasesConfig;


    @Test
    void shouldCreateSecurityUseCase() {
        CustomerManagerUseCase customerManagerUseCase =
                useCasesConfig.userManagerUseCase(customerGateway);
        Assertions.assertNotNull(customerManagerUseCase);
    }

}
