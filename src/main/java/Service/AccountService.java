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
        if (acc.getPassword().length() < 4 ) {
            System.out.println("Password too short");
            return null;
        } else if (acc.getUsername().isEmpty()) {
            System.out.println("Username cannot be empty");
            return null;
        }
        return accDAO.register(acc);
    }

    public Account loginAcc(Account acc) {
        return accDAO.login(acc);
    }
}
