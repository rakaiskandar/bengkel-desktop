/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;
import java.util.List;
import models.ServiceRecord;
/**
 *
 * @author HP
 */
public interface ServiceRecordInterface {
    ServiceRecord getById(int id);
    List<ServiceRecord> getByVehicleId(int vehicleId);
    List<ServiceRecord> getAll();
    boolean addService(ServiceRecord service);
    boolean updateService(ServiceRecord service);
    boolean deleteService(int id);
}
