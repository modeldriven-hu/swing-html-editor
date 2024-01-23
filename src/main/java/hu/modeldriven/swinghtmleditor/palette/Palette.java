package hu.modeldriven.swinghtmleditor.palette;

import java.util.List;

public interface Palette {

    List<PaletteItem> getItems();

    PaletteItem getDefault();

}
