import React from "react";
import './detail-forecast.css';

const DetailForecast = ({data}) => {
    return (
        <div className="d-forecast">
            <h1>7-Day Forecast</h1>
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