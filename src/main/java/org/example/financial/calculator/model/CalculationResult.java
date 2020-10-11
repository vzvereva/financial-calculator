package org.example.financial.calculator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Model object that represents calculation result for a client.
 */
@Builder
@Value
@JsonDeserialize(builder = CalculationResult.CalculationResultBuilder.class)
@JsonSerialize(as = CalculationResult.class)
public class CalculationResult {

    @JsonProperty("date")
    LocalDate date;

    @JsonProperty("curentBalance")
    BigDecimal currentBalance;

    @JsonProperty("totalTurnover")
    BigDecimal totalTurnover;

    @JsonProperty("totalIncomes")
    BigDecimal totalIncomes;

    @JsonProperty("totalExpenditures")
    BigDecimal totalExpenditures;
}
