package hu.modeldriven.swinghtmleditor.toolbar.command;

import hu.modeldriven.swinghtmleditor.toolbar.command.action.UnorderedListAction;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import javax.swing.*;
import java.util.Optional;

public class UnorderedListCommand implements Command {

    @Override
    public Optional<Action> getAction() {
        return Optional.of(new UnorderedListAction());
    }

    @Override
    public String getActionMapKey() {
        return "insert-ul";
    }

    @Override
    public String getText() {
        return "ul";
    }

    @Override
    public String getTooltipText() {
        return "Unordered List";
    }

    @Override
    public Ikon getIcon() {
        return MaterialDesign.MDI_FORMAT_LIST_BULLETED;
    }

}
