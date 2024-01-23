package hu.modeldriven.swinghtmleditor.command;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.awt.event.KeyEvent;
import java.util.Optional;

public class UndoCommand implements Command {

    @Override
    public String getActionMapKey() {
        return "Undo";
    }

    @Override
    public String getText() {
        return "Undo";
    }

    @Override
    public String getTooltipText() {
        return "Undo";
    }

    @Override
    public Ikon getIcon() {
        return MaterialDesign.MDI_UNDO;
    }

    @Override
    public Optional<Integer> getKeyEvent() {
        return Optional.of(KeyEvent.VK_Z);
    }
}
