package hu.modeldriven.swinghtmleditor.command;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignF;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.Optional;

public class ItalicCommand implements Command {

    @Override
    public Action getAction() {
        return null;
    }

    @Override
    public String getActionMapKey() {
        return "font-italic";
    }

    @Override
    public String getText() {
        return "I";
    }

    @Override
    public boolean isRequestFocusEnabled() {
        return false;
    }

    @Override
    public String getTooltipText() {
        return "Italic";
    }

    @Override
    public Ikon getIcon() {
        return MaterialDesignF.FORMAT_ITALIC;
    }

    @Override
    public Optional<Integer> getKeyEvent() {
        return Optional.of(KeyEvent.VK_I);
    }
}
