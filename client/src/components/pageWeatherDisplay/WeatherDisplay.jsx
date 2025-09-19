import React from "react";

import Grid from "@mui/material/Grid2";
import { Box } from "@mui/material";

import WeatherLocation from "./WeatherLocation";

export default function WeatherDisplay({ inputLocation, similarLocation }) {
  return (
    <>
      <Grid container spacing={2} justifyContent="center">
        <Grid item>
          <Box>
            <WeatherLocation location={inputLocation} />
          </Box>
        </Grid>
        <Grid item>
          <Box>
            <WeatherLocation location={similarLocation} />
          </Box>
        </Grid>
      </Grid>
    </>
  );
}
