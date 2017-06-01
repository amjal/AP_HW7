package GUI;

import java.awt.*;

/**
 * Created by amir on 5/30/17.
 */
public class ImageText {
    private String text;
    private Color color;
    private Font font;
    private Point location;
    public ImageText(String text , Color color , Font font , Point location){
        this.text = text;
        this.color = color;
        this.font = font;
        this.location = location;
    }
    public String getText(){
        return text;
    }
    public Color getColor(){
        return color;
    }
    public Font getFont(){
        return font;
    }
    public Point getLocation(){
        return location;
    }
    public void setLocation(Point p){
        location = p;
    }
}
