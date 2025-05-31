import React from 'react';
import axios from 'axios';

const DeleteCityButton = ({ cityName, onCityDeleted }) => {
    const handleDeleteCity = async () => {
        try {
            const token = localStorage.getItem('token');
            const headers = {};

            if (token) {
                headers['Authorization'] = `Bearer ${token}`;
            }

            const response = await axios.delete('http://localhost:8080/api/cities', {
                headers,
                params: { name: cityName },
                withCredentials: true
            });

            console.log("City deleted:", cityName);
            if (onCityDeleted) {
                onCityDeleted(cityName);
            }
        } catch (error) {
            console.error("Error deleting city:", error);
        }
    };

    return (
        <button onClick={handleDeleteCity}>
            🗑{cityName}
        </button>
    );
};

export default DeleteCityButton;
