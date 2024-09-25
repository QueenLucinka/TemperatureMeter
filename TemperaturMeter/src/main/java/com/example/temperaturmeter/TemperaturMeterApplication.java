package com.example.temperaturmeter;
//this is main class which we run
import org.springframework.beans.factory.annotation.Autowired;// Importing the @Autowired annotation for automatic dependency injection.
import org.springframework.boot.CommandLineRunner;// Importing the CommandLineRunner interface, which allows us to run code when the application starts.
import org.springframework.boot.SpringApplication;// Importing SpringApplication, which is used to bootstrap and launch the Spring Boot application.
import org.springframework.boot.autoconfigure.SpringBootApplication;// Importing the @SpringBootApplication annotation, which enables component scanning, auto-config. and more.

@SpringBootApplication
public class TemperaturMeterApplication implements CommandLineRunner {

    @Autowired
    private SerialCommunicationListener serialService;  // Inject the serial communication service

    public static void main(String[] args) {
        SpringApplication.run(TemperaturMeterApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Initialize the serial communication when the application starts
        serialService.initializeSerialPort();
    }

}

/*
    WHAT SPECIFIC IMPORTS ARE AND WHY WE NEED THEM IN THIS PROJECT:
import org.springframework.beans.factory.annotation.Autowired;
Purpose:
    This line imports the @Autowired annotation from Spring Framework.
    @Autowired is used for automatic dependency injection, allowing Spring to inject a bean
    (in this case, the SerialCommunicationListener service) into a class automatically.

import org.springframework.boot.CommandLineRunner;
Purpose:
    This imports the CommandLineRunner interface from Spring Boot.
    Implementing this interface allows the TemperaturMeterApplication class to run specific code
    (like initializing a service) when the application starts.

import org.springframework.boot.SpringApplication;
Purpose:
    This imports the SpringApplication class from Spring Boot.
    SpringApplication.run() is used to launch the Spring Boot application.

import org.springframework.boot.autoconfigure.SpringBootApplication;
Purpose:
    This imports the @SpringBootApplication annotation from Spring Boot.
    This annotation is essential in Spring Boot applications as it enables several key features,
    including component scanning, auto-configuration, and the ability to define a Spring Boot application.

COMMENTS FOR LINES
@SpringBootApplication
Purpose:
    This annotation is a combination of three other annotations:
    @Configuration: Allows the class to be a source of bean definitions.
    @EnableAutoConfiguration: Enables Spring Boot’s auto-configuration mechanism.
    @ComponentScan: Tells Spring to scan the package for Spring-managed beans or components.
     This annotation marks the class as the starting point for the Spring Boot application.

public class TemperaturMeterApplication implements CommandLineRunner {
Purpose:
    This declares the TemperaturMeterApplication class, which implements the CommandLineRunner interface.
    This means the class must implement the run() method, which will be executed after the application context is loaded,
    allowing the application to run additional logic after startup.

 @Autowired
Purpose:
    This annotation is used for automatic dependency injection.
    In this case, it tells Spring to inject an instance of SerialCommunicationListener
    into the serialService variable at runtime.

private SerialCommunicationListener serialService;
Purpose:
    This declares a private field of type SerialCommunicationListener.
    The @Autowired annotation ensures that an instance of SerialCommunicationListener is injected by Spring,
    allowing the class to use the serial communication functionality.

public static void main(String[] args) {
Purpose:
    This is the main method that serves as the entry point for the Java application.
    When you run the Java program, the JVM starts executing the code inside this method.

SpringApplication.run(TemperaturMeterApplication.class, args);
Purpose:
    This line starts the Spring Boot application.
    It launches the embedded server and creates the application context (Spring container),
    which manages the lifecycle of beans (such as serialService) and their dependencies.

@Override
Purpose:
    This annotation indicates that the run() method below is an override of a method declared
    in the CommandLineRunner interface. It ensures that you are correctly overriding an inherited method.

public void run(String... args) throws Exception {
Purpose:
    This is the run() method from the CommandLineRunner interface,
    which is executed after the application starts. The String... args parameter allows the method
    to accept a variable number of arguments passed to the application at runtime.
    The throws Exception part indicates that this method can throw exceptions if something goes wrong.

serialService.initializeSerialPort();
Purpose:
    This calls the initializeSerialPort() method on the serialService (which was injected by Spring).
    The purpose is to initialize the serial communication when the application starts.

*/
