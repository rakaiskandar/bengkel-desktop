/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author HP
 */
public class Car extends Vehicle {
    
    public Car(int customerId, String model, String licensePlate) {
        super(customerId, "Car", model, licensePlate);
    }

    public Car(int id, int customerId, String model, String licensePlate) {
        super(id, customerId, "Car", model, licensePlate);
    }

    @Override
    public String printVehicleInfo() {
        return "Mobil - " + model;
    }

}
