package hu.modeldriven.swinghtmleditor.component.icon;

import javax.swing.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class MergedIcon extends ImageIcon{

    public MergedIcon(final ImageIcon ... icons){
        int width = 0, height = 0;

        for (ImageIcon ico : icons) {
            width = Math.max(width, ico.getIconWidth());
            height = Math.max(height, ico.getIconHeight());
        }

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = image.createGraphics();

        for (ImageIcon ico : icons) {
            g.drawImage(ico.getImage(), 0, 0, null);
        }

        image.flush();
        g.dispose();

        setImage(image);
    }

}
