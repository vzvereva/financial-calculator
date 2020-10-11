package org.example.financial.calculator.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Model object that represents transaction direction.
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum TransactionType {

    INCOME("income"),
    OUTCOME("outcome");

    @JsonValue
    private final @NonNull String type;
}
