package lyc.formation;

import lyc.life.Creature;

public class ArrowFormation extends Formation {
    public ArrowFormation(Creature []creature) {
        super(5, 6);
        this.setName("锋矢阵法");
        for (int i = 0,cnt=1; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                //this.contents[j][i] = grass;
                if ((j - i) == 2 || (j + i) == 2 || j==2)
                    this.contents[j][i] = creature[cnt++];
            }
        }
        this.contents[2][0] = creature[0]; // formation leader
    }

    @Override
    public String toString() {
        return "ArrowFormation{}";
    }
}
