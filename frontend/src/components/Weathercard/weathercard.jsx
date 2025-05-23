import React from "react";
import "./weathercard.css";
import Clear from "../../assets/weathericons/clear-day.svg"; // Platzhalter-Icon

const Weathercard = ({ data, forecast }) => {
    const {
        city,
        temperature,
        minTemperature,
        maxTemperature,
        condition,
        feelsLike,
        pressure,
        humidity,
        sunrise,
        sunset,
        windSpeed,
        windDegree,
        windDirection,
    } = data;

    // Beispielhafte Funktion, um basierend auf der Beschreibung ein Icon zu wählen (hier sehr rudimentär)
    const getIconByDescription = (description) => {
        if (description.includes("rain")) return Clear; // Beispiel: Regen-Icon ersetzen
        // Hier weitere Bedingungen einfügen
        return Clear; // Default Icon
    };

    return (
        <div className="card">
            <div className="header">
                <p>{city}</p>
            </div>
            <div className="today">
                <img src={Clear} alt={condition} />
                <p>Today</p>
            </div>
            <div className="today-temp">
                <p>{temperature}°C</p>
            </div>
            <div className="lh">
                <p>L: {minTemperature}°C</p>
                <p>H: {maxTemperature}°C</p>
            </div>

            <div className="forecast">
                {forecast?.list?.slice(0, 5).map((day, index) => (
                    <ul key={index}>
                        <li>
                            <p className="forecast-day">{day.weekday}</p>
                            <img
                                src={Clear}
                                alt={day.weather[0].description}
                            />
                            <div className="forecast-lh">
                                <p className="l">{Math.round(day.temp.min)}°</p>
                                <p className="h">{Math.round(day.temp.max)}°</p>
                            </div>
                        </li>
                    </ul>
                ))}
            </div>
        </div>
    );
};

export default Weathercard;
