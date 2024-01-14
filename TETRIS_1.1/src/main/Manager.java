package main;

import mino.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
/**
 *This class manages the play of the game, the running the pausing and the ending
 **/
public class Manager {
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;
    Mino nextMino;
    final int NEXT_MINO_X;
    final int NEXT_MINO_Y;
    public static ArrayList<Block> staticBlocks = new ArrayList<>();

    public static int dropInterval = 60;
    boolean gameOver;

    int level = 1;
    int lines;
    int score;

    public Manager() {
        left_x = (Panel.WIDTH / 2) - (WIDTH / 2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        MINO_START_X = left_x + (WIDTH / 2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        NEXT_MINO_X = right_x + 175;
        NEXT_MINO_Y = top_y + 500;

        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
        nextMino = pickMino();
        nextMino.setXY(NEXT_MINO_X, NEXT_MINO_Y);
    }

    private Mino pickMino() {/**this method manages the random generation if the tetrominos**/
        Mino m = null;
        int i = new Random().nextInt(7);
        switch (i) {
            case 0:
                m = new L();
                break;
            case 1:
                m = new L_Reverse();
                break;
            case 2:
                m = new Square();
                break;
            case 3:
                m = new Line();
                break;
            case 4:
                m = new T();
                break;
            case 5:
                m = new Z();
                break;
            case 6:
                m = new Z_Reverse();
                break;
        }
        return m;
    }

    public void update() {/**this method updates the game at a certain framerate, in this case 60**/
        if (!currentMino.active) {
            staticBlocks.add(currentMino.b[0]);
            staticBlocks.add(currentMino.b[1]);
            staticBlocks.add(currentMino.b[2]);
            staticBlocks.add(currentMino.b[3]);

            if(currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y){
                gameOver = true;
            }

            currentMino.deactivating = false;

            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);
            nextMino = pickMino();
            nextMino.setXY(NEXT_MINO_X, NEXT_MINO_Y);

            checkDelete();
        } else {
            currentMino.update();
        }
    }
    private void checkDelete(){/**it checks for completed rows and deletes them**/
        int x = left_x;
        int y = top_y;
        int blockCount = 0;
        while (x<right_x && y<bottom_y){
            for(int i = 0; i<staticBlocks.size(); i++){
                if(staticBlocks.get(i).x == x && staticBlocks.get(i).y == y){
                    blockCount++;
                }
            }
            x+=Block.SIZE;
            if(x == right_x){
                if(blockCount == 12){
                    for(int i = staticBlocks.size()-1; i>-1; i--){
                        if(staticBlocks.get(i).y == y){
                            staticBlocks.remove(i);
                        }
                    }
                    for(int i = 0; i<staticBlocks.size(); i++){
                        if(staticBlocks.get(i).y < y){
                            staticBlocks.get(i).y += Block.SIZE;
                        }
                    }
                }
                blockCount = 0;
                x = left_x;
                y+=Block.SIZE;
            }
        }
    }
    public void draw(Graphics2D g2) {/**the graphics**/
        for(int i = 0; i<staticBlocks.size(); i++){
            staticBlocks.get(i).draw(g2);
        }
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x - 4, top_y - 4, WIDTH + 8, HEIGHT + 8);

        int x = right_x + 100;
        int y = bottom_y - 200;
        g2.drawRect(x, y, 200, 200);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("NEXT", x + 60, y + 60);


        if (currentMino != null) {
            currentMino.draw(g2);
        }
        nextMino.draw(g2);
        g2.setColor(Color.RED);
        if(gameOver){
            x = 10;
            y = 50;
            g2.drawString("GAME_OVER", x, y);
        }
        if (KeyH.pausePressed) {
            x = 10;
            y = 50;
            g2.drawString("PAUSED", x, y);
        }
        g2.setColor(Color.WHITE);
        x = 10;
        y = 200;
        g2.drawString("ASD_FOR_MOVEMENT", x, y);
        y = 240;
        g2.drawString("W_TO_ROTATE", x, y);
        y = 280;
        g2.drawString("Esc_TO_PAUSE", x, y);
    }
}
