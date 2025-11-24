import java.time.LocalDateTime;

/**
 * Data model representing a single climate reading with timestamp
 */
public class ClimateReading {
    private double temperature;
    private double humidity;
    private int airQuality;
    private LocalDateTime timestamp;
    
    public ClimateReading(double temperature, double humidity, int airQuality) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.airQuality = airQuality;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters
    public double getTemperature() { return temperature; }
    public double getHumidity() { return humidity; }
    public int getAirQuality() { return airQuality; }
    public LocalDateTime getTimestamp() { return timestamp; }
    
    @Override
    public String toString() {
        return String.format("[%s] Temp: %.1f°C, Humidity: %.1f%%, Air Quality: %d", 
                           timestamp, temperature, humidity, airQuality);
    }
    
    public String toCSV() {
        return String.format("%s,%.2f,%.2f,%d", 
                           timestamp, temperature, humidity, airQuality);
    }
}