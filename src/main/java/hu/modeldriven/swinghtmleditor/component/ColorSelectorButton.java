package hu.modeldriven.swinghtmleditor.component;

import hu.modeldriven.swinghtmleditor.palette.Palette;
import hu.modeldriven.swinghtmleditor.palette.PaletteItem;
import hu.modeldriven.swinghtmleditor.util.IconHelper;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import javax.swing.*;
import javax.swing.text.StyledEditorKit;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

public class ColorSelectorButton extends JButton {
    private static final long serialVersionUID = 42L;
    private final SetColorActionFactory colorFactory;
    private final JPopupMenu colorMenu;

    private final Palette palette;

    public ColorSelectorButton(Palette palette) {
        this.colorFactory = new SetColorActionFactory();
        this.palette = palette;

        this.colorMenu = createPopup();

        setSelectedColor(palette.getDefault());

        addActionListener(actionEvent ->
                colorMenu.show(ColorSelectorButton.this, 0,
                        ColorSelectorButton.this.getHeight()));
    }

    protected final JPopupMenu createPopup() {
        JPopupMenu colorMenu = new JPopupMenu();
        colorMenu.setRequestFocusEnabled(false);
        colorMenu.setLayout(new GridLayout(8, 2));

        for (final PaletteItem paletteItem : palette.getItems()) {
            JMenuItem menuItem = new JMenuItem(paletteItem.getName());
            menuItem.addActionListener(e -> setSelectedColor(paletteItem, e));
            IconHelper.set(MaterialDesign.MDI_COLOR_HELPER, menuItem, paletteItem.getColor());
            colorMenu.add(menuItem);
        }

        return colorMenu;
    }

    public void setSelectedColor(final PaletteItem paletteItem) {
        final Icon icon = getIcon();
        final String text = getText();

        setAction(colorFactory.create(paletteItem));

        // Reset original text and icon
        setIcon(icon);
        setText(text);
    }

    public void setSelectedColor(final PaletteItem c, final ActionEvent e) {
        setSelectedColor(c);
        getAction().actionPerformed(e);
    }

    public static class SetColorActionFactory {
        public StyledEditorKit.StyledTextAction create(final PaletteItem newColor) {
            return new StyledEditorKit.ForegroundAction(newColor.getName(), newColor.getColor());
        }
    }
}
