package com.example.zomnieapp;

import com.example.zomnieapp.ui_app.ui.MainFrame;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
@EnableScheduling
public class ZomnieAppApplication {

    // TODO: CHECK BEFORE LAUNCH!
    public static String HOST_URL = "https://games-test.datsteam.dev";

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless()) {
            System.err.println("No graphical environment detected. Exiting.");
            System.exit(1);
        }
//        new GameThread().start();
        ConfigurableApplicationContext context = SpringApplication.run(ZomnieAppApplication.class, args);
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = context.getBean(MainFrame.class);
            mainFrame.setVisible(true);
        });
    }
}
