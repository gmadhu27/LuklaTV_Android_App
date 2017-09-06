package smadhu.example.com.luklatv;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by suman on 8/9/2017.
 */

class YoutubeHttpConnection {
    public String getService(String urlString) {
        String response = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            //connection.setConnectTimeout(10000);
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            response = stringResponse(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private String stringResponse(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        try{
            while((line = reader.readLine())!= null){
                sb.append(line).append("\n");
            }
        }catch (Exception e){

        }

        return sb.toString();
    }
}

