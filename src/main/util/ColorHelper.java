package main.util;

import main.position.Position;

import java.awt.*;

import static main.util.Constants.*;

public class ColorHelper {
    public static Color getTraversalColor(Position pos) {
        int distance = ROWS - pos.getI() + COLUMNS - pos.getJ();
        double ratio = (double)(distance+1)/(ROWS+COLUMNS+1);

        int red = (int)Math.abs((ratio * TRAVERSAL_COLOR_RANGE[0].getRed()) + ((1 - ratio) * TRAVERSAL_COLOR_RANGE[1].getRed()));
        int green = (int)Math.abs((ratio * TRAVERSAL_COLOR_RANGE[0].getGreen()) + ((1 - ratio) * TRAVERSAL_COLOR_RANGE[1].getGreen()));
        int blue = (int)Math.abs((ratio * TRAVERSAL_COLOR_RANGE[0].getBlue()) + ((1 - ratio) * TRAVERSAL_COLOR_RANGE[1].getBlue()));

        return new Color(red, green, blue);
    }
}
