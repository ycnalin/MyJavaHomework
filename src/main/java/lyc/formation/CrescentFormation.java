package lyc.formation;

import lyc.life.Creature;

public class CrescentFormation extends Formation {
    public CrescentFormation(Creature []creature) {
        super(6, 9);
        this.setName("鹤翼阵法");
        for (int i = 0,cnt = 1; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                //this.contents[j][i] = grass;
                if ((i - j) == 3 || (j + i) == 5 || (i > 0 && j + i == 4) || (i< 8 && i - j == 4))
                    this.contents[j][i] = creature[cnt++];
            }
        }
        this.contents[2][4] = creature[1];
        this.contents[5][0] = creature[0]; // formation leader
    }

    @Override
    public String toString() {
        return "CrescentFormation{}";
    }
}
