package org.penzgtu.Application.menu;

import java.util.Scanner;

public interface MenuInterface {
    <T> T scanValue(String prompt, Class<T> type);

    Scanner getNewScanner();

    void cleanDisplay();

    void outTable(String request);

    void print(String forPrint);

}
