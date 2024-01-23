package hu.modeldriven.swinghtmleditor.command;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.awt.event.KeyEvent;
import java.util.Optional;

public class RedoCommand implements Command {

    @Override
    public String getActionMapKey() {
        return "Redo";
    }

    @Override
    public String getText() {
        return "Redo";
    }

    @Override
    public String getTooltipText() {
        return "Redo";
    }

    @Override
    public Ikon getIcon() {
        return MaterialDesign.MDI_REDO;
    }

    @Override
    public Optional<Integer> getKeyEvent() {
        return Optional.of(KeyEvent.VK_Y);
    }
}
