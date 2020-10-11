package org.example.financial.calculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component("exceptionHandlerService")
public class ExceptionHandlingService {

    public Message<?> handleThrowable(MessageHandlingException exception) {
        if (exception.getCause() instanceof ClientException) {
            ClientException clientException = (ClientException) exception.getCause();
            return MessageBuilder
                    .withPayload(clientException.getClientError())
                    .setHeader("http_statusCode", HttpStatus.BAD_REQUEST)
                    .build();
        }
        return new ErrorMessage(exception);
    }
}
