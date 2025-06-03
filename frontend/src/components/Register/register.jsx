import React, { useState } from "react";
import ReactDOM from "react-dom";

// register Modal
export default function Register({ onClose, onSwitchToLogin }) {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [error, setError] = useState("");

    // Register
    const handleRegister = async (e) => {
        e.preventDefault();

        const jsonData = {
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
                body: JSON.stringify(jsonData),
                credentials: "include",
                redirect: "manual"
            });

            if (response.status === 200 || response.status === 302) {
                window.location.href = "/";
            } else {
                setError("Registrierung fehlgeschlagen. Bitte überprüfe deine Eingaben.");
            }
        } catch (err) {
            console.error("Fehler bei der Registrierung:", err);
            setError("Ein Fehler ist aufgetreten.");
        }
    };

    // create Register Popup -> add to modal root to fill screen
    return ReactDOM.createPortal(
        <div className="modal-overlay">
            <div className="modal">
                <button className="close-button" onClick={onClose}>
                    &times;
                </button>
                <h2>Register</h2>
                <form onSubmit={handleRegister}>
                    <label>First Name</label>
                    <input
                        type="text"
                        placeholder="Enter first name"
                        value={firstName}
                        onChange={(e) => setFirstName(e.target.value)}
                        required
                    />
                    <label>Last Name</label>
                    <input
                        type="text"
                        placeholder="Enter last name"
                        value={lastName}
                        onChange={(e) => setLastName(e.target.value)}
                        required
                    />
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
                    <button type="submit">Register</button>
                    {error && <p className="error">{error}</p>}
                </form>
                <button className="register" onClick={onSwitchToLogin}>
                    Already have an account? Login
                </button>
            </div>
        </div>,
        document.getElementById("modal-root")
    );
}
