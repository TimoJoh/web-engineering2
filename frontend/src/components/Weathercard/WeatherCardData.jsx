import React, { useEffect, useState } from "react";
import axios from "axios";
import Weathercard from "./weathercard";

function WeathercardData({ city }) {
    const [weatherData, setWeatherData] = useState(null);
    const [forecastData, setForecastData] = useState(null)
    const [error, setError] = useState(null);

    useEffect(() => {
        if (!city) return;

        axios
            .get("http://localhost:8080/api/weather", { params: { city } })
            .then((res) => setWeatherData(res.data))
            .catch((err) => {
                console.error("Fehler beim Laden der Wetterdaten:", err);
                setError("Wetterdaten konnten nicht geladen werden.");
            });

        axios
            .get("http://localhost:8080/api/daily", { params: { city } })
            .then((res) => setForecastData(res.data))
            .catch((err) => {
                console.error("Fehler beim Laden der Wetterdaten:", err);
                setError("Wetterdaten konnten nicht geladen werden.");
            });
    }, [city]);



    if (error) return <p>{error}</p>;
    if (!weatherData) return <p>Lade Wetterdaten…</p>;

    return <Weathercard data={weatherData} forecast={forecastData} />;
}

export default WeathercardData;