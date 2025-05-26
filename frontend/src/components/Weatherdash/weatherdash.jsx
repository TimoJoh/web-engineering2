import React from 'react';
import './weatherdash.css';
import WeathercardData from "../Weathercard/WeatherCardData";

const weatherData = [
    { city: 'Friedrichshafen'},
    { city: 'Auckland'},
    { city: 'Pretoria'},
];

const Weatherdash = () => {
    return (
        <div className="weatherdash">
            {weatherData.map((weatherData, index) => (
                <WeathercardData
                    key={index}
                    city={weatherData.city}
                />
            ))}
        </div>
    );
}

export default Weatherdash;