import React, { useState, useEffect, useRef } from "react";
import "./header.css";
import { IoSearchOutline } from "react-icons/io5";
import Logo from "../../assets/logo.png";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import Login from "../Login/login";

const Header = ({ onCitySelect }) => {
    const navigate = useNavigate();

    const [query, setQuery] = useState("");
    const [results, setResults] = useState([]);
    const [selected, setSelected] = useState(false);
    const [dropdownOpen, setDropdownOpen] = useState(false);
    const [windowWidth, setWindowWidth] = useState(window.innerWidth);

    const headerRef = useRef(null);

    // Track window resize
    useEffect(() => {
        const handleResize = () => setWindowWidth(window.innerWidth);
        window.addEventListener("resize", handleResize);
        return () => window.removeEventListener("resize", handleResize);
    }, []);

    // Close dropdown if window is resized to >=768
    useEffect(() => {
        if (windowWidth >= 1024) {
            setDropdownOpen(false);
        }
    }, [windowWidth]);

    // Fetch city/town/village suggestions
    useEffect(() => {
        if (selected || query.length < 3) {
            setResults([]);
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
        setDropdownOpen(false);
        onCitySelect(cityName);
        navigate(`/weather/${encodeURIComponent(cityName)}`);
    };

    // Toggle dropdown open/close only on small screens
    const handleHeaderClick = () => {
        if (windowWidth < 1024) {
            setDropdownOpen((prev) => !prev);
        }
    };

    // Prevent header click toggle when clicking logo
    const handleLogoClick = (e) => {
        e.stopPropagation();
        navigate("/");
    };

    return (
        <>
            <section
                className="header-section"
                ref={headerRef}
                onClick={handleHeaderClick}
                style={{ cursor: windowWidth < 1024 ? "pointer" : "default" }}
            >
                <div>
                    <img
                        src={Logo}
                        alt="Logo"
                        style={{ cursor: "pointer" }}
                        onClick={handleLogoClick}
                    />
                </div>

                {/* Show search + login in header ONLY if window width >= 768 */}
                {windowWidth >= 1024 && (
                    <>
                        <div className="search-container" onClick={(e) => e.stopPropagation()}>
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
                                onClick={(e) => e.stopPropagation()}
                            />

                            {results.length > 0 && (
                                <ul className="autocomplete-dropdown" onClick={(e) => e.stopPropagation()}>
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

                        <div onClick={(e) => e.stopPropagation()}>
                            <Login />
                        </div>
                    </>
                )}


            {/* Dropdown content only visible on small screens and when open */}
            {dropdownOpen && windowWidth < 1024 && (
                <section
                    className="dropdown"
                    onClick={(e) => e.stopPropagation()}
                >
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
                            onClick={(e) => e.stopPropagation()}
                        />

                        {results.length > 0 && (
                            <ul className="autocomplete-dropdown" onClick={(e) => e.stopPropagation()}>
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

                    <div onClick={(e) => {
                        e.stopPropagation();
                    }}>
                        <Login />
                    </div>
                </section>
            )}
            </section>
        </>
    );
};

export default Header;
