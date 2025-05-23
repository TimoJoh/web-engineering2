import React from "react";
import './detail-forecast.css';
import {CalendarOutline} from "react-ionicons";

const DetailForecast = ({data}) => {
    return (
        <div className="d-forecast">
            <div className="forecast-heading">
                <CalendarOutline height="18px" width="18px" color="#000000"/>
                <h1>7-Day Forecast</h1>
            </div>
            {data.map((forecast) => (
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
    )
}

export default DetailForecast;