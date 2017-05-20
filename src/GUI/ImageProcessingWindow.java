package GUI;
import javafx.scene.input.MouseDragEvent;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Amir on 5/16/2017.
 */
public class ImageProcessingWindow extends JFrame{
    File file;
    BufferedImage image;
    JMenuBar menuBar;
    CostumePanel paintPanel;
    int panelDimenssion;
    public ImageProcessingWindow(File file){
        super("Edit Image");
        this.file = file;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        init();
    }

    private void init(){
        menuBarConfigure();
        panelDimenssion = Math.max(image.getHeight() , image.getWidth());
        createPaintPanel();
        add(paintPanel);
        setSize(panelDimenssion , panelDimenssion +200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setJMenuBar(menuBar);
        setVisible(true);
    }
    private void menuBarConfigure(){
        menuBar = new JMenuBar();
        menuBar.setPreferredSize(new Dimension(getWidth(), 40));
        fileMenuConfigure();
        editMenuConfigure();
    }
    private void fileMenuConfigure(){
        JMenuItem save = new JMenuItem("save");
        JMenuItem undo = new JMenuItem("undo changes");
        {
            adjustFontSize(save);
            adjustFontSize(undo);
            undo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    remove(paintPanel);
                    createPaintPanel();
                    add(paintPanel);
                    repaint();
                }
            });
            save.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new FileChoosingWindow();
                }
            });
        }
        JMenu fileMenu = new JMenu("File");
        {
            fileMenu.setPreferredSize(new Dimension(40, 40));
            adjustFontSize(fileMenu);
            fileMenu.add(save);
            fileMenu.add(undo);
        }
        menuBar.add(fileMenu);
    }
    private void editMenuConfigure(){
        JMenu editMenu = new JMenu("Edit");
        editMenu.setPreferredSize(new Dimension(40 , 40));
        editMenu.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {}

            @Override
            public void menuDeselected(MenuEvent e) {
                repaint();
            }

            @Override
            public void menuCanceled(MenuEvent e) {}
        });
        JMenuItem rotate = new JMenuItem("rotate");
        rotate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JLabel message = new JLabel("enter how much you want to rotate:");
                message.setSize(300 , 30);
                message.setFont(new Font(message.getFont().getName() , message.getFont().getStyle() ,
                        message.getFont().getSize()+4));
                message.setLocation( 0 ,  panelDimenssion+5);
                JTextField input = new JTextField();
                input.setLocation(0 , message.getY() + message.getHeight() +5);
                input.setSize(50 , 30);
                CostumeButton confirm = new CostumeButton("rotate" , null);
                confirm.setLocation(55 , message.getY() + message.getHeight() +5);
                confirm.setSize(80 , 30);
                confirm.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        remove(input);
                        remove(message);
                        remove(confirm);
                        paintPanel.rotate(input.getText());
                        repaint();
                    }
                });
                add(message);
                add(input);
                add(confirm);
                repaint();
            }
        });
        JMenuItem crop = new JMenuItem("crop");
        crop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintPanel.crop();
            }
        });
        JMenuItem filter = new JMenuItem("filter");
        filter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        JMenuItem color = new JMenuItem("color");
        JMenuItem addText = new JMenuItem("add text");
        JMenuItem addSticker = new JMenuItem("add sticker");
        adjustFontSize(editMenu);
        adjustFontSize(rotate);
        adjustFontSize(crop);
        adjustFontSize(filter);
        adjustFontSize(color);
        adjustFontSize(addText);
        adjustFontSize(addSticker);
        editMenu.add(rotate);
        editMenu.add(crop);
        editMenu.add(filter);
        editMenu.add(color);
        editMenu.add(addText);
        editMenu.add(addSticker);
        menuBar.add(editMenu);
    }
    private void createPaintPanel(){
        paintPanel = new CostumePanel(image , panelDimenssion , this);
    }
    private void adjustFontSize(Component c){
        c.setFont(new Font(c.getFont().getName() , c.getFont().getStyle() , c.getFont().getSize() +4));
    }
    public JPanel getPaintPanel(){
        return paintPanel;
    }
}
//TODO use clip for crop
