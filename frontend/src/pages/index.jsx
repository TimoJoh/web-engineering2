import React, {useState} from "react";
import Header from "../components/Header/header";
import Footer from "../components/Footer/footer";
import Weatherdash from "../components/Weatherdash/weatherdash";


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