import React from 'react';
import './weatherdetail.css';
import DetailCurrentData from "../Detail-current/detail-current-data";
import DetailForecastData from "../Detail-forecast/detail-forecast-data";
import DetailHourlyData from "../Detail-hourly/detail-hourly-data";
import DetailSunsetRise from "../Detail-sunset-rise/detail-sunset-rise"
import DetailRainmap from "../Detail-rainmap/details-rainmap";
import Clear from "../../assets/weathericons/clear-day.svg";

const weatherData = [
    { hour: '0', temperature: 11, precipitation: 0, condition: Clear },
    { hour: '1', temperature: 10, precipitation: 0, condition: Clear },
    { hour: '2', temperature: 5, precipitation: 0, condition: Clear },
    { hour: '3', temperature: 9, precipitation: 0.6, condition: Clear },
    { hour: '4', temperature: 8, precipitation: 0.2, condition: Clear },
    { hour: '5', temperature: -5, precipitation: 0, condition: Clear },
    { hour: '6', temperature: -5, precipitation: 0, condition: Clear },
    { hour: '7', temperature: -3, precipitation: 0, condition: Clear },
    { hour: '8', temperature: -2, precipitation: 0, condition: Clear },
    { hour: '9', temperature: 1, precipitation: 0, condition: Clear },
    { hour: '10', temperature: 7, precipitation: 0, condition: Clear },
    { hour: '11', temperature: 13, precipitation: 0.3, condition: Clear },
    { hour: '12', temperature: 19, precipitation: 0.1, condition: Clear },
    { hour: '13', temperature: 23, precipitation: 0, condition: Clear },
    { hour: '14', temperature: 21, precipitation: 0, condition: Clear },
    { hour: '15', temperature: 20, precipitation: 0, condition: Clear },
    { hour: '16', temperature: 20, precipitation: 0, condition: Clear },
    { hour: '17', temperature: 24, precipitation: 0, condition: Clear },
    { hour: '18', temperature: 17, precipitation: 0, condition: Clear },
    { hour: '19', temperature: 15, precipitation: 0.4, condition: Clear },
    { hour: '20', temperature: 14, precipitation: 0.2, condition: Clear },
    { hour: '21', temperature: 13, precipitation: 0, condition: Clear },
    { hour: '22', temperature: 12, precipitation: 0, condition: Clear },
    { hour: '23', temperature: 11, precipitation: 0, condition: Clear },
];
const forecastData = [
    { day: 'Mon', low: 21, high: 32, condition: Clear},
    { day: 'Tue', low: 21, high: 32, condition: Clear},
    { day: 'Wed', low: 21, high: 32, condition: Clear},
    { day: 'Thu', low: 21, high: 32, condition: Clear},
    { day: 'Fri', low: 21, high: 32, condition: Clear},
    { day: 'Sat', low: 21, high: 32, condition: Clear},
    { day: 'Sun', low: 21, high: 32, condition: Clear}
];
const sunstate = {sunrise: "06:30", sunset: "21:30"};
const loc = {lat: 52.519, lon: 13.408};

const Weatherdetail = ({city}) => {
    return (
        <div className="weatherdetail">
            <DetailCurrentData city={city}/>
            <DetailHourlyData city={city}/>
            <DetailForecastData city={city}/>
            <DetailSunsetRise city={city} data={sunstate}/>
            <DetailRainmap city={city} data={loc} />
        </div>
    )
}

export default Weatherdetail;