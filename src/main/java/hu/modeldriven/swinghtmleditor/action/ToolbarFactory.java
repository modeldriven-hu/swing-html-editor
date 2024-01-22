package hu.modeldriven.swinghtmleditor.action;

import hu.modeldriven.swinghtmleditor.component.ColorSelectorButton;
import hu.modeldriven.swinghtmleditor.util.IconHelper;
import org.kordamp.ikonli.materialdesign2.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

        editorActionMap.put("font-strike", new StrikethroughAction());
        editorActionMap.put("increment-indent", new IndentAction("increment-indent", 20f));
        editorActionMap.put("reduce-indent", new IndentAction("reduce-indent", -20f));
        editorActionMap.put("justified",
                new StyledEditorKit.AlignmentAction("Justify", StyleConstants.ALIGN_JUSTIFIED));
        editorActionMap.put("link", new LinkAction());
        editorActionMap.put("unlink", new UnlinkAction());
        editorActionMap.put("insert-hr", new HTMLEditorKit.InsertHTMLTextAction("insert-hr",
                "<hr size=1 align=left noshade>", HTML.Tag.BODY, HTML.Tag.HR));
        editorActionMap.put("insert-img", new InsertImageAction());

        JToolBar toolBar = new JToolBar();

        // BOLD
        JButton btnBold = new JButton(editorActionMap.get("font-bold"));
        btnBold.setText("B");
        btnBold.setRequestFocusEnabled(false);
        btnBold.setToolTipText("Bold");
        IconHelper.set(MaterialDesignF.FORMAT_BOLD, btnBold);
        mapKey(editorPane, KeyEvent.VK_B, "font-bold");
        toolBar.add(btnBold);

        // ITALIC
        JButton btnItalic = new JButton(editorActionMap.get("font-italic"));
        btnItalic.setText("I");
        btnItalic.setRequestFocusEnabled(false);
        btnItalic.setToolTipText("Italic");
        IconHelper.set(MaterialDesignF.FORMAT_ITALIC, btnItalic);
        mapKey(editorPane, KeyEvent.VK_I, "font-italic");
        toolBar.add(btnItalic);

        // UNDERLINE
        JButton btnUnderline = new JButton(editorActionMap.get("font-underline"));
        btnUnderline.setText("U");
        btnUnderline.setRequestFocusEnabled(false);
        btnUnderline.setToolTipText("Underline");
        {
            final Font fontUnderline = btnUnderline.getFont();
            final Map<TextAttribute, Object> attributes = new HashMap<>(fontUnderline.getAttributes());
            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            btnUnderline.setFont(fontUnderline.deriveFont(attributes));
        }
        IconHelper.set(MaterialDesignF.FORMAT_UNDERLINE, btnUnderline);
        mapKey(editorPane, KeyEvent.VK_U, "font-underline");
        toolBar.add(btnUnderline);

        // STRIKE-THROUGH
        JButton btnStrikethrough = new JButton(editorActionMap.get("font-strike"));
        btnStrikethrough.setText("S");
        btnStrikethrough.setRequestFocusEnabled(false);
        btnStrikethrough.setToolTipText("Strikethrough");
        {
            final Font fontStrikethrough = btnStrikethrough.getFont();
            final Map<TextAttribute, Object> attributes = new HashMap<>(fontStrikethrough.getAttributes());
            attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
            btnStrikethrough.setFont(fontStrikethrough.deriveFont(attributes));
        }
        IconHelper.set(MaterialDesignF.FORMAT_STRIKETHROUGH, btnStrikethrough);
        mapKey(editorPane, KeyEvent.VK_S, "font-strike");
        toolBar.add(btnStrikethrough);

        // FONT COLOR
        ColorSelectorButton btnFontColor = new ColorSelectorButton();
        btnFontColor.setRequestFocusEnabled(false);
        btnFontColor.setToolTipText("Font Color");
        IconHelper.setColorHelper(MaterialDesignF.FORMAT_COLOR_TEXT, btnFontColor);
        toolBar.add(btnFontColor);

        toolBar.addSeparator();

        // PARAGRAPH + INDENT
        JButton btnIncreaseIndent = new JButton(editorActionMap.get("increment-indent"));
        btnIncreaseIndent.setText("+Indent");
        btnIncreaseIndent.setRequestFocusEnabled(false);
        btnIncreaseIndent.setToolTipText("Increase Indent");
        IconHelper.set(MaterialDesignF.FORMAT_INDENT_INCREASE, btnIncreaseIndent);
        toolBar.add(btnIncreaseIndent);

        // PARAGRAPH - INDENT
        JButton btnReduceIndent = new JButton(editorActionMap.get("reduce-indent"));
        btnReduceIndent.setText("-Indent");
        btnReduceIndent.setRequestFocusEnabled(false);
        btnReduceIndent.setToolTipText("Reduce Indent");
        IconHelper.set(MaterialDesignF.FORMAT_INDENT_DECREASE, btnReduceIndent);
        toolBar.add(btnReduceIndent);

        toolBar.addSeparator();

        // PARAGRAPH LEFT ALIGN
        JButton btnLeftAlign = new JButton(editorActionMap.get("left-justify"));
        btnLeftAlign.setText("Left");
        btnLeftAlign.setRequestFocusEnabled(false);
        btnLeftAlign.setToolTipText("Left Alignment");
        IconHelper.set(MaterialDesignF.FORMAT_ALIGN_LEFT, btnLeftAlign);
        toolBar.add(btnLeftAlign);

        // PARAGRAPH CENTER ALIGN
        JButton btnCenterAlign = new JButton(editorActionMap.get("center-justify"));
        btnCenterAlign.setText("Center");
        btnCenterAlign.setRequestFocusEnabled(false);
        btnCenterAlign.setToolTipText("Center Alignment");
        IconHelper.set(MaterialDesignF.FORMAT_ALIGN_CENTER, btnCenterAlign);
        toolBar.add(btnCenterAlign);

        // PARAGRAPH RIGHT ALIGN
        JButton btnRightAlign = new JButton(editorActionMap.get("right-justify"));
        btnRightAlign.setText("Right");
        btnRightAlign.setRequestFocusEnabled(false);
        btnRightAlign.setToolTipText("Right Alignment");
        IconHelper.set(MaterialDesignF.FORMAT_ALIGN_RIGHT, btnRightAlign);
        toolBar.add(btnRightAlign);

        // PARAGRAPH JUSTIFIED
        JButton btnJustify = new JButton(editorActionMap.get("justified"));
        btnJustify.setText("Justify");
        btnJustify.setRequestFocusEnabled(false);
        btnJustify.setToolTipText("Justify Alignment");
        IconHelper.set(MaterialDesignF.FORMAT_ALIGN_JUSTIFY, btnJustify);
        toolBar.add(btnJustify);

        toolBar.addSeparator();

        // TEXT HYPERLINK
        JButton btnLink = new JButton(editorActionMap.get("link"));
        btnLink.setText("Link");
        btnLink.setRequestFocusEnabled(false);
        btnLink.setToolTipText("Link");
        IconHelper.set(MaterialDesignL.LINK_VARIANT, btnLink);
        toolBar.add(btnLink);

        // TEXT CLEAR HYPERLINK
        JButton btnUnlink = new JButton(editorActionMap.get("unlink"));
        btnUnlink.setText("Unlink");
        btnUnlink.setRequestFocusEnabled(false);
        btnUnlink.setToolTipText("Unlink");
        IconHelper.set(MaterialDesignL.LINK_VARIANT_OFF, btnUnlink);
        toolBar.add(btnUnlink);

        toolBar.addSeparator();

        // HORIZONTAL RULE
        JButton btnHorizontalRule = new JButton(editorActionMap.get("insert-hr"));
        btnHorizontalRule.setText("\u2015");
        btnHorizontalRule.setRequestFocusEnabled(false);
        btnHorizontalRule.setToolTipText("Horizontal Rule");
        IconHelper.set(MaterialDesignM.MINUS, btnHorizontalRule);
        toolBar.add(btnHorizontalRule);

        // IMAGE
        JButton btnImage = new JButton(editorActionMap.get("insert-img"));
        btnImage.setText("Img");
        btnImage.setRequestFocusEnabled(false);
        btnImage.setToolTipText("Image");
        IconHelper.set(MaterialDesignI.IMAGE, btnImage);
        toolBar.add(btnImage);

        toolBar.addSeparator();

        // UNDO CHANGE
        JButton btnUndo = new JButton(editorActionMap.get("Undo"));
        btnUndo.setText("Undo");
        btnUndo.setRequestFocusEnabled(false);
        btnUndo.setToolTipText("Undo");
        IconHelper.set(MaterialDesignU.UNDO, btnUndo);
        mapKey(editorPane, KeyEvent.VK_Z, "Undo");
        toolBar.add(btnUndo);

        // REDO CHANGE
        JButton btnRedo = new JButton(editorActionMap.get("Redo"));
        btnRedo.setText("Redo");
        btnRedo.setRequestFocusEnabled(false);
        btnRedo.setToolTipText("Redo");
        IconHelper.set(MaterialDesignR.REDO, btnRedo);
        mapKey(editorPane, KeyEvent.VK_Y, "Redo");
        toolBar.add(btnRedo);

        toolBar.addSeparator();

        // SAVE FILE
        JButton btnSave = new JButton("Save");
        btnSave.setText("Save");
        btnSave.setRequestFocusEnabled(false);
        btnSave.setToolTipText("Save");
        btnSave.addActionListener(a -> writeFile(editorPane));
        IconHelper.set(MaterialDesignF.FLOPPY, btnSave);
        toolBar.add(btnSave);

        // LOAD FILE
        JButton btnLoad = new JButton("Load");
        btnLoad.setText("Load");
        btnLoad.setRequestFocusEnabled(false);
        btnLoad.setToolTipText("Load");
        btnLoad.addActionListener(a -> loadFile(editorPane));
        IconHelper.set(MaterialDesignF.FOLDER_OPEN, btnLoad);
        toolBar.add(btnLoad);

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
