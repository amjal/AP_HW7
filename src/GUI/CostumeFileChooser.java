package GUI;

import javax.swing.*;

/**
 * Created by Amir on 5/16/2017.
 */
public class CostumeFileChooser extends JFileChooser {
    //TODO font size
    JFrame container;
    public CostumeFileChooser(JFrame container){
        this.container = container;
    }
    @Override
    public void approveSelection(){
        container.dispose();
        Menu.processes.add(new ImageProcessingWindow(getSelectedFile()));
    }
    @Override
    public void cancelSelection(){
        container.dispose();
    }
}
