import React, { createContext, useState, useEffect } from "react";

export const UserContext = createContext();

export const UserProvider = ({ children }) => {
    const [firstName, setFirstName] = useState("");

    useEffect(() => {
        // Fetch logged-in user when app starts
        fetch("http://localhost:8080/api/auth/me", {
            method: "GET",
            credentials: "include",
        })
            .then((res) => res.json())
            .then((data) => {
                if (data.firstName) {
                    setFirstName(data.firstName);
                }
            });
    }, []);

    const logout = async () => {
        await fetch("http://localhost:8080/api/auth/logout", {
            method: "GET",
            credentials: "include",
        });
        setFirstName("");
    };

    return (
        <UserContext.Provider value={{ firstName, setFirstName }}>
            {children}
        </UserContext.Provider>
    );
};
