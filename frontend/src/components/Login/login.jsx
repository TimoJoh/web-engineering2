import React, { useState } from "react";
import ReactDOM from "react-dom";
import "./login.css";

function Modal({ onClose }) {
    return ReactDOM.createPortal(
        <div className="modal-overlay">
            <div className="modal">
                <button className="close-button" onClick={onClose}>
                    &times;
                </button>
                <h2>Login</h2>
                <form>
                    <label>Email</label>
                    <input type="email" placeholder="Enter email" required />
                    <label>Password</label>
                    <input type="password" placeholder="Enter password" required />
                    <button type="submit">Login</button>
                </form>
            </div>
        </div>,
        document.getElementById("modal-root")
    );
}

export default function Login() {
    const [isOpen, setIsOpen] = useState(false);

    return (
        <>
            <button className="open-button" onClick={() => setIsOpen(true)}>
                Login
            </button>
            {isOpen && <Modal onClose={() => setIsOpen(false)} />}
        </>
    );
}
