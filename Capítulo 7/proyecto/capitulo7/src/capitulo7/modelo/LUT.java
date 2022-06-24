package capitulo7.modelo;

import java.awt.Color;

public class LUT {

    private static final int BLACK = 0;
    private static final int WHITE = 1;
    public static final int RED = 2;
    public static final int ORANGE = 3;
    public static final int YELLOW = 4;
    public static final int GREEN_1 = 5;
    public static final int GREEN_2 = 6;
    public static final int GREEN_3 = 7;
    public static final int BLUE_1 = 8;
    public static final int BLUE_2 = 9;
    public static final int BLUE_3 = 10;
    public static final int PURPLE = 11;
    public static final int PINK = 12;
    public static final int MAGENTA = 13;

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
    
    /**
     * Dado un color por parámetro decide que color de los 14 coloroes que tenemos es
     * basandonse en sus valores HSV
     * @param color
     * @return 
     */
    public int lookUpColor(Color color) {
        // Given a color, return the index of the closest color in the LUT.
        float[] hsv = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsv);
        float h = hsv[0]*360;
        float s = hsv[1]*100;
        float v = hsv[2]*100;
        int res = -1;
        if (v < 5) {
            res = BLACK;
        } else if ( s < 5 && v > 95) {
            res = WHITE;
        } else if (h >= 345 || h <= RED_VALUE + VARIATION) { // El rojo es un caso especial porque está en el borde de 0 y 360
            res = RED;
        } else if (h >= ORANGE_VALUE - VARIATION && h <= ORANGE_VALUE + VARIATION) {
            res = ORANGE;
        } else if (h >= YELLOW_VALUE - VARIATION && h <= YELLOW_VALUE + VARIATION) {
            res = YELLOW;
        } else if (h >= GREEN_VALUE_1 - VARIATION && h <= GREEN_VALUE_1 + VARIATION) {
            res = GREEN_1;
        } else if (h >= GREEN_VALUE_2 - VARIATION && h <= GREEN_VALUE_2 + VARIATION) {
            res = GREEN_2;
        } else if (h >= GREEN_VALUE_3 - VARIATION && h <= GREEN_VALUE_3 + VARIATION) {
            res = GREEN_3;
        } else if (h >= BLUE_VALUE_1 - VARIATION && h <= BLUE_VALUE_1 + VARIATION) {
            res = BLUE_1;
        } else if (h >= BLUE_VALUE_2 - VARIATION && h <= BLUE_VALUE_2 + VARIATION) {
            res = BLUE_2;
        } else if (h >= BLUE_VALUE_3 - VARIATION && h <= BLUE_VALUE_3 + VARIATION) {
            res = BLUE_3;
        } else if (h >= PURPLE_VALUE - VARIATION && h <= PURPLE_VALUE + VARIATION) {
            res = PURPLE;
        } else if (h >= PINK_VALUE - VARIATION && h <= PINK_VALUE + VARIATION) {
            res = PINK;
        } else if (h >= MAGENTA_VALUE - VARIATION && h <= MAGENTA_VALUE + VARIATION) {
            res = MAGENTA;
        }

        return res;

    }
}
