package GUI;

import javax.swing.*;

/**
 * Created by Amir on 5/20/2017.
 */
public class CostumeFileSaver extends JFileChooser {
    JFrame container;
    public CostumeFileSaver(JFrame container){
        setApproveButtonText("save");
        this.container = container;
    }
}
