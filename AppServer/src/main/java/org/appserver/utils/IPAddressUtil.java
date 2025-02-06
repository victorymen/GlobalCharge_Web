package org.appserver.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class IPAddressUtil {
    public static void main(String[] args) {

        try {
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println("本地IP地址: " + localhost.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("无法获取本地IP地址");
        }



        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    System.out.println("网络接口: " + networkInterface.getName() + ", IP地址: " + inetAddress.getHostAddress());
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
