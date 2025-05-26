import React, { useEffect, useState } from "react";
import axios from "axios";
import DetailCurrent from "./detail-current";

function DetailCurrentData({ city }) {
    const [weatherData, setWeatherData] = useState(null);
    const [error, setError] = useState(null);

    useEffect(() => {
        if (!city) return;

        setWeatherData(null);
        setError(null);

        axios
            .get("http://localhost:8080/api/weather", { params: { city } })
            .then((res) => setWeatherData(res.data))
            .catch((err) => {
                console.error("Fehler beim Laden der Wetterdaten:", err);
                setError("Wetterdaten konnten nicht geladen werden.");
            });

    }, [city]);



    if (error) return <p>{error}</p>;
    if (!weatherData) return <p>Lade Wetterdaten…</p>;

    return <DetailCurrent data={weatherData}/>;
}

export default DetailCurrentData;