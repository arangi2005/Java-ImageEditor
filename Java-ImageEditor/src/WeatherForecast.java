import com.formdev.flatlaf.json.Json;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class WeatherForecast {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://api.open-meteo.com/v1/forecast?"
                    + "latitude=39.168804&longitude=-86.536659"
                    + "&hourly=temperature_2m"
                    + "&temperature_unit=fahrenheit"
                    + "&timezone=EST");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new IOException();
            }


            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JsonElement je = JsonParser.parseString(response.toString());
            JsonObject jsonObj = je.getAsJsonObject();
            JsonArray times = jsonObj.getAsJsonObject("hourly").getAsJsonArray("time");
            JsonArray temperatures = jsonObj.getAsJsonObject("hourly").getAsJsonArray("temperature_2m");


            System.out.println("Bloomington 7-Day Forecast in Fahrenheit:");
            String currentDate = "";
            for (int i = 0; i < times.size(); i++) {
                String time = times.get(i).getAsString();
                double temp = temperatures.get(i).getAsDouble();
                String date = time.split("T")[0];
                if (!date.equals(currentDate)) {
                    System.out.printf("Forecast for %s:\n", date);
                    currentDate = date;
                }
                System.out.printf("%s: %.1f°F\n", time.substring(11), temp);
            }



        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}