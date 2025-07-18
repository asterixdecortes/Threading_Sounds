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
- logging.
- health checks.

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

Then, we can create the table needed

```sql
CREATE TABLE songs (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    artist VARCHAR(255) NOT NULL,
    album VARCHAR(255) NOT NULL,
    length INT NOT NULL
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

# WIP, /HELLO CRASHING NEEDED CONNECTION TO DATABASE