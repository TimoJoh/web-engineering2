import axios from 'axios';
import './index.css';
import React from "react";
import Header from "./components/Header/header";
import Footer from "./components/Footer/footer";
import Weatherdash from "./components/Weatherdash/weatherdash";


const App = () => {
    return (
        <>
            <Header/>
            <Weatherdash/>
            <Footer/>
        </>
    );
};

export default App;