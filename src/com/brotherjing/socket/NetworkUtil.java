package com.brotherjing.socket;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Brotherjing on 2015-12-06.
 */
public class NetworkUtil {

    public static String getIp()throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

}
