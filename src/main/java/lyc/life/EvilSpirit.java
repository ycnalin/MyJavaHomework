package lyc.life;

public class EvilSpirit extends Creature {  //xiao lou lou
    private String name;
    private static final int order = 20;
    public EvilSpirit(String name){
        super(20,50,"Monster");
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public static int getOrder() {
        return order;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void attack(Creature creature){
        //implement a attack
    }

    @Override
    public String toString() {
        return "l";
    }
}
