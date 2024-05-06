package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.security.Key;

public class playwithkeyboardTest {

    public static void main(String[] args) {
        StdDraw.setCanvasSize(40 * 16, 40 * 16);
        StdDraw.setXscale(0, 40);
        StdDraw.setYscale(0, 40);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(20, 32, "CS61B: THE GAME");
        StdDraw.text(20, 21, "NEW GAME (N)");
        StdDraw.text(20, 19, "LOAD GAME (L)");
        StdDraw.text(20, 17, "QUIT (Q)");
        StdDraw.show();

        /*
        * 下面制作New Game逻辑
        * */
        while (!StdDraw.hasNextKeyTyped()) {  // 确保等待直到有按键输入
            StdDraw.pause(100);
        }
        char key = StdDraw.nextKeyTyped();
        if (key == 'N') {
            StdDraw.clear(Color.BLACK);
            StdDraw.text(20, 20, "Enter Seed and press S to start:");
            StdDraw.show();

            String seed = "";
            while (true) {
                if (!StdDraw.hasNextKeyTyped()) {
                    StdDraw.pause(100);
                    continue;
                }
                char ch = StdDraw.nextKeyTyped();
                if (ch == 'S') {
                    break;
                }
                seed += ch;
                // 显示当前输入的种子，反馈给用户
                StdDraw.clear(Color.BLACK);
                StdDraw.text(20, 20, "Enter Seed: " + seed);
                StdDraw.text(20, 18, "Press S to start");
                StdDraw.show();
            }
            GenerateNewWorld G = new GenerateNewWorld(Integer.parseInt(seed));
            TETile[][] world = G.GenerateAll();
            Entity e = new Entity(Tileset.PLAYER, world);
            e.GenerateEntity();
            Game.play(e,world);

        } else if (key == 'L') {
            GameSaver.GameSave gamestate = GameSaver.load("game_save.ser");
            TETile[][] world = gamestate.world;
            Entity e = new Entity(world, gamestate.playerPosX, gamestate.playerPosY);
            Game.play(e,world);
        } else {
            System.exit(0);
        }
    }
}
