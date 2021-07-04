package game;

public abstract class MovingEntity extends Entity{
    public MovingEntity(Level level, int x, int y) {
        super(level, x, y);
    }

    protected void moveUp() {
        if(!this.isUpOutOfLevel()) {
            Tile targetTile = this.level.getTile(this.positionX, this.positionY-1);

            if(targetTile.hasEntity()) {
                Entity topEntity = targetTile.getTopEntity();
                this.crashesInto(topEntity);
            }
            else {
                this.level.getTile(this.positionX, this.positionY--).removeEntity(this);
                targetTile.putEntity(this);
            }
        }
    }

    protected void moveDown() {
        if(!this.isDownOutOfLevel()) {
            Tile targetTile = this.level.getTile(this.positionX, this.positionY+1);

            if(targetTile.hasEntity()) {
                Entity topEntity = targetTile.getTopEntity();
                this.crashesInto(topEntity);
            }
            else {
                this.level.getTile(this.positionX, this.positionY++).removeEntity(this);
                targetTile.putEntity(this);
            }
        }
    }

    protected void moveRight() {
        if(!this.isRightOutOfLevel()) {
            Tile targetTile = this.level.getTile(this.positionX+1, this.positionY);

            if(targetTile.hasEntity()) {
                Entity topEntity = targetTile.getTopEntity();
                this.crashesInto(topEntity);
            }
            else {
                this.level.getTile(this.positionX++, this.positionY).removeEntity(this);
                targetTile.putEntity(this);
            }
        }
    }

    protected void moveLeft() {
        if(!this.isLeftOutOfLevel()) {
            Tile targetTile = this.level.getTile(this.positionX-1, this.positionY);

            if(targetTile.hasEntity()) {
                Entity topEntity = targetTile.getTopEntity();
                this.crashesInto(topEntity);
            }
            else {
                this.level.getTile(this.positionX--, this.positionY).removeEntity(this);
                targetTile.putEntity(this);
            }
        }
    }

    protected boolean isUpOutOfLevel() {
        return this.positionY - 1 < 0;
    }

    protected boolean isDownOutOfLevel() {
        return this.positionY + 1 == this.level.getHeight();
    }

    protected boolean isRightOutOfLevel() {
        return this.positionX + 1 == this.level.getWidth();
    }

    protected boolean isLeftOutOfLevel() {
        return this.positionX - 1 < 0;
    }

    protected boolean canMoveUp() {
        boolean ret = false;

        if(!this.isUpOutOfLevel()) {
            Tile targetTile = this.level.getTile(this.positionX, this.positionY-1);
            ret = !targetTile.hasEntity();
        }
        return ret;
    }

    protected boolean canMoveDown() {
        boolean ret = false;

        if(!this.isDownOutOfLevel()) {
            Tile targetTile = this.level.getTile(this.positionX, this.positionY+1);
            ret = !targetTile.hasEntity();
        }
        return ret;
    }

    protected boolean canMoveRight() {
        boolean ret = false;

        if(!this.isRightOutOfLevel()) {
            Tile targetTile = this.level.getTile(this.positionX+1, this.positionY);
            ret = !targetTile.hasEntity();
        }
        return ret;
    }

    protected boolean canMoveLeft() {
        boolean ret = false;

        if(!this.isLeftOutOfLevel()) {
            Tile targetTile = this.level.getTile(this.positionX-1, this.positionY);
            ret = !targetTile.hasEntity();
        }
        return ret;
    }
}
