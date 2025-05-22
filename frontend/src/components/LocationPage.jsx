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
                    fetch('http://localhost:8080/api/location/save', { // Host und Port vom Backend prüfen
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify({ latitude, longitude }),
                    }).then(res => res.text()).then(console.log);
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
