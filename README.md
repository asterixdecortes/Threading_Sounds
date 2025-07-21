# Threading_Sounds
Application where TCS training will be developed.
We will create a Java Spring Boot application as a backend service to manage music songs allocated in our database.

## Architecture
- **Spring Boot**: API REST.
- **PostgreSQL**: Relational database.
- **GitHub**: Version management.

## Requirements
- CRUD (title, artist, album, lenght).
- Error handling.
- Logging.
- Health checks.

## Endpoints REST
- `GET /songs`
- `POST /songs`
- `PUT /songs/{id}`
- `DELETE /songs/{id}`

## ERD Schema
Actually a really simple one as it is only one table, you can check it [here](https://dbdiagram.io/d/Threading-Sounds-687ab8c2f413ba35089e6b66) :

![ERD Diagram](/documentation_imgs/ERD.png)

## Development process
1. Analysis and design.
2. Database modeling.
3. API implementation.
4. Testing and final documentation.

## Walkthrough

### Database creation
First we will need to create our PostgreSQL database and user, to do so:
```bash
sudo -u postgres psql
```

Once you are inside the sql console

```sql
CREATE DATABASE threading_sounds;
CREATE USER ts_user WITH PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE threading_sounds TO ts_user;
```

Then we will connect to the database and give all permissions needed to that user and make new objects inherit privileges.

```sql
GRANT CREATE ON SCHEMA public TO ts_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO ts_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO ts_user;
GRANT USAGE ON SCHEMA public TO ts_user;

--inheritance
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO ts_user;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO ts_user;
```

We will not create a table here as we will do that in our code, anyway you should create a test one to check your privileges.

### Spring initializr
We will visit the page of [Spring initializr](https://start.spring.io/) and fill the options as the image

![Spring Initializr Configuration](/documentation_imgs/Spring_Initializr.png)

The 3 dependencies:
- Spring Web: includes many functions for web development, needed to create a RESTful API.
- Spring Data JPA: makes the access and management of the database from our Spring Boot app easier.
- PostgreSQL Driver: driver needed to connect to a PostgreSQL database.

Now we can create a hello world class inside our /src/main/com/threadingsounds/...

```java
@GetMapping("/hello")
    public String hello() {
        return "Hello world";
	}
```

We need to check that our pom.xml has the dependencies for the postgreSQL Database and drivers

```xml
<dependency>
	<groupId>org.postgresql</groupId>
	<artifactId>postgresql</artifactId>
	<scope>runtime</scope>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

Then, inside /src/main/resources/application.properties

```properties
spring.application.name=Threading-Sounds

spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
```

Where:
- url: the url where our database is hosted, by default PostgreSQL runs on port 5432
- username: the user we created to access the database, this is important as to not to access it as an admin which can be insecure
- password: password given to the user
- jpa.hibernate: this will declare the way the database behaves.

The ones starting with $ are environment variables so that you can define them in your system with

```bash
export DB_URL=jdbc:postgresql://localhost:5432/threading_sounds
export DB_USERNAME=ts_user
export DB_PASSWORD=password
```

Or if you docker this app, save them in a .env looking like:

```env
DB_URL=jdbc:postgresql://db:5432/threading_sounds
DB_USERNAME=ts_user
DB_PASSWORD=supersegura
```

Once this is done, we can run in our terminal

```bash
./mvnw spring-boot:run
```

and if we visit http://localhost:8080/hello or make a curl request, we should see our hello world

![Hello World](/documentation_imgs/Hello_World.png)

I forgot to add lombok as a dependency so we have to add it in our pom.xml 
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```

### Songs
Each of these objects will represent a song, taking the data from the database
```java
@Table(name="songs")
@Column (name = "title", nullable = false)
```
That first line will create a table if needed or edit the one existing.
The second one will manage the colums.

### SongsRepository

### MainController

### MainService
