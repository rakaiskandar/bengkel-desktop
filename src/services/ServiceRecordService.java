/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import utils.Database;
import interfaces.ServiceRecordInterface;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.ServiceDetail;
import models.ServiceRecord;
import models.SparePart;
/**
 *
 * @author HP
 */
public class ServiceRecordService implements ServiceRecordInterface {
    private final Database db = new Database();

    @Override
    public ServiceRecord getById(int id) {
        String sql = "SELECT * FROM services WHERE id = ?";
        try (ResultSet rs = db.selectQuery(sql, id)) {
            if (rs.next()) {
                return new ServiceRecord(
                    rs.getInt("id"),
                    rs.getInt("vehicle_id"),
                    rs.getString("type"),
                    rs.getString("description"),
                    rs.getDouble("cost")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error getById: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<ServiceRecord> getByVehicleId(int vehicleId) {
        List<ServiceRecord> list = new ArrayList<ServiceRecord>();
        String sql = "SELECT * FROM services WHERE vehicle_id = ?";
        try (ResultSet rs = db.selectQuery(sql, vehicleId)) {
            while (rs.next()) {
                list.add(new ServiceRecord(
                    rs.getInt("id"),
                    rs.getInt("vehicle_id"),
                    rs.getString("type"),
                    rs.getString("description"),
                    rs.getDouble("cost")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error getByVehicleId: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<ServiceRecord> getAll() {
        List<ServiceRecord> list = new ArrayList<ServiceRecord>();
        String sql = "SELECT * FROM services";
        try (ResultSet rs = db.selectQuery(sql)) {
            while (rs.next()) {
                list.add(new ServiceRecord(
                    rs.getInt("id"),
                    rs.getInt("vehicle_id"),
                    rs.getString("type"),
                    rs.getString("description"),
                    rs.getDouble("cost")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error getAll: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean addService(ServiceRecord sr) {
        String sql = "INSERT INTO services (vehicle_id, type, description, cost) VALUES (?, ?, ?, ?)";
        return db.executeUpdate(sql, sr.getVehicleId(), sr.getType(), sr.getDescription(), sr.getCost()) > 0;
    }

    @Override
    public boolean updateService(ServiceRecord sr) {
        String sql = "UPDATE services SET vehicle_id = ?, type = ?, description = ?, cost = ? WHERE id = ?";
        return db.executeUpdate(sql, sr.getVehicleId(), sr.getType(), sr.getDescription(), sr.getCost(), sr.getId()) > 0;
    }

    @Override
    public boolean deleteService(int id) {
        String sql = "DELETE FROM services WHERE id = ?";
        return db.executeUpdate(sql, id) > 0;
    }

    public double calculateSpareCost() {
        ServiceRecord sds = new ServiceRecord();
        SparePartService sps = new SparePartService();
        double total = 0;
        for (ServiceDetail d : sds.getDetails()) {
            SparePart part = sps.getSparePartById(d.getSparePartId());
            total += part.getPrice() * d.getQuantity();
        }
        return total;
    }
    
}
