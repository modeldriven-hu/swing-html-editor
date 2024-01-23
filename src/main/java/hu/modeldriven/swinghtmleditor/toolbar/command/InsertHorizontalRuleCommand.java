package hu.modeldriven.swinghtmleditor.toolbar.command;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import javax.swing.*;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import java.util.Optional;

public class InsertHorizontalRuleCommand implements Command {

    @Override
    public Optional<Action> getAction() {
        return Optional.of(new HTMLEditorKit.InsertHTMLTextAction("insert-hr",
                "<hr size=1 align=left noshade>", HTML.Tag.BODY, HTML.Tag.HR));
    }

    @Override
    public String getActionMapKey() {
        return "insert-hr";
    }

    @Override
    public String getText() {
        return "\u2015";
    }

    @Override
    public String getTooltipText() {
        return "Horizontal rule";
    }

    @Override
    public Ikon getIcon() {
        return MaterialDesign.MDI_BORDER_HORIZONTAL;
    }

}
