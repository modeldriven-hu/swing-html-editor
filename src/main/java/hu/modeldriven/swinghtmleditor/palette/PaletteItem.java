package hu.modeldriven.swinghtmleditor.palette;

import java.awt.Color;

public class PaletteItem {

    private final String name;
    private final Color color;

    public PaletteItem(Color color, String name ){
        this.name = name;
        this.color = color;
    }

    public Color getColor(){
        return color;
    }
    public String getName(){
        return name;
    }

}
