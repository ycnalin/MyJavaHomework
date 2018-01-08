package lyc.formation;

import lyc.life.Creature;

public class CraneFormation extends Formation {
    public CraneFormation(Creature []creature){
        super(7,4);
        this.setName("鹤翼阵法");
        for(int i=0,cnt = 1;i<getHeight();i++){
            for(int j=0;j<getWidth();j++){
                //this.contents[j][i] = grass;
                if(j == 3+i || j== 3-i)
                    this.contents[j][i] = creature[cnt++];
            }
        }
        this.contents[3][0] = creature[0];  // formation leader
    }

    @Override
    public String toString() {
        return "CraneFormation{}";
    }
}
