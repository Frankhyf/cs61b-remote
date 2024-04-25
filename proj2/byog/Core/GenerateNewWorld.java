package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/*
* generate a world that josh want
*
*
* */
public class GenerateNewWorld {
    private static final int WIDTH = Game.WIDTH;
    private static final int HEIGHT = Game.HEIGHT;
    private static int minRoomWidth = 3;
    private static int maxRoomWidth = 7;
    private static int minRoomHeight = 3;
    private static int maxRoomHeight = 7;
    private long SEED;
    private Random random;
    private int numRooms; // 随机生成的房间数为10-15


    public TETile[][] world ;
    ArrayList<Room> rooms = new ArrayList<>();// 用于存储房间的数据结构

    public GenerateNewWorld(long SEED) {
        this.SEED = SEED;
        this.random = new Random(SEED);
        numRooms= random.nextInt(5)+20;
        world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }
    public void Initializer() {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    public class Room {
        int x;
        int y;
        int width;
        int height;
        public Room(int x,int y,int width,int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }
    public void GenerateRoom() {
        int createdRooms = 0;
        while (createdRooms < numRooms) {
            int width = random.nextInt(maxRoomWidth - minRoomWidth + 1) + minRoomWidth;
            int height = random.nextInt(maxRoomHeight - minRoomHeight + 1) + minRoomHeight;
            int x = random.nextInt(WIDTH - width);  // 确保房间不会超出地图边界
            int y = random.nextInt(HEIGHT - height);

            Room room = new Room(x, y, width, height);
            if (!roomsOverlap(room, rooms)) {
                rooms.add(room);
                createdRooms++;
            }
        }
    }

    private boolean roomsOverlap(Room newRoom, ArrayList<Room> rooms) {
        for (Room room : rooms) {
            if (newRoom.x + newRoom.width + 1 >= room.x &&
                    newRoom.x - 1 <= room.x + room.width &&
                    newRoom.y + newRoom.height + 1 >= room.y &&
                    newRoom.y - 1 <= room.y + room.height) {
                return true;  // 有重叠
            }
        }
        return false;
    }
    public void placeRoomOnMap(Room room, TETile[][] world) {
        for (int x = 0; x < room.width; x++) {
            for (int y = 0; y < room.height; y++) {
                world[room.x + x][room.y + y] = Tileset.FLOOR;
            }
        }
    }
    public void drawAllRooms() {
        for (Room room : rooms) {
            placeRoomOnMap(room,world);
        }
    }
    public class Corridor {
        Room room1;
        Room room2;
        double length;

        public Corridor(Room room1, Room room2) {
            this.room1 = room1;
            this.room2 = room2;
            this.length = Math.sqrt(Math.pow(room1.x - room2.x, 2) + Math.pow(room1.y - room2.y, 2));
        }
    }
    //generation of all corridors
    private ArrayList<Corridor> generateAllPossibleCorridors() {
        ArrayList<Corridor> corridors = new ArrayList<>();
        for (int i = 0; i < rooms.size(); i++) {
            for (int j = i + 1; j < rooms.size(); j++) {
                corridors.add(new Corridor(rooms.get(i), rooms.get(j)));
            }
        }
        return corridors;
    }
    //using kruskal to find the MST
    public void GenerateAndDrawCorridorsWithKruskal() {
        ArrayList<Corridor> allCorridors = generateAllPossibleCorridors();
        Collections.sort(allCorridors, Comparator.comparingDouble(c -> c.length));

        UnionFind unionFind = new UnionFind(rooms.size());
        ArrayList<Corridor> mst = new ArrayList<>(); // 最小生成树的走廊

        for (Corridor corridor : allCorridors) {
            int room1Index = rooms.indexOf(corridor.room1);
            int room2Index = rooms.indexOf(corridor.room2);
            if (unionFind.find(room1Index) != unionFind.find(room2Index)) {
                unionFind.union(room1Index, room2Index);
                mst.add(corridor);
                if (mst.size() == rooms.size() - 1) break;
            }
        }

        // 可选：使用 mst 集合中的走廊在地图上画出走廊
        for (Corridor corridor : mst) {
            drawCorridor(corridor, world);
        }
    }
    public class UnionFind {
        private int[] parent;
        private int[] rank;

        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;  // 每个节点最初指向自己，是自己的根
                rank[i] = 0;    // 初始秩为0
            }
        }

        public int find(int p) {
            while (p != parent[p]) {
                parent[p] = parent[parent[p]];  // 路径压缩
                p = parent[p];
            }
            return p;
        }

        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP != rootQ) {
                if (rank[rootP] > rank[rootQ]) {
                    parent[rootQ] = rootP;
                } else if (rank[rootP] < rank[rootQ]) {
                    parent[rootP] = rootQ;
                } else {
                    parent[rootQ] = rootP;
                    rank[rootP]++;
                }
            }
        }
    }
    public void drawCorridor(Corridor corridor, TETile[][] world) {
        Point p1 = chooseRandomPointOnRoom(corridor.room1, random);
        Point p2 = chooseRandomPointOnRoom(corridor.room2, random);

        // 先水平后垂直或者先垂直后水平
        drawLine(world, p1, new Point(p2.x, p1.y), Tileset.FLOOR); // 水平部分
        drawLine(world, new Point(p2.x, p1.y), p2, Tileset.FLOOR); // 垂直部分
    }
    private Point chooseRandomPointOnRoom(Room room, Random random) {
        int x, y;
        switch(random.nextInt(4)) { // 随机选择四条边之一
            case 0: // Top edge
                x = random.nextInt(room.width) + room.x;
                y = room.y + room.height - 1;
                break;
            case 1: // Bottom edge
                x = random.nextInt(room.width) + room.x;
                y = room.y;
                break;
            case 2: // Left edge
                x = room.x;
                y = random.nextInt(room.height) + room.y;
                break;
            default: // Right edge
                x = room.x + room.width - 1;
                y = random.nextInt(room.height) + room.y;
                break;
        }
        return new Point(x, y);
    }
    private void drawLine(TETile[][] world, Point start, Point end, TETile tile) {
        int dx = Integer.compare(end.x, start.x);
        int dy = Integer.compare(end.y, start.y);
        int x = start.x;
        int y = start.y;
        world[x][y] = tile;
        while(x != end.x || y != end.y) {
            if(x != end.x) {
                x += dx;
            }
            if(y != end.y) {
                y += dy;
            }
            world[x][y] = tile;
        }
    }
    public class Point {
        int x, y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public void GenerateAllBlocks() {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                if (world[x][y].equals(Tileset.FLOOR)) {
                    GenerateBlocks(x,y);
                };
            }
        }
    }
    private void GenerateBlocks(int x,int y) {
        for (int i = x - 1;i <= x + 1;i++) {
            for (int j = y - 1;j <= y + 1;j++) {
                if (i >=0 && j >= 0 && i < WIDTH && j < HEIGHT && world[i][j].equals(Tileset.NOTHING)) {
                    world[i][j] = Tileset.WALL;
                }
            }
        }
    }

    public TETile[][]  GenerateAll() {
        GenerateRoom();
        drawAllRooms();
        GenerateAndDrawCorridorsWithKruskal();
        GenerateAllBlocks();
        return world;
    }
}
