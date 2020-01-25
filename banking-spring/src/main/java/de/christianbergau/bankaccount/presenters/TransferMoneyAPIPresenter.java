package de.christianbergau.bankaccount.presenters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.christianbergau.bankaccount.api.v1.response.TransactionModel;
import de.christianbergau.bankaccount.usecase.TransferMoneyPresenter;
import de.christianbergau.bankaccount.usecase.TransferMoneyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;

@RequiredArgsConstructor
public class TransferMoneyAPIPresenter implements TransferMoneyPresenter, BaseSpringPresenter {

    private ResponseEntity<String> responseEntity;
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
            responseEntity = ResponseEntity
                    .badRequest()
                    .body(objectMapper.writeValueAsString(map));
        } catch (JsonProcessingException e) {
            responseEntity = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
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
            responseEntity = ResponseEntity
                    .ok()
                    .body(objectMapper.writeValueAsString(transactionModel));
        } catch (JsonProcessingException e) {
            responseEntity = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @Override
    public ResponseEntity<String> getResponseEntity() {
        return responseEntity;
    }
}
