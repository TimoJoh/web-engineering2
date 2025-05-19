import React, { useState, useEffect } from "react";
import "./weathercard.css";
import Clear from "../../assets/weathericons/clear-day.svg";

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
        <div className="time">
            <p>18:00</p>
        </div>
        <div className="forecast">
            <ul>
                <li>
                    <img src={Clear} alt=""/>
                    <p className="temp">22°C</p>
                    <p>Mon</p>
                </li>
                <li>
                    <img src={Clear} alt=""/>
                    <p className="temp">22°C</p>
                    <p>Mon</p>
                </li>
                <li>
                    <img src={Clear} alt=""/>
                    <p className="temp">22°C</p>
                    <p>Mon</p>
                </li>
                <li>
                    <img src={Clear} alt=""/>
                    <p className="temp">22°C</p>
                    <p>Mon</p>
                </li>
                <li>
                    <img src={Clear} alt=""/>
                    <p className="temp">22°C</p>
                    <p>Mon</p>
                </li>
            </ul>
        </div>
    </div>
)

export default Weathercard;
