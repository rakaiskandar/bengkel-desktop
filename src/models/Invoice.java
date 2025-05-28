/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Date;

/**
 *
 * @author HP
 */
public class Invoice {
    // Constructor tanpa id (untuk insert baru)
    private int id;
    private int serviceId;
    private double total;
    private Date date;
    
    public Invoice(int serviceId, double total, Date date) {
        this.serviceId = serviceId;
        this.total = total;
        this.date = date;
    }

    // Constructor lengkap (misalnya saat load dari database)
    public Invoice(int id, int serviceId, double total, Date date) {
        this.id = id;
        this.serviceId = serviceId;
        this.total = total;
        this.date = date;
    }

    // Getter & Setter
    public int getId() {
        return id;
    }

    public int getServiceId() {
        return serviceId;
    }

    public double getTotal() {
        return total;
    }

    public Date getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
