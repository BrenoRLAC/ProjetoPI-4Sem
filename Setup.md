# Project Setup Guide

## Database Configuration

To use this application, follow these steps to configure the database:

### Step 1: Update `application.properties`

Modify the `application.properties` file to connect to your database:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_username
spring.datasource.password=your_password
```

```
CREATE TABLE CHANNEL (
   ID BIGINT AUTO_INCREMENT PRIMARY KEY,
   PROFILE_IMAGE LONGBLOB,
   FIRST_NAME VARCHAR(255),
   LAST_NAME VARCHAR(255),
   FULL_NAME VARCHAR(255),
   EMAIL_ADDRESS VARCHAR(255),
   PASSWORD VARCHAR(255),
   ACTIVE TINYINT(1)
);

CREATE TABLE VIDEO_STATUS (
   ID BIGINT AUTO_INCREMENT PRIMARY KEY,
   STATUS_NAME VARCHAR(255) NOT NULL,
   ACTIVE TINYINT(1)
);

INSERT INTO VIDEO_STATUS (STATUS_NAME, ACTIVE) VALUES ('PUBLICO', '1');
INSERT INTO VIDEO_STATUS (STATUS_NAME, ACTIVE) VALUES ('PRIVADO', '1');
INSERT INTO VIDEO_STATUS (STATUS_NAME, ACTIVE) VALUES ('NÃO LISTADO', '1');

CREATE TABLE VIDEO (
   ID BIGINT AUTO_INCREMENT PRIMARY KEY,
   THUMBNAIL_URL LONGBLOB NOT NULL,
   VIDEO_URL VARCHAR(255) NOT NULL,
   TITLE VARCHAR(255) NOT NULL,
   DESCRIPTION TEXT NOT NULL,
   LIKES INT,
   DISLIKES INT,
   VIEW_COUNT INT,
   ACTIVE TINYINT(1),
   VIDEO_STATUS_ID BIGINT NOT NULL,
   CHANNEL_ID BIGINT NOT NULL,
   FOREIGN KEY (CHANNEL_ID) REFERENCES CHANNEL(ID),
   FOREIGN KEY (VIDEO_STATUS_ID) REFERENCES VIDEO_STATUS(ID)
);

CREATE TABLE COMMENT (
   ID BIGINT AUTO_INCREMENT PRIMARY KEY,
   TEXT VARCHAR(255) NOT NULL,
   VIDEO_ID BIGINT NOT NULL,
   LIKE_COUNT INT,
   DISLIKE_COUNT INT,
   FOREIGN KEY (VIDEO_ID) REFERENCES VIDEO(ID)
);
```
Pom.xml:
```
<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>8.0.27</version>
		</dependency>
```
Depending on you Database, the configurations related to the Database connection and SQL statement will differ. These statements are referencing the connection to the MySql Database
