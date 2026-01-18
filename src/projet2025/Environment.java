package projet2025;

import java.util.ArrayList;
import java.util.List;

public class Environment {

    int width;
    int height;
    Cell[][] grid;

    List<Cow> cows;
    int cowIdCounter = 0;
    int currentTime = 0;   

    int baseX;
    int baseY;

    public static final double RADIATION_FORBIDDEN = 3.0; // si 3+μSv/h drone ne peut pas entrer
    public static final double RADIATION_LIMITED  = 1.0; // si 1+μSv/h drone peut entrer mais traitement de caw avec signalement

    int timeSinceLastHotspot;

    int timeSinceLastCowMove = 0;
    static final int COW_MOVE_INTERVAL = 40; // deplacement cow tous les 40 secondes
    
    public Environment(int width, int height) {
        this.width = width;
        this.height = height;

        this.baseX = 5;
        this.baseY = 5;

        this.cows = new ArrayList<>();
        
        this.timeSinceLastHotspot = 0;

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
    //emplacement initial
    public void initializeEnvironment() {
        for (int x = 0; x < width; x++) { // d'abord initialiser entierement en radiationLevel au dessous de 1 
            for (int y = 0; y < height; y++) {
                Cell c = grid[x][y];
                if (c.isBase) continue; 
                c.radiationLevel = 0.00; // 0.0 ～ 1.0
                // c.hasCow = Math.random() < 0.05;  //　cell a 5% de possibilite d'avoir un cow
                c.cowHandled = false;
            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Cell c = grid[x][y];
                if (c.isBase) continue;
                if (Math.random() < 0.05) {
                    cowIdCounter++;
                    Cow cow = new Cow(
                        cowIdCounter,
                        x,
                        y,
                        currentTime   
                    );
                    cows.add(cow);
                    c.hasCow = true; //pour affichage Environment
                }
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
        double halfLife = 180.0; //demi-vie radioactive : temps nécessaire pour que le niveau de radiation soit divisé par deux 
        double decay = Math.pow(0.5, 1.0 / halfLife); //calcul du facteur de décroissance correspondant à une seconde

        //début de la boucle parcourant toute la grille
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Cell c = grid[x][y];
                if (c.isBase) continue; //la cellule de la base doit toujours avoir un niveau de radiation nul
                c.radiationLevel *= decay; //diminution du niveau de radiation pour une seconde
            }
        }
        timeSinceLastHotspot++;

        // --- apparition nouveau hotspot possible ---
        if (timeSinceLastHotspot >= 60) { // apres 60 secondes
            if (Math.random() < 0.75) {    // 75% de chance
                generateHotspot();
                System.out.println("New hotspot!");
            }
            timeSinceLastHotspot = 0;
            // ちょうどいる cell に hotspot が出現したら？後で「緊急退避」を追加する
        }

        currentTime++;  // time for cow detection delay

        // --- deplacement de cow ---
        timeSinceLastCowMove++;

        if (timeSinceLastCowMove >= COW_MOVE_INTERVAL) {
            moveSomeCows(); 
            timeSinceLastCowMove = 0;
        }

    }


    private void moveSomeCows() {
        if (cows.isEmpty()) return;
        for (int i = 0; i < 2; i++) {
            Cow cow = cows.get(
                (int)(Math.random() * cows.size())
            );
            tryMoveCow(cow);
            System.out.println("moving cow id: " + cow );
        }
    }


    private void tryMoveCow(Cow cow) {
    	// combinaisons representent haut, bas, gauche, droit
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0, 1};
        int dir = (int)(Math.random() * 4);
        int nx = cow.x + dx[dir];
        int ny = cow.y + dy[dir];

        if (!isInside(nx, ny)) return;

        Cell target = grid[nx][ny];

        // cow deplace pas s'il y en a deja
        if (target.hasCow) return;

        // mettre a jour environment
        grid[cow.x][cow.y].hasCow = false;
        target.hasCow = true;

        // mettre a jour de cellule de l'objet cow
        cow.x = nx;
        cow.y = ny;
    }



    public Cow getCowAt(int x, int y) {
		for (Cow c : cows) {
			if (c.x == x && c.y == y) {
				return c;
			}
		}
		return null;
	}


    // Méthodes pour l'évaluation - comptage des cellules
    
    /**
     * Compte le nombre de cellules explorées dans la grille
     * @return nombre de cellules avec explored = true
     */
    public int countExploredCells() {
        int count = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (grid[x][y].explored) {
                    count++;
                }
            }
        }
        return count;
    }
    
    /**
     * Compte le nombre de cellules explorables (accessibles)
     * Une cellule est explorable si son niveau de radiation ≤ RADIATION_FORBIDDEN
     * @return nombre de cellules accessibles
     */
    public int countExplorableCells() {
        int count = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (grid[x][y].radiationLevel <= RADIATION_FORBIDDEN) {
                    count++;
                }
            }
        }
        return count-1; //sauf celulle base(controlcenter)
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
        System.out.println("=== ENVIRONNEMENT ACTUEL (Class Environment) ===");
    	for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
            	Cell c = grid[x][y];
            	
            	// Afficher uniquement la base, les vaches et les hotspots (pas de drones, pas de points)
		    	if (c.isBase) {
		    	    System.out.print("B|");
	            } else if (c.radiationLevel > RADIATION_FORBIDDEN) {
		    		if (c.hasCow) {
		    	        System.out.print("!|"); // vache en zone interdite
		    	    } else {
		    	        System.out.print("X|"); // hotspot interdit
		    	    }
		    	} else if (c.radiationLevel > RADIATION_LIMITED) {
		    	    if (c.hasCow) {
		    	        System.out.print("c|"); // vache en zone limitée
		    	    } else {
		    	        System.out.print("~|"); // zone à radiation limitée
		    	    }
		    	} else if (c.hasCow) {
		    	    System.out.print("C|"); // vache en zone sûre
		    	} else {
		    	    System.out.print(" |"); // cellule vide
		    	}
            }
            System.out.println();
    	}
    	System.out.println();
    }
      
}