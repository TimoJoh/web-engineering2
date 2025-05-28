import React, { useState } from "react";
import ReactDOM from "react-dom";
import "./login.css";
import {PersonOutline} from "react-ionicons";
import Register from "../Register/register";

function LoginModal({ onClose, onSwitchToRegister, onLoginSuccess  }) {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const handleLogin = async (e) => {
        e.preventDefault();

        const jsonData = {
            email: email,
            password: password
        };

        try {
            const response = await fetch("http://localhost:8080/api/auth/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(jsonData),
                credentials: "include",
            });

            if (response.status === 302 || response.status === 200) {
                // Nach erfolgreichem Login: Benutzerinfos holen
                const userResponse = await fetch("http://localhost:8080/api/auth/me", {
                    method: "GET",
                    credentials: "include",
                });

                if (userResponse.ok) {
                    const userData = await userResponse.json();
                    onLoginSuccess(userData.firstName);
                    console.log("Willkommen,", userData.firstName);
                    console.log(userData)// Oder weiterverarbeiten
                } else {
                    onLoginSuccess(""); // Fallback, falls /me scheitert
                }
            } else {
                setError("Login fehlgeschlagen. Bitte überprüfe deine Zugangsdaten.");
            }
        } catch (err) {
            console.error("Fehler beim Login:", err);
            setError("Ein Fehler ist aufgetreten.");
        }
    };


    return ReactDOM.createPortal(
        <div className="modal-overlay">
            <div className="modal">
                <button className="close-button" onClick={onClose}>
                    &times;
                </button>
                <h2>Login</h2>
                <form onSubmit={handleLogin}>
                    <label>Email</label>
                    <input
                        type="email"
                        placeholder="Enter email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                    <label>Password</label>
                    <input
                        type="password"
                        placeholder="Enter password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                    <button type="submit">Login</button>
                    {error && <p className="error">{error}</p>}
                </form>
                <button className="register" onClick={onSwitchToRegister}>
                    Register
                </button>
            </div>
        </div>,
        document.getElementById("modal-root")
    );
}

function RegisterModal({ onClose, onSwitchToLogin }) {
    return ReactDOM.createPortal(
        <>
            <Register onClose={onClose} onSwitchToLogin={onSwitchToLogin}/>
        </>,
        document.getElementById("modal-root")
    );
}

export default function Login() {
    const [firstName, setFirstName] = useState("");
    const [isOpen, setIsOpen] = useState(false);
    const [showRegister, setShowRegister] = useState(false);

    const handleClose = () => {
        setIsOpen(false);
        setShowRegister(false);
    };

    return (
        <>
            {firstName ? (
                <>
                    <button className="icon-button">
                        <PersonOutline />
                    </button>
                    <button className="open-button">
                        {firstName}
                    </button>
                </>
            ) : (
                <>
                    <button className="icon-button" onClick={() => setIsOpen(true)}>
                        <PersonOutline />
                    </button>
                    <button className="open-button" onClick={() => setIsOpen(true)}>
                        Login
                    </button>
                </>
            )}

            {isOpen && !showRegister && (
                <LoginModal
                    onClose={handleClose}
                    onSwitchToRegister={() => setShowRegister(true)}
                    onLoginSuccess={(name) => {
                        setFirstName(name);
                        handleClose();
                    }}
                />
            )}

            {isOpen && showRegister && (
                <RegisterModal
                    onClose={handleClose}
                    onSwitchToLogin={() => setShowRegister(false)}
                />)}
        </>
    );
}