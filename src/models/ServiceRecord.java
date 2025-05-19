/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.List;
import services.ServiceDetailService;

/**
 *
 * @author HP
 */
public class ServiceRecord {
    private int id;
    private int vehicleId;
    private String type;
    private String description;
    private double cost;

    public ServiceRecord() {
    }
    
    public ServiceRecord(int vehicleId, String type, String description, double cost) {
        this.vehicleId = vehicleId;
        this.type = type;
        this.description = description;
        this.cost = cost;
    }

    public ServiceRecord(int id, int vehicleId, String type, String description, double cost) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.type = type;
        this.description = description;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public double getCost() {
        return cost;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    
    public List<ServiceDetail> getDetails() {
        ServiceDetailService sds = new ServiceDetailService();
        // Pastikan panggil getId(), bukan direct this.id
        return sds.getByServiceId(this.getId());
    }
    
}
