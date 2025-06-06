import React, { useState, useContext } from "react";
import ReactDOM from "react-dom";
import "./login.css";
import { IoPersonOutline } from "react-icons/io5";
import Register from "../Register/register";
import { UserContext } from "../../UserContext";

// Login Modal
function LoginModal({ onClose, onSwitchToRegister }) {
    const { setFirstName } = useContext(UserContext);
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    // Login
    const handleLogin = async (e) => {
        e.preventDefault();

        try {
            const response = await fetch("http://localhost:8080/api/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                },
                body: new URLSearchParams({
                    username: email,
                    password: password,
                }),
                credentials: "include",
                redirect: "manual",

            });

            if (response.status === 302) {
                // Spring Security redirectet => prüfen, wohin
                const location = response.headers.get("Location");
                if (location && location.includes("error")) {
                    setError("Login failed. Bitte überprüfe die Zugangsdaten.");
                    return;
                }
            }

            if (response.ok) {
                const userResponse = await fetch("http://localhost:8080/api/auth/me", {
                    method: "GET",
                    credentials: "include",
                });

                if (userResponse.ok) {
                    const userData = await userResponse.json();
                    setFirstName(userData.firstName);
                    onClose();
                } else {
                    setFirstName("");
                }
                window.location.reload();
            } else {
                setError("Login failed. Bitte überprüfe die Zugangsdaten.");
            }
        } catch (err) {
            console.error("Fehler beim Login:", err);
            setError("Ein Fehler ist aufgetreten.");
        }
    };

    // create Login Popup -> add to modal root to fill screen
    return ReactDOM.createPortal(
        <div className="modal-overlay">
            <div className="modal" onClick={(e) => e.stopPropagation()}>
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

// Register Modal
function RegisterModal({ onClose, onSwitchToLogin }) {
    return ReactDOM.createPortal(
        <Register onClose={onClose} onSwitchToLogin={onSwitchToLogin} />,
        document.getElementById("modal-root")
    );
}

// Login Button
export default function Login() {
    const { firstName, logout } = useContext(UserContext);
    const [isOpen, setIsOpen] = useState(false);
    const [showRegister, setShowRegister] = useState(false);

    const handleClose = () => {
        setIsOpen(false);
        setShowRegister(false);
    };

    return (
        <>
            {firstName ? (
                // if logged in show name and sign out
                <>
                    <div className="login-container2">
                        <button className="icon-button2">
                            <IoPersonOutline fontSize="22px"/>
                        </button>
                        <button className="open-button2">{firstName}</button>
                    </div>
                    <div>
                        <button className="logout-button" onClick={logout}>
                            Sign Out
                        </button>
                    </div>
                </>
            ) : (
                // if not logged in
                <div className="login-container">
                    <button className="icon-button" onClick={() => setIsOpen(true)}>
                        <IoPersonOutline fontSize="22px"/>
                    </button>
                    <button className="open-button" onClick={() => setIsOpen(true)}>
                        Login
                    </button>
                </div>
            )}

            {isOpen && !showRegister && (
                // show login
                <LoginModal
                    onClose={handleClose}
                    onSwitchToRegister={() => setShowRegister(true)}
                />
            )}

            {isOpen && showRegister && (
                // show register if user clicks on register button
                <RegisterModal
                    onClose={handleClose}
                    onSwitchToLogin={() => setShowRegister(false)}
                />
            )}
        </>
    );
}
