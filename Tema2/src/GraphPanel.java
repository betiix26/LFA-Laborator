import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

public class GraphPanel extends JPanel implements MouseListener, MouseMotionListener {

    private Graf graf;
    private boolean mouseClickStanga = false;

    private int mouseX;
    private int mouseY;

    private Nod nodSubCursor;
    private Muchie muchieSubCursor;

    private boolean nodB_ales = false;

    private Nod muchieNoua_nodA;

    /*private double zoomFactor = 1;
    private double prevZoomFactor = 1;
    private boolean zoomer;
    private double xOffset = 0;
    private double yOffset = 0;
    private final BufferedImage image=null;*/
    /*Path2D.Double sineWave;
    AffineTransform at = new AffineTransform();
    double scale = 1.0;
    double scaleInc = 0.01;*/

    @Override
    public void mouseDragged(MouseEvent event) {
        if (mouseClickStanga) {
            mutaGraf_drag(event.getX(), event.getY());
        } else {
            setMouseCursor(event);
        }
    }

    /*@Override*/
    /*public void mouseWheelMoved(MouseWheelEvent e) {
        zoomer = true;

        //Zoom in
        if (e.getWheelRotation() < 0) {
            zoomFactor *= 1.1;
            repaint();
        }
        //Zoom out
        if (e.getWheelRotation() > 0) {
            zoomFactor /= 1.1;
            repaint();
        }
    }*/
    @Override
    public void mouseMoved(MouseEvent event) {
        setMouseCursor(event);
    }

    @Override
    public void mouseClicked(MouseEvent event) {
    }

    @Override
    public void mouseEntered(MouseEvent event) {
    }

    @Override
    public void mouseExited(MouseEvent event) {
    }

    @Override
    public void mousePressed(MouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON1) {
            mouseClickStanga = true;
        }
        setMouseCursor(event);
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON1) {
            mouseClickStanga = false;
            finalizeAddMuchie();
        }

        if (event.getButton() == MouseEvent.BUTTON3) {
            nodB_ales = false;
            if (nodSubCursor != null) {
                createMeniuPopupNod(event, nodSubCursor);
            } else if (muchieSubCursor != null) {
                createMenuPopupMuchie(event, muchieSubCursor);
            } else {
                createMeniuPopup(event);
            }
        }
        setMouseCursor(event);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if (graf != null) {
            graf.draw(graphics);
        }
        /*Graphics2D g2 = (Graphics2D) graphics;
        if (zoomer) {
            AffineTransform at = new AffineTransform();

            double xRel = MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX();
            double yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY();

            double zoomDiv = zoomFactor / prevZoomFactor;

            xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel;
            yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel;

            at.translate(xOffset, yOffset);
            at.scale(zoomFactor, zoomFactor);
            prevZoomFactor = zoomFactor;
            g2.transform(at);
            zoomer = false;*/

        /*g2.drawImage(image, 0, 0, this);*/
    }
    /*private void initComponent() {
        addMouseWheelListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);
    }*/

    public GraphPanel(Graf gr) {
        if (gr == null) {
            graf = new Graf();
        } else {
            setGraf(gr);
        }
        //addMouseWheelListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);

        /*initComponent();*/
        setFocusable(true);
        requestFocus();
    }

    public void setGraf(Graf graf) {
        if (graf == null) {
            this.graf = new Graf();
        } else {
            this.graf = graf;
        }
    }

    public void createGrafNou() {
        setGraf(new Graf());
        repaint();
    }

    private void createMeniuPopup(MouseEvent event) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem newNodeMenuItem = new JMenuItem("Nod nou");
        popupMenu.add(newNodeMenuItem);
        newNodeMenuItem.addActionListener((action) -> createNodNou(event.getX(), event.getY()));
        popupMenu.show(event.getComponent(), event.getX(), event.getY());
    }

    private void createMeniuPopupNod(MouseEvent event, Nod node) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem removeNodeMenuItem = new JMenuItem("Elimina nod");
        popupMenu.add(removeNodeMenuItem);
        removeNodeMenuItem.addActionListener((action) -> removeNod(node));

        popupMenu.addSeparator();

        JMenuItem addEdgeMenuItem = new JMenuItem("Adauga muchie");
        popupMenu.add(addEdgeMenuItem);
        addEdgeMenuItem.addActionListener((action) -> initializeAddMuchie(node));

        if (nodSubCursor != null) {
            popupMenu.addSeparator();

            JMenuItem changeNodeRadiusMenuItem = new JMenuItem("Schimba dimensiunea nodului");
            popupMenu.add(changeNodeRadiusMenuItem);
            changeNodeRadiusMenuItem.addActionListener((action) -> changeRazaNod(node));

            JMenuItem changeTextMenuItem = new JMenuItem("Schimba textul nodului");
            popupMenu.add(changeTextMenuItem);
            changeTextMenuItem.addActionListener((action) -> changeTextNod(node));

            JMenuItem changeNodeColor = new JMenuItem("Schimba culoarea nodului");
            popupMenu.add(changeNodeColor);
            changeNodeColor.addActionListener((action) -> changeCuloareNod(node));
        }
        popupMenu.show(event.getComponent(), event.getX(), event.getY());
    }


    private void createMenuPopupMuchie(MouseEvent event, Muchie muchie) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem removeEdgeMenuItem = new JMenuItem("Elimina muchie");
        popupMenu.add(removeEdgeMenuItem);
        removeEdgeMenuItem.addActionListener((action) -> removeMuchie(muchie));

        if (muchie != null) {
            popupMenu.addSeparator();

            JMenuItem changeEdgeStrokeMenuItem = new JMenuItem("Schimba dimensiunea muchiei");
            popupMenu.add(changeEdgeStrokeMenuItem);
            changeEdgeStrokeMenuItem.addActionListener((action) -> changeDimensiuneMuchie(muchie));

            JMenuItem changeEdgeTextMenuItem = new JMenuItem("Schimba textul muchiei");
            popupMenu.add(changeEdgeTextMenuItem);
            changeEdgeTextMenuItem.addActionListener((action) -> changeTextMuchie(muchie));

            JMenuItem changeEdgeColorMenuItem = new JMenuItem("Schimba culoarea muchiei");
            popupMenu.add(changeEdgeColorMenuItem);
            changeEdgeColorMenuItem.addActionListener((action) -> changeCuloareMuchie(muchie));
        }
        popupMenu.show(event.getComponent(), event.getX(), event.getY());
    }

    public void setMouseCursor(MouseEvent event) {
        if (event != null) {
            nodSubCursor = graf.findNodSubCursor(event.getX(), event.getY());
            if (nodSubCursor == null) {
                muchieSubCursor = graf.findMuchieSubCursor(event.getX(), event.getY());
            }
            mouseX = event.getX();
            mouseY = event.getY();
        }

        int mouseCursor;
        if (nodSubCursor != null) {
            mouseCursor = Cursor.HAND_CURSOR;
        } else if (muchieSubCursor != null) {
            mouseCursor = Cursor.CROSSHAIR_CURSOR;
            if (event != null) {
                muchieSubCursor = graf.findMuchieSubCursor(event.getX(), event.getY());
            }
            setToolTipText(muchieSubCursor.toString());
        } else if (nodB_ales) {
            mouseCursor = Cursor.WAIT_CURSOR;
        } else if (mouseClickStanga) {
            mouseCursor = Cursor.MOVE_CURSOR;
        } else {
            mouseCursor = Cursor.DEFAULT_CURSOR;
        }
        setCursor(Cursor.getPredefinedCursor(mouseCursor));
    }

    private void mutaGraf_drag(int mouseX, int mouseY) {
        int dragX = mouseX - this.mouseX;
        int dragY = mouseY - this.mouseY;

        if (nodSubCursor != null) {
            nodSubCursor.mutaNod(dragX, dragY);
        } else if (muchieSubCursor != null) {
            muchieSubCursor.mutaMuchie(dragX, dragY);
        } else {
            graf.mutaGraf(dragX, dragY);
        }

        this.mouseX = mouseX;
        this.mouseY = mouseY;
        repaint();
    }

    private void createNodNou(int mouseX, int mouseY) {
        Color culoare = JColorChooser.showDialog(this, "Alege culoarea", Color.WHITE);
        int raza = (Integer) JOptionPane.showInputDialog(this, "Alege dimensiunea", "Nod nou",
                JOptionPane.PLAIN_MESSAGE, null, Nod.RADIUS_VALUES, Nod.RADIUS_VALUES[0]);
        String text = JOptionPane.showInputDialog(this, "Input text:", "Nod nou",
                JOptionPane.QUESTION_MESSAGE);
        graf.addNod(new Nod(mouseX, mouseY, culoare, raza, text));
        repaint();
    }

    private void removeNod(Nod nod) {
        graf.removeNod(nod);
        repaint();
    }

    private void initializeAddMuchie(Nod nod) {
        if (nodSubCursor != null) {
            muchieNoua_nodA = nod;
            nodB_ales = true;
            setMouseCursor(null);
        }
    }

    private void finalizeAddMuchie() {
        if (nodB_ales) {
            if (nodSubCursor != null) {
                if (nodSubCursor.equals(muchieNoua_nodA)) {
                    JOptionPane.showMessageDialog(this, "Alege un nod diferit!", "Eroare!",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        Nod newEdgeNodeB = nodSubCursor;
                        Color culoare = JColorChooser.showDialog(this, "Alege culoarea", Color.BLACK);
                        int dimension = (Integer) JOptionPane.showInputDialog(this, "Alege dimensiunea",
                                "Muchie noua", JOptionPane.PLAIN_MESSAGE, null, Muchie.VALUES, Muchie.VALUES[0]);
                        String text = JOptionPane.showInputDialog(this, "Input text:", "Muchie noua",
                                JOptionPane.QUESTION_MESSAGE);
                        graf.addMuchie(new Muchie(muchieNoua_nodA, newEdgeNodeB, culoare, dimension, text));
                        repaint();
                    } catch (NullPointerException exception) {
                        JOptionPane.showMessageDialog(this, "Operatiune anulata.", "Anulat",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
            nodB_ales = false;
        }
    }

    private void removeMuchie(Muchie muchie) {
        graf.removeMuchie(muchie);
        repaint();
    }

    private void changeRazaNod(Nod nod) {
        try {
            int raza = (Integer) JOptionPane.showInputDialog(this, "Alege raza:",
                    "Editeaza nod", JOptionPane.QUESTION_MESSAGE, null, Nod.RADIUS_VALUES, Nod.RADIUS_VALUES[0]);
            nod.setRazaNod(raza);
            repaint();
        } catch (ClassCastException exception) {
            JOptionPane.showMessageDialog(this, "Acest nod nu poate avea o raza diferita.",
                    "Eroare!", JOptionPane.ERROR_MESSAGE);
        } catch (NullPointerException exception) {
            JOptionPane.showMessageDialog(this, "Operatiune anulata.", "Anulat.",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void changeTextNod(Nod nod) {
        String text = JOptionPane.showInputDialog(this, "Input text:", "Editeaza nod",
                JOptionPane.QUESTION_MESSAGE);
        try {
            nod.setTextNod(text);
            repaint();
        } catch (NullPointerException exception) {
            JOptionPane.showMessageDialog(this, "Operatiune anulata.", "Anulat.",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void changeCuloareNod(Nod nod) {
        Color culoare = JColorChooser.showDialog(this, "Alege culoarea", Color.BLACK);
        try {
            nod.setCuloareNod(culoare);
            repaint();
        } catch (NullPointerException exception) {
            JOptionPane.showMessageDialog(this, "Operatiune anulata.", "Anulat.",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void changeDimensiuneMuchie(Muchie muchie) {
        try {
            int dimensiune = (Integer) JOptionPane.showInputDialog(this, "Alege valoarea",
                    "Editeaza muchie", JOptionPane.PLAIN_MESSAGE, null, Muchie.VALUES, Muchie.VALUES[0]);
            muchie.setValoareMuchie(dimensiune);
            repaint();
        } catch (ClassCastException exception) {
            JOptionPane.showMessageDialog(this, "Aceasta muchie nu poate avea o valoare diferita.",
                    "Eroare!", JOptionPane.INFORMATION_MESSAGE);
        } catch (NullPointerException exception) {
            JOptionPane.showMessageDialog(this, "Operatiune anulata.", "Anulat.",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void changeTextMuchie(Muchie muchie) {
        try {
            String text = JOptionPane.showInputDialog(this, "Input text:", "Editeaza latura",
                    JOptionPane.QUESTION_MESSAGE);
            muchie.setTextMuchie(text);
            repaint();
        } catch (ClassCastException exception) {
            JOptionPane.showMessageDialog(this, "Aceasta latura nu poate avea text diferit.",
                    "Eroare!", JOptionPane.INFORMATION_MESSAGE);
        } catch (NullPointerException exception) {
            JOptionPane.showMessageDialog(this, "Operatiune anulata.", "Anulat.",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void changeCuloareMuchie(Muchie muchie) {
        try {
            Color culoare = JColorChooser.showDialog(this, "Alege culoarea", Color.BLACK);
            muchie.setCuloareMuchie(culoare);
            repaint();
        } catch (ClassCastException exception) {
            JOptionPane.showMessageDialog(this, "Aceasta muchie nu poate avea o culoare diferita.",
                    "Eroare!", JOptionPane.INFORMATION_MESSAGE);
        } catch (NullPointerException exception) {
            JOptionPane.showMessageDialog(this, "Operatiune anulata.", "Anulat.",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
