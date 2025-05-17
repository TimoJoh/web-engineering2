import React from "react";
import "./header.css";
import IonIcon from '@reacticons/ionicons';
import Logo from "../../assets/logo.png";


const Header = () => {
  return (
    <section className="header-section">
      <div>
        <img src={Logo} alt="Logo" />
      </div>
      <div>
        <IonIcon name="search-outline" />
        <input type="text" placeholder="Search here" />
      </div>
      <div>
        <IonIcon name="person-outline" />
        <button>Login</button>
      </div>
    </section>
  );
};

export default Header;