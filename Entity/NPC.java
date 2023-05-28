package Entity;

import Index.Main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NPC extends Entity{

    public static BufferedImage NPC_00;

    public NPC(int cordX, int cordY, int width, int height) {
        super(cordX, cordY, width, height);
    }

    @Override
    public void Render(Graphics2D graphics2D) {
        graphics2D.drawImage(NPC_00,hitBox.x - Main.offsetX,hitBox.y - Main.offsetY, hitBox.width, hitBox.height, null);
    }

    @Override
    public void Move() {

    }
}
