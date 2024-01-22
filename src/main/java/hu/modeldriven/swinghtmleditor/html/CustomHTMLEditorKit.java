package hu.modeldriven.swinghtmleditor.html;

import hu.modeldriven.swinghtmleditor.MainFrame;
import org.apache.commons.io.IOUtils;
import org.fit.net.DataURLHandler;

import javax.swing.text.*;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.ImageView;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomHTMLEditorKit extends HTMLEditorKit {
    private static final long serialVersionUID = 42L;
    private final AtomicBoolean init = new AtomicBoolean();

    // https://community.oracle.com/tech/developers/discussion/1363299/detect-when-image-finishes-loading-in-html-with-jeditorpane
    private final ViewFactory factory = new HTMLFactory() {
        public View create(final Element elem) {
            final View v = super.create(elem);
            if ((v != null) && (v instanceof ImageView)) {
                final String src = (String) elem.getAttributes().getAttribute(HTML.Attribute.SRC);
                if (src == null) {
                    return null;
                }
                final boolean isDataURI = src.startsWith("data:");
                if (isDataURI) {
                    return new ImageView(elem) {
                        @Override
                        public URL getImageURL() {
                            try {
                                return DataURLHandler.createURL(null, src);
                            } catch (MalformedURLException e) {
                                return null;
                            }
                        }
                    };
                }
                // img.setLoadsSynchronously(true);
            }
            return v;
        }
    };

    @Override
    public ViewFactory getViewFactory() {
        return factory;
    }

    @Override
    public void read(final InputStream in, final Document doc, final int pos) //
            throws IOException, BadLocationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void write(final OutputStream out, final Document doc, final int pos, final int len) //
            throws IOException, BadLocationException {
        throw new UnsupportedOperationException();
    }

    protected String sanitizeHTML(final String input) {
        return MainFrame.HTML_POLICY_DEFINITION_BASIC.sanitize(input);
    }

    @Override
    public void read(final Reader in, final Document doc, final int pos) //
            throws IOException, BadLocationException {
        if (init.getAndSet(true)) {
            final String text = sanitizeHTML(IOUtils.toString(in));
            super.read(new StringReader(text), doc, pos);
        } else {
            super.read(in, doc, pos);
        }
    }

    @Override
    public void write(final Writer out, final Document doc, final int pos, final int len) //
            throws IOException, BadLocationException {
        final CustomHTMLWriter w = new CustomHTMLWriter(out, (HTMLDocument) doc, pos, len);
        w.write();
        out.flush();
    }
}