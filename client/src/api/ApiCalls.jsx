import axios from "axios";

const URL = import.meta.env.VITE_SERVER_URL;

const fetchWeatherData = async (queryData) => {
  try {
    const response = await axios.get(`${URL}/api/weatherdata`, {
      params: queryData,
    });
    return response;
  } catch (error) {
    console.error("fetchWeatherData error: " + error);
  }
};

export { fetchWeatherData };
