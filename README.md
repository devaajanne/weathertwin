# Weather Twin

Weather Twin a fun, silly little app to find which city in the world has the same weather as your location.

This is the repository for the server-side application. For the client-side repository, see here: [Weather Twin client](https://github.com/devaajanne/weathertwin-client).

## About the project
Weather Twin is a hobby project alongside my studies. I wanted to learn more about back and front end development and deepen my knowledge about the technologies used in the project. I also wanted to create an application that requires no log-in so that the app is simple, easy to use, and hopefully fun as well!

## Deployment

Weather Twin is now live at GitHub pages! See the live demo of the app here: [Weather Twin](https://devaajanne.github.io/weathertwin-client/)

## How it works

Weather Twin's server app works by

1. fetching weather data from OpenWeatherMap.org API for 1200 locations around the world every hour
2. converting fetched data from JSON to WeatherData ojects with only the fields we need from the JSON
3. saving fetched weather data to PostgreSQL
4. getting the user's input location's name and coordinates as well as desired temperature unit from the client
5. fething weather data for the user's input location and converting the received weather data from JSON to WeatherData object
6. querying the database for similar locations that experience the same weather but are not right next to each other with the following criteria:

- user's input location must be excluded
- locations' temperature must be at most 1 degree kelvin (1 °C or 1.8 °F) apart from each other
- locations' latitude coordinates must be at least 20 degrees from each other
- locations' longitude coordinates must be at least 20 degrees from each other
- locations must experience the same weather conditions (clouds, rain, clear etc)

7. getting a random location from the list of similar locations, or null if no similar location found
8. saving input and similar location WeatherData objects into a map and returning it to the client

## Technologies

The server-side of this application has been written in [Java](https://www.java.com/en/) version 23, using [Spring Boot](https://spring.io/projects/spring-boot) version 3.4.2 and [Gradle](https://gradle.org/) version 8.11.1. Database has been created with [PostgreSQL](https://www.postgresql.org/) with database migration with [Liquibase](https://www.liquibase.com/). For reading JSON files, [Jackson](https://github.com/FasterXML/jackson) library is used.

Server-side app and PostgreSQL database are deployed on [Heroku](https://www.heroku.com/).

For the front-end side of the project, written in JavaScript and React, see this repository: [Weather Twin client](https://github.com/devaajanne/weathertwin-client).

## API documentation

The app provides one endpoint, api/weatherdata.

The endpoint allows fetching weather data for the input location, and querying the repository for a similar weather data. This endpoint does not require authentication or authorization.

As a result, the endpoint retuns the weather for the input location as well as the weather for a location that has a similar weather.

### Weather data (/api/weatherdata)

<details>
<summary>Click to see detailed API documentation</summary>

**URL** : `/api/weatherdata`

**Method** : `POST`

**Auth required** : No

**Path parameters** : None

**Request body content** :

The request body should be a JSON object. It must include the following fields:

| Field        | Type   | Required | Description                                                                                        |
| ------------ | ------ | -------- | -------------------------------------------------------------------------------------------------- |
| `cityName`   | String | YES      | The name of the input city. Can include the city's country, separated by comma.                    |
| `cityCoords` | JSON   | YES      | The coordinates of the city. Must include `lat` and `lon` fields and their values as float/double. |
| `unit`       | String | YES      | Response unit. Must be `standard`/`null`/empty, `metric`, or `imperial`.                           |

#### Example request

`POST /api/weatherdata`

```json
{
  "cityName": "Helsinki, Finland",
  "cityCoords": { "lat": 60.16985569999999, "lon": 24.938379 },
  "unit": "metric"
}
```

### Success reponses

**Condition** : Data provided in the body is valid and a similar location has been found.

**Code** : `200 OK`

**Content example** : Returns a JSON object with two properties: `inputLocation` and `similarLocation`. Both include the weather data for their respective locations. `temp` is in the unit given in the body.

```json
{
  "similarLocation": {
    "city": "Fareydūnshahr",
    "countryCode": "IR",
    "countryName": "Iran",
    "id": 134601,
    "lat": 32.941,
    "lon": 50.121,
    "temp": -5.6,
    "tempUnit": "°C",
    "weatherGroup": "Clouds",
    "weatherIcon": "03d"
  },
  "inputLocation": {
    "city": "Helsinki",
    "countryCode": "FI",
    "countryName": "Finland",
    "id": 658225,
    "lat": 60.1675,
    "lon": 24.9427,
    "temp": -5.4,
    "tempUnit": "°C",
    "weatherGroup": "Clouds",
    "weatherIcon": "03d"
  }
}
```

**Condition** : Data provided in the body is valid but a similar location has not been found.

**Code** : `200 OK`

**Content example** : Returns a JSON object with two properties: `inputLocation` and `similarLocation`. Only the `inputLocation` contains its respective weather data, whereas `similarLocation` is `null`. `temp` is in the unit given in the body.

```json
{
  "similarLocation": null,
  "inputLocation": {
    "city": "Helsinki",
    "countryCode": "FI",
    "countryName": "Finland",
    "id": 658225,
    "lat": 60.1675,
    "lon": 24.9427,
    "temp": -5.4,
    "tempUnit": "°C",
    "weatherGroup": "Clouds",
    "weatherIcon": "03d"
  }
}
```

</details>

## Contact

You can contact me through [LinkedIn](https://www.linkedin.com/in/janair/) or by email at janne.airaksinen.mail(at)gmail.com.

## License

There are two JSON files of used in this application:

- ISO3166_CountryCodesAndNames.json
- citiesLatsAndLons.json

These files are located in `src/main/resources`. These have been created with the help of AI. The base for the files have been JSON files found in these two GitHub repositories:

- [cities-json by lmfmaier](https://github.com/lmfmaier/cities-json)
- [ISO-3166-Countries-with-Regional-Codes by lukes](https://github.com/lukes/ISO-3166-Countries-with-Regional-Codes)

As their works have been licenced with Creative Commons 4.0 license, the JSON files in this repository have also been licensed under [Creative Commonce 4.0 license](https://creativecommons.org/licenses/by-sa/4.0/)
