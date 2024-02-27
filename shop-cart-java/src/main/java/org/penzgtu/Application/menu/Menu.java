package org.penzgtu.Application.menu;

import com.github.freva.asciitable.AsciiTable;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.penzgtu.Application.dao.RequestsSQL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu implements MenuInterface {
    @Override
    public <T> T scanValue(String prompt, Class<T> type) {
        Scanner scanner = getNewScanner();
        System.out.print(prompt);

        while (true) {
            try {
                if (type == Integer.class) {
                    return type.cast(scanner.nextInt());
                } else if (type == Double.class) {
                    return type.cast(scanner.nextDouble());
                } else if (type == String.class) {
                    return type.cast(scanner.nextLine());
                } else if (type == Boolean.class) {
                    return type.cast(scanner.nextBoolean());
                } else {throw new IllegalArgumentException("Unsupported type");}
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid " + type.getSimpleName());
                scanner.nextLine();
            }
        }
    }
    @Override
    public Scanner getNewScanner() {return new Scanner(System.in);}

    @Override
    public void cleanDisplay() {for (int i = 0; i < 100; i++) {System.out.println();}}
    @Override
    public void outTable(String request) {
        try (Connection connection = RequestsSQL.connect()) {
            QueryRunner queryRunner = new QueryRunner();
            List<Map<String, Object>> resultSet = queryRunner.query(connection, request, new MapListHandler());

            String[] headers = resultSet.isEmpty()
                    ? new String[0]
                    : resultSet.get(0).keySet().toArray(new String[0]);
            Object[][] data = resultSet.stream()
                    .map(map -> map.values().toArray())
                    .toArray(Object[][]::new);
            System.out.println(AsciiTable.getTable(headers, data));

        } catch (SQLException e) {e.printStackTrace();}
    }
    @Override
    public void print(String forPrint) {
        System.out.println(forPrint);
    }
}
