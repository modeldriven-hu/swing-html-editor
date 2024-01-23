package hu.modeldriven.swinghtmleditor.command;

import hu.modeldriven.swinghtmleditor.action.InsertImageAction;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import javax.swing.*;
import java.util.Optional;

public class InsertImageCommand implements Command {

    @Override
    public Optional<Action> getAction() {
        return Optional.of(new InsertImageAction());
    }

    @Override
    public String getActionMapKey() {
        return "insert-img";
    }

    @Override
    public String getText() {
        return "Image";
    }

    @Override
    public String getTooltipText() {
        return "Insert Image";
    }

    @Override
    public Ikon getIcon() {
        return MaterialDesign.MDI_IMAGE;
    }
}
