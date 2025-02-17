package com.seek.retotecnico.model.sqs;

import reactor.core.publisher.Mono;

public interface SQSGateway {
    Mono<String> sendMessage(String message);
}