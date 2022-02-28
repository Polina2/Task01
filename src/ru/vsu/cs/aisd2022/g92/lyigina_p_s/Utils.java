package ru.vsu.cs.aisd2022.g92.lyigina_p_s;

import ru.vsu.cs.util.JTableUtils;
import ru.vsu.cs.util.SwingUtils;

import javax.swing.*;
import java.io.PrintWriter;
import java.util.List;

public class Utils {
    public static void listToTableP(List<Paragraph> list, JTable table) {
        String[][] array = new String[list.size()][1];
        for (int i = 0; i < list.size(); i++) {
            array[i][0] = list.get(i).getName();
        }
        JTableUtils.writeArrayToJTable(table, array);
    }

    public static void listToTableS(List<Style> list, JTable table) {
        String[][] array = new String[list.size()][1];
        for (int i = 0; i < list.size(); i++) {
            array[i][0] = list.get(i).getName();
        }
        JTableUtils.writeArrayToJTable(table, array);
    }

    public static void textToFile(String[] text, String fileName) {
        try {
            PrintWriter out = new PrintWriter(fileName);
            for (String par : text) {
                out.println(par);
            }
            out.close();
        } catch (Exception e) {
            SwingUtils.showErrorMessageBox(e);
        }
    }
}
