# Weather Twin

Weather Twin a fun, silly little app to find which city in the world has the same weather as your location.

This is the repository for the client-side application. For the server-side repository, see here: [Weather Twin server](https://github.com/devaajanne/weathertwin-server).

## About the project
Enter a location - your own city perhaps - and see where in the world is there a city that is experiencing the same weather at the moment!

Weather Twin is a hobby project alongside my studies. I wanted to learn more about back and front end development and deepen my knowledge about the technologies used in the project. I also wanted to create an application that requires no log-in so that the app is simple, easy to use, and hopefully fun as well!

## Deployment

Weather Twin is now live at GitHub pages! See the live demo of the app here: [Weather Twin](https://devaajanne.github.io/weathertwin-client/)

## How it works

Weather Twin's client app works by

1. getting the user's desired city through React Google Places Autocomplete library's autocomplete input field which suggests cities as the user types in the input field
2. getting the user's desired temperature unit (celsius or fahrenheit)
3. finding the coordinates (latitude and longitude) for the user's selected city through React Google Places Autocomplete
4. submitting the user's input data to the server
5. receiving weather data for the user's input location and for a location that has a similar weather
6. displaying both weather data in the user's selected temperature unit
7. clearing user's inputs for the next submission

## Technologies

This app has been written in JavaScript and [React](https://react.dev/). Installed libraries include

- [Material UI](https://mui.com/material-ui/)
- [Axios](https://www.npmjs.com/package/axios)
- [React Google Places Autocomplete](https://www.npmjs.com/package/react-google-places-autocomplete)

Client-side app is deployed on [GitHub Pages](https://devaajanne.github.io/weathertwin-client/)

For the back-end side of the project, written in Java with Spring Boot, see this repository: [Weather Twin server](https://github.com/devaajanne/weathertwin-server).

## Contact

You can contact me through [LinkedIn](https://www.linkedin.com/in/janair/) or by email at janne.airaksinen.mail(at)gmail.com.