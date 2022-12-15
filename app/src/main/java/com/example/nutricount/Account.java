package com.example.nutricount;

import java.util.List;

public class Account {
    private int ID;
    private String username, email, password;
    private String firstName, lastName, gender;
    private double height, weight, goal;
    private List<String> allergy;

    Account(
            int ID, String username, String email, String password,
            String firstName, String lastName, String gender,
            double height, double weight, double goal,
            List<String> allergy
    ) {
        this.ID = ID;
        this.username = username;
        this.email = email;
        this.password = password;

        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;

        this.height = height;
        this.weight = weight;
        this.goal = goal;
        this.allergy = allergy;
    }

    public int getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public double getGoal() {
        return goal;
    }

    public List<String> getAllergy() {
        return allergy;
    }
}
