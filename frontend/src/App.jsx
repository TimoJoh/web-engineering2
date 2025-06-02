import './index.css';
import React from "react";
import {
    BrowserRouter as Router,
    Routes,
    Route,
} from "react-router-dom";
import Index from "./pages/index";
import Weather from "./pages/weather";
import AddCityForm from "./components/TestCityUtil/AddCityForm";


const App = () => {
    return (
        <Router>
            <Routes>
                <Route exact path="/" element={<Index />} />
                <Route exact path="/weather/:city" element={<Weather />} />
                <Route exact path="/AddCityForm" element={<AddCityForm />}/>
            </Routes>
        </Router>
    );
};

export default App;