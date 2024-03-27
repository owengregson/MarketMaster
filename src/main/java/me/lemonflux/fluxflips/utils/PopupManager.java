package me.lemonflux.fluxflips.utils;

import java.awt.*;
import java.awt.event.*;

public class PopupManager {

    public static void createPopup(String title, String message, String buttonLabel) {
        // Create a frame as the parent of the dialog. It won't be visible.
        Frame parent = new Frame();
        // Ensure the program exits when the popup is closed if this is the only window open.
        parent.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // Create the dialog.
        Dialog dialog = new Dialog(parent, title, true);
        dialog.setLayout(new FlowLayout());

        // Add a label to display the message.
        Label label = new Label(message);
        dialog.add(label);

        // Add a button that closes the dialog.
        Button okButton = new Button(buttonLabel);
        okButton.addActionListener(e -> {
            dialog.setVisible(false);
            dialog.dispose();
            parent.dispose(); // Dispose of the parent frame as well.
        });
        dialog.add(okButton);

        dialog.setSize(300, 150); // Set the size of the dialog.

        // Center the dialog on screen.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - dialog.getWidth()) / 2;
        int y = (screenSize.height - dialog.getHeight()) / 2;
        dialog.setLocation(x, y);

        // Show the dialog.
        dialog.setVisible(true);
    }
}
