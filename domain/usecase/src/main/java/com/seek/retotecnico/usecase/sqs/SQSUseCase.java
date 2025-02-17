package com.seek.retotecnico.usecase.sqs;

import com.seek.retotecnico.model.sqs.SQSGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SQSUseCase {
    private final SQSGateway sqsGateway;
    public Mono<String> sendMessage(String message){
        return sqsGateway.sendMessage(message);
    }
}
