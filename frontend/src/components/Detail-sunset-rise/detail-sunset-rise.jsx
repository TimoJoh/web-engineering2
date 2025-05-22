import React, {useEffect, useState} from 'react';
import './detail-sunset-rise.css';
import {Sunrise, Sunset, Sun, Moon } from 'lucide-react';

const DetailSunsetRise = ({data}) => {
    const [angle, setAngle] = useState(0);
    const [isDay, setIsDay] = useState(true);


    useEffect(() => {
        const updatePosition = () => {
            const now = new Date();
            const today = now.toISOString().split("T")[0];

            const sunriseTime = new Date(`${today}T${data.sunrise}:00`);
            const sunsetTime = new Date(`${today}T${data.sunset}:00`);

            let angle = 0;
            let isDay = false;

            if (now >= sunriseTime && now <= sunsetTime) {
                // Daytime
                const total = sunsetTime - sunriseTime;
                const passed = now - sunriseTime;
                const percent = passed / total;
                angle = percent * 180; // 0°–180°
                isDay = true;
            } else {
                // Nighttime
                let nightStart, nightEnd;

                if (now < sunriseTime) {
                    // After midnight, before sunrise
                    nightStart = new Date(sunsetTime.getTime() - 86400000); // previous sunset
                    nightEnd = sunriseTime;
                } else {
                    // After sunset
                    nightStart = sunsetTime;
                    nightEnd = new Date(sunriseTime.getTime() + 86400000); // next day's sunrise
                }

                const total = nightEnd - nightStart;
                const passed = now - nightStart;
                const percent = passed / total;
                angle = 180 + percent * 180; // 180°–360°
            }

            setAngle(angle);
            setIsDay(isDay);
        };

        updatePosition();
        const interval = setInterval(updatePosition, 60 * 1000);
        return () => clearInterval(interval);
    }, [data.sunrise, data.sunset]);

    const celestialStyle = {
        transform: `rotate(${angle}deg) translateX(-165.525px) rotate(-${angle}deg) translate(-50%, -50%)`
    };

    return (
        <div className="suns">
            <div className="circle">
                <div className="sun-info sunrise">
                    <Sunrise className="icon" color="#ffa500"/>
                    <p className="state">Sunrise</p>
                    <p className="time">{data.sunrise}</p>
                </div>
                <div className="sun-info sunset">
                    <Sunset className="icon" color="#849AAA"/>
                    <p className="state">Sunset</p>
                    <p className="time">{data.sunset}</p>
                </div>

                <div className="sun" style={celestialStyle}>
                    <div className="celestial-wrapper">
                        {isDay ? <Sun color="#ffa500" size={24}/> : <Moon color="#849AAA" size={24}/>}
                    </div>
                </div>

            </div>
            <div className="horizon-line"></div>
            <div className="horizon">
                <p>HORIZON</p>
            </div>



        </div>
    )
}

export default DetailSunsetRise;