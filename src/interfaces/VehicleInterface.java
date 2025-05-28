/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import models.Vehicle;
import java.util.List;

/**
 *
 * @author BlazeBy
 */
public interface VehicleInterface {
    Vehicle getVehicleById(int id);
    List<Vehicle> getAllVehicles();
    boolean addVehicle(Vehicle vehicle);
    boolean updateVehicle(Vehicle vehicle);
    boolean deleteVehicle(int id);
}
