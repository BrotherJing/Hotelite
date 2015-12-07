package com.brotherjing.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Brotherjing on 2015-12-06.
*/

public class ReadHandlerThread implements Runnable {
    private Socket client;

    public ReadHandlerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        DataInputStream dis = null;
        try {
            while (true) {
                dis = new DataInputStream(client.getInputStream());
                String receiver = dis.readUTF();
                System.out.println("receive:" + receiver);
                if(listener!=null)
                    listener.onReceive(receiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (dis != null) {
                    dis.close();
                }
                if (client != null) {
                    client = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public interface OnReceiveListener{
        public void onReceive(String msg);
    }

    private OnReceiveListener listener;

    public void setListener(OnReceiveListener listener){this.listener =listener;}
}
