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

We will create the database here to import some data to test but if you don't, the application will create it for you
```sql
-- DROP TABLES IF EXIST
DROP TABLE IF EXISTS playlist_songs;
DROP TABLE IF EXISTS playlists;
DROP TABLE IF EXISTS songs;
DROP TABLE IF EXISTS albums;
DROP TABLE IF EXISTS artists;

-- ARTISTS
CREATE TABLE artists (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

-- ALBUMS
CREATE TABLE albums (
    id SERIAL PRIMARY KEY,
    title VARCHAR NOT NULL,
    artist_id INTEGER NOT NULL,
    CONSTRAINT fk_album_artist FOREIGN KEY (artist_id) REFERENCES artists(id) ON DELETE CASCADE
);

-- SONGS
CREATE TABLE songs (
    id SERIAL PRIMARY KEY,
    title VARCHAR NOT NULL,
    artist_id INTEGER NOT NULL,
    album_id INTEGER,
    length INT NOT NULL,
    CONSTRAINT fk_song_artist FOREIGN KEY (artist_id) REFERENCES artists(id) ON DELETE CASCADE,
    CONSTRAINT fk_song_album FOREIGN KEY (album_id) REFERENCES albums(id) ON DELETE SET NULL
);

-- PLAYLISTS
CREATE TABLE playlists (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    description VARCHAR
);

-- PLAYLIST_SONGS (Join Table)
CREATE TABLE playlist_songs (
    playlist_id INTEGER NOT NULL,
    song_id INTEGER NOT NULL,
    PRIMARY KEY (playlist_id, song_id),
    CONSTRAINT fk_playlist FOREIGN KEY (playlist_id) REFERENCES playlists(id) ON DELETE CASCADE,
    CONSTRAINT fk_song FOREIGN KEY (song_id) REFERENCES songs(id) ON DELETE CASCADE
);

```

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
Interface which inherits from JpaRepository where the majority of the functions needed in the service are declared.

### Services
Made a Service with every entity in the DatabaseSchema, at the moment all have more or less the same methods so I will show here only from MainService(SongService is the real meaning).

```java
public SongDto convertToDto(Song song)
public Song convertToEntity(SongDto dto)
```
These two are conversions between Entities and Data Transfer Object, needed for security reasons and the app working properly

```java
public Song createSong(Song song, Long artistId, Long albumId)
```
Will receive a new song and insert it inside the Database

```java
public SongDto getSongById(Long id)
public List<SongDto> getAllSongs()
```
Will extract all the info from a song using its id or all the songs in the database

```java
public Song updateSong(Long songId, Song updatedSong, Long artistId, Long albumId)
```
Will edit the data from a song already existing.

```java
public void deleteSong(Long songId)
```
Will delete a song existing from the database

IMPORTANT 
#### Exceptions
Every request that does not find the information will throw a made exception 
```java
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message){
        super(message);
    }
}
```

### Controllers
Made a Controller with every entity in the DatabaseSchema, at the moment all have more or less the same methods so I will show here only from MainController(MainController is the real meaning).

They use the same methods as the service and "translate" them to HTTP Mapping.

Every method will return code 200 if ok or its respecting error code. 

This method does not throw exceptions, they are thrown by the service

### Health Checks
We will use spring actuator, after addind it in pom.xml, in application.properties, we need to add 
```properties
management.endpoints.web.exposure.include=health,info
management.endpoint.health.show-details=always
```

This way if you visit http://localhost:8080/actuator/health
You will receive a json with important information about your backend machine and if the app is running.

### Testing
We will add this dependency
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

Then we can start with the tests, I included 4 for the Main Service very simple and self explainatory by their names

### Logging
The logs are in file /logs/threadingsounds.log

Currently only logging on MainService.createSong, MainService.getSongById and the whole Spring app

### Endpoints Documentation
Using right now [springdoc](https://springdoc.org/#Introduction) to generate it, you can see its UI at 
http://server:port/context-path/swagger-ui.html