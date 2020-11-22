package de.christianbergau.banking.reactive.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.christianbergau.bankaccount.repository.SaveTransactionReactiveRepository;
import de.christianbergau.bankaccount.usecase.TransferMoneyInteractor;
import de.christianbergau.bankaccount.usecase.TransferMoneyUseCase;
import de.christianbergau.banking.reactive.presenter.ReactiveTransferMoneyPresenter;
import de.christianbergau.banking.reactive.repository.ReactiveRedisRepositoryImpl;
import de.christianbergau.banking.reactive.repository.ReactiveTransactionRedisDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializationContext.RedisSerializationContextBuilder;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class AppConfig {

    @Bean
    public ReactiveTransferMoneyPresenter transferMoneyPresenter() {
        return new ReactiveTransferMoneyPresenter(new ObjectMapper());
    }

    @Bean
    ReactiveRedisOperations<String, ReactiveTransactionRedisDto> redisOperations(
            ReactiveRedisConnectionFactory factory
    ) {
        Jackson2JsonRedisSerializer<ReactiveTransactionRedisDto> serializer
                = new Jackson2JsonRedisSerializer<>(ReactiveTransactionRedisDto.class);

        RedisSerializationContextBuilder<String, ReactiveTransactionRedisDto> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, ReactiveTransactionRedisDto> context
                = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

    @Bean
    public SaveTransactionReactiveRepository saveTransactionRepository(
            ReactiveRedisOperations<String, ReactiveTransactionRedisDto> operations
    ) {
        return new ReactiveRedisRepositoryImpl(operations);
    }

    @Bean
    public TransferMoneyUseCase transferMoneyUseCase(
            ReactiveTransferMoneyPresenter presenter,
            SaveTransactionReactiveRepository repository
    ) {
        return new TransferMoneyInteractor(presenter, repository);
    }
}
