import java.awt.*;
import java.io.Serializable;

import static com.sun.org.apache.xml.internal.utils.LocaleUtility.EMPTY_STRING;

public class Muchie implements Serializable {

    public static final Integer[] VALUES = {1, 2, 3, 4, 5};

    private final Nod nodA;
    private final Nod nodB;

    protected Color culoare;

    private int valoare;

    private String text;

    public Muchie(Nod nodA, Nod nodB, Color culoare, int valoare, String text) {
        this.nodA = nodA;
        this.nodB = nodB;
        this.valoare = valoare;
        this.culoare = culoare;
        this.text = text;
        //setCuloareMuchie(culoare);
    }

    public Nod getNodA() {
        return nodA;
    }

    public Nod getNodB() {
        return nodB;
    }

    public void setValoareMuchie(int valoare) {
        if (valoare < VALUES[0]) {
            this.valoare = VALUES[0];
        } else if (valoare > VALUES[VALUES.length - 1]) {
            this.valoare = VALUES[VALUES.length - 1];
        } else {
            this.valoare = valoare;
        }
    }

    public void setTextMuchie(String text) {
        if (text == null) {
            this.text = EMPTY_STRING;
        } else {
            this.text = text;
        }
    }

    public void setCuloareMuchie(Color culoare) {
        this.culoare = culoare;
    }

    public void drawMuchie(Graphics graphics) {
        int xOfNodA = getNodA().getX();
        int xOfNodB = getNodB().getX();

        int yOfNodA = getNodA().getY();
        int yOfNodB = getNodB().getY();

        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setStroke(new BasicStroke(valoare));
        graphics2D.setColor(culoare);
        graphics2D.drawLine(xOfNodA, yOfNodA, xOfNodB, yOfNodB);
        graphics2D.setStroke(new BasicStroke());
    }

    public boolean muchieSubCursor(int mouseX, int mouseY) {
        if (mouseX < Math.min(nodA.getX(), nodB.getX()) || mouseX > Math.max(nodA.getX(), nodB.getX()) ||
            mouseY < Math.min(nodA.getY(), nodB.getY()) || mouseY > Math.max(nodA.getY(), nodB.getY())) {
            return false;
        }

        int A = nodB.getY() - nodA.getY();
        int B = nodB.getX() - nodA.getX();

        double distanta = Math.abs(A * mouseX - B * mouseY + nodB.getX()
                * nodA.getY() - nodB.getY() * nodA.getX()) / Math.sqrt(A * A + B * B);
        return distanta <= VALUES[4];
    }

    public void mutaMuchie(int distantaX, int distantaY) {
        nodA.mutaNod(distantaX, distantaY);
        nodB.mutaNod(distantaX, distantaY);
    }

    @Override
    public String toString() {
        String culoareHex = "#" + Integer.toHexString(culoare.getRGB()).substring(2).toUpperCase();
        return "Valoare: " + valoare + ", Culoare: " + culoareHex + ", Valoare muchie: " + text;
    }
}
