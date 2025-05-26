import React from 'react';
import './weatherdash.css';
import WeathercardData from "../Weathercard/WeatherCardData";


const Weatherdash = ({data}) => {
    return (
        <WeathercardData city={data} />
    );
}

export default Weatherdash;