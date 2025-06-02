import React, {useState} from "react";
import Header from "../components/Header/header";
import Footer from "../components/Footer/footer";
import Weatherdetail from "../components/Weatherdetail/weatherdetail";
import {useParams} from "react-router-dom";


const Weather = () => {
    const { city } = useParams();
    const [selectedCity, setSelectedCity] = useState(city);

    return (
        <>
            <Header onCitySelect={setSelectedCity}/>
            <Weatherdetail city={selectedCity}/>
            <Footer/>
        </>
    );
};

export default Weather;