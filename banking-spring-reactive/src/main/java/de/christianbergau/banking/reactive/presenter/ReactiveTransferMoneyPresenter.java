package de.christianbergau.banking.reactive.presenter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.christianbergau.bankaccount.usecase.TransferMoneyPresenter;
import de.christianbergau.bankaccount.usecase.TransferMoneyRequest;
import de.christianbergau.banking.reactive.v1.response.TransactionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;

@RequiredArgsConstructor
public class ReactiveTransferMoneyPresenter implements TransferMoneyPresenter {
    private Mono<ResponseEntity<String>> responseEntity;
    private final ObjectMapper objectMapper;

    @Override
    public void presentError(Set<ConstraintViolation<TransferMoneyRequest>> errors) {
        Map<String, Set<String>> map = errors
                .stream()
                .collect(groupingBy(
                        violation -> violation.getPropertyPath().toString(),
                        HashMap::new,
                        mapping(ConstraintViolation::getMessage, Collectors.toSet()))
                );

        try {
            responseEntity = Mono.just(ResponseEntity
                    .badRequest()
                    .body(objectMapper.writeValueAsString(map)));
        } catch (JsonProcessingException e) {
            responseEntity = Mono.just(ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build());
        }
    }

    @Override
    public void presentSuccess(String transactionNumber, String fromIban, String toIban, double amount) {
        TransactionModel transactionModel = TransactionModel.builder()
                .transactionNumber(transactionNumber)
                .fromIban(fromIban)
                .toIban(toIban)
                .amount(amount)
                .build();

        try {
            responseEntity = Mono.just(ResponseEntity
                    .ok()
                    .body(objectMapper.writeValueAsString(transactionModel)));
        } catch (JsonProcessingException e) {
            responseEntity = Mono.just(ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build());
        }
    }

    public Mono<ResponseEntity<String>> getResponseEntity() {
        return responseEntity;
    }
}
