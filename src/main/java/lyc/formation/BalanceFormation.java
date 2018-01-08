package lyc.formation;

import lyc.life.Creature;

public class BalanceFormation extends Formation{
    public BalanceFormation(Creature []creature){
        super(2,7);
        this.setName("衡轭阵法");
        for(int i=0,cnt=1;i<getWidth();i++){
            for(int j=0;j<getHeight();j++){
                //this.contents[i][j] = grass;
                if(j%2== 1-i)
                    this.contents[i][j] = creature[cnt++];
            }
        }
        this.contents[1][0] = creature[0]; // formation leader
    }

    @Override
    public String toString() {
        return "BalanceFormation{}";
    }
}
