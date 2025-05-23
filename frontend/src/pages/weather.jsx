import React from "react";
import Header from "../components/Header/header";
import Footer from "../components/Footer/footer";
import Weatherdetail from "../components/Weatherdetail/weatherdetail";


const Weather = () => {
    return (
        <>
            <Header/>
            <Weatherdetail/>
            {/*<Footer/>*/}
        </>
    );
};

export default Weather;