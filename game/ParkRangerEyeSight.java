package game;

public class ParkRangerEyeSight extends  MovingEntity{
    public ParkRangerEyeSight(Level level, int x, int y) {
        super(level,x,y);
        this.setImage("/sprites/ParkRangerEyeSight.png");
    }

    @Override
    public void crashesInto(Entity entity) {
        if(entity instanceof YogiBear) {
            ((YogiBear) entity).takeDamage();
        }
    }

    @Override
    protected void moveUp() {
        if(!this.isUpOutOfLevel()) {
            Tile targetTile = this.level.getTile(this.positionX, this.positionY-1);

            if(targetTile.hasEntity()) {
                Entity topEntity = targetTile.getTopEntity();

                this.crashesInto(topEntity);
            }

            targetTile.putEntity(this);
            this.level.getTile(this.positionX, this.positionY).removeEntity(this);
            --this.positionY;
        }
    }

    @Override
    protected void moveDown() {
        if(!this.isDownOutOfLevel()) {
            Tile targetTile = this.level.getTile(this.positionX, this.positionY+1);

            if(targetTile.hasEntity()) {
                Entity topEntity = targetTile.getTopEntity();

                this.crashesInto(topEntity);
            }

            targetTile.putEntity(this);
            this.level.getTile(this.positionX, this.positionY).removeEntity(this);
            ++this.positionY;
        }
    }

    @Override
    protected void moveRight() {
        if(!this.isRightOutOfLevel()) {
            Tile targetTile = this.level.getTile(this.positionX+1, this.positionY);

            if(targetTile.hasEntity()) {
                Entity topEntity = targetTile.getTopEntity();

                this.crashesInto(topEntity);
            }

            targetTile.putEntity(this);
            this.level.getTile(this.positionX, this.positionY).removeEntity(this);
            ++this.positionX;
        }
    }

    @Override
    protected void moveLeft() {
        if(!this.isLeftOutOfLevel()) {
            Tile targetTile = this.level.getTile(this.positionX-1, this.positionY);

            if(targetTile.hasEntity()) {
                Entity topEntity = targetTile.getTopEntity();

                this.crashesInto(topEntity);
            }

            targetTile.putEntity(this);
            this.level.getTile(this.positionX, this.positionY).removeEntity(this);
            --this.positionX;
        }
    }

    @Override
    protected boolean canMoveUp() {
        Tile tile = this.level.getTile(this.positionX, this.positionY);
        return !this.isUpOutOfLevel() && tile.getEntityCount() == 1;
    }

    @Override
    protected boolean canMoveDown() {
        Tile tile = this.level.getTile(this.positionX, this.positionY);
        return !this.isDownOutOfLevel() && tile.getEntityCount() == 1;
    }

    @Override
    protected boolean canMoveRight() {
        Tile tile = this.level.getTile(this.positionX, this.positionY);
        return !this.isRightOutOfLevel() && tile.getEntityCount() == 1;
    }

    @Override
    protected boolean canMoveLeft() {
        Tile tile = this.level.getTile(this.positionX, this.positionY);
        return !this.isLeftOutOfLevel() && tile.getEntityCount() == 1;
    }

}
