package main;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel implements Runnable{
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    final int FPS = 60;
    Thread thread;
    Manager m;

    public Panel(){
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        this.addKeyListener(new KeyH());
        this.setFocusable(true);

        m = new Manager();
    }
    public void launch(){
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        double interval = (double) 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (thread != null){
            currentTime = System.nanoTime();

            delta += (currentTime-lastTime)/interval;
            lastTime = currentTime;

            if(delta>=1){
                update();
                repaint();
                delta--;
            }
        }
    }
    private void update(){
        if(!KeyH.pausePressed && !m.gameOver) {
            m.update();
        }
    }
    public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2 = (Graphics2D)g;
        m.draw(g2);
    }
}
