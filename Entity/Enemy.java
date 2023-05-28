package Entity;

import Index.Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends Entity{
    public static BufferedImage centerImage_00, centerImage_01, centerImage_02;
    public Enemy(int cordX, int cordY, int width, int height, int velX, int velY) {
        super(cordX, cordY,width,height);
        this.velX = velX;
        this.velY = velY;
    }
    public void Render(Graphics2D graphics2D){
        switch (animationStage) {
            case 0 -> graphics2D.drawImage(centerImage_00,hitBox.x - Main.offsetX,hitBox.y - Main.offsetY, hitBox.width, hitBox.height, null);
            case 1, 3 -> graphics2D.drawImage(centerImage_01,hitBox.x - Main.offsetX,hitBox.y - Main.offsetY, hitBox.width, hitBox.height, null);
            case 2 -> graphics2D.drawImage(centerImage_02,hitBox.x - Main.offsetX,hitBox.y - Main.offsetY, hitBox.width, hitBox.height, null);
        }
    }
    public void Move() {

        hitBox.x += velX;
        hitBox.y += velY;

        if (!CheckAround()) {
            hitBox.x -= velX;
            hitBox.y -= velY;

            switch (new Random().nextInt(4)) {
                case 0 -> velX = -5;
                case 1 -> velX = 5;
                case 2 -> velY = -5;
                case 3 -> velY = 5;
            }

        }



    }
}
