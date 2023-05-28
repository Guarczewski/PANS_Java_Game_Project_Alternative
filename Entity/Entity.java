package Entity;

import CustomUtilityTools.Config;
import Index.Main;

import java.awt.*;

public abstract class Entity {
    public Rectangle hitBox;
    public int velX, velY, prevVelX, prevVelY, arrayCordX, arrayCordY, animationStage;
    public double maxHealth, currentHealth;
    public Entity(int cordX, int cordY, int width, int height) {
        hitBox = new Rectangle(cordX + (Config.NODE_SIZE / 4),cordY + (Config.NODE_SIZE / 4),width - (Config.NODE_SIZE / 4),height - (Config.NODE_SIZE / 4));
        currentHealth = maxHealth = 100;
    }
    public abstract void Render(Graphics2D graphics2D);
    public abstract void Move();
    public void UpdateHitBoxCord(int x, int y){
        hitBox.x = x;
        hitBox.y = y;

    }
    public void UpdateArrayCordX(){
        arrayCordX = (int) Math.round((double) hitBox.x / Config.NODE_SIZE);
    }
    public void UpdateArrayCordY(){
        arrayCordY = (int) Math.round((double) hitBox.y / Config.NODE_SIZE);
    }

    protected boolean CheckAround(){
        boolean success = true;

        UpdateArrayCordX();
        UpdateArrayCordY();

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {

                if (arrayCordX + i < 0 || arrayCordY + j < 0 || arrayCordX + i >= Config.SIZE_WIDTH || arrayCordY + j >= Config.SIZE_HEIGHT) {
                    continue;
                }

                if (Main.nodes[arrayCordX + i][arrayCordY + j].type != Config.BLANK_NODE_ID) {
                    if (hitBox.intersects(Main.nodes[arrayCordX + i][arrayCordY + j].hitBox)) {
                        success = false;
                        break;
                    }
                }

            }
        }

        return success;

    }

}
