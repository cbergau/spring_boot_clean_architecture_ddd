package de.christianbergau.bankaccount.presenters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.christianbergau.bankaccount.api.v1.response.TransactionModel;
import de.christianbergau.bankaccount.usecase.TransferMoneyPresenter;
import de.christianbergau.bankaccount.usecase.TransferMoneyRequest;
import org.springframework.http.ResponseEntity;

import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;

public class TransferMoneyAPIPresenter implements TransferMoneyPresenter, BaseSpringPresenter {

    private ResponseEntity<String> responseEntity;

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
                    .body(new ObjectMapper().writeValueAsString(map));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
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
                    .body(new ObjectMapper().writeValueAsString(transactionModel));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResponseEntity<String> getResponseEntity() {
        return responseEntity;
    }
}
