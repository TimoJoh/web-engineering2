import React from 'react';
import './weatherdetail.css';
import { ThermometerOutline } from 'react-ionicons'
import { WaterOutline } from "react-ionicons";
import { Wind } from "lucide-react";

import Clear from "../../assets/weathericons/clear-day.svg";

const forecastData = [
    { day: 'Mon', low: 21, high: 32, condition: Clear},
    { day: 'Tue', low: 21, high: 32, condition: Clear},
    { day: 'Wed', low: 21, high: 32, condition: Clear},
    { day: 'Thu', low: 21, high: 32, condition: Clear},
    { day: 'Fri', low: 21, high: 32, condition: Clear},
    { day: 'Sat', low: 21, high: 32, condition: Clear},
    { day: 'Sun', low: 21, high: 32, condition: Clear}
]

const Weatherdetail = () => {
    return (
        <div className="weatherdetail">
            <div className="current">
                <div className="heading">
                    <h1>Berlin</h1>
                    <p>18:00</p>
                </div>
                <div className="current-temp">
                    <img src={Clear} alt=""/>
                    <p className="temp">24<sup>°C</sup></p>
                    <p className="cond">Sunny</p>
                </div>
                <div className="infos">
                    <div className="info">
                        <WaterOutline height="24px" width="24px"/>
                        <p>68%</p>
                    </div>
                    <div className="info">
                        <ThermometerOutline height="24px" width="24px" />
                        <p>22°C</p>
                    </div>
                    <div className="info">
                        <Wind />
                        <p>12km/h</p>
                    </div>
                </div>
            </div>
            <div className="hourly">

            </div>
            <div className="d-forecast">
                <h1>7-Day Forecast</h1>
                {forecastData.map((forecast) => (
                <ul>
                    <li>
                        <div className="day-forecast">
                            <img src={ forecast.condition } alt=""/>
                            <p className="l-h">{forecast.low}° / {forecast.high}°</p>
                            <p className="date">{forecast.day}</p>
                        </div>
                    </li>
                </ul>
                ))}
            </div>
            <div className="sun">

            </div>
            <div className="precipitation-map">

            </div>
        </div>
    )
}

export default Weatherdetail;