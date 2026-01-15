package projet2025; 

public class Cow {
    int id;
    int x, y;
    int generatedTime;
    boolean detected;

    public Cow(int id, int x, int y, int time) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.generatedTime = time;
        this.detected = false;
    }
    
    @Override
    public String toString() {
        return "Cow{id=" + id +
               ", x=" + x +
               ", y=" + y +
               ", generatedTime=" + generatedTime +
               ", detected=" + detected +
               "}";
    }
}