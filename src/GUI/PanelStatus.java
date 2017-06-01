package GUI;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by amir on 5/30/17.
 */
public class PanelStatus {
    private boolean rotate;
    private ImageText imageText;
    private Sticker sticker;
    private double rotateAngle;
    private boolean imageCropInQueue;
    private boolean cropImage;
    private Point cropStart;
    private Point cropEnd;
    private BufferedImage image;
    public PanelStatus(BufferedImage image){
        this.image = image;
        imageCropInQueue = false;
        cropImage = false;
        rotate = false;
        imageText = null;
        sticker = null;
    }

    public PanelStatus(PanelStatus former){
        image = copyImage(former.getImage());
        rotate = former.isRotate();
        imageText = former.getImageText();
        sticker = former.getSticker();
        rotateAngle = former.getRotateAngle();
        imageCropInQueue = former.isImageCropInQueue();
        cropImage = former.isCropImage();
        cropStart = former.getCropStart();
        cropEnd = former.getCropEnd();
    }
    public BufferedImage copyImage(BufferedImage image){
        BufferedImage output = new BufferedImage(image.getWidth() , image.getHeight() , BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = output.createGraphics();
        g2d.drawImage(image , 0 , 0 , null);
        g2d.dispose();
        return output;
    }

    public void setRotateAngle(double rotateAngle) {
        this.rotateAngle = rotateAngle;
    }

    public void setImageCropInQueue(boolean imageCropInQueue) {
        this.imageCropInQueue = imageCropInQueue;
    }

    public void setCropImage(boolean cropImage) {
        this.cropImage = cropImage;
    }

    public void setRotate(boolean rotate) {
        this.rotate = rotate;
    }

    public void setImageText(ImageText imageText) {
        this.imageText = imageText;
    }

    public void setSticker(Sticker sticker) {
        this.sticker = sticker;
    }
    public void setImage(BufferedImage image){
        this.image = image;
    }

    public void setCropStart(Point p){
        cropStart = p;
    }
    public void setCropEnd(Point p){
        cropEnd = p;
    }
    public double getRotateAngle() {
        return rotateAngle;
    }

    public boolean isImageCropInQueue() {
        return imageCropInQueue;
    }

    public boolean isCropImage() {
        return cropImage;
    }


    public boolean isRotate() {
        return rotate;
    }

    public ImageText getImageText() {
        return imageText;
    }

    public Sticker getSticker() {
        return sticker;
    }
    public BufferedImage getImage(){
        return image;
    }
    public Point getCropStart(){
        return cropStart;
    }
    public Point getCropEnd(){
        return cropEnd;
    }
}
