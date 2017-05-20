package GUI;

import javax.swing.*;

/**
 * Created by Amir on 5/20/2017.
 */
public class FileSavingWindow extends JFrame {
    public FileSavingWindow(){
        super("Save File");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500 , 700);
        add(new CostumeFileSaver(this));
        setVisible(true);
    }
}
