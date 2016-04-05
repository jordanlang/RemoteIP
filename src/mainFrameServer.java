import ViewerApp.*;
import org.omg.CosNaming.*;

import org.omg.CORBA.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.imageio.ImageIO;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

import java.io.BufferedInputStream;




/**
 * Created by jordan on 22/03/2016.
 */
public class mainFrameServer {
    private static String identifiant = "";
    static Viewer viewImpl;


    public static void mainFrameServer() {


        serverDialog dlg = new serverDialog();
        dlg.setTitle("Identification");
        dlg.setResizable(false);
        dlg.pack();
        dlg.setModal(true);
        dlg.setVisible(true);
        setIdentifiant(dlg.getId());

        try {
            String args[] = {
                    "-ORBInitialPort",
                    "46293",
                    "-ORBInitialHost",
                    getIdentifiant()
            };
            // create and initialize the ORB
            ORB orb = ORB.init(args, null);

            // get the root naming context
            org.omg.CORBA.Object objRef =
                    orb.resolve_initial_references("NameService");
            // Use NamingContextExt instead of NamingContext. This is
            // part of the Interoperable naming Service.
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // resolve the Object Reference in Naming
            String name = "View";
            viewImpl = ViewerHelper.narrow(ncRef.resolve_str(name));

            System.out.println("Obtained a handle on server object: " + viewImpl);
            JFrame fr = new JFrame();
            fr.getContentPane().setLayout(new FlowLayout());
            fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            fr.setTitle("Administration");
            fr.setLocation(100, 100);

            Espion espion = new Espion(viewImpl,fr);
            while(true)
            {
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
                fr.setJMenuBar(menuBar);


                byte img[] = viewImpl.view();
                InputStream in = new ByteArrayInputStream(img);
                BufferedInputStream bis = new BufferedInputStream(in);

                // lire l'image.
                BufferedImage imageRecup = ImageIO.read(bis);

                ImageIcon im = new ImageIcon(imageRecup);
                JLabel imageLabel = new JLabel(im);



                fr.getContentPane().removeAll();
                fr.getContentPane().add(imageLabel);
                if(espion.isMouseIn())
                {
                    int x = fr.getMousePosition().x-8;
                    int y = fr.getMousePosition().y-31-20;
                    viewImpl.mouseMove(x, y);
                }
                fr.pack();

                fr.setVisible(true);

            }



        } catch (Exception ex) {
            System.out.println("ERROR : " + ex);
            ex.printStackTrace(System.out);
        }


    }



    public static void setIdentifiant(String id) {
        identifiant = id;
    }

    public static String getIdentifiant() {
        return identifiant;
    }
}

class Espion extends java.applet.Applet implements MouseListener,KeyListener {

    static Viewer viewImpl;
    JFrame f;
    private boolean mouseIn;
    public Espion(Viewer v, JFrame fr) {
        super();
        viewImpl =v;
        mouseIn=false;
        f = fr;
        for(int j=0;j<f.getComponentCount();j++)
        {
            f.getComponent(j).addMouseListener(this);
            f.getComponent(j).addKeyListener(this);
        }

    }

    public void init() {
        addKeyListener(this);
    }

    public void start() {
        requestFocus();
    }



    public void mouseClicked(MouseEvent e) {

        System.out.println("click!");
        if(e.getButton()==MouseEvent.BUTTON1)
            viewImpl.mouseClick();
        else if(e.getButton()==MouseEvent.BUTTON3)
            viewImpl.mouseRightClick();
    }

    public void mousePressed(MouseEvent e) {
        System.out.println("Press!");
        if(e.getButton()==MouseEvent.BUTTON1)
            viewImpl.mousePress();
        else if(e.getButton()==MouseEvent.BUTTON3)
            viewImpl.mouseRightPress();

    }

    public void mouseReleased(MouseEvent e) {
        System.out.println("Release!");
        if(e.getButton()==MouseEvent.BUTTON1)
            viewImpl.mouseRelease();
        else if(e.getButton()==MouseEvent.BUTTON3)
            viewImpl.mouseRightRelease();
    }

    public void mouseEntered(MouseEvent e) {
        mouseIn=true;
        f.setCursor((Cursor) Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(32,32,BufferedImage.TRANSLUCENT),new Point(0,0),"curseurInvisible"));
    }

    public void mouseExited(MouseEvent e) {
        mouseIn=false;
    }




    public void keyPressed(KeyEvent e) {
        System.out.println("key Press!");
        viewImpl.keyPress(e.getKeyCode());

    }


    public void keyReleased(KeyEvent e) {
        System.out.println("key release!");
        viewImpl.keyRelease(e.getKeyCode());
    }

    public void keyTyped(KeyEvent e) {
        System.out.println("key click!");
        viewImpl.keyClick(e.getKeyCode());
    }

    public boolean isMouseIn()
    {
        return this.mouseIn;
    }
}





