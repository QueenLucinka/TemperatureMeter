package com.example.temperaturmeter;
import com.fazecast.jSerialComm.SerialPort;// Imports the SerialPort class from the jSerialComm library to handle serial communication.
import org.springframework.beans.factory.annotation.Autowired;// Imports the @Autowired annotation for automatic dependency injection by Spring.
import org.springframework.beans.factory.annotation.Value;// Imports the @Value annotation to inject values from application properties.
import org.springframework.stereotype.Service;// Imports the @Service annotation, marking this class as a Spring-managed service.
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;// Imports the ExecutorService and Executors classes to manage threading and concurrency.

@Service
// Marks this class as a service in Spring's component-scanning, so it can be injected into other components.
public class SerialCommunicationListener {

    @Value("${serial.port}")
    // Injects the serial port name from the application properties (e.g., "COM3" or "/dev/ttyUSB0").
    private String serialPortName;

    @Value("${serial.baudrate}")
    // Injects the baud rate for serial communication from the application properties (e.g., 9600).
    private int baudRate;

    private SerialPort serialPort;
    // The SerialPort object used to interact with the serial device (e.g., Arduino).

    @Autowired
    // Injects the TemperatureService to handle the saving of temperature data to the database.
    private TemperatureService temperatureService;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    // Creates a single-threaded executor service to manage background tasks, such as saving data to the database.

    public void initializeSerialPort() {
        // Initializes the serial port connection and starts reading data.

        serialPort = SerialPort.getCommPort(serialPortName);
        // Sets the serial port using the injected serial port name.

        serialPort.setBaudRate(baudRate);
        // Configures the baud rate for the serial port communication.

        if (serialPort.openPort()) {
            // Attempts to open the serial port and checks if it was successful.
            System.out.println("Serial port " + serialPortName + " opened successfully.");
            // Prints a message if the serial port opens successfully.
        } else {
            // If the serial port fails to open, prints an error message.
            System.err.println("Failed to open serial port " + serialPortName);
        }

        // Starts reading data from the serial port in a separate thread.
        new Thread(() -> {
            while (serialPort.isOpen()) {
                // Continuously reads data from the serial port while it remains open.

                byte[] buffer = new byte[1024];
                // Creates a buffer to store incoming serial data.

                int bytesRead = serialPort.readBytes(buffer, buffer.length);
                // Reads bytes from the serial port into the buffer.

                if (bytesRead > 0) {
                    // If data is read from the serial port:
                    String data = new String(buffer, 0, bytesRead).trim();
                    // Converts the byte array to a string and trims any excess whitespace.

                    if (!data.isEmpty()) {
                        // If the received data is not empty:
                        System.out.println("Received data: " + data);
                        // Prints the received data to the console.
                        processAndSaveData(data);
                        // Processes and saves the temperature data.
                    } else {
                        // If the received data is empty or malformed, skip processing.
                        System.out.println("Received empty or malformed data, skipping...");
                    }
                }
            }
        }).start();
        // Starts the thread for reading data from the serial port.
    }

    private void processAndSaveData(String data) {
        // Processes the incoming serial data and saves the temperature reading to the database.

        try {
            // Try-catch block to handle parsing and processing errors.

            // Remove non-numeric characters (except the period for decimal).
            String cleanedData = data.replaceAll("[^\\d.]", "");
            // Cleans the data by removing any characters that are not digits or decimal points.

            if (!cleanedData.isEmpty()) {
                // If the cleaned data is not empty:
                Double temperature = Double.parseDouble(cleanedData);
                // Converts the cleaned string to a Double representing the temperature.

                // Submit the save operation to the executor service to avoid blocking the main thread.
                executorService.submit(() -> {
                    try {
                        // Try-catch block for saving the temperature to the database.
                        temperatureService.saveTemperature(temperature);
                        // Calls the service to save the temperature in the database.
                        System.out.println("Temperature saved: " + temperature);
                        // Prints a message when the temperature is successfully saved.
                    } catch (Exception e) {
                        // Catches any exceptions during the save operation.
                        System.err.println("Error saving temperature: " + e.getMessage());
                        // Prints an error message if the save operation fails.
                    }
                });
            } else {
                // If the data is malformed and can't be parsed:
                System.err.println("Malformed data, unable to parse temperature: " + data);
                // Prints an error message.
            }
        } catch (NumberFormatException e) {
            // Catches any exceptions if parsing the temperature fails.
            System.err.println("Failed to parse temperature: " + data);
            // Prints an error message if the data can't be parsed as a valid number.
        }
    }

    public void closeSerialPort() {
        // Closes the serial port connection and shuts down the executor service.

        if (serialPort != null && serialPort.isOpen()) {
            // If the serial port is open:
            serialPort.closePort();
            // Closes the serial port.
            System.out.println("Serial port closed.");
            // Prints a message confirming that the serial port is closed.
        }

        // Shuts down the executor service to avoid
    }
}


