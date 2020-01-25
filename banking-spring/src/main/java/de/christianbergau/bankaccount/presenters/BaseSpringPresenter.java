package de.christianbergau.bankaccount.presenters;

import org.springframework.http.ResponseEntity;

public interface BaseSpringPresenter {
    public ResponseEntity<String> getResponseEntity();
}
