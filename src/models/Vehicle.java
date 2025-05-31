/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author HP
 */
public abstract class Vehicle {
    protected int id;
    protected int customerId;
    protected String type; // e.g. "Car" or "Motorcycle"
    protected String model;
    protected String licensePlate;
    
    public Vehicle(int id, int customerId, String type, String model, String licensePlate) {
        this.id = id;
        this.customerId = customerId;
        this.type = type;
        this.model = model;
        this.licensePlate = licensePlate;
    }
    
    public Vehicle(int customerId, String type, String model, String licensePlate) {
        this.customerId = customerId;
        this.type = type;
        this.model = model;
        this.licensePlate = licensePlate;
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getModel() {
        return model;
    }

    public String getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        return this.licensePlate;
    }
    
    public abstract String printVehicleInfo();
}
