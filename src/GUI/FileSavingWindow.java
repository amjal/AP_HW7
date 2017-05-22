package GUI;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * Created by Amir on 5/20/2017.
 */
public class FileSavingWindow extends JFrame {
    public FileSavingWindow(BufferedImage image){
        super("Save File");
        System.out.println("ok");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500 , 700);
        add(new CostumeFileSaver(this , image));
        setVisible(true);
    }
}
