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

    protected int id;
    protected int vehicleId;
    protected String type;
    protected String description;
    protected double cost;

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

    private List<ServiceDetail> details;

    public void setDetails(List<ServiceDetail> details) {
        this.details = details;
    }

    public List<ServiceDetail> getDetails() {
        if (this.details != null) {
            return this.details;
        }
        ServiceDetailService sds = new ServiceDetailService();
        return sds.getByServiceId(this.getId());
    }

}
