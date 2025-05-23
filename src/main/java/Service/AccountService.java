package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account) {
        if (account.getUsername().isEmpty()) {
            return null;
        }

        if (account.getPassword().length() < 4) {
            return null;
        }

        else {
            return accountDAO.createAccount(account);
        }
    }

    public Account LoginToAccount(String username, String password) {
        return accountDAO.verifyAccount(username, password);
    }
}
