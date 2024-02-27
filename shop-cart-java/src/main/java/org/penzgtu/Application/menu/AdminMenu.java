package org.penzgtu.Application.menu;

import org.penzgtu.Application.dao.CreateTableSQLite;
import org.penzgtu.Application.dao.GenerateData;
import org.penzgtu.Application.dao.RequestsSQL;

public class AdminMenu {

    private static final MenuInterface menu = new Menu();
    public static void adminMenu() {
        int param = -1;
        while (true) {
            menu.print("0. exit program       1. RequestSQL         2. outTable(SQL)  3. Generate Data\n" +
                       "4. Out active Tables  5. Create Any Table  6. clear");
            param = menu.scanValue("int: ", Integer.class);
            switch (param) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    printParam("Enter SQL Request:");
                    RequestsSQL.executeUpdate(menu.scanValue("Request: ", String.class));
                    break;
                case 2:
                    printParam("Enter SQL Request for print Table:");
                    menu.outTable(menu.scanValue("Request: ", String.class));
                    break;
                case 3:
                    printParam("Generate the data in the required amount (n int). (Basket is not generated)");
                    GenerateData.generateAllTables(menu.scanValue("int: ", Integer.class));
                    break;
                case 4:
                    printParam("List of existing tables:");
                    CreateTableSQLite.outActiveTables();
                    break;
                case 5:
                    printParam("Tables:\n\n1. Users\n2. Passwords\n3. UserWallet\n4. Basket\n5. Phones\n" +
                        "6.Sessions\n\n");
                    CreateTableSQLite.createAnyTable(menu.scanValue("int: ", Integer.class));
                    break;
                case 6:
                    menu.cleanDisplay();
                    break;
                default:
                    menu.print("Error input");
            }
        }
    }
    static void printParam(String print) {
        menu.cleanDisplay();
        menu.print(print);
    }
}
