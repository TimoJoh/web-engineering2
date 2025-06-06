package com.weatherApp.WeatherWeb.api.Models;

import jakarta.persistence.*;
import java.util.List;

/**
 * Represents a user in the application.
 * <p>
 * The class is mapped to a database table that stores user information such as
 * email, password, and name, as well as their associated cities.
 */
@Entity
@Table(name = "users")
public class User {

    /**
     * Default constructor for JPA.
     */
    public User() {}

    /**
     * The primary key of the user (auto-generated).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The unique email address of the user.
     */
    @Column(nullable = false, unique = true, length = 45)
    private String email;

    /**
     * The encrypted password of the user.
     */
    @Column(nullable = false, length = 64)
    private String password;

    /**
     * The first name of the user.
     */
    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    /**
     * The last name of the user.
     */
    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    /**
     * List of cities associated with the user.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<City> cities;

    /**
     * Returns the ID of the user.
     *
     * @return User ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the user.
     *
     * @param id User ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the email address of the user.
     *
     * @return Email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email Email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the password of the user.
     *
     * @return Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password Password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the first name of the user.
     *
     * @return First name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName First name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of the user.
     *
     * @return Last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName Last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the list of cities associated with the user.
     *
     * @return List of cities
     */
    public List<City> getCities() {
        return cities;
    }

    /**
     * Sets the list of cities associated with the user.
     *
     * @param cities List of cities
     */
    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}