import java.util.*;

/**
 * Main system controller - coordinates all components
 */
public class ClimateMonitor {
    private ClimateSensor sensor;
    private DataLogger logger;
    private AlertSystem alertSystem;
    private List<AlertSystem.Alert> activeAlerts;
    private Timer monitoringTimer;
    
    public ClimateMonitor() {
        this.sensor = new ClimateSensor();
        this.logger = new DataLogger();
        this.alertSystem = new AlertSystem();
        this.activeAlerts = new ArrayList<>();
        
        // Load historical data on startup
        logger.loadHistoricalData();
    }
    
    public void startMonitoring() {
        monitoringTimer = new Timer();
        monitoringTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                takeReading();
            }
        }, 0, 5000); // Take reading every 5 seconds
    }
    
    public void stopMonitoring() {
        if (monitoringTimer != null) {
            monitoringTimer.cancel();
        }
    }
    
    private void takeReading() {
        ClimateReading reading = sensor.readSensor();
        logger.logReading(reading);
        
        // Check for alerts
        AlertSystem.Alert alert = alertSystem.checkReading(reading);
        if (alert != null) {
            activeAlerts.add(alert);
            System.out.println("ALERT: " + alert.getMessage());
        }
        
        System.out.println(reading);
    }
    
    public void simulateEvent(String event) {
        sensor.simulateEnvironmentChange(event);
        System.out.println("Simulated event: " + event);
    }
    
    public List<ClimateReading> getHistoricalData() {
        return logger.getHistoricalData();
    }
    
    public List<AlertSystem.Alert> getActiveAlerts() {
        return new ArrayList<>(activeAlerts);
    }
    
    public ClimateReading getCurrentReading() {
        return new ClimateReading(
            sensor.readSensor().getTemperature(),
            sensor.readSensor().getHumidity(),
            sensor.readSensor().getAirQuality()
        );
    }
}