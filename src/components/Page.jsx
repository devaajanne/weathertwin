import React from "react";

import PageToolbar from "./PageToolBar";
import WeatherLayout from "./pageWeatherDisplay/WeatherLayout";

export default function Page() {
  return (
    <>
      <PageToolbar />
      <WeatherLayout />
    </>
  );
}
