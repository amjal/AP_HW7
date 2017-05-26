package GUI;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Amir on 5/15/2017.
 */
public class Menu{
    JFrame frame;
    JButton exitWithoutSavingButton;
    JButton exitAndSaveButton;
    JButton newWindowButton;
    protected static List<ImageProcessingWindow> processes;
    public Menu(){
        buttonInit();
        frameInit();
    }
    private void frameInit(){
        frame = new JFrame("Main Menu");
        processes = new ArrayList<>();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridLayout frameLayout = new GridLayout(3 , 1);
        frame.getContentPane().setLayout(frameLayout);
        frame.setSize(500 , 500);
        frame.add(exitAndSaveButton);
        frame.add(exitWithoutSavingButton);
        frame.add(newWindowButton);
        frame.setVisible(true);
    }
    private void buttonInit(){
        exitWithoutSavingButton = new CostumeButton("exit and discard processes" , ButtonType.DISCARD);
        exitAndSaveButton = new CostumeButton("exit and save processes" , ButtonType.SAVE);
        newWindowButton = new CostumeButton("edit a new image" , ButtonType.NEW);

    }
}
