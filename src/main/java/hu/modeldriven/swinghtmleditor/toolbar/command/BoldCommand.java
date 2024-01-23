package hu.modeldriven.swinghtmleditor.toolbar.command;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.awt.event.KeyEvent;
import java.util.Optional;

public class BoldCommand implements Command {

    @Override
    public String getActionMapKey() {
        return "font-bold";
    }

    @Override
    public String getText() {
        return "B";
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
