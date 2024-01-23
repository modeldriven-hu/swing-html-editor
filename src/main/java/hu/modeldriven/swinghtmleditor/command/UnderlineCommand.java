package hu.modeldriven.swinghtmleditor.command;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.awt.event.KeyEvent;
import java.util.Optional;

public class UnderlineCommand implements Command {

    @Override
    public String getActionMapKey() {
        return "font-underline";
    }

    @Override
    public String getText() {
        return "U";
    }

    @Override
    public String getTooltipText() {
        return "Underline";
    }

    @Override
    public Ikon getIcon() {
        return MaterialDesign.MDI_FORMAT_UNDERLINE;
    }

    @Override
    public Optional<Integer> getKeyEvent() {
        return Optional.of(KeyEvent.VK_U);
    }
}
