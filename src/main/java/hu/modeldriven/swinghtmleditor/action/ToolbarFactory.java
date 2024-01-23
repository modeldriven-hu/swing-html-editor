package hu.modeldriven.swinghtmleditor.action;

import hu.modeldriven.swinghtmleditor.command.*;
import hu.modeldriven.swinghtmleditor.component.ColorSelectorButton;
import hu.modeldriven.swinghtmleditor.util.IconHelper;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.Component;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ToolbarFactory {

    public JToolBar createToolBar(HTMLDocument doc, JTextPane editorPane){

        final UndoManager undoManager = new UndoManager();
        doc.addUndoableEditListener(undoManager);

        ActionMap editorActionMap = editorPane.getActionMap();

        editorActionMap.put("Undo", new AbstractAction() {
            private static final long serialVersionUID = 42L;

            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    if (undoManager.canUndo()) {
                        undoManager.undo();
                    }
                } catch (CannotUndoException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        editorActionMap.put("Redo", new AbstractAction() {
            private static final long serialVersionUID = 42L;

            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    if (undoManager.canRedo()) {
                        undoManager.redo();
                    }
                } catch (CannotUndoException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JToolBar toolBar = new JToolBar();

        createToolBarButtons(toolBar, editorPane, editorActionMap);

        // Focus gain/lost enable/disable butons
        editorPane.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                for (final Component comp : toolBar.getComponents()) {
                    comp.setEnabled(true);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                for (final Component comp : toolBar.getComponents()) {
                    comp.setEnabled(false);
                }
            }
        });

        return toolBar;
    }

    private void createToolBarButtons(JToolBar toolBar, JTextPane editorPane, ActionMap editorActionMap) {

        List<Command> commands = new ArrayList<>();

        commands.add(new BoldCommand());
        commands.add(new ItalicCommand());
        commands.add(new UnderlineCommand());
        commands.add(new StrikethroughCommand());

        // FONT COLOR
        ColorSelectorButton btnFontColor = new ColorSelectorButton();
        btnFontColor.setRequestFocusEnabled(false);
        btnFontColor.setToolTipText("Font Color");
        IconHelper.setColorHelper(MaterialDesign.MDI_COLOR_HELPER, btnFontColor);
        toolBar.add(btnFontColor);

        // add separator

        commands.add(new IncreaseIndentCommand());
        commands.add(new ReduceIndentCommand());

        // add separator

        commands.add(new AlignLeftCommand());
        commands.add(new AlignCenterCommand());
        commands.add(new AlignRightCommand());
        commands.add(new AlignJustifiedCommand());

        // add separator

        commands.add(new LinkCommand());
        commands.add(new UnlinkCommand());

        // add separator

        commands.add(new InsertHorizontalRuleCommand());
        commands.add(new InsertImageCommand());

        // add separator

        commands.add(new UndoCommand());
        commands.add(new RedoCommand());

//        // SAVE FILE
//        JButton btnSave = new JButton("Save");
//        btnSave.setText("Save");
//        btnSave.setRequestFocusEnabled(false);
//        btnSave.setToolTipText("Save");
//        btnSave.addActionListener(a -> writeFile(editorPane));
//        IconHelper.set(MaterialDesign.MDI_CONTENT_SAVE, btnSave);
//        toolBar.add(btnSave);
//
//        // LOAD FILE
//        JButton btnLoad = new JButton("Load");
//        btnLoad.setText("Load");
//        btnLoad.setRequestFocusEnabled(false);
//        btnLoad.setToolTipText("Load");
//        btnLoad.addActionListener(a -> loadFile(editorPane));
//        IconHelper.set(MaterialDesign.MDI_OPEN_IN_NEW, btnLoad);
//        toolBar.add(btnLoad);

        for (Command command : commands) {
            JButton button = new JButton(editorActionMap.get(command.getActionMapKey()));
            button.setText(command.getText());
            button.setRequestFocusEnabled(command.isRequestFocusEnabled());
            button.setToolTipText(command.getTooltipText());
            IconHelper.set(command.getIcon(), button);
            command.getKeyEvent().ifPresent(keyEvent -> mapKey(editorPane, keyEvent, command.getActionMapKey()));
            toolBar.add(button);
        }

    }


    protected void loadFile(final JTextPane editor) {
        if (editor == null) {
            return;
        }
        final HTMLDocument doc = (HTMLDocument) editor.getDocument();
        final HTMLEditorKit kit = (HTMLEditorKit) editor.getEditorKit();
        final JFileChooser chooser = new JFileChooser();
        chooser.setRequestFocusEnabled(false);
        chooser.setCurrentDirectory(new File("."));
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileFilter(new FileNameExtensionFilter("HTML files", //
                "html", "htm"));
        final int returnVal = chooser.showOpenDialog(null);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        final File file = chooser.getSelectedFile();
        try {
            editor.setText("");
            kit.read(new FileReader(file), doc, 0);
            afterLoad(editor);
        } catch (BadLocationException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    protected void afterLoad(final JTextPane editor) {
        // Goto first Paragraph
        final StyledDocument doc = editor.getStyledDocument();
        final int len = doc.getLength();
        for (int i = 0; i <= len; i++) {
            final Element elem = doc.getParagraphElement(i);
            final AttributeSet attr = elem.getAttributes();
            final Object o = attr.getAttribute(StyleConstants.NameAttribute);
            if (o == HTML.Tag.P) {
                editor.setCaretPosition(elem.getStartOffset());
                break;
            }
            i = elem.getEndOffset() - 1; // fast-forward
        }
        editor.requestFocus();
    }

    protected void writeFile(final JTextPane editor) {
        if (editor == null) {
            return;
        }
        final JFileChooser chooser = new JFileChooser();
        chooser.setRequestFocusEnabled(false);
        // Path relativize(Path other)
        chooser.setCurrentDirectory(new File("."));
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileFilter(new FileNameExtensionFilter("HTML files", //
                new String[] {
                        "html", "htm"
                }));
        final int returnVal = chooser.showSaveDialog(null);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        final File file = chooser.getSelectedFile();
        final HTMLDocument doc = (HTMLDocument) editor.getDocument();
        final EditorKit kit = editor.getEditorKit();
        try (final FileWriter writer = new FileWriter(file)) {
            kit.write(writer, doc, 0, doc.getLength());
        } catch (BadLocationException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void mapKey(final JTextPane editor, final int keyCode, final String actionMapKey) {
        final InputMap im = editor.getInputMap(JComponent.WHEN_FOCUSED);
        //TODO find correct java 8 version Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();
        final int mask =  InputEvent.CTRL_DOWN_MASK;
        im.put(KeyStroke.getKeyStroke(keyCode, mask), actionMapKey);
    }


}
