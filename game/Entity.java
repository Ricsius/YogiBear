package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Entity {

    protected Level level;
    protected int positionX;
    protected int positionY;
    private Image image;

    public Entity(Level level, int x, int y) {
        this.level = level;
        this.positionX = x;
        this.positionY = y;
    }

    public void setPositionX(int x) {
        this.positionX = x;
    }

    public void setPositionY(int y) {
        this.positionY = y;
    }

    public int getPositionX() {
        return  this.positionX;
    }

    public int getPositionY() {
        return  this.positionY;
    }

    public Image getImage() {
        return this.image;
    }

    public void crashesInto(Entity entity) { }

    public void update() {}

    protected void setImage(String imagePath) {
        BufferedImage bufferedImage;

        try {
            bufferedImage = ImageIO.read(getClass().getResource(imagePath));
            this.image = bufferedImage;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
