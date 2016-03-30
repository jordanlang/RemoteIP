package src.View;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jordan on 22/03/2016.
 */
public class mainFrameServer extends JFrame {
    private static String identifiant = "";

    public mainFrameServer() {
        JMenuBar menuBar;
        JMenu menuSession;
        JMenu menuGestion;
        JMenuItem menuItem1;
        JMenuItem menuItem2;
        JMenuItem menuItem3;
        ButtonGroup gestion;
        JRadioButtonMenuItem radioUnique;
        JRadioButtonMenuItem radioMultiple;
        FlowLayout mainLayout;
        JPanel window;
        JPanel activeWindow;

        //Menu
        menuBar = new JMenuBar();
        menuSession = new JMenu("Session");
        menuGestion = new JMenu("Gestion");
        menuItem1 = new JMenuItem("Nouvelle session");
        menuItem2 = new JMenuItem("Déconnexion");
        menuItem3 = new JMenuItem("Tout déconnecter");
        gestion = new ButtonGroup();
        radioUnique = new JRadioButtonMenuItem("Unique");
        radioMultiple = new JRadioButtonMenuItem("Multiple");
        gestion.add(radioUnique);
        gestion.add(radioMultiple);
        radioUnique.setSelected(true);

        menuSession.add(menuItem1);
        menuSession.addSeparator();
        menuSession.add(menuItem2);
        menuSession.add(menuItem3);
        menuGestion.add(radioUnique);
        menuGestion.add(radioMultiple);

        menuBar.add(menuSession);
        menuBar.add(menuGestion);
        this.setJMenuBar(menuBar);


        //Fenetre
        ImageIcon img = new ImageIcon("./cp.png");
        JLabel image = new JLabel(img);
        image.setPreferredSize(new Dimension(img.getIconWidth(), img.getIconHeight()));

        window = new JPanel();
        activeWindow = new JPanel();
        mainLayout = new FlowLayout();
        window.setLayout(mainLayout);
        activeWindow.add(image);
        window.add(activeWindow);
        window.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        setContentPane(window);
        setLocationRelativeTo(getOwner());

        serverDialog dlg = new serverDialog();
        dlg.setTitle("Identification");
        dlg.setResizable(false);
        dlg.pack();
        dlg.setModal(true);
        dlg.setVisible(true);
        setIdentifiant(dlg.getId());
    }

    public static void setIdentifiant(String id) {
        identifiant = id;
    }

    public static String getIdentifiant() {
        return identifiant;
    }

}
