import lyc.game.Recorder;
import lyc.game.Server;

public class Main {
    public static void main(String[] args) {

        God god = new God();
        god.createLife();
        Server server = new Server();
        server.setRecorder(new Recorder("User_Records.txt"));
        server.setInitState(god.randomPickFormation());
        server.handOn();

    }
}
