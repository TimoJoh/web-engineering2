import React from 'react';
import './details-rainmap.css';
import { MapContainer, Marker, TileLayer } from "react-leaflet";
import { UmbrellaOutline } from 'react-ionicons'


const DetailRainmap = ({data}) => {
    const openWeatherMapAPIKey = '6b6fc8b83de4c5852251641238546a05';
    return (
        <div className="map-wrapper">
            <div className="rain-heading">
                <UmbrellaOutline height="18px" width="18px" color="#000000"/>
                <h1>Precipitation</h1>
            </div>
            <MapContainer center={[data.lat, data.lon]} zoom={9} scrollWheelZoom={false}>
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                <TileLayer
                    attribution='Map data © <a href="https://openweathermap.org/">OpenWeatherMap</a>'
                    url={`http://maps.openweathermap.org/maps/2.0/weather/PA0/{z}/{x}/{y}?date=${data.dt}&appid=${openWeatherMapAPIKey}`}
                    opacity={1}
                />
                <Marker position={[data.lat, data.lon]}></Marker>
            </MapContainer>

        </div>
    );
}

export default DetailRainmap;