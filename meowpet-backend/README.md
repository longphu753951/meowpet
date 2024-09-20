### BUILD CONTAINER FOR POSTGRESQL AND SPRING BOOT WITH DATABASE
## How to build container for postgresql:
```sh
docker run --rm -d \
--name postgresql-petpal-container \
--network petpal-app-network \
-v postgresql-springboot-data:/var/lib/postgresql/data \
-v postgresql-springboot-config:/etc/postgresql \
-p 5433:5432 \
-e POSTGRES_USER=phutl \
-e POSTGRES_PASSWORD=qwpo1234 \
-e POSTGRES_DB=petpaldbdev \
postgres:latest
```
--rm: Automatically remove the container when it exits. This ensures that no stopped containers are left behind.
-d: Run the container in detached mode (in the background).
--name postgresql-petpal-container: Assign a name to the container for easier reference.
--network petpal-app-network: Connect the container to the specified Docker network (petpal-app-network). This allows the container to communicate with other containers on the same network.
-v postgresql-springboot-data:/var/lib/postgresql/data: Mount the volume postgresql-springboot-data to /var/lib/postgresql/data inside the container. This is where PostgreSQL stores its data files, ensuring data persistence.
-v postgresql-springboot-config:/etc/postgresql: Mount the volume postgresql-springboot-config to /etc/postgresql inside the container. This allows you to manage PostgreSQL configuration files.
-p 5433:5432: Map port 5432 inside the container to port 5433 on the host machine. This allows you to access PostgreSQL on localhost:5433 from your host machine.
-e POSTGRES_USER=phutl: Set the PostgreSQL user to phutl.
-e POSTGRES_PASSWORD=qwpo1234: Set the PostgreSQL password to qwpo1234.
-e POSTGRES_DB=petpaldbdev: Create a PostgreSQL database named petpaldbdev.
postgres:latest: Use the latest version of the official PostgreSQL Docker image.

### Building the Docker Image for the Application

To build the Docker image for your Spring Boot application, use the following command:

```sh
docker build -t longphu753951/pet-pal-backend-image . 
```

docker build: This command is used to build a Docker image from a Dockerfile.
-t longphu753951/pet-pal-backend-image: The -t option tags the image with a name. In this case, the image is tagged as longphu753951/pet-pal-backend-image. This makes it easier to reference the image later.
.: The dot at the end of the command specifies the build context. It tells Docker to look for the Dockerfile and other necessary files in the current directory.

### Running the Spring Boot Application Container

To run the Spring Boot application container, use the following command:

```sh
docker run --rm -d \
--name petpal-app-container \
--network petpal-app-network \
-p 8085:8085 \
longphu753951/pet-pal-backend-image
```
--rm: Automatically remove the container when it exits. This ensures that no stopped containers are left behind.
-d: Run the container in detached mode (in the background).
--name petpal-app-container: Assign a name to the container for easier reference.
--network petpal-app-network: Connect the container to the specified Docker network (petpal-app-network). This allows the container to communicate with other containers on the same network.
-p 8085:8085: Map port 8085 on the host to port 8085 in the container. This allows you to access the Spring Boot application on localhost:8085 from your host machine.
longphu753951/pet-pal-backend-image: The name of the Docker image to use for creating the container. This should be the image you built earlier.


### REPLACE ALL COMMAND WITH DOCKER COMPOSER