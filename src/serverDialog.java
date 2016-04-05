import ViewerApp.ViewerHelper;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by jordan on 30/03/2016.
 */
public class serverDialog extends JDialog {
    private static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    private Pattern pattern;
    private Matcher matcher;
    private JTextField mdp;


    public serverDialog() {
        JPanel window = new JPanel();
        JLabel lbl = new JLabel("Identifiant :");
        lbl.setHorizontalAlignment(JLabel.CENTER);
        mdp = new JTextField();
        mdp.setHorizontalAlignment(JTextField.CENTER);
        JButton btn_connexion = new JButton("Se connecter");
        btn_connexion.addActionListener(this::btn_connexion_actionPerformed);

        btn_connexion.setPreferredSize(new Dimension(400, 100));

        GridLayout grid = new GridLayout(3, 1);
        window.setLayout(grid);
        window.add(lbl);
        window.add(mdp);
        window.add(btn_connexion);

        this.setLayout(new BorderLayout());
        this.getContentPane().add(window, BorderLayout.CENTER);
        this.getContentPane().add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.NORTH);
        this.getContentPane().add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.SOUTH);
        this.getContentPane().add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.WEST);
        this.getContentPane().add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.EAST);
    }

    public void btn_connexion_actionPerformed(ActionEvent e) {
        pattern = Pattern.compile(IPADDRESS_PATTERN);
        matcher = pattern.matcher(getId());
        if(!matcher.matches()) {
            JOptionPane.showMessageDialog(this, "Adresse IP non valide", "Erreur", JOptionPane.ERROR_MESSAGE);
        } else {
            this.dispose();
        }

    }

    public String getId() {
        return mdp.getText();
    }
}


