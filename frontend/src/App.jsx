import './index.css';
import React from "react";
import {
    BrowserRouter as Router,
    Routes,
    Route,
} from "react-router-dom";
import Index from "./pages/index";
import Weather from "./pages/weather";


const App = () => {
    return (
        <Router>
            <Routes>
                <Route exact path="/" element={<Index />} />
                <Route exact path="/weather" element={<Weather />} />
            </Routes>
        </Router>
    );
};

export default App;