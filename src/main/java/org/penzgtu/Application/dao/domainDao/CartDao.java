package org.penzgtu.Application.dao.domainDao;

import lombok.Getter;
import lombok.Setter;
import org.penzgtu.Application.dao.DatabaseManager.DataValidator;
import org.penzgtu.Application.dao.DatabaseManager.DatabaseHandler;
import org.penzgtu.Application.dao.DatabaseManager.InsertTables;
import org.penzgtu.Application.domain.Cart;

@Getter
@Setter
public class CartDao {
    private Cart product;
    
    public void insertBasket() {InsertTables.insertBasket(this.product);}

    public void addProductToCart() {
        DatabaseHandler.addProductToCart(this.product);
    }

    public void removeProductToCart(int session_id, int product_id) {
        DatabaseHandler.removeProductFromCart(session_id, product_id);
    }

    public boolean isValidPhoneId(int id) {return DataValidator.isValidPhoneId(id);}

    public boolean isValidBasketId(int product_id) {
        return DataValidator.isValidBasketId(this.product.getSession_id(), product_id);
    }
}
