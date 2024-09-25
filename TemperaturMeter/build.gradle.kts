plugins {
    java
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot starter for working with JPA (Java Persistence API) and relational databases.
    // It includes Hibernate as the default JPA provider.
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Spring Boot starter for Thymeleaf, a template engine used to render web pages.
    // This integrates Thymeleaf into a Spring Boot application.
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    // Spring Boot starter for building web applications, including RESTful services.
    // It provides features like embedded Tomcat and Spring MVC.
    implementation("org.springframework.boot:spring-boot-starter-web")

    // jSerialComm library for serial communication with devices like Arduino.
    // This allows reading/writing data through serial ports.
    implementation ("com.fazecast:jSerialComm:2.6.2")

    // Lombok library for reducing boilerplate code like getters, setters, constructors, etc.
    // The 'compileOnly' scope ensures that Lombok is only required during the compilation process.
    compileOnly("org.projectlombok:lombok")

    // Spring Boot DevTools provides development-time features like automatic restarts and live reloading.
    // This should only be used in the development environment.
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // MySQL Connector/J, the JDBC driver for connecting a Spring Boot application to a MySQL database.
    // This is needed at runtime to communicate with the database.
    runtimeOnly("com.mysql:mysql-connector-j")

    // Annotation processor for Lombok, enabling the automatic generation of boilerplate code like getters, setters, etc.
    annotationProcessor("org.projectlombok:lombok")

    // Spring Boot starter for testing, including libraries like JUnit, Mockito, and Spring Test.
    // This is required to write and run unit and integration tests.
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // JUnit Platform Launcher is used to run tests in a JUnit 5 environment.
    // This is required only at runtime for executing the tests.
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


tasks.withType<Test> {
    useJUnitPlatform()
}
