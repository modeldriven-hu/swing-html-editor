package hu.modeldriven.swinghtmleditor.command;

import hu.modeldriven.swinghtmleditor.command.action.LinkAction;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import javax.swing.*;
import java.util.Optional;

public class LinkCommand implements Command {

    @Override
    public Optional<Action> getAction() {
        return Optional.of(new LinkAction());
    }

    @Override
    public String getActionMapKey() {
        return "link";
    }

    @Override
    public String getText() {
        return "Link";
    }

    @Override
    public String getTooltipText() {
        return "Link";
    }

    @Override
    public Ikon getIcon() {
        return MaterialDesign.MDI_LINK;
    }

}
