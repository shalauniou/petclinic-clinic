# Pet Clinic Service

## Configuration
* Add `management.security.enabled: false` property into `application.yml` file if you need to connect to secured endpoints from actuator (don't commit that configuration) 

# Running
Create the database : configure gradle.properties and run gradlew createDatabase(for dropping run: gradlew dropDatabase)
Populate the database : gradlew update (could be done independently from bootRun)
For tag or rollback use: gradlew tag/rollback -PliquibaseCommandValue=<version> (could be done independently from bootRun)
Disable @EnableEurekaClient(move temporary bootstrap.yml) or run it on http://127.0.0.1:8080/eureka/
Run from project root: gradlew bootRun

