import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public interface RateDataSource {
    Map<Currency, Double> getRates();

    boolean isValid();
}

class LocalRateDataSource implements RateDataSource {

    @Override
    public Map<Currency, Double> getRates() {
        try {
            Map<Currency, Double> result = new HashMap<>();

            BufferedReader reader = new BufferedReader(new FileReader(Config.CACHE_FILE_NAME));
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Config.FILE_SEPARATOR);
                result.put(
                        new Currency(parts[0]),
                        Double.parseDouble(parts[1])
                );
            }
            reader.close();
            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public boolean isValid() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String formatted = formatter.format(date);
        return formatted.equals(readDate());
    }

    private String readDate() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(Config.CACHE_FILE_NAME));
            String line = reader.readLine();
            reader.close();
            return line;
        } catch (Exception ex) {
            return null;
        }
    }

}

class RemoteRateDataSource implements RateDataSource {

    @Override
    public Map<Currency, Double> getRates() {
        String response = getResponse();
        Map<Currency, Double> result = new HashMap<>();
        if (response != null) {
            JSONObject json = new JSONObject(response);
            JSONObject rawRates = json.getJSONObject("rates");
            for (String key : rawRates.keySet()) {
                result.put(
                        new Currency(key),
                        rawRates.optDouble(key)
                );
            }
            cacheResponse(result, json.optString("date"));
        }
        return result;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    private String getResponse() {
        try {
            URL url = new URL(Config.END_POINT + Config.METHOD);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            if (con.getResponseCode() != 200) {
                return null;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            return content.toString();
        } catch (Exception ex) {
            return null;
        }
    }

    private void cacheResponse(Map<Currency, Double> rates, String date) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(Config.CACHE_FILE_NAME));
            writer.write(date);
            writer.newLine();
            for (Map.Entry<Currency, Double> entry : rates.entrySet()) {
                writer.write(entry.getKey().getCode().toString() + Config.FILE_SEPARATOR + entry.getValue());
                writer.newLine();
            }
            writer.close();
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
    }

}