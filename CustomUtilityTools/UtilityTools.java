package CustomUtilityTools;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UtilityTools {

    public static BufferedImage RemakeImage(BufferedImage original, int width, int height){
        BufferedImage output = original;
        output = new BufferedImage(width, height, output.getType());
        Graphics2D graphics2D = output.createGraphics();
        graphics2D.drawImage(original,0,0,Config.NODE_SIZE, Config.NODE_SIZE,null);
        graphics2D.dispose();
        return output;
    }

}
