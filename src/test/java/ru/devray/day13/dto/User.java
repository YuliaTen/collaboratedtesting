package ru.devray.day13.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    public int id;

    public String email;

    @JsonProperty("first_name")
    public String firstName;

    @JsonProperty("last_name")
    public String lastName;

    public String avatar;

    //Обязателен для работы jackson и корректной десериализации!!!
    public User(){}

    public User(int id, String email, String firstName, String lastName, String avatar) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
