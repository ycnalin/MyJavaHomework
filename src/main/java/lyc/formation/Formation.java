package lyc.formation;
import lyc.life.Creature;
import lyc.life.Terrain;

public class Formation {
    private String name;
    private static final Creature soil = new Terrain(0,0,".");
    private Location location;
    private int width;
    private int height;
    protected Creature[][] contents;


    public Formation(int width, int height) {
        this.width = width;
        this.height = height;
        this.contents = new Creature[width][height];
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                this.contents[i][j] = soil;
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Creature[][] getContents() {
        return contents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                sb.append(contents[i][j] + " ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
