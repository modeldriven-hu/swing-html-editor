package hu.modeldriven.swinghtmleditor.command;

import hu.modeldriven.swinghtmleditor.command.action.IndentAction;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import javax.swing.*;
import java.util.Optional;

public class IncreaseIndentCommand implements Command {

    @Override
    public Optional<Action> getAction() {
        return Optional.of(new IndentAction(getActionMapKey(), 20f));
    }

    @Override
    public String getActionMapKey() {
        return "increment-indent";
    }

    @Override
    public String getText() {
        return "+Indent";
    }

    @Override
    public String getTooltipText() {
        return "Increase Indent";
    }

    @Override
    public Ikon getIcon() {
        return MaterialDesign.MDI_FORMAT_INDENT_INCREASE;
    }

}
