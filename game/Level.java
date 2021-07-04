package game;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Level {
    private final String ParkGateId = "G";
    private final String ParkRangerId = "R";
    private final String PicnicBasketId = "P";
    private final String WallId = "W";
    private GameManager manager;
    private int width;
    private int height;
    private ArrayList<String> ids;
    private ArrayList<Tile> tiles;
    private ArrayList<Entity> entities;
    private ArrayList<Entity> garbageEntities;
    private ParkGate spawnGate;
    private int basketCount;

    public Level(GameManager manager, BufferedReader reader) throws IOException {
        Scanner lineScanner = new Scanner(reader.readLine());
        Scanner fileScanner = new Scanner(reader.lines().collect(Collectors.joining("\n")));
        this.ids = new ArrayList<>();
        this.tiles = new ArrayList<>();
        this.entities = new ArrayList<>();
        this.garbageEntities = new ArrayList<>();
        this.manager = manager;
        this.width = 0;
        this.height = 1;
        this.basketCount = 0;

        while(lineScanner.hasNext()) {
            String id = lineScanner.next();
            this.tiles.add(new Tile());
            this.ids.add(id);
            ++this.width;
        }

        ++this.height;

        int i = -1;
        while(fileScanner.hasNext()) {
            String id = fileScanner.next();

            ++i;

            this.tiles.add(new Tile());
            this.ids.add(id);

            if(i + 1 == this.width && fileScanner.hasNext()){
                ++this.height;
                i = -1;
            }
        }

        this.createEntities();
    }

    public GameManager getManager() {
        return this.manager;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Tile getTile(int x, int y) {
        return this.tiles.get((y * this.width) + x);
    }

    public ParkGate getSpawnGate() {
        return this.spawnGate;
    }

    public void decreaseBasketCount() {
        --this.basketCount;

        if(this.basketCount == 0) {
            this.manager.loadNextLevel();
        }
    }

    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }

    public void deleteEntity(Entity entity) {
        Tile tile = this.getTile(entity.getPositionX(), entity.getPositionY());
        tile.removeEntity(entity);
        this.garbageEntities.add(entity);
    }

    public void update() {
        for(Entity entity : this.entities) {
            entity.update();
        }

        for(Entity garbage : this.garbageEntities) {
            this.entities.remove(garbage);
        }

        this.garbageEntities.clear();
    }

    private void createEntities() {
        for(int y = 0; y < this.height; ++y) {
            for(int x = 0; x < this.width; ++x) {
                Entity entity = null;
                int ind = (y * this.width) + x;
                String id = this.ids.get(ind);

                if(id.equals(ParkGateId)) {
                    entity = new ParkGate(this, x, y);
                    YogiBear yogiBear = new YogiBear(this, x, y);

                    this.tiles.get(ind).putEntity(yogiBear);
                    this.entities.add(yogiBear);
                    this.spawnGate = (ParkGate) entity;
                }
                else if(id.equals(ParkRangerId)) {
                    entity = new ParkRanger(this, x, y);
                }
                else if(id.equals(PicnicBasketId)) {
                    entity = new PicnicBasket(this, x, y);
                    ++this.basketCount;
                }
                else if(id.equals(WallId)) {
                    entity = new Wall(this, x, y);
                }

                if(entity != null) {
                    this.tiles.get(ind).putEntity(entity);
                    this.entities.add(entity);
                }
            }
        }
    }
}
