/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import interfaces.VehicleInterface;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Vehicle;
import models.Car;
import models.Motorcycle;
import utils.Database;

/**
 *
 * @author BlazeBy
 */
public class VehicleService implements VehicleInterface {

    private final Database db = new Database();

    @Override
    public Vehicle getVehicleById(int id) {
        String sql = "SELECT * FROM vehicles WHERE id = ?";
        try {
            ResultSet rs = db.selectQuery(sql, id);
            if (rs.next()) {
                return mapVehicle(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error getVehicleById: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> list = new ArrayList<>();
        String sql = "SELECT * FROM vehicles";
        try (ResultSet rs = db.selectQuery(sql)) {
            while (rs.next()) {
                list.add(mapVehicle(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error getAllVehicles: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean addVehicle(Vehicle vehicle) {
        String sql = "INSERT INTO vehicles (customer_id, type, model, license_plate) VALUES (?, ?, ?, ?)";
        return db.executeUpdate(sql,
                vehicle.getCustomerId(),
                vehicle.getType(),
                vehicle.getModel(),
                vehicle.getLicensePlate()
        ) > 0;
    }

    @Override
    public boolean updateVehicle(Vehicle vehicle) {
        String sql = "UPDATE vehicles SET customer_id = ?, type = ?, model = ?, license_plate = ? WHERE id = ?";
        return db.executeUpdate(sql,
                vehicle.getCustomerId(),
                vehicle.getType(),
                vehicle.getModel(),
                vehicle.getLicensePlate(),
                vehicle.getId()
        ) > 0;
    }

    @Override
    public boolean deleteVehicle(int id) {
        String sql = "DELETE FROM vehicles WHERE id = ?";
        return db.executeUpdate(sql, id) > 0;
    }

    private Vehicle mapVehicle(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int customerId = rs.getInt("customer_id");
        String type = rs.getString("type");
        String model = rs.getString("model");
        String license = rs.getString("license_plate");

        if (type.equalsIgnoreCase("car")) {
            return new Car(id, customerId, model, license);
        } else if (type.equalsIgnoreCase("motorcycle")) {
            return new Motorcycle(id, customerId, model, license);
        } else {
            return null;
        }
    }
}
