package projet2025;

public class Environment {

    int width;
    int height;
    Cell[][] grid;  // マップの各セル

    public Environment(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new Cell[width][height];
    }
    
}
