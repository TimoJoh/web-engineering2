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
                        .then(res => res.text())
                        .then(console.log)
                        .then(() => {
                            // Wetterdaten vom Backend holen
                            fetch(`http://localhost:8080/api/weather/current?latitude=${latitude}&longitude=${longitude}`)
                                .then(res => res.json())
                                .then(data => {
                                    console.log("Wetterdaten:", data);
                                    // Optional: setze hier einen State, um die Daten anzuzeigen
                                });
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
