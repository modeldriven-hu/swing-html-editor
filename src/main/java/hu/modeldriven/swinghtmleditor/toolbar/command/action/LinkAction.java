package hu.modeldriven.swinghtmleditor.toolbar.command.action;

import hu.modeldriven.swinghtmleditor.component.LinkDialog;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.event.ActionEvent;

public class LinkAction extends HTMLEditorKit.HTMLTextAction {
    private static final long serialVersionUID = 42L;

    public LinkAction() {
        super("link-broken");
    }

    private void insertLink(final JTextPane editor, final String href) {
        final SimpleAttributeSet sasTag = new SimpleAttributeSet();
        final SimpleAttributeSet sasAttr = new SimpleAttributeSet();
        sasAttr.addAttribute(HTML.Attribute.HREF, href);
        sasTag.addAttribute(HTML.Tag.A, sasAttr);
        final int pss = editor.getSelectionStart();
        final int pse = editor.getSelectionEnd();
        if (pss != pse) {
            final HTMLDocument doc = getHTMLDocument(editor);
            doc.setCharacterAttributes(pss, pse - pss, sasTag, false);
        }
    }

    private String findLink(final JTextPane editor) {
        final int pss = editor.getSelectionStart();
        final int pse = editor.getSelectionEnd();
        final HTMLDocument doc = getHTMLDocument(editor);
        for (int i = pss; i <= pse; i++) {
            final Element elem = doc.getCharacterElement(i);
            final AttributeSet elemAttr = elem.getAttributes();
            final AttributeSet tagAttr = (AttributeSet) elemAttr.getAttribute(HTML.Tag.A);
            if (tagAttr != null) {
                final String href = (String) tagAttr.getAttribute(HTML.Attribute.HREF);
                if ((href != null) && !href.isEmpty()) {
                    return href;
                }
            }
            i = elem.getEndOffset() - 1; // fast-forward
        }
        return null;
    }

    public void actionPerformed(final ActionEvent e) {
        final JTextPane editor = (JTextPane) getEditor(e);
        if (editor != null) {
            final String href = findLink(editor);
            final LinkDialog d = new LinkDialog(null);
            if ((href != null) && !href.isEmpty()) {
                d.setLink(href);
            }
            d.init();
            if (!d.isAccepted()) { // Cancel
                return;
            }
            insertLink(editor, d.getLink());
        }
    }
}
