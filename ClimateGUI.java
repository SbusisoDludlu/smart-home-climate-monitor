import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Graphical User Interface for the Climate Monitor
 */
public class ClimateGUI extends JFrame {
    private ClimateMonitor monitor;
    private JLabel tempLabel, humidityLabel, airQualityLabel;
    private JTextArea alertArea, logArea;
    
    public ClimateGUI() {
        this.monitor = new ClimateMonitor();
        initializeGUI();
        startMonitoring();
    }
    
    private void initializeGUI() {
        setTitle("Smart Home Climate Monitor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create panels
        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        JPanel sensorPanel = createSensorPanel();
        JPanel controlPanel = createControlPanel();
        
        mainPanel.add(sensorPanel);
        mainPanel.add(controlPanel);
        
        add(mainPanel, BorderLayout.CENTER);
        add(createAlertPanel(), BorderLayout.SOUTH);
        
        setSize(600, 500);
        setLocationRelativeTo(null);
    }
    
    private JPanel createSensorPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.setBorder(BorderFactory.createTitledBorder("Current Sensor Readings"));
        
        tempLabel = new JLabel("-- °C", JLabel.CENTER);
        humidityLabel = new JLabel("-- %", JLabel.CENTER);
        airQualityLabel = new JLabel("--", JLabel.CENTER);
        
        tempLabel.setFont(new Font("Arial", Font.BOLD, 24));
        humidityLabel.setFont(new Font("Arial", Font.BOLD, 24));
        airQualityLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        panel.add(new JLabel("Temperature:"));
        panel.add(tempLabel);
        panel.add(new JLabel("Humidity:"));
        panel.add(humidityLabel);
        panel.add(new JLabel("Air Quality:"));
        panel.add(airQualityLabel);
        
        return panel;
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Controls"));
        
        JButton windowBtn = new JButton("Open Window");
        JButton heaterBtn = new JButton("Turn On Heater");
        JButton cookingBtn = new JButton("Start Cooking");
        
        windowBtn.addActionListener(e -> monitor.simulateEvent("window_open"));
        heaterBtn.addActionListener(e -> monitor.simulateEvent("heater_on"));
        cookingBtn.addActionListener(e -> monitor.simulateEvent("cooking"));
        
        panel.add(windowBtn);
        panel.add(heaterBtn);
        panel.add(cookingBtn);
        
        return panel;
    }
    
    private JPanel createAlertPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Alerts & Logs"));
        
        alertArea = new JTextArea(5, 50);
        logArea = new JTextArea(8, 50);
        
        alertArea.setEditable(false);
        logArea.setEditable(false);
        
        panel.add(new JLabel("Active Alerts:"), BorderLayout.NORTH);
        panel.add(new JScrollPane(alertArea), BorderLayout.CENTER);
        panel.add(new JLabel("Recent Logs:"), BorderLayout.SOUTH);
        panel.add(new JScrollPane(logArea), BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void startMonitoring() {
        Timer guiTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDisplay();
            }
        });
        guiTimer.start();
        
        monitor.startMonitoring();
    }
    
    private void updateDisplay() {
        ClimateReading reading = monitor.getCurrentReading();
        
        tempLabel.setText(String.format("%.1f °C", reading.getTemperature()));
        humidityLabel.setText(String.format("%.1f %%", reading.getHumidity()));
        airQualityLabel.setText(String.format("%d", reading.getAirQuality()));
        
        // Update alerts
        StringBuilder alerts = new StringBuilder();
        for (AlertSystem.Alert alert : monitor.getActiveAlerts()) {
            alerts.append(alert.getMessage()).append("\n");
        }
        alertArea.setText(alerts.toString());
        
        // Update logs (last 5 readings)
        List<ClimateReading> history = monitor.getHistoricalData();
        StringBuilder logs = new StringBuilder();
        int start = Math.max(0, history.size() - 5);
        for (int i = start; i < history.size(); i++) {
            logs.append(history.get(i)).append("\n");
        }
        logArea.setText(logs.toString());
    }
}