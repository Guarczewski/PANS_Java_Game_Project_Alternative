package Index;

import CustomUtilityTools.Config;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class LightArea {

    private BufferedImage Shadows;

    public LightArea(int CircleSize){
        UpdateLight(CircleSize);
    }

    public void UpdateLight(int CircleSize){

        Shadows = new BufferedImage(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2D = (Graphics2D) Shadows.getGraphics();

        int circleCenterX, circleCenterY;
        circleCenterX = (Config.WINDOW_WIDTH / 2) + (Config.NODE_SIZE / 2);
        circleCenterY = (Config.WINDOW_HEIGHT / 2) + (Config.NODE_SIZE / 2);

        Area Rectangle = new Area(new Rectangle2D.Double(0, 0, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT));
        Area Circle = new Area(new Ellipse2D.Double(circleCenterX, circleCenterY, CircleSize, CircleSize));
        Rectangle.subtract(Circle);

        Color[] color = new Color[5];
        float[] spacing = new float[5];

        color[0] = new Color(0,0,0,0f);
        color[1] = new Color(0,0,0,0.25f);
        color[2] = new Color(0,0,0,0.50f);
        color[3] = new Color(0,0,0,0.75f);
        color[4] = new Color(0,0,0,0.95f);

        spacing[0] = 0f;
        spacing[1] = 0.25f;
        spacing[2] = 0.50f;
        spacing[3] = 0.75f;
        spacing[4] = 0.95f;

        RadialGradientPaint gradientPaint = new RadialGradientPaint(circleCenterX, circleCenterY, CircleSize,spacing,color);

        g2D.setPaint(gradientPaint);
        g2D.fill(Circle);
        g2D.fill(Rectangle);
        g2D.dispose();
    }

    public void Render(Graphics2D g2D) {
        g2D.drawImage(Shadows,0,0,null);
    }

}
