package game;

public class YogiBear extends MovingEntity{
    public YogiBear(Level level, int x, int y) {
        super(level,x,y);
        this.setImage("/sprites/YogiBear(right).png");
    }

    public void takeDamage() {
        ParkGate spawnGate = this.level.getSpawnGate();
        int x = spawnGate.getPositionX();
        int y = spawnGate.getPositionY();
        Tile spawnTile = this.level.getTile(x,y);

        this.level.getManager().decreaseLives();
        this.level.getTile(this.positionX, this.positionY).removeEntity(this);
        this.positionX = x;
        this.positionY = y;
        spawnTile.removeEntity(spawnGate);
        spawnTile.putEntity(this);
        spawnTile.putEntity(spawnGate);
    }

    private void pickUpPicnicBasket(Entity entity) {
        Tile targetTile = this.level.getTile(entity.getPositionX(), entity.getPositionY());

        this.level.getTile(this.positionX, this.positionY).removeEntity(this);
        this.positionX = entity.getPositionX();
        this.positionY = entity.getPositionY();
        this.level.decreaseBasketCount();
        targetTile.putEntity(this);
        this.level.deleteEntity(entity);
    }

    @Override
    public void update() {
        char inputtedKey = this.level.getManager().getInputtedKey();

        switch (inputtedKey) {
            case 'w':
                this.moveUp();
                break;

            case 's':
                this.moveDown();
                break;

            case 'a':
                this.moveLeft();
                break;

            case 'd':
                this.moveRight();
                break;

            case '':
                level.getManager().pauseGame();
                break;
        }

    }

    @Override
    public void crashesInto(Entity entity) {
        if(entity instanceof ParkRanger) {
            takeDamage();
        }
        else if(entity instanceof ParkRangerEyeSight) {
            Tile tile = this.level.getTile(entity.getPositionX(), entity.getPositionY());

            if(tile.getEntityCount() == 1) {
                this.takeDamage();
            }
        }
        else if(entity instanceof ParkGate) {
            int x = entity.getPositionX();
            int y = entity.getPositionY();
            Tile tile = this.level.getTile(x,y);

            this.level.getTile(this.positionX, this.positionY).removeEntity(this);
            this.positionX = x;
            this.positionY = y;
            tile.removeEntity(entity);
            tile.putEntity(this);
            tile.putEntity(entity);
        }
        else if (entity instanceof PicnicBasket) {
            this.pickUpPicnicBasket(entity);
        }
    }

    @Override
    public void moveLeft() {
        super.moveLeft();
        this.setImage("/sprites/YogiBear(left).png");
    }

    @Override
    public void moveRight() {
        super.moveRight();
        this.setImage("/sprites/YogiBear(right).png");
    }
}
