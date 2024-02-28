package org.penzgtu.Application.menu;

import lombok.Setter;
import org.penzgtu.Application.dao.CreateTableSQLite;
import org.penzgtu.Application.dao.DatabaseManager.DataValidator;

import java.io.IOException;

import static org.penzgtu.Application.AuthenticationService.signIn;
import static org.penzgtu.Application.AuthenticationService.signUp;

@Setter
public class AuthenticationServiceMenu {

    private static final MenuInterface menu = new Menu();
    public static void authenticationMenu() {
        if (DataValidator.DatabaseIsEmpty()) {
            try {
                new CreateTableSQLite().InitialDatabase();
            } catch (IOException e) {throw new RuntimeException(e);}
        }
        menu.cleanDisplay();
        menu.print("You have an account? - (true || false)");
        if (menu.scanValue("Boolean:", Boolean.class)) {
            int user_id = signIn();
            if (user_id == 0) {
                menu.print("There is no such user");
                System.exit(0);
            } else if (user_id == 1) {
                AdminMenu.adminMenu();
            } else {
                UserMenu userMenu =  new UserMenu(user_id);
                userMenu.userMenu();
            }
        } else {signUp();}
    }
}
