package lyc.game;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

public class FileChooser{
    public static void main(String[] args) {
        File file = new FileChooser().getFile(System.getProperty("user.dir"));
        System.out.println(file.getAbsoluteFile() + file.getName());
    }

    public File getFile(String path){
        JFileChooser jfc=new JFileChooser(path);
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY );
        jfc.showDialog(new JLabel(), "选择");
        File file=jfc.getSelectedFile();
        return file;
    }

}
