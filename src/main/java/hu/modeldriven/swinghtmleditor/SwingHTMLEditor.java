package hu.modeldriven.swinghtmleditor;

import hu.modeldriven.swinghtmleditor.action.ToolbarFactory;
import hu.modeldriven.swinghtmleditor.html.CustomHTMLEditorKit;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.BorderLayout;
import java.awt.Color;

public class SwingHTMLEditor extends JPanel {

    public SwingHTMLEditor(){
        super();
        initComponents();
    }

    private void initComponents() {

        setLayout(new BorderLayout());

        final JTextPane editorPane = new JTextPane();
        editorPane.setContentType("text/html");
        editorPane.setBackground(Color.WHITE);

        final JScrollPane editorScrollPane = new JScrollPane();
        editorScrollPane.setViewportView(editorPane);

        final CustomHTMLEditorKit kit = new CustomHTMLEditorKit();
        editorPane.setEditorKit(kit);
        fixEditorCssRules(kit);
        kit.setAutoFormSubmission(false);

        final HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
        doc.setPreservesUnknownTags(false);
        doc.putProperty("IgnoreCharsetDirective", true);
        editorPane.setDocument(doc);
        editorPane.setText(createEmptyDocument());

        ToolbarFactory toolbarFactory = new ToolbarFactory();
        JToolBar toolBar = toolbarFactory.createToolBar(doc, editorPane);

        add(toolBar, BorderLayout.NORTH);
        add(editorScrollPane, BorderLayout.CENTER);
        afterLoad(editorPane);
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

    protected void fixEditorCssRules(final HTMLEditorKit kit) {
        // TODO: Fix font scaling: http://kynosarges.org/GuiDpiScaling.html
        // https://stackoverflow.com/questions/17551537/how-to-fit-font-into-pixel-size-in-java-how-to-convert-pixels-to-points
        // https://stackoverflow.com/questions/15659044/how-to-set-the-dpi-of-java-swing-apps-on-windows-linux
        final StyleSheet css = kit.getStyleSheet();
        css.addRule("body, p, li { font-size: 1.1em; }");
    }

    protected String createEmptyDocument() {
        return "<html><head>" //
                + "<style type=\"text/css\">" //
                + "body { color: black; background-color: white; font-family: \"Verdana\"; font-size: 10pt; font-weight: normal; font-style: normal; }" //
                + "p { margin-top: 2px; margin-bottom: 2px; }" //
                + "hr { border-top: 1px solid gray; }" //
                + "ol, ul { margin-left: 40px; margin-top: 2px; }" //
                + "</style></head><body>" //
                + "<p></p>" //
                + "</body></html>";
    }

}
