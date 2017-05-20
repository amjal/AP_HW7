package GUI;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Created by Amir on 5/19/2017.
 */
public class CostumePanel extends JPanel {
    private BufferedImage image;
    private int dimenssion;
    private AffineTransform transform;
    private double rotateAngle;
    private boolean imageCropInQueue = false;
    private boolean cropImage = false;
    private boolean drawRect = false;
    private Point rectStart , rectEnd;
    private JFrame container;
    public CostumePanel(BufferedImage image , int size , JFrame container){
        this.image = image;
        this.dimenssion = size;
        setSize(dimenssion , dimenssion);
        setLocation(0 , 0);
        setLayout(null);
        transform = new AffineTransform();
        if (dimenssion>image.getHeight()){
            transform.translate(0 , (dimenssion - image.getHeight())/2);
        }
        else transform.translate((dimenssion - image.getWidth())/2 , 0);
        setFocusable(true);
        RectHandler rectHandler = new RectHandler();
        addMouseListener(rectHandler);
        addMouseMotionListener(rectHandler);
        this.container = container;
    }
    public void rotate(String theta){
        rotateAngle = Math.toRadians(Double.parseDouble(theta));
        transform.rotate(rotateAngle, image.getWidth() / 2, image.getHeight() / 2 + 20);
    }
    public void crop(){
        imageCropInQueue = true;
    }
    @Override
    public void paintComponent(Graphics g){
        System.out.println("paint");
        Graphics2D g2d = (Graphics2D)g.create();
        g2d.clearRect(0 , 0, getWidth() , getHeight());
        if(cropImage) {
            cropImage = false;
            g2d.clipRect((int) rectStart.getX(), (int) rectStart.getY(),
                    (int) rectEnd.getX() - (int) rectStart.getX(),
                    (int) rectEnd.getY() - (int) rectStart.getY());
        }
        g2d.setTransform(transform);
        g2d.drawImage(image , 0 , 40 , null);
        g2d.dispose();
        if(drawRect){
            Graphics2D rectG = (Graphics2D) g.create();
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
            container.repaint();
        }
        public void mouseDragged(MouseEvent e){
            drawRect = true;
            rectStart = start;
            rectEnd = e.getPoint();
            container.repaint();
        }
    }
}
