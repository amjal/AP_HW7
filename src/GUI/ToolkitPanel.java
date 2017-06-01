package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import com.jhlabs.image.*;
/**
 * Created by Amir on 5/22/2017.
 */
public class ToolkitPanel extends JPanel{
    PaintPanel paintPanel;
    public ToolkitPanel(PaintPanel paintPanel){
        super();
        this.paintPanel = paintPanel;
        setLayout(null);
        setLocation(0 , paintPanel.getDimension());
        setSize(paintPanel.getDimension() , 200);
        setOpaque(true);
        setDoubleBuffered(true);
    }
    public void setupNewToolkit(ToolkitPanelMode mode){
        removeAll();
        paintPanel.crop(false);
        switch (mode) {
            case ROTATE: {
                JLabel message = new JLabel("enter how much you want to rotate:");
                message.setSize(300, 30);
                message.setFont(new Font(message.getFont().getName(), message.getFont().getStyle(),
                        message.getFont().getSize() + 3));
                message.setLocation(0, 5);
                JTextField input = new JTextField();
                input.setLocation(0, message.getY() + message.getHeight() + 5);
                input.setSize(50, 40);
                CostumeButton confirm = new CostumeButton("rotate", null);
                confirm.setLocation(55, message.getY() + message.getHeight() + 5);
                confirm.setSize(90, 40);
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
            case CROP: {
                paintPanel.crop(true);
                break;
            }
            case COLOR: {
                paintPanel.newStatus();
                JSlider redSlider = new JSlider(0, 255 , 0);
                JSlider greenSlider = new JSlider(0, 255 , 0);
                JSlider blueSlider = new JSlider(0, 255 , 0);
                redSlider.setBounds(0 , 5, paintPanel.getDimension() , 40);
                redSlider.setPaintLabels(true);
                redSlider.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        Hashtable<Integer , JLabel> table = new Hashtable<>();
                        table.put(redSlider.getValue() , new JLabel(""+ redSlider.getValue()));
                        redSlider.setLabelTable(table);
                        paintPanel.adjustColor(redSlider.getValue() , Color.red);
                    }
                });
                greenSlider.setBounds(0 , 45 , paintPanel.getDimension() , 40);
                greenSlider.setPaintLabels(true);
                greenSlider.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        Hashtable<Integer , JLabel> table = new Hashtable<>();
                        table.put(greenSlider.getValue() , new JLabel(""+greenSlider.getValue()));
                        greenSlider.setLabelTable(table);
                        paintPanel.adjustColor(greenSlider.getValue() , Color.green);
                    }
                });
                blueSlider.setBounds(0 , 85 , paintPanel.getDimension() , 40);
                blueSlider.setPaintLabels(true);
                blueSlider.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        Hashtable<Integer , JLabel> table = new Hashtable<>();
                        table.put(blueSlider.getValue() , new JLabel(""+blueSlider.getValue()));
                        blueSlider.setLabelTable(table);
                        paintPanel.adjustColor(blueSlider.getValue() , Color.blue);
                    }
                });
                add(redSlider);
                add(greenSlider);
                add(blueSlider);
                break;
            }
            case TEXT: {
                JTextField textField = new JTextField("press enter to apply");
                Color[] colors= {Color.white , Color.black , Color.blue , Color.cyan , Color.gray ,
                        Color.green , Color.magenta , Color.orange , Color.pink , Color.red , Color.yellow};
                String[] colorNames = {"white" , "black" , "blue" , "cyan" , "gray" , "green" , "magenta"
                        , "orange" , "pink" , "red" , "yellow"};
                JComboBox colorList = new JComboBox(colorNames);
                String[] fontSizes = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15",
                        "16","17","18","19","20","21","22","23","24","25","26","27","28","29","30",
                        "31","32","33","34","35","36","37","38","39","40"};
                JComboBox fontSizeList = new JComboBox(fontSizes);
                {
                    textField.setBounds(0 , 5 , 180 , 30);
                    textField.setFont(new Font(textField.getFont().getName() , textField.getFont().getStyle() ,
                         textField.getFont().getSize() +4));
                    textField.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            if(e.getKeyChar() == KeyEvent.VK_ENTER) {
                                paintPanel.drawText(textField.getText(),
                                        new Font(textField.getFont().getName() , textField.getFont().getStyle() ,fontSizeList.getSelectedIndex())
                                        , colors[colorList.getSelectedIndex()]);
                            }
                        }
                    });
                }
                {
                    colorList.setBounds(185, 5, 90, 30);
                    colorList.setFont(textField.getFont());
                    colorList.setMaximumRowCount(3);
                    colorList.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            super.keyTyped(e);
                            if(e.getKeyChar() == KeyEvent.VK_ENTER){
                                paintPanel.drawText(textField.getText(),
                                        new Font(textField.getFont().getName() , textField.getFont().getStyle() ,fontSizeList.getSelectedIndex())
                                        , colors[colorList.getSelectedIndex()]);
                            }

                        }
                    });
                }
                {
                    fontSizeList.setBounds(280, 5, 50, 30);
                    fontSizeList.setFont(textField.getFont());
                    fontSizeList.setMaximumRowCount(3);
                    fontSizeList.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            super.keyTyped(e);
                            if(e.getKeyChar() == KeyEvent.VK_ENTER){
                                paintPanel.drawText(textField.getText(),
                                        new Font(textField.getFont().getName() , textField.getFont().getStyle() ,fontSizeList.getSelectedIndex())
                                        , colors[colorList.getSelectedIndex()]);
                            }
                        }
                    });
                }
                JLabel message = new JLabel("hold shift and drag the mouse to relocate the text");
                message.setBounds(0 , 40 , paintPanel.getDimension() , 30);
                message.setFont(new Font(message.getFont().getName() , message.getFont().getStyle() ,
                        message.getFont().getSize() +4));
                revalidate();
                add(message);
                add(fontSizeList);
                add(colorList);
                add(textField);
                break;
            }
            case STICKER: {
                ImageIcon[] icons = new ImageIcon[2];
                for(int i =0 ; i < 2 ; i ++){
                    try {
                        icons[i] = new ImageIcon(ImageIO.read(new File("src/stickers/"+i+".png")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                JList<ImageIcon> list = new JList(icons);
                list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
                list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                list.setVisibleRowCount(1);
                list.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        super.mouseClicked(mouseEvent);
                        paintPanel.setSticker((BufferedImage)icons[list.locationToIndex(mouseEvent.getPoint())].getImage());
                        paintPanel.repaint();
                    }
                });
                JScrollPane scrollPane = new JScrollPane(list);
                scrollPane.setBounds(0 , 0 , paintPanel.getDimension() , 105);
                revalidate();
                add(scrollPane);
                break;
            }
            case FILTER: {
                ImageIcon[] icons = new ImageIcon[12];
                java.util.List<AbstractBufferedImageOp> filters = new ArrayList<>();
                BufferedImage iconImage = null;
                try {
                    iconImage = ImageIO.read(new File("src/trump.jpg"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                TwirlFilter twirlFilter = new TwirlFilter();
                twirlFilter.setAngle(2);
                InvertFilter invertFilter = new InvertFilter();
                CrystallizeFilter crystallizeFilter = new CrystallizeFilter();
                BlurFilter blurFilter = new BlurFilter();
                CurlFilter curlFilter = new CurlFilter();
                curlFilter.setAngle(3);
                curlFilter.setTransition((float)0.3);
                DitherFilter ditherFilter = new DitherFilter();
                GammaFilter gammaFilter = new GammaFilter();
                gammaFilter.setGamma(3);
                GlowFilter glowFilter = new GlowFilter();
                LookupFilter lookupFilter = new LookupFilter();
                SparkleFilter sparkleFilter = new SparkleFilter();
                StampFilter stampFilter = new StampFilter();

                filters.add(new TwirlFilter());
                filters.add(twirlFilter);
                filters.add(invertFilter);
                filters.add(crystallizeFilter);
                filters.add(blurFilter);
                filters.add(curlFilter);
                filters.add(ditherFilter);
                filters.add(gammaFilter);
                filters.add(glowFilter);
                filters.add(lookupFilter);
                filters.add(sparkleFilter);
                filters.add(stampFilter);

                for(int i =0 ; i < 12 ; i ++){
                    icons[i] = new ImageIcon(filters.get(i).filter(iconImage , null), null);
                }
                final BufferedImage originalImage = paintPanel.getImage();
                JList<ImageIcon> list = new JList(icons);
                list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
                list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                list.setVisibleRowCount(1);
                list.addMouseListener(new MouseInputAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        if(list.locationToIndex(e.getPoint()) != 0)
                            paintPanel.newStatus();
                        paintPanel.setImage(filters.get(list.locationToIndex(e.getPoint())).filter(originalImage , null));
                        paintPanel.repaint();
                    }
                });
                JScrollPane scrollPane = new JScrollPane(list);
                scrollPane.setBounds(0 , 0 , paintPanel.getDimension() , 105);
                revalidate();
                add(scrollPane);
                break;
            }
        }
        repaint();
    }
    public void setPaintPanel(PaintPanel paintPanel){
        this.paintPanel = paintPanel;
    }
}
