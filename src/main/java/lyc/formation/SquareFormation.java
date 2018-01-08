package lyc.formation;

import lyc.life.Creature;

public class SquareFormation extends Formation {
    public SquareFormation(Creature []creature) {
        super(5, 5);
        this.setName("衡轭阵法");
        for (int i = 0,cnt = 1; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                //this.contents[i][j] = grass;
                if (i <= 2 && (j == 2 - i || j == 2 + i))
                    this.contents[i][j] = creature[1];
                else if (i > 2 && (j == 2 - i % 2 || j == 2 + i % 2))
                    this.contents[i][j] = creature[cnt++];
            }
        }
        this.contents[2][0] = creature[0]; // formation leader
    }

    @Override
    public String toString() {
        return "SquareFormation{}";
    }
}
