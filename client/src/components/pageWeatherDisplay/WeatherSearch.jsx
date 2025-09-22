import React, { useState } from "react";

import {
  Button,
  Radio,
  RadioGroup,
  FormControl,
  FormLabel,
  FormControlLabel,
  Box,
  Stack,
  Dialog,
  DialogTitle,
  DialogContent,
  Typography,
  DialogActions,
  CircularProgress,
} from "@mui/material";

import SendIcon from "@mui/icons-material/Send";

// NPM: https://www.npmjs.com/package/react-google-places-autocomplete
// Docs: https://tintef.github.io/react-google-places-autocomplete/
import GooglePlacesAutocomplete, {
  geocodeByAddress,
  getLatLng,
} from "react-google-places-autocomplete";

import { fetchWeatherData } from "../../api/ApiCalls";
import { sleep } from "../../utils/Utils";

export default function WeatherSearch({
  setInputLocation,
  setSimilarLocation,
}) {
  const [cityData, setCityData] = useState(null);
  const [unitInput, setUnitInput] = useState(null);
  const [weatherDataIsLoading, setWeatherDataIsLoading] = useState(false);
  const [inputErrorAlertIsOpen, setInputErrorAlertIsOpen] = useState(false);
  const [
    noSimilarLocationErrorAlertIsOpen,
    setNoSimilarLocationErrorAlertIsOpen,
  ] = useState(false);

  const handleCityChange = (cityData) => {
    setCityData(cityData);
  };

  const handleUnitChange = (event) => {
    if (
      (unitInput == "metric" && event.target.value == "metric") ||
      (unitInput == "imperial" && event.target.value == "imperial")
    ) {
      setUnitInput(null);
    } else {
      setUnitInput(event.target.value);
    }
  };

  const getLatAndLon = async () => {
    try {
      const results = await geocodeByAddress(cityData.label);
      const { lat, lng } = await getLatLng(results[0]);
      return { lat: lat, lon: lng };
    } catch (error) {
      console.error("getLatAndLon error: " + error);
    }
  };

  const handleSubmit = async () => {
    if (checkUserInput()) {
      // Here we set location states to null to clear the display, and indicate loading start
      setInputLocation(null);
      setSimilarLocation(null);
      setWeatherDataIsLoading(true);

      // Here we load the actual data and set it to correct states
      // Sleep time is included for user friendliness
      const cityLatLon = await getLatAndLon();
      const bodyData = JSON.stringify({
        cityName: cityData.label,
        cityCoords: cityLatLon,
        unit: unitInput,
      });
      const response = await fetchWeatherData(bodyData);

      await sleep(1500);
      setInputLocation(response.data.inputLocation);
      await sleep(1500);
      setSimilarLocation(response.data.similarLocation);

      // We check if a similar location has been found, and if not, an error dialog is opened
      if (
        response.data.inputLocation != null &&
        response.data.similarLocation == null
      ) {
        setNoSimilarLocationErrorAlertIsOpen(true);
      }

      // After we have loaded the data, we indicate that the loading has ended
      setWeatherDataIsLoading(false);

      // Finally, we reset input fields so that the user can start again
      setCityData(null);
      setUnitInput(null);
    }
  };

  const checkUserInput = () => {
    if (!cityData || !unitInput) {
      setInputErrorAlertIsOpen(true);
      return false;
    }

    return true;
  };

  const handleClose = () => {
    setInputErrorAlertIsOpen(false);
    setNoSimilarLocationErrorAlertIsOpen(false);
  };

  return (
    <>
      <Stack justifyContent="space-between" alignItems="center">
        <FormControl>
          <Box display="flex" justifyContent="center" alignItems="center">
            <GooglePlacesAutocomplete
              autocompletionRequest={{ types: ["locality"] }}
              debounce={500}
              selectProps={{
                placeholder: "Start typing your city",
                value: cityData ? cityData : null,
                onChange: handleCityChange,
                styles: {
                  control: (base) => ({
                    ...base,
                    width: "250px",
                  }),
                  container: (base) => ({
                    ...base,
                    width: "250px",
                  }),
                },
              }}
            />
          </Box>
          <Box
            display="flex"
            flexDirection="column"
            alignItems="center"
            sx={{ mt: 2, mb: 2 }}
          >
            <FormLabel
              sx={{
                color: "black",
                "&.Mui-focused": { color: "black" },
                mb: 2,
              }}
            >
              Choose your temperature unit
            </FormLabel>
            <RadioGroup row value={unitInput}>
              <FormControlLabel
                value="metric"
                control={<Radio onClick={handleUnitChange} />}
                label="celsius"
                labelPlacement="bottom"
              />
              <FormControlLabel
                value="imperial"
                control={<Radio onClick={handleUnitChange} />}
                label="fahrenheit"
                labelPlacement="bottom"
              />
            </RadioGroup>
          </Box>

          <Button
            variant="contained"
            onClick={handleSubmit}
            endIcon={<SendIcon />}
            loading={weatherDataIsLoading}
            loadingIndicator={<CircularProgress size={25} thickness={5} />}
            loadingPosition="center"
          >
            Submit
          </Button>

          {/*This an alert to notify the user that they have not selected a city and/or a unit*/}
          <Dialog open={inputErrorAlertIsOpen} onClose={handleClose}>
            <DialogTitle>Oops!</DialogTitle>
            <DialogContent>
              <Typography gutterBottom={true}>
                You have not selected a city or the output unit (celsius or
                fahrenheit) or neither.
              </Typography>
              <Typography>
                Please select both a city and the output unit to submit.
              </Typography>
            </DialogContent>
            <DialogActions>
              <Button variant="contained" onClick={handleClose}>
                Got it!
              </Button>
            </DialogActions>
          </Dialog>

          {/*This an alert to notify the user that a similar location has not been found*/}
          <Dialog
            open={noSimilarLocationErrorAlertIsOpen}
            onClose={handleClose}
          >
            <DialogTitle>Sorry!</DialogTitle>
            <DialogContent>
              <Typography gutterBottom={true}>
                Unfortunately, we could not find any location that has a similar
                weather as your location.
              </Typography>
              <Typography>
                Please select another location or try again later.
              </Typography>
            </DialogContent>
            <DialogActions>
              <Button variant="contained" onClick={handleClose}>
                Got it!
              </Button>
            </DialogActions>
          </Dialog>
        </FormControl>
      </Stack>
    </>
  );
}
