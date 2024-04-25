package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

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
        int startIndex = input.indexOf('n') + 1; // 找到 'n' 后的位置作为起始索引
        int endIndex = startIndex; // 初始化结束索引

        // 从 startIndex 开始，找到第一个非数字字符的位置，这就是结束索引
        while (endIndex < input.length() && Character.isDigit(input.charAt(endIndex))) {
            endIndex++;
        }

        // 截取从 startIndex 到 endIndex 的子字符串，这部分字符串就是种子
        String seedString = input.substring(startIndex, endIndex);

        // 将字符串种子转换为长整型
        long seed = Long.parseLong(seedString);
        //使用种子生成世界
        GenerateNewWorld G = new GenerateNewWorld(seed);
        TETile[][] finalWorldFrame = G.GenerateAll();
        return finalWorldFrame;
    }
}
