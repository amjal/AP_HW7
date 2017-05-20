package GUI;

import com.sun.javafx.font.FontFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Amir on 5/16/2017.
 */
public class FileChoosingWindow extends JFrame{
    public FileChoosingWindow(){
        super("Select File");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500 , 700);
        add(new CostumeFileChooser(this));
        setVisible(true);
    }
}
