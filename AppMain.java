package main;

import controller.MainController;
import view.MainGUI;

import javax.swing.*;
import java.io.File;

public class AppMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            File outputs = new File("outputs");
            MainController controller = new MainController(outputs);
            MainGUI gui = new MainGUI(controller);
            gui.setVisible(true);
        });
    }
}
