package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Amir on 5/20/2017.
 */
public class CostumeFileSaver extends JFileChooser {
    JFrame container;
    BufferedImage image;
    public CostumeFileSaver(JFrame container , BufferedImage image){
        super();
        setApproveButtonText("save");
        this.image = image;
        if (image == null) System.out.println("null");
        this.container = container;
    }
    @Override
    public void approveSelection(){
        try {
            ImageIO.write(image , "png" , new File(getCurrentDirectory().getAbsolutePath() , getSelectedFile().getName()+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        container.dispose();
    }
    @Override
    public void cancelSelection(){
        container.dispose();
    }
}
