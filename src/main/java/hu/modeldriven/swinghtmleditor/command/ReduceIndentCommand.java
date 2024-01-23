package hu.modeldriven.swinghtmleditor.command;

import hu.modeldriven.swinghtmleditor.command.action.IndentAction;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import javax.swing.*;
import java.util.Optional;

public class ReduceIndentCommand implements Command {

    @Override
    public Optional<Action> getAction() {
        return Optional.of(new IndentAction("reduce-indent", -20f));
    }

    @Override
    public String getActionMapKey() {
        return "reduce-indent";
    }

    @Override
    public String getText() {
        return "-Indent";
    }

    @Override
    public String getTooltipText() {
        return "Reduce Indent";
    }

    @Override
    public Ikon getIcon() {
        return MaterialDesign.MDI_FORMAT_INDENT_DECREASE;
    }

}
