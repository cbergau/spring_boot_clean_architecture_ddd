package de.christianbergau.bankaccount.presenters;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class TransferMoneyErrorResponseModel {
    private final String field;
    private final List<String> errors;
}
