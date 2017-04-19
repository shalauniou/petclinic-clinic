# Pet Clinic Service

## Configuration
* Add `management.security.enabled: false` property into `application.yml` file if you need to connect to secured endpoints from actuator (don't commit that configuration) 

# Running
Create data base with name petclinic
Set postgres password in the application.yml for spring.datasource.password
Disable @EnableEurekaClient or run it
Run from project root: gradle bootRun

