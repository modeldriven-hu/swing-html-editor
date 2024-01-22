package hu.modeldriven.swinghtmleditor.command;

import org.kordamp.ikonli.Ikon;

import javax.swing.*;
import java.util.Optional;

public interface Command {

    Action getAction();

    String getActionMapKey();

    String getText();
    boolean isRequestFocusEnabled();

    String getTooltipText();

    Ikon getIcon();

    Optional<Integer> getKeyEvent();

}
