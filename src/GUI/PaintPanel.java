package GUI;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Created by Amir on 5/19/2017.
 */
public class PaintPanel extends JPanel {
    protected BufferedImage image;
    private int dimension;
    private double rotateAngle;
    private boolean imageCropInQueue = false;
    private boolean cropImage = false;
    private boolean drawRect = false;
    private boolean rotate = false;
    private boolean drawText = false;
    private Point textLocation;
    private Point rectStart , rectEnd;
    private String text;
    private Color textColor;
    private Font textFont;
    public PaintPanel(BufferedImage image , int size){
        this.image = image;
        this.dimension = size;
        setSize(dimension , dimension);
        setLayout(null);
        setFocusable(true);
        RectHandler rectHandler = new RectHandler();
        addMouseListener(rectHandler);
        addMouseMotionListener(rectHandler);
        TextLocationHandler textLocationHandler = new TextLocationHandler();
        addMouseMotionListener(textLocationHandler);
        textLocation = new Point(dimension/2 , dimension/2);

    }
    public void rotate(String theta){
        rotateAngle = Math.toRadians(Double.parseDouble(theta));
        rotate = true;
        repaint();
    }
    public void drawText(String text , Font font , Color color){
        drawText = true;
        this.text = text;
        textFont = font;
        textColor = color;
        repaint();
    }
    public void crop(boolean b){
        imageCropInQueue = b;
    }
    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        super.paintComponent(g2d);
        if(cropImage) {
            g2d.clipRect((int) rectStart.getX(), (int) rectStart.getY(),
                    (int) rectEnd.getX() - (int) rectStart.getX(),
                    (int) rectEnd.getY() - (int) rectStart.getY());
        }
        if(rotate)
            g2d.rotate(rotateAngle, dimension / 2, dimension/2);
        if(dimension > image.getHeight())
            g2d.drawImage(image , 0 , (dimension - image.getHeight())/2 , null);
        else if(dimension <image.getWidth())
            g2d.drawImage(image , (dimension - image.getWidth())/2 , 0 , null);
        if (drawText) {
            g2d.setFont(textFont);
            g2d.setColor(textColor);
            g2d.drawString(text, textLocation.x, textLocation.y);
        }
        if(drawRect){
            Graphics2D rectG = (Graphics2D) g.create();
            if(rotate) rectG.rotate(-rotateAngle , dimension/2 , dimension/2);
            rectG.drawRect((int)rectStart.getX() , (int)rectStart.getY(),
                    (int)rectEnd.getX() - (int)rectStart.getX() ,
                    (int)rectEnd.getY() - (int)rectStart.getY());
            rectG.dispose();
        }
    }
    class RectHandler extends MouseInputAdapter{
        Point start;
        @Override
        public void mousePressed(MouseEvent e){
            start = e.getPoint();
        }
        public void mouseReleased(MouseEvent e){
            drawRect = false;
            if (imageCropInQueue){
                cropImage = true;
                imageCropInQueue = false;
            }
            repaint();
        }
        public void mouseDragged(MouseEvent e){
            if (!e.isShiftDown()) {
                drawRect = true;
                rectStart = start;
                rectEnd = e.getPoint();
                repaint();
            }
        }
    }
    class TextLocationHandler extends MouseAdapter{
        @Override
        public void mouseDragged(MouseEvent e) {
            super.mouseDragged(e);
            if (e.isShiftDown()){
                textLocation = e.getPoint();
                repaint();
            }
        }
    }
    public void adjustColor(int value , Color color){
        int localValue;
        if (color.equals(Color.red)){
            for(int i =0 ; i < image.getWidth() ; i++){
                for(int j =0 ; j < image.getHeight() ; j++){
                    int red = getRed(image.getRGB(i , j));
                    if (value >0)
                        localValue = Math.min(255 - red, value);
                    else
                        localValue = Math.max((-red) , value);
                    image.setRGB(i, j, image.getRGB(i, j) + localValue * 65536);
                }
            }
        }
        else if(color.equals(Color.green)){
            for(int i =0 ; i < image.getWidth() ; i ++){
                for(int j =0 ; j < image.getHeight() ; j ++){
                    int green = getGreen(image.getRGB(i , j));
                    if (value >0)
                        localValue = Math.min(255 - green, value);
                    else
                        localValue = Math.max((-green) , value);
                    image.setRGB(i , j , image.getRGB(i , j ) + localValue*256);
                }
            }
        }
        else if(color.equals(Color.blue)){
            for(int i =0 ; i < image.getWidth() ; i ++){
                for(int j =0 ; j <image.getHeight() ; j ++){
                    int blue = getBlue(image.getRGB(i , j));
                    if (value >0)
                        localValue = Math.min(255 - blue, value);
                    else
                        localValue = Math.max((-blue) , value);
                    image.setRGB(i , j , image.getRGB(i , j) + localValue);
                }
            }
        }
    }
    public int getRed(int rgb){
        return ((rgb & 0b00000000111111110000000000000000)/65536);
    }
    public int getGreen(int rgb){
        return ((rgb & 0b00000000000000001111111100000000)/265);
    }
    public int getBlue(int rgb){
        return ((rgb & 0b0000000000000000000000011111111));
    }

    public BufferedImage exportImage(){
        BufferedImage outputImage = new BufferedImage(dimension , dimension , BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = outputImage.createGraphics();
        paintComponent(g2d);
        return outputImage;
    }
    public int getDimension(){
        return dimension;
    }
}
