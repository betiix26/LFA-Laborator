import java.awt.*;
import java.io.Serializable;

import static com.sun.org.apache.xml.internal.utils.LocaleUtility.EMPTY_STRING;

public class Nod implements Serializable {

    public static final Integer[] RADIUS_VALUES = {20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35,
                                                   36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51};
    protected int x;
    protected int y;
    protected int raza;

    protected Color culoare;

    private String text;

    public Nod(int x, int y, Color culoare, int raza, String text) {
        this.x = x;
        this.y = y;
        this.raza = raza;
        this.culoare = culoare;

        setTextNod(text);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setCuloareNod(Color culoare) {
        this.culoare = culoare;
    }

    public void setRazaNod(int raza) {
        if (raza < RADIUS_VALUES[0]) {
            this.raza = RADIUS_VALUES[0];
        } else if (raza > RADIUS_VALUES[RADIUS_VALUES.length - 1]) {
            this.raza = RADIUS_VALUES[RADIUS_VALUES.length - 1];
        } else {
            this.raza = raza;
        }
    }

    public void setTextNod(String text) {
        if (text == null) {
            this.text = EMPTY_STRING;
        } else {
            this.text = text;
        }
    }

    public void drawNod(Graphics graphics) {
        graphics.setColor(culoare);
        graphics.fillOval(x - raza, y - raza, raza + raza, raza + raza);
        graphics.setColor(Color.BLACK);
        graphics.drawOval(x - raza, y - raza, raza + raza, raza + raza);

        FontMetrics fontMetrics = graphics.getFontMetrics();

        int xOfText = x - fontMetrics.stringWidth(text) * 2;
        int yOfText = y - fontMetrics.getHeight() / 2 + fontMetrics.getAscent();

        graphics.drawString(" " + text + "," + raza, xOfText, yOfText);
    }

    public boolean nodSubCursor(int mouseX, int mouseY) {
        int a = x - mouseX;
        int b = y - mouseY;

        return a * a + b * b <= raza * raza;
    }

    public void mutaNod(int distantaX, int distantaY) {
        x = x + distantaX;
        y = y + distantaY;
    }
}
