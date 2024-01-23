package hu.modeldriven.swinghtmleditor.component.icon;

import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class FontBasedImageIcon extends ImageIcon {

    public FontBasedImageIcon(FontIcon fontIcon){
        BufferedImage image = new BufferedImage(fontIcon.getIconWidth(), fontIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        fontIcon.paintIcon(null, image.getGraphics(), 0, 0);
        setImage(image);
    }

}
