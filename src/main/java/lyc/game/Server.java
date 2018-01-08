package lyc.game;

import javax.security.auth.login.FailedLoginException;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.lang.Math;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Server extends JFrame {
    private final int OFFSET = 30;
    private StringBuilder initState;
    private Field field;
    private final byte[] lock = new byte[0];
    private boolean movable = true;
    private Recorder recorder;
    private boolean recordable = true;
    private final static int defaultrate = 10;
    private int huluSurvivors;
    private int snakeSurvivors;
    private int state = 0; //0 start, 1:running ,2:complete,3:replay
    Set<Character> hulu;
    {
        hulu = new HashSet<>();
        hulu.add('r');
        hulu.add('g');
        hulu.add('b');
        hulu.add('o');
        hulu.add('y');
        hulu.add('c');
        hulu.add('v');
        hulu.add('h');//爷爷
    }

    public Server() {
        field = new Field(this);
        add(field);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(field.getBoardWidth() + OFFSET + 10,
                field.getBoardHeight() + 3 * OFFSET);
        setLocationRelativeTo(null);
        setTitle("Displayer");
        setVisible(true);
    }

    /*
     * 加载葫芦娃大战画面中的一帧
     */
    public void loadField(StringBuilder frame) {
        if (!frame.toString().equals(field.getFrame().toString())) {  //比较字符串内容而不是指针
            field.setAndPaint(frame);
            recordAnimation(frame.toString());
        }
        else
            field.repaint();
    }

    public void waitForMoving() throws InterruptedException{
        synchronized (lock){
            while(!movable) {
                lock.wait();
            }
            movable = false;
        }
    }

    /*
     * 处理玩家请求
     */
    public int processRequest(Player player, int nx, int ny) {
        if(!player.isAlive()) return 0;
        synchronized (lock) {
            MoveAndAttack(player, nx, ny);
        }
        field.repaint();
        synchronized (lock){
            recordAnimation(field.getFrame().toString());
            movable = true;
            lock.notifyAll();
            return 1;
        }
    }

    /*
     * 执行移动和攻击
     */
    private int MoveAndAttack(Player player, int nx, int ny){
        if(nx == 0 && ny == 0) {
            attack(player);
            return 1;
        }

        int lx = (player.x() - Field.getOFFSET())/Field.getSPACE() + 1;//逻辑位置
        int ly = (player.y() - Field.getOFFSET())/Field.getSPACE() + 1;//逻辑位置

        StringBuilder level = field.getFrame();
        if(hulu.contains(player.getType())){
            int i, j = 0, loc;
            for(i = nx; i>= -nx; --i) {
                for (j = ny; j >= -ny; --j) {
                    loc = tranformLoc(lx + i, ly + j);
                    if( loc== -1) continue;
                    if (level.charAt(loc) == 's' || level.charAt(loc) == 'x' || level.charAt(loc) == 'l')
                    break;
                }
                if(j != -ny - 1) break;
            }
            if(i != -nx - 1){
                int lo;
                if((lo = tranformLoc(lx + Math.max(-nx,i-1), ly + j)) != -1) {
                    if(level.charAt(lo) == '.')
                        setXY(player,lx + Math.max(-nx,i-1),ly + j);
                }
                else if( ( lo = tranformLoc(lx + i, ly + Math.max(-ny, j-1))) != -1){
                    if(level.charAt(lo) == '.')
                        setXY(player,lx + i,ly + Math.max(-ny, j-1));
                }
                else if((lo = tranformLoc(lx + Math.max(-nx,i - 2), ly + j)) != -1) {
                    if(level.charAt(lo) == '.')
                        setXY(player,lx + Math.max(-nx,i - 2),ly + j);
                }
                else if((lo = tranformLoc(lx + i, ly + Math.min(ny, j+1))) != -1){
                    if(level.charAt(lo) == '.')
                        setXY(player,lx + i,ly + Math.min(ny, j+1));
                }
                else if((lo = tranformLoc(lx + Math.min(nx,i + 1), ly + j)) != -1) {
                    if(level.charAt(lo) == '.')
                        setXY(player,lx + Math.min(nx,i + 1),ly + j);
                }
                attack(player);
                return 1;
            }
        }
        else{
            int i, j = 0, loc;
            for(i = -nx; i <= nx; ++i) {
                for (j = ny; j >= -ny; --j) {
                    loc = tranformLoc(lx + i, ly + j);
                    if( loc== -1) continue;
                    if (hulu.contains(level.charAt(loc)))
                        break;
                }
                if(j != -ny - 1) break;
            }
            if(i != nx + 1){
                int lo;
                if((lo = tranformLoc(lx + Math.min(nx,i+1), ly + j)) != -1) {
                    if(level.charAt(lo) == '.')
                        setXY(player,lx + Math.min(nx,i+1),ly + j);
                }
                else if((lo = tranformLoc(lx + i, ly + Math.max(-ny, j-1))) != -1){
                    if(level.charAt(lo) == '.')
                        setXY(player,lx + i,ly + Math.max(-ny, j-1));
                }
                else if((lo = tranformLoc(lx + Math.min(nx,i + 2), ly + j)) != -1) {
                    if(level.charAt(lo) == '.')
                        setXY(player,lx + Math.min(nx,i + 2),ly + j);
                }
                else if((lo = tranformLoc(lx + i, ly + Math.min(ny, j+1))) != -1){
                    if(level.charAt(lo) == '.')
                        setXY(player,lx + i,ly + Math.min(ny, j+1));
                }
                else if((lo = tranformLoc(lx + Math.max(-nx,i - 1), ly + j)) != -1) {
                    if(level.charAt(lo) == '.')
                        setXY(player,lx + Math.max(-nx,i - 1),ly + j);
                }
                attack(player);
                return 1;
            }
        }

        Random rand = new Random();
        if(nx != 0)
            lx = lx + rand.nextInt(nx * 2) - nx;
        if(ny != 0)
            ly = ly + rand.nextInt(ny * 2) - ny;
        int lo = tranformLoc(lx,ly);
        if(lo != -1 && level.charAt(lo) == '.') {
            setXY(player, lx, ly);
            attack(player);
        }
        return 1;
    }

    /*
     * 执行攻击，把绘制新画面
     */
    private int attack(Player player){
        double prob = 0.5;
        int s = 2, i, j;
        StringBuilder level = field.getFrame();
        boolean isHulu = hulu.contains(player.getType());
        int lx = (player.x() - Field.getOFFSET())/Field.getSPACE() + 1;//逻辑位置
        int ly = (player.y() - Field.getOFFSET())/Field.getSPACE() + 1;//逻辑位置
        for(i = -s; i < s; ++i) {
            for (j = s; j > -s; --j) {
                int loc = tranformLoc(lx + i, ly + j);
                if( loc== -1) continue;
                if (level.charAt(loc) != '.'&&level.charAt(loc) != 'd' && (hulu.contains(level.charAt(loc)) != isHulu)) {
                    if(isHulu) prob = 0.1;
                    else prob = 0.5;
                    if(Math.random() > prob){
                        ArrayList<Player> players = field.getPlayers();
                        int xpixel = (lx + i - 1)*Field.getSPACE() + Field.getOFFSET();
                        int ypixel = (ly + j - 1)*Field.getSPACE() + Field.getOFFSET();
                        for(Player ex: players){
                            if(ex.x() == xpixel && ex.y() == ypixel){
                                if(ex.isAlive()) {
                                    ex.killPlayer();
                                    int llx = (ex.x() - Field.getOFFSET())/Field.getSPACE() + 1;//逻辑位置
                                    int lly = (ex.y() - Field.getOFFSET())/Field.getSPACE() + 1;//逻辑位置
                                    int oldLoc = tranformLoc(llx,lly);
                                    level.replace(oldLoc, oldLoc + 1, "d");
                                    break;
                                }
                            }
                        }
                        return 1;
                    }
                }
            }
        }
        //System.out.println("attack nothing");
        return 0;
    }

    /*
     * 坐标转换
     */
    private int tranformLoc(int lx, int ly){
        int gw = Field.getGridWidth();
        int gh = Field.getGridHeight();
        if(lx < 1 || ly < 1 || lx >gw || ly > gh)
            return -1; //边界
        int res = (ly - 1) * (gw + 1) + lx - 1;
        if(res > field.getFrame().length() || res < 0)
            System.out.println("lx: "+lx+" ly: "+ly+" res: "+ res);
        return res;
    }

    private void setXY(Player player,int lx, int ly){
        if(!player.isAlive()) return;
        StringBuilder level = field.getFrame();
        //删除之前的位置
        int llx = (player.x() - Field.getOFFSET())/Field.getSPACE() + 1;//逻辑位置
        int lly = (player.y() - Field.getOFFSET())/Field.getSPACE() + 1;//逻辑位置
        int oldLoc = tranformLoc(llx,lly);
        level.replace(oldLoc, oldLoc + 1, ".");

        int loc = tranformLoc(lx,ly);
        level.replace(loc, loc + 1, ""+player.getType());
        player.move((lx - 1)*Field.getSPACE() + Field.getOFFSET(),
                (ly-1)*Field.getSPACE() + Field.getOFFSET());
        //System.out.println(""+player.getType()+" move to: " + lx + ", " + ly );
    }

    /*
     * 开始运行
     */
    public void start(boolean replay) {
        if(!replay) {
            recorder.clearFile();
            //切换到运行态
            this.state = 2;
            this.recordable = true;
            this.loadField(initState);
            System.out.println("开始运行");
        }
        else {
            this.recordable = false;
            this.state = 3;
            System.out.println("开始回放");
        }
    }

    public void updateDeaths(Player player){
        if(hulu.contains(player.getType()))
            --huluSurvivors;
        else --snakeSurvivors;
        if(huluSurvivors == 0 || snakeSurvivors == 0){
            String result = "葫芦娃战队获得本场比赛的胜利！";
            if(huluSurvivors ==0) result = "蛇精战队获得本场比赛的胜利";
            System.out.println("battle over!");
            System.out.println(result);
            System.out.println("战场剩余人数："+ (huluSurvivors > 0?huluSurvivors:snakeSurvivors));
            //杀死其他幸存者;
            field.killAll();
            this.state = 0;
        }
    }

    public void addPlayer(Player player){
        if(hulu.contains(player.getType()))
            ++huluSurvivors;
        else ++snakeSurvivors;
    }

    public void handOn(){
        //System.out.println("Enter hang on: "+state);
        System.out.println("请按空格开始游戏， 按L键选择");
        System.out.println("或者按L键选择选择Collector_Edition，回放精彩记录");
        System.out.println("或者按L键选择选择User_Records，回放上次记录");
        while(true) {
            while (state != 2 && state != 3) {
                try {
                    TimeUnit.MILLISECONDS.sleep(50);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (state == 3) {
                replayMode(null);
                state = 0;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setInitState(StringBuilder initState) {
        this.initState = initState;
    }


    public void setRecorder(Recorder recorder){
        this.recorder = recorder;
        this.recordable = true;
    }

    public int getServerState() {
        return state;
    }

    public boolean OpenRecord(String path){
        return recorder.selectRecord(path);
    }

    public void clearRecords(){
        recorder.clearFile();
    }

    public void recordAnimation(String frame){
        if(recordable){
            recorder.writeFrame(frame);
        }
    }

    public void replayMode(String file){
        this.state = 2;
        if(file != null) recorder.setUserRecord(file);
        this.recordable = false;
        recorder.readInit();
        int gap = 1000/defaultrate;
        String frame;
        while(!(frame = recorder.readNextFrame()).equals("")){
            try {
                this.loadField(new StringBuilder(frame));
                TimeUnit.MILLISECONDS.sleep(gap);
            }catch (InterruptedException e){
                e.printStackTrace();
                System.exit(-1);
            }
        }
        recorder.readFinish();
        System.out.println("replay over!!!");
    }

    public static void main(String[] args) {

    }

}
