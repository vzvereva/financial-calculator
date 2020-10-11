package org.example.financial.calculator.exception;

import lombok.Builder;
import lombok.Value;
import org.example.financial.calculator.model.ClientError;

/**
 * Exception thrown if client request fails validation.
 */
@Builder
@Value
public class ClientException extends RuntimeException {

    ClientError clientError;

    public static ClientException createClientException(String errorMessage) {
        return createClientException(null, errorMessage);
    }

    public static ClientException createClientException(String field, String errorMessage) {
        return ClientException.builder()
                .clientError(ClientError.builder()
                        .field(field)
                        .errorMessage(errorMessage)
                        .build())
                .build();
    }
}
