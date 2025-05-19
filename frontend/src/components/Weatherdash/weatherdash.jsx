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
            {weatherData.map((weather, index) => (
                <Weathercard
                    key={index}
                    city={weather.city}
                    temperature={weather.temperature}
                    condition={weather.condition}
                />
            ))}
        </div>
    );
}

export default Weatherdash;