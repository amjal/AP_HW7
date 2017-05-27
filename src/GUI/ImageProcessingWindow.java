package GUI;

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
    protected PaintPanel paintPanel;
    ToolkitPanel toolkitPanel;
    int paintPanelDimension;
    public ImageProcessingWindow(File file) {
        super("Edit Image");
        this.file = file;
        image = readImage();
        init();
    }
    private BufferedImage readImage(){
        BufferedImage output = null;
        try {
            output = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
    private void init(){
        menuBarConfigure();
        setJMenuBar(menuBar);
        paintPanelDimension = Math.max(300 , Math.max(image.getHeight() , image.getWidth()));
        createPaintPanel();
        setSize(paintPanelDimension , paintPanelDimension +200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        add(paintPanel);
        toolkitPanel = new ToolkitPanel(paintPanel);
        add(toolkitPanel);
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
        JMenuItem undo = new JMenuItem("undo color and filter changes");
        JMenuItem undoAll = new JMenuItem("undo all changes");
        JMenu fileMenu = new JMenu("File");
        {
            adjustFontSize(save);
            adjustFontSize(undo);
            adjustFontSize(undoAll);
            undoAll.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    remove(paintPanel);
                    image = readImage();
                    createPaintPanel();
                    add(paintPanel);
                    toolkitPanel.setPaintPanel(paintPanel);
                    repaint();
                }
            });
            save.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new FileSavingWindow(paintPanel.exportImage());
                }
            });
            undo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    paintPanel.image = readImage();
                    repaint();
                }
            });
        }
        {
            fileMenu.setPreferredSize(new Dimension(40, 40));
            adjustFontSize(fileMenu);
            fileMenu.add(save);
            fileMenu.add(undo);
            fileMenu.add(undoAll);
        }
        menuBar.add(fileMenu);
    }
    private void editMenuConfigure(){
        JMenu editMenu = new JMenu("Edit");
        editMenu.setPreferredSize(new Dimension(40 , 40));
        JMenuItem rotate = new JMenuItem("rotate");
        rotate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {toolkitPanel.setupNewToolkit(ToolkitPanelMode.ROTATE);}
        });
        JMenuItem crop = new JMenuItem("crop");
        crop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolkitPanel.setupNewToolkit(ToolkitPanelMode.CROP);
            }
        });
        JMenuItem filter = new JMenuItem("filter");
        filter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolkitPanel.setupNewToolkit(ToolkitPanelMode.FILTER);
            }
        });
        JMenuItem color = new JMenuItem("color");
        color.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolkitPanel.setupNewToolkit(ToolkitPanelMode.COLOR);
            }
        });
        JMenuItem addText = new JMenuItem("add text");
        addText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolkitPanel.setupNewToolkit(ToolkitPanelMode.TEXT);
            }
        });
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
        paintPanel = new PaintPanel(image , paintPanelDimension );

    }
    private void adjustFontSize(Component c){
        c.setFont(new Font(c.getFont().getName() , c.getFont().getStyle() , c.getFont().getSize() +4));
    }

    public void forceSaveAndExit(){
        try {
            ImageIO.write(paintPanel.exportImage() , "png" , new File(""+Menu.processes.indexOf(this)+".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dispose();
    }
    @Override
    public void dispose() {
        super.dispose();
        Menu.processes.remove(this);
    }
}
//TODO use clip for crop
