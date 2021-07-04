package game;

public class ParkRanger extends MovingEntity{
    private ParkRangerEyeSight eyeSight;
    private State state;
    private int maxMoveDelay;
    private int currentMoveDelay;

    public ParkRanger(Level level, int x, int y) {
        super(level, x,y);
        this.setImage("/sprites/ParkRanger(left).png");
        this.eyeSight = new ParkRangerEyeSight(level, x, y);
        this.state = new LeftPatrollingState();
        this.maxMoveDelay = 300;
        this.currentMoveDelay = this.maxMoveDelay;
        this.level.addEntity(this.eyeSight);
    }

    @Override
    public void update() {
        this.currentMoveDelay -= this.level.getManager().getDelay();

        if(this.currentMoveDelay <= 0){
            this.currentMoveDelay = this.maxMoveDelay;
            this.state.update();
        }
    }

    private Tile lookLeft() {
        Tile ret = level.getTile(positionX-1, positionY);
        Entity topEntity = ret.getTopEntity();

        if(ret.hasEntity() && topEntity instanceof YogiBear) {
            eyeSight.crashesInto(topEntity);
        }

        return ret;
    }

    private Tile lookRight() {
        Tile ret = level.getTile(positionX+1, positionY);
        Entity topEntity = ret.getTopEntity();

        if(ret.hasEntity() && topEntity instanceof YogiBear) {
            eyeSight.crashesInto(topEntity);
        }

        return ret;
    }

    private Tile lookUp() {
        Tile ret = level.getTile(positionX, positionY-1);
        Entity topEntity = ret.getTopEntity();

        if(ret.hasEntity() && topEntity instanceof YogiBear) {
            eyeSight.crashesInto(topEntity);
        }

        return ret;
    }

    private Tile lookDown() {
        Tile ret = level.getTile(positionX, positionY+1);
        Entity topEntity = ret.getTopEntity();

        if(ret.hasEntity() && topEntity instanceof YogiBear) {
            eyeSight.crashesInto(topEntity);
        }

        return ret;
    }

    private interface State {
        public void update();
    }

    private class LeftPatrollingState implements State {
        private LeftPatrollingState() {
            int oldX = eyeSight.getPositionX();
            int oldY = eyeSight.getPositionY();
            int x = positionX-1;
            int y = positionY;
            boolean validOldCoordinates = (oldX >= 0 && oldX < level.getWidth()) && (oldY >= 0 && oldY < level.getHeight());
            boolean validCoordinates = (x >= 0 && x < level.getWidth()) && (y >= 0 && y < level.getHeight());

            setImage("/sprites/ParkRanger(left).png");

            if(validOldCoordinates) {
                level.getTile(oldX, oldY).removeEntity(eyeSight);
            }

            if(validCoordinates) {
                eyeSight.setPositionX(x);
                eyeSight.setPositionY(y);
                Tile tile = level.getTile(x,y);

                if(tile.hasEntity()) {
                    eyeSight.crashesInto(tile.getTopEntity());
                }

                tile.putEntity(eyeSight);
            }
        }

        @Override
        public void update() {
            if(eyeSight.canMoveLeft()) {
                eyeSight.moveLeft();
                moveLeft();
            }
            else {
                if(!isRightOutOfLevel()){
                    Tile tile = lookRight();

                    if(tile.hasEntity()) {
                        state = new DownPatrollingState();
                    }
                    else {
                        state = new RightPatrollingState();
                    }
                }
                else {
                    state = new DownPatrollingState();
                }
            }
        }
    }

    private class RightPatrollingState implements State {
        private RightPatrollingState() {
            int oldX = eyeSight.getPositionX();
            int oldY = eyeSight.getPositionY();
            int x = positionX+1;
            int y = positionY;
            boolean validOldCoordinates = (oldX >= 0 && oldX < level.getWidth()) && (oldY >= 0 && oldY < level.getHeight());
            boolean validCoordinates = (x >= 0 && x < level.getWidth()) && (y >= 0 && y < level.getHeight());

            setImage("/sprites/ParkRanger(right).png");

            if(validOldCoordinates) {
                level.getTile(oldX, oldY).removeEntity(eyeSight);
            }

            if(validCoordinates) {
                eyeSight.setPositionX(x);
                eyeSight.setPositionY(y);
                Tile tile = level.getTile(x,y);

                if(tile.hasEntity()) {
                    eyeSight.crashesInto(tile.getTopEntity());
                }

                tile.putEntity(eyeSight);
            }
        }

        @Override
        public void update() {
            if(eyeSight.canMoveRight()) {
                eyeSight.moveRight();
                moveRight();
            }
            else {
                if(!isLeftOutOfLevel()){
                    Tile tile = lookLeft();

                    if(tile.hasEntity()) {
                        state = new DownPatrollingState();
                    }
                    else {
                        state = new LeftPatrollingState();
                    }
                }
                else {
                    state = new DownPatrollingState();
                }
            }
        }
    }

    private class UpPatrollingState implements State {
        private UpPatrollingState() {
            int oldX = eyeSight.getPositionX();
            int oldY = eyeSight.getPositionY();
            int x = positionX;
            int y = positionY-1;
            boolean validOldCoordinates = (oldX >= 0 && oldX < level.getWidth()) && (oldY >= 0 && oldY < level.getHeight());
            boolean validCoordinates = (x >= 0 && x < level.getWidth()) && (y >= 0 && y < level.getHeight());

            if(validOldCoordinates) {
                level.getTile(oldX, oldY).removeEntity(eyeSight);
            }

            if(validCoordinates) {
                eyeSight.setPositionX(x);
                eyeSight.setPositionY(y);
                Tile tile = level.getTile(x,y);

                if(tile.hasEntity()) {
                    eyeSight.crashesInto(tile.getTopEntity());
                }

                tile.putEntity(eyeSight);
            }
        }

        @Override
        public void update() {
            if(eyeSight.canMoveUp()) {
                eyeSight.moveUp();
                moveUp();
            }
            else {
                if(!isDownOutOfLevel()){
                    Tile tile = lookDown();

                    if(tile.hasEntity()) {
                        state = new LeftPatrollingState();
                    }
                    else {
                        state = new DownPatrollingState();
                    }
                }
                else {
                    state = new LeftPatrollingState();
                }
            }
        }
    }

    private class DownPatrollingState implements State {
        private DownPatrollingState() {
            int oldX = eyeSight.getPositionX();
            int oldY = eyeSight.getPositionY();
            int x = positionX;
            int y = positionY+1;
            boolean validOldCoordinates = (oldX >= 0 && oldX < level.getWidth()) && (oldY >= 0 && oldY < level.getHeight());
            boolean validCoordinates = (x >= 0 && x < level.getWidth()) && (y >= 0 && y < level.getHeight());

            if(validOldCoordinates) {
                level.getTile(oldX, oldY).removeEntity(eyeSight);
            }

            if(validCoordinates) {
                eyeSight.setPositionX(x);
                eyeSight.setPositionY(y);
                Tile tile = level.getTile(x,y);

                if(tile.hasEntity()) {
                    eyeSight.crashesInto(tile.getTopEntity());
                }

                tile.putEntity(eyeSight);
            }
        }

        @Override
        public void update() {
            if(eyeSight.canMoveDown()) {
                eyeSight.moveDown();
                moveDown();
            }
            else {
                if(!isUpOutOfLevel()){
                    Tile tile = lookUp();

                    if(tile.hasEntity()) {
                        state = new LeftPatrollingState();
                    }
                    else {
                        state = new UpPatrollingState();
                    }
                }
                else {
                    state = new LeftPatrollingState();
                }
            }
        }
    }
}
