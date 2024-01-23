package hu.modeldriven.swinghtmleditor.component.icon;

import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class RainbowIcon extends ImageIcon {

    public static final int ICON_SIZE = 24;

    public RainbowIcon(int width, int height) {
        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = image.createGraphics();

        final int y = ICON_SIZE - 4;

        int n = 0;

        for (final Color c : new Color[]{
                Color.RED, //
                Color.YELLOW, //
                Color.GREEN, //
                Color.CYAN, //
                Color.BLUE, //
                Color.MAGENTA //
        }) {
            g.setColor(c);
            g.fillRect(n * 4, y, 4, 4);
            n++;
        }

        image.flush();
        g.dispose();
        setImage(image);
    }

}
