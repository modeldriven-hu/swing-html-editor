package hu.modeldriven.swinghtmleditor.toolbar.command;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class AlignCenterCommand implements Command {

    @Override
    public String getActionMapKey() {
        return "center-justify";
    }

    @Override
    public String getText() {
        return "Left";
    }

    @Override
    public String getTooltipText() {
        return "Center Alignment";
    }

    @Override
    public Ikon getIcon() {
        return MaterialDesign.MDI_FORMAT_ALIGN_CENTER;
    }

}
