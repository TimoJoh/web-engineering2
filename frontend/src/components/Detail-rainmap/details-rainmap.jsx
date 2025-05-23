import React from 'react';
import './details-rainmap.css';
import { MapContainer, Marker, TileLayer } from "react-leaflet";
import { UmbrellaOutline } from 'react-ionicons'


const DetailRainmap = ({data}) => {
    return (
        <div className="map-wrapper">
            <div className="rain-heading">
                <UmbrellaOutline height="18px" width="18px" color="#000000"/>
                <h1>Precipitation</h1>
            </div>
            <MapContainer center={[data.lat, data.lon]} zoom={10} scrollWheelZoom={false}>
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                <Marker position={[data.lat, data.lon]}></Marker>
            </MapContainer>

        </div>
    );
}

export default DetailRainmap;