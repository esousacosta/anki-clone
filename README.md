# Running
To run this project, follow these steps:
1. Clone the repository to your local machine:
   ```
   git clone <repository_url>
   ```
2. Navigate to the project directory:
   ```
   cd <project_directory>
   ```
3. Run docker-compose to start the services:
   ```
   docker-compose up -d
   ```
4. Access the application in your web browser at:
   ```
   http://localhost:8080
   ```

# Inspecting the database
To inspect the database, you can use the following command to access the database container:
    ```
    docker exec -it <database_container_name> psql -U <username> -d <database_name>
    ``` 

Example:
    ```
    docker exec -it anki-clone-postgres-1 psql -U edesousacosta -d anki_clone_db
    ```
