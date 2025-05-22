import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Header from "./components/Header/header";
import Footer from "./components/Footer/footer";
import Weatherdash from "./components/Weatherdash/weatherdash";
import LocationPage from "./components/LocationPage";

const App = () => {
    return (
        <Router>
            <Header />
            <Routes>
                <Route path="/" element={<Weatherdash />} />
                <Route path="/location" element={<LocationPage />} />
            </Routes>
            <Footer />
        </Router>
    );
};

export default App;