import PropTypes from "prop-types";
import {
  Card,
  CardHeader,
  Avatar,
  CardContent,
  Typography,
  CardActions,
  Button,
} from "@mui/material";

export default function WeatherLocationCard({ location }) {
  // Here we set the country's flag icon to card avatar
  // https://www.npmjs.com/package/country-flag-icons
  const flagIconSrc = `${import.meta.env.BASE_URL}flagIcons/${location.countryCode}.svg`;
  const flagIconAlt = `${location.countryName} flag`;

  // Here we set the weather's icon to card content
  // Weather icons are found using: https://github.com/erikflowers/weather-icons
  const weatherIconClass = `wi wi-owm-${location.weatherId}`;
  const weatherIconAlt = `${location.weatherGroup} icon`;

  const weatherURL = `https://openweathermap.org/city/${location.id}`;

  return (
    <>
      <Card
        sx={{
          width: 275,
          height: 425,
          display: "flex",
          flexDirection: "column",
        }}
      >
        <CardHeader
          sx={{ textAlign: "left" }}
          avatar={
            <Avatar
              src={flagIconSrc}
              alt={flagIconAlt}
              sx={{ width: 40, height: 40 }}
            ></Avatar>
          }
          titleTypographyProps={{ variant: "h5" }}
          subheaderTypographyProps={{ color: "black" }}
          title={location.city}
          subheader={location.countryName}
        ></CardHeader>

        <CardContent
          sx={{
            alignItems: "center",
            justifyContent: "center",
            textAlign: "center",
            height: "100%",
          }}
        >
          <Typography variant={"h5"}>
            {location.temp} {location.tempUnit}
          </Typography>
          <Typography variant={"h5"}>{location.weatherGroup}</Typography>
          {/*Finds the weather icon for the location's weather, displays it on the card and fixes its position*/}
          <i
            className={weatherIconClass}
            style={{
              fontSize: "75px",
              margin: "20px 0 0 0",
            }}
            aria-label={weatherIconAlt}
          ></i>
        </CardContent>

        <CardActions sx={{ justifyContent: "center" }}>
          <Button
            sx={{ width: "90%", mb: 1 }}
            variant="contained"
            size="small"
            aria-label="link to location's full weather"
            onClick={() => window.open(weatherURL)}
          >
            See full weather
          </Button>
        </CardActions>
      </Card>
    </>
  );
}

WeatherLocationCard.propTypes = {
  location:
    PropTypes.shape({
      countryCode: PropTypes.string,
      countryName: PropTypes.string,
      weatherId: PropTypes.string,
      weatherGroup: PropTypes.string,
      id: PropTypes.number,
      city: PropTypes.string,
      temp: PropTypes.number,
      tempUnit: PropTypes.string,
    }) || null,
};
