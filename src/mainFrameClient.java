import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import ViewerApp.*;

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
        btn_connexion.addActionListener(this::btn_connexion_actionPerformed);

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

    public void btn_connexion_actionPerformed(ActionEvent e) {
        try {
            String args[] = {
                    "-ORBInitialPort",
                    "46293"
            };
            ORB orb = ORB.init(args, null);

            // get reference to rootpoa & activate the POAManager
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // create servant and register it with the ORB
            ViewImpl viewImpl = new ViewImpl();
            viewImpl.setORB(orb);

            // get object reference from the servant
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(viewImpl);
            Viewer href = ViewerHelper.narrow(ref);

            // get the root naming context
            // NameService invokes the name service
            org.omg.CORBA.Object objRef =
                    orb.resolve_initial_references("NameService");
            // Use NamingContextExt which is part of the Interoperable
            // Naming Service (INS) specification.
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // bind the Object Reference in Naming
            String name = "View";
            NameComponent path[] = ncRef.to_name(name);
            ncRef.rebind(path, href);

            System.out.println("viewServer ready and waiting ...");

            // wait for invocations from clients
            while (true) {
                orb.run();
            }
        } catch (Exception ex) {
            System.err.println("ERROR: " + ex);
            ex.printStackTrace(System.out);
        }
        System.out.println("HelloServer Exiting ...");

    }
}