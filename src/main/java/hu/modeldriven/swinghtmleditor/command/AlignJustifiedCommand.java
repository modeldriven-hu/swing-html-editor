package hu.modeldriven.swinghtmleditor.command;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import javax.swing.*;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import java.util.Optional;

public class AlignJustifiedCommand implements Command {

    @Override
    public Optional<Action> getAction() {
        return Optional.of(new StyledEditorKit.AlignmentAction("Justify", StyleConstants.ALIGN_JUSTIFIED));
    }

    @Override
    public String getActionMapKey() {
        return "justified";
    }

    @Override
    public String getText() {
        return "Left";
    }

    @Override
    public String getTooltipText() {
        return "Align Justified";
    }

    @Override
    public Ikon getIcon() {
        return MaterialDesign.MDI_FORMAT_ALIGN_JUSTIFY;
    }

}
