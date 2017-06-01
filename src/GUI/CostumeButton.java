package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Created by Amir on 5/16/2017.
 */
public class CostumeButton extends JButton{
    //TODO press color change
    ButtonType type;
    public CostumeButton(String text , ButtonType type){
        super(text);
        this.type = type;
        setBackground(new Color(255, 231 , 84 , 255));
        setBorder(BorderFactory.createLineBorder(Color.black , 2));
        setFont(new Font(getFont().getName() , getFont().getStyle() , getFont().getSize() + 10));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(255 , 100 , 50 , 255));
            }
            @Override
            public void mouseExited(MouseEvent e){
                setBackground(new Color(255 , 231 , 84 , 255));
            }
        });
        if (type != null){
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch (type){
                        case NEW:{
                            new FileChoosingWindow();
                            break;
                        }
                        case SAVE:{
                            int size = Menu.processes.size();
                            for(int i =0 ; i < size ; i ++){
                                Menu.processes.get(0).forceSaveAndExit(i);
                            }
                            System.exit(0);
                            break;
                        }
                        case DISCARD:{
                            try {
                                for (ImageProcessingWindow ipw : Menu.processes) {
                                    ipw.dispose();
                                }
                            }catch(ConcurrentModificationException exception){

                            }
                            System.exit(0);
                            break;
                        }
                        case BLANK:{
                            new ImageProcessingWindow(new File("src/index.png"));
                        }
                    }
                }
            });
        }
    }
}
