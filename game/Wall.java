package game;

public class Wall extends Entity{
    public Wall(Level level, int x, int y) {
        super(level,x,y);
        this.setImage("/sprites/Wall.png");
    }
}
