import React, { useState } from "react";
import ReactDOM from "react-dom";
import "./login.css";
import {PersonOutline} from "react-ionicons";
import Register from "../Register/register";

function LoginModal({ onClose, onSwitchToRegister  }) {
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
                // Erfolgreicher Login
                window.location.href = "/"; // oder z.B. /dashboard
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
    const [isOpen, setIsOpen] = useState(false);
    const [showRegister, setShowRegister] = useState(false);

    const handleClose = () => {
        setIsOpen(false);
        setShowRegister(false);
    };

    return (
        <>
            <button className="icon-button" onClick={() => setIsOpen(true)}>
                <PersonOutline />
            </button>
            <button className="open-button" onClick={() => setIsOpen(true)}>
                Login
            </button>

            {isOpen && !showRegister && (
                <LoginModal
                    onClose={handleClose}
                    onSwitchToRegister={() => setShowRegister(true)}
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