import {IoThermometerOutline, IoWaterOutline} from "react-icons/io5";
import {Wind} from "lucide-react";
import React from "react";
import './detail-current.css';
import {getWeatherIcon} from "../Icon-fetch/icon-fetch"
import AddCityButton from "../CityUtils/AddCityButton";

const DetailCurrent = ({data}) => {
    const {
        city,
        temperature,
        formattedTime,
        condition,
        feelsLike,
        humidity,
        windSpeed,
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

    const capitalizeFirstLetter = (string) => {
        if (!string) return "";
        return string.charAt(0).toUpperCase() + string.slice(1);
    };

    return (
        <div className="current">
            <div className="heading">
                <div className="data">
                    <h1>{capitalizeFirstLetter(city)}</h1>
                    <p>{formattedTime}</p>
                </div>
                <div className="add">
                    <AddCityButton cityName={city} onCityAdded={() => console.log(`${city} added`)}/>
                </div>
            </div>
            <div className="current-temp">
                <img src={currentIconSrc} alt="" width="100px" height="auto"/>
                <p className="temp">{temperature}<sup>°C</sup></p>
            </div>
            <div className="infos">
                <div className="info">
                    <IoWaterOutline fontSize="24px"/>
                    <p>{humidity}%</p>
                </div>
                <div className="info">
                    <IoThermometerOutline fontSize="24px"/>
                    <p>{feelsLike}°C</p>
                </div>
                <div className="info">
                    <Wind size={24}/>
                    <p>{windSpeed}km/h</p>
                </div>
            </div>
        </div>
    )
}

export default DetailCurrent;