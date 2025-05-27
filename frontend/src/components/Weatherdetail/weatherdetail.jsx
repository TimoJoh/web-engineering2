import React from 'react';
import './weatherdetail.css';
import DetailCurrentData from "../Detail-current/detail-current-data";
import DetailForecastData from "../Detail-forecast/detail-forecast-data";
import DetailHourlyData from "../Detail-hourly/detail-hourly-data";
import DetailRainmapData from "../Detail-rainmap/details-rainmap-data";
import DetailSunData from "../Detail-sunset-rise/detail-sunset-rise-data";

const loc = {lat: 52.519, lon: 13.408};

const Weatherdetail = ({city}) => {
    return (
        <div className="weatherdetail">
            <DetailCurrentData city={city}/>
            <DetailHourlyData city={city}/>
            <DetailForecastData city={city}/>
            <DetailSunData city={city}/>
            <DetailRainmapData city={city}/>
        </div>
    )
}

export default Weatherdetail;