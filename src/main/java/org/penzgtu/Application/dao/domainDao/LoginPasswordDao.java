package org.penzgtu.Application.dao.domainDao;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.penzgtu.Application.dao.DatabaseManager.DatabaseHandler;
import org.penzgtu.Application.dao.DatabaseManager.InsertTables;
import org.penzgtu.Application.domain.LoginPassword;

@Setter
@AllArgsConstructor
public class LoginPasswordDao {

    private LoginPassword loginPassword;

    public void insertLogin() {
        InsertTables.insertPasswords(this.loginPassword);
    }
    public int getIdByLoginPassword() {
        int id = DatabaseHandler.getIdByLoginPassword(this.loginPassword);
        this.loginPassword.setUser_id(id);
        return id;
    }
    public static boolean checkLogin(String login) {return DatabaseHandler.checkLogin(login);}
}
