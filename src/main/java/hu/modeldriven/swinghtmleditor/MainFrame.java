package hu.modeldriven.swinghtmleditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.*;
import java.lang.invoke.MethodHandles;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import hu.modeldriven.swinghtmleditor.action.*;
import hu.modeldriven.swinghtmleditor.action.ToolbarFactory;
import hu.modeldriven.swinghtmleditor.html.CustomHTMLEditorKit;
import org.owasp.html.CssSchema;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme;

public class MainFrame {
	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private static final boolean useDark = true;

	private JFrame frame;

	/**
	 * Launch the application.
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				try {
					log.info("Load decorators...");
					UIManager.put("TitlePane.menuBarEmbedded", false);
					if (useDark) {
						FlatDarculaLaf.setup();
					} else {
						FlatLightFlatIJTheme.setup();
					}
					FlatLaf.updateUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					log.info("Open window...");
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	protected void initialize() {
		frame = new JFrame("Simple HTML Text Editor");
		frame.setMinimumSize(new Dimension(763, 300));
		frame.setLocation(100, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());

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

		contentPanel.add(toolBar, BorderLayout.NORTH);
		contentPanel.add(editorScrollPane, BorderLayout.CENTER);
		afterLoad(editorPane);

		frame.getContentPane().add(contentPanel, BorderLayout.CENTER);
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
