import axios from "axios";

const URL = "https://weathertwin-server-6008c41e2aa4.herokuapp.com/api";

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
