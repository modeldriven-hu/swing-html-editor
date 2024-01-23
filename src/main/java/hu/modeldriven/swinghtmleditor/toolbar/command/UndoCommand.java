package hu.modeldriven.swinghtmleditor.toolbar.command;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import javax.swing.*;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Optional;

public class UndoCommand implements Command {

    private final UndoManager undoManager;

    public UndoCommand(UndoManager undoManager) {
        this.undoManager = undoManager;
    }

    @Override
    public Optional<Action> getAction() {
        return Optional.of(new AbstractAction() {

            private static final long serialVersionUID = 42L;

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (undoManager.canUndo()) {
                        undoManager.undo();
                    }
                } catch (CannotUndoException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    @Override
    public String getActionMapKey() {
        return "undo";
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
