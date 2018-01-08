import lyc.game.Recorder;
import lyc.game.Server;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        God god = new God();
        god.createLife();
        Server server = new Server();
        server.setRecorder(new Recorder("temp_Records"));
        server.setInitState(god.randomPickFormation());
        server.handOn();

    }
}
