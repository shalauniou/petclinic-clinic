# Pet Clinic Service

# Configuration
* Add `management.security.enabled: false` property into `application.yml` file if you need to connect to secured endpoints from actuator (don't commit that configuration) 

# Prepare plugins
Download the petclinic-plugins and read readme.md

# Running
Create the database : configure gradle.properties and run gradlew createDatabase(for dropping run: gradlew dropDatabase)
Populate the database : gradlew update (could be done independently from bootRun)
For tag or rollback use: gradlew tag/rollback -PliquibaseCommandValue=<version> (could be done independently from bootRun)
Disable @EnableEurekaClient(move temporary bootstrap.yml) or run it on http://127.0.0.1:8080/eureka/
Run from project root: gradlew bootRun

#Security
Read Security testing section from "Microservices PoC: Pet Clinic" document on docs.google.com or
change `.authenticated()` to `.permitAll()` in code - for temporary disabling

# Dependencies
For more details about project dependencies use:  gradlew projectReport
Also spring boot version:1.5.2.RELEASE used dependencies from
http://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-parent/1.5.2.RELEASE
and additionally for version configuration with org.springframework.cloud:spring-cloud-dependencies:Dalston.RC1 see
https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-dependencies/Dalston.RC1
