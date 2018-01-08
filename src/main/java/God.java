import lyc.formation.*;
import lyc.life.*;

import java.util.Random;


public class God {
    private Grandpa papa;
    private HuluBros huluwa;
    private Snake snake;
    private Scorpion scorpion;

    //上帝创造万物
    public void createLife(){
        papa = new Grandpa("Grandpa");
        huluwa = new HuluBros();
        snake = new Snake("Snake");
        scorpion = new Scorpion("Scorpion");
    }

    //获取一个随机的战场初始状态
    public StringBuilder randomPickFormation(){
        //按照年龄大小排成一列
        huluwa.prepare();
        //蛇精召集蝎子精和小喽啰准备战斗
        snake.prepare();
        // 蛇精的阵型初始化
        Formation[] formations = {
                new SnakeFormation(snake.getArmies()),
                new CraneFormation(snake.getArmies()),
                new ArrowFormation(snake.getArmies()),
                new BalanceFormation(snake.getArmies()),
                new FishFormation(snake.getArmies()),
                new GooseFormation(snake.getArmies()),
                new SquareFormation(snake.getArmies()),
                new CrescentFormation(snake.getArmies()),
                new SnakeFormation(huluwa.getHulubros())
        };
        Random rand = new Random();
        StringBuilder frame = new StringBuilder();
        try {
            //随机挑选一个阵型
            Formation formation = formations[rand.nextInt(8)];
            Ground battlefield = new Ground(10, 16);
            int w = battlefield.getWidth(),h =battlefield.getHeight();
            battlefield.layout(formations[8], new Location((w - formations[8].getWidth())/2, 0));
            battlefield.layout(formation, new Location((w - formation.getWidth())/2, h-formation.getHeight()));
            battlefield.setPlace(papa, new Location((w + formations[8].getWidth())/2, 0));   //放置爷爷
            battlefield.setPlace(snake, new Location((w + formation.getWidth())/2, h-formation.getHeight()));  //放置蛇精
            frame.append(""+battlefield);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("God is not omnipotent, it fails you occasional");
            System.exit(-1);
        }
       return frame;
    }
}
