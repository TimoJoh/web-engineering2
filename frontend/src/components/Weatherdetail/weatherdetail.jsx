import React from 'react';
import './weatherdetail.css';
import DetailCurrentData from "../Detail-current/detail-current-data";
import DetailForecastData from "../Detail-forecast/detail-forecast-data";
import DetailHourlyData from "../Detail-hourly/detail-hourly-data";
import DetailRainmap from "../Detail-rainmap/details-rainmap";
import DetailSunData from "../Detail-sunset-rise/detail-sunset-rise-data";

const loc = {lat: 52.519, lon: 13.408};

const Weatherdetail = ({city}) => {
    return (
        <div className="weatherdetail">
            <DetailCurrentData city={city}/>
            <DetailHourlyData city={city}/>
            <DetailForecastData city={city}/>
            <DetailSunData city={city}/>
            <DetailRainmap city={city} data={loc} />
        </div>
    )
}

export default Weatherdetail;