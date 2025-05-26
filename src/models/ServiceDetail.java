/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author HP
 */
public class ServiceDetail extends ServiceRecord {
    private int id;
    private int serviceId;
    private int sparePartId;
    private int quantity;
    
    public ServiceDetail(int serviceId, int sparePartId, int quantity) {
        this.serviceId   = serviceId;
        this.sparePartId = sparePartId;
        this.quantity    = quantity;
    }

    public ServiceDetail(int id, int serviceId, int sparePartId, int quantity) {
        this.id          = id;
        this.serviceId   = serviceId;
        this.sparePartId = sparePartId;
        this.quantity    = quantity;
    }

    public int getId() {
        return id;
    }

    public int getServiceId() {
        return serviceId;
    }

    public int getSparePartId() {
        return sparePartId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public void setSparePartId(int sparePartId) {
        this.sparePartId = sparePartId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
   
}
