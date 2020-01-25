package de.christianbergau.bankaccount.presenters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.christianbergau.bankaccount.usecase.TransferMoneyRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransferMoneyAPIPresenterTest {

    @Test
    void testPresentSingleError() {
        // Given
        Set<ConstraintViolation<TransferMoneyRequest>> errors = new HashSet<>();
        addError("toIban", "Can not be empty", errors);
        TransferMoneyAPIPresenter presenter = new TransferMoneyAPIPresenter(new ObjectMapper());

        // When
        presenter.presentError(errors);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, presenter.getResponseEntity().getStatusCode());
        assertEquals("{\"toIban\":[\"Can not be empty\"]}", presenter.getResponseEntity().getBody());
    }

    @Test
    void testPresentMultipleErrors() {
        // Given
        Set<ConstraintViolation<TransferMoneyRequest>> errors = new HashSet<>();
        addError("toIban", "Can not be empty", errors);
        addError("toIban", "Second Error", errors);
        TransferMoneyAPIPresenter presenter = new TransferMoneyAPIPresenter(new ObjectMapper());

        // When
        presenter.presentError(errors);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, presenter.getResponseEntity().getStatusCode());
        assertEquals("{\"toIban\":[\"Can not be empty\",\"Second Error\"]}", presenter.getResponseEntity().getBody());
    }

    @Test
    void testPresentInternalServerErrorWhenBuildingJsonFails() throws Exception {
        // Given
        ObjectMapper objectMapper = mock(ObjectMapper.class);
        when(objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        TransferMoneyAPIPresenter presenter = new TransferMoneyAPIPresenter(objectMapper);

        // When
        presenter.presentError(new HashSet<>());

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, presenter.getResponseEntity().getStatusCode());
        assertNull(presenter.getResponseEntity().getBody());
    }

    @Test
    void givenTransactionWasSuccessful_andJsonParsingFails_whenPresentingSuccess_shouldRespondWithInternalServerError() throws Exception {
        // Given
        ObjectMapper objectMapper = mock(ObjectMapper.class);
        when(objectMapper.writeValueAsString(any())).thenThrow(JsonProcessingException.class);
        TransferMoneyAPIPresenter presenter = new TransferMoneyAPIPresenter(objectMapper);

        // When
        presenter.presentSuccess("", "", "", 0.0);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, presenter.getResponseEntity().getStatusCode());
        assertNull(presenter.getResponseEntity().getBody());
    }

    private void addError(String fieldName, String errorMessage, Set<ConstraintViolation<TransferMoneyRequest>> errors) {
        ConstraintViolation violation = mock(ConstraintViolation.class);
        Path path = mock(Path.class);
        when(path.toString()).thenReturn(fieldName);
        when(violation.getMessage()).thenReturn(errorMessage);
        when(violation.getPropertyPath()).thenReturn(path);
        errors.add(violation);
    }
}
