package lyc.formation;

import lyc.life.Creature;
import lyc.life.Terrain;

import java.util.ArrayList;
import java.util.List;

public class Ground {
    private int width;
    private int height;
    private static final Creature terrain = new Terrain(0, 0, ".");
    private List armies;
    private Creature [][]battlefield;

    public Ground(int width, int height) {
        this.width = width;
        this.height = height;
        this.armies = new ArrayList();
        battlefield = new Creature[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                battlefield[i][j] = terrain;
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void layout(Formation f, Location loc) throws TooCrowdedException {
        if (loc.getX() + f.getWidth() > width || loc.getY() + f.getHeight() > height)
            throw new TooCrowdedException("out of bounds");
        Location location = f.getLocation();
        f.setLocation(loc);
        for (Object o : armies) {
            if (conflict((Formation) o, f)) {
                f.setLocation(location);
                throw new TooCrowdedException("Too Crowded");
            }
        }
        armies.add(f);
    }

    private boolean conflict(Formation f1, Formation f2) {
        return contain(f1, f2) || contain(f2, f1) || overlap(f1, f2) || overlap(f2, f1);
    }

    private boolean overlap(Formation f1, Formation f2) {
        if ((f1.getLocation().getX() + f1.getWidth() >= f2.getLocation().getX()) &&
                (f1.getLocation().getY() + f1.getHeight() >= f2.getLocation().getY()) &&
                (f1.getLocation().getX() <= f2.getLocation().getX()) &&
                (f1.getLocation().getY() <= f2.getLocation().getY()))
            return true;
        return false;
    }

    private boolean contain(Formation f1, Formation f2) {
        if ((f1.getLocation().getX() <= f2.getLocation().getX() &&
                (f1.getLocation().getX() + f1.getWidth() >= f2.getLocation().getX() + f2.getWidth()) &&
                (f1.getLocation().getY() <= f2.getLocation().getY()) &&
                (f1.getLocation().getY() + f1.getHeight() >= f2.getLocation().getY() + f2.getHeight())))
            return true;
        return false;
    }

    public void setPlace(Creature creature, Location loc) {

        this.battlefield[loc.getX()][loc.getY()] = creature;
    }

    @Override
    public String toString() {
        for (Object obj : armies) {
            Formation f = (Formation) obj;
            for (int i = 0; i < f.getWidth(); i++) {
                for (int j = 0; j < f.getHeight(); j++) {
                    battlefield[f.getLocation().getX() + i][f.getLocation().getY() + j] = f.getContents()[i][j];
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < width; j++) {
            for (int i = 0; i < height; i++) {
                sb.append(battlefield[j][i]);
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
