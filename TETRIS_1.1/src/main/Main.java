package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("TETRIS_1.0");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        Panel p = new Panel();
        window.add(p);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        p.launch();
    }
}