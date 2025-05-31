import React, {useState, useEffect} from "react";
import Header from "../components/Header/header";
import axios from "../axiosInstance";
import Footer from "../components/Footer/footer";
import Weatherdash from "../components/Weatherdash/weatherdash";
import {useNavigate} from "react-router-dom";


const Index = () => {
    const [selectedCity, setSelectedCity] = useState("");

    return (
        <>
            <Header onCitySelect={setSelectedCity}/>
            <Weatherdash />

            <Footer/>
        </>
    );
};

export default Index;