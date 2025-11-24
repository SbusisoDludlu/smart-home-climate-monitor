/**
 * Monitors readings and triggers alerts based on configured thresholds
 */
public class AlertSystem {
    private static final double MAX_SAFE_TEMP = 30.0;
    private static final double MIN_SAFE_TEMP = 16.0;
    private static final double MAX_SAFE_HUMIDITY = 70.0;
    private static final double MIN_SAFE_HUMIDITY = 30.0;
    private static final int MAX_SAFE_AIR_QUALITY = 150;
    
    public enum AlertLevel {
        NORMAL, WARNING, CRITICAL
    }
    
    public static class Alert {
        private String message;
        private AlertLevel level;
        private LocalDateTime timestamp;
        
        public Alert(String message, AlertLevel level) {
            this.message = message;
            this.level = level;
            this.timestamp = LocalDateTime.now();
        }
        
        // Getters
        public String getMessage() { return message; }
        public AlertLevel getLevel() { return level; }
        public LocalDateTime getTimestamp() { return timestamp; }
        
        @Override
        public String toString() {
            return String.format("[%s] %s: %s", level, timestamp, message);
        }
    }
    
    public Alert checkReading(ClimateReading reading) {
        double temp = reading.getTemperature();
        double humidity = reading.getHumidity();
        int airQuality = reading.getAirQuality();
        
        // Check for critical conditions first
        if (temp > 35.0) {
            return new Alert(String.format("CRITICAL: Temperature too high (%.1f°C)", temp), AlertLevel.CRITICAL);
        }
        if (temp < 10.0) {
            return new Alert(String.format("CRITICAL: Temperature too low (%.1f°C)", temp), AlertLevel.CRITICAL);
        }
        if (airQuality > 300) {
            return new Alert(String.format("CRITICAL: Poor air quality (%d)", airQuality), AlertLevel.CRITICAL);
        }
        
        // Check for warning conditions
        if (temp > MAX_SAFE_TEMP) {
            return new Alert(String.format("High temperature warning (%.1f°C)", temp), AlertLevel.WARNING);
        }
        if (temp < MIN_SAFE_TEMP) {
            return new Alert(String.format("Low temperature warning (%.1f°C)", temp), AlertLevel.WARNING);
        }
        if (humidity > MAX_SAFE_HUMIDITY) {
            return new Alert(String.format("High humidity warning (%.1f%%)", humidity), AlertLevel.WARNING);
        }
        if (humidity < MIN_SAFE_HUMIDITY) {
            return new Alert(String.format("Low humidity warning (%.1f%%)", humidity), AlertLevel.WARNING);
        }
        if (airQuality > MAX_SAFE_AIR_QUALITY) {
            return new Alert(String.format("Poor air quality warning (%d)", airQuality), AlertLevel.WARNING);
        }
        
        return null; // No alert
    }
}