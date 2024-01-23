package hu.modeldriven.swinghtmleditor.component;

import hu.modeldriven.swinghtmleditor.component.icon.ColorSelectorIcon;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.Color;

// business objects
public class IconHelper {
    private static final int ICON_SIZE = 24; // FIXME: Hardcoded value

    public static Color getColor(String id) {
        return UIManager.getColor(id);
    }

    public static void setColorHelper(Ikon icon, AbstractButton comp) {
        comp.setText(null);

        ImageIcon iconEnabled = new ColorSelectorIcon(icon,
                getColor("Button.foreground"), getColor("Button.foreground"));

        ImageIcon iconRollover = new ColorSelectorIcon(icon,
                getColor("Button.foreground"), null);

        ImageIcon iconDisabled = new ColorSelectorIcon(icon,
                getColor("Button.disabledText"), getColor("Button.foreground"));

        comp.setIcon(iconEnabled);
        comp.setRolloverIcon(iconRollover);
        comp.setDisabledIcon(iconDisabled);
    }

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
