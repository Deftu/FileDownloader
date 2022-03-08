package xyz.deftu.fd;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

class ConnectionHelper {
    static HttpURLConnection createConnection(String url, boolean caches, String userAgent, int timeout) {
        try {
            URL theUrl = new URL(url);
            URLConnection urlConnection = theUrl.openConnection();
            if (urlConnection instanceof HttpURLConnection) {
                HttpURLConnection connection = (HttpURLConnection) urlConnection;
                connection.setRequestMethod("GET");
                if (userAgent != null) connection.addRequestProperty("User-Agent", userAgent);
                connection.setUseCaches(caches);
                connection.setReadTimeout(timeout);
                connection.setConnectTimeout(timeout);
                connection.setDoInput(true);
                return connection;
            } else return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}