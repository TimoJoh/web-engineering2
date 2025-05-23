import React from 'react';
import './weatherdash.css';
import Weathercard from "../Weathercard/weathercard";

const weatherData = [
    { city: 'Berlin', temperature: 21, condition: 'Sunny' },
    { city: 'Paris', temperature: 18, condition: 'Cloudy' },
    { city: 'London', temperature: 15, condition: 'Rainy' },
];

const Weatherdash = () => {
    return (
        <div className="weatherdash">
            {weatherData.map((weatherData, index) => (
                <Weathercard
                    key={index}
                    data={weatherData}
                />
            ))}
        </div>
    );
}

export default Weatherdash;