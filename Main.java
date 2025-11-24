/**
 * Main class to run the Climate Monitor application
 * Choose between console mode or GUI mode
 */
public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("console")) {
            runConsoleMode();
        } else {
            runGUIMode();
        }
    }
    
    private static void runConsoleMode() {
        System.out.println("=== Smart Home Climate Monitor (Console Mode) ===");
        System.out.println("Starting monitoring... Press Ctrl+C to stop.");
        
        ClimateMonitor monitor = new ClimateMonitor();
        monitor.startMonitoring();
        
        // Keep the program running
        try {
            Thread.sleep(30000); // Run for 30 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        monitor.stopMonitoring();
        System.out.println("Monitoring stopped.");
    }
    
    private static void runGUIMode() {
        SwingUtilities.invokeLater(() -> {
            ClimateGUI gui = new ClimateGUI();
            gui.setVisible(true);
        });
    }
}