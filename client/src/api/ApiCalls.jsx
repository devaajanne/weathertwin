import axios from "axios";

const URL = "https://weathertwin-f1db2b3da629.herokuapp.com/api";

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
