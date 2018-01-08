package lyc.formation;

import lyc.life.Creature;

public class SnakeFormation extends Formation {
    public SnakeFormation(Creature []creature) {
        super(7, 1);
        this.setName("长蛇阵法");
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                this.contents[i][j] = creature[i];
            }
        }
    }

    @Override
    public String toString() {
        return "SnakeFormation{}";
    }
}
