package Index;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MotionBlurExample extends JPanel {
    private BufferedImage bufferedImage;

    public MotionBlurExample() {
        bufferedImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.RED);
        g2d.fillOval(100, 100, 200, 200);
        g2d.dispose();

        bufferedImage = applyMotionBlur(bufferedImage, 20, 135, 5);
    }

    private BufferedImage applyMotionBlur(BufferedImage image, float angle, float distance, int iterations) {
        // Create an AffineTransform that will be used to apply motion blur
        AffineTransform transform = new AffineTransform();
        transform.translate(distance * Math.cos(Math.toRadians(angle)), distance * Math.sin(Math.toRadians(angle)));

        // Create an AffineTransformOp that will apply the motion blur transform to the image
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);

        // Create a new BufferedImage to hold the blurred image
        BufferedImage blurredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        // Apply the motion blur effect to the image for the desired number of iterations
        for (int i = 0; i < iterations; i++) {
            op.filter(image, blurredImage);
            image.setData(blurredImage.getData());
        }

        return blurredImage;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
        g2d.drawImage(bufferedImage, 0, 0, null);
        g2d.dispose();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Motion Blur Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0,0,500,500);
        frame.add(new MotionBlurExample());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
