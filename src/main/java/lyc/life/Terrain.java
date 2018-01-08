package lyc.life;

public class Terrain extends Creature {     //二维场地：绿草或者寸草不生
    public Terrain(int power,int blood,String string) {
        super(power, blood, string);
    }

    @Override
    public String toString() {
        return getIdentity();
    }
}
