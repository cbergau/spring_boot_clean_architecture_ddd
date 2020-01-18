package de.christianbergau.bankaccount.config;

import de.christianbergau.bankaccount.presenters.TransferMoneyAPIPresenter;
import de.christianbergau.bankaccount.repository.JPATransactionAdapter;
import de.christianbergau.bankaccount.repository.JPATransactionMapper;
import de.christianbergau.bankaccount.repository.JPATransactionRepository;
import de.christianbergau.bankaccount.repository.SaveTransactionRepository;
import de.christianbergau.bankaccount.usecase.TransferMoneyInteractor;
import de.christianbergau.bankaccount.usecase.TransferMoneyUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
public class AppConfig {

    @Bean
    public TransferMoneyAPIPresenter transferMoneyPresenter() {
        return new TransferMoneyAPIPresenter();
    }

    @Bean
    public SaveTransactionRepository saveTransactionRepository(JPATransactionRepository repository) {
        return new JPATransactionAdapter(new JPATransactionMapper(), repository);
    }

    @Bean
    public TransferMoneyUseCase transferMoneyUseCase(TransferMoneyAPIPresenter presenter, SaveTransactionRepository repository) {
        return new TransferMoneyInteractor(presenter, repository);
    }
}
