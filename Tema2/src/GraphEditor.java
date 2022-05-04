import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphEditor extends JFrame implements ActionListener {

    private static final String TITLE = "Editor de grafuri";

    private final GraphPanel graphPanel = new GraphPanel(null);

    private final JMenuBar menuBar = new JMenuBar();

    private final JMenu fileMenu = new JMenu("File");

    private final JMenuItem newGraphMenuItem = new JMenuItem("Graf nou");

    //final Container c = this.getContentPane();

    public GraphEditor() {

        super(TITLE);

        //setBackground(new Color(243, 172, 245));
        //getContentPane().setBackground(Color.red);
        /*this.getContentPane().setBackground(Color.red);
        getContentPane().setBackground(new java.awt.Color(204, 166, 166));*/

        setSize(800, 600);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(graphPanel);

        UIManager.put("OptionPane.messageFont", new Font("Serif", Font.BOLD, 20));
        addActionListeners();

        createMenuBar();
        setVisible(true);
    }

    private void addActionListeners() {
        newGraphMenuItem.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object eventSource = event.getSource();
        //c.setBackground(Color.red);
        if (eventSource == newGraphMenuItem) {
            graphPanel.createGrafNou();
        }
    }

    private void createMenuBar() {
        fileMenu.add(newGraphMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }
}
