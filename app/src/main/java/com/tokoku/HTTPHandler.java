package com.tokoku;

import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

public class HTTPHandler {
    private static final String TAG = HTTPHandler.class.getSimpleName();

    public String makeServiceCall(String request) {
        String response = null;
        try {
            URL url = new URL(request);
            URLConnection connection = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer stringBuffer = new StringBuffer();

            String line;

            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(line);
            }

            response = stringBuffer.toString();

        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        }

        Log.e(MainActivity.class.getSimpleName(), "RESPONS from url " + request + " (handler class): " + response);

        return response;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}
