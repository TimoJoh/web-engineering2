import React, { useState } from 'react';
import AddCityButton from '../CityUtils/AddCityButton';
import CityList from "../CityUtils/CityList";
import DeleteCity from "../CityUtils/DeleteCity";

const AddCityForm = () => {
    const [cityName, setCityName] = useState('');

    const handleInputChange = (event) => {
        setCityName(event.target.value);
    };

    const handleAddCity = () => {
        console.log('Adding city:', cityName);
    };

    const handleDeleteCity = () => {
        console.log("Delete city", cityName);
    }

    return (
        <div>
            <h2>Stadt hinzufügen</h2>
            <input
                type="text"
                value={cityName}
                onChange={handleInputChange}
                placeholder="Stadtname"
            />
            <AddCityButton cityName={cityName} onCityAdded={handleAddCity} />
            <DeleteCity cityName={cityName} onCityDeleted={handleDeleteCity} />
            <div><CityList /></div>
        </div>

    );
};

export default AddCityForm;