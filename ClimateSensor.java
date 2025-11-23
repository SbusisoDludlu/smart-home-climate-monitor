import java.util.Random;

/**
 * Simulates a physical climate sensor hardware unit
 * Generates realistic temperature, humidity and air quality readings
 */
public class ClimateSensor {
    private Random random;
    private double currentTemp;
    private double currentHumidity;
    private int currentAirQuality;
    
    // Sensor specifications (similar to real sensors like DHT22)
    private static final double MIN_TEMP = -10.0;
    private static final double MAX_TEMP = 50.0;
    private static final double MIN_HUMIDITY = 20.0;
    private static final double MAX_HUMIDITY = 90.0;
    private static final int MIN_AIR_QUALITY = 0;
    private static final int MAX_AIR_QUALITY = 500;
    
    public ClimateSensor() {
        this.random = new Random();
        // Initialize with realistic room conditions
        this.currentTemp = 22.0;
        this.currentHumidity = 45.0;
        this.currentAirQuality = 50;
    }
    
    public ClimateReading readSensor() {
        // Simulate small fluctuations in readings
        double tempChange = (random.nextDouble() - 0.5) * 2.0;
        double humidityChange = (random.nextDouble() - 0.5) * 5.0;
        int airQualityChange = random.nextInt(20) - 10;
        
        // Update current values with realistic constraints
        currentTemp = Math.max(MIN_TEMP, Math.min(MAX_TEMP, currentTemp + tempChange));
        currentHumidity = Math.max(MIN_HUMIDITY, Math.min(MAX_HUMIDITY, currentHumidity + humidityChange));
        currentAirQuality = Math.max(MIN_AIR_QUALITY, Math.min(MAX_AIR_QUALITY, currentAirQuality + airQualityChange));
        
        return new ClimateReading(currentTemp, currentHumidity, currentAirQuality);
    }
    
    // Method to simulate environmental changes (like opening a window)
    public void simulateEnvironmentChange(String event) {
        switch (event.toLowerCase()) {
            case "window_open":
                currentTemp -= 3.0;
                currentAirQuality -= 30;
                break;
            case "heater_on":
                currentTemp += 5.0;
                currentHumidity -= 10.0;
                break;
            case "cooking":
                currentTemp += 2.0;
                currentHumidity += 15.0;
                currentAirQuality += 100;
                break;
        }
    }
}
