package game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class JPanelWithBackgroundImage extends JPanel {
    private Image backgroundImage;

    public JPanelWithBackgroundImage(String backgroundImagePath) {
        BufferedImage bufferedImage;

        try {
            URL url = getClass().getResource(backgroundImagePath);
            bufferedImage = ImageIO.read(url);
            this.backgroundImage = bufferedImage;

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image image = this.backgroundImage.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT);
        g.drawImage(image, 0, 0, null);
    }
}