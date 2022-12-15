package com.example.nutricount;

import java.io.Serializable;
import java.util.List;

public class Account implements Serializable {
    private int ID;
    private String email, password;
    private String firstName, lastName, gender;
    private double height, weight, goal;
    private List<String> allergy;

    Account(
            int ID, String email, String password,
            String firstName, String lastName, String gender,
            double height, double weight, double goal,
            List<String> allergy
    ) {
        this.ID = ID;
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

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setGoal(double goal) {
        this.goal = goal;
    }

    public void setAllergy(List<String> allergy) {
        this.allergy = allergy;
    }

    public int getID() {
        return ID;
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
