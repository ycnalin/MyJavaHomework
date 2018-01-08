package lyc.life;

public class Grandpa extends Creature {
    private static final int order = -1;    // highest order
    private String name;

    public Grandpa(String name){
        super(10,50,"Human");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getOrder() {
        return order;
    }

    @Override
    public String toString(){
        return "h";
    }
}
