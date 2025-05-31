import React from 'react';
import axios from 'axios';

const AddCityButton = ({ cityName, onCityAdded }) => {
    const handleAddCity = async () => {
        try {
            const token = localStorage.getItem('token');
            const headers = {};

            if (token) {
                headers['Authorization'] = `Bearer ${token}`;
            }

            const response = await axios.post('http://localhost:8080/api/cities',
                { name: cityName },
                {
                    headers,
                    withCredentials: true
                }
            );

            console.log("City added:", response.data);
            if (onCityAdded) {
                onCityAdded(response.data);
            }
        } catch (error) {
            console.error("Error adding city:", error);
        }
    };

    return (
        <button onClick={handleAddCity}>
            +
        </button>
    );
};

export default AddCityButton;
