import {ThermometerOutline, WaterOutline} from "react-ionicons";
import {Wind} from "lucide-react";
import React from "react";
import './detail-current.css';
import {getWeatherIcon} from "../Icon-fetch/icon-fetch"
import axios from "../../axiosInstance";

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

    const AddCity = ({cityName, onCityAdded}) => {
        const handleAddCity = (e) => {
            e.preventDefault();

            axios.post('/cities', {name: cityName})
                .then(res => {
                    console.log("City added:", res.data);
                    if (onCityAdded) {
                        onCityAdded(res.data);
                    }
                })
                .catch(err => console.error("Fehler beim adden einer Stadt:", err));
        };
        return(
            <button onClick={handleAddCity}>
                +
            </button>
        )
    }

    return (
        <div className="current">
            <div className="heading">
                <div className="data">
                    <h1>{capitalizeFirstLetter(city)}</h1>
                    <p>{formattedTime}</p>
                </div>
                <div className="add">
                    <AddCity cityName={city} onCityAdded={(city) => console.log(city)}/>
                </div>
            </div>
            <div className="current-temp">
                <img src={currentIconSrc} alt="" width="100px" height="auto"/>
                <p className="temp">{temperature}<sup>°C</sup></p>
            </div>
            <div className="infos">
                <div className="info">
                    <WaterOutline height="24px" width="24px"/>
                    <p>{humidity}%</p>
                </div>
                <div className="info">
                    <ThermometerOutline height="24px" width="24px"/>
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