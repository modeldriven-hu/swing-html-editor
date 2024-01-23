package hu.modeldriven.swinghtmleditor.command;

import hu.modeldriven.swinghtmleditor.command.action.UnlinkAction;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import javax.swing.*;
import java.util.Optional;

public class UnlinkCommand implements Command {

    @Override
    public Optional<Action> getAction() {
        return Optional.of(new UnlinkAction());
    }

    @Override
    public String getActionMapKey() {
        return "unlink";
    }

    @Override
    public String getText() {
        return "Unlink";
    }

    @Override
    public String getTooltipText() {
        return "Unlink";
    }

    @Override
    public Ikon getIcon() {
        return MaterialDesign.MDI_LINK_OFF;
    }
}
