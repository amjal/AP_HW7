/**
 * Created by Amir on 5/15/2017.
 */
import javax.swing.*;
import GUI.*;

import java.io.File;

public class Main {
    public static void main(String args[]){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new Menu();
            }
        });
    }
}
