package src.View;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by jordan on 22/03/2016.
 */
public class main {
    public static src.View.mainFrame frame = new src.View.mainFrame();

    public static void main(String[] args) {
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setTitle("RemoteIP");
        frame.setLocation(300, 200);
        frame.setVisible(true);
        frame.pack();

        // System.out.println(System.getProperty("os.name"));
        // System.out.println(Inet4Address.getLocalHost().getHostAddress());

    }
}
