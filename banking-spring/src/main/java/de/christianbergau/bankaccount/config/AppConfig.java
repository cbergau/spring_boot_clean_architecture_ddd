package de.christianbergau.bankaccount.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.christianbergau.bankaccount.presenters.TransferMoneyAPIPresenter;
import de.christianbergau.bankaccount.repository.TransactionAdapter;
import de.christianbergau.bankaccount.repository.TransactionDtoMapper;
import de.christianbergau.bankaccount.repository.TransactionRepository;
import de.christianbergau.bankaccount.repository.SaveTransactionRepository;
import de.christianbergau.bankaccount.usecase.TransferMoneyInteractor;
import de.christianbergau.bankaccount.usecase.TransferMoneyUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class AppConfig {

    @Bean
    public TransferMoneyAPIPresenter transferMoneyPresenter() {
        return new TransferMoneyAPIPresenter(new ObjectMapper());
    }

    @Bean
    public SaveTransactionRepository saveTransactionRepository(TransactionRepository repository) {
        return new TransactionAdapter(new TransactionDtoMapper(), repository);
    }

    @Bean
    public TransferMoneyUseCase transferMoneyUseCase(TransferMoneyAPIPresenter presenter, SaveTransactionRepository repository) {
        return new TransferMoneyInteractor(presenter, repository);
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
