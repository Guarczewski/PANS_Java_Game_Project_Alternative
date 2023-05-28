package Blocks;

import CustomUtilityTools.Config;
import Index.Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Node implements Serializable {
    public int type, explosionPower, bombFusion, decoration = 0;
    public static BufferedImage Node_00, Node_01, Node_02;
    public static BufferedImage Item_00;
    public static BufferedImage Decoration_00;
    public static BufferedImage Bomb_00, Bomb_01, Bomb_02;
    public Rectangle hitBox;
    public boolean hasItem;

    public Node (int cordX, int cordY) {
        type = Config.BLANK_NODE_ID;
        hitBox = new Rectangle((cordX * Config.NODE_SIZE), (cordY * Config.NODE_SIZE), Config.NODE_SIZE, Config.NODE_SIZE);
    }

    public void Render(Graphics2D graphics2D){
        if (type == 1) {
            if (Config.debugOff) {
                graphics2D.drawImage(Node.Node_01, hitBox.x - Main.offsetX, hitBox.y - Main.offsetY, hitBox.width, hitBox.height, null);
            }
            else {
                graphics2D.setColor(Color.DARK_GRAY);
                graphics2D.fillRect(hitBox.x - Main.offsetX, hitBox.y - Main.offsetY, hitBox.width, hitBox.height);
            }
        }
        else if (type == 2) {
            if (Config.debugOff) {
                graphics2D.drawImage(Node.Node_02, hitBox.x - Main.offsetX, hitBox.y - Main.offsetY, hitBox.width, hitBox.height, null);
            }
            else {
                graphics2D.setColor(Color.GRAY);
                graphics2D.fillRect(hitBox.x - Main.offsetX, hitBox.y - Main.offsetY, hitBox.width, hitBox.height);
            }
        }
        else {
            if (Config.debugOff) {
                graphics2D.drawImage(Node.Node_00, hitBox.x - Main.offsetX, hitBox.y - Main.offsetY, hitBox.width, hitBox.height, null);
                if (hasItem)
                    graphics2D.drawImage(Node.Item_00, (hitBox.x - Main.offsetX) + 16, (hitBox.y - Main.offsetY) + 16, hitBox.width - 32, hitBox.height - 32, null);

                graphics2D.setColor(Color.RED);

                //switch (decoration) {
                //    case 0 -> graphics2D.drawImage(Node.Decoration_00, (hitBox.x - Main.offsetX), (hitBox.y - Main.offsetY), hitBox.width, hitBox.height, null);
              //  }

                switch (bombFusion) {
                    case 3 -> graphics2D.drawImage(Node.Bomb_02, hitBox.x - Main.offsetX, hitBox.y - Main.offsetY, hitBox.width, hitBox.height, null);
                    case 2 -> graphics2D.drawImage(Node.Bomb_01, hitBox.x - Main.offsetX, hitBox.y - Main.offsetY, hitBox.width, hitBox.height, null);
                    case 1 -> graphics2D.drawImage(Node.Bomb_00, hitBox.x - Main.offsetX, hitBox.y - Main.offsetY, hitBox.width, hitBox.height, null);
                }

                switch (explosionPower) {
                    case 4 -> graphics2D.fillOval(hitBox.x - Main.offsetX, hitBox.y - Main.offsetY, hitBox.width, hitBox.height);
                    case 3 -> graphics2D.fillOval(hitBox.x - Main.offsetX + 8, hitBox.y - Main.offsetY + 8, hitBox.width - 16, hitBox.height - 16);
                    case 2 -> graphics2D.fillOval(hitBox.x - Main.offsetX + 16, hitBox.y - Main.offsetY + 16, hitBox.width - 32, hitBox.height - 32);
                    case 1 -> graphics2D.fillOval(hitBox.x - Main.offsetX + 24, hitBox.y - Main.offsetY + 24, hitBox.width - 48, hitBox.height - 48);
                }



            }
            else {
                graphics2D.setColor(Color.GREEN);
                graphics2D.fillRect(hitBox.x - Main.offsetX, hitBox.y - Main.offsetY, hitBox.width, hitBox.height);
            }
        }
        graphics2D.setColor(Color.WHITE);
       // String x = "X: " + hitBox.x/Config.NODE_WIDTH + "Y: " + (hitBox.y/Config.NODE_HEIGHT);
      //  graphics2D.drawString(x,hitBox.x - Main.offsetX,hitBox.y - Main.offsetY + Config.NODE_HEIGHT/2);

    }

    public void UpdateNodeSize(){
        hitBox.width = Config.NODE_SIZE;
        hitBox.height = Config.NODE_SIZE;
    }

}
