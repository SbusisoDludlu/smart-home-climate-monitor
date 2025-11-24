import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles data persistence - logs readings to file and manages historical data
 */
public class DataLogger {
    private static final String LOG_FILE = "data/climate_log.txt";
    private List<ClimateReading> historicalData;
    
    public DataLogger() {
        this.historicalData = new ArrayList<>();
        ensureDataDirectory();
    }
    
    private void ensureDataDirectory() {
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
    
    public void logReading(ClimateReading reading) {
        historicalData.add(reading);
        
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(reading.toCSV() + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
    
    public List<ClimateReading> getHistoricalData() {
        return new ArrayList<>(historicalData);
    }
    
    public List<ClimateReading> loadHistoricalData() {
        List<ClimateReading> loadedData = new ArrayList<>();
        File file = new File(LOG_FILE);
        
        if (!file.exists()) return loadedData;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    double temp = Double.parseDouble(parts[1]);
                    double humidity = Double.parseDouble(parts[2]);
                    int airQuality = Integer.parseInt(parts[3]);
                    loadedData.add(new ClimateReading(temp, humidity, airQuality));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading log file: " + e.getMessage());
        }
        
        historicalData.addAll(loadedData);
        return loadedData;
    }
}