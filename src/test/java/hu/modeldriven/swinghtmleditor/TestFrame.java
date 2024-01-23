package hu.modeldriven.swinghtmleditor;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.lang.invoke.MethodHandles;

public class TestFrame {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final boolean useDark = true;

    private JFrame frame;

    /**
     * Launch the application.
     *
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
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
        });

        SwingUtilities.invokeLater(() -> {
            try {
                log.info("Open window...");
                TestFrame window = new TestFrame();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public TestFrame() {
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

        SwingHTMLEditor editor = new SwingHTMLEditor();
        editor.setText("<p>Hello <b>world</b> from HTML!</p>");
        editor.addDocumentListener(new MyDocumentListener(editor));
        frame.getContentPane().add(editor, BorderLayout.CENTER);
    }

    class MyDocumentListener implements DocumentListener{

        private final SwingHTMLEditor editor;

        public MyDocumentListener(SwingHTMLEditor editor) {
            this.editor = editor;
        }

        @Override
        public void insertUpdate(DocumentEvent documentEvent) {
            handleDocumentUpdate(documentEvent);
        }

        @Override
        public void removeUpdate(DocumentEvent documentEvent) {
            handleDocumentUpdate(documentEvent);
        }

        @Override
        public void changedUpdate(DocumentEvent documentEvent) {
            handleDocumentUpdate(documentEvent);
        }

        private void handleDocumentUpdate(DocumentEvent documentEvent) {
            System.out.println(editor.getText());
        }
    }

}
