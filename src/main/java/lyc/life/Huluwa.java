package lyc.life;

public class Huluwa extends Creature {
    private Color color;
    private Name name;

    public Huluwa(Color color,Name name){
        super(70,100,"Huluwa");
        this.name = name;
        this.color = color;
    }

    public String sayColor(){
        return color.name();
    }

    public String sayName(){
        return name.name();
    }

    public Color getColor() {
        return color;
    }

    public Name getName() {
        return name;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setName(Name nickname) {
        this.name = nickname;
    }

    public int getOrder(){       //return nickname order
        return name.ordinal();
    }

    public int getCorder(){     //return color order
        return color.ordinal();
    }

    @Override
    public String toString(){
        return this.color.toString();
    }
}

enum Color {
    r, o, y, g, c, b, v;
};

enum Name {
    老大, 老二, 老三, 老四, 老五, 老六, 老七;
}
