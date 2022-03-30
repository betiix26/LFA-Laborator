
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;
import java.awt.Color;
import javax.swing.*;

@SuppressWarnings("serial")
public class RegexMain extends JPanel {
    protected JTextField textField;		// first text field for regex
    protected JTextField textField2;	// second text field for verify
    protected JButton verifyButton;		// the button to verify the regex
    protected static JFrame frame;

    private static int INSETS_10 = 10;

    public RegexMain() {
        super(new GridBagLayout());
        super.setBackground(new Color(243, 172, 245));

        //super.setBackground(Color.getHSBColor(230,230,250));
        //super.setBackground(Color.getHSBColor(0.3f, 0.7f,0.4f));
        //frame.getContentPane().setBackground(new Color(243, 172, 245));

        //regex
        textField = new JTextField(20);
        JLabel regexLabel = new JLabel("Pattern: ");

        //string to verify
        textField2 = new JTextField(20);
        JLabel textLabel = new JLabel("String: ");

        //button for verify
        verifyButton = new JButton("Verify");
        verifyButton.setForeground(new Color(230,230,250));
        verifyButton.setBackground(new Color(211, 36, 96));
        //frame.getContentPane().setBackground(new java.awt.Color(204, 166, 166));
        //verifyButton.setBounds(163, 207, 89, 23);
        verifyButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog d = new JDialog(frame, "Results", true);
                    d.setLocationRelativeTo(frame);
                boolean isRegexInvalid = false;
                Pattern pattern = null;
                try {
                    pattern = Pattern.compile(textField.getText());
                } catch (Exception x){
                  isRegexInvalid = true;
                }
                if (isRegexInvalid){
                    d.add(new JLabel("Invalid pattern!"));
                } else {
                    if (pattern.matcher(textField2.getText()).matches()) {
                        d.add(new JLabel("Match found!"));
                    } else {
                        d.add(new JLabel("No match found."));
                    }
                }
                d.pack();
                d.setVisible(true);
            }
        });

        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        //frame.getContentPane().setBackground(new Color(243, 172, 245));

        //this.getContentPane().setBackground(Color.white);
        //c.gridwidth = GridBagConstraints.VERTICAL;
        //c.gridwidth = GridBagConstraints.VERTICAL;
        //contentPane.setLayout(new GridBagLayout());

        c.insets = new Insets(INSETS_10, INSETS_10, INSETS_10, INSETS_10);
        c.weightx = 0.5;
        c.weighty = 0.5;

        c.gridx = 0;
        c.gridy = 0;

        add(regexLabel, c);
        c.gridx++;
        add(textField, c);

        c.gridx = 0;
        c.gridy++;
        add(textLabel, c);
        c.gridx++;
        add(textField2, c);

        c.gridx = 0;
        c.gridy++;
        add(verifyButton,c);
    }

    private static void createAndShowGUI() {
        // create and set up the window
        frame = new JFrame("RegexApp");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(350,350);

        // add contents to the window
        frame.add(new RegexMain());

        //frame.getContentPane().setBackground(new Color(243, 172, 245));
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.getContentPane().setLayout(null);
        //frame.getContentPane().setBackground(new Color(243, 172, 245));

        // display the window
        //frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}