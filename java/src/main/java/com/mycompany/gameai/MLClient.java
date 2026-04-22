package com.mycompany.gameai;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MLClient {

    public static String enviarDados(double[] vetor) {
        try {
            URL url = new URL("http://127.0.0.1:8000/predict");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInput = "{"
                    + "\"agressividade\":" + vetor[0] + ","
                    + "\"exploracao\":" + vetor[1] + ","
                    + "\"cautela\":" + vetor[2]
                    + "}";

            OutputStream os = conn.getOutputStream();
            os.write(jsonInput.getBytes());
            os.flush();
            os.close();

            Scanner scanner = new Scanner(conn.getInputStream());
            String response = scanner.useDelimiter("\\A").next();
            scanner.close();

            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}