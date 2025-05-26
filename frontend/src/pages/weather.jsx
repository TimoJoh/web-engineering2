import React, {useState} from "react";
import Header from "../components/Header/header";
import Footer from "../components/Footer/footer";
import Weatherdetail from "../components/Weatherdetail/weatherdetail";


const Weather = () => {
    const [selectedCity, setSelectedCity] = useState("Berlin");

    return (
        <>
            <Header onCitySelect={setSelectedCity}/>
            <Weatherdetail city={selectedCity}/>
            {/*<Footer/>*/}
        </>
    );
};

export default Weather;