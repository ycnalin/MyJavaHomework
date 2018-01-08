package lyc.formation;

import lyc.life.Creature;

public class GooseFormation extends Formation {
    public GooseFormation(Creature []creature) {
        super(5, 5);
        this.setName("雁行阵法");
        int cnt = 1;
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                //this.contents[i][j] = Terrain;
                if (i + j == 4)
                    this.contents[i][j] = creature[cnt++];
            }
        }
        this.contents[4][0] = creature[0]; // formation leader
    }

    @Override
    public String toString() {
        return "GooseFormation{}";
    }
}
