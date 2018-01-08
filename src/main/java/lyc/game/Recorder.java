package lyc.game;


import java.io.File;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;

public class Recorder {
    private final static String lasRecord = "Collector_Edition.txt";
    private String userRecord;
    private BufferedReader in;
    private BufferedWriter out;


    public Recorder(String fileName){
        userRecord = fileName;

    }
    public Recorder(){
        userRecord = lasRecord;
    }

    public void setUserRecord(String userRecord) {
        if(userRecord == null){
            System.out.println("文件名不能为空");
        }
        else
            this.userRecord = userRecord;
    }

    public void writeFrame(String frame){
        try {
             out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(userRecord, true)));
            out.write(frame);
            out.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(out != null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void readInit(){
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(userRecord)));
        } catch (Exception e) {
            //System.out.println("first frame");
            e.printStackTrace();
        }
    }

    public String readNextFrame(){
        StringBuilder sb = new StringBuilder();
        String oneline;
        try{
            while((oneline = in.readLine()) != null && !oneline.equals("")) {
                sb.append(oneline);
                sb.append("\n");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    public void readFinish(){
        try {
            if(in != null){
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean clearFile(){
        File file = new File(userRecord);
        return file.delete();
    }

    public static void main(String[] args){
        Recorder record = new Recorder();
        String frame = "this is first line\nthis is next line\n";
        String frame1 = "this is 1st line\nthis is 2th line\n";
        record.writeFrame(frame);
        record.writeFrame(frame1);
        System.out.println("writeFrame done!");
        record.setUserRecord(null);
        record.readInit();
        System.out.println("first frame");
        System.out.print(record.readNextFrame());
        System.out.println("next frame");
        System.out.print(record.readNextFrame());
        record.readFinish();
    }
}
