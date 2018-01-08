
import static org.junit.Assert.*;

import lyc.game.Recorder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.File;
import java.io.IOException;

public class RecorderTest{
    public static String filename = "测试文件1.txt";
    public static String frame = "this is first line\nthis is next line\n";
    public static String frame1 = "this is 1st line\nthis is 2th line\n";

    @BeforeClass
    public static void setupBeforeClass(){

        File file = new File(filename);
        if(file.exists()) {
            file.delete();
            file = new File(filename);
        }
        try{
            java.io.PrintWriter out = new java.io.PrintWriter(file);
            out.println(frame);
            out.println(frame1);
            out.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void writeFrame() throws Exception {
        Recorder recorder = new Recorder("测试文件2.txt");
        recorder.clearFile();
        recorder.writeFrame(frame);
        recorder.writeFrame(frame1);
        recorder.readInit();
        assertEquals("写文件有问题",frame,recorder.readNextFrame());
        assertEquals("连续写文件有问题",frame1,recorder.readNextFrame());
        recorder.readFinish();
    }


    @Test
    public void readNextFrame() throws Exception {
        Recorder recorder = new Recorder(filename);
        recorder.readInit();
        assertEquals("读文件有问题", frame,recorder.readNextFrame());
        assertEquals("连续读文件有问题", frame1,recorder.readNextFrame());
    }
}