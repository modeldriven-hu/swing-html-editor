package hu.modeldriven.swinghtmleditor.component.icon;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.Color;

public class ColorSelectorIcon extends ImageIcon {

    private static final int ICON_SIZE = 24;

    public ColorSelectorIcon(Ikon ikonBase, Color colorBase, Color colorHelper){
        ImageIcon iconBase = new FontBasedImageIcon(FontIcon.of(ikonBase, ICON_SIZE, colorBase));

        if (colorHelper != null) {
            ImageIcon iconHelper = new FontBasedImageIcon(FontIcon.of(MaterialDesign.MDI_COLOR_HELPER, ICON_SIZE, colorBase));
            setImage(new MergedIcon(iconBase, iconHelper).getImage());
        } else {
            setImage(new MergedIcon(iconBase, new RainbowIcon(ICON_SIZE, ICON_SIZE)).getImage());
        }

    }

}
