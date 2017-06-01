package GUI;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Amir on 5/19/2017.
 */
public class PaintPanel extends JPanel {
    private int dimension;
    private Point rectStart , rectEnd;
    private java.util.List<PanelStatus> panelStatuses;
    private int statusIndex =0;
    private boolean drawRect = false;
    public PaintPanel(BufferedImage image , int size){
        this.dimension = size;
        setSize(dimension , dimension);
        setLayout(null);
        setFocusable(true);
        RectHandler rectHandler = new RectHandler();
        addMouseListener(rectHandler);
        addMouseMotionListener(rectHandler);
        panelStatuses = new ArrayList<>();
        panelStatuses.add(new PanelStatus(image));

    }
    public void rotate(String theta){
        panelStatuses.add(new PanelStatus(panelStatuses.get(statusIndex)));
        statusIndex ++;
        panelStatuses.get(statusIndex).setRotateAngle( Math.toRadians(Double.parseDouble(theta)));
        panelStatuses.get(statusIndex).setRotate(true);
        repaint();
    }
    public void drawText(String text , Font font , Color color){
        addMouseMotionListener(new TextLocationHandler());
        panelStatuses.add(new PanelStatus(panelStatuses.get(statusIndex)));
        statusIndex ++;
        ImageText imageText = new ImageText(text , color , font , new Point(dimension/2 , dimension/2));
        panelStatuses.get(statusIndex).setImageText(imageText);
        repaint();
    }
    public void setSticker(BufferedImage image){
        addMouseMotionListener(new StickerLocationHandler());
        panelStatuses.add(new PanelStatus(panelStatuses.get(statusIndex)));
        statusIndex ++;
        Sticker sticker = new Sticker(image , new Point(dimension/2 , dimension/2));
        panelStatuses.get(statusIndex).setSticker(sticker);
        repaint();
    }
    public void crop(boolean b){
        if(b) {
            panelStatuses.add(new PanelStatus(panelStatuses.get(statusIndex)));
            statusIndex++;
        }
        panelStatuses.get(statusIndex).setImageCropInQueue(b);
    }
    public void newStatus(){
        panelStatuses.add(new PanelStatus(panelStatuses.get(statusIndex)));
        statusIndex ++;
    }
    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        super.paintComponent(g2d);
        if(panelStatuses.get(statusIndex).isCropImage()) {
            g2d.clipRect((int) panelStatuses.get(statusIndex).getCropStart().getX(), (int) panelStatuses.get(statusIndex).getCropStart().getY(),
                    (int) panelStatuses.get(statusIndex).getCropEnd().getX() - (int) panelStatuses.get(statusIndex).getCropStart().getX(),
                    (int) panelStatuses.get(statusIndex).getCropEnd().getY() - (int) panelStatuses.get(statusIndex).getCropStart().getY());
        }
        if(panelStatuses.get(statusIndex).isRotate())
            g2d.rotate(panelStatuses.get(statusIndex).getRotateAngle(), dimension / 2, dimension/2);
        g2d.drawImage(panelStatuses.get(statusIndex).getImage() ,
                (dimension - panelStatuses.get(statusIndex).getImage().getWidth())/2 ,
                (dimension - panelStatuses.get(statusIndex).getImage().getHeight())/2 , null);
        if (panelStatuses.get(statusIndex).getImageText() != null) {
            g2d.setFont(panelStatuses.get(statusIndex).getImageText().getFont());
            g2d.setColor(panelStatuses.get(statusIndex).getImageText().getColor());
            g2d.drawString(panelStatuses.get(statusIndex).getImageText().getText(),
                    panelStatuses.get(statusIndex).getImageText().getLocation().x,
                    panelStatuses.get(statusIndex).getImageText().getLocation().y);
        }
        if(panelStatuses.get(statusIndex).getSticker() != null){
            g2d.drawImage(panelStatuses.get(statusIndex).getSticker().getImage() ,
                    panelStatuses.get(statusIndex).getSticker().getLocation().x ,
                    panelStatuses.get(statusIndex).getSticker().getLocation().y , null);
        }
        if(drawRect){
            Graphics2D rectG = (Graphics2D) g.create();
            if(panelStatuses.get(statusIndex).isRotate()) rectG.rotate(-panelStatuses.get(statusIndex).getRotateAngle()
                    , dimension/2 , dimension/2);
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
            if (panelStatuses.get(statusIndex).isImageCropInQueue()){
                panelStatuses.get(statusIndex).setCropImage(true);
                panelStatuses.get(statusIndex).setImageCropInQueue(false);
                panelStatuses.get(statusIndex).setCropStart(start);
                panelStatuses.get(statusIndex).setCropEnd(e.getPoint());
            }
            repaint();
        }
        public void mouseDragged(MouseEvent e){
            if (!e.isShiftDown() && !e.isControlDown()) {
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
                panelStatuses.get(statusIndex).getImageText().setLocation( e.getPoint());
                repaint();
            }
        }
    }
    class StickerLocationHandler extends MouseAdapter{
        @Override
        public void mouseDragged(MouseEvent e){
            super.mouseDragged(e);
            if(e.isControlDown()){
                panelStatuses.get(statusIndex).getSticker().setLocation(e.getPoint());
                repaint();
            }
        }
    }

    public void adjustColor(int value , Color color){
        if (color.equals(Color.red)){
            for(int i =0 ; i < panelStatuses.get(statusIndex).getImage().getWidth() ; i++){
                for(int j =0 ; j < panelStatuses.get(statusIndex).getImage().getHeight() ; j++){
                    panelStatuses.get(statusIndex).getImage().setRGB(i, j, setRed(panelStatuses.get(statusIndex).getImage().getRGB(i , j) , value));
                }
            }
        }
        else if(color.equals(Color.green)){
            for(int i =0 ; i < panelStatuses.get(statusIndex).getImage().getWidth() ; i ++){
                for(int j =0 ; j < panelStatuses.get(statusIndex).getImage().getHeight() ; j ++){
                    panelStatuses.get(statusIndex).getImage().setRGB(i , j , setGreen(panelStatuses.get(statusIndex).getImage().getRGB(i , j) , value));
                }
            }
        }
        else if(color.equals(Color.blue)){
            for(int i =0 ; i < panelStatuses.get(statusIndex).getImage().getWidth() ; i ++){
                for(int j =0 ; j <panelStatuses.get(statusIndex).getImage().getHeight() ; j ++){
                    panelStatuses.get(statusIndex).getImage().setRGB(i , j , setBlue(panelStatuses.get(statusIndex).getImage().getRGB( i , j) , value));
                }
            }
        }
        repaint();
    }
    public int setRed(int rgb , int value){
        rgb = (rgb & 0b11111111000000001111111111111111);
        rgb = (rgb | value*65536);
        return rgb;
    }
    public int setGreen(int rgb , int value){
        rgb = (rgb & 0b11111111111111110000000011111111);
        rgb = (rgb | value*256);
        return rgb;
    }
    public int setBlue(int rgb , int value)
    {
        rgb = (rgb & 0b11111111111111111111111100000000);
        rgb = (rgb | value);
        return rgb;
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
    public void setImage(BufferedImage image){
        panelStatuses.get(statusIndex).setImage(image);
    }
    public BufferedImage getImage(){
        return panelStatuses.get(statusIndex).getImage();
    }
    public void undo(){
        if(statusIndex != 0) {
            panelStatuses.remove(statusIndex);
            statusIndex--;
        }
        repaint();
    }
}
