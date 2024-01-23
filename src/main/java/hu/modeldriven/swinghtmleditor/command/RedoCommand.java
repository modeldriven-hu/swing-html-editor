package hu.modeldriven.swinghtmleditor.command;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import javax.swing.*;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Optional;

public class RedoCommand implements Command {

    private final UndoManager undoManager;

    public RedoCommand(UndoManager undoManager) {
        this.undoManager = undoManager;
    }

    @Override
    public Optional<Action> getAction() {
        return Optional.of(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (undoManager.canRedo()) {
                        undoManager.redo();
                    }
                } catch (CannotUndoException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    @Override
    public String getActionMapKey() {
        return "redo";
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
