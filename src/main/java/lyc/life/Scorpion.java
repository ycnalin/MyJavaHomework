package lyc.life;

public class Scorpion extends Creature {
    private String name;
    private static final int order = 15;

    public Scorpion(String name){
        super(70,100,"Monster");
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

        return "x";
    }
}
