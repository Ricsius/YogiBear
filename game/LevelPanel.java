package game;

import javax.swing.*;
import java.awt.*;

public class LevelPanel extends JPanel {

    Level level;

    public LevelPanel() {
        super();
    }

    public void setLevelToDraw(Level level) {
        this.level = level;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x = 0;
        int y = 0;
        int width = this.getWidth() / this.level.getWidth();
        int height = this.getHeight() / this.level.getHeight();

        for(int yPos = 0; yPos < this.level.getHeight(); ++yPos) {
            for(int xPos = 0; xPos < this.level.getWidth(); ++xPos) {
                this.level.getTile(xPos, yPos).drawInto(g, x, y, width, height);
                x += width;
            }
            x = 0;
            y += height;
        }
    }
}
