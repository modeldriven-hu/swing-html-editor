package hu.modeldriven.swinghtmleditor.command;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.Optional;

public class BoldCommand implements Command {
    @Override
    public Action getAction() {
        return null;
    }

    @Override
    public String getActionMapKey() {
        return "font-bold";
    }

    @Override
    public String getText() {
        return "B";
    }

    @Override
    public boolean isRequestFocusEnabled() {
        return false;
    }

    @Override
    public String getTooltipText() {
        return "Bold";
    }

    @Override
    public Ikon getIcon() {
        return MaterialDesign.MDI_FORMAT_BOLD;
    }

    @Override
    public Optional<Integer> getKeyEvent() {
        return Optional.of(KeyEvent.VK_B);
    }
}
