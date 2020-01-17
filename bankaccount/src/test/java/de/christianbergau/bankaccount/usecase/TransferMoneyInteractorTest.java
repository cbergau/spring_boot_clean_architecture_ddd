package de.christianbergau.bankaccount.usecase;

import lombok.Getter;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferMoneyInteractorTest {

    @Test
    void testGivenDestinationIbanIsInvalid_ShouldPresentError() {
        // given
        TransferMoneyPresenterSpy presenter = new TransferMoneyPresenterSpy();
        TransferMoneyInteractor interactor = new TransferMoneyInteractor(presenter);

        // when
        interactor.execute(new TransferMoneyRequest("DE89 3704 0044 0532 0130 00", ""));

        // then
        assertEquals(1, presenter.getConstraintViolations().size());
        assertEquals("{javax.validation.constraints.NotEmpty.message}", presenter.getConstraintViolations().iterator().next().getMessageTemplate());
    }
}

@Getter
class TransferMoneyPresenterSpy implements TransferMoneyPresenter {
    private Set<ConstraintViolation<TransferMoneyRequest>> constraintViolations;

    @Override
    public void presentError(Set<ConstraintViolation<TransferMoneyRequest>> constraintViolations) {
        this.constraintViolations = constraintViolations;
    }
}
