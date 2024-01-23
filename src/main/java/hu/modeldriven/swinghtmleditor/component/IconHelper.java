package hu.modeldriven.swinghtmleditor.component;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.Color;

import static javax.swing.UIManager.getColor;

// business objects
public class IconHelper {
    private static final int ICON_SIZE = 24; // FIXME: Hardcoded value

    public static void set(Ikon icon, AbstractButton comp) {
        comp.setText(null);

        FontIcon iconEnabled = FontIcon.of(icon, ICON_SIZE, getColor("Button.foreground"));
        FontIcon iconDisabled = FontIcon.of(icon, ICON_SIZE, getColor("Button.disabledText"));

        comp.setIcon(iconEnabled);
        comp.setDisabledIcon(iconDisabled);
    }

    public static void set(Ikon icon, AbstractButton comp, Color color) {
        FontIcon iconEnabled = FontIcon.of(icon, ICON_SIZE, color);
        comp.setIcon(iconEnabled);
    }

}
