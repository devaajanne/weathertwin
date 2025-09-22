import PropTypes from "prop-types";
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

WeatherDisplay.propTypes = {
  inputLocation: PropTypes.shape({
    countryCode: PropTypes.string,
    countryName: PropTypes.string,
    weatherIcon: PropTypes.string,
    weatherGroup: PropTypes.string,
    id: PropTypes.number,
    city: PropTypes.string,
    temp: PropTypes.number,
    tempUnit: PropTypes.string,
  }),
  similarLocation:
    PropTypes.shape({
      countryCode: PropTypes.string,
      countryName: PropTypes.string,
      weatherIcon: PropTypes.string,
      weatherGroup: PropTypes.string,
      id: PropTypes.number,
      city: PropTypes.string,
      temp: PropTypes.number,
      tempUnit: PropTypes.string,
    }) || null,
};
