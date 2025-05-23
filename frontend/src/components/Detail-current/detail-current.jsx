import Clear from "../../assets/weathericons/clear-day.svg";
import {ThermometerOutline, WaterOutline} from "react-ionicons";
import {Wind} from "lucide-react";
import React from "react";
import './detail-current.css';

const DetailCurrent = () => {
    return (
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
                    <ThermometerOutline height="24px" width="24px"/>
                    <p>22°C</p>
                </div>
                <div className="info">
                    <Wind size={24}/>
                    <p>12km/h</p>
                </div>
            </div>
        </div>
    )
}

export default DetailCurrent;