package com.seek.retotecnico.api.processor;

import com.seek.retotecnico.api.dto.request.SeekRequestApi;
import com.seek.retotecnico.model.user.User;
import com.seek.retotecnico.model.util.enums.Operation;
import com.seek.retotecnico.model.util.exception.BusinessException;
import com.seek.retotecnico.usecase.usermanager.UserManagerUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.seek.retotecnico.api.util.UserManagerUtilApi.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetCustomerProcess {
    private final UserManagerUseCase userManagerUseCase;
    public Mono<ServerResponse> execute(Operation operation) {
        return Mono.just(operation)
                .flatMap(this::processAllCustomers);
    }

    private Mono<ServerResponse> processAllCustomers(Operation operation) {
        return getAllCustomers()
                .flatMap(customers -> buildSuccessResponse(customers, operation))
                .onErrorResume(BusinessException.class, businessException -> buildBusinessErrorResponse(null, businessException));
    }

    protected Mono<List<User>> getAllCustomers() {
        return Mono.empty(); //userManagerUseCase.listAllCustomers();
    }
    private Mono<ServerResponse> buildBusinessErrorResponse(SeekRequestApi request, BusinessException exception) {
        return buildResponseBusinessException(request, exception);
    }

}
