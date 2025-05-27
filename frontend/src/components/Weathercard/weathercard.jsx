import React from "react";
import "./weathercard.css";
import Clear from "../../assets/weathericons/clear-day.svg";

const forecastData = [
    { day: 'Mon', low: 21, high: 32, condition: Clear},
    { day: 'Mon', low: 21, high: 32, condition: Clear},
    { day: 'Mon', low: 21, high: 32, condition: Clear},
    { day: 'Mon', low: 21, high: 32, condition: Clear},
    { day: 'Mon', low: 21, high: 32, condition: Clear},];

const Weathercard = ({city, temperature, condition}) => (
    <div className="card">
        <div className="header">
            <p>{city}</p>
        </div>
        <div className="today">
            <img src={Clear} alt={condition}/>
            <p>Today</p>
        </div>
        <div className="today-temp">
            <p>{temperature}°C</p>
        </div>
        <div className="lh">
            <p>L: 12°C</p>
            <p>H: 25°C</p>
        </div>

        <div className="forecast">
            {forecastData.map((forecast, index) => (
                <ul key={index}>
                    <li>
                        <p className="forecast-day">{forecast.day}</p>
                        <img src={forecast.condition} alt=""/>
                        <div className="forecast-lh">
                            <p className="l">{forecast.low}°</p>
                            <p className="h">{forecast.high}°</p>
                        </div>
                    </li>
                </ul>
            ))}
        </div>
    </div>
)

export default Weathercard;
