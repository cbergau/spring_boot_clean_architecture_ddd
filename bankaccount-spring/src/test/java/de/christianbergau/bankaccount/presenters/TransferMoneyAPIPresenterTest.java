package de.christianbergau.bankaccount.presenters;

import de.christianbergau.bankaccount.usecase.TransferMoneyRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferMoneyAPIPresenterTest {

    @Test
    void testPresentSingleError() {
        // Given
        Set<ConstraintViolation<TransferMoneyRequest>> errors = new HashSet<>();
        addError("toIban", "Can not be empty", errors);
        TransferMoneyAPIPresenter presenter = new TransferMoneyAPIPresenter();

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
        TransferMoneyAPIPresenter presenter = new TransferMoneyAPIPresenter();

        // When
        presenter.presentError(errors);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, presenter.getResponseEntity().getStatusCode());
        assertEquals("{\"toIban\":[\"Can not be empty\",\"Second Error\"]}", presenter.getResponseEntity().getBody());
    }

    private void addError(String fieldName, String errorMessage, Set<ConstraintViolation<TransferMoneyRequest>> errors) {
        ConstraintViolation violation = Mockito.mock(ConstraintViolation.class);
        Path path = Mockito.mock(Path.class);
        Mockito.when(path.toString()).thenReturn(fieldName);
        Mockito.when(violation.getMessage()).thenReturn(errorMessage);
        Mockito.when(violation.getPropertyPath()).thenReturn(path);
        errors.add(violation);
    }
}
