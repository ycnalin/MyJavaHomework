package lyc.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;

/*
 * 战场
 * 功能：1、初始化；2、绘制战场
 */
public class Field extends JPanel {

    private final static int OFFSET = 30;
    private final static int SPACE = 20;
    private Server server;


    private ArrayList<Tile> tiles = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();

    private int w = 0; //unit: piexl
    private int h = 0; //unit: piexl
    private final static int gridWidth = 16;
    private final static int gridHeight = 10;

    private boolean completed = false;

    private StringBuilder level = new StringBuilder();

    public Field(Server server) {
        this.server = server;
        addKeyListener(new TAdapter());
        setFocusable(true);

        int len = gridHeight*gridWidth;
        for(int i = 0; i<len; ++i){
            if(i%gridWidth == 0 && i != 0)
                level.append("\n");
            level.append(".");
        }
        //System.out.println(level);
        initWorld();
    }

    public void setAndPaint(StringBuilder frame) {
        this.level = frame;
        restartLevel();
        this.repaint();
    }

    public void killAll(){
        for(Player ex:players){
            ex.safeExit();
        }
    }

    public StringBuilder getFrame() {
        return this.level;
    }

    public int getBoardWidth() {
        return this.w;
    }

    public int getBoardHeight() {
        return this.h;
    }

    public static int getGridWidth() {
        return gridWidth;
    }

    public static int getGridHeight() {
        return gridHeight;
    }

    public static int getOFFSET() {
        return OFFSET;
    }

    public static int getSPACE() {
        return SPACE;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public final void initWorld() {

        int x = OFFSET;
        int y = OFFSET;

        for (int i = 0; i < level.length(); i++) {
            char item = level.charAt(i);
            if (item == '\n') {
                y += SPACE;
                if (this.w < x) {
                    this.w = x;
                }
                x = OFFSET;
            } else if (item == '.') {
                Tile tile = new Tile(x, y);
                tiles.add(tile);
                x += SPACE;
            } else {
                tiles.add(new Tile(x, y));
                players.add(new Player(x, y, item, server));
                x += SPACE;
            }
            h = y;
        }

        for(Player player: players)
            server.addPlayer(player);
    }

    public void buildWorld(Graphics g) {

        g.setColor(new Color(250, 240, 170));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        ArrayList<Thing2D> world = new ArrayList<>();
        world.addAll(tiles);
        world.addAll(players);

        for (int i = 0; i < world.size(); i++) {

            Thing2D item = world.get(i);

            if (item instanceof Player) {
                g.drawImage(item.getImage(), item.x() + 2, item.y() + 2, this);
            } else {
                g.drawImage(item.getImage(), item.x(), item.y(), this);
            }
             if (completed) {
                g.setColor(new Color(0, 0, 0));
                g.drawString("Completed", 25, 20);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        buildWorld(g);
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            if (completed) {
                return;
            }

            int key = e.getKeyCode();


            if (key == KeyEvent.VK_LEFT) {

                //player.move(-SPACE, 0);

            } else if (key == KeyEvent.VK_RIGHT) {

                //player.move(SPACE, 0);

            } else if (key == KeyEvent.VK_UP) {

                //player.move(0, -SPACE);

            } else if (key == KeyEvent.VK_DOWN) {


                //player.move(0, SPACE);

            } else if (key == KeyEvent.VK_SPACE) {
                if(server.getServerState() != 2) {
                    server.start(false);
                    for (Player player : players) {
                        new Thread(player).start();
                    }
                }

            } else if (key == KeyEvent.VK_R) {

                restartLevel();
            }
            else if(key == KeyEvent.VK_L){
                if(server.getServerState() != 2) {
                    if(server.OpenRecord(System.getProperty("user.dir")))
                        server.start(true);
                }
            }
            repaint();
        }
    }


    public void restartLevel() {
        tiles.clear();
        players.clear();
        initWorld();
        if (completed) {
            completed = false;
        }
    }
}