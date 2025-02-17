package com.seek.retotecnico.sqs.sender.adapter;

import com.seek.retotecnico.model.sqs.SQSGateway;
import com.seek.retotecnico.model.util.enums.TechnicalMessage;
import com.seek.retotecnico.model.util.exception.TechnicalException;
import com.seek.retotecnico.sqs.sender.operations.SQSOperations;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static net.logstash.logback.argument.StructuredArguments.kv;



@Slf4j
@Repository
@AllArgsConstructor
public class SQSSender implements SQSGateway {
    private static final String SEND_MESSAGE_REQUEST = "Send Message Request";
    private static final String SEND_MESSAGE_KEY_REQUEST = "sendMessageRQ";
    private static final String SEND_MESSAGE_RESPONSE = "Send Message Response";
    private static final String SEND_MESSAGE_KEY_RESPONSE = "sendMessageRS";
    private static final String ERROR_SEND_MESSAGE_RESPONSE = "Error Sending Message";
    private static final String ERROR_SEND_MESSAGE_KEY_RESPONSE = "sendMessageErrorRS";

    private final SQSOperations sqsOperations;

    @Override
    public Mono<String> sendMessage(String message) {
        return sqsOperations.send(message)
                .doOnSubscribe(subscription -> log.info(SEND_MESSAGE_REQUEST, kv(SEND_MESSAGE_KEY_REQUEST, message)))
                .doOnSuccess(messageResponse -> log.info(SEND_MESSAGE_RESPONSE, kv(SEND_MESSAGE_KEY_RESPONSE, messageResponse)))
                .doOnError(exception -> log.error(ERROR_SEND_MESSAGE_RESPONSE, kv(ERROR_SEND_MESSAGE_KEY_RESPONSE, exception)))
                .onErrorMap(exception -> new TechnicalException(exception, TechnicalMessage.ERROR_INTERNAL_SERVER));
    }

}
