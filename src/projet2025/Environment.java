package projet2025;

public class Environment {

    int width;
    int height;
    Cell[][] grid;  

    int baseX;
    int baseY;

    public static final double RADIATION_FORBIDDEN = 3.0; // μSv/h
    public static final double RADIATION_LIMITED  = 1.5; // μSv/h

    public Environment(int width, int height, int baseX, int baseY) {
        this.width = width;
        this.height = height;

        this.baseX = baseX;
        this.baseY = baseY;

        grid = new Cell[width][height];
    
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = new Cell(x, y);  //initialisation des cell
            }
        }
    }

    public boolean isInside(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Cell getCell(int x, int y){
        if(!isInside(x,y)){
            return null;
        }else{
            return grid[x][y];
        }
    }

    public void markExplored(int x, int y){
        Cell c = getCell(x, y);
        c.explored = true;
    }

    public boolean isForbidden(Cell c) {
        return c.radiationLevel > RADIATION_FORBIDDEN;
    }

    public boolean isLimited(Cell c) {
        return c.radiationLevel > RADIATION_LIMITED && c.radiationLevel <= RADIATION_FORBIDDEN;
    }


    //radiationLevel　
    //hasCow
    //hasDebris
    //emplacement initial
    public void initializeEnvironment() {
    for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
            Cell c = grid[x][y];

            c.radiationLevel = Math.random() * 5.0;  //0〜5マイクロシーベルト
            c.hasDebris = Math.random() < 0.1; //10% de possibilite true
            c.hasCow = Math.random() < 0.05;  //5% de possibilite true
            c.cowHandled = false;
        }
    }
}

    //l’évolution de l’environnement
    //propagation(la direction du vent), diminution(demi-vie radioactive)　etc.
    public void updateEnvironment() {}


    public void printEnvironment() {
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            System.out.print(grid[x][y] + " | ");
        }
        System.out.println();
    }
}

    public static void main(String[] args) {

        Environment env = new Environment(20, 20, 1, 1);
        env.initializeEnvironment();
        env.printEnvironment();
    }
    
}
