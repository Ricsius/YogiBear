package game;

import java.awt.*;
import java.util.ArrayList;

public class Tile {
    private ArrayList<Entity> entities;

    public Tile() {
        this.entities = new ArrayList<>();
    }

    public int getEntityCount() {
        return this.entities.size();
    }

    public Entity getTopEntity() {
        Entity ret = null;

        if(this.hasEntity()) {
            ret = this.entities.get(this.entities.size()-1);
        }

        return ret;
    }

    public void putEntity(Entity entity) {
        this.entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        this.entities.remove(entity);
    }

    public boolean hasEntity() {
        return this.entities.size() > 0;
    }

    public void drawInto(Graphics graphics, int x, int y, int width, int height) {
        for(Entity entity : this.entities) {
            Image image = entity.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
            graphics.drawImage(image, x, y, null);
        }

        Graphics2D g2d = (Graphics2D) graphics;
        float thickness = 3;

        Stroke oldStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(thickness));
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x,y,width,height);
        g2d.setStroke(oldStroke);
    }
}
