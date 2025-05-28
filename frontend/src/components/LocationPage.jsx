import React, { useEffect, useState } from 'react';

const LocationPage = () => {
    const [coordinates, setCoordinates] = useState('Geokoordinaten werden geladen...');
    const [time, setTime] = useState('');

    useEffect(() => {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(
                position => {
                    const { latitude, longitude } = position.coords;
                    setCoordinates(`Geokoordinaten: ${latitude}, ${longitude}`);

                    // an Spring Boot API senden
                    fetch('http://localhost:8080/api/location/save', {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify({ latitude, longitude }),
                    })
                        .then(response => {
                            if (!response.ok) {
                                throw new Error(`HTTP-Fehler: ${response.status}`);
                            }
                            return response.text();
                        })
                        .then(data => {
                            console.log("API-Antwort:", data); // Bestätigung vom Backend
                        })
                        .catch(error => {
                            console.error("Fehler beim Senden der Koordinaten:", error);
                        });
                },
                () => setCoordinates('Fehler beim Abrufen der Position.')
            );
        } else {
            setCoordinates('Geolocation wird nicht unterstützt.');
        }

        // Zeit aktualisieren
        const interval = setInterval(() => {
            const now = new Date();
            setTime(`Systemzeit: ${now.toLocaleTimeString()}`);
        }, 1000);

        return () => clearInterval(interval);
    }, []);

    return (
        <div>
            <h1>Dein Standort</h1>
            <p>{coordinates}</p>
            <p>{time}</p>
        </div>
    );
};

export default LocationPage;
