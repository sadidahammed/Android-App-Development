package com.example.get_vehicle;

public class Owner_Data_Insert {
    String Gender, Location ,Name, Password,Phone;

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public Owner_Data_Insert(String gender, String location, String name, String password, String phone) {
        Gender = gender;
        Location = location;
        Name = name;
        Password = password;
        Phone = phone;
    }
}
