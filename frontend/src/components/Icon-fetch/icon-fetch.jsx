import {weatherIconMap} from "./icon-mapper"

export function getWeatherIcon(condition, currentTime, sunriseTime, sunsetTime) {

    //calculate if it is day or night
    const isDay = currentTime >= sunriseTime && currentTime < sunsetTime;

    // map weather conditions according to https://openweathermap.org/weather-conditions
    const map = {
        'clear sky': isDay ? 'clear-day' : 'clear-night',
        'sky is clear': isDay ? 'clear-day' : 'clear-night',  // neu hinzugefügt
        'few clouds': isDay ? 'cloudy-1-day' : 'cloudy-1-night',
        'scattered clouds': 'cloudy',
        'broken clouds': 'cloudy',
        'overcast clouds': 'cloudy',

        'light rain': isDay ? 'rainy-3-day' : 'rainy-3-night',
        'moderate rain': isDay ? 'rainy-3-day' : 'rainy-3-night',
        'heavy intensity rain': isDay ? 'rainy-3-day' : 'rainy-3-night',
        'drizzle': 'drizzle',
        'shower rain': 'rainy-3',

        'thunderstorm': 'thunderstorms',

        'snow': 'snow',
        'light snow': 'snow',
        'sleet': 'rain-and-sleet-mix',
        'rain and snow': 'rain-and-snow-mix',

        'mist': 'fog',
        'smoke': 'haze',
        'haze': 'haze',
        'fog': 'fog',
        'sand': 'dust',
        'dust': 'dust',

        'tornado': 'tornado',
        'squalls': 'wind',
        'windy': 'wind',
    };

    const iconKey = map[condition.toLowerCase()] || 'cloudy';
    return weatherIconMap[iconKey];
}

// map weather conditions according to https://openweathermap.org/weather-conditions
// only for daytime
export function getWeatherIconDayOnly(condition) {
    const map = {
        'clear sky': 'clear-day',
        'sky is clear': 'clear-day',
        'few clouds': 'cloudy-1-day',
        'scattered clouds': 'cloudy',
        'broken clouds': 'cloudy',
        'overcast clouds': 'cloudy',
        'light rain': 'rainy-3-day',
        'moderate rain': 'rainy-3-day',
        'heavy intensity rain': 'rainy-3',
        'drizzle': 'drizzle',
        'shower rain': 'rainy-3',
        'thunderstorm': 'thunderstorms',
        'snow': 'snow',
        'light snow': 'snow',
        'sleet': 'rain-and-sleet-mix',
        'rain and snow': 'rain-and-snow-mix',
        'mist': 'fog',
        'smoke': 'haze',
        'haze': 'haze',
        'fog': 'fog',
        'sand': 'dust',
        'dust': 'dust',
        'tornado': 'tornado',
        'squalls': 'wind',
        'windy': 'wind',
    };

    const iconKey = map[condition.toLowerCase()] || 'cloudy';
    return weatherIconMap[iconKey];
}
