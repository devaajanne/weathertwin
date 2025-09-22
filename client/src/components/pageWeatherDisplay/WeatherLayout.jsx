import React, { useState } from "react";

import Grid from "@mui/material/Grid2";
import { Container, Typography } from "@mui/material";

import WeatherSearch from "./WeatherSearch";
import WeatherDisplay from "./WeatherDisplay";

export default function WeatherLayout() {
  const [inputLocation, setInputLocation] = useState(null);
  const [similarLocation, setSimilarLocation] = useState(null);

  return (
    <>
      <Container maxWidth="lg">
        <Grid container alignItems="flex-start" spacing={2}>
          <Grid item size={{ xs: 12 }}>
            <Typography sx={{ textAlign: "center", variant: "h4", mt: 2 }}>
              Enter your city below and see which other city has the same
              weather!
            </Typography>
          </Grid>
          <Grid item size={{ xs: 12, md: 6 }} sx={{ mt: 2 }}>
            <WeatherSearch
              setInputLocation={setInputLocation}
              setSimilarLocation={setSimilarLocation}
            />
          </Grid>
          <Grid item size={{ xs: 12, md: 6 }} sx={{ mt: 2 }}>
            <WeatherDisplay
              inputLocation={inputLocation}
              similarLocation={similarLocation}
            />
          </Grid>
        </Grid>
      </Container>
    </>
  );
}
