package de.christianbergau.banking.reactive.v1;

import de.christianbergau.bankaccount.usecase.TransferMoneyRequest;
import de.christianbergau.bankaccount.usecase.TransferMoneyUseCase;
import de.christianbergau.banking.reactive.presenter.ReactiveTransferMoneyPresenter;
import de.christianbergau.banking.reactive.v1.request.TransferModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class TransferMoneyController {

    private final TransferMoneyUseCase useCase;
    private final ReactiveTransferMoneyPresenter presenter;

    @PostMapping("/transfer")
    public Mono<ResponseEntity<String>> transfer(@RequestBody TransferModel model) {
        useCase.execute(new TransferMoneyRequest(model.fromIban, model.toIban, model.amount));
        return presenter.getResponseEntity();
    }
}
