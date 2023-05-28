package Entity;

import Index.Main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    public static BufferedImage centerImage_00, centerImage_01, centerImage_02;
    public static BufferedImage rightImage_00, rightImage_01, rightImage_02;
    public static BufferedImage leftImage_00, leftImage_01, leftImage_02;
    public Player (int cordX, int cordY, int width, int height) {
        super(cordX,cordY,width,height);
    }
    @Override
    public void Render(Graphics2D graphics2D){

        if (prevVelX > 0){
            switch (animationStage) {
                case 0 -> graphics2D.drawImage(rightImage_00, hitBox.x - Main.offsetX, hitBox.y - Main.offsetY, hitBox.width, hitBox.height, null);
                case 1, 3 -> graphics2D.drawImage(rightImage_01, hitBox.x - Main.offsetX, hitBox.y - Main.offsetY, hitBox.width, hitBox.height, null);
                case 2 -> graphics2D.drawImage(rightImage_02, hitBox.x - Main.offsetX, hitBox.y - Main.offsetY, hitBox.width, hitBox.height, null);
            }
        }
        else if (prevVelX < 0){
            switch (animationStage) {
                case 0 -> graphics2D.drawImage(leftImage_00, hitBox.x - Main.offsetX, hitBox.y - Main.offsetY, hitBox.width , hitBox.height, null);
                case 1,3 -> graphics2D.drawImage(leftImage_01, hitBox.x - Main.offsetX, hitBox.y - Main.offsetY, hitBox.width, hitBox.height, null);
                case 2 -> graphics2D.drawImage(leftImage_02, hitBox.x - Main.offsetX, hitBox.y - Main.offsetY, hitBox.width, hitBox.height, null);
            }
        }
        else if (velX == 0 && velY == 0) {
            switch (animationStage) {
                case 0 -> graphics2D.drawImage(centerImage_00, hitBox.x - Main.offsetX, hitBox.y - Main.offsetY, hitBox.width, hitBox.height, null);
                case 1,3 -> graphics2D.drawImage(centerImage_01, hitBox.x - Main.offsetX, hitBox.y - Main.offsetY, hitBox.width, hitBox.height, null);
                case 2 -> graphics2D.drawImage(centerImage_02, hitBox.x - Main.offsetX, hitBox.y - Main.offsetY, hitBox.width, hitBox.height, null);
            }
        }
    }
    @Override
    public void Move() {

        Main.offsetX += velX;
        Main.offsetY += velY;

        hitBox.x += velX;
        hitBox.y += velY;

        if (!CheckAround()) {

            Main.offsetX -= velX;
            Main.offsetY -= velY;

            hitBox.x -= velX;
            hitBox.y -= velY;

        }

        if (prevVelX != velX)
            prevVelX = velX;

        if (prevVelY != velY)
            prevVelY = velY;

        velX = velY = 0;

    }
}
