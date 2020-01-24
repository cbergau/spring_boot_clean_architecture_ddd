package de.christianbergau.bankaccountcli;

import de.christianbergau.bankaccount.data.InMemoryTransactionRepository;
import de.christianbergau.bankaccount.repository.SaveTransactionRepository;
import de.christianbergau.bankaccount.usecase.TransferMoneyInteractor;
import de.christianbergau.bankaccount.usecase.TransferMoneyPresenter;
import de.christianbergau.bankaccount.usecase.TransferMoneyRequest;
import de.christianbergau.bankaccount.usecase.TransferMoneyUseCase;
import de.christianbergau.bankaccountcli.presenter.TransferMoneyCliPresenter;

public class App {
    public static void main(String... args) {
        switch (args[0]) {
            case "transfer":
                TransferMoneyPresenter presenter = new TransferMoneyCliPresenter();
                SaveTransactionRepository repository = new InMemoryTransactionRepository();
                TransferMoneyUseCase useCase = new TransferMoneyInteractor(presenter, repository);
                useCase.execute(new TransferMoneyRequest(args[1], args[2], Double.parseDouble(args[3])));
                break;
            default:
                System.err.println("Unknown Command");
                break;
        }
    }
}
