package src.View;

import javax.swing.*;
import java.awt.*;
import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * Created by jordan on 30/03/2016.
 */
public class mainFrameClient extends JFrame {
    public mainFrameClient() {
        JPanel window = new JPanel();
        JTextField motDePasse = new JTextField();
        try {
            motDePasse.setText(Inet4Address.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        motDePasse.setFocusable(false);
        motDePasse.setHorizontalAlignment(JTextField.CENTER);
        JButton btn_connexion = new JButton("Initier la connexion");

        btn_connexion.setPreferredSize(new Dimension(400, 100));

        GridLayout grid = new GridLayout(3, 1);
        window.setLayout(grid);
        window.add(motDePasse);
        window.add(Box.createRigidArea(new Dimension(0, 10)));
        window.add(btn_connexion);

        this.setLayout(new BorderLayout());
        this.getContentPane().add(window, BorderLayout.CENTER);
        this.getContentPane().add(Box.createRigidArea(new Dimension(0, 100)), BorderLayout.NORTH);
        this.getContentPane().add(Box.createRigidArea(new Dimension(0, 100)), BorderLayout.SOUTH);
        this.getContentPane().add(Box.createRigidArea(new Dimension(100, 0)), BorderLayout.WEST);
        this.getContentPane().add(Box.createRigidArea(new Dimension(100, 0)), BorderLayout.EAST);
    }
}