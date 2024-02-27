package org.penzgtu.Application.dao.domainDao;

import lombok.Getter;
import lombok.Setter;
import org.penzgtu.Application.dao.DatabaseManager.DatabaseHandler;
import org.penzgtu.Application.dao.DatabaseManager.InsertTables;
import org.penzgtu.Application.domain.Session;

@Getter
@Setter
public class SessionDao {
    private Session session;

    public void insertSession() {InsertTables.insertSession(this.session);}

    public int activeSession() {
        int id_session = DatabaseHandler.activeSession(session.getUser_id());
        return id_session;
    }

    public double calculateTotalPriceForSession() {
        return DatabaseHandler.calculateTotalPriceForSession(activeSession());
    }
}
