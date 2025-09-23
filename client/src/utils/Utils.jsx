const getDefaultCity = () => {
  const cityMap = new Map();
  cityMap.set("Helsinki, Finland", { lat: 60.1695, lon: 24.9354 });
  cityMap.set("New York, USA", { lat: 40.7128, lon: -74.006 });
  cityMap.set("Tokyo, Japan", { lat: 35.6895, lon: 139.6917 });
  cityMap.set("Sydney, Australia", { lat: -33.8688, lon: 151.2093 });
  cityMap.set("Cairo, Egypt", { lat: 30.0444, lon: 31.2357 });
  cityMap.set("Rio de Janeiro, Brazil", { lat: -22.9068, lon: -43.1729 });
  cityMap.set("Moscow, Russia", { lat: 55.7558, lon: 37.6173 });
  cityMap.set("Paris, France", { lat: 48.8566, lon: 2.3522 });
  cityMap.set("London, UK", { lat: 51.5074, lon: -0.1278 });
  cityMap.set("Berlin, Germany", { lat: 52.52, lon: 13.405 });
  cityMap.set("Toronto, Canada", { lat: 43.65107, lon: -79.347015 });
  cityMap.set("Beijing, China", { lat: 39.9042, lon: 116.4074 });
  cityMap.set("Mumbai, India", { lat: 19.076, lon: 72.8777 });
  cityMap.set("Mexico City, Mexico", { lat: 19.4326, lon: -99.1332 });
  cityMap.set("Cape Town, South Africa", { lat: -33.9249, lon: 18.4241 });

  const units = ["metric", "imperial"];

  const defaultCity = Array.from(cityMap.keys())[
    Math.floor(Math.random() * cityMap.size)
  ];
  const defaultLatLon = cityMap.get(defaultCity);
  const defaultUnit = units[Math.floor(Math.random() * units.length)];

  const bodyData = JSON.stringify({
    cityName: defaultCity,
    cityCoords: defaultLatLon,
    unit: defaultUnit,
  });

  return bodyData;
};

// This is a a method to make the app wait a certain time
// Sometimes wait time is included to make the app more user friendly
const sleep = (delay) => new Promise((resolve) => setTimeout(resolve, delay));

export { getDefaultCity, sleep };
