package lyc.game;

import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.Random;
import javax.swing.ImageIcon;

public class Player extends Thing2D implements Runnable {
    private Server server;
    private final char type;
    private boolean isAlive = true;
    private boolean safeExit = false;

    //private Field field;

    public Player(int x, int y, char type, Server server) {
        super(x, y);
        this.server = server;
        this.type = type;
        String path = "" + type + ".png";
        URL loc = this.getClass().getClassLoader().getResource(path);
        if (loc == null) {
            throw new NullPointerException("image" + path + ".png" + "not found\n");
        }
        this.setImage(new ImageIcon(loc).getImage());
    }

    private int RequestMove(Player player,int nx, int ny){
        return server.processRequest(player, nx, ny);
    }

    public char getType() {
        return type;
    }

    public void move(int x, int y){
        this.setX(x);
        this.setY(y);
    }

    public void killPlayer(){
        this.isAlive = false;
        String path = "player.png";
        URL loc = this.getClass().getClassLoader().getResource(path);
        if (loc == null) {
            System.out.println("image" + path + ".png" + "not found\n");
            System.exit(-1);
        }
        this.setImage(new ImageIcon(loc).getImage());
    }

    public boolean isAlive(){
        return isAlive;
    }

    public void safeExit(){
        this.safeExit = true;
    }

    public void run() {
        Random rand = new Random();
        try {
            while (!Thread.interrupted() && isAlive && !safeExit) {
                server.waitForMoving();
                int nx = rand.nextInt(4);
                int ny = rand.nextInt(4);
                RequestMove(this, nx, ny);
                Thread.yield();
                TimeUnit.MILLISECONDS.sleep(rand.nextInt(500) + 200);
            }
        }
        catch (InterruptedException e){
            System.err.println("Exiting via interrupt");
        }
        if(!isAlive) {
            server.updateDeaths(this);
            System.err.println("player " + this.type + " dead");
        }
    }
}