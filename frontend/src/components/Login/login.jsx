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
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(jsonData),
                credentials: "include", // sendet Session-Cookie mit
                redirect: "manual" // verhindert automatisches Redirect
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
                    console.log("Willkommen,", userData.firstName); // Oder weiterverarbeiten
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
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    const handleRegister = async (e) => {
        e.preventDefault();

        const userData = {
            email,
            password,
            firstName,
            lastName
        };

        try {
            const response = await fetch("http://localhost:8080/api/auth/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(userData),
            });

            if (response.ok) {
                setSuccess("Registrierung erfolgreich! Du kannst dich jetzt einloggen.");
                setTimeout(() => {
                    onSwitchToLogin();
                }, 1500);
            } else {
                const message = await response.text();
                setError(message || "Registrierung fehlgeschlagen.");
            }
        } catch (err) {
            console.error("Fehler bei der Registrierung:", err);
            setError("Ein Fehler ist aufgetreten.");
        }
    };

    return ReactDOM.createPortal(
        <div className="modal-overlay">
            <div className="modal">
                <button className="close-button" onClick={onClose}>
                    &times;
                </button>
                <h2>Registrieren</h2>
                <form onSubmit={handleRegister}>
                    <label>Vorname</label>
                    <input
                        type="text"
                        placeholder="Vorname"
                        value={firstName}
                        onChange={(e) => setFirstName(e.target.value)}
                        required
                    />
                    <label>Nachname</label>
                    <input
                        type="text"
                        placeholder="Nachname"
                        value={lastName}
                        onChange={(e) => setLastName(e.target.value)}
                        required
                    />
                    <label>Email</label>
                    <input
                        type="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                    <label>Passwort</label>
                    <input
                        type="password"
                        placeholderChange={(e) => setPassword(e.target.value)}
                        required
                    />
                    <button type="submit">Registrieren</button>
                    {error && <p className="error">{error}</p>}
                    {success && <p className="success">{success}</p>}
                </form>
                <button className="register" onClick={onSwitchToLogin}>
                    Bereits registriert? Login
                </button>
            </div>
        </div>,
        document.getElementById("modal-root")
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