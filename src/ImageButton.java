import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ImageButton {

    public void changeImgColor(JButton btn, Color color){
        
        Icon icon = btn.getIcon();
        if (icon == null){
            System.out.println("Button hat kein Icon");
            return;
        }

        if (icon instanceof ImageIcon){
            BufferedImage img = toBufferedImg(((ImageIcon)icon).getImage());
            if (img != null){
                BufferedImage newImg = changeColor(img, color);
                if (newImg != null) {
                    btn.setIcon(new ImageIcon(newImg));
                    btn.revalidate();
                    btn.repaint();
                    
                }
            }
        }
    }
    private BufferedImage toBufferedImg(Image img){

        if (img instanceof BufferedImage){
            return (BufferedImage) img;
        }

        BufferedImage bImg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D bGr= bImg.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bImg;
    }
    private BufferedImage changeColor(BufferedImage oldImg, Color color){

        BufferedImage newImg = new BufferedImage(oldImg.getWidth(), oldImg.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < oldImg.getHeight(); y++) {
            for (int x = 0; x < oldImg.getWidth(); x++) {
                int rgb = oldImg.getRGB(x, y);
                Color col = new Color(rgb, true);
                if (col.getAlpha() != 0){
                    newImg.setRGB(x,y,color.getRGB());
                } 
                else{
                    newImg.setRGB(x, y, rgb);
                }
            } 
        }

        return newImg;
    }
}