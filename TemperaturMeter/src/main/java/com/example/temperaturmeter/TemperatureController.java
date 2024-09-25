package com.example.temperaturmeter;
import org.springframework.beans.factory.annotation.Autowired;// Imports the @Autowired annotation for dependency injection in Spring.
import org.springframework.format.annotation.DateTimeFormat;// Imports the DateTimeFormat annotation to format date and time parameters.
import org.springframework.stereotype.Controller;// Imports the Controller annotation to designate this class as a Spring MVC controller.
import org.springframework.web.bind.annotation.GetMapping;// Imports the GetMapping annotation to map HTTP GET requests to specific methods.
import org.springframework.web.bind.annotation.RequestMapping;// Imports the RequestMapping annotation for specifying base URL mappings for this controller.
import org.springframework.web.bind.annotation.RequestParam;// Imports the RequestParam annotation to retrieve request parameters in GET requests.
import org.springframework.web.bind.annotation.RestController;// Imports the RestController annotation for creating RESTful web services (not used in this case).
import java.time.LocalDateTime;// Imports the LocalDateTime class to handle date and time.
import org.springframework.ui.Model;// Imports the Model interface, used to pass data to the Thymeleaf templates.
import java.util.List;// Imports the List interface to handle collections of data (e.g., temperature readings).

//@RestController // For SpringBoot REST API readings
@Controller // For Thymeleaf template-based readings
@RequestMapping("/api/temperature")
// Specifies that all endpoints in this controller will start with "/api/temperature".
public class TemperatureController {

    @Autowired// Automatically injects the TemperatureService instance.
    private TemperatureService temperatureService;  // The service used to fetch temperature data.

    // METHODS USING THYMELEAF

    // Method for fetching all temperature readings
    @GetMapping("/readings")
    // Maps HTTP GET requests at "/api/temperature/readings" to this method.
    public String getAllReadings(Model model) {
        List<TemperatureReading> readings = temperatureService.getAllReadings();
        // Fetches all temperature readings from the service.
        model.addAttribute("readings", readings);
        // Adds the list of readings to the model to be passed to the Thymeleaf template.
        model.addAttribute("pageTitle", "All Temperature Readings");
        // Adds a dynamic title to the page.
        return "Templates_Temperature_Readings";
        // Returns the name of the Thymeleaf template (without the ".html" extension).
    }
    // Example URL: http://localhost:8080/api/temperature/readings

    // Method for fetching the maximum temperature from the last 5 minutes
    @GetMapping("/max-last-5-minutes")
    // Maps HTTP GET requests at "/api/temperature/max-last-5-minutes" to this method.
    public String getMaxTemperatureLast5Minutes(Model model) {
        Double maxTemperature = temperatureService.getMaxTemperatureInLast5Minutes();
        // Fetches the maximum temperature from the last 5 minutes.
        model.addAttribute("temperature", maxTemperature);
        // Adds the temperature to the model.
        model.addAttribute("type", "Maximum");
        // Specifies that this is the maximum temperature.
        model.addAttribute("timeFrame", "last 5 minutes");
        // Adds a dynamic timeframe description.
        return "Template_Temperature_Display";
        // Returns the Thymeleaf template name for displaying temperature.
    }
    // Example URL: http://localhost:8080/api/temperature/max-last-5-minutes

    // Method for fetching the minimum temperature from the last 5 minutes
    @GetMapping("/min-last-5-minutes")
    // Maps HTTP GET requests at "/api/temperature/min-last-5-minutes" to this method.
    public String getMinTemperatureLast5Minutes(Model model) {
        Double minTemperature = temperatureService.getMinTemperatureInLast5Minutes();
        // Fetches the minimum temperature from the last 5 minutes.
        model.addAttribute("temperature", minTemperature);
        // Adds the temperature to the model.
        model.addAttribute("type", "Minimum");
        // Specifies that this is the minimum temperature.
        model.addAttribute("timeFrame", "last 5 minutes");
        // Adds a dynamic timeframe description.
        return "Template_Temperature_Display";
        // Returns the Thymeleaf template name for displaying temperature.
    }
    // Example URL: http://localhost:8080/api/temperature/min-last-5-minutes

    // Method for fetching temperature readings from a specific date
    @GetMapping("/readings-from-date")
    // Maps HTTP GET requests at "/api/temperature/readings-from-date" to this method.
    public String getReadingsFromDate(@RequestParam("startDate")
                                      // Retrieves the "startDate" parameter from the request.
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate, Model model) {
        List<TemperatureReading> readings = temperatureService.getReadingsFromDate(startDate);
        // Fetches the readings starting from the specified date.
        model.addAttribute("readings", readings);
        // Adds the list of readings to the model.
        model.addAttribute("pageTitle", "Temperature Readings from " + startDate.toLocalDate());
        // Sets a dynamic title using the provided start date.
        return "Templates_Temperature_Readings";
        // Returns the template for displaying readings.
    }
    // Example URL: http://localhost:8080/api/temperature/readings-from-date?startDate=2024-09-23T00:00:00

    // Method for fetching today's temperature readings
    @GetMapping("/readings-today")
    // Maps HTTP GET requests at "/api/temperature/readings-today" to this method.
    public String getTodaysReadings(Model model) {
        List<TemperatureReading> readings = temperatureService.getTodaysReadings();
        // Fetches today's temperature readings from the service.
        model.addAttribute("readings", readings);
        // Adds the readings to the model.
        return "Templates_Temperature_Readings";
        // Returns the Thymeleaf template for displaying today's readings.
    }
    // Example URL: http://localhost:8080/api/temperature/readings-today


    // METHODS FOR SPRINGBOOT WEB-BASED INTERACTIONS

   /*
    // GET endpoint to return ALL temperature readings in JSON format (for web API)
    @GetMapping("/readings")
    public List<TemperatureReading> getAllTemperatureReadings() {
        return temperatureService.getAllReadings();
        // Fetches all temperature readings and returns them as a JSON response.
    }
    // Example URL: http://localhost:8080/api/temperature/readings

    // Endpoint to get the highest temperature in the last 5 minutes in JSON format
    @GetMapping("/max-last-5-minutes")
    public Double getMaxTemperatureLast5Minutes() {
        return temperatureService.getMaxTemperatureInLast5Minutes();
        // Returns the maximum temperature from the last 5 minutes as a JSON response.
    }
    // Example URL: http://localhost:8080/api/temperature/max-last-5-minutes

    // Endpoint to get the lowest temperature in the last 5 minutes in JSON format
    @GetMapping("/min-last-5-minutes")
    public Double getMinTemperatureLast5Minutes() {
        return temperatureService.getMinTemperatureInLast5Minutes();
        // Returns the minimum temperature from the last 5 minutes as a JSON response.
    }
    // Example URL: http://localhost:8080/api/temperature/min-last-5-minutes

    // Endpoint to get readings from a specific date in JSON format
    @GetMapping("/readings-from-date")
    public List<TemperatureReading> getAllTemperatureReadingsFromDate(
            @RequestParam("startDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate) {
        return temperatureService.getReadingsFromDate(startDate);
        // Fetches readings from the specified date and returns them as a JSON response.
    }
    // Example URL: http://localhost:8080/api/temperature/readings-from-date?startDate=2024-09-23T00:00:00

    // Endpoint to get today's readings in JSON format
    @GetMapping("/readings-today")
    public List<TemperatureReading> getTodaysTemperatureReadings() {
        return temperatureService.getTodaysReadings();
        // Returns today's readings as a JSON response.
    }
    // Example URL: http://localhost:8080/api/temperature/readings-today
   */

   /*
   // Test method to verify Thymeleaf template rendering
   @GetMapping("/test-thymeleaf")
   public String testThymeleaf(Model model) {
       model.addAttribute("message", "Hello Thymeleaf!");
       // Adds a test message to the model.
       return "test";
       // Returns a simple Thymeleaf template for testing purposes.
   }
   // Example URL: http://localhost:8080/api/temperature/test-thymeleaf
   */

}

/*
Imports: The necessary Spring components, model handling, and date formatting classes are imported.
Controller Annotations:
@Controller:
    Marks this class as a controller that serves Thymeleaf templates.

@RequestMapping("/api/temperature"):
    Sets a base URL for all endpoints in
*/
