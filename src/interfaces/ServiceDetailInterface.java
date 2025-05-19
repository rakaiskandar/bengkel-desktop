/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;
import java.util.List;
import models.ServiceDetail;

/**
 *
 * @author HP
 */
public interface ServiceDetailInterface {
    ServiceDetail getById(int id);
    List<ServiceDetail> getByServiceId(int serviceId);
    List<ServiceDetail> getAll();
    boolean addServiceDetail(ServiceDetail detail);
    boolean updateServiceDetail(ServiceDetail detail);
    boolean deleteServiceDetail(int id);
}
