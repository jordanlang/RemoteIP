package src.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by jordan on 30/03/2016.
 */
public class mainFrame extends JFrame {
    public mainFrame() {
        JPanel window = new JPanel();
        JButton btn_admin = new JButton("Administrer un poste distant");
        JButton btn_client = new JButton("Etre administré");
        btn_admin.addActionListener(this::btn_admin_actionPerformed);
        btn_client.addActionListener(this::btn_client_actionPerformed);

        btn_admin.setMaximumSize(new Dimension(250, 40));
        btn_client.setPreferredSize(new Dimension(400, 100));

        GridLayout grid = new GridLayout(3, 1);
        window.setLayout(grid);
        window.add(btn_admin);
        window.add(Box.createRigidArea(new Dimension(0, 10)));
        window.add(btn_client);

        this.setLayout(new BorderLayout());
        this.getContentPane().add(window, BorderLayout.CENTER);
        this.getContentPane().add(Box.createRigidArea(new Dimension(0, 100)), BorderLayout.NORTH);
        this.getContentPane().add(Box.createRigidArea(new Dimension(0, 100)), BorderLayout.SOUTH);
        this.getContentPane().add(Box.createRigidArea(new Dimension(100, 0)), BorderLayout.WEST);
        this.getContentPane().add(Box.createRigidArea(new Dimension(100, 0)), BorderLayout.EAST);
    }

    public void btn_client_actionPerformed(ActionEvent e) {
        Runtime runtime = Runtime.getRuntime();
        if (System.getProperty("os.name").startsWith("Windows")) { //Sytème Windows
            try {
                runtime.exec("start orbd -ORBInitialPort 46293");
            } catch (Exception exp) {
                System.err.println(exp.getMessage());
            }
        } else { //Tous les autres systèmes
            try {
                runtime.exec("orbd -ORBInitialPort 46293&");
            } catch (Exception exp) {
                System.err.println(exp.getMessage());
            }
        }

        mainFrameClient clientFrame = new mainFrameClient();
        clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientFrame.setTitle("Paramètres");
        clientFrame.setLocation(300, 200);
        clientFrame.pack();
        clientFrame.setVisible(true);
        this.setVisible(false);
    }

    public void btn_admin_actionPerformed(ActionEvent e) {
        mainFrameServer serverFrame = new mainFrameServer();

        Runtime runtime = Runtime.getRuntime();
        String cmd = "java ViewerClient -ORBInitialPort 46293 -ORBInitialHost " + serverFrame.getIdentifiant();
        try {
            runtime.exec(cmd);
        } catch (Exception exp) {
            System.err.println(exp.getMessage());
        }

        serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        serverFrame.setTitle("Administration");
        serverFrame.setLocation(300, 200);
        serverFrame.pack();
        serverFrame.setVisible(true);
        this.setVisible(false);
    }
}
