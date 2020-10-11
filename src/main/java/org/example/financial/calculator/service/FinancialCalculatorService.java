package org.example.financial.calculator.service;

import org.example.financial.calculator.model.CalculatorRequest;
import org.example.financial.calculator.model.CalculatorResponse;

/**
 * Financial calculator service that for each client from the request calculates:
 *  - balance on a day when the request was made
 *  - total turnover on accounts - from last day with known balance until when the request was made
 *  - total incomes - from last day with known balance until when the request was made
 *  - total expenditures - from last day with known balance until when the request was made
 */
public interface FinancialCalculatorService {

    CalculatorResponse calculate(CalculatorRequest request);
}
