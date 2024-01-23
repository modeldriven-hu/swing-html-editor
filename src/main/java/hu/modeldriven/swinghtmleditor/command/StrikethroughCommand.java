package hu.modeldriven.swinghtmleditor.command;

import hu.modeldriven.swinghtmleditor.command.action.StrikethroughAction;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.Optional;

public class StrikethroughCommand implements Command {

    @Override
    public Optional<Action> getAction() {
        return Optional.of(new StrikethroughAction());
    }

    @Override
    public String getActionMapKey() {
        return "font-strike";
    }

    @Override
    public String getText() {
        return "S";
    }

    @Override
    public String getTooltipText() {
        return "Strikethrough";
    }

    @Override
    public Ikon getIcon() {
        return MaterialDesign.MDI_FORMAT_STRIKETHROUGH;
    }

    @Override
    public Optional<Integer> getKeyEvent() {
        return Optional.of(KeyEvent.VK_S);
    }
}
