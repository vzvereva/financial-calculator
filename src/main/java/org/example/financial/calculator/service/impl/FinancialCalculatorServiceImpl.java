package org.example.financial.calculator.service.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.example.financial.calculator.exception.ClientException;
import org.example.financial.calculator.model.Balance;
import org.example.financial.calculator.model.CalculationResult;
import org.example.financial.calculator.model.CalculatorRequest;
import org.example.financial.calculator.model.CalculatorResponse;
import org.example.financial.calculator.model.CalculatorResponse.CalculatorResponseBuilder;
import org.example.financial.calculator.model.Client;
import org.example.financial.calculator.model.ClientInfo;
import org.example.financial.calculator.model.ClientResult;
import org.example.financial.calculator.model.Transaction;
import org.example.financial.calculator.model.TransactionType;
import org.example.financial.calculator.service.FinancialCalculatorService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static org.example.financial.calculator.model.TransactionType.INCOME;
import static org.example.financial.calculator.model.TransactionType.OUTCOME;

/**
 * Implementation of financial calculator service that for each client from the request calculates:
 * - balance on a day when the request was made
 * - total turnover on accounts - from last day with known balance until when the request was made
 * - total incomes - from last day with known balance until when the request was made
 * - total expenditures - from last day with known balance until when the request was made
 */
@Service("financialCalculatorService")
public class FinancialCalculatorServiceImpl implements FinancialCalculatorService {

    public CalculatorResponse calculate(CalculatorRequest request) {

        LocalDate currentDate = LocalDate.now();

        CalculatorResponseBuilder calculatorResponseBuilder =
                CalculatorResponse.builder();

        request.getClients().getClient().stream()
                .map(client -> calculateForClient(client, currentDate))
                .forEach(calculatorResponseBuilder::client);

        return calculatorResponseBuilder.build();
    }

    private ClientResult calculateForClient(Client client, LocalDate currentDate) {

        validateClient(client, currentDate);

        LocalDate balanceDate = client.getBalance().getDate();

        ImmutableList<Transaction> transactionRange =
                getTransactionRange(client.getTransactions(), balanceDate, currentDate);

        BigDecimal totalIncomes =
                getTransactionsSum(transactionRange, INCOME);
        BigDecimal totalExpenditures =
                getTransactionsSum(transactionRange, OUTCOME);

        BigDecimal totalTurnover = totalIncomes.subtract(totalExpenditures);
        BigDecimal currentBalance = client.getBalance().getTotal().add(totalTurnover);

        return ClientResult.builder()
                .clientInfo(client.getInfo())
                .calculationResult(CalculationResult.builder()
                        .date(currentDate)
                        .currentBalance(currentBalance)
                        .totalIncomes(totalIncomes)
                        .totalExpenditures(totalExpenditures)
                        .totalTurnover(totalTurnover)
                        .build())
                .build();
    }

    private ImmutableList<Transaction> getTransactionRange(ImmutableList<Transaction> transactions,
                                                           LocalDate balanceDate,
                                                           LocalDate currentDate) {
        return transactions.stream()
                .filter(t -> t.getDate().isAfter(balanceDate)
                        && !t.getDate().isAfter(currentDate))
                .collect(toImmutableList());
    }

    private BigDecimal getTransactionsSum(ImmutableList<Transaction> transactions,
                                          TransactionType transactionType) {
        return transactions.stream()
                .filter(t -> transactionType == t.getType())
                .map(Transaction::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void validateClient(Client client, LocalDate currentDate) {
        validateBalance(client.getBalance(), client.getInfo(), currentDate);

        Optional<String> transactionsCurrency = getTransactionsCurrency(client);
        if (transactionsCurrency.isPresent() &&
                !transactionsCurrency.get().equals(client.getBalance().getCurrency())) {
            throw ClientException.createClientException(
                    String.format(TRANSACTION_CURRENCY_ERROR_MESSAGE, client.getInfo()));
        }
    }

    private void validateBalance(Balance balance, ClientInfo clientInfo, LocalDate currentDate) {
        LocalDate balanceDate = balance.getDate();
        if (balanceDate.isAfter(currentDate)) {
            throw ClientException.createClientException(BALANCE_DATE_PATH,
                    String.format(FUTURE_BALANCE_DATE_MESSAGE, clientInfo));
        }
    }

    private Optional<String> getTransactionsCurrency(Client client) {
        ImmutableSet<String> currencies =
                client.getTransactions().stream().map(Transaction::getCurrency).collect(toImmutableSet());
        if (currencies.size() > 1) {
            throw ClientException.createClientException(TRANSACTION_CURRENCY_PATH,
                    String.format(MULTIPLE_CURRENCIES_MESSAGE, client.getInfo()));
        }
        return currencies.stream().findFirst();
    }

    private static final String TRANSACTION_CURRENCY_PATH = "clients.client[].transactions[].currency";
    private static final String BALANCE_DATE_PATH = "clients.client[].balance.date";

    private static final String TRANSACTION_CURRENCY_ERROR_MESSAGE = "Transactions currency does not match balance currency for client %s";
    private static final String FUTURE_BALANCE_DATE_MESSAGE = "Invalid future balance date provided for client %s";
    private static final String MULTIPLE_CURRENCIES_MESSAGE = "Multiple currencies detected in transactions of %s";
}
