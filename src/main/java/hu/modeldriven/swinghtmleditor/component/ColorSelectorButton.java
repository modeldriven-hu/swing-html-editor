package hu.modeldriven.swinghtmleditor.component;

import hu.modeldriven.swinghtmleditor.component.icon.ColorSelectorIcon;
import hu.modeldriven.swinghtmleditor.palette.Palette;
import hu.modeldriven.swinghtmleditor.palette.PaletteItem;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import javax.swing.text.StyledEditorKit;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import static javax.swing.UIManager.getColor;

public class ColorSelectorButton extends JButton {
    private static final long serialVersionUID = 42L;

    private static final String COLOR_FOREGROUND = "Button.foreground";
    private static final String COLOR_DISABLED = "Button.disabledText";

    private final transient SetColorActionFactory colorFactory;
    private final transient Palette palette;
    private final JPopupMenu colorMenu;

    public ColorSelectorButton(Palette palette) {
        this.colorFactory = new SetColorActionFactory();
        this.palette = palette;

        this.colorMenu = createPopup();

        setSelectedColor(palette.getDefault());

        addActionListener(actionEvent ->
                colorMenu.show(ColorSelectorButton.this, 0,
                        ColorSelectorButton.this.getHeight()));

        setIcons(MaterialDesign.MDI_COLOR_HELPER);
    }

    private void setIcons(Ikon icon){
        setText(null);

        ImageIcon iconEnabled = new ColorSelectorIcon(icon,
                getColor(COLOR_FOREGROUND), getColor(COLOR_FOREGROUND));

        ImageIcon iconRollover = new ColorSelectorIcon(icon,
                getColor(COLOR_FOREGROUND), null);

        ImageIcon iconDisabled = new ColorSelectorIcon(icon,
                getColor(COLOR_DISABLED), getColor(COLOR_FOREGROUND));

        setIcon(iconEnabled);
        setRolloverIcon(iconRollover);
        setDisabledIcon(iconDisabled);
    }

    protected final JPopupMenu createPopup() {
        JPopupMenu result = new JPopupMenu();

        result.setRequestFocusEnabled(false);
        result.setLayout(new GridLayout(8, 2));

        for (PaletteItem paletteItem : palette.getItems()) {
            JMenuItem menuItem = new JMenuItem(paletteItem.getName());
            menuItem.addActionListener(e -> setSelectedColor(paletteItem, e));

            FontIcon iconEnabled = FontIcon.of(MaterialDesign.MDI_COLOR_HELPER, 24, paletteItem.getColor());
            menuItem.setIcon(iconEnabled);

            result.add(menuItem);
        }

        return result;
    }

    public void setSelectedColor(PaletteItem paletteItem) {
        final Icon icon = getIcon();
        final String text = getText();

        setAction(colorFactory.create(paletteItem));

        // Reset original text and icon
        setIcon(icon);
        setText(text);
    }

    public void setSelectedColor( PaletteItem c, ActionEvent e) {
        setSelectedColor(c);
        getAction().actionPerformed(e);
    }

    public static class SetColorActionFactory {
        public StyledEditorKit.StyledTextAction create(PaletteItem newColor) {
            return new StyledEditorKit.ForegroundAction(newColor.getName(), newColor.getColor());
        }
    }
}
