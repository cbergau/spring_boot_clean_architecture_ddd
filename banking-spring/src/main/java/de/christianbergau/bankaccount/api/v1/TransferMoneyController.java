package de.christianbergau.bankaccount.api.v1;

import de.christianbergau.bankaccount.api.v1.request.TransferModel;
import de.christianbergau.bankaccount.presenters.TransferMoneyAPIPresenter;
import de.christianbergau.bankaccount.usecase.TransferMoneyRequest;
import de.christianbergau.bankaccount.usecase.TransferMoneyUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransferMoneyController {

    private final TransferMoneyUseCase useCase;
    private final TransferMoneyAPIPresenter presenter;

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferModel model) {
        useCase.execute(new TransferMoneyRequest(model.fromIban, model.toIban, model.amount));
        return presenter.getResponseEntity();
    }
}
