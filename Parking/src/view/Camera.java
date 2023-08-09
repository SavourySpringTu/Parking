package view;

import java.awt.*;
import javax.swing.*;

import com.github.sarxos.webcam.Webcam;

// Class - Swing Class
public class Camera {
    Webcam webcam;
    public Camera(JLabel label){
        webcam= Webcam.getDefault();
        webcam.setViewSize(new Dimension(640,480));
        webcam.open();
        new Thread(new Runnable() {
            @Override public void run()
            {
                while(true){
                    try {
                        Image image = webcam.getImage();
                        label.setIcon(new ImageIcon(image));
                        Thread.sleep(80);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
        webcam.close();
    }
}
