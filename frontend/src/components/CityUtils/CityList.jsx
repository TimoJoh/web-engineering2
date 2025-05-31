import React, { useEffect, useState } from 'react';
import axios from 'axios';

const CityList = () => {
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
            setCities(response.data);
        } catch (err) {
            console.error("Error fetching cities:", err);
            setError("Städte konnten nicht geladen werden.");
        }
    };

    useEffect(() => {
        fetchCities();
    }, []);

    return (
        <div>
            <h2>Meine Städte</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <ul>
                {cities.map((city) => (
                    <li key={city.id}>{city.name}</li>
                ))}
            </ul>
        </div>
    );
};

export default CityList;
