package hu.modeldriven.swinghtmleditor.action;

import hu.modeldriven.swinghtmleditor.command.*;
import hu.modeldriven.swinghtmleditor.util.IconHelper;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.undo.UndoManager;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ToolbarFactory {

    public JToolBar createToolBar(HTMLDocument doc, JTextPane editorPane) {

        JToolBar toolBar = new JToolBar();

        // Create toolbar buttons
        createToolBarButtons(toolBar, editorPane, doc);

        // Focus gain/lost enable/disable buttons
        editorPane.addFocusListener(createFocusListener(toolBar));

        return toolBar;
    }


    private void createToolBarButtons(JToolBar toolBar, JTextPane editorPane, HTMLDocument doc) {

        UndoManager undoManager = new UndoManager();
        doc.addUndoableEditListener(undoManager);

        List<CommandGroup> commandGroups = new ArrayList<>();

        commandGroups.add(new CommandGroup(
                new BoldCommand(),
                new ItalicCommand(),
                new UnderlineCommand(),
                new StrikethroughCommand()
        ));

//        // FONT COLOR
//        ColorSelectorButton btnFontColor = new ColorSelectorButton();
//        btnFontColor.setRequestFocusEnabled(false);
//        btnFontColor.setToolTipText("Font Color");
//        IconHelper.setColorHelper(MaterialDesign.MDI_COLOR_HELPER, btnFontColor);
//        toolBar.add(btnFontColor);

        // add separator

        commandGroups.add(new CommandGroup(
                new IncreaseIndentCommand(),
                new ReduceIndentCommand()
        ));

        // add separator

        commandGroups.add(new CommandGroup(
                new AlignLeftCommand(),
                new AlignCenterCommand(),
                new AlignRightCommand(),
                new AlignJustifiedCommand()
        ));

        // add separator

        commandGroups.add(new CommandGroup(
                new LinkCommand(),
                new UnlinkCommand()
        ));

        // add separator

        commandGroups.add(new CommandGroup(
                new InsertHorizontalRuleCommand(),
                new InsertImageCommand()
        ));

        // add separator

        commandGroups.add(new CommandGroup(
                new UndoCommand(undoManager),
                new RedoCommand(undoManager)
        ));

        ActionMap editorActionMap = editorPane.getActionMap();

        for (Iterator<CommandGroup> it = commandGroups.iterator(); it.hasNext(); ) {

            CommandGroup commandGroup = it.next();

            for (Command command : commandGroup.getCommands()) {

                command.getAction().ifPresent(a -> editorActionMap.put(command.getActionMapKey(), a));
                command.getKeyEvent().ifPresent(keyEvent -> mapKey(editorPane, keyEvent, command.getActionMapKey()));

                JButton button = new JButton(editorActionMap.get(command.getActionMapKey()));
                button.setText(command.getText());
                button.setRequestFocusEnabled(command.isRequestFocusEnabled());
                button.setToolTipText(command.getTooltipText());
                IconHelper.set(command.getIcon(), button);

                toolBar.add(button);
            }

            if (it.hasNext()) {
                toolBar.addSeparator();
            }
        }

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
