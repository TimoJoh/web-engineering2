import React, { useEffect, useState } from "react";
import axios from "axios";
import DetailHourly from "./detail-hourly";
import Loader from "../Loader/loader";

function DetailHourlyData({ city }) {
    const [weatherData, setWeatherData] = useState(null);
    const [currentData, setCurrentData] = useState(null);
    const [error, setError] = useState(null);

    useEffect(() => {
        if (!city) return;

        setCurrentData(null);
        setWeatherData(null);
        setError(null);

        axios
            .get("http://localhost:8080/api/hourly", { params: { city } })
            .then((res) => setWeatherData(res.data))
            .catch((err) => {
                console.error("Fehler beim Laden der Wetterdaten:", err);
                setError("Wetterdaten konnten nicht geladen werden.");
            });

        axios
            .get("http://localhost:8080/api/weather", { params: { city } })
            .then((res) => setCurrentData(res.data))
            .catch((err) => {
                console.error("Fehler beim Laden der Wetterdaten:", err);
                setError("Wetterdaten konnten nicht geladen werden.");
            });

    }, [city]);
    console.log(weatherData);



    if (error) return <p>{error}</p>;
    if (!weatherData) return <Loader/>;
    if (!currentData) return <Loader/>;

    return <DetailHourly apiData={weatherData} current={currentData}/>;
}

export default DetailHourlyData;