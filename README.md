<a id="readme-top"></a>

# Weather Twin

Weather Twin a fun, silly little app to find which city in the world has the same weather as your location.
<br>
<details>
<summary><b>Table of Contents</b></summary>
  <ol>
    <li>
        <a href="#about-the-project">About the project</a>
    </li>
    <li>
        <a href="#deployment">Deployment</a>
    </li>
    <li>
        <a href="#how-it-works">How it works</a>
    </li>     
    <li>
        <a href="#technologies">Technologies</a>
    </li>
    <li>
        <a href="#api-documentation">API documentation</a>
    </li>
    <li>
        <a href="#contact">Contact</a>
    </li>
    <li>
        <a href="#license">License</a>
    </li>
  </ol>
</details><br>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## About the project
Enter a location - your own city perhaps - and see where in the world is there a city that is experiencing the same weather at the moment!

Weather Twin is a hobby project alongside my studies. I wanted to learn more about back and front end development and deepen my knowledge about the technologies used in the project. I also wanted to create an application that requires no log-in so that the app is simple, easy to use, and hopefully fun as well!

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Deployment

Weather Twin is now live at GitHub pages! See the live demo of the app here: [Weather Twin](https://devaajanne.github.io/weathertwin/)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## How It Works

### Client-side
1) Gets the user's desired city through React Google Places Autocomplete input field.
2) Gets the user's desired temperature unit (Celsius or Fahrenheit).
3) Finds the coordinates (latitude and longitude) for the selected city.
4) Submits the user's input data to the server.
5) Receives weather data for the input location and a location with similar weather.
5) Displays both weather data in the selected temperature unit.
6) Clears inputs for the next submission.

### Server-side
1) Fetches weather data from OpenWeatherMap.org API for 1200 locations around the world every hour.
2) Converts fetched data from JSON to WeatherData objects with only the needed fields.
3) Saves fetched weather data to PostgreSQL.
4) Gets the user's input location's name, coordinates, and desired temperature unit from the client.
5) Fetches weather data for the input location and converts it to a WeatherData object.
6) Queries the database for similar locations with the following criteria:
- Excludes the user's input location.
- Temperature must be at most 1 degree kelvin (1 °C or 1.8 °F) apart.
- Latitude and longitude must be at least 20 degrees apart.
- Must experience the same weather conditions (clouds, rain, clear, etc).
7) Returns a random similar location or null if none found.
8) Returns both input and similar location weather data to the client.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Technologies

### Client-side

[![JavaScript][javascript-logo]][javascript-url]
[![React][react-logo]][react-url]


This app has been written in JavaScript and [React](https://react.dev/). Installed libraries include

- [Material UI](https://mui.com/material-ui/)
- [Axios](https://www.npmjs.com/package/axios)
- [React Google Places Autocomplete](https://www.npmjs.com/package/react-google-places-autocomplete)

Weather icons use this icon pack: [Weather Icons](https://erikflowers.github.io/weather-icons/)


### Server-side
[![Java][java-logo]][java-url]
[![Spring Boot][spring-logo]][spring-url]
[![Gradle][gradle-logo]][gradle-url]
[![PostgreSQL][postgres-logo]][postgres-url]
[![Liquibase][liquibase-logo]][liquibase-url]
[![Heroku][heroku-logo]][heroku-url]

The server-side of this application has been written in [Java](https://www.java.com/en/) version 25, using [Spring Boot](https://spring.io/projects/spring-boot) version 3.5.6 and [Gradle](https://gradle.org/) version 9.1.0. Database has been created with [PostgreSQL](https://www.postgresql.org/) with database migration with [Liquibase](https://www.liquibase.com/). For reading JSON files, [Jackson](https://github.com/FasterXML/jackson) library is used. [Spotless](https://github.com/diffplug/spotless) formats server-side code.

Server-side app and PostgreSQL database are deployed on [Heroku](https://www.heroku.com/).

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## API documentation

The app provides one endpoint, `api/weatherdata`.

The endpoint allows fetching weather data for the input location, and querying the repository for a similar weather data. This endpoint does not require authentication or authorization.

As a result, the endpoint retuns the weather for the input location as well as the weather for a location that has a similar weather.

### Weather data (/api/weatherdata)

<details>
<summary>Click to see detailed API documentation</summary>

**URL** : `/api/weatherdata`

**Method** : `GET`

**Auth required** : No

**Path parameters** : None

**Query parameters**:

The query parameters must contain these parameters:

| Parameter        | Type   | Required | Description                                                                                        |
| ------------ | ------ | -------- | -------------------------------------------------------------------------------------------------- |
| `city`   | string | YES      | The name of the input city. Can include the city's country, separated by comma.                    |
| `lat` | float/double   | YES      | The latitude coordinate of the city. |
| `lon` | float/double   | YES      | The longitude coordinates of the city.  |
| `unit`       | string | YES      | Response unit. Must be `standard`/`null`/empty, `metric`, or `imperial`.                           |

**Request body content** : None

#### Example request

`GET /api/weatherdata`

`{serverURL}/api/weatherdata?city=Helsinki, Finland&lat=60.16985569999999&lon=24.938379&unit=metric`


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
    "weatherId": "802"
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
    "weatherId": "802"
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
    "weatherId": "802"
  }
}
```

</details>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Contact

[![LinkedIn][linkedin-logo]][linkedin-url]
[![Gmail][gmail-logo]][gmail-url]

You can contact me through [LinkedIn](https://www.linkedin.com/in/janair/) or by email at janne.airaksinen.mail(at)gmail.com.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## License

There are two JSON files of used in this application:

- ISO3166_CountryCodesAndNames.json
- citiesLatsAndLons.json

These files are located in `weathertwin-server/src/main/resources`. These have been created with the help of AI. The base for the files have been JSON files found in these two GitHub repositories:

- [cities-json by lmfmaier](https://github.com/lmfmaier/cities-json)
- [ISO-3166-Countries-with-Regional-Codes by lukes](https://github.com/lukes/ISO-3166-Countries-with-Regional-Codes)

As their works have been licenced with Creative Commons 4.0 license, the JSON files in this repository have also been licensed under [Creative Commonce 4.0 license](https://creativecommons.org/licenses/by-sa/4.0/)

<p align="right">(<a href="#readme-top">back to top</a>)</p>


[linkedin-logo]: https://img.shields.io/badge/linkedin-%230077B5.svg?style=for-the-badge&logo=linkedin&logoColor=white
[linkedin-url]: https://www.linkedin.com/in/janair/
[gmail-logo]: https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white
[gmail-url]: mailto:janne.airaksinen.mail@gmail.com

[java-logo]: https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white&style=for-the-badge
[java-url]: https://www.java.com/en/
[spring-logo]: https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white
[spring-url]: https://spring.io/
[gradle-logo]: https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white
[gradle-url]: https://gradle.org/
[heroku-logo]: https://img.shields.io/badge/Heroku-430098?logo=heroku&logoColor=fffe&style=for-the-badge
[heroku-url]: https://www.heroku.com/
[postgres-logo]: https://img.shields.io/badge/postgresql-4169e1?style=for-the-badge&logo=postgresql&logoColor=white
[postgres-url]: https://www.postgresql.org/
[liquibase-logo]: https://img.shields.io/badge/Liquibase-2962FF?style=for-the-badge&logo=Liquibase&logoColor=white
[liquibase-url]: https://www.liquibase.com/

[javascript-logo]: https://img.shields.io/badge/JavaScript-F7DF1E?logo=javascript&logoColor=000&style=for-the-badge
[javascript-url]: https://developer.mozilla.org/en-US/docs/Web/JavaScript
[react-logo]: https://img.shields.io/badge/React-%2320232a.svg?logo=react&logoColor=%2361DAFB&style=for-the-badge
[react-url]: https://react.dev/


