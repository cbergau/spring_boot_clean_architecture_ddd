package de.christianbergau.bankaccount.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class IBAN {
    private final String value;

    public static IBAN of(String value) {
        return new IBAN(value);
    }
}
