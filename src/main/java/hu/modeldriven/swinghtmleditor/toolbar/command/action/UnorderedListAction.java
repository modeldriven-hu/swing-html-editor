package hu.modeldriven.swinghtmleditor.toolbar.command.action;

import javax.swing.*;
import javax.swing.text.Element;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.event.ActionEvent;

public class UnorderedListAction extends HTMLEditorKit.HTMLTextAction {

    public UnorderedListAction() {
        super("unordered-list");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JTextPane editor = (JTextPane) getEditor(actionEvent);

        if (editor != null) {
            HTMLDocument document = (HTMLDocument)editor.getDocument();
            int caretPos = editor.getCaretPosition();
            Element elem = document.getParagraphElement(caretPos);
            HTML.Tag currentTag = HTML.getTag(elem.getName());

            if (currentTag.equals(HTML.Tag.P)) {
                HTMLEditorKit.InsertHTMLTextAction action =
                        new HTMLEditorKit.InsertHTMLTextAction("insertHTML", "<ul>", currentTag, HTML.Tag.UL);
                action.actionPerformed(actionEvent);
            }

        }
    }
}
