import React, { useState, useEffect } from "react";
import "./header.css";
import {PersonOutline, SearchOutline} from "react-ionicons";
import Logo from "../../assets/logo.png";
import axios from "axios";

const Header = ({ onCitySelect }) => {
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

    return (
        <section className="header-section">
            <div>
                <img src={Logo} alt="Logo" />
            </div>

            <div className="search-container">
                <SearchOutline />
                <input
                    type="text"
                    placeholder="Stadt eingeben"
                    value={query}
                    onChange={(e) => {
                        setQuery(e.target.value);
                        setSelected(false); // Reset selection if user types
                    }}
                    onKeyDown={(e) => {
                        if (e.key === "Enter" && query.trim().length > 0) {
                            setSelected(true);
                            setResults([]);
                            onCitySelect(query.trim());
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
                                    onClick={() => {
                                        setQuery(p.name);        // Zeigt den Namen im Input
                                        setSelected(true);       // Verhindert weitere Abfragen
                                        setResults([]);          // Dropdown schließen
                                        onCitySelect(p.name);    // <-- Wetterdaten nur hier abrufen!
                                    }}
                                >
                                    {label}
                                </li>
                            );
                        })}
                    </ul>
                )}
            </div>

            <div className="login-container">
                <PersonOutline />
                <button>Login</button>
            </div>
        </section>
    );
};

export default Header;
