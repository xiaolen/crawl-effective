package com.crawleffective.crawleffective.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public class MyClass {

//    public static String ParseStream(InputStream stream) {
//        StringBuilder builder = new StringBuilder("");
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
//            String strtmp;
//            try {
//                strtmp = reader.readLine();
//                while (null != strtmp) {
//                    builder.append(strtmp);
//                    builder.append("\n");
//                    strtmp = reader.readLine();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return builder.toString();
//    }
//
//    public static void main(String[] args) {
//        System.setProperty("http.proxyHost", "localhost");
//        System.setProperty("http.proxyPort", "8888");
//        System.setProperty("https.proxyHost", "localhost");
//        System.setProperty("https.proxyPort", "8888");
//        try {
//            URL url = new URL("http://www.baidu.com");
//            URLConnection connection = url.openConnection();
//            System.out.println(ParseStream(connection.getInputStream()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    public static String ParseStream(InputStream stream) {
        StringBuilder builder = new StringBuilder("");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String strtmp;
            try {
                strtmp = reader.readLine();
                while (null != strtmp) {
                    builder.append(strtmp);
                    builder.append("\n");
                    strtmp = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        SocketAddress address = new InetSocketAddress("localhost", 8888);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
        try {
            URL url = new URL("www.baidu.com");
            URLConnection connection = url.openConnection(proxy);
            System.out.println(ParseStream(connection.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}