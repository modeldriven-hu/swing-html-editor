package hu.modeldriven.swinghtmleditor.toolbar.command;

import org.kordamp.ikonli.Ikon;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Optional;

public interface Command {

    default Optional<ActionListener> getActionListener() {
        return Optional.empty();
    }

    default Optional<Action> getAction() {
        return Optional.empty();
    }

    String getActionMapKey();

    String getText();

    default boolean isRequestFocusEnabled() {
        return false;
    }

    String getTooltipText();

    Ikon getIcon();

    default Optional<Integer> getKeyEvent() {
        return Optional.empty();
    }

}
