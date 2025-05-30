/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import interfaces.ServiceDetailInterface;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.Database;
import models.ServiceDetail;

/**
 *
 * @author HP
 */
public class ServiceDetailService implements ServiceDetailInterface {

    private final Database db = new Database();

    @Override
    public ServiceDetail getById(int id) {
        String sql = "SELECT * FROM service_details WHERE id = ?";
        try ( ResultSet rs = db.selectQuery(sql, id)) {
            if (rs.next()) {
                return new ServiceDetail(
                        rs.getInt("id"),
                        rs.getInt("service_id"),
                        rs.getInt("spare_part_id"),
                        rs.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error getById ServiceDetail: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<ServiceDetail> getByServiceId(int serviceId) {
        List<ServiceDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM service_details WHERE service_id = ?";
        try ( ResultSet rs = db.selectQuery(sql, serviceId)) {
            while (rs.next()) {
                list.add(new ServiceDetail(
                        rs.getInt("id"),
                        rs.getInt("service_id"),
                        rs.getInt("spare_part_id"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error getByServiceId: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<ServiceDetail> getAll() {
        List<ServiceDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM service_details";
        try ( ResultSet rs = db.selectQuery(sql)) {
            while (rs.next()) {
                list.add(new ServiceDetail(
                        rs.getInt("id"),
                        rs.getInt("service_id"),
                        rs.getInt("spare_part_id"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error getAll ServiceDetail: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean addServiceDetail(ServiceDetail detail) {
        String sql = "INSERT INTO service_details (service_id, spare_part_id, quantity) VALUES (?, ?, ?)";
        return db.executeUpdate(sql, detail.getServiceId(), detail.getSparePartId(), detail.getQuantity()) > 0;
    }

    @Override
    public boolean updateServiceDetail(ServiceDetail detail) {
        String sql = "UPDATE service_details SET spare_part_id = ?, quantity = ? WHERE id = ?";
        return db.executeUpdate(sql, detail.getSparePartId(), detail.getQuantity(), detail.getId()) > 0;
    }

    @Override
    public boolean deleteServiceDetail(int id) {
        String sql = "DELETE FROM service_details WHERE id = ?";
        return db.executeUpdate(sql, id) > 0;
    }
    
}
