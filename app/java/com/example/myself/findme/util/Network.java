package com.example.myself.findme.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Network {
    // Android Client
    public static String getJson(String iurl) throws IOException {
        // url of php pages
        URL url = new URL(iurl);
        // connection with url
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // conneciton open
        connection.connect();

      // receive data from server in form of bytes
        InputStream inputStream = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String content = "";
        // reading line by line
        while ((content = bufferedReader.readLine()) != null) {
            stringBuilder.append(content);

        }
        // return complete string receive from server
        return stringBuilder.toString();

    }
}
