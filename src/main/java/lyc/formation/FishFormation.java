package lyc.formation;

import lyc.life.Creature;

import java.lang.Math;

public class FishFormation extends Formation {
    public FishFormation(Creature []creature) {
        super(7, 5);
        this.setName("鱼鳞阵法");
        for (int i = 0,cnt = 1; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                //this.contents[j][i] = grass;
                if ((j - i) == 3 || (j + i) == 3 || (Math.abs(j - 3) + Math.abs(i - 3) ==1))
                    if(!(i==1 && j == 2))
                        this.contents[j][i] = creature[cnt++];
            }
        }
        this.contents[3][0] = creature[0]; // formation leader
    }

    @Override
    public String toString() {
        return "FishFormation{}";
    }
}
