package JavaFx.utils;

import JavaFx.exception.NoConnectionException;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * Класс-утилита для отправки REST запросов и принятия ответов
 */
public class HTTP {
    /**
     * отправляет GET-запроса и возвращает ответ
     * @param urlString String
     * @return String
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static String GetRequest(String urlString) throws NoConnectionException {
        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();

            StringBuilder sb = new StringBuilder();
            InputStream is = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            return sb.toString();
        } catch (IOException e) {
            throw new NoConnectionException("Нет соединения");
        }
    }

    /**
     * отправляет POST-запроса и возвращает ответ
     * @param link String
     * @param jsonObject JSONObject
     * @return String
     * @throws NoConnectionException если при соединении возникли ошибки
     * @throws IOException если при соединении возникли ошибки
     */
    public static String Post(String link, JSONObject jsonObject)
            throws NoConnectionException, IOException {
        try {
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            try (OutputStream os = httpURLConnection.getOutputStream()) {
                byte[] input = jsonObject.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } catch (ConnectException e) {
            throw new NoConnectionException("Нет соединения");
        } catch (ProtocolException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * отправляет PUT-запроса и возвращает ответ
     * @param urlString String
     * @param jsonString JSONObject
     * @return String
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static String PutRequest(String urlString, String jsonString) throws NoConnectionException{
        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            http.setRequestMethod("PUT");
            http.setDoOutput(true);

            byte[] out = jsonString.getBytes(StandardCharsets.UTF_8);
            int length = out.length;
            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.connect();
            try (OutputStream os = http.getOutputStream()) {
                os.write(out);
            }

            StringBuilder sb = new StringBuilder();
            InputStream is = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new NoConnectionException("Нет соединения");
        }
    }

    /**
     * отправляет DELETE-запроса и возвращает ответ
     * @param urlString String
     * @return boolean
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static boolean DeleteRequest(String urlString) throws  NoConnectionException{
        try {

            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            http.setRequestMethod("DELETE");
            http.setDoOutput(true);
            http.connect();

            http.getResponseCode();
            return true;
        } catch (IOException e) {
            throw new NoConnectionException("Нет соединения");
        }
    }
}
