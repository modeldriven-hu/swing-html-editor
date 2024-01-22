package hu.modeldriven.swinghtmleditor.command;

import hu.modeldriven.swinghtmleditor.util.IconHelper;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignL;

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
