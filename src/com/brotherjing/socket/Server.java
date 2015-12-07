package com.brotherjing.socket;

/**
 * Created by Brotherjing on 2015-12-06.
 */

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {

    public static final int PORT = 13425;

    ReadHandlerThread readHandlerThread;

    private ReadHandlerThread.OnReceiveListener listener;

    public Server(){
    }

    public void setListener(ReadHandlerThread.OnReceiveListener listener){
        this.listener = listener;
    }

    public void init() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("new client!!");
                readHandlerThread = new ReadHandlerThread(client);
                readHandlerThread.setListener(listener);
                //WriteHandlerThread writeHandlerThread = new WriteHandlerThread(client);
                new Thread(new ReadHandlerThread(client)).start();
                //new Thread(new WriteHandlerThread(client)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

class WriteHandlerThread implements Runnable {
    private Socket client;

    public WriteHandlerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        DataOutputStream dos = null;
        BufferedReader br = null;
        try {
            while (true) {
                dos = new DataOutputStream(client.getOutputStream());
                System.out.print("请输入:\t");
                br = new BufferedReader(new InputStreamReader(System.in));
                String send = br.readLine();
                dos.writeUTF(send);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (dos != null) {
                    dos.close();
                }
                if (br != null) {
                    br.close();
                }
                if (client != null) {
                    client = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

