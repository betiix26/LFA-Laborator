import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Graf implements Serializable {

    private List<Nod> noduri;

    private List<Muchie> muchii;

    public Graf() {
        setNoduri(new ArrayList<>());
        setMuchii(new ArrayList<>());
    }

    public List<Nod> getNoduri() {
        return noduri;
    }

    public void setNoduri(List<Nod> noduri) {
        this.noduri = noduri;
    }

    public List<Muchie> getMuchii() {
        return muchii;
    }

    public void setMuchii(List<Muchie> muchii) {
        this.muchii = muchii;
    }

    public void draw(Graphics graphics) {
        for (Muchie muchie : getMuchii()) {
            muchie.drawMuchie(graphics);
        }

        for (Nod node : getNoduri()) {
            node.drawNod(graphics);
        }
    }

    public void addNod(Nod node) {
        noduri.add(node);
    }

    public void addMuchie(Muchie muchieNoua) {
        for (Muchie muchie : muchii) {
            if (muchieNoua.equals(muchie)) {
                return;
            }
        }
        muchii.add(muchieNoua);
    }

    public Nod findNodSubCursor(int mouseX, int mouseY) {
        for (Nod nod : noduri) {
            if (nod.nodSubCursor(mouseX, mouseY)) {
                return nod;
            }
        }
        return null;
    }

    public Muchie findMuchieSubCursor(int mouseX, int mouseY) {
        for (Muchie muchie : muchii) {
            if (muchie.muchieSubCursor(mouseX, mouseY)) {
                return muchie;
            }
        }
        return null;
    }

    public void removeNod(Nod nodSubCursor) {
        removeMuchiiAtasate(nodSubCursor);
        noduri.remove(nodSubCursor);
    }

    protected void removeMuchiiAtasate(Nod nodSubCursor) {
        muchii.removeIf(muchie -> muchie.getNodA().equals(nodSubCursor) || muchie.getNodB().equals(nodSubCursor));
    }

    public void removeMuchie(Muchie muchieSubCursor) {
        muchii.remove(muchieSubCursor);
    }

    public void mutaGraf(int distantaX, int distantaY) {
        for (Nod nod : noduri) {
            nod.mutaNod(distantaX, distantaY);
        }
    }
}
