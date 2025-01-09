# Weather Twin

Weather Twin a fun, silly little app to find which city in the world has the same weather as your location.

The JSON file of cities with their latitudes and longitudes has been created with AI. The base for the file has been this GitHub repository by lmfmaier: https://github.com/lmfmaier/cities-json

## API documentation

The app provides one endpoint, api/weatherdata. This endpoint does not require authentication or authorization. The endpoint allows fetching weather data for the input location, and querying the repository for a similar weather data.

### Weather data (/api/weatherdata)

<details>
<summary>Click for details</summary>

**URL** : `/api/weatherdata`

**Method** : `GET`

**Auth required** : No

**Path parameters** : None

**Request body content** :

The request body should be a JSON object. It must include the following fields:

| Field  | Type   | Required | Description                                                              |
| ------ | ------ | -------- | ------------------------------------------------------------------------ |
| `city` | String | YES      | The name of the input city .                                             |
| `unit` | String | YES      | Response unit. Must be `standard`/`null`/empty, `metric`, or `imperial`. |

#### Example request

`GET /api/weatherdata`

```json
{
  "city": "Helsinki",
  "unit": "metric"
}
```

### Success reponse

**Condition** : Data provided in the body is valid.

**Code** : ``200 OK`

**Content example** : Returns a JSON object with two properties: `inputLocation` and `similarLocation`.

```json
{
  "similarLocation": {
    "id": 134601,
    "lat": 32.941,
    "lon": 50.121,
    "city": "Fareydūnshahr",
    "countryCode": "IR",
    "weatherGroup": "Clouds",
    "temp": -5.6
  },
  "inputLocation": {
    "id": 658225,
    "lat": 60.1675,
    "lon": 24.9427,
    "city": "Helsinki",
    "countryCode": "FI",
    "weatherGroup": "Clouds",
    "temp": -5.4
  }
}
```

</details>
