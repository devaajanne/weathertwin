import axios from "axios";

const URL = import.meta.env.VITE_SERVER_URL;

const fetchWeatherData = async (bodyData) => {
  const config = {
    headers: { "Content-Type": "application/json" },
  };

  try {
    const response = await axios.post(URL + "/weatherdata", bodyData, config);
    return response;
  } catch (error) {
    console.error("fetchWeatherData error: " + error);
  }
};

export { fetchWeatherData };
