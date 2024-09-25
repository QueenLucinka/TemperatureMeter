package com.example.temperaturmeter;
import jakarta.transaction.Transactional;// Imports the @Transactional annotation, which ensures that the method’s database operations are executed in a transaction.
import org.springframework.beans.factory.annotation.Autowired;// Imports the @Autowired annotation for automatic dependency injection by Spring.
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;// Imports the LocalDateTime class to handle date and time.
import java.util.List;// Imports the List interface to work with collections of temperature readings.

@Service
// Marks this class as a Spring Service, which provides business logic and can be injected into other components.
public class TemperatureService {

    @Autowired
    // Automatically injects an instance of TemperatureReadingRepository into this class.
    private TemperatureReadingRepository temperatureReadingRepository;
    // Repository interface for interacting with the database and performing CRUD operations.

    @Transactional
    // Ensures that the database operations inside this method are executed within a transaction.
    public void saveTemperature(Double temperature) {
        TemperatureReading reading = new TemperatureReading();
        // Creates a new instance of the TemperatureReading entity.
        reading.setTemperature(temperature);
        // Sets the temperature value to the reading object.
        reading.setTimestamp(LocalDateTime.now());
        // Sets the current timestamp for the reading.
        temperatureReadingRepository.save(reading);
        // Saves the new reading into the database via the repository.
    }

    // Method to fetch all temperature readings
    public List<TemperatureReading> getAllReadings() {
        return temperatureReadingRepository.findAll();
        // Fetches all the temperature readings from the database.
    }

    // Method to get the highest temperature in the last 5 minutes
    public Double getMaxTemperatureInLast5Minutes() {
        LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusMinutes(5);
        // Calculates the time exactly 5 minutes ago from the current time.
        return temperatureReadingRepository.findMaxTemperatureInLast5Minutes(fiveMinutesAgo);
        // Queries the repository to find the maximum temperature recorded in the last 5 minutes.
    }

    // Method to get the lowest temperature in the last 5 minutes
    public Double getMinTemperatureInLast5Minutes() {
        LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusMinutes(5);
        // Calculates the time exactly 5 minutes ago from the current time.
        return temperatureReadingRepository.findMinTemperatureInLast5Minutes(fiveMinutesAgo);
        // Queries the repository to find the minimum temperature recorded in the last 5 minutes.
    }

    // Method to get the most recent (last) temperature measurement
    public TemperatureReading getLastMeasurement() {
        return temperatureReadingRepository.findLastMeasurement();
        // Queries the repository to get the most recent temperature reading.
    }

    // Method to get temperature readings from a specific date
    public List<TemperatureReading> getReadingsFromDate(LocalDateTime startDate) {
        return temperatureReadingRepository.findMeasurementsFromDate(startDate);
        // Fetches all temperature readings from the specified start date onward from the repository.
    }

    // Method to get today's temperature readings
    public List<TemperatureReading> getTodaysReadings() {
        return temperatureReadingRepository.findTodaysMeasurements();
        // Queries the repository to get all temperature readings recorded today.
    }

}

/*
Annotations:
@Service:
    Marks this class as a service, meaning it contains business logic and will be managed by Spring’s IoC container.

@Autowired:
    Automatically injects dependencies (in this case, the TemperatureReadingRepository) by Spring,
    so you don't need to manually instantiate them.

@Transactional:
    Ensures that the method will be executed in a single database transaction.

Methods:
saveTemperature(Double temperature):
    Creates and saves a new TemperatureReading with the given temperature and current timestamp.

getAllReadings():
    Fetches all the temperature readings from the database.

getMaxTemperatureInLast5Minutes():
    Fetches the highest temperature recorded in the last 5 minutes.

getMinTemperatureInLast5Minutes():
    Fetches the lowest temperature recorded in the last 5 minutes.

getLastMeasurement():
    Retrieves the most recent temperature reading.

getReadingsFromDate(LocalDateTime startDate):
    Retrieves all temperature readings from a specified date onward.
getTodaysReadings():
    Fetches all temperature readings recorded today.

Repository interaction:
    The temperatureReadingRepository is used to interact with the database,
    performing various read and write operations, such as saving a new reading, finding all readings,
    and fetching specific temperature statistics based on time intervals or dates.
*/