package projet2025;

public class Cell {

    int x;
    int y;
    boolean explored;
    
    double radiationLevel;     // Niveau de radiation

    boolean hasCow;       // Présence de bovins
    boolean cowHandled;;     // Bovins déjà pris en charge
   
    boolean hasDebris;       //Présence de débris (pour l’interface utilisateur, pour la prise de décision au centre)
    
    
    public Cell(int x, int y){
        this.x = x;
        this.y = y;

        this.explored = false;

        this.radiationLevel = 0.0;

        this.hasCow = false;
    
        this.cowHandled = false;

        this.hasDebris = false;

    }


}
