package projet2025;

public class GlobalCellInfo {
	
	public boolean explored;
    public double radiationLevel;
    public boolean hasCow;

	public GlobalCellInfo() {
		this.explored = false;
        this.radiationLevel = 0.0;
        this.hasCow = false;
	}

    public GlobalCellInfo(GlobalCellInfo other) {
        this.explored = other.explored;
        this.radiationLevel = other.radiationLevel;
        this.hasCow = other.hasCow;
    }

}
