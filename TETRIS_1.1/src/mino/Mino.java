package mino;

import main.KeyH;
import main.Manager;

import java.awt.*;

public class Mino {
    public Block[] b = new Block[4];
    public Block[] tempB = new Block[4];
    int autoDropCounter = 0;
    public int direction = 1;

    /**
     * There are 4 directions 1|2|3|4
     **/
    boolean leftCol, rightCol, bottomCol;
    public boolean active = true;
    public boolean deactivating;
    int deactivateCounter = 0;

    public void create(Color c) {/**creates the tetromino*/
        b[0] = new Block(c);
        b[1] = new Block(c);
        b[2] = new Block(c);
        b[3] = new Block(c);
        tempB[0] = new Block(c);/**temp meaning the temporary cords since it's still moving**/
        tempB[1] = new Block(c);
        tempB[2] = new Block(c);
        tempB[3] = new Block(c);
    }

    public void setXY(int x, int y) {
    }

    public void updateXY(int direction) {
        checkRotationCollision();
        if (!leftCol && !rightCol && !bottomCol) {
            this.direction = direction;
            b[0].x = tempB[0].x;
            b[0].y = tempB[0].y;
            b[1].x = tempB[1].x;
            b[1].y = tempB[1].y;
            b[2].x = tempB[2].x;
            b[2].y = tempB[2].y;
            b[3].x = tempB[3].x;
            b[3].y = tempB[3].y;
        }
    }

    public void getDirection1() {
    }

    public void getDirection2() {
    }

    public void getDirection3() {
    }

    public void getDirection4() {
    }

    public void checkMovementCollision() {
        leftCol = false;
        rightCol = false;
        bottomCol = false;

        checkStaticBlockCollision();

        for (int i = 0; i < b.length; i++) {
            if (b[i].x == Manager.left_x) {
                leftCol = true;
            }
        }
        for (int i = 0; i < b.length; i++) {
            if (b[i].x + Block.SIZE == Manager.right_x) {
                rightCol = true;
            }
        }
        for (int i = 0; i < b.length; i++) {
            if (b[i].y + Block.SIZE == Manager.bottom_y) {
                bottomCol = true;
            }
        }
    }

    public void checkRotationCollision() {
        leftCol = false;
        rightCol = false;
        bottomCol = false;

        checkStaticBlockCollision();

        for (int i = 0; i < b.length; i++) {
            if (tempB[i].x < Manager.left_x) {
                leftCol = true;
            }
        }
        for (int i = 0; i < b.length; i++) {
            if (tempB[i].x + Block.SIZE > Manager.right_x) {
                rightCol = true;
            }
        }
        for (int i = 0; i < b.length; i++) {
            if (tempB[i].y + Block.SIZE > Manager.bottom_y) {
                bottomCol = true;
            }
        }
    }
    private void checkStaticBlockCollision(){/**allows to place minos on top of the already placed ones**/
        for(int i = 0; i<Manager.staticBlocks.size(); i++){
            int targetX = Manager.staticBlocks.get(i).x;
            int targetY = Manager.staticBlocks.get(i).y;

            for(int j = 0; j<b.length; j++){
                if(b[j].y + Block.SIZE == targetY && b[j].x == targetX){
                    bottomCol = true;
                }
            }
            for(int k = 0; k<b.length; k++){
                if(b[k].x - Block.SIZE == targetX && b[k].y == targetY){
                    leftCol = true;
                }
            }
            for(int l = 0; l<b.length; l++){
                if(b[l].x + Block.SIZE == targetX && b[l].y == targetY){
                    rightCol = true;
                }
            }
        }
    }
    public void update() {
        if(deactivating){
            deactivate();
        }
        if (KeyH.upPressed) {
            switch (direction) {
                case 1:
                    getDirection2();
                    break;
                case 2:
                    getDirection3();
                    break;
                case 3:
                    getDirection4();
                    break;
                case 4:
                    getDirection1();
                    break;
            }
            KeyH.upPressed = false;
        }
        checkMovementCollision();

        if (KeyH.downPressed) {
            if (!bottomCol) {
                b[0].y += Block.SIZE;
                b[1].y += Block.SIZE;
                b[2].y += Block.SIZE;
                b[3].y += Block.SIZE;

                autoDropCounter = 0;
            }
            KeyH.downPressed = false;
        }
        if (KeyH.leftPressed) {
            if (!leftCol) {
                b[0].x -= Block.SIZE;
                b[1].x -= Block.SIZE;
                b[2].x -= Block.SIZE;
                b[3].x -= Block.SIZE;
            }
            KeyH.leftPressed = false;
        }
        if (KeyH.rightPressed) {
            if (!rightCol) {
                b[0].x += Block.SIZE;
                b[1].x += Block.SIZE;
                b[2].x += Block.SIZE;
                b[3].x += Block.SIZE;
            }
            KeyH.rightPressed = false;
        }
        if (bottomCol){
            deactivating = true;

        }
        else{
            autoDropCounter++;
            if (autoDropCounter == Manager.dropInterval) {
                b[0].y += Block.SIZE;
                b[1].y += Block.SIZE;
                b[2].y += Block.SIZE;
                b[3].y += Block.SIZE;

                autoDropCounter = 0;
            }
        }

    }
    private void deactivate(){
      deactivateCounter++;
      if(deactivateCounter == 45){
          deactivateCounter = 0;
          checkMovementCollision();
          if(bottomCol){
              active = false;
          }
      }
    }
    public void draw(Graphics2D g2) {
        int margin = 2;
        g2.setColor(b[0].c);
        g2.fillRect(b[0].x + margin, b[0].y + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
        g2.fillRect(b[1].x + margin, b[1].y + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
        g2.fillRect(b[2].x + margin, b[2].y + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
        g2.fillRect(b[3].x + margin, b[3].y + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
    }

    /* just something to copy and paste since its used so many times
    tempB[0].x = b[0].x;
    tempB[0].y = b[0].y;
    tempB[1].x = b[0].x;
    tempB[1].y = b[0].y;
    tempB[2].x = b[0].x;
    tempB[2].y = b[0].y;
    tempB[3].x = b[0].x;
    tempB[3].y = b[0].y;
     */

}
