package com.example.get_vehicle;

public class driver_data_Insert {
    String name,password, gender, phone,email,experience,licence,income;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public driver_data_Insert(String name, String password, String gender, String phone, String selectedLocation, String experience, String licence, String income) {
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.phone = phone;
        this.experience = experience;
        this.licence = licence;
        this.income = income;
        this.email = email;

    }
}
