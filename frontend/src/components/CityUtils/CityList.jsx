import React, { useEffect, useState } from 'react';
import axios from 'axios';
import WeathercardData from "../Weathercard/WeatherCardData";
import {useNavigate} from "react-router-dom";

const CityList = () => {
    const navigate = useNavigate();
    const [cities, setCities] = useState([]);
    const [error, setError] = useState(null);

    const fetchCities = async () => {
        try {
            const token = localStorage.getItem('token');
            const headers = {};

            if (token) {
                headers['Authorization'] = `Bearer ${token}`;
            }

            const response = await axios.get('http://localhost:8080/api/cities', {
                headers,
                withCredentials: true
            });

            console.log("Cities fetched:", response.data);
            if(response.data.length === 0) {
                setCities([{ id: 0, name: "Friedrichshafen" }, { id: 1, name: "New York" }, { id: 2, name: "Tokyo" }]); // Fallback cities
            } else {
                setCities(response.data);
            }
        } catch (err) {
            console.error("Error fetching cities:", err);
            setError("Städte konnten nicht geladen werden.");
            setCities([{ id: 0, name: "Friedrichshafen" }, { id: 1, name: "New York" }, { id: 2, name: "Tokyo" }]); // Fallback cities
        }
    };

    useEffect(() => {
        fetchCities();
    }, []);

    const handleClick = (city) => {
        // Navigiere zur Weather-Seite mit dem Stadtnamen als URL-Parameter
        navigate(`/weather/${city}`);
    };

    return (
        <div className="weatherdash">
            {cities.map((data) => (
            <div
                key={data.id}
                onClick={() => handleClick(data.name)}>
                <WeathercardData city={data.name} />
            </div>
            ))}
        </div>
    );
};

export default CityList;
