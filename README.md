# Weather Twin

Weather Twin a fun, silly little app to find which city in the world has the same weather as your location.

This is the repository for the server-side application. For the client-side repository, see here: https://github.com/devaajanne/weathertwin-client.

## API documentation

The app provides one endpoint, api/weatherdata.

The endpoint allows fetching weather data for the input location, and querying the repository for a similar weather data. This endpoint does not require authentication or authorization.

As a result, the endpoint retuns the weather for the input location as well as the weather for a location that has a similar weather .

### Weather data (/api/weatherdata)

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

### License

There are two JSON files of used in this application:

- ISO3166_CountryCodesAndNames.json
- citiesLatsAndLons.json

These files are located in `src/main/resources`. These have been created with the help of AI. The base for the files have been JSON files found in these two GitHub repositories:

- cities-json by lmfmaier: https://github.com/lmfmaier/cities-json
- ISO-3166-Countries-with-Regional-Codes by lukes: https://github.com/lukes/ISO-3166-Countries-with-Regional-Codes

As their works have been licenced with Creative Commons 4.0 license, the JSON files in this repository have also been licensed under Creative Commonce 4.0 license: https://creativecommons.org/licenses/by-sa/4.0/

