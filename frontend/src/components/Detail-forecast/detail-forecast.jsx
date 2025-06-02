import React from "react";
import './detail-forecast.css';
import {IoCalendarOutline} from "react-icons/io5";
import {getWeatherIconDayOnly} from "../Icon-fetch/icon-fetch";

const DetailForecast = ({data}) => {
    return (
        <div className="d-forecast">
            <div className="forecast-heading">
                <IoCalendarOutline fontSize="18px" color="#000000"/>
                <h1>7-Day Forecast</h1>
            </div>
            <ul>
                {data?.list?.slice(0, 7).map((day, index) => {
                const condition = day.weather[0].description;
                const forecastIconSrc = getWeatherIconDayOnly(condition);
                const weekday = day.weekday.slice(0, 3);

                return (
                    <li key={index}>
                        <div className="day-forecast">
                            <img src={forecastIconSrc} alt={condition} width="28px" height="auto"/>
                            <p className="l-h">{Math.round(day.temp.min)}° / {Math.round(day.temp.max)}°</p>
                            <p className="date">{weekday}</p>
                        </div>
                    </li>
                );
            })}
            </ul>
        </div>
    )
}

export default DetailForecast;