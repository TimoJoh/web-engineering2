import React, { useState, useEffect } from "react";
import "./header.css";
import {IoSearchOutline} from "react-icons/io5";
import Logo from "../../assets/logo.png";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import Login from "../Login/login";

const Header = ({ onCitySelect }) => {
    const navigate = useNavigate();

    const [query, setQuery] = useState("");
    const [results, setResults] = useState([]);
    const [selected, setSelected] = useState(false);

    // Fetch city/town/village suggestions
    useEffect(() => {
        if (selected || query.length < 3) {
            return;
        }

        const delayDebounce = setTimeout(() => {
            fetchSuggestions(query);
        }, 300);

        return () => clearTimeout(delayDebounce);
    }, [query, selected]);

    const fetchSuggestions = async (input) => {
        try {
            const response = await axios.get("https://photon.komoot.io/api/", {
                params: {
                    q: input,
                    limit: 10,
                },
            });

            const raw = response.data.features;

            // Filter: only cities, towns, villages
            const filtered = raw.filter((place) => {
                const type = place.properties.osm_value;
                return ["city", "town", "village"].includes(type);
            });

            setResults(filtered);
        } catch (error) {
            console.error("Photon API error:", error);
        }
    };

    const handleSelectCity = (cityName) => {
        setQuery(cityName);
        setSelected(true);
        setResults([]);
        onCitySelect(cityName);
        navigate(`/weather/${encodeURIComponent(cityName)}`);
    };

    return (
        <section className="header-section">
            <div>
                <img src={Logo} alt="Logo" style={{ cursor: "pointer" }}
                     onClick={() => navigate("/")}  />
            </div>

            <div className="search-container">
                <IoSearchOutline />
                <input
                    type="text"
                    placeholder="Stadt eingeben"
                    value={query}
                    onChange={(e) => {
                        setQuery(e.target.value);
                        setSelected(false);
                    }}
                    onKeyDown={(e) => {
                        if (e.key === "Enter" && query.trim().length > 0) {
                            handleSelectCity(query.trim());
                        }
                    }}
                    autoComplete="off"
                />

                {results.length > 0 && (
                    <ul className="autocomplete-dropdown">
                        {results.map((place) => {
                            const p = place.properties;
                            const label = `${p.name}${p.state ? ", " + p.state : ""}${
                                p.country ? ", " + p.country : ""
                            }`;
                            return (
                                <li
                                    key={p.osm_id}
                                    className="suggestion-item"
                                    onClick={() => handleSelectCity(p.name)}
                                >
                                    {label}
                                </li>
                            );
                        })}
                    </ul>
                )}
            </div>

            <div >
                <Login />
            </div>
        </section>
    );
};

export default Header;
