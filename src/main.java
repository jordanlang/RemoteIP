import javax.swing.*;

/**
 * Created by jordan on 22/03/2016.
 */
public class main {
    public static mainFrame frame = new mainFrame();

    public static void main(String[] args) {

        frame.setTitle("RemoteIP");
        frame.setLocation(300, 200);
        frame.setResizable(false);
        frame.setModal(true);
        frame.pack();
        frame.setVisible(true);
        if (frame.isadmin())
            mainFrameServer.mainFrameServer();
        else
        {
            mainFrameClient clientFrame = new mainFrameClient();
            clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            clientFrame.setTitle("Paramètres");
            clientFrame.setLocation(300, 200);
            clientFrame.pack();
            clientFrame.setVisible(true);
        }


        // System.out.println(System.getProperty("os.name"));
    }
}
