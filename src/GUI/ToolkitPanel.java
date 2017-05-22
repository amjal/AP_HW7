package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Amir on 5/22/2017.
 */
public class ToolkitPanel extends JPanel{
    PaintPanel paintPanel;
    public ToolkitPanel(PaintPanel paintPanel){
        this.paintPanel = paintPanel;
        setLayout(null);
        setLocation(0 , paintPanel.getDimension());
        setSize(paintPanel.getDimension() , 200);
        setOpaque(true);
        setDoubleBuffered(true);
    }
    public void setupNewToolkit(ToolkitPanelMode mode){
        removeAll();
        switch (mode){
            case ROTATE:{
                JLabel message = new JLabel("enter how much you want to rotate:");
                message.setSize(300 , 30);
                message.setFont(new Font(message.getFont().getName() , message.getFont().getStyle() ,
                        message.getFont().getSize()+4));
                message.setLocation( 0 ,  5);
                JTextField input = new JTextField();
                input.setLocation(0 , message.getY() + message.getHeight() +5);
                input.setSize(50 , 30);
                CostumeButton confirm = new CostumeButton("rotate" , null);
                confirm.setLocation(55 , message.getY() + message.getHeight() +5);
                confirm.setSize(80 , 30);
                confirm.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        paintPanel.rotate(input.getText());
                    }
                });
                add(message);
                add(input);
                add(confirm);
                break;
            }
            case CROP:{
                paintPanel.crop();
                break;
            }
            case COLOR:{

                break;
            }
            case TEXT:{
                break;
            }
            case STICKER:{
                break;
            }
            case FILTER:{
                break;
            }
        }
    }
}
