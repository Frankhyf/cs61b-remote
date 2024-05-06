package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;
import byog.TileEngine.Tileset;
import jdk.nashorn.internal.ir.ContinueNode;

import java.awt.*;
import java.util.Random;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;

    public static void showDetails(TETile[][] world) {
        //显示字幕
        StdDraw.setPenColor(Color.BLACK);  // 假设背景是黑色
        StdDraw.filledRectangle(Game.WIDTH / 2, Game.HEIGHT - 1, Game.WIDTH / 2, 1);  // 假设字幕高度为1

        // 根据鼠标位置更新字幕
        StdDraw.setPenColor(Color.white);
        String pointerInfo = world[(int) StdDraw.mouseX()][(int) StdDraw.mouseY()+3].description();
        StdDraw.text(Game.WIDTH / 2, Game.HEIGHT - 1, pointerInfo);
        StdDraw.show();

    }
    public static void play(Entity e,TETile[][] world) {
        TERenderer ter = new TERenderer();
        ter.initialize(Game.WIDTH,Game.HEIGHT);
        ter.renderFrame(world);

        boolean expectingQuit = false;  // 这是一个标志，用来标记是否正在等待 'Q' 按键
        while (true) {
            showDetails(world);
            if (!StdDraw.hasNextKeyTyped()) {
                StdDraw.pause(100);
                continue;
            }
            char action = StdDraw.nextKeyTyped();

            // 如果正在等待 'Q' 并且按键为 'Q'
            if (expectingQuit && action == 'Q') {
                GameSaver.GameSave saveState = new GameSaver.GameSave(world, e.posx, e.posy);
                GameSaver.save(saveState, "game_save.ser");
                System.exit(0);
            }

            switch (action) {
                case 'W':
                    e.turnup();
                    break;
                case 'S':
                    e.turndown();
                    break;
                case 'A':
                    e.turnleft();
                    break;
                case 'D':
                    e.turnright();
                    break;
                case ':':
                    expectingQuit = true;  // 设置标志，等待 'Q'
                    StdDraw.pause(200);  // 给用户一点时间来按下 'Q'
                    break;
                default:
                    expectingQuit = false;  // 如果按下任何其他键，重置标志
                    break;
            }
            ter.renderFrame(world);  // 更新画面
        }
    }
    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */


    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        TETile[][] world = null;
        Entity e = null;
        int seed = 0;
        StringBuilder seedBuilder = new StringBuilder();
        boolean readingSeed = false;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == 'N' || c == 'n') {
                readingSeed = true;
            } else if (c == 'S' || c == 's') {
                seed = Integer.parseInt(seedBuilder.toString());
                GenerateNewWorld G = new GenerateNewWorld(seed);
                world = G.GenerateAll();
                e = new Entity(Tileset.PLAYER, world);
                e.GenerateEntity();
                readingSeed = false;
            } else if (c == 'Q' || c == 'q') {
                System.exit(0);
            } else if (readingSeed) {
                seedBuilder.append(c);
            } else {
                switch (c) {
                    case 'W':
                    case 'w':
                        e.turnup();
                        break;
                    case 'S':
                    case 's':
                        e.turndown();
                        break;
                    case 'A':
                    case 'a':
                        e.turnleft();
                        break;
                    case 'D':
                    case 'd':
                        e.turnright();
                        break;
                    case ':':
                        if (i + 1 < input.length() && input.charAt(i + 1) == 'Q') {
                            GameSaver.GameSave saveState = new GameSaver.GameSave(world, e.posx, e.posy);
                            GameSaver.save(saveState, "game_save.ser");
                            System.exit(0);
                        }
                        break;
                }
            }
        }

        return world;
    }
    public void playWithKeyboard() {
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
