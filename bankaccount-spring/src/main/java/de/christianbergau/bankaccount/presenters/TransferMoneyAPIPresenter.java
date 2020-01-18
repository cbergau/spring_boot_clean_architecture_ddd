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

public class TransferMoneyAPIPresenter implements TransferMoneyPresenter, BaseSpringPresenter {

    private ResponseEntity<String> responseEntity;

    @Override
    public void presentError(Set<ConstraintViolation<TransferMoneyRequest>> errors) {
        //@todo Use Java Collector (probably groupingBy?)
        Map<String, Set<String>> map = new HashMap<>();

        errors.forEach(violation -> {
            if (map.containsKey(violation.getPropertyPath().toString())) {
                map.get(violation.getPropertyPath().toString()).add(violation.getMessage());
            } else {
                Set<String> tempList = new HashSet<>();
                tempList.add(violation.getMessage());
                map.put(violation.getPropertyPath().toString(), tempList);
            }
        });

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
