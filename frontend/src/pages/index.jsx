import React, {useState} from "react";
import Header from "../components/Header/header";
import Footer from "../components/Footer/footer";
import Weatherdash from "../components/Weatherdash/weatherdash";
import {useNavigate} from "react-router-dom";


const Index = () => {
    const navigate = useNavigate();
    const cities = ["Friedrichshafen", "Auckland", "Pretoria"];
    const [selectedCity, setSelectedCity] = useState("");

    const handleClick = (city) => {
        // Navigiere zur Weather-Seite mit dem Stadtnamen als URL-Parameter
        navigate(`/weather/${city}`);
    };

    return (
        <>
            <Header onCitySelect={setSelectedCity}/>
            <div className="weatherdash">
                {cities.map((city, index) => (
                <div
                    key={city}
                    onClick={() => handleClick(city)}>
                    <Weatherdash data={city}/>
                </div>
            ))}
            </div>

            {/*<Footer/>*/}
        </>
    );
};

export default Index;