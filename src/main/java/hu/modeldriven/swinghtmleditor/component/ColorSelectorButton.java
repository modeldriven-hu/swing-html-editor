package hu.modeldriven.swinghtmleditor.component;

import hu.modeldriven.swinghtmleditor.html.WebColor;
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

    public ColorSelectorButton() {
        this.colorFactory = new SetColorActionFactory();
        this.colorMenu = createPopup();
        setSelectedColor(WebColor.W_BLACK);
        addActionListener(actionEvent ->
                colorMenu.show(ColorSelectorButton.this, 0,
                        ColorSelectorButton.this.getHeight()));
    }

    protected final JPopupMenu createPopup() {
        JPopupMenu colorMenu = new JPopupMenu();
        colorMenu.setRequestFocusEnabled(false);
        colorMenu.setLayout(new GridLayout(8, 2));

        final WebColor[] colors = {
                WebColor.W_WHITE, //
                WebColor.W_SILVER, //
                WebColor.W_GRAY, //
                WebColor.W_BLACK, //
                WebColor.W_RED, //
                WebColor.W_MAROON, //
                WebColor.W_YELLOW, //
                WebColor.W_OLIVE, //
                WebColor.W_LIME, //
                WebColor.W_GREEN, //
                WebColor.W_AQUA, //
                WebColor.W_TEAL, //
                WebColor.W_BLUE, //
                WebColor.W_NAVY, //
                WebColor.W_FUCHSIA, //
                WebColor.W_PURPLE //
        };

        for (final WebColor c : colors) {
            JMenuItem menuItem = new JMenuItem(c.getWebName());
            menuItem.addActionListener(e -> setSelectedColor(c, e));
            IconHelper.set(MaterialDesign.MDI_COLOR_HELPER, menuItem, c);
            colorMenu.add(menuItem);
        }

        return colorMenu;
    }

    public void setSelectedColor(final WebColor newColor) {
        final Icon icon = getIcon();
        final String text = getText();

        setAction(colorFactory.create(newColor));

        // Reset original text and icon
        setIcon(icon);
        setText(text);
    }

    public void setSelectedColor(final WebColor c, final ActionEvent e) {
        setSelectedColor(c);
        getAction().actionPerformed(e);
    }

    public static class SetColorActionFactory {
        public StyledEditorKit.StyledTextAction create(final WebColor newColor) {
            return new StyledEditorKit.ForegroundAction(newColor.getWebName(), newColor);
        }
    }
}
