package Package;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Boxing {
    String walletString;

    public static GradientPaint gp = new GradientPaint(0, 0, Color.ORANGE, 100, 100, Color.RED);
    public static Font infoFont = new Font("Arial",Font.BOLD,22);
    int  imageBoxSize = 125, imageSize = (imageBoxSize - 20);

    BufferedImage myManager = null;

    public Boxing() {
        try {
            BufferedImage tempMyManager = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/Player/Player_Icon.png")));
            myManager = imageMaker(tempMyManager);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    BufferedImage imageMaker(BufferedImage input){
        int diameter = Math.min(input.getWidth(), input.getHeight());
        BufferedImage mask = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = mask.createGraphics();
        applyQualityRenderingHints(g2d);
        g2d.fillOval(0, 0, diameter - 1, diameter - 1);
        g2d.dispose();
        BufferedImage circled = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        g2d = circled.createGraphics();
        applyQualityRenderingHints(g2d);
        int x = (diameter - input.getWidth()) / 2;
        int y = (diameter - input.getHeight()) / 2;
        g2d.drawImage(input, x, y, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
        g2d.drawImage(mask, 0, 0,null);
        return circled;
    }

    public void paintComponent(Graphics2D g2D){
        // Progress Bar
        g2D.setColor(Color.ORANGE);
        g2D.fillPolygon(new int[] {imageSize / 2, imageSize / 2, 600, 620}, new int[] {10, 50, 50, 10},4);
        g2D.fillPolygon(new int[] {imageSize / 2, imageSize / 2, 560, 580}, new int[] {55, 120, 120, 55},4);

        g2D.setColor(Color.DARK_GRAY);
        g2D.fillOval(0,0, imageBoxSize, imageBoxSize);

        g2D.setPaint(gp);
        g2D.fillOval(10,10, imageBoxSize - 20, imageBoxSize - 20);

        g2D.drawImage(myManager,10, 10, imageSize,imageSize,null);

        g2D.setColor(Color.BLACK);
        g2D.setFont(infoFont);
        g2D.drawString("Player", 200,40);
        g2D.drawString("Upgrade Points: ", 210,100);

    }

    public static void applyQualityRenderingHints(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }
}
