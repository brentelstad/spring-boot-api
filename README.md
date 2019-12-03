# spring-boot-api
Spring boot api using docker compose with tomcat and postegresql. Liquibase is used for change management on database.

#Building the Project
run the command from the root project directory: mvn clean package (may need to run with optional "-DskipTests=true" due to database being in seperate docker)

#Deployig the Application
run the command form the directory docker-compose.yml is located: docker-compose up
