package byog.Core;
import java.io.Serializable;
import byog.TileEngine.TETile;
import java.io.*;

public class GameSaver {

    public static class GameSave implements Serializable {
        private static final long serialVersionUID = 1L;

        public TETile[][] world;
        public int playerPosX;
        public int playerPosY;

        public GameSave(TETile[][] world, int playerPosX, int playerPosY) {
            this.world = world;
            this.playerPosX = playerPosX;
            this.playerPosY = playerPosY;
        }
    }

    public static void save(GameSaver.GameSave gameState, String fileName) {
        String directoryPath = "C:\\Users\\Frank\\Desktop\\cs61b\\skeleton-sp18\\proj2";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs(); // 如果目录不存在，创建目录
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(directory, fileName)))) {
            out.writeObject(gameState);
        } catch (IOException e) {
            System.err.println("Error saving game state: " + e);
        }
    }


    public static GameSave load(String fileName) {
        String filePath = "C:\\Users\\Frank\\Desktop\\cs61b\\skeleton-sp18\\proj2\\" + fileName;
        GameSave gameState = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            gameState = (GameSave) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading game state: " + e);
        }
        return gameState;
    }

}