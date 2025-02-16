# Weather Twin

Weather Twin a fun, silly little app to find which city in the world has the same weather as your location.

This is the repository for the server-side application. For the client-side repository, see here: https://github.com/devaajanne/weathertwin-client.

## Deployment

Weather Twin is now live at GitHub pages! See the app here: https://devaajanne.github.io/weathertwin-client/

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
  "cityCoords": {
    "lat": 60.1675,
    "lon": 24.9427
  },
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
    "id": 134601,
    "lat": 32.941,
    "lon": 50.121,
    "city": "Fareydūnshahr",
    "countryCode": "IR",
    "countryName": "Iran",
    "weatherGroup": "Clouds",
    "temp": -5.6
  },
  "inputLocation": {
    "id": 658225,
    "lat": 60.1675,
    "lon": 24.9427,
    "city": "Helsinki",
    "countryCode": "FI",
    "countryName": "Finland",
    "weatherGroup": "Clouds",
    "temp": -5.4
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
    "id": 658225,
    "lat": 60.1675,
    "lon": 24.9427,
    "city": "Helsinki",
    "countryCode": "FI",
    "countryName": "Finland",
    "weatherGroup": "Clouds",
    "temp": -5.4
  }
}
```
</details>

## Q&A

Click on the questions to see the answers.

<details>
<summary>Why did you decide to create this application?</summary>
This was a training / hobby project. I wanted to learn more about back and front end development and deepen my knowledge about the technologies used in the project. I also wanted to create an application that requires no log-in so that the app is simple, easy to use, and hopefully fun as well!
</details>

<details>
<summary>What are the technologies used in the project?</summary>
This repository houses the back-end side of the WeatherTwin application. It has been written in Java version 23, using Spring Boot version 3.4.2 and Gradle version 8.11.1. Database has been created with PostgreSQL with database migration with Liquibase. For reading JSON files, Jackson library is used. For the front-end side of the project, written in JavaScript and React, see this repository: https://github.com/devaajanne/weathertwin-client
</details>

<details>
<summary>Your app is down! How can I let you know?</summary>
Contact me through LinkedIn (<a href="https://www.linkedin.com/in/janair/">https://www.linkedin.com/in/janair/</a>) or send me an email at janne.airaksinen.mail(at)gmail.com. Thanks!
</details>

## License

There are two JSON files of used in this application:

- ISO3166_CountryCodesAndNames.json
- citiesLatsAndLons.json

These files are located in `src/main/resources`. These have been created with the help of AI. The base for the files have been JSON files found in these two GitHub repositories:

- cities-json by lmfmaier: https://github.com/lmfmaier/cities-json
- ISO-3166-Countries-with-Regional-Codes by lukes: https://github.com/lukes/ISO-3166-Countries-with-Regional-Codes

As their works have been licenced with Creative Commons 4.0 license, the JSON files in this repository have also been licensed under Creative Commonce 4.0 license: https://creativecommons.org/licenses/by-sa/4.0/

