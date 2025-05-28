import React, {useState, useEffect} from "react";
import Header from "../components/Header/header";
import axios from "../axiosInstance";
import Footer from "../components/Footer/footer";
import Weatherdash from "../components/Weatherdash/weatherdash";
import {useNavigate} from "react-router-dom";


const Index = () => {
    const navigate = useNavigate();
    const [cities, setCities] = useState([]);
    const [selectedCity, setSelectedCity] = useState("");

    useEffect(() => {
        axios.get("/cities")
            .then(res => {
                setCities(res.data);
            })
            .catch(err => {
                console.error("Fehler beim Abrufen der Städte:", err);
            });
    }, []);

    const handleClick = (city) => {
        // Navigiere zur Weather-Seite mit dem Stadtnamen als URL-Parameter
        navigate(`/weather/${city}`);
    };

    return (
        <>
            <Header onCitySelect={setSelectedCity}/>
            <div className="weatherdash">
                {cities.map((city) => (
                <div
                    key={city.id}
                    onClick={() => handleClick(city.name)}>
                    <Weatherdash data={city.name}/>
                </div>
            ))}
            </div>

            {/*<Footer/>*/}
        </>
    );
};

export default Index;