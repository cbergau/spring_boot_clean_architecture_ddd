package de.christianbergau.banking.reactive.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.christianbergau.bankaccount.repository.SaveTransactionReactiveRepository;
import de.christianbergau.bankaccount.repository.SaveTransactionRepository;
import de.christianbergau.bankaccount.usecase.TransferMoneyInteractor;
import de.christianbergau.bankaccount.usecase.TransferMoneyUseCase;
import de.christianbergau.banking.reactive.presenter.ReactiveTransferMoneyPresenter;
import de.christianbergau.banking.reactive.repository.ReactiveRedisRepositoryImpl;
import de.christianbergau.banking.reactive.repository.ReactiveTransactionRedisDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ReactiveRedisOperations;

@Configuration
public class AppConfig {

    @Bean
    public ReactiveTransferMoneyPresenter transferMoneyPresenter() {
        return new ReactiveTransferMoneyPresenter(new ObjectMapper());
    }

    @Bean
    public SaveTransactionReactiveRepository saveTransactionRepository(ReactiveRedisOperations<String, ReactiveTransactionRedisDto> operations) {
        return new ReactiveRedisRepositoryImpl(operations);
    }

    @Bean
    public TransferMoneyUseCase transferMoneyUseCase(ReactiveTransferMoneyPresenter presenter, SaveTransactionReactiveRepository repository) {
        return new TransferMoneyInteractor(presenter, repository);
    }
}
