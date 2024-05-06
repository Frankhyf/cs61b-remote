package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
public class Entity {
    public int posx;
    public int posy;

    public TETile[][] world;
    public TETile e;

    public  Entity(TETile e, TETile[][] world) {
        this.e = e;
        this.world = world;
    }
    public  Entity(TETile[][] world,int x,int y) {
        this.e = world[x][y];
        this.world = world;
        posx = x;
        posy = y;
    }
    public void GenerateEntity() {
        for (int x = 0; x < Game.WIDTH; x++) {
            for (int y = 0; y < Game.HEIGHT; y++) {
                if (world[x][y].equals(Tileset.FLOOR)){
                    world[x][y] = e;
                    this.posx = x;
                    this.posy = y;
                    return;
                }
            }
        }
    }

    public void turnright() {
        if (!world[posx+1][posy].equals(Tileset.FLOOR)){
            return;
        }
        world[posx][posy] = Tileset.FLOOR;
        posx += 1;
        world[posx][posy] = e;
    }
    public void turnleft() {
        if (!world[posx-1][posy].equals(Tileset.FLOOR)){
            return;
        }
        world[posx][posy] = Tileset.FLOOR;
        posx -= 1;
        world[posx][posy] = e;
    }
    public void turndown() {
        if (!world[posx][posy-1].equals(Tileset.FLOOR)){
            return;
        }
        world[posx][posy] = Tileset.FLOOR;
        posy -= 1;
        world[posx][posy] = e;
    }
    public void turnup() {
        if (!world[posx][posy+1].equals(Tileset.FLOOR)){
            return;
        }
        world[posx][posy] = Tileset.FLOOR;
        posy += 1;
        world[posx][posy] = e;
    }
}
