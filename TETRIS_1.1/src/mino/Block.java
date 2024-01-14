package mino;/**every class in this package is the coordinated for the tetrominos apart from Block and Mino**/

import java.awt.*;
/**the Block class is the foundation of all the tetrominos since they are all made of exactly 4 blocks**/
public class Block extends Rectangle {
    public int x, y;
    public static final int SIZE = 30;
    public Color c;

    public Block(Color c){
        this.c = c;
    }/**the colour**/
    public void draw(Graphics2D g2){
        int margin = 2;
        g2.setColor(c);
        g2.fillRect(x + margin, y + margin, SIZE - (margin*2), SIZE - (margin*2));
    }
}
