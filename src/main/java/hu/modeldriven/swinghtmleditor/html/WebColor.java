package hu.modeldriven.swinghtmleditor.html;

import java.awt.Color;

/**
 * Standard Web Colors:
 * aqua, black, blue, fuchsia, gray, green, lime, maroon, navy, olive, purple,
 * red, silver, teal, white, and yellow
 */
public class WebColor extends Color {
    private static final long serialVersionUID = 42L;
    public static final WebColor W_WHITE = new WebColor(0xFF, 0xFF, 0xFF, "white");
    public static final WebColor W_SILVER = new WebColor(0xC0, 0xC0, 0xC0, "silver");
    public static final WebColor W_GRAY = new WebColor(0x80, 0x80, 0x80, "gray");
    public static final WebColor W_BLACK = new WebColor(0x00, 0x00, 0x00, "black");
    public static final WebColor W_RED = new WebColor(0xFF, 0x00, 0x00, "red");
    public static final WebColor W_MAROON = new WebColor(0x80, 0x00, 0x00, "maroon");
    public static final WebColor W_YELLOW = new WebColor(0xFF, 0xFF, 0x00, "yellow");
    public static final WebColor W_OLIVE = new WebColor(0x80, 0x80, 0x00, "olive");
    public static final WebColor W_LIME = new WebColor(0x00, 0xFF, 0x00, "lime");
    public static final WebColor W_GREEN = new WebColor(0x00, 0x80, 0x00, "green");
    public static final WebColor W_AQUA = new WebColor(0x00, 0xFF, 0xFF, "aqua");
    public static final WebColor W_TEAL = new WebColor(0x00, 0x80, 0x80, "teal");
    public static final WebColor W_BLUE = new WebColor(0x00, 0x00, 0xFF, "blue");
    public static final WebColor W_NAVY = new WebColor(0x00, 0x00, 0x80, "navy");
    public static final WebColor W_FUCHSIA = new WebColor(0xFF, 0x00, 0xFF, "fuchsia");
    public static final WebColor W_PURPLE = new WebColor(0x80, 0x00, 0x80, "purple");

    private final String name;

    public WebColor(final int r, final int g, final int b, final String name) {
        super(r, g, b);
        this.name = name;
    }

    public String getWebName() {
        return name;
    }
}
