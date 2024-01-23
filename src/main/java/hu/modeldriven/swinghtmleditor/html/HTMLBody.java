package hu.modeldriven.swinghtmleditor.html;

import javax.swing.text.Element;
import javax.swing.text.html.HTMLDocument;
import java.io.StringWriter;

public class HTMLBody {

    private final HTMLDocument document;

    private final CustomHTMLEditorKit editorKit;

    public HTMLBody(HTMLDocument document, CustomHTMLEditorKit editorKit){
        this.document = document;
        this.editorKit = editorKit;
    }

    public String asString(){

        try {

            Element rootElement = document.getDefaultRootElement();
            Element element = findElementByTagName(rootElement, "body");

            StringWriter stringWriter = new StringWriter();

            editorKit.write(stringWriter, element.getDocument(), element.getStartOffset(), element.getEndOffset()-element.getStartOffset());

            return editorKit.sanitizeHTML(stringWriter.toString());
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private Element findElementByTagName(Element parent, String tagName) {
        if (parent != null && parent.getName() != null && parent.getName().equals(tagName)) {
            return parent;
        }

        for (int i = 0; i < parent.getElementCount(); i++) {
            Element child = parent.getElement(i);
            Element found = findElementByTagName(child, tagName);
            if (found != null) {
                return found;
            }
        }

        return null;
    }

}
