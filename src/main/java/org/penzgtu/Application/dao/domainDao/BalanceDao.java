package org.penzgtu.Application.dao.domainDao;

import lombok.Getter;
import org.penzgtu.Application.dao.DatabaseManager.DatabaseHandler;
import org.penzgtu.Application.dao.DatabaseManager.InsertTables;
import org.penzgtu.Application.domain.UserWallet;
@Getter
public class BalanceDao {
    private UserWallet userWallet;
    public void insertBalance() {InsertTables.insertBalance(this.userWallet);}

    public void updateBalance(double newBalance) {
        DatabaseHandler.updateBalance(this.userWallet.getUser_id(), newBalance);
    }
    public double checkBalance() {return DatabaseHandler.checkBalance(this.userWallet.getUser_id());}

    public void setUserWallet(int id) {this.userWallet = new UserWallet(id, DatabaseHandler.checkBalance(id));}

}