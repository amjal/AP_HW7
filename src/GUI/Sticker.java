package GUI;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by amir on 5/30/17.
 */
public class Sticker {
    private BufferedImage image;
    private Point location;
    public Sticker(BufferedImage image , Point location){
        this.image = image;
        this.location = location;
    }
    public BufferedImage getImage(){
        return image;
    }
    public Point getLocation(){
        return location;
    }
    public void setLocation(Point p){
        location = p;
    }
}
