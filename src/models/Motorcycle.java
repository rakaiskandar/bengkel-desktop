/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author HP
 */
public class Motorcycle extends Vehicle {
    
    public Motorcycle(int customerId, String model, String licensePlate) {
        super(customerId, "Motorcycle", model, licensePlate);
    }
    
    public Motorcycle(int id, int customerId, String model, String licensePlate) {
        super(id, customerId, "Motorcycle", model, licensePlate);
    }
    
    @Override
    public String printVehicleInfo() {
        return "Motor - " + model;
    }
    
}
