import React from "react";
import './detail-hourly.css';
import {
    ComposedChart,
    Area,
    Bar,
    XAxis,
    YAxis,
    Tooltip,
    ResponsiveContainer,
} from 'recharts';
import {Clock} from "lucide-react";
import {getWeatherIcon} from "../Icon-fetch/icon-fetch";


const generateColorStops = (temperatures, tempColorMap) => {
    const maxTemp = Math.max(...temperatures);
    const minTemp = Math.min(...temperatures);
    const extendedMin = minTemp - 10; // New start of the gradient
    const stops = [];

    tempColorMap
        .filter(([temp]) => temp >= extendedMin && temp <= maxTemp)
        .sort((a, b) => a[0] - b[0])
        .forEach(([temp, color]) => {
            const offset = ((temp - extendedMin) / (maxTemp - extendedMin)) * 100;
            stops.push(
                <stop key={temp} offset={`${offset.toFixed(2)}%`} stopColor={color} />
            );
        });

    const lastColor = tempColorMap
        .filter(([temp]) => temp <= maxTemp)
        .sort((a, b) => b[0] - a[0])[0][1];

    stops.push(
        <stop key="max" offset="100%" stopColor={lastColor} />
    );

    return stops;
};

const generateColorStopsLine = (temperatures, tempColorMap) => {
    const maxTemp = Math.max(...temperatures);
    const minTemp = Math.min(...temperatures);
    const extendedMin = minTemp - 10;
    const midPoint = extendedMin + 0.475* (maxTemp - extendedMin); // 50% buffer start

    const stops = [];

    tempColorMap
        .filter(([temp]) => temp >= midPoint && temp <= maxTemp)
        .sort((a, b) => a[0] - b[0])
        .forEach(([temp, color]) => {
            const offset = ((temp - midPoint) / (maxTemp - midPoint)) * 100;
            stops.push(
                <stop key={temp} offset={`${offset.toFixed(2)}%`} stopColor={color} />
            );
        });

    // Ensure the gradient reaches 100% with the last color
    const lastColor = tempColorMap
        .filter(([temp]) => temp <= maxTemp)
        .sort((a, b) => b[0] - a[0])[0][1];

    stops.push(
        <stop key="max" offset="100%" stopColor={lastColor} />
    );

    return stops;
};

const CustomTooltip = ({ active, payload, label }) => {
    if (active && payload && payload.length) {
        return (
            <div
                style={{
                    backgroundColor: 'white',
                    borderRadius: '10px',
                    boxShadow: '0 4px 15px rgba(0, 0, 0, 0.1)', // soft shadow
                    padding: '10px 14px',
                    border: 'none', // explicitly remove border
                }}
            >
                <p style={{ margin: 0, fontWeight: 600}}>{`${label}:00`}</p>
                {payload.map((entry, index) => (
                    <p key={index} style={{ margin: 0, color: "black"}}>
                        {entry.name === "Temperature (°C)"
                            ? `${entry.name}: ${Math.round(entry.value)}`
                            : `${entry.name}: ${entry.value}`}
                    </p>
                ))}
            </div>
        );
    }
    return null;
};

const WeatherIconTick = ({ x, y, payload, data }) => {
    const entry = data.find(d => d.hour.toString() === payload.value.toString());
    if (!entry || !entry.condition) return null;

    return (
        <image
            href={entry.condition}
            x={x - 12}
            y={y - 32}
            width={24}
        />
    );
};

const HighlightDot = ({ cx, cy, index, maxIndex, minIndex }) => {
    if (cx === null || cy === null) return null;

    if (index === maxIndex) {
        return (
            <>
                <circle
                    cx={cx}
                    cy={cy}
                    r={4}
                    fill="none"
                    stroke="#454545"
                    strokeWidth={3}
                />
                <text
                    x={cx}
                    y={cy - 10}
                    textAnchor="middle"
                    fill="#454545"
                    fontWeight="bold"
                    fontSize={14}
                >
                    H
                </text>
            </>
        );
    }

    if (index === minIndex) {
        return (
            <>
                <circle
                    cx={cx}
                    cy={cy}
                    r={4}
                    fill="none"
                    stroke="#454545"
                    strokeWidth={3}
                />
                <text
                    x={cx}
                    y={cy - 10}
                    textAnchor="middle"
                    fill="#454545"
                    fontWeight="bold"
                    fontSize={14}
                >
                    L
                </text>
            </>
        );
    }

    return null;
};

const DetailHourly = ({apiData, current}) => {

    if (!apiData || !apiData.list) {
        return <div>Loading...</div>;
    }

    const data = apiData.list.slice(0,24).map((entry) => {
        console.log(entry);
        return {
            hour: entry.formattedTime.split(":")[0],
            temperature: entry.main.temp,
            precipitation: entry.rain?.["1h"] ?? 0,
            condition: getWeatherIcon(entry.weather?.[0]?.description, entry.formattedTime, current.sunrise, current.sunset),
        };
    });

    const minTemp = Math.min(...data.map(d => d.temperature));
    const maxTemp = Math.max(...data.map(d => d.temperature));
    const maxIndex = data.findIndex(d => d.temperature === maxTemp);
    const minIndex = data.findIndex(d => d.temperature === minTemp);
    const temperatures = data.map(d => d.temperature);
    const tempColorMap = [
        [-40, "#191F7F"],
        [-30, "#2A4CD9"],
        [-20, "#1B5BE6"],
        [-10, "#1991FF"],
        [-5, "#3DBBF9"],
        [0, "#4ABFE9"],
        [5, "#51C9DB"],
        [15, "#78D435"],
        [20, "#E2D829"],
        [25, "#FFDE1B"],
        [30, "#FF7012"],
        [35, "#E0312B"],
        [40, "#DB0000"],
        [50, "#7D0000"],
    ];
    const gradientStops = generateColorStops(temperatures, tempColorMap);
    const gradientStopsLine = generateColorStopsLine(temperatures, tempColorMap);

    return (
        <div className="hourly">
            <div className="hourly-heading">
                <Clock size={18} color="#000000" strokeWidth="2.75px"/>
                <h1>Hourly Forecast</h1>
            </div>
            <ResponsiveContainer width="100%" height={250}>
                <ComposedChart data={data} margin={{ top: 10, right: 0, left: 0, bottom: 0}}>
                    <XAxis
                        dataKey="hour"
                        orientation="top"
                        axisLine={false}
                        tickLine={false}
                        xAxisId="top"
                        tick={(props) => <WeatherIconTick {...props} data={data} />}
                    />
                    <XAxis
                        dataKey="hour"
                        orientation="bottom"
                        tickLine={false}
                        xAxisId="bottom"
                        interval={2}
                    />
                    <YAxis yAxisId="left"
                           domain={[Math.round(minTemp) - 10, Math.round(maxTemp) + 5]}
                           tickFormatter={(value) => `${value}°`}
                    />
                    <YAxis yAxisId="right"
                           domain={[0, 3]}
                           orientation="right"
                    />
                    <Tooltip content={<CustomTooltip />} labelFormatter={(value) => `${value}:00`}/>

                    <defs>
                        <linearGradient id="tempGradient" x1="0" y1="1" x2="0" y2="0">
                            {gradientStops}
                        </linearGradient>
                        <linearGradient id="tempGradientLine" x1="0" y1="1" x2="0" y2="0">
                            {gradientStopsLine}
                        </linearGradient>
                    </defs>

                    <Area
                        connectNulls={true}
                        xAxisId="bottom"
                        yAxisId="left"
                        type="monotone"
                        dataKey="temperature"
                        stroke="url(#tempGradientLine)"
                        strokeWidth={2}
                        fill="url(#tempGradient)"
                        fillOpacity={0.6}
                        dot={(dotProps) => {
                            const { key, ...rest } = dotProps;
                            return (
                                <HighlightDot
                                    key={key}
                                    {...rest}
                                    maxIndex={maxIndex}
                                    minIndex={minIndex}
                                />
                            );
                        }}
                        activeDot={{fill: "white", stroke: "#454545"}}
                        name="Temperature (°C)"
                        baseValue="dataMin"
                    />

                    <Bar
                        xAxisId="bottom"
                        yAxisId="right"
                        dataKey="precipitation"
                        barSize={20}
                        fill="#2150DD"
                        name="Precipitation (mm)"
                    />
                </ComposedChart>
            </ResponsiveContainer>
        </div>
    )
}

export default DetailHourly;