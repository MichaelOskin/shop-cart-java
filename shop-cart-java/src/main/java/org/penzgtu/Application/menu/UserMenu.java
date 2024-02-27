package org.penzgtu.Application.menu;

import org.penzgtu.Application.dao.domainDao.BalanceDao;
import org.penzgtu.Application.dao.domainDao.CartDao;
import org.penzgtu.Application.dao.domainDao.SessionDao;
import org.penzgtu.Application.domain.Session;

import java.time.LocalDate;

public class UserMenu {

    private final int id;
    private final BalanceDao balanceDao =  new BalanceDao();
    private static final MenuInterface menu = new Menu();

    public UserMenu(int id) {
        this.id = id;
        balanceDao.setUserWallet(id);
    }


    public void userMenu()  {
        int param = -1;
        while (true) {
            menu.print("0. End the program 1. Basket 2. Top up your balance");
            param = menu.scanValue("int: ", Integer.class);
            switch (param) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    menu.cleanDisplay();
                    new CartMenu(new SessionDao(), new CartDao(), balanceDao).cartMenu();
                    break;
                case 2:
                    double balance = balanceDao.checkBalance();
                    menu.print("Your balance: " + balance);
                    System.out.print("Replenishment: ");
                    double replenishment = menu.scanValue("(',') double:", Double.class);
                    balanceDao.updateBalance(replenishment+balance);
                    menu.cleanDisplay();
                    menu.print("Your balance: " + balanceDao.checkBalance());
                    break;
                case 3:
                    menu.cleanDisplay();
                    break;
                default:
                    menu.print("Error input");
            }
        }
    }
}
