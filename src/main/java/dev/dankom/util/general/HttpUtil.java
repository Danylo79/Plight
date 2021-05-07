package dev.dankom.util.general;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

public class HttpUtil {

    private final String url;

    public HttpUtil(String url) {
        this.url = url;
    }

    public StringBuilder getStringBuilder() throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = getURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result;
    }

    public URL getURL() {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void downloadFile(File localFile) throws IOException {
        if (localFile.exists()) {
            localFile.delete();
        }
        localFile.createNewFile();
        URL readUrl = new URL(url);
        OutputStream out = new BufferedOutputStream(new FileOutputStream(localFile.getName()));
        URLConnection conn = readUrl.openConnection();
        String encoded = Base64.getEncoder().encodeToString(("username"+":"+"password").getBytes(StandardCharsets.UTF_8));  //Java 8
        conn.setRequestProperty("Authorization", "Basic "+ encoded);
        InputStream in = conn.getInputStream();
        byte[] buffer = new byte[1024];

        int numRead;
        while ((numRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, numRead);
        }
        if (in != null) {
            in.close();
        }
        if (out != null) {
            out.close();
        }
    }

    public JSONObject getJSON() throws ParseException {
        String json = null;
        try {
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.connect();
            InputStream inStream = connection.getInputStream();
            json = new Scanner(inStream, "UTF-8").useDelimiter("\\Z").next(); // input stream to string
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return (JSONObject) new JSONParser().parse(json);
    }
}
