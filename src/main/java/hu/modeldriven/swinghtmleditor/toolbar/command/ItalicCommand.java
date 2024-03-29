package hu.modeldriven.swinghtmleditor.toolbar.command;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.awt.event.KeyEvent;
import java.util.Optional;

public class ItalicCommand implements Command {

    @Override
    public String getActionMapKey() {
        return "font-italic";
    }

    @Override
    public String getText() {
        return "I";
    }

    @Override
    public String getTooltipText() {
        return "Italic";
    }

    @Override
    public Ikon getIcon() {
        return MaterialDesign.MDI_FORMAT_ITALIC;
    }

    @Override
    public Optional<Integer> getKeyEvent() {
        return Optional.of(KeyEvent.VK_I);
    }
}
