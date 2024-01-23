package hu.modeldriven.swinghtmleditor;

import hu.modeldriven.swinghtmleditor.command.Command;
import hu.modeldriven.swinghtmleditor.command.CommandGroup;
import hu.modeldriven.swinghtmleditor.component.ColorSelectorButton;
import hu.modeldriven.swinghtmleditor.util.IconHelper;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import javax.swing.*;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.util.List;

public class ToolbarFactory {

    public JToolBar createToolBar(JTextPane editorPane, List<CommandGroup> commandGroups) {

        JToolBar toolBar = new JToolBar();

        // Create toolbar buttons
        createToolBarButtons(toolBar, editorPane, commandGroups);

        // Focus gain/lost enable/disable buttons
        editorPane.addFocusListener(createFocusListener(toolBar));

        return toolBar;
    }


    private void createToolBarButtons(JToolBar toolBar, JTextPane editorPane, List<CommandGroup> commandGroups) {

        ActionMap editorActionMap = editorPane.getActionMap();

        for (CommandGroup commandGroup : commandGroups) {

            for (Command command : commandGroup.getCommands()) {

                command.getAction().ifPresent(a -> editorActionMap.put(command.getActionMapKey(), a));
                command.getKeyEvent().ifPresent(keyEvent -> mapKey(editorPane, keyEvent, command.getActionMapKey()));

                JButton button = new JButton(editorActionMap.get(command.getActionMapKey()));
                button.setText(command.getText());
                button.setRequestFocusEnabled(command.isRequestFocusEnabled());
                button.setToolTipText(command.getTooltipText());
                IconHelper.set(command.getIcon(), button);

                command.getActionListener().ifPresent(l -> button.addActionListener(l));

                toolBar.add(button);
            }

            toolBar.addSeparator();
        }

        // FONT COLOR
        ColorSelectorButton btnFontColor = new ColorSelectorButton();
        btnFontColor.setRequestFocusEnabled(false);
        btnFontColor.setToolTipText("Font Color");
        IconHelper.setColorHelper(MaterialDesign.MDI_COLOR_HELPER, btnFontColor);
        toolBar.add(btnFontColor);
    }

    private FocusListener createFocusListener(JToolBar toolBar) {
        return new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                enableComponents(toolBar, true);
            }

            @Override
            public void focusLost(FocusEvent e) {
                enableComponents(toolBar, false);
            }
        };
    }

    private void enableComponents(JToolBar container, boolean enable) {
        for (Component comp : container.getComponents()) {
            comp.setEnabled(enable);
        }
    }

    protected void mapKey(final JTextPane editor, final int keyCode, final String actionMapKey) {
        final InputMap im = editor.getInputMap(JComponent.WHEN_FOCUSED);
        //TODO find correct java 8 version Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();
        final int mask = InputEvent.CTRL_DOWN_MASK;
        im.put(KeyStroke.getKeyStroke(keyCode, mask), actionMapKey);
    }


}