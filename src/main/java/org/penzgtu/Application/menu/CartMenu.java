package org.penzgtu.Application.menu;

import org.penzgtu.Application.dao.RequestsSQL;
import org.penzgtu.Application.dao.domainDao.BalanceDao;
import org.penzgtu.Application.dao.domainDao.CartDao;
import org.penzgtu.Application.dao.domainDao.SessionDao;
import org.penzgtu.Application.domain.Cart;
import org.penzgtu.Application.domain.Session;

import java.time.LocalDate;

public class CartMenu {

    private static final MenuInterface menu = new Menu();
    private final SessionDao sessionDao;
    private final CartDao cartDao;
    private final BalanceDao balanceDao;


    public CartMenu(SessionDao dSession, CartDao dCart, BalanceDao dBalance) {
        this.sessionDao = dSession;
        this.cartDao = dCart;
        this.balanceDao = dBalance;

        sessionDao.setSession(new Session(balanceDao.getUserWallet().getUser_id(), 1, LocalDate.now()));
        cartDao.setProduct(new Cart(sessionDao.activeSession(), -1, -1));
    }

    public void cartMenu() {
        int param; boolean cycle = true; int product_id;
        while (cycle) {
            menu.print("0. back   1. show cart 2. add " +
                       "3. remove 4. store     5. buy 6. clear");
            param = menu.scanValue("int: ", Integer.class);
            switch (param) {
                case 0:
                    cycle = false;
                    break;
                case 1:
                    menu.cleanDisplay();
                    menu.outTable
                            (
                        "SELECT Phones.NameCPU, Phones.Name, Basket.quantity, Basket.quantity * Phones.Price AS total\n" +
                                "FROM Basket\n" +
                                "INNER JOIN Phones ON Basket.product_id = Phones.id\n" +
                                "WHERE Basket.session_id = " + sessionDao.activeSession() + ";\n"
                            );
                    menu.print("Total sum basket: " + sessionDao.calculateTotalPriceForSession());
                    break;
                case 2:
                    menu.cleanDisplay();
                    product_id = menu.scanValue("Enter the product_id to add:", Integer.class);
                    if (cartDao.isValidPhoneId(product_id)) {
                        cartDao.setProduct(new Cart(sessionDao.activeSession(), product_id, 1));
                        cartDao.addProductToCart();
                    } else {
                        menu.print("error id");
                    }
                    break;
                case 3:
                    menu.cleanDisplay();
                    product_id = menu.scanValue("Enter the product_id to delete: ", Integer.class);
                    if (cartDao.isValidBasketId(product_id)) {
                        cartDao.removeProductToCart(sessionDao.activeSession(), product_id);
                    } else {
                        menu.print("error id");
                    }
                    break;
                case 4:
                    menu.cleanDisplay();
                    menu.outTable("SELECT * FROM Phones;");
                    break;
                case 5:
                    menu.cleanDisplay();
                    double basketSum = sessionDao.calculateTotalPriceForSession();
                    double balance = balanceDao.checkBalance();
                    if (basketSum == 0) {
                        menu.print("Empty Cart");
                    } else if (basketSum <= balance) {
                        balanceDao.updateBalance(balance-basketSum);
                        RequestsSQL.executeUpdate
                                (
                                "UPDATE Sessions\n" +
                                    "SET \"is_active\" = 0, \"date\" = '"+LocalDate.now()+"'\n" +
                                    "WHERE \"user_id\" = " + balanceDao.getUserWallet().getUser_id() +"\n" +
                                    "AND \"is_active\" = 1;"
                                );
                        // update date session
                        sessionDao.getSession().setDate(LocalDate.now());
                        sessionDao.insertSession();
                    } else {
                        menu.print("You don't have enough money. Top up your balance");
                    }
                    cycle=false;
                    break;
                case 6:
                    menu.cleanDisplay();
                default:
                    menu.print("Error input");
            }
        }
    }
}
