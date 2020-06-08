TDD practice assignment
=====================================
A step-by-step introduction.

System Requirements:
--------------------
- OpenJDK for Java 1.8
- Git
- Gradle 
- Retrofit rest client
- MockWebServer 
- Project Lombok https://projectlombok.org

Building the example project:
-----------------------------

To build the fat JAR and run tests:

    ./gradlew build test

Run:

    java -jar build/libs/amazinkart-1.0.0.jar promotionSetA

Jacoco tst coverage:
    
    ./gradlew jacocoTestReport


Assumptions:
-------------------------
- Assumed promotionSetA if not argument supplied 
- Assumed single threaded.
- JUnit coverage using jacoco-plugin.

References:
-----------


