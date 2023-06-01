# Earthquake Application

This is a simple Spring Boot application that retrieves earthquake information from the USGS (United States Geological Survey) API based on specified state and number of days.

## Features

- Retrieve earthquake information for a specific country within a given number of days.
- Display the earthquake data in JSON format.
- Handle requests using the REST API endpoints.

## Build Instructions

1. Clone the repository to your local machine using Git.

2. Open the project in your preferred IDE.

3. Build the project using your IDE or the command line:
    - IDE: Import the project as a Gradle project and let the IDE handle the build process.
    - Command Line: Run `./gradlew build` in the project's root directory.

4. Run the application using your IDE or the command line:
    - IDE: Execute the `main` method in the `EarthquakeApplication` class.
    - Command Line: Run `./gradlew bootRun` in the project's root directory.

5. Access the API endpoints at port 8080 using a tool like cURL or Postman.

## API Endpoints

- `GET /`: Displays a welcome message and provides instructions on how to use the API.
- `GET /{country}/{numberOfDays}`: Retrieves earthquake information for the specified country within the specified number of days.

## Example Usage

To retrieve earthquake information for the state of Hawaii within the last 10 days, make a GET request to the following endpoint:

```
GET /turkey/10
```

This will return a JSON response containing the earthquake data specific to Turkey within the last 10 days.

Example output:
```
[
  {
    "Country": "Turkey",
    "Magnitude": 4,
    "Date": "Tue May 23 04:12:47 TRT 2023",
    "Location": "17 km WSW of Göksun, Turkey"
  },
  {
    "Country": "Turkey",
    "Magnitude": 4.3,
    "Date": "Mon May 22 19:42:58 TRT 2023",
    "Location": "18 km SE of Saimbeyli, Turkey"
  },
  {
    "Country": "Turkey",
    "Magnitude": 4.4,
    "Date": "Mon May 22 19:06:58 TRT 2023",
    "Location": "16 km ESE of Saimbeyli, Turkey"
  }
]

```

Note: Replace "turkey" with the desired country or state name (e.g., "california", "alaska", "russia" etc.) and "10" with the desired number of days.

## Dependencies

The Earthquake Application relies on the following dependencies:

- Spring Boot Starter Web: Provides the necessary components for building a web application using Spring MVC.
- org.json: A library for working with JSON data in Java.
- RestTemplate: A class provided by Spring for making HTTP requests.

## License

This project is licensed under the [MIT License](LICENSE).
