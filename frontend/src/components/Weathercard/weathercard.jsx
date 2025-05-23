import React from "react";
import "./weathercard.css";
import Clear from "../../assets/weathericons/clear-day.svg";

const forecastData = [
    { day: 'Mon', low: 21, high: 32, condition: Clear},
    { day: 'Mon', low: 21, high: 32, condition: Clear},
    { day: 'Mon', low: 21, high: 32, condition: Clear},
    { day: 'Mon', low: 21, high: 32, condition: Clear},
    { day: 'Mon', low: 21, high: 32, condition: Clear},];


const Weathercard = ({data}) => {

    const {
        city,
        temperature,
        minTemperature,
        maxTemperature,
        condition,
        feelsLike,
        pressure,
        humidity,
        sunrise,
        sunset,
        windSpeed,
        windDegree,
        windDirection,
    } = data;
   // const icon = conditionsIcons[condition] || Clear;

    return (
        <div className="card">
            <div className="header">
                <p>{city}</p>
            </div>
            <div className="today">
                <img src={Clear} alt={condition}/>
                <p>Today</p>
            </div>
            <div className="today-temp">
                <p>{temperature}</p>
            </div>
            <div className="lh">
                <p>L: 12°C</p>
                <p>H: 25°C</p>
            </div>

            <div className="forecast">
                {forecastData.map((forecast) => (
                    <ul>
                        <li>
                            <p className="forecast-day">Mon</p>
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
}

export default Weathercard;
