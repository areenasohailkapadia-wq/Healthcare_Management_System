package view;

import javax.swing.*;
import java.awt.*;

public final class MessageUtil {
    private MessageUtil() {}

    public static void info(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void error(Component parent, String msg, Exception e) {
        String text = msg;
        if (e != null && e.getMessage() != null) text += "\n\n" + e.getMessage();
        JOptionPane.showMessageDialog(parent, text, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static boolean confirm(Component parent, String msg) {
        return JOptionPane.showConfirmDialog(parent, msg, "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }
}
