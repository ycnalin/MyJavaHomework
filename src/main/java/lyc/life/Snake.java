package lyc.life;

public class Snake extends Creature {
    private String name;
    private static final int order = 10;
    private static Creature []armies;

    public Snake(String name){
        super(420,450,"Monster");
        this.name = name;
    }

    public void prepare(){
        armies = new Creature[20];  //多到可以排出任意阵型
        armies[0] = new Scorpion("Scorpion");
        for(int i=1;i<armies.length;i++)
            armies[i] = new EvilSpirit("xiao lou lou");
    }

    public Creature[] getArmies() {
        return armies;
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

    @Override
    public String toString() {
        return "s";
    }
}
