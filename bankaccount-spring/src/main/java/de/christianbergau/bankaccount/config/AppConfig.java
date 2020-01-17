package de.christianbergau.bankaccount.config;

import de.christianbergau.bankaccount.presenters.TransferMoneyAPIPresenter;
import de.christianbergau.bankaccount.usecase.TransferMoneyInteractor;
import de.christianbergau.bankaccount.usecase.TransferMoneyUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public TransferMoneyAPIPresenter transferMoneyPresenter() {
        return new TransferMoneyAPIPresenter();
    }

    @Bean
    public TransferMoneyUseCase transferMoneyUseCase(TransferMoneyAPIPresenter presenter) {
        return new TransferMoneyInteractor(presenter);
    }
}
