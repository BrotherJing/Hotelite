package com.brotherjing.view;

import com.brotherjing.controller.CustomerController;
import com.brotherjing.controller.RoomController;
import com.brotherjing.controller.RoomTypeController;
import com.brotherjing.controller.StaffController;
import com.brotherjing.global.CustomerRequestQueue;
import com.brotherjing.socket.Server;
import com.brotherjing.view.pages.RoomPage;
import com.brotherjing.view.pages.ServicePage;
import com.brotherjing.view.pages.ShiftPage;
import jp.co.worksap.intern.util.Preference;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.IOException;

public class Hotel extends JFrame {

    private static boolean firstUse = true;
    private static final Log logger = LogFactory.getLog(Hotel.class);
    private String[] tabs = {"ROOM","SERVICE","SHIFT","WAREHOUSE"};
    private JTabbedPane pane = new JTabbedPane();

    public static Server tcpServer;

    public Hotel(){
        pane.addTab(tabs[0],new RoomPage(this));
        pane.addTab(tabs[1],new ServicePage(this));
        pane.addTab(tabs[2],new ShiftPage(this));
        /*pane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                System.out.println(pane.getSelectedIndex());
            }
        });*/
        add(pane);
    }

    public static void loadFromCSV()throws IOException{
        RoomController.saveToDatabase();
        RoomTypeController.saveToDatabase();
        CustomerController.saveToDatabase();
        StaffController.saveToDatabase();
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        try {
            Preference preference = new Preference();
            firstUse = preference.get(Preference.KEY_INT_FIRST_USE).getValue().equals("1");
            logger.info(preference.get(Preference.KEY_INT_FIRST_USE).getValue());
            if (firstUse) {
                preference.set(Preference.KEY_INT_FIRST_USE, "0");
                preference.save();
                loadFromCSV();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        CustomerRequestQueue.init();

        tcpServer = new Server();
        runServer();

        SwingConsole.run(new Hotel(), 800, 500);

    }

    private static void runServer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                tcpServer.init();
            }
        }).start();
    }

}
