import PropTypes from "prop-types";
import Grid from "@mui/material/Grid2";
import { Box } from "@mui/material";

import WeatherLocationCard from "../weatherCardComponents/WeatherLocationCard";
import WeatherLocationLoadingCard from "../weatherCardComponents/WeatherLocationLoadingCard";
import WeatherLocationNotFoundCard from "../weatherCardComponents/WeatherLocationNotFoundCard";

export default function WeatherDisplay({
  inputLocation,
  similarLocation,
  weatherDataIsLoading,
}) {
  return (
    <>
      <Grid container spacing={2} justifyContent="center">
        <Grid item>
          <Box>
            {weatherDataIsLoading ? (
              <WeatherLocationLoadingCard />
            ) : inputLocation === null ? (
              <WeatherLocationNotFoundCard />
            ) : (
              <WeatherLocationCard location={inputLocation} />
            )}
          </Box>
        </Grid>
        <Grid item>
          <Box>
            {weatherDataIsLoading ? (
              <WeatherLocationLoadingCard />
            ) : similarLocation === null ? (
              <WeatherLocationNotFoundCard />
            ) : (
              <WeatherLocationCard location={similarLocation} />
            )}
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
    weatherId: PropTypes.string,
    weatherGroup: PropTypes.string,
    id: PropTypes.number,
    city: PropTypes.string,
    temp: PropTypes.number,
    tempUnit: PropTypes.string,
  }),
  similarLocation: PropTypes.shape({
    countryCode: PropTypes.string,
    countryName: PropTypes.string,
    weatherId: PropTypes.string,
    weatherGroup: PropTypes.string,
    id: PropTypes.number,
    city: PropTypes.string,
    temp: PropTypes.number,
    tempUnit: PropTypes.string,
  }),
  weatherDataIsLoading: PropTypes.bool,
};
