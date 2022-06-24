package capitulo7.modelo.Colores;

import java.awt.Color;

public class LUT {

    // Create the lower raange and upper range arrays in hsv values for the colors
    // (Red green blue orange yellow magenta white and black) have in to account
    // that h goes from 0 to 360 and s and v go from 0 to 1.
    private final int RED = 0;
    private final int GREEN = 1;
    private final int BLUE = 2;
    private final int ORANGE = 3;
    private final int YELLOW = 4;
    private final int MAGENTA = 5;
    private final int WHITE = 6;
    private final int BLACK = 7;

    public static final int RED_VALUE = 0;
    public static final int ORANGE_VALUE = 30;
    public static final int YELLOW_VALUE = 60;
    public static final int GREEN_VALUE_1 = 90;
    public static final int GREEN_VALUE_2 = 120;
    public static final int GREEN_VALUE_3 = 150;
    public static final int BLUE_VALUE_1 = 180;
    public static final int BLUE_VALUE_2 = 210;
    public static final int BLUE_VALUE_3 = 240;
    public static final int PURPLE_VALUE = 270;
    public static final int PINK_VALUE = 300;
    public static final int MAGENTA_VALUE = 330;

    public static final int VARIATION = 15;

    public LUT() {
    }

    public int lookUpColor(Color color) {
        // Given a color, return the index of the closest color in the LUT.
        int[] hsv = new int[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsv);
        int h = hsv[0];
        int s = hsv[1];
        int v = hsv[2];
        int res = -1;

        if (v == 0) {
            res = BLACK;
        } else if (h == 0 && s == 0 && v == 100) {
            res = WHITE;
        } else if (h >= RED_VALUE - VARIATION && h <= RED_VALUE + VARIATION) {
            res = RED;
        } else if (h >= ORANGE_VALUE - VARIATION && h <= ORANGE_VALUE + VARIATION) {
            res = ORANGE;
        } else if (h >= YELLOW_VALUE - VARIATION && h <= YELLOW_VALUE + VARIATION) {
            res = YELLOW;
        } else if (h >= GREEN_VALUE_1 - VARIATION && h <= GREEN_VALUE_1 + VARIATION) {
            res = GREEN;
        } else if (h >= GREEN_VALUE_2 - VARIATION && h <= GREEN_VALUE_2 + VARIATION) {
            res = GREEN;
        } else if (h >= GREEN_VALUE_3 - VARIATION && h <= GREEN_VALUE_3 + VARIATION) {
            res = GREEN;
        } else if (h >= BLUE_VALUE_1 - VARIATION && h <= BLUE_VALUE_1 + VARIATION) {
            res = BLUE;
        } else if (h >= BLUE_VALUE_2 - VARIATION && h <= BLUE_VALUE_2 + VARIATION) {
            res = BLUE;
        } else if (h >= BLUE_VALUE_3 - VARIATION && h <= BLUE_VALUE_3 + VARIATION) {
            res = BLUE;
        } else if (h >= PURPLE_VALUE - VARIATION && h <= PURPLE_VALUE + VARIATION) {
            res = MAGENTA;
        } else if (h >= PINK_VALUE - VARIATION && h <= PINK_VALUE + VARIATION) {
            res = MAGENTA;
        } else if (h >= MAGENTA_VALUE - VARIATION && h <= MAGENTA_VALUE + VARIATION) {
            res = MAGENTA;
        }
        return res;

    }
}
