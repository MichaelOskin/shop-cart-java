package org.penzgtu.Application;

import org.penzgtu.Application.dao.DatabaseManager.DatabaseHandler;
import org.penzgtu.Application.dao.DatabaseManager.InsertTables;
import org.penzgtu.Application.dao.domainDao.LoginPasswordDao;
import org.penzgtu.Application.domain.LoginPassword;
import org.penzgtu.Application.domain.Session;
import org.penzgtu.Application.domain.User;
import org.penzgtu.Application.domain.UserWallet;
import org.penzgtu.Application.menu.Menu;
import org.penzgtu.Application.menu.MenuInterface;
import java.time.LocalDate;
import java.util.ArrayList;
import static org.penzgtu.Application.dao.DatabaseManager.DataValidator.validLoginPassword;

public class AuthenticationService {

    private static final MenuInterface menu = new Menu();
    public static void signUp() {

        String nameSurname = menu.scanValue("Enter your Name Surname:", String.class);

        ArrayList<String> loginPassword = validLoginPassword();
        if (LoginPasswordDao.checkLogin(loginPassword.get(0))) {
            signUp();
        } else {
            int lastId = DatabaseHandler.getLastInsertedId("Users", "id");
            dataLoadForUser(lastId, nameSurname, 0, loginPassword.get(0), loginPassword.get(1));
        }
    }

    public static int signIn() {
        ArrayList<String> loginPassword = validLoginPassword();
        LoginPasswordDao authentication = new LoginPasswordDao(
                                          new LoginPassword(0, loginPassword.get(0), loginPassword.get(1)));
        return authentication.getIdByLoginPassword();
    }

    private static void dataLoadForUser(int lastId, String nameSurname, int tier, String login, String password) {
        lastId++;
        InsertTables.insertUsers(new User(lastId, nameSurname, tier));
        InsertTables.insertPasswords(new LoginPassword(lastId, login, password));
        InsertTables.insertBalance(new UserWallet(lastId, 0));
        InsertTables.insertSession(new Session(lastId, 1, LocalDate.now()));
    }
}
