package com.example.get_vehicle;

public class Add_Vehicle_Information_Insert {

    String Email,phone,vehiclePapers,firstOwnerInfo,Vehicleage,name;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVehiclePapers() {
        return vehiclePapers;
    }

    public void setVehiclePapers(String vehiclePapers) {
        this.vehiclePapers = vehiclePapers;
    }

    public String getFirstOwnerInfo() {
        return firstOwnerInfo;
    }

    public void setFirstOwnerInfo(String firstOwnerInfo) {
        this.firstOwnerInfo = firstOwnerInfo;
    }

    public String getVehicleage() {
        return Vehicleage;
    }

    public void setVehicleage(String vehicleage) {
        Vehicleage = vehicleage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Add_Vehicle_Information_Insert(String email, String phone, String vehiclePapers, String firstOwnerInfo, String vehicleage, String name) {
        Email = email;
        this.phone = phone;
        this.vehiclePapers = vehiclePapers;
        this.firstOwnerInfo = firstOwnerInfo;
        Vehicleage = vehicleage;
        this.name = name;
    }
}
