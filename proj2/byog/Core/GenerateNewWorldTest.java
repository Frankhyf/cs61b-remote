package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import org.junit.Test;

public class GenerateNewWorldTest {
    public static void main (String[] args) {
        GenerateNewWorld G = new GenerateNewWorld(4444);
        TETile[][] worldState = G.GenerateAll();
        TERenderer ter = new TERenderer();
        ter.initialize(Game.WIDTH,Game.HEIGHT);
        ter.renderFrame(worldState);
    }

}
