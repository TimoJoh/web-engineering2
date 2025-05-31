import React from "react";
import "./weathercard.css";
import {getWeatherIcon, getWeatherIconDayOnly} from "../Icon-fetch/icon-fetch"
import DeleteCity from "../CityUtils/DeleteCity";

const Weathercard = ({ data, forecast }) => {
    const {
        city,
        temperature,
        condition,
        sunrise,
        sunset
    } = data;

    const now = new Date();
    const parseTime = (t) => {
        const [h, m] = t.split(':').map(Number);
        const d = new Date();
        d.setHours(h, m, 0, 0);
        return d;
    };

    const currentIconSrc = getWeatherIcon(condition, now, parseTime(sunrise), parseTime(sunset));

    return (
        <div className="card">
            <div className="header">
                <p>{city}</p>
            </div>
            <div className="trash">
                <DeleteCity cityName={city} onCityDeleted={() => console.log(`${city} deleted`)}/>
            </div>
            <div className="today">
                <img src={currentIconSrc} alt={condition} />
                <p>Today</p>
            </div>
            <div className="today-temp">
                <p>{temperature}°C</p>
            </div>
            <div className="lh">
                {forecast?.list?.length > 0 && (
                    <>
                        <p>L: {Math.round(forecast.list[0].temp.min)}°C</p>
                        <p>H: {Math.round(forecast.list[0].temp.max)}°C</p>
                    </>
                )}
            </div>

            <div className="forecast">
                {forecast?.list?.slice(0, 5).map((day, index) => {
                    const condition = day.weather[0].description;
                    const forecastIconSrc = getWeatherIconDayOnly(condition);
                    const weekday = day.weekday.slice(0, 3);

                    return(
                        <ul key={index}>
                            <li>
                                <p className="forecast-day">{weekday}</p>
                                <img
                                    src={forecastIconSrc}
                                    alt={condition}
                                />
                                <div className="forecast-lh">
                                    <p className="l">{Math.round(day.temp.min)}°</p>
                                    <p className="h">{Math.round(day.temp.max)}°</p>
                                </div>
                            </li>
                        </ul>
                        );
                })}
            </div>
        </div>
    );
};

export default Weathercard;
