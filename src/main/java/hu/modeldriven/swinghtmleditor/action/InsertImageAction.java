package hu.modeldriven.swinghtmleditor.action;

import hu.modeldriven.swinghtmleditor.component.Image;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.atomic.AtomicReference;

public class InsertImageAction extends HTMLEditorKit.HTMLTextAction {
    private static final long serialVersionUID = 42L;

    public InsertImageAction() {
        super("insert-img");
    }

    private boolean isBlock(final Object a) {
        if (a instanceof HTML.Tag) {
            final HTML.Tag t = (HTML.Tag) a;
            return t.isBlock();
        }
        return false;
    }

    private HTML.Tag getBlockTag(final HTMLDocument doc, final int offset) {
        Element e = doc.getCharacterElement(offset);
        while (e != null && !isBlock(e.getAttributes().getAttribute(StyleConstants.NameAttribute))) {
            e = e.getParentElement();
        }
        if (e != null) {
            return (HTML.Tag) e.getAttributes().getAttribute(StyleConstants.NameAttribute);
        }
        return null;
    }

    private String getMimeOfImage(final File file) {
        String type = null;
        try {
            final URL u = file.toURI().toURL();
            final URLConnection uc = u.openConnection();
            type = uc.getContentType();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return type;
    }

    private String getImage(final File file) {
        final String mime = getMimeOfImage(file);
        if (mime == null) {
            return null;
        }
        try (final FileInputStream is = new FileInputStream(file)) {
            final Image img = Image.parse(mime, is);
            return "<img id=\"" + img.md5sum + "\" src=\"" + img.dataUri + "\">";
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void loadImage(final HTML.Tag blockTag, final ActionEvent ae) {
        final JFileChooser chooser = new JFileChooser();
        chooser.setRequestFocusEnabled(false);
        chooser.setCurrentDirectory(new File("."));
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileFilter(new FileNameExtensionFilter("Image files", //
                "png", "gif", "jpeg", "jpg", "jpe", "jfif"));
        final int returnVal = chooser.showOpenDialog(null);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        final File file = chooser.getSelectedFile();
        final SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            private final AtomicReference<String> html = new AtomicReference<>();

            @Override
            public Void doInBackground() {
                html.set(getImage(file));
                return null;
            }

            @Override
            public void done() {
                new HTMLEditorKit.InsertHTMLTextAction("delegated", html.get(), blockTag, HTML.Tag.IMG)
                        .actionPerformed(ae);
            }
        };
        worker.execute();
    }

    public void actionPerformed(final ActionEvent e) {
        final JTextPane editor = (JTextPane) getEditor(e);
        if (editor != null) {
            final HTMLDocument doc = getHTMLDocument(editor);
            final int offset = editor.getSelectionStart();
            final HTML.Tag blockTag = getBlockTag(doc, offset);
            if (blockTag != null) {
                loadImage(blockTag, e);
            }
        }
    }
}
