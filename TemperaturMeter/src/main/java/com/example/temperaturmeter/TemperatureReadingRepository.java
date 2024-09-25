package com.example.temperaturmeter;
import org.springframework.data.jpa.repository.JpaRepository;// Imports JpaRepository, which provides CRUD methods for the entity.
import org.springframework.data.jpa.repository.Query;// Imports the @Query annotation, which allows defining custom queries using JPQL (Java Persistence Query Language).
import org.springframework.data.repository.query.Param;// Imports the @Param annotation, which binds method parameters to named parameters in JPQL queries.
import java.time.LocalDateTime;// Imports the LocalDateTime class for working with timestamps.
import java.util.List;// Imports the List interface to handle collections of TemperatureReading entities.

public interface TemperatureReadingRepository extends JpaRepository<TemperatureReading, Long> {
// Declares the repository interface for the TemperatureReading entity, with Long as the type of the primary key.
// It extends JpaRepository, which provides basic CRUD operations like save, delete, and findAll.

    @Query("SELECT MAX(t.temperature) FROM TemperatureReading t WHERE t.timestamp >= :startTime")
        // Custom JPQL query to find the maximum temperature recorded in the last 5 minutes.
    Double findMaxTemperatureInLast5Minutes(@Param("startTime") LocalDateTime startTime);
    // Method to get the maximum temperature. The @Param annotation binds the 'startTime' parameter to the query.

    // Query for the lowest temperature in the last 5 minutes
    @Query("SELECT MIN(t.temperature) FROM TemperatureReading t WHERE t.timestamp >= :startTime")
    // Custom JPQL query to find the minimum temperature recorded in the last 5 minutes.
    Double findMinTemperatureInLast5Minutes(@Param("startTime") LocalDateTime startTime);
    // Method to get the minimum temperature. The 'startTime' parameter is used in the query.

    // Query for the most recent (last) temperature measurement
    @Query("SELECT t FROM TemperatureReading t ORDER BY t.timestamp DESC")
    // Custom JPQL query to fetch the most recent temperature reading, ordered by timestamp in descending order.
    TemperatureReading findLastMeasurement();
    // Method to retrieve the most recent temperature reading.

    // Query for temperature readings from a specific date
    @Query("SELECT t FROM TemperatureReading t WHERE t.timestamp >= :startDate ORDER BY t.timestamp DESC")
    // Custom JPQL query to fetch all temperature readings from a specified date onward, ordered by timestamp in descending order.
    List<TemperatureReading> findMeasurementsFromDate(@Param("startDate") LocalDateTime startDate);
    // Method to retrieve all readings from the specified start date.

    // Query for today's temperature readings
    @Query("SELECT t FROM TemperatureReading t WHERE DATE(t.timestamp) = CURRENT_DATE ORDER BY t.timestamp DESC")
    // Custom JPQL query to fetch all temperature readings recorded today, ordered by timestamp in descending order.
    List<TemperatureReading> findTodaysMeasurements();
    // Method to retrieve all temperature readings recorded on the current date.
}

/*
Inheritance from JpaRepository:
    This interface extends JpaRepository, which provides default CRUD methods like save(), delete(), findById(), and findAll().
    These methods are automatically implemented by Spring, so there's no need to write them explicitly.

Custom Queries (@Query):
    JPQL (Java Persistence Query Language) is used for writing queries based on the entity model.

@Param annotation:
    This is used to bind method parameters to the named parameters in the JPQL query.

Custom Methods:

findMaxTemperatureInLast5Minutes(LocalDateTime startTime):
    Retrieves the maximum temperature recorded since a given startTime (used to calculate the last 5 minutes).

findMinTemperatureInLast5Minutes(LocalDateTime startTime):
    Retrieves the minimum temperature recorded since a given startTime (for the last 5 minutes).

findLastMeasurement():
    Retrieves the most recent temperature reading, ordering the records by timestamp in descending order.

findMeasurementsFromDate(LocalDateTime startDate):
    Retrieves temperature readings from a specific date onward, ordered by the timestamp in descending order.

findTodaysMeasurements():
    Retrieves all temperature readings recorded on the current date (CURRENT_DATE), ordered by timestamp.
*/