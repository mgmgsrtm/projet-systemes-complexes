package projet2025;

public class Environment {

    int width;
    int height;
    Cell[][] grid;  

    int baseX;
    int baseY;

    public static final double RADIATION_FORBIDDEN = 3.0; // si 3+μSv/h drone ne peut pas entrer
    public static final double RADIATION_LIMITED  = 1.0; // si 1+μSv/h drone peut entrer mais traitement de caw avec signalement

    public Environment(int width, int height) {
        this.width = width;
        this.height = height;

        this.baseX = 0;
        this.baseY = 0;

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

    public boolean isBase(int x, int y) {
        return x == baseX && y == baseY;
    }


    //radiationLevel　
    //hasCow
    //hasDebris
    //emplacement initial
    public void initializeEnvironment() {
        for (int x = 0; x < width; x++) { // d'abord initialiser entierement en radiationLevel au dessous de 1 
            for (int y = 0; y < height; y++) {
                Cell c = grid[x][y];
                if (c.isBase) continue; 
                c.radiationLevel = 0.00; // 0.0 ～ 1.0
                c.hasDebris = Math.random() < 0.1; // cell a 10% de possibilite d'avoir debris
                c.hasCow = Math.random() < 0.05;  //　cell a 5% de possibilite d'avoir un caw
                c.cowHandled = false;
            }
        }
        int nbHotspot = 4 + (int)(Math.random()* 2);  // il y a 4 ou 5 hotspot par defaut

        for(int i = 0; i < nbHotspot; i ++ ){
            generateHotspot();
        }  
        setControlCentre();
    }


    
    public void generateHotspot(){
        int hotspotX, hotspotY;
            do {
                hotspotX = (int)(Math.random() * width);
                hotspotY = (int)(Math.random() * height);
            } while (Math.abs(hotspotX - baseX) <= 1 && Math.abs(hotspotY - baseY) <= 1);

            grid[hotspotX][hotspotY].radiationLevel = 4.5;

            for (int dx = -1; dx <= 1; dx++) { //3*3 autour de hotspot est radiationLevet 1~3 qui est niveau "entry limited"
                for (int dy = -1; dy <= 1; dy++) {
                    if (dx == 0 && dy == 0) continue;
                    setIfInside(hotspotX + dx, hotspotY + dy, setEntryLimitedZone());
                }
            }
    }



    private void setControlCentre(){
        Cell base = grid[baseX][baseY];
        base.isBase = true;
        base.radiationLevel = 0.0;
        base.hasCow = false;
        base.hasDebris = false;
        base.explored = true;
    }

    private void setIfInside(int x, int y, double value) {
        if (isInside(x, y)) {
            // choir le radiationLevel qui est plus haut
            grid[x][y].radiationLevel = Math.max(grid[x][y].radiationLevel, value);
        }
    }

    private double setEntryLimitedZone() {
        return 1.0 + Math.random() * 2.0; // 1.0 ～ 3.0
    }

    //l’évolution de l’environnement
    //propagation(la direction du vent), diminution(demi-vie radioactive)　etc.
    public void updateEnvironment() {
        double halfLife = 60.0; //demi-vie radioactive : temps nécessaire pour que le niveau de radiation soit divisé par deux 
        double decay = Math.pow(0.5, 1.0 / halfLife); //calcul du facteur de décroissance correspondant à une seconde

        //début de la boucle parcourant toute la grille
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Cell c = grid[x][y];
                if (c.isBase) continue; //la cellule de la base doit toujours avoir un niveau de radiation nul
                c.radiationLevel *= decay; //diminution du niveau de radiation pour une seconde
            }
        }
    }




    public void printEnvironment() {
	    for (int y = 0; y < height; y++) {
	        for (int x = 0; x < width; x++) {
	            System.out.println(grid[x][y] + " | ");
	        }
	        System.out.println();
	    }
    }
    
    public void printRadLevelMap() {
    	for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
            	System.out.print(String.format("%.2f", grid[x][y].radiationLevel) + " ");
            }
            System.out.println();
    	}
    	System.out.println();
    }
    
    public void printIfCowMap() {
    	String c;
    	for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
            	if (grid[x][y].hasCow) {
            		c = "C";
            	}else {
            		c = " ";
            	}
            	System.out.print(c + "|");
            }
            System.out.println();
    	}
    	System.out.println();
    }
    
    public void printMap() {
    	for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
            	Cell c = grid[x][y];
		    	if (c.isBase) {
		    	    System.out.print("B|");
		    	} else if (c.radiationLevel > RADIATION_FORBIDDEN) {
		    		if (c.hasCow) {
		    	        System.out.print("!|");
		    	    } else {
		    	        System.out.print("X|");
		    	    }
		    	} else if (c.radiationLevel > RADIATION_LIMITED) {
		    	    if (c.hasCow) {
		    	        System.out.print("c|"); // cow + limited
		    	    } else {
		    	        System.out.print("~|");
		    	    }
		    	} else if (c.hasCow) {
		    	    System.out.print("C|");   // safe cow
		    	} else {
		    	    System.out.print(" |");
		    	}
            }
            System.out.println();
    	}
    	System.out.println();
    }
    
    
    public void printDrone(Drone d) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                if (x == d.x && y == d.y) {
                    System.out.print("D|");   // Drone
                } else {
		    	    System.out.print(" |");
		    	}
            }
            System.out.println();
        }
        System.out.println();
    }
        


    public static void main(String[] args) {

        Environment env = new Environment(20, 20);
        env.initializeEnvironment();
        env.printEnvironment();
        env.printRadLevelMap();
        env.printIfCowMap();
        env.printMap();

        ControlCenter cc = new ControlCenter();
        Drone d1 = new Drone(1, env, cc);

        for (int t = 0; t < 300; t++) { // 300 秒
            d1.step();
            env.updateEnvironment();

            if (t % 10 == 0) {
                env.printRadLevelMap();
                env.printDrone(d1);
            }
            
            try {
                Thread.sleep(1000); // 1秒待つ（＝実時間で1秒）
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    
    
    
}
