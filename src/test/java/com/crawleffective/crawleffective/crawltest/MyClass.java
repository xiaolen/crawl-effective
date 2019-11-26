package com.crawleffective.crawleffective.crawltest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.net.Proxy.Type;
import java.util.ArrayList;
import java.util.List;

public class MyClass {

    public static class MyProxy extends ProxySelector {

        private List<URI> failed = new ArrayList<>();

        @Override
        public List<Proxy> select(URI uri) {
            List<Proxy> result = new ArrayList<>();
            if (failed.contains(uri) || (!"https".equalsIgnoreCase(uri.getScheme()) && !"http".equalsIgnoreCase(uri.getScheme()))) {
                result.add(Proxy.NO_PROXY);
            } else {
                SocketAddress address = new InetSocketAddress("localhost", 8888);
                Proxy proxy = new Proxy(Type.HTTP, address);
                result.add(proxy);
            }
            return result;
        }

        @Override
        public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
            failed.add(uri);
        }

    }

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
        try {
            URL url = new URL("http://www.baidu.com");
            URLConnection connection = url.openConnection();
            System.out.println(ParseStream(connection.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}