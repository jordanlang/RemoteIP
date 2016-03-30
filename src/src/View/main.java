package src.View;

import javax.swing.*;

/**
 * Created by jordan on 22/03/2016.
 */
public class main {
    public static mainFrame frame = new mainFrame();

    public static void main(String[] args) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("RemoteIP");
        frame.setLocation(300, 200);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

        // System.out.println(System.getProperty("os.name"));
    }
}
