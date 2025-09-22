// This is a a method to make the app wait a certain time
// Sometimes wait time is included to make the app more user friendly
const sleep = (delay) => new Promise((resolve) => setTimeout(resolve, delay));

export { sleep };
