import React from "react";

import {
  Card,
  CardHeader,
  Avatar,
  CardContent,
  Typography,
  CardActions,
  Button,
} from "@mui/material";
import Grid from "@mui/material/Grid2";

export default function WeatherLocation({ location }) {
  // We return an empty card (ie. displays nothing) if no location has been searched yet or found
  if (!location) {
    return <Card style={{ border: "none", boxShadow: "none" }} />;
  }

  // Here we set the country's flag image's <img> tag's attributes
  // https://www.npmjs.com/package/country-flag-icons
  const flagIconSrc = `http://purecatamphetamine.github.io/country-flag-icons/3x2/${location.countryCode}.svg`;
  const flagIconAlt = `${location.countryName} flag`;

  // Here we set the weather's icon's <img> tag's attributes
  const weatherIconSrc = ` https://openweathermap.org/img/wn/${location.weatherIcon}@2x.png`;
  const weatherIconAlt = `${location.weatherGroup} icon`;

  const weatherURL = `https://openweathermap.org/city/${location.id}`;

  //  <img src={flagIconSrc} alt={flagIconAlt} style={{ width: 30, height: 20, marginLeft: 10 }} />
  return (
    <>
      {/*The location card is only rendered if a location has been found*/}
      {location && (
        <Card sx={{ minWidth: 100 }}>
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
            <Typography variant={"h6"}>
              {location.temp} {location.tempUnit}
            </Typography>
            <Typography variant={"h6"}>{location.weatherGroup}</Typography>
            {/*Finds the weather icon for the location's weather, displays it on the card and fixes its position*/}
            <img src={weatherIconSrc} alt={weatherIconAlt} />
          </CardContent>

          <CardActions sx={{ justifyContent: "center" }}>
            <Button
              variant="contained"
              size="small"
              aria-label="link to location's full weather"
              onClick={(event) => window.open(weatherURL)}
            >
              See full weather
            </Button>
          </CardActions>
        </Card>
      )}
    </>
  );
}
