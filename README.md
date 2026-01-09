Eco‑Station Management Hub is a desktop dashboard application built with Java Swing that helps visualize and manage an EV charging network. It provides an admin‑style interface with live station metrics, energy and revenue analytics, and user authentication.

Features
Secure login & registration

Simple username/password authentication for admin users.

Separate windows for login and sign‑up.

Dashboard overview

Summary cards for:

Total stations

Total energy (kWh)

Total revenue (currency)

Energy Consumption Flow chart for a selected hub (e.g., Pune Hub) with a smooth line graph.

Stations Management

View all stations in a structured table.

Add new stations using a dedicated form.

Edit station details (code, name, location, status, energy, revenue).

Delete stations that are no longer active.

Analytics

Total station count.

Total energy and revenue.

Station counts per status (AVAILABLE / CHARGING / MAINTENANCE).

Average revenue per station.

Top‑revenue station summary.

Tech Stack
Language: Java (JDK 8+ recommended)

UI: Java Swing

Database: MySQL (via JDBC)

Architecture:

DAO (Data Access Object) layer for database operations

Separate UI windows/panels for login, register, dashboard, stations, and analytics

Database Setup
Create a MySQL database, for example:

sql
CREATE DATABASE eco_station;
USE eco_station;
Create tables (simplified example):

sql
CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL
);

CREATE TABLE stations (
  id INT AUTO_INCREMENT PRIMARY KEY,
  code VARCHAR(20) NOT NULL,
  name VARCHAR(100) NOT NULL,
  location VARCHAR(100),
  status VARCHAR(20),         -- e.g., AVAILABLE, CHARGING, MAINTENANCE
  energy_kwh INT DEFAULT 0,
  revenue DOUBLE DEFAULT 0
);
Update your database connection in DBUtil.java:

java
private static final String URL = "jdbc:mysql://localhost:3306/eco_station";
private static final String USER = "your_mysql_user";
private static final String PASS = "your_mysql_password";
(Use your actual credentials.)

How to Run
Clone the repository

bash
git clone https://github.com/<your-username>/eco-station-hub-java.git
cd eco-station-hub-java
Add JDBC driver

Place the MySQL Connector JAR (for example mysql-connector-j-9.5.0.jar) into the lib/ folder and ensure the filename matches the classpath you use.

Compile

bash
javac -cp .;src;lib\mysql-connector-j-9.5.0.jar;lib\jdbc-api-1.4.jar src\app\Main.java src\dao\*.java src\model\*.java src\ui\*.java
(On Linux/macOS, replace ; with : in the classpath.)

Run

bash
java -cp .;src;lib\mysql-connector-j-9.5.0.jar;lib\jdbc-api-1.4.jar app.Main
Possible Improvements
Replace static chart data with real‑time data derived from station history.

Add more charts (connector distribution donut, revenue vs. energy bar chart).

Add role‑based access (admin vs. viewer).

Migrate UI to JavaFX or a web frontend for richer visuals.

Containerize the app with Docker for easier deployment
