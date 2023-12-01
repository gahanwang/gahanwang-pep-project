package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    AccountDAO accDAO;

    public AccountService() {
        accDAO = new AccountDAO();
    }

    public AccountService(AccountDAO a) {
        this.accDAO = a;
    }

    public Account registerAcc(Account acc) {
        return accDAO.register(acc);
    }

    public Account loginAcc(Account acc) {
        return accDAO.login(acc);
    }
}
