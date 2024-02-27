package org.penzgtu.Application.dao.domainDao;

import lombok.Setter;
import org.penzgtu.Application.dao.DatabaseManager.InsertTables;
import org.penzgtu.Application.domain.User;
@Setter
public class UserDao {
    private User user;
    public void insertUser() {
        InsertTables.insertUsers(this.user);
    }
}
